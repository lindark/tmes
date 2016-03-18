package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.KaoqinBrushCardRecord;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinBrushCardRecordService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.WorkingBillService;

/**
 * 考勤
 * @author gaoyf
 *
 */
@Service
public class KaoqinServiceImpl extends BaseServiceImpl<Kaoqin, String> implements KaoqinService
{
	@Resource
	private KaoqinDao kqDao;
	@Resource
	private AdminService adminService;
	@Resource
	public void setBaseDao(KaoqinDao kqDao) {
		super.setBaseDao(kqDao);
	}
	@Resource
	private TeamService teamService;
	@Resource
	private KaoqinBrushCardRecordService kqBCRService;
	@Resource
	private HandOverService handOverService;
	@Resource
	private WorkingBillService workingbillservice;

	/**
	 * jqGrid查询
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map)
	{
		return this.kqDao.getKaoqinPager(pager,map);
	}
	/**
	 * 修改Admin表员工工作状态
	 */
	public void updateEmpWorkState(Admin admin)
	{
		Admin a = this.adminService.get(admin.getId());//根据ID查询员工
		a.setWorkstate(admin.getWorkstate());//工作状态
		a.setTardyHours(admin.getTardyHours());//误工小时数
		a.setModifyDate(new Date());
		this.adminService.update(a);
	}

	/**
	 * 查询当天历史员工
	 */
	public List<Kaoqin>getSamedayEmp(String strdate)
	{
		return this.kqDao.getByKqdate(strdate);
	}
	
	/**
	 * 添加新代班员工
	 */
	public void saveNewEmp(String[] ids,String sameteamid,Admin admin)
	{
		for(int i=0;i<ids.length;i++)
		{
			//admin表修改员工状态为代班
			if(ids[i]!=null&&!"".equals(ids[i]))
			{
				Admin a=this.adminService.get(ids[i]);
				a.setIsdaiban(sameteamid);//班组ID
				a.setModifyDate(new Date());
				a.setProductDate(admin.getProductDate());//生产日期
				a.setShift(admin.getShift());//班次
				this.adminService.update(a);
			}
		}
	}
	
	/**
	 * 保存开启考勤(刷卡)记录
	 */
	public void updateBrushCardEmp(String loginid,int my_id)
	{
		Admin admin=this.adminService.get(loginid);//当前登录人
		/**1.保存刷卡记录*/
		KaoqinBrushCardRecord kqbcr=new KaoqinBrushCardRecord();
		kqbcr.setCardnum(admin.getCardNumber());//刷卡人卡号
		kqbcr.setEmpname(admin.getName());//刷卡人姓名
		kqbcr.setAdmin(admin);//admin表
		kqbcr.setCreateDate(new Date());//刷卡时间
		this.kqBCRService.save(kqbcr);
		/**2.获取班组状态,开启考勤/关闭考勤*/
		Team t=admin.getTeam();
		if(my_id==1)
		{
			//开启考勤
			t.setIscancreditcard("N");//是否可以刷卡
			t.setIsWork("Y");//班组状态改为开启状态
			t.setModifyDate(new Date());//修改日期
			this.teamService.update(t);
			
			//初始化开启考勤的人的班组员工的生产日期和班次
			List<Admin>list=this.adminService.getByTeamId(t.getId());
			for(int i=0;i<list.size();i++)
			{
				Admin a=list.get(i);
				if(!a.getId().equals(admin.getId()))
				{
					a.setProductDate(admin.getProductDate());//生产日期
					a.setShift(admin.getShift());//班次
					a.setModifyDate(new Date());//修改日期
					this.adminService.update(a);
				}
			}
			//开启考勤的人改为上班状态
			admin.setWorkstate("2");//开启考勤后,开启人改为上班
			admin.setModifyDate(new Date());
			this.adminService.update(admin);
		}
		else
		{
			//关闭考勤
			t.setIscancreditcard("Y");//可以再刷卡
			t.setModifyDate(new Date());//修改日期
			this.teamService.update(t);
		}
	}
	
	/**
	 * 交接完成后，下班。等操作
	 */
	public String updateHandOver(String handoverid,String mblnr,Admin admin){
		HandOver handover = handOverService.get(handoverid);
		handover.setMblnr(mblnr);
		handover.setState("3");
		handover.setApprovaladmin(admin);
		handOverService.update(handover);
		/***随工单状态更新为交接完成***/
		List<WorkingBill> workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份的所有随工单
		for(WorkingBill workingbill : workingbillList){
			workingbill.setIsHand("Y");
			workingbillservice.update(workingbill);
		}
		/**班组下班**/
		Team team = admin.getTeam();//取得班组
		if("Y".equals(team.getIsWork()))
		{
			String str="s";
			/**根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示*/
			List<Kaoqin>kqlist=this.kqDao.getByTPS(team.getId(),admin.getProductDate(),admin.getShift());
			List<Admin> adminList = adminService.getByTeamId(team.getId());
			if(kqlist!=null&&kqlist.size()>0)
			{
				for(int i=0;i<adminList.size();i++)
				{
					Admin a=adminList.get(i);
					/**员工下班*/
					a.setWorkstate("1");//状态-默认为未上班
					a.setIsdaiban("N");//是否代班默认为N
					a.setModifyDate(new Date());//修改日期
					a.setProductDate(null);//生产日期
					a.setShift(null);//班次
					a.setTardyHours(null);//误工小时数
					this.adminService.update(a);
				}
				str=team.getTeamName();
			}
			else
			{
				saveKq(adminList,team,admin);
			}
			/**班组下班*/
			team.setIsWork("N");
			team.setIscancreditcard("Y");
			team.setModifyDate(new Date());
			this.teamService.update(team);
			return str;
		}
		return "e";
	}

