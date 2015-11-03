package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class PostAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Post post;
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
				System.out.println("obj=" + obj);
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
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
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
			post= postService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Post persistent = postService.load(id);
			BeanUtils.copyProperties(post, persistent, new String[] { "id","createDate", "modifyDate"});
			postService.update(persistent);
			redirectionUrl = "post!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "post.postCode", message = "岗位编号不允许为空!"),
					@RequiredStringValidator(fieldName = "post.postName", message = "岗位名称不允许为空!")
			  },
			requiredFields = {
					@RequiredFieldValidator(fieldName = "post.orderList", message = "排序不允许为空!")
						
			}, 
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "post.orderList", min = "0", message = "排序必须为零或正整数!")
				}
			  
	)
	public String save()throws Exception{
		postService.save(post);
		redirectionUrl="post!list.action";
		return SUCCESS;	
	}
		


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
	}


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
	
	
}
