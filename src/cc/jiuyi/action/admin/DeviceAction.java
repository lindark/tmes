package cc.jiuyi.action.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Device;
import cc.jiuyi.service.DeviceService;

/**
 * 后台Action类 - 设备
 */

@ParentPackage("admin")
public class DeviceAction extends BaseAdminAction {

	private static final long serialVersionUID = -5133263227242342963L;
	
	private Device device;
	
	@Resource
	private DeviceService deviceService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		device = deviceService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = deviceService.findByPager(pager);
		return LIST;
	}

	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();
		}
		pager = deviceService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
		
	// 删除
	public String delete() throws Exception {			
		deviceService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	
}
