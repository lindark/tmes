package cc.jiuyi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
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
	private DictService dictService;
	/**
	 * 根据当前的日期yyyyMMdd查询数据
	 */
	public void saveByKqdate(List<Admin>list,String strdate)
	{
		List<Kaoqin>listkq=this.kqDao.getByKqdate(strdate);
		if(listkq.size()==0)
		{
			for(int i=0;i<list.size();i++)
			{
				Admin a=list.get(i);
				Kaoqin kq=new Kaoqin();
				kq.setCardNum(a.getCardNumber());//卡号
				kq.setEmpName(a.getName());//员工姓名
				kq.setTeams(a.getDepartment().getTeam().getTeamName());//班组
				kq.setClasstime(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", a.getShift()));//班次
				kq.setWorkState( a.getWorkstate());//工作状态
				kq.setKqdate(strdate);//当前日期
				kq.setSkill(a.getPost().getPostName());//技能
				kq.setAdminId(a.getId());//员工表主键
				kq.setCreateDate(new Date());
				kq.setModifyDate(new Date());
				this.kqDao.save(kq);
			}
		}
	}

	/**
	 * jqGrid查询
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map)
	{
		return this.kqDao.getKaoqinPager(pager,map);
	}
	/**
	 * 修改员工工作状态
	 */
	public void updateWorkState(Kaoqin kaoqin)
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String strdate=sdf.format(date);
		// 修改kaoqin表中状态
		System.out.println(kaoqin.getId());
		Kaoqin kq = this.kqDao.getByCardnumAndSameday(kaoqin.getCardNum(),strdate);
		kq.setWorkState(kaoqin.getWorkState());
		kq.setModifyDate(new Date());
		this.kqDao.update(kq);

		// 修改admin表中状态
		Admin a = this.adminService.get(kq.getAdminId());
		a.setWorkstate(kaoqin.getWorkState());
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
	public void saveNewEmp(String[] ids)
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String strdate=sdf.format(date);
		for(int i=0;i<ids.length;i++)
		{
			//admin表修改员工状态为代班
			if(ids[i]!=null&&!"".equals(ids[i]))
			{
				System.out.println(ids[i]);
				Admin a=this.adminService.get(ids[i]);
				a.setWorkstate("6");
				a.setModifyDate(new Date());
				this.adminService.update(a);
				
				//根据员工id和当前日期查询
				Kaoqin k=this.kqDao.getByCardnumAndSameday(a.getCardNumber(),strdate);
				if(k==null)
				{
					//新增代班员工，状态默认为代班
					Kaoqin kq=new Kaoqin();
					kq.setCardNum(a.getCardNumber());//卡号
					kq.setEmpName(a.getName());//员工姓名
					kq.setTeams(a.getDepartment().getTeam().getTeamName());//班组
					kq.setClasstime(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", a.getShift()));//班次
					kq.setWorkState( "6");//工作状态
					kq.setKqdate(strdate);//当前日期
					kq.setSkill(a.getPost().getPostName());//技能
					kq.setAdminId(a.getId());//员工表主键
					kq.setCreateDate(new Date());
					kq.setModifyDate(new Date());
					this.kqDao.save(kq);
				}
			}
		}
	}
}
