package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.CraftService;

/**
 * 后台Action类 - 工艺维修单
 */

@ParentPackage("admin")
public class CraftAction extends BaseAdminAction {

	private static final long serialVersionUID = -2383463207248343967L;
	
	private Craft craft;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	
	@Resource
	private CraftService craftService;
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
		craft = craftService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = craftService.findByPager(pager);
		return LIST;
	}

	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();
		}
		pager = craftService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	public String save() {	
		abnormal=abnormalService.load(abnormalId);
		craft.setAbnormal(abnormal);
		craft.setState("已提交");
		craftService.save(craft);	
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}
		
	// 删除
	public String delete() throws Exception {
		ids=id.split(",");
		craftService.delete(ids);
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}

	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
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
