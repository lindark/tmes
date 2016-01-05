package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.KaoqinBrushCardRecord;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Team;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.KaoqinBrushCardRecordService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.TeamService;
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
				a.setWorkstate("6");
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
		Admin a=this.adminService.getLoginAdmin();
		a=this.adminService.get(a.getId());
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
	public void mergeAdminafterWork(){//状态改成已确认,给人员历史插入数据,人员下班，班组下班。
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		List<Admin> adminList = adminService.getByTeamId(admin.getDepartment().getTeam().getId());
		for(int i=0;i<adminList.size();i++){
			Admin admin1 = adminList.get(i);
			Kaoqin kaoqin = new Kaoqin();
			Post post = admin1.getPost();
			String cardNumber= admin1.getCardNumber();//卡号
			String classtime = admin1.getShift();//班次
			String empname = admin1.getName(); //名字
			String postname = post.getPostName();//技能名称
			String team = admin.getDepartment().getTeam().getTeamName();//班组
			String workState = admin1.getWorkstate();//工作状态
			kaoqin.setCardNumber(cardNumber);
			kaoqin.setClasstime(classtime);
			kaoqin.setEmpname(empname);
			kaoqin.setPostname(postname);
			kaoqin.setTeam(team);
			kaoqin.setWorkState(workState);
			this.save(kaoqin);
			admin1.setWorkstate("1");//未上班
			adminService.update(admin1);//人员下班
		}
		
		
	}
}
