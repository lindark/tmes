package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.QualityService;
import cc.jiuyi.util.ThinkWayUtil;

@ParentPackage("admin")
public class QualityAction extends BaseAdminAction {

	private static final long serialVersionUID = -3242463207248131962L;

	private Quality quality;
	
	@Resource
	private QualityService qualityService;
	
	// 添加
		public String add() {
			return INPUT;
		}

		// 编辑
		public String edit() {
			quality = qualityService.load(id);
			return INPUT;
		}

		// 列表
		public String list() {
			//pager = qualityService.findByPager(pager);
			return LIST;
		}

		/* ajax列表
		public String ajlist() {
			if(pager == null) {
				pager = new Pager();
			}
			pager = qualityService.findByPager(pager);
			JSONArray jsonArray = JSONArray.fromObject(pager);
			return ajaxJson(jsonArray.get(0).toString());
		}*/
		
		
		/**
		 * ajax 列表
		 * @return
		 */
		public String ajlist(){
			HashMap<String,String> map = new HashMap<String,String>();
			if(pager == null) {
				pager = new Pager();
				pager.setOrderType(OrderType.asc);
				pager.setOrderBy("orderList");
			}
			if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
				if(!filters.equals("")){
					JSONObject filt = JSONObject.fromObject(filters);
					Pager pager1 = new Pager();
					Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
					m.put("rules", jqGridSearchDetailTo.class);
					pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
					pager.setRules(pager1.getRules());
					pager.setGroupOp(pager1.getGroupOp());
				}
			}
			if(pager.is_search()==true && Param != null){//普通搜索功能
				if(!Param.equals("")){
				//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
					JSONObject param = JSONObject.fromObject(Param);
					String start = ThinkWayUtil.null2String(param.get("start"));
					String end = ThinkWayUtil.null2String(param.get("end"));
					String state = ThinkWayUtil.null2String(param.get("state"));//状态
					map.put("start", start);
					map.put("end", end);
					map.put("state", state);
				}
			}
			
			pager = qualityService.getQualityPager(pager, map);
			JSONArray jsonArray = JSONArray.fromObject(pager);
			System.out.println(jsonArray.get(0).toString());
			 return ajaxJson(jsonArray.get(0).toString());
			
		}
		
		public String save() {		
			qualityService.save(quality);	
			redirectionUrl = "quality!list.action";
			return SUCCESS;
		}
		
		@InputConfig(resultName = "error")
		public String update() {
			Quality persistent = qualityService.load(id);
			BeanUtils.copyProperties(quality, persistent, new String[] { "id" });
			qualityService.update(persistent);
			redirectionUrl = "quality!list.action";
			return SUCCESS;
		}
			
		// 删除
		public String delete() throws Exception {
			ids=id.split(",");
			qualityService.delete(ids);
			redirectionUrl = "quality!list.action";
			return SUCCESS;
		}

		public Quality getQuality() {
			return quality;
		}

		public void setQuality(Quality quality) {
			this.quality = quality;
		}
		
		
}
