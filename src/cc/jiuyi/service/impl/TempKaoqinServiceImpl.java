package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.dao.TempKaoqinDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.KaoqinBrushCardRecord;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Station;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.TempKaoqin;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinBrushCardRecordService;
import cc.jiuyi.service.StationService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 考勤
 * @author gaoyf
 *
 */
@Service
public class TempKaoqinServiceImpl extends BaseServiceImpl<TempKaoqin, String> implements TempKaoqinService
{
	@Resource
	private KaoqinDao kqDao;
	@Resource
	private TempKaoqinDao tempKqDao;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	public void setBaseDao(TempKaoqinDao tempKqDao) {
		super.setBaseDao(tempKqDao);
	}
	@Resource
	private TeamService teamService;
	@Resource
	private KaoqinBrushCardRecordService kqBCRService;
//	@Resource
//	private HandOverService handOverService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private StationService stationService;

	/**
	 * jqGrid查询
	 */
	
	public Admin getAdminWorkStateByAdmin(Admin admin)
	{
		if(admin==null || admin.getTeam()==null || admin.getTeam().getFactoryUnit()==null || admin.getWorkNumber()==null || admin.getWorkNumber().equals("") ||	 admin.getProductDate()==null || admin.getProductDate().equals("") ||	admin.getShift()==null || admin.getShift().equals(""))
		{
			admin.setWorkstate("1");//设置成未上班
			return admin;
		}
		List<TempKaoqin> tkqList=tempKqDao.getByTPSA(admin.getTeam().getFactoryUnit().getId(), admin.getProductDate(), admin.getShift(), admin.getId());
		if(tkqList!=null && tkqList.size()>0)
		{
			admin.setWorkstate(tkqList.get(0).getWorkState());
			return admin;
		}
		else
		{
			admin.setWorkstate("1");//设置成未上班
			return admin;
		}		
	}
	
	public Pager getTempKaoqinPager(Pager pager, HashMap<String, String> map)
	{
		return this.tempKqDao.getTempKaoqinPager(pager,map);
	}
	

	/**
	 * 查询当天历史员工
	 */
	public List<TempKaoqin>getSamedayEmp(String strdate)
	{
		return this.tempKqDao.getByKqdate(strdate);
	}
	
	/**
	 * 添加新代班员工
	 */
	public void saveNewEmp(String[] ids,String sameteamid,Admin admin,String workHours)
	{
		admin=adminService.get(admin.getId());
		Team team=admin.getTeam();
		List<Admin> adminList=new ArrayList<Admin>();
		for(int i=0;i<ids.length;i++)
		{			
			if(ids[i]!=null&&!"".equals(ids[i]))
			{
				Admin a=this.adminService.get(ids[i]);			
				a.setModifyDate(new Date());
				a.setProductDate(admin.getProductDate());//生产日期
				a.setShift(admin.getShift());//班次
				//this.adminService.update(a);//添加代班的不修改admin信息，已经注释
				//先update，后设置，此处并未保存代班字段，仅用于生成临时考勤使用。
				a.setIsdaiban(sameteamid);//班组ID
				adminList.add(a);
			}			
		}
		//先存储临时，再存储记录
		saveKqListByTkqList( saveTkqList(adminList,team,admin,workHours));
		for(Admin a :adminList){
			a.setIsdaiban("N");
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
			List<Admin> adminList=this.adminService.getByTeamId(t.getId());
			for(int i=0;i<adminList.size();i++)
			{
				Admin a=adminList.get(i);				
				a.setProductDate(admin.getProductDate());//生产日期
				a.setShift(admin.getShift());//班次
				a.setModifyDate(new Date());//修改日期
				//this.adminService.update(a);				
			}					
			
			/**根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示*/
			List<TempKaoqin> kqlist=this.tempKqDao.getByTPS(t.getId(),admin.getProductDate(),admin.getShift());			
			if(kqlist != null && kqlist.size() > 0 )
			{
				//之前已经开启过考勤，	无需重新添加数据				
								
				for(TempKaoqin tkq:kqlist)
				{					
					//开启考勤的人的考勤记录改为上班状态
					if(tkq.getId().equals(loginid))
					{
						tkq.setWorkState("2");
						tempKqDao.update(tkq);
						Kaoqin kq=kqDao.getByTPSA(tkq.getTeam().getFactoryUnit().getId(), tkq.getProductdate(), tkq.getClasstime(), tkq.getEmp().getId()).get(0);
						kq.setWorkState(tkq.getWorkState());
						kqDao.update(kq);
					}
					
				}
			}
			else
			{			
				//开启考勤后存到临时记录表里,同时存储到记录表里
				saveKqListByTkqList(saveTkqList(adminList, t, admin, null)); 
			}			
			
			
			/*List<WorkingBill>wblist=this.workingbillservice.getListWorkingBillByDate(admin);
			if(wblist!=null&&wblist.size()>0)
			{
				for(int i=0;i<wblist.size();i++)
				{
					WorkingBill wb=wblist.get(i);
					if(wb.getTeam()==null)
					{
						wb.setTeam(t);
						wb.setModifyDate(new Date());
						this.workingbillservice.update(wb);
					}
				}
			}*/
			
			
		}		
		
	}
	
	
	
	
	

