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
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinBrushCardRecordService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.StationService;
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
	private TempKaoqinDao tempKqDao;
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
	@Resource
	private StationService stationService;

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
		a.setUnitdistributeModelSet(admin.getUnitdistributeModelSet());
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
	public String updateBrushCardEmp(String loginid,int my_id)
	{
		String str="";
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
		if(my_id==2)
		{
			//关闭考勤	
			//走班组下班流程								
			if("Y".equals(t.getIsWork()))
			{	
				
				/**根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示*/
				List<Kaoqin> kqList=this.kqDao.getByTPS(t.getId(),admin.getProductDate(),admin.getShift());
				
				if(kqList!=null && kqList.size()>0)
				{
					//数据已经保存
					str="ybc";
					//为了能够重新保存，先全部删除
					for(Kaoqin kq:kqList)
					{
						kqDao.delete(kq);
					}
					kqList.clear();
				}
				
					//不存在考勤数据，要从临时考勤表里读取数据，并进行存储
					List<TempKaoqin> tempList=tempKqDao.getByTPS(t.getId(), admin.getProductDate(), admin.getShift());
					kqList=new ArrayList<Kaoqin>();
					for(TempKaoqin tkq:tempList)
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
						kq.setStationName(tkq.getStationName());
						kq.setWorkName(tkq.getWorkName());
						kq.setIsdaiban(tkq.getIsdaiban());						
						
						kqDao.save(kq);
						kqList.add(kq);
					}					
					str="s";
				
				//处理admin里的下班信息
				for(int i=0;i<kqList.size();i++)
				{
					Admin a=kqList.get(i).getEmp();
					/**员工下班*/
					a.setWorkstate("1");//状态-默认为未上班
					a.setIsdaiban("N");//是否代班默认为N
					a.setModifyDate(new Date());//修改日期
					a.setProductDate(null);//生产日期
					a.setShift(null);//班次
					a.setTardyHours(null);//误工小时数
					this.adminService.update(a);
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
		return "e";
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
		/**根据登录人查询当前随工单,然后把本班组保存进去,如果随工单已有班组,则不更新*/
		List<WorkingBill>wblist=this.workingbillservice.getListWorkingBillByDate(admin);
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
		}
		String procutdate=admin.getProductDate();
		String shift=admin.getShift();
		FactoryUnit fu=t.getFactoryUnit();
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
			
			if(p!=null)
			{
				kq.setPostname(p.getPostName());//岗位名称
				kq.setPostCode(p.getPostCode());//岗位编码
			}
			else
			{
				kq.setPostname("");
			}
			kq.setTeam(t);//班组
			kq.setCreateDate(new Date());
			kq.setModifyDate(new Date());
			//新增保存：字段工号  联系电话  工位  模具组号  工作范围-编码  岗位编码  工位名称  模具组号名称  工作范围名称  单元
			//              
			kq.setWorkCode(a.getWorkNumber());//工号
			kq.setPhoneNum(a.getPhoneNo());//联系电话
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
				kq.setStationCode(code);//工位编码
				kq.setStationName(name);//工位名称
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
				kq.setWorkNum(code);//工作范围-编码
				kq.setWorkName(name);//工作范围名称
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
						code+=um.getStation()+",";
					}
				}
				if(code.endsWith(","))
				{
					code=code.substring(0,code.length()-1);
				}
				kq.setModleNum(code);//模具组号
			}
			//单元
			if(fu!=null)
			{
				kq.setFactoryUnit(fu.getFactoryUnitName());
			}
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
	}
	@Override
	public List<Kaoqin> getKaoqinList(String productDate, String shift) {
		return this.kqDao.getKaoqinList(productDate, shift);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return kqDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return kqDao.historyExcelExport(map);
	}
	@Override
	public String getIsCanCreditCard(Admin admin) {
		List<TempKaoqin> tkqList = tempKqDao.getByTPS(admin.getTeam().getId(), admin.getProductDate(), admin.getShift());
		if(tkqList==null || tkqList.size()<1)//无数据，可以刷卡开启考勤
		{
			return "Y";
		}
		return "N";
	}
	
	public List<Kaoqin> getByTPSA(String sameTeamId, String productDate,String shift,String adminId)
	{
		return kqDao.getByTPSA(sameTeamId, productDate, shift, adminId);
	}
}
