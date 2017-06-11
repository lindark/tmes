package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Station;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.service.StationService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 工位
 * 
 */
@ParentPackage("admin")
public class StationAction extends BaseAdminAction
{

	private static final long serialVersionUID = 7580712819995604831L;

	/**========================variable,object,interface  start========================*/
	/**
	 * 对象，变量
	 */
	private Station station;
	private List<Station>list_station;
	private List<Post>list_post;
	private String stationcode;//工位编码
	
	/**
	 * service接口
	 */
	@Resource
	private StationService stationService;
	@Resource
	private DictService dictService;
	@Resource
	private PostService postService;
	
	/**========================end  variable,object,interface==========================*/
	
	/**========================method  start======================================*/
	
	/**
	 * 进入list页面
	 */
	public String list()
	{
		return "list";
	}
	
	/**
	 * jqgrid查询
	 */
	public String ajlist()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("createDate");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		//普通查询
		if (pager.is_search() == true && Param != null)
		{
			JSONObject obj = JSONObject.fromObject(Param);
			//工位编码
			if(obj.get("stationcode")!=null)
			{
				String stationcode=obj.get("stationcode").toString();
				map.put("stationcode", stationcode);
			}
			//工位名称
			if (obj.get("stationname") != null)
			{
				String stationname = obj.getString("stationname").toString();
				map.put("stationname", stationname);
			}
			//岗位名称
			if (obj.get("postname") != null)
			{
				String postname = obj.getString("postname").toString();
				map.put("postname", postname);
			}
			//是否启用
			if (obj.get("isWork") != null&&!"".equals(obj.get("isWork")))
			{
				String isWork = obj.getString("isWork").toString();
				map.put("isWork", isWork);
			}
		}
		pager =this.stationService.getByPager(pager, map);
		@SuppressWarnings("unchecked")
		List<Station> oldlist = pager.getList();
		List<Station> newlist = new ArrayList<Station>();
		for (int i = 0; i < oldlist.size(); i++)
		{
			Station s=oldlist.get(i);
			//岗位名称
			if(s.getPosts()!=null)
			{
				s.setXposts(s.getPosts().getPostName());
			}
			//是否启用
			if(s.getIsWork()!=null)
			{
				s.setXisWork(ThinkWayUtil.getDictValueByDictKey(dictService, "stationIsWork", s.getIsWork()));
			}
			newlist.add(s);
		}
		pager.setList(newlist);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Station.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 新增前
	 */
	public String add()
	{
		this.list_post=this.postService.getAllPost();//查询所有未删除的岗位
		return "input";
	}
	
	/**
	 * 新增保存
	 */
	public String save()
	{
		//保存前再判断一次编码是否已存在
		if(checkcode(station.getCode(),station.getId()))
		{
			this.stationService.saveInfo(station);
			this.redirectionUrl="station!list.action";
			return SUCCESS;
		}
		addActionError("工位编码已存在!");
		return ERROR;
	}
	
	/**
	 * 修改前
	 */
	public String edit()
	{
		this.station=this.stationService.get(id);
		this.list_post=this.postService.getAllPost();//查询所有未删除的岗位
		return "input";
	}
	
	/**
	 * 修改保存
	 */
	public String update()
	{
		if(checkcode(station.getCode(),station.getId()))
		{
			this.stationService.updateInfo(station);
			this.redirectionUrl="station!list.action";
			return SUCCESS;
		}
		addActionError("工位编码已存在!");
		return ERROR;
	}
	
	/**
	 * 删除
	 */
	public String delete()
	{
		this.stationService.updateToDel(id);
		this.redirectionUrl="station!list.action";
		return SUCCESS;
	}
	
	/**
	 * 工位编码是否已存在
	 */
	public String ckcode()
	{
		if(checkcode(stationcode,id))
		{
			return this.ajaxJsonSuccessMessage("success");
		}
		return this.ajaxJsonErrorMessage("error");
	}
	
	public boolean checkcode(String xcode,String xid)
	{
		Station s=this.stationService.getByCode(xcode);//根据编码查询
		if(s!=null)
		{
			if(xid!=null&&xid.equals(s.getId()))
			{
				return true;
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**========================end  method======================================*/
	
	/**===========================get/set start=============================*/
	
	public Station getStation()
	{
		return station;
	}
	public void setStation(Station station)
	{
		this.station = station;
	}
	public List<Station> getList_station()
	{
		return list_station;
	}
	public void setList_station(List<Station> list_station)
	{
		this.list_station = list_station;
	}
	public List<Post> getList_post()
	{
		return list_post;
	}
	public void setList_post(List<Post> list_post)
	{
		this.list_post = list_post;
	}
	public String getStationcode()
	{
		return stationcode;
	}
	public void setStationcode(String stationcode)
	{
		this.stationcode = stationcode;
	}
	
	/**===========================end get/set===============================*/
}
