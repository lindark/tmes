package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

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
@Repository
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
		Admin a = this.adminService.getByCardnum(admin.getCardNumber());//根据卡号查询员工
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
	public void saveNewEmp(String[] ids,String sameteamid)
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
				this.adminService.update(a);
			}
		}
	}
	
	/**
	 * 修改班组的状态
	 */
	public void updateState(Date date)
	{
		Admin b=this.adminService.getLoginAdmin();
		Admin a=this.adminService.get(b.getId());
		String teamstate=a.getDepartment().getTeam().getState();
		Team team=a.getDepartment().getTeam();
		team=this.teamService.get(team.getId());
		if(!"1".equals(teamstate))
		{
			team.setModifyDate(date);
			team.setState("1");
			this.teamService.update(team);
		}
	}

	/**
	 * 保存开启考勤(刷卡)记录
	 */
	public void saveBrushCardEmp(Admin admin)
	{
		KaoqinBrushCardRecord kqbcr=new KaoqinBrushCardRecord();
		kqbcr.setCardnum(admin.getCardNumber());//刷卡人卡号
		kqbcr.setEmpname(admin.getName());//刷卡人姓名
		kqbcr.setAdmin(admin);//admin表
		kqbcr.setCreateDate(new Date());//刷卡时间
		this.kqBCRService.save(kqbcr);
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
		team.setIsWork("N");
		
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
			String classtime = admin1.getShift();//班次
			String empname = admin1.getName(); //名字
			
			String workState = admin1.getWorkstate();//工作状态
			kaoqin.setCardNumber(cardNumber);
			kaoqin.setClasstime(classtime);
			kaoqin.setEmpname(empname);
			kaoqin.setPostname(postname);
			kaoqin.setTeam(team.getTeamName());
			kaoqin.setWorkState(workState);
			this.save(kaoqin);
			admin1.setWorkstate("1");//未上班
			adminService.update(admin1);//人员下班
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
	
}