	/**
	 * 点击后刷卡
	 */
	public int updateWorkStateByCreidt(String cardnumber,String teamid,String loginid)
	{
		//根据卡号查询admin
		Admin a=adminService.getByCardnum(cardnumber);	
		Admin a_login=this.adminService.get(loginid);
		if(a!=null)
		{	
			Team team = teamService.get(teamid);
			List<TempKaoqin> tkqs=tempKqDao.getByTPSA(team.getFactoryUnit().getId(), a_login.getProductDate(), a_login.getShift(), a.getId());			
			if(tkqs!=null && tkqs.size()>0) 
			{
				TempKaoqin tqk=tkqs.get(0);
				//过滤已上班的
				if(tqk.getWorkState().equals("2"))
				{
					//已上班，无需重新刷卡
					return 2;
				}
				tqk.setWorkHours(tqk.getWorkHours());
				tqk.setWorkState("2");
				tqk.setModifyDate(new Date());
				tempKqDao.update(tqk);				
				List<Kaoqin> kqs=kqDao.getByTPSA(team.getFactoryUnit().getId(), a_login.getProductDate(), a_login.getShift(), a.getId());
				if(kqs!=null && kqs.size()>0) 
				{
					tqk.setWorkHours(tqk.getWorkHours());
					Kaoqin qk=kqs.get(0);
					qk.setWorkState("2");
					qk.setModifyDate(new Date());
					kqDao.update(qk);
				}
				return 1;
				//admin不做修改
				/*a.setShift(a_login.getShift());//班次
				a.setProductDate(a_login.getProductDate());//生产日期				
				a.setModifyDate(new Date());
				this.adminService.update(a);*/
			}else{
				if(a.getTeam()==null){
					//并无此人员的考勤临时记录
					return 3;
				}else{
					if(a.getTeam().getId().equals(teamid)){
						a.setShift(a_login.getShift());//班次
						a.setProductDate(a_login.getProductDate());//生产日期				
						a.setModifyDate(new Date());
						this.adminService.update(a);
						saveKaoqin(saveTeamKaoqin(a));
						return 1;
					}else{
						return 3;
					}
				}
			}
		}			
		//卡号无对应的人员
		return 3;
	}


	
	
