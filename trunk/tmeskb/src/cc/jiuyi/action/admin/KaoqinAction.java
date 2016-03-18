package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 考勤
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class KaoqinAction extends BaseAdminAction
{

	private static final long serialVersionUID = -7695470770728132309L;
	
	/**========================variable,object,interface  start========================*/
	
	/**
	 * 对象，变量
	 */
	private Kaoqin kaoqin;
	private Admin admin;
	private List<Dict>list_dict;//员工状态
	private List<Kaoqin>list_kq;
	private List<Admin>list_emp;
	private String info;
	private String sameTeamId;//当前班组ID
	private String isstartteam;//是否开启班组
	private String iswork;//班组是否在上班
	private String iscancreditcard;//是否可以刷卡
	private String loginid;//当前登录人的ID
	private String cardnumber;//刷卡人
	private int my_id;
	
	/**
	 * service接口
	 */
	@Resource
	private KaoqinService kqService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private TeamService teamService;
	/**========================end  variable,object,interface==========================*/
	
	/**========================method  start======================================*/
	
	/**
	 * 生产日期或班次是否为空
	 */
	public boolean isnull()
	{
		this.admin=this.adminService.get(loginid);//当前登录人
		String productionDate=admin.getProductDate();//生产日期
		String shift=admin.getShift();//班次
		if(productionDate==null||"".equals(productionDate)||shift==null||"".equals(shift))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 进入list页面
	 */
	public String list()
	{
		this.admin=this.adminService.get(loginid);
		if(!isnull())
		{
			addActionError("请选择生产日期及班次!");
			return ERROR;
		}
		if(this.admin.getDepartment()==null)
		{
			addActionError("部门为空!");
			return ERROR;
		}
		else
		{
			if(this.admin.getTeam()==null)
			{
				addActionError("班组为空!");
				return ERROR;
			}
		}
		//班次
		admin.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", admin.getShift()));
		this.list_dict=this.dictService.getState("adminworkstate");//list中员工的状态
		this.sameTeamId=this.admin.getTeam().getId();//班组ID
		//读取员工到记录表中
		/*List<Admin>l_emp=this.adminService.getByTeamId(tid);//根据班组ID获得班组下的所有员工
		if(l_emp!=null)
		{
			this.list_emp=getNewAdminList(l_emp);
		}*/
		Team t=this.teamService.get(sameTeamId);
		//this.isstartteam=t.getIsWork();//班组是否开启
		this.iscancreditcard=t.getIscancreditcard();//是否可以开启考勤
		this.iswork=t.getIsWork();//班组是否在上班
		return LIST;
	}
	
	/**
	 * 查看历史
	 */
	public String show()
	{
		return "show";
	}
	
	/**
	 * 转入查询员工页面
	 */
	public String beforegetemp()
	{
		this.sameTeamId=this.info;
		return "alert";
	}
	
	/**
	 * 进入页面后查询本班组的员工
	 */
	public String empajlist()
	{
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("modifyDate");//以创建日期排序
		pager = this.adminService.getEmpAjlist(pager,sameTeamId);//查询本班组的员工及在本班代班的员工
		@SuppressWarnings("unchecked")
		List<Admin>list1=pager.getList();
		List<Admin>list2=getNewAdminList(list1);
		pager.setList(list2);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * 查询员工
	 */
	public String getemp()
	{
		this.admin=this.adminService.get(loginid);
		HashMap<String ,String>map=new HashMap<String,String>();
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("createDate");//以创建日期排序
		if(pager.is_search()==true&&Param!=null)
		{
			JSONObject obj=JSONObject.fromObject(Param);
			//班组
			if (obj.get("team") != null)
			{
				String team = obj.getString("team").toString();
				map.put("team", team);
			}
			//班次
			if (obj.get("shift") != null)
			{
				String shift = obj.getString("shift").toString();
				map.put("shift", shift);
			}
			//员工姓名
			if (obj.get("name") != null)
			{
				String name = obj.getString("name").toString();
				map.put("name", name);
			}
			//技能
			if (obj.get("skill") != null)
			{
				String skill = obj.getString("skill").toString();
				map.put("skill", skill);
			}
		}
		pager = this.adminService.getEmpPager(pager, map,admin);
		@SuppressWarnings("unchecked")
		List<Admin>list1=pager.getList();
		List<Admin>list2=getNewAdminList(list1);
		pager.setList(list2);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * jqGrid查询
	 */
	public String ajlist()
	{
		HashMap<String ,String>map=new HashMap<String,String>();
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("createDate");//以创建日期排序
		if(pager.is_search()==true&&Param!=null)
		{
			JSONObject obj=JSONObject.fromObject(Param);
			//班组
			/*if (obj.get("teams") != null)
			{
				String teams = obj.getString("teams").toString();
				map.put("teams", teams);
			}*/
			//班次
			if (obj.get("classtime") != null)
			{
				String classtime = obj.getString("classtime").toString();
				map.put("classtime", classtime);
			}
			//开始日期
			if(obj.get("start")!=null)
			{
				String start = obj.get("start").toString();
				map.put("start", start);
			}
			//结束日期
			if(obj.get("end")!=null)
			{
				String end = obj.get("end").toString();
				map.put("end", end);
			}
		}
		pager = this.kqService.getKaoqinPager(pager, map);
		@SuppressWarnings("unchecked")
		List<Kaoqin>list1=pager.getList();
		List<Kaoqin>list2=new ArrayList<Kaoqin>();
		for(int i=0;i<list1.size();i++)
		{
			Kaoqin k=list1.get(i);
			//k.setXclasstime(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", k.getClasstime()));
			//k.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", k.getWorkState()));
			list2.add(k);
		}
		pager.setList(list2);
		JSONArray jsonArray=JSONArray.fromObject(pager);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * 修改Admin表员工工作状态
	 */
	public String updateEmpWorkState()
	{
		this.kqService.updateEmpWorkState(admin);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 添加新代班员工
	 */
	public String addnewemp()
	{
		try
		{
			if(!isnull())
			{
				return this.ajaxJsonErrorMessage("2");//生产日期或班次不能为空!
			}
			if(ids!=null)
			{
				ids=ids[0].split(",");
				this.kqService.saveNewEmp(ids,sameTeamId,admin);
			}
			return this.ajaxJsonSuccessMessage("1");//添加成功
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("3");//系统出现异常
		}
	}
	
	/**
	 * 开启考勤
	 */
	public String creditreply()
	{
		if(!isnull())
		{
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		//保存开启考勤(刷卡)记录
		this.kqService.updateBrushCardEmp(loginid,my_id);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 点击后刷卡
	 * creditapproval
	 */
	public String creditapproval()
	{
		Admin a=this.adminService.getByCardnum(cardnumber);
		//修改刷卡 人的状态,返回：1修改成功 2已经刷卡成功无需重复刷卡  3不是本班员工或本班代班员工
		int n=this.kqService.updateWorkStateByCreidt(cardnumber,sameTeamId,loginid);
		if(n==1)
		{
			return this.ajaxJsonSuccessMessage(a.getName()+"刷卡成功!");
		}
		else if(n==2)
		{
			return this.ajaxJsonErrorMessage(a.getName()+"已经刷卡成功,无需重复刷卡!");
		}
		else
		{
			return this.ajaxJsonErrorMessage(a.getName()+"非本班或本班代班员工刷卡无效!");
		}
	}
	
	/**
	 * 下班前获取生产日期和班次
	 */
	public String getDateAndShift()
	{
		try
		{
			this.admin=this.adminService.get(loginid);//当前登录人
			String productionDate=admin.getProductDate();//生产日期
			String shift=admin.getShift();//班次
			if(productionDate==null||"".equals(productionDate)||shift==null||"".equals(shift))
			{
				return this.ajaxJsonErrorMessage("1");
			}
			String xshift=ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses",shift);
			return this.ajaxJsonSuccessMessage("生产日期："+productionDate+",班次："+xshift+",将记录于本次员工考勤,确定下班吗?");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("2");
		}
	}
	
	/**
	 * 下班
	 */
	public String creditundo()
	{
		HashMap<String, String> hashmap = new HashMap<String, String>();
		if(!isnull())
		{
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		String productdate=admin.getProductDate();//生产日期
		String shift=admin.getShift();//班次
		String str=this.kqService.mergeGoOffWork(this.sameTeamId,admin);
		if("s".equals(str))
		{
			//操作成功
			hashmap.put("info", str);
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "您的操作已成功!");
			return this.ajaxJson(hashmap);
		}
		else if("e".equals(str))
		{
			//操作错误
			hashmap.put("info", str);
			hashmap.put(STATUS, "error");
			hashmap.put(MESSAGE, "该班组已经下班,请刷新页面查看!");
			return this.ajaxJson(hashmap);
		}
		else
		{
			//操作成功,考勤已保存过,提示本次不在保存
			String xshift=ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses",shift);
			hashmap.put("info", "t");
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "班组下班成功!班组:"+str+",生产日期:"+productdate+",班次:"+xshift+",已经记录到考勤,本次不再重复记录!");
			return this.ajaxJson(hashmap);
		}
	}
	
	/**
	 * Admin表假字段
	 */
	public List<Admin>getNewAdminList(List<Admin>list1)
	{
		List<Admin>list2=new ArrayList<Admin>();
		for(int i=0;i<list1.size();i++)
		{
			Admin a=list1.get(i);
			//班次
			if(a.getShift()!=null&&!"".equals(a.getShift()))
			{
				a.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", a.getShift()));
			}
			//班组
			if(a.getDepartment()!=null)
			{
				if(a.getTeam()!=null)
				{
					a.setXteam( a.getTeam().getTeamName());
				}
			}
			//工作状态
			if(a.getWorkstate()!=null&&!"".equals(a.getWorkstate()))
			{
				a.setXworkstate(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", a.getWorkstate()));
			}
			//岗位
			if(a.getPost()!=null)
			{
				a.setXpost(a.getPost().getPostName());
				a.setXgongwei(a.getPost().getStation());//工位
			}
			//模具组号
			List<UnitdistributeProduct>list_up=new ArrayList<UnitdistributeProduct>(a.getUnitdistributeProductSet());
			if(list_up.size()>0)
			{
				String str="";
				for(int j=0;j<list_up.size();j++)
				{
					UnitdistributeProduct up=list_up.get(j);
					if(up.getMaterialName()!=null)
					{
						str+=up.getMaterialName()+",";
					}
				}
				if(str.endsWith(","))
				{
					str=str.substring(0,str.length()-1);
				}
				a.setXworkscope(str);
			}
			//工作范围
			List<UnitdistributeModel>list_um=new ArrayList<UnitdistributeModel>(a.getUnitdistributeModelSet());
			if(list_um.size()>0)
			{
				String str="";
				for(int j=0;j<list_um.size();j++)
				{
					UnitdistributeModel um=list_um.get(j);
					if(um.getStation()!=null)
					{
						str+=um.getStation()+",";
					}
				}
				if(str.endsWith(","))
				{
					str=str.substring(0,str.length()-1);
				}
				a.setXstation(str);
			}
			list2.add(a);
		}
		return list2;
	}
	/**========================end  method======================================*/
	
	/**===========================get/set start=============================*/
	
	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
	}

	public List<Dict> getList_dict()
	{
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict)
	{
		this.list_dict = list_dict;
	}

	public Kaoqin getKaoqin()
	{
		return kaoqin;
	}

	public void setKaoqin(Kaoqin kaoqin)
	{
		this.kaoqin = kaoqin;
	}

	public List<Kaoqin> getList_kq()
	{
		return list_kq;
	}

	public void setList_kq(List<Kaoqin> list_kq)
	{
		this.list_kq = list_kq;
	}

	public List<Admin> getList_emp()
	{
		return list_emp;
	}

	public void setList_emp(List<Admin> list_emp)
	{
		this.list_emp = list_emp;
	}

	public String getSameTeamId()
	{
		return sameTeamId;
	}

	public void setSameTeamId(String sameTeamId)
	{
		this.sameTeamId = sameTeamId;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getIscancreditcard()
	{
		return iscancreditcard;
	}

	public void setIscancreditcard(String iscancreditcard)
	{
		this.iscancreditcard = iscancreditcard;
	}

	public String getIsstartteam()
	{
		return isstartteam;
	}

	public void setIsstartteam(String isstartteam)
	{
		this.isstartteam = isstartteam;
	}

	public String getLoginid()
	{
		return loginid;
	}

	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}

	public String getCardnumber()
	{
		return cardnumber;
	}

	public void setCardnumber(String cardnumber)
	{
		this.cardnumber = cardnumber;
	}

	public int getMy_id()
	{
		return my_id;
	}

	public void setMy_id(int my_id)
	{
		this.my_id = my_id;
	}

	public String getIswork()
	{
		return iswork;
	}

	public void setIswork(String iswork)
	{
		this.iswork = iswork;
	}

	/**===========================end get/set===============================*/
}