	/**
	 * 点击后刷卡
	 */
	public int updateWorkStateByCreidt(String cardnumber,String teamid,String loginid)
	{
		//根据卡号 班组ID查询
		Admin a=this.adminService.getByCardnumAndTeamid(cardnumber,teamid);
		if(a!=null)
		{
			//过滤已上班的或者代班的
			if(!"2".equals(a.getWorkstate())&&!"6".equals(a.getWorkstate()))
			{
				Admin a_login=this.adminService.get(loginid);
				a.setShift(a_login.getShift());//班次
				a.setProductDate(a_login.getProductDate());//生产日期
				a.setWorkstate("2");
				//是否是代班的
				if(a.getIsdaiban().equals(teamid))
				{
					a.setWorkstate("6");
				}
				a.setModifyDate(new Date());
				this.adminService.update(a);
				return 1;
			}
			return 2;
		}
		return 3;
	}


	/**
	 * 下班
	 */
	public String mergeGoOffWork(String sameTeamId,Admin admin)
	{
		List<Admin>list=this.adminService.getByTeamId(sameTeamId);
		Team t=this.teamService.get(sameTeamId);
		if("Y".equals(t.getIsWork()))
		{
			String str="s";
			/**根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示*/
			List<Kaoqin>kqlist=this.kqDao.getByTPS(sameTeamId,admin.getProductDate(),admin.getShift());
			if(kqlist!=null&&kqlist.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					Admin a=list.get(i);
					/**员工下班*/
					a.setWorkstate("1");//状态-默认为未上班
					a.setIsdaiban("N");//是否代班默认为N
					a.setModifyDate(new Date());//修改日期
					a.setProductDate(null);//生产日期
					a.setShift(null);//班次
					a.setTardyHours(null);//误工小时数
					this.adminService.update(a);
				}
				str=t.getTeamName();
			}
			else
			{
				saveKq(list,t,admin);
			}
			/**班组下班*/
			t.setIsWork("N");
			t.setIscancreditcard("Y");
			t.setModifyDate(new Date());
			this.teamService.update(t);
			return str;
		}
		return "e";
	}
	
	/**
	 * 添加考勤记录
	 */
	public void saveKq(List<Admin>list,Team t,Admin admin)
	{
		String procutdate=admin.getProductDate();
		String shift=admin.getShift();
		for(int i=0;i<list.size();i++)
		{
			Admin a=list.get(i);
			Post p=a.getPost();
			Kaoqin kq=new Kaoqin();
			kq.setCardNumber(a.getCardNumber());//卡号
			kq.setClasstime(shift);//班次
			kq.setEmp(a);//员工
			kq.setEmpname(a.getName());//名字
			kq.setWorkState(a.getWorkstate());//工作状态
			kq.setProductdate(procutdate);//生产日期
			kq.setEmpid(a.getId());//员工主键ID
			kq.setTardyHours(a.getTardyHours());//误工小时数
			//技能名称
			if(p!=null)
			{
				kq.setPostname(p.getPostName());
			}
			else
			{
				kq.setPostname("");
			}
			kq.setTeam(t);//班组
			kq.setCreateDate(new Date());
			kq.setModifyDate(new Date());
			this.save(kq);
			
			/**员工下班*/
			a.setWorkstate("1");//状态-默认为未上班
			a.setIsdaiban("N");//是否代班默认为N
			a.setModifyDate(new Date());//修改日期
			a.setProductDate(null);//生产日期
			a.setShift(null);//班次
			a.setTardyHours(null);//误工小时数
			this.adminService.update(a);
		}
		/**根据登录人查询当前随工单,然后把本班组保存进去,如果随工单已有班组,则不更新*/
		List<WorkingBill>wblist=this.workingbillservice.getListWorkingBillByDate(admin);
		if(wblist!=null&&wblist.size()>0)
		{
			boolean flag=true;
			for(int i=0;i<wblist.size()&&flag;i++)
			{
				WorkingBill wb=wblist.get(i);
				if(wb.getTeam()==null)
				{
					wb.setTeam(t);
					wb.setModifyDate(new Date());
					this.workingbillservice.update(wb);
				}
				else
				{
					flag=false;
				}
			}
		}
	}
	@Override
	public List<Kaoqin> getKaoqinList(String productDate, String shift) {
		return this.kqDao.getKaoqinList(productDate, shift);
	}
}
