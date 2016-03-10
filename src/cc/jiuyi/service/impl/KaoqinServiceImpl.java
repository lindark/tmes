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
import cc.jiuyi.util.ThinkWayUtil;

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
		a.setWorkstate(admin.getWorkstate());
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
				System.out.println(ids[i]);
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
		Team t=admin.getDepartment().getTeam();
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
	public void mergeAdminafterWork(Admin admin,String handoverId){//状态改成已确认,给人员历史插入数据,人员下班，班组下班。
		/**主表修改状态和确认人**/
		HandOver handover = handOverService.get(handoverId);
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
		Team team = admin.getDepartment().getTeam();//取得班组
		if("Y".equals(team.getIsWork()))
		{
			List<Admin> adminList = adminService.getByTeamId(admin.getDepartment().getTeam().getId());
			for(int i=0;i<adminList.size();i++){
				Admin admin1 = adminList.get(i);
				Kaoqin kaoqin = new Kaoqin();
				Post post = admin1.getPost();
				String postname="";
				if(post == null)
					postname = "";//技能名称
				else
					postname = ThinkWayUtil.null2String(post.getPostName());
				String cardNumber= admin1.getCardNumber();//卡号
				String classtime = admin.getShift();//班次
				String empname = admin1.getName(); //名字
				
				String workState = admin1.getWorkstate();//工作状态
				kaoqin.setCardNumber(cardNumber);
				kaoqin.setClasstime(classtime);
				kaoqin.setEmpname(empname);
				kaoqin.setPostname(postname);
				kaoqin.setTeam(team);
				kaoqin.setWorkState(workState);
				kaoqin.setProductdate(admin.getProductDate());//生产日期
				kaoqin.setEmpid(admin1.getId());//员工主键ID
				this.save(kaoqin);
				admin1.setWorkstate("1");//未上班
				admin1.setProductDate(null);
				admin1.setShift(null);
				admin1.setIsdaiban("N");
				admin1.setModifyDate(new Date());
				adminService.update(admin1);//人员下班
			}
			
			/**班组下班*/
			team.setIsWork("N");
			team.setIscancreditcard("Y");
			team.setModifyDate(new Date());
			this.teamService.update(team);
		}
	}
	
	public void updateHandOver(String handoverid,String mblnr,Admin admin){
		HandOver handover = handOverService.load(handoverid);
		handover.setMblnr(mblnr);
		handover.setState("3");
		handover.setApprovaladmin(admin);
		handOverService.update(handover);
		this.mergeAdminafterWork(admin,handoverid);
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
			/**添加考勤记录*/
			for(int i=0;i<list.size();i++)
			{
				Admin a=list.get(i);
				Post p=a.getPost();
				Kaoqin kq=new Kaoqin();
				kq.setCardNumber(a.getCardNumber());//卡号
				kq.setClasstime(admin.getShift());//班次
				kq.setEmpname(a.getName());//名字
				kq.setWorkState(a.getWorkstate());//工作状态
				kq.setProductdate(admin.getProductDate());//生产日期
				kq.setEmpid(a.getId());//员工主键ID
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
				this.adminService.update(a);
			}
			/**班组下班*/
			t.setIsWork("N");
			t.setIscancreditcard("Y");
			t.setModifyDate(new Date());
			this.teamService.update(t);
			return "s";
		}
		return "e";
	}
}
