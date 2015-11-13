package cc.jiuyi.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ModelService;

/**
 * 后台Action类 - 工模维修单
 */

@ParentPackage("admin")
public class ModelAction extends BaseAdminAction {

	private static final long serialVersionUID = -5323263207248342963L;
	
	private Model model;
	private String loginUsername;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	
	@Resource
	private ModelService modelService;
	@Resource
	private AdminService adminService;
	@Resource
	private AbnormalService abnormalService;
	
	// 添加
	public String add() {
		if(aid!=null){
			abnormal=abnormalService.load(aid);
		}	
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
	
	@InputConfig(resultName = "error")
	public String update() {
		Model persistent = modelService.load(id);
		BeanUtils.copyProperties(model, persistent, new String[] { "id" });
		modelService.update(persistent);
		redirectionUrl = "model!list.action";
		return SUCCESS;
	}
	
	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();
		}
		pager = modelService.findByPager(pager);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Model model  = (Model)pagerlist.get(i);
			model.setAbnormal(null);
			pagerlist.set(i, model);
		}
		pager.setList(pagerlist);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	public String save() {
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin = adminService.get("username", loginUsername);
		model.setCreateUser(admin.getId());
		model.setModifyUser(admin.getId());
		abnormal=abnormalService.load(abnormalId);
		model.setAbnormal(abnormal);
		model.setState("已提交");
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

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public String getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(String abnormalId) {
		this.abnormalId = abnormalId;
	}
	
	
}
