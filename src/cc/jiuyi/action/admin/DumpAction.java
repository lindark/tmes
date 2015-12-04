package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 转储管理
 * 
 */
@ParentPackage("admin")
public class DumpAction extends BaseAdminAction {

	private static final long serialVersionUID = -5672674230144520389L;

	private static final String UNCONFIRMED = "2";//未确认

	private Dump dump;
	private Admin admin;
	private String warehouse;
	private String warehouseName;
	private String dumpId;
	private List<Dump> dumpList;

	@Resource
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpService dumpService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;

	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.load(admin.getId());
		warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();
		warehouseName = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouseName();
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
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	/**
	 * 刷卡确认
	 * 
	 * @return
	 */
	public String confirm() {
		try {
			String[] ids = dumpId.split(",");
			dumpList = dumpRfc.findMaterialDocument("1805", "20150901","20151001");
			for (int i = 0; i < ids.length; i++) {
				if(dumpService.isExist("voucherId", ids[i])){
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
			}
			dumpService.saveDump(ids, dumpList);
			return ajaxJsonSuccessMessage("您的操作已成功!");
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO操作失败");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
		
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
		dumpService.updateisdel(ids, "Y");
		redirectionUrl = "dump!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			admin = adminService.getLoginAdmin();
			admin = adminService.load(admin.getId());
			warehouse = admin.getDepartment().getTeam().getFactoryUnit()
					.getWarehouse();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String today = sdf.format(date);
			dumpList = dumpRfc.findMaterialDocument("1805", "20150901",
					"20151001");
			List<Dump> dpList = dumpService.getAll();
			if(dpList.size()!=0){
				for (int i = 0; i < dumpList.size(); i++) {
					for (int j = 0; j < dpList.size(); j++) {
						if(dumpList.get(i).getVoucherId().equals(dpList.get(j).getVoucherId())){
							dumpList.get(i).setConfirmUser(dpList.get(j).getConfirmUser());
							dumpList.get(i).setState(dpList.get(j).getState());
							break;
						}
					}
				}
			}
			for (int i = 0; i < dumpList.size(); i++) {
				Dump dump = (Dump)dumpList.get(i);
				if(dump.getState()==null){
					dump.setState(UNCONFIRMED);					
				}
				dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "dumpState", dump.getState()));
				if(dump.getConfirmUser()!=null){
					dump.setAdminName(dump.getConfirmUser().getName());
				}
			}
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));//排除有关联关系的属性字段 
			JSONArray jsonArray = JSONArray.fromObject(dumpList,jsonConfig);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("list", jsonArray);
			return ajaxJson(jsonobject.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CustomerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Dump getDump() {
		return dump;
	}

	public void setDump(Dump dump) {
		this.dump = dump;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public DumpRfcImpl getDumpRfc() {
		return dumpRfc;
	}

	public void setDumpRfc(DumpRfcImpl dumpRfc) {
		this.dumpRfc = dumpRfc;
	}

	public String getDumpId() {
		return dumpId;
	}

	public void setDumpId(String dumpId) {
		this.dumpId = dumpId;
	}

	public List<Dump> getDumpList() {
		return dumpList;
	}

	public void setDumpList(List<Dump> dumpList) {
		this.dumpList = dumpList;
	}

}
