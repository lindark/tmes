package cc.jiuyi.action.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
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
	
	/**========================对象，变量，接口 start============================*/
	
	/**
	 * 对象，变量
	 */
	private Kaoqin kaoqin;
	private Admin admin;
	private List<Dict>list_dict;//员工状态
	private List<Kaoqin>list_kq;
	/**
	 * service接口
	 */
	@Resource
	private KaoqinService kqService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	
	/**========================end 对象，变量，接口 =============================*/
	
	/**===========================方法start==================================*/
	
	/**
	 * 进入list页面
	 */
	public String list()
	{
		this.admin=this.adminService.getLoginAdmin();
		this.admin=this.adminService.get(admin.getId());
		this.list_dict=this.dictService.getState("adminworkstate");//list中员工的状态
		
		//读取员工到记录表中
		List<Admin>list_emp=this.adminService.getByTeamId(this.admin.getDepartment().getTeam().getId());//根据班组ID获得班组下的所有员工
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String strdate=sdf.format(date);
		this.kqService.saveByKqdate(list_emp,strdate);
		//查询当天历史员工
		List<Kaoqin>l_kq=this.kqService.getSamedayEmp(strdate);
		this.list_kq=new ArrayList<Kaoqin>();
		for(int i=0;i<l_kq.size();i++)
		{
			Kaoqin kq=l_kq.get(i);
			kq.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", kq.getWorkState()));
			this.list_kq.add(kq);
		}
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
		List<Admin>list2=new ArrayList<Admin>();
		for(int i=0;i<list1.size();i++)
		{
			Admin a=list1.get(i);
			//班次
			a.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", a.getShift()));
			//班组
			a.setXteam( a.getDepartment().getTeam().getTeamName());
			//工作状态
			a.setXworkstate(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", a.getWorkstate()));
			//技能
			a.setXpost(a.getPost().getPostName());
			list2.add(a);
		}
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
			k.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", k.getWorkState()));
			list2.add(k);
		}
		pager.setList(list2);
		JSONArray jsonArray=JSONArray.fromObject(pager);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * 修改员工工作状态
	 */
	public String updateWorkState()
	{
		this.kqService.updateWorkState(kaoqin);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**===========================end 方法===================================*/
	
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

	/**===========================end get/set===============================*/
}
