package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Model;
import cc.jiuyi.service.ModelService;

/**
 * 后台Action类 - 工模维修单
 */

@ParentPackage("admin")
public class ModelAction extends BaseAdminAction {

	private static final long serialVersionUID = -5323263207248342963L;
	
	private Model model;
	
	@Resource
	private ModelService modelService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		model = modelService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = modelService.findByPager(pager);
		return LIST;
	}
	
	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();
		}
		pager = modelService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	public String save() {
		modelService.save(model);
		redirectionUrl = "model!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		modelService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
