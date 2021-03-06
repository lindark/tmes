package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.QualityService;

/**
 * 后台Action类 - 质量问题单
 */

@ParentPackage("admin")
public class MassAction extends BaseAdminAction {

	private static final long serialVersionUID = -3243463207248132962L;
	
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
		pager = qualityService.findByPager(pager);
		return LIST;
	}
	
	public String save() {
		qualityService.save(quality);
		redirectionUrl = "mass!list.action";
		return SUCCESS;
	}
	
	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();

		}
		pager = qualityService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}

	// 删除
	public String delete() throws Exception {
		ids=id.split(",");
		qualityService.delete(ids);
		redirectionUrl = "mass!list.action";
		return SUCCESS;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	
	
}
