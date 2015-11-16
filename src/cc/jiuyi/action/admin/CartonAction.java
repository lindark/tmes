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
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
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
	private static final String UNDO = "3";

	private Carton carton;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;

	@Resource
	private CartonService cartonService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;

	public String list() {
		workingbill = workingBillService.get(workingBillId);
		return "list";
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
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "carton.cartonAmount", message = "纸箱数量不允许为空!") }

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (cartonService.getTotalCount() <= 0) {
			carton.setTotalAmount("0");
		}else{
			carton.setTotalAmount(cartonService.getAll().get(0).getTotalAmount());			
		}
		cartonService.save(carton);
		redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();
		return SUCCESS;
	}

	//查询数据库中是否有是否删除为'N'的
	public boolean getN(){
		List<Carton> cartons = cartonService.getAll();
		for (int i = 0; i < cartons.size(); i++) {
			//if(cartons.get(i).getIsDel())
		}
		return true;
	}
	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		Carton persistent = cartonService.load(id);
		BeanUtils.copyProperties(carton, persistent, new String[] { "id" });
		cartonService.update(persistent);
		redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();
		return SUCCESS;
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
	public String confirms() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			carton = cartonService.load(ids[i]);
			carton.setState(CONFIRMED);
			admin = adminService.getLoginAdmin();
			carton.setConfirmUser(admin.getId());
			addTotal(carton);
			cartonService.save(carton);
		}
		redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();
		return SUCCESS;
	}

	// 根据给定纸箱，将其他纸箱的累计数量全部加上该纸箱的收货数量
	public void addTotal(Carton carton) {
		List<Carton> cartons = cartonService.getAll();
		for (int i = 0; i < cartons.size(); i++) {
			cartons.get(i).setTotalAmount(
					Integer.parseInt(cartons.get(i).getTotalAmount())
							+ Integer.parseInt(carton.getCartonAmount()) + "");
			cartonService.save(cartons.get(i));
		}
	}
	
	// 根据给定纸箱，将其他纸箱的累计数量全部减去该纸箱的收货数量
	
	public void minusTotal(Carton carton){
		List<Carton> cartons = cartonService.getAll();
		for (int i = 0; i < cartons.size(); i++) {
			cartons.get(i).setTotalAmount(
					Integer.parseInt(cartons.get(i).getTotalAmount())
							- Integer.parseInt(carton.getCartonAmount()) + "");
			cartonService.save(cartons.get(i));
		}
	}

	// 刷卡撤销
	public String undo() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			carton = cartonService.load(ids[i]);
			carton.setState(UNDO);
			admin = adminService.getLoginAdmin();
			carton.setConfirmUser(admin.getId());
			minusTotal(carton);
			cartonService.save(carton);
		}
		redirectionUrl = "carton!list.action?workingBillId="
				+ carton.getWorkingbill().getId();
		return SUCCESS;
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
		pager = cartonService.getCartonPager(pager, map);
		List<Carton> cartonList = pager.getList();
		List<Carton> lst = new ArrayList<Carton>();
		for (int i = 0; i < cartonList.size(); i++) {
			Carton carton = (Carton) cartonList.get(i);
			carton.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "cartonState", carton.getState()));
			carton.setWorkingbill(null);
			lst.add(carton);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
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

}