	/**
	 * 添加临时考勤记录
	 */
	public List<TempKaoqin> saveTkqList(List<Admin>list,Team t,Admin admin,String workHours)
	{
		List<TempKaoqin> tkqList=new ArrayList<TempKaoqin>();
		String procutdate=admin.getProductDate();
		String shift=admin.getShift();
		FactoryUnit fu=t.getFactoryUnit();
		for(int i=0;i<list.size();i++)
		{
			Admin a=list.get(i);
			Post p=a.getPost();
			TempKaoqin tkq=new TempKaoqin();
			tkq.setCardNumber(a.getCardNumber());//卡号
			tkq.setClasstime(shift);//班次
			tkq.setEmp(a);//员工
			tkq.setEmpid(a.getId());
			tkq.setEmpname(a.getName());//名字
			tkq.setWorkState("1");//工作状态
			
			//如果是刷卡登陆的人，就将其改为已上班状态
			if(a.getId().equals(admin.getId()))
			{
				tkq.setWorkState("2");//工作状态
			}
			
			tkq.setProductdate(procutdate);//生产日期
			tkq.setEmpid(a.getId());//员工主键ID
			tkq.setTardyHours(null);//误工小时数
			tkq.setIsdaiban(a.getIsdaiban());
			
			if(p!=null)
			{
				tkq.setPostname(p.getPostName());//岗位名称
				tkq.setPostCode(p.getPostCode());//岗位编码
			}
			else
			{
				tkq.setPostname("");
			}
			tkq.setTeam(t);//班组
			tkq.setCreateDate(new Date());
			tkq.setModifyDate(new Date());
			//新增保存：字段工号  联系电话  工位  模具组号  工作范围-编码  岗位编码  工位名称  模具组号名称  工作范围名称  单元
			//              
			tkq.setWorkCode(a.getWorkNumber());//工号
			tkq.setPhoneNum(a.getPhoneNo());//联系电话
			//工位   工位名称
			if(a.getStationids()!=null)
			{
				String []sids=a.getStationids().split(",");
				String code="";
				String name="";
				for(int j=0;j<sids.length;j++)
				{
					Station s=this.stationService.get(sids[j]);
					if(s!=null&&"N".equals(s.getIsDel()))
					{
						if(s.getCode()!=null)
						{
							code+=s.getCode()+",";
						}
						if(s.getName()!=null)
						{
							name+=s.getName()+",";
						}
					}
				}
				if(name.endsWith(","))
				{
					name=name.substring(0,name.length()-1);
				}
				if(code.endsWith(","))
				{
					code=code.substring(0,code.length()-1);
				}
				tkq.setStationCode(code);//工位编码
				tkq.setStationName(name);//工位名称
			}
			//工作范围-编码   工作范围名称
			List<UnitdistributeProduct>list_up=new ArrayList<UnitdistributeProduct>(a.getUnitdistributeProductSet());
			if(list_up.size()>0)
			{
				String code="";
				String name="";
				for(int j=0;j<list_up.size();j++)
				{
					UnitdistributeProduct up=list_up.get(j);
					if(up!=null&&"N".equals(up.getIsDel()))
					{
						if(up.getMaterialCode()!=null)
						{
							code+=up.getMaterialCode()+",";
						}
						if(up.getMaterialName()!=null)
						{
							name+=up.getMaterialName()+",";
						}
					}
				}
				if(code.endsWith(","))
				{
					code=code.substring(0,code.length()-1);
				}
				if(name.endsWith(","))
				{
					name=name.substring(0,name.length()-1);
				}
				tkq.setWorkNum(code);//工作范围-编码
				tkq.setWorkName(name);//工作范围名称
			}
			//模具组号    模具组号名称
			List<UnitdistributeModel>list_um=new ArrayList<UnitdistributeModel>(a.getUnitdistributeModelSet());
			if(list_um.size()>0)
			{
				String code="";
				for(int j=0;j<list_um.size();j++)
				{
					UnitdistributeModel um=list_um.get(j);
					if(um!=null&&"N".equals(um.getIsDel())&&um.getStation()!=null)
					{
						code+=um.getId()+",";
					}
				}
				if(code.endsWith(","))
				{
					code=code.substring(0,code.length()-1);
				}
				tkq.setModelNum(code);//模具组号
			}
			//单元
			if(fu!=null)
			{
				tkq.setFactoryUnit(fu.getFactoryUnitName());
				tkq.setFactoryUnitCode(fu.getFactoryUnitCode());
			}
			tkq.setWorkHours(workHours);
			this.save(tkq);
			tkqList.add(tkq);
			
		}
		return tkqList;
	}
	public TempKaoqin saveTeamKaoqin(Admin admin){
		String procutdate=admin.getProductDate();
		String shift=admin.getShift();
		FactoryUnit fu=admin.getTeam().getFactoryUnit();
		Post p=admin.getPost();
		TempKaoqin tkq=new TempKaoqin();
		tkq.setCardNumber(admin.getCardNumber());//卡号
		tkq.setClasstime(shift);//班次
		tkq.setEmp(admin);//员工
		tkq.setEmpid(admin.getId());
		tkq.setEmpname(admin.getName());//名字
		tkq.setWorkState("2");//工作状态
		tkq.setProductdate(procutdate);//生产日期
		tkq.setEmpid(admin.getId());//员工主键ID
		tkq.setTardyHours(null);//误工小时数
		tkq.setIsdaiban(admin.getIsdaiban());
		List<TempKaoqin> tkqlist =  tempKqDao.getToWorkList(procutdate,shift,"2",admin.getTeam());
		tkq.setWorkHours(tkqlist.get(0).getWorkHours());
		if(p!=null)
		{
			tkq.setPostname(p.getPostName());//岗位名称
			tkq.setPostCode(p.getPostCode());//岗位编码
		}
		else
		{
			tkq.setPostname("");
		}
		tkq.setTeam(admin.getTeam());//班组
		tkq.setCreateDate(new Date());
		tkq.setModifyDate(new Date());
		//新增保存：字段工号  联系电话  工位  模具组号  工作范围-编码  岗位编码  工位名称  模具组号名称  工作范围名称  单元
		//              
		tkq.setWorkCode(admin.getWorkNumber());//工号
		tkq.setPhoneNum(admin.getPhoneNo());//联系电话
		//工位   工位名称
		if(admin.getStationids()!=null)
		{
			String []sids=admin.getStationids().split(",");
			String code="";
			String name="";
			for(int j=0;j<sids.length;j++)
			{
				Station s=this.stationService.get(sids[j]);
				if(s!=null&&"N".equals(s.getIsDel()))
				{
					if(s.getCode()!=null)
					{
						code+=s.getCode()+",";
					}
					if(s.getName()!=null)
					{
						name+=s.getName()+",";
					}
				}
			}
			if(name.endsWith(","))
			{
				name=name.substring(0,name.length()-1);
			}
			if(code.endsWith(","))
			{
				code=code.substring(0,code.length()-1);
			}
			tkq.setStationCode(code);//工位编码
			tkq.setStationName(name);//工位名称
		}
		//工作范围-编码   工作范围名称
		List<UnitdistributeProduct>list_up=new ArrayList<UnitdistributeProduct>(admin.getUnitdistributeProductSet());
		if(list_up.size()>0)
		{
			String code="";
			String name="";
			for(int j=0;j<list_up.size();j++)
			{
				UnitdistributeProduct up=list_up.get(j);
				if(up!=null&&"N".equals(up.getIsDel()))
				{
					if(up.getMaterialCode()!=null)
					{
						code+=up.getMaterialCode()+",";
					}
					if(up.getMaterialName()!=null)
					{
						name+=up.getMaterialName()+",";
					}
				}
			}
			if(code.endsWith(","))
			{
				code=code.substring(0,code.length()-1);
			}
			if(name.endsWith(","))
			{
				name=name.substring(0,name.length()-1);
			}
			tkq.setWorkNum(code);//工作范围-编码
			tkq.setWorkName(name);//工作范围名称
		}
		//模具组号    模具组号名称
		List<UnitdistributeModel>list_um=new ArrayList<UnitdistributeModel>(admin.getUnitdistributeModelSet());
		if(list_um.size()>0)
		{
			String code="";
			for(int j=0;j<list_um.size();j++)
			{
				UnitdistributeModel um=list_um.get(j);
				if(um!=null&&"N".equals(um.getIsDel())&&um.getStation()!=null)
				{
					code+=um.getId()+",";
				}
			}
			if(code.endsWith(","))
			{
				code=code.substring(0,code.length()-1);
			}
			tkq.setModelNum(code);//模具组号
		}
		//单元
		if(fu!=null)
		{
			tkq.setFactoryUnit(fu.getFactoryUnitName());
			tkq.setFactoryUnitCode(fu.getFactoryUnitCode());
		}
		this.save(tkq);
		return tkq;
	}
	public void saveKaoqin(TempKaoqin tkq){

		Kaoqin kq=new Kaoqin();						
		kq.setId(null);
		kq.setCreateDate(new Date());
		kq.setModifyDate(new Date());
		
		kq.setCardNumber(tkq.getCardNumber());
		kq.setClasstime(tkq.getClasstime());
		kq.setEmp(tkq.getEmp());
		kq.setEmpid(tkq.getEmpid());
		kq.setEmpname(tkq.getEmpname());
		kq.setPostname(tkq.getPostname());
		kq.setTeam(tkq.getTeam());
		kq.setWorkState(tkq.getWorkState());
		kq.setProductdate(tkq.getProductdate());
		kq.setTardyHours(tkq.getTardyHours());
		kq.setWorkCode(tkq.getWorkCode());
		kq.setPhoneNum(tkq.getPhoneNum());
		kq.setStationCode(tkq.getStationCode());
		kq.setModleNum(tkq.getModelNum());
		kq.setWorkNum(tkq.getWorkNum());
		kq.setPostCode(tkq.getPostCode());
		kq.setFactoryUnit(tkq.getFactoryUnit());
		kq.setFactoryUnitCode(tkq.getFactoryUnitCode());
		kq.setStationName(tkq.getStationName());
		kq.setWorkName(tkq.getWorkName());
		kq.setIsdaiban(tkq.getIsdaiban());						
		kq.setWorkHours(tkq.getWorkHours());
		kqDao.save(kq);
	}
	
