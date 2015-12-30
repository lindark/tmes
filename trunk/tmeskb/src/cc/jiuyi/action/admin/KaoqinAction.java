package cc.jiuyi.action.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.action.cron.KaoqinMonitor;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Team;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.util.QuartzManagerUtil;
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
	private String iscancreditcard;//是否可以刷卡
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
	 * 进入list页面
	 */
	public String list()
	{
		this.admin=this.adminService.getLoginAdmin();
		this.admin=this.adminService.get(admin.getId());
		this.list_dict=this.dictService.getState("adminworkstate");//list中员工的状态
		String tid=this.admin.getDepartment().getTeam().getId();//班组ID
		//读取员工到记录表中
		List<Admin>l_emp=this.adminService.getByTeamId(tid);//根据班组ID获得班组下的所有员工
		if(l_emp!=null)
		{
			this.list_emp=getNewAdminList(l_emp);
		}
		Team t=this.teamService.get(tid);
		this.isstartteam=t.getIsWork();//班组是否开启
		this.iscancreditcard=t.getIscancreditcard();//是否可以开启考勤
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
	 * 查询员工
	 */
	public String getemp()
	{
		this.admin=this.adminService.getLoginAdmin();
		this.admin=this.adminService.get(admin.getId());
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
			if (obj.get("teams") != null)
			{
				String teams = obj.getString("teams").toString();
				map.put("teams", teams);
			}
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
		
		if(ids!=null)
		{
			ids=ids[0].split(",");
			this.kqService.saveNewEmp(ids,sameTeamId);
		}
		return ajaxJsonSuccessMessage("s");
	}
	
	/**
	 * 开启考勤
	 */
	public String creditreply()
	{
		
		/**获取班组状态*/
		this.admin=this.adminService.getLoginAdmin();
		this.admin=this.adminService.get(admin.getId());
		Team t=admin.getDepartment().getTeam();
		t.setIscancreditcard("N");
		t.setModifyDate(new Date());
		this.teamService.update(t);
		
		String job_name = "startWorking"+this.sameTeamId;
		SimpleDateFormat sdf=new SimpleDateFormat("HH dd MM ? yyyy");
		
		//当前时间
		Calendar can=Calendar.getInstance();
		this.kqService.updateState(can.getTime());
		int fen=can.get(Calendar.MINUTE);//分
		int miao=can.get(Calendar.SECOND);//秒
		
		//当前时间的后1整点
		Calendar can2=Calendar.getInstance();
		can2.add(Calendar.HOUR_OF_DAY, 1);
		can2.set(Calendar.MINUTE, 0);
		can2.set(Calendar.SECOND,0);
		
		//两个时间的相差分钟
		int differ=Integer.parseInt(String.valueOf((can2.getTimeInMillis()-can.getTimeInMillis())/(60*1000)));
		int n=40-differ;
		QuartzManagerUtil.removeJob(job_name);
		QuartzManagerUtil.removeJob("xxx"+job_name);
		HashMap<String,Object> map1=new HashMap<String,Object>();
		HashMap<String,Object> map2=new HashMap<String,Object>();
		if(n<=0)
		{
			/**不跨时*/
			String xquartz=miao+" "+fen+"-"+(fen+40)+"/1 "+sdf.format(can.getTime());
			map1.put("kaoqintime", xquartz);
			map1.put("teamid", job_name);
			//添加定时任务
			QuartzManagerUtil.addJob(job_name, KaoqinMonitor.class, xquartz,map1);
		}
		else
		{
			/**跨时*/
			String xquartz=miao+" "+fen+"-59/1 "+sdf.format(can.getTime());
			String x2quartz="0 0-"+n+"/1 "+sdf.format(can2.getTime());
			//添加定时任务
			map1.put("kaoqintime", xquartz);
			map1.put("teamid", job_name);
			QuartzManagerUtil.addJob(job_name, KaoqinMonitor.class, xquartz,map1);
			
			map2.put("kaoqintime", x2quartz);
			map2.put("teamid", "xxx"+job_name);
			map2.put("d_value", n+"");
			QuartzManagerUtil.addJob("xxx"+job_name, KaoqinMonitor.class, x2quartz,map2);
		}
		//保存开启考勤(刷卡)记录
		this.kqService.saveBrushCardEmp(admin);
		return ajaxJsonSuccessMessage("您的操作已成功!");
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
			a.setXteam( a.getDepartment().getTeam().getTeamName());
			//工作状态
			if(a.getWorkstate()!=null&&!"".equals(a.getWorkstate()))
			{
				a.setXworkstate(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", a.getWorkstate()));
			}
			//技能
			if(a.getPost()!=null)
			{
				a.setXpost(a.getPost().getPostName());
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
	
	
	/**===========================end get/set===============================*/
}
