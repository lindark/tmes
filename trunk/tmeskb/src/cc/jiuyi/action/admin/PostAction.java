package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Post;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class PostAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Post xpost;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private PostService postService;
	@Resource
	private DictService dictService;
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
//		List<Post> postList = pager.getList();
//		for (Post post1 : postList) {
//			post1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"postState", post1.getState()));
//		}
		//dictService.getDictValueByDictKey("postState", post.getState());
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("postCode") != null) {
				//System.out.println("obj=" + obj);
				String postCode = obj.getString("postCode").toString();
				map.put("postCode", postCode);
			}
			if (obj.get("postName") != null) {
				String postName = obj.getString("postName").toString();
				map.put("postName", postName);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = postService.getPostPager(pager, map);
			List<Post> postList = pager.getList();
			List<Post> lst = new ArrayList<Post>();
			for (int i = 0; i < postList.size(); i++) {
				Post post = (Post) postList.get(i);
				post.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "postState", post.getState()));
				lst.add(post);
			}
		pager.setList(lst);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Post.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		postService.updateisdel(ids, "Y");
//		for (String id:ids){
//			Post post=postService.load(id);
//		}
		redirectionUrl = "post!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			xpost= postService.load(id);
			return INPUT;	
		}
		
	//更新
		public String update() {
			Post persistent = postService.get(xpost.getId());
			BeanUtils.copyProperties(xpost, persistent, new String[] { "id","createDate", "modifyDate"});
			persistent.setModifyDate(new Date());
			postService.update(persistent);
			redirectionUrl = "post!list.action";
			return SUCCESS;
		}
		
	//保存
	public String save(){
		postService.save(xpost);
		redirectionUrl="post!list.action";
		return SUCCESS;	
	}
	
	/**========================================*/
	/**
	 * 弹框:进入岗位页面
	 */
	public String beforegetpost()
	{
		
		return "alert";
	}
	
	/**
	 * 获取岗位数据
	 */
	public String getpost()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		if(pager == null)
		{
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && Param != null)
		{
			JSONObject obj = JSONObject.fromObject(Param);
			//岗位编码
			if (obj.get("stationcode") != null)
			{
				String stationcode = obj.getString("stationcode").toString();
				map.put("stationcode", stationcode);
			}
			//岗位名称
			if (obj.get("stationname") != null)
			{
				String stationname = obj.getString("stationname").toString();
				map.put("stationname", stationname);
			}
		}
		pager =this.postService.getAllPost(pager,map);//查询岗位数据
		@SuppressWarnings("unchecked")
		List<Post> pagerlist = pager.getList();
		List<Post>newlist=new ArrayList<Post>();
		for(int i=0;i<pagerlist.size();i++)
		{
			Post p=pagerlist.get(i);
			if(p.getState()!=null&&!"".equals(p.getState()))
			{
				p.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "postState", p.getState()));
			}
			newlist.add(p);
		}
		pager.setList(newlist);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Post.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	/**========================================*/

	public PostService getPostService() {
		return postService;
	}
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "StateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}
	public DictService getDictService() {
		return dictService;
	}
	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	public Post getXpost()
	{
		return xpost;
	}
	public void setXpost(Post xpost)
	{
		this.xpost = xpost;
	}
}