	private List<Kaoqin> saveKqListByTkqList(List<TempKaoqin> tkqList)
	{
		
		List<Kaoqin> kqList=new ArrayList<Kaoqin>();
		for(TempKaoqin tkq:tkqList)
		{
			Kaoqin kq=new Kaoqin();						
			kq.setId(null);
			kq.setCreateDate(new Date());
			kq.setModifyDate(new Date());
			
			kq.setCardNumber(tkq.getCardNumber());
			kq.setClasstime(tkq.getClasstime());
			kq.setEmp(tkq.getEmp());
			kq.setEmpid(tkq.getEmpid());
			kq.setEmpname(tkq.getEmpname());
			kq.setPostname(tkq.getPostname());
			kq.setTeam(tkq.getTeam());
			kq.setWorkState(tkq.getWorkState());
			kq.setProductdate(tkq.getProductdate());
			kq.setTardyHours(tkq.getTardyHours());
			kq.setWorkCode(tkq.getWorkCode());
			kq.setPhoneNum(tkq.getPhoneNum());
			kq.setStationCode(tkq.getStationCode());
			kq.setModleNum(tkq.getModelNum());
			kq.setWorkNum(tkq.getWorkNum());
			kq.setPostCode(tkq.getPostCode());
			kq.setFactoryUnit(tkq.getFactoryUnit());
			kq.setFactoryUnitCode(tkq.getFactoryUnitCode());
			kq.setStationName(tkq.getStationName());
			kq.setWorkName(tkq.getWorkName());
			kq.setIsdaiban(tkq.getIsdaiban());						
			kq.setWorkHours(tkq.getWorkHours());
			kqDao.save(kq);
			kqList.add(kq);
		}
		return kqList;
	}
	
	
	
	
	
