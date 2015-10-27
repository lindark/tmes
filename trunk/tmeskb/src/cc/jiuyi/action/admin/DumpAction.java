package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.DumpService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 转储管理
 * 
 */
@ParentPackage("admin")
public class DumpAction extends BaseAdminAction {

	private static final long serialVersionUID = -5672674230144520389L;

	private Dump dump;

	@Resource
	private DumpService dumpService;

	public String list() {
		//pager = dumpService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		dump = dumpService.load(id);
		return INPUT;
	}

	public String save() {
		dumpService.save(dump);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Dump persistent = dumpService.load(id);
		BeanUtils.copyProperties(dump, persistent, new String[] { "id" });
		dumpService.update(persistent);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		dumpService.delete(ids);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		pager = dumpService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}

	public Dump getDump() {
		return dump;
	}

	public void setDump(Dump dump) {
		this.dump = dump;
	}

}
