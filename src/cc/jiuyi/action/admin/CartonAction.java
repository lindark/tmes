package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.type.IntegerType;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 纸箱
 * 
 */
@ParentPackage("admin")
public class CartonAction extends BaseAdminAction {

	private static final long serialVersionUID = 5682952528834965226L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private Carton carton;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private String matnr;

	@Resource
	private CartonService cartonService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private MaterialService materialService;

	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	// 历史返修记录
	public String history() {
		return "history";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}

	// 编辑
	public String edit() {
		workingbill = workingBillService.get(workingBillId);
		carton = cartonService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "carton.cartonAmount", min = "0", message = "纸箱数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String creditsave() throws Exception {
		admin = adminService.loadLoginAdmin();
		carton.setCreateUser(admin);
		carton.setWorkingbillCode(workingBillService.get(carton.getWorkingbill().getId()).getWorkingBillCode());
		cartonService.save(carton);
		/*redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "carton.cartonAmount", min = "0", message = "纸箱数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String creditupdate() throws Exception {
		Carton persistent = cartonService.load(id);
		BeanUtils.copyProperties(carton, persistent, new String[] { "id" });
		cartonService.update(persistent);
		/*redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		cartonService.updateisdel(ids, "Y");
		redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 刷卡确认
	public String creditapproval() {

		try {
			ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				carton = cartonService.load(ids[i]);
				if (CONFIRMED.equals(carton.getState())) {
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
				if (UNDO.equals(carton.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再确认！");
				}
			}
			List<Carton> list = cartonService.get(ids);
			cartonService.updateState(list, workingBillId);
			workingbill = workingBillService.get(workingBillId);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, SUCCESS);
			hashmap.put(MESSAGE, "您的操作已成功");
			hashmap.put("totalAmount", workingbill.getCartonTotalAmount()
					.toString());
			return ajaxJson(hashmap);
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO操作失败");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}

	}

	// 刷卡撤销
	public String creditundo() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			carton = cartonService.load(ids[i]);
			if (UNDO.equals(carton.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Carton> list = cartonService.get(ids);
		cartonService.updateState2(list, workingBillId);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getCartonTotalAmount()
				.toString());
		return ajaxJson(hashmap);
	}
	
	public String historylist(){
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("workingbillCode") != null) {
				System.out.println("obj=" + obj);
				String workingbillCode = obj.getString("workingbillCode").toString();
				map.put("workingbillCode", workingbillCode);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		pager = cartonService.historyjqGrid(pager, map);
		List<Carton> cartonList = pager.getList();
		List<Carton> lst = new ArrayList<Carton>();
		for (int i = 0; i < cartonList.size(); i++) {
			Carton carton = (Carton) cartonList.get(i);
			carton.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "cartonState", carton.getState()));
			/*
			 * carton.setCartonCode(material.getMaterialCode());
			 * carton.setCartonDescribe(material.getMaterialName());
			 */
			if (carton.getConfirmUser() != null) {
				carton.setAdminName(carton.getConfirmUser().getName());
			}
			carton.setMaktx(workingBillService.get(carton.getWorkingbill().getId()).getMaktx());
			carton.setCreateName(carton.getCreateUser().getName());
			lst.add(carton);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Carton.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		pager = cartonService.getCartonPager(pager, map, workingBillId);
		List<Carton> cartonList = pager.getList();
		List<Carton> lst = new ArrayList<Carton>();
		for (int i = 0; i < cartonList.size(); i++) {
			Carton carton = (Carton) cartonList.get(i);
			carton.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "cartonState", carton.getState()));
			/*
			 * carton.setCartonCode(material.getMaterialCode());
			 * carton.setCartonDescribe(material.getMaterialName());
			 */
			if (carton.getConfirmUser() != null) {
				carton.setAdminName(carton.getConfirmUser().getName());
			}
			carton.setCreateName(carton.getCreateUser().getName());
			lst.add(carton);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Carton.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	public Carton getCarton() {
		return carton;
	}

	public void setCarton(Carton carton) {
		this.carton = carton;
	}

	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