	@Override
	public List<TempKaoqin> getTempKaoqinList(String productDate, String shift) {
		return this.tempKqDao.getTempKaoqinList(productDate, shift);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return tempKqDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return tempKqDao.historyExcelExport(map);
	}


	@Override
	public List<TempKaoqin> getByTPS(String sameTeamId, String productDate,
			String shift) {
		return tempKqDao.getByTPS(sameTeamId, productDate, shift);
	}


	@Override
	public List<TempKaoqin> getByTPSA(String sameTeamId, String productDate,
			String shift, String adminId) {
		return tempKqDao.getByTPSA(sameTeamId, productDate, shift, adminId);
	}
	
	@Override
	public List<TempKaoqin> getWorkNumList(String productDate, String shift,
			String factoryUnitCode, String workState) {
		return tempKqDao.getWorkNumList(productDate, shift, factoryUnitCode, workState);
	}
	
	@Override
	public List<TempKaoqin> getToWorkList(String productDate, String shift,
			String factoryUnitCode, String workState,Team team) {
		return tempKqDao.getToWorkList(productDate, shift, factoryUnitCode, workState,team);
	}
	public void updateWorkHours(String workHours,String productdate,String classtime,Team team){
		tempKqDao.updateWorkHours(workHours,productdate,classtime,team);
	}
	
	@Override
	public List<TempKaoqin> getToWorkList(String productDate, String shift, String workState,Team team) {
		return tempKqDao.getToWorkList(productDate, shift, workState,team);
	}
	public Object[] sumAmount(String productDate, String shift,String workState,String teamid){
		return tempKqDao.sumAmount(productDate, shift, workState, teamid);
	}

	@Override
	public List<TempKaoqin> getKaoqinList(String productDate, String shift,
			String factoryUnitCode) {
		return tempKqDao.getKaoqinList(productDate, shift, factoryUnitCode);
	}
}
