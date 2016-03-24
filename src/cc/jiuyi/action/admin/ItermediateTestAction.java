package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.IpRecord;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类-半成品巡检主表
 */

@ParentPackage("admin")
public class ItermediateTestAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045295823911487260L;

	private static final String CONFIRMED = "2";
	private static final String REPEAL = "3";

	private ItermediateTest itermediateTest;
	private Admin admin;// 人员
	private String workingBillId;// 随工单ID
	private WorkingBill workingbill;// 随工单
	private String add;// 新增时
	private String edit;// 编辑时
	private String show;// 查看时
	private Products product;// 产品
	private String my_id;//
	private List<Dict> allState;// 获取所有状态
	private List<Bom> list_material;// 产品Bom
	private List<Cause> list_cause;// 缺陷
	private List<ItermediateTestDetail> list_itmesg;// 巡检从表不合格信息
	private List<IpRecord> list_itbug;// 不合格原因
	private String cardnumber;// 卡号

	@Resource
	private ItermediateTestService itermediateTestService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private ItermediateTestDetailService itermediateTestDetailService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProductsService productsService;
	@Resource
	private CauseService causeService;
	@Resource
	private BomService bomservice;

	private String xcreateUser;//创建人员
	private String xconfirmUser;//确认人员
	private String materialCode;//组件编码
	private String materialName;//组件名
	private String start;//日期起始点
	private String end;//日期结束点
	private String state;//状态
	
	// 添加 ----modify weitao
	public String add() {
		this.workingbill = this.workingBillService.load(workingBillId);

		String aufnr = workingbill.getWorkingBillCode().substring(0,
				workingbill.getWorkingBillCode().length() - 2);
		// Date productDate =
		// ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		list_material = bomservice.findBom(aufnr, workingbill.getProductDate(),
				workingbill.getWorkingBillCode());
		this.list_cause = this.causeService.getBySample("3");// 半成品不合格内容
		this.add = "add";
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill = this.workingBillService.get(workingBillId);
		return LIST;
	}

	// 历史巡检记录
	public String history() {
		return "history";
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
		pager = itermediateTestService.getItermediateTestPager(workingBillId,
				pager, map);
		List<ItermediateTest> itermediateTestList = pager.getList();
		List<ItermediateTest> lst = new ArrayList<ItermediateTest>();
		for (int i = 0; i < itermediateTestList.size(); i++) {
			ItermediateTest itermediateTest = (ItermediateTest) itermediateTestList
					.get(i);
			itermediateTest.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "itermediateTestState",
					itermediateTest.getState()));
			if (itermediateTest.getConfirmUser() != null) {
				itermediateTest.setXconfirmUser(itermediateTest
						.getConfirmUser().getName());
			}
			if (itermediateTest.getCreateUser() != null) {
				itermediateTest.setXcreateUser(itermediateTest.getCreateUser()
						.getName());
			}
			lst.add(itermediateTest);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Rework.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		itermediateTestService.updateisdel(ids, "Y");
		redirectionUrl = "itermediateTest!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		this.list_material = new ArrayList<Bom>();
		this.workingbill = this.workingBillService.load(workingBillId);
		String aufnr = workingbill.getWorkingBillCode().substring(0,
				workingbill.getWorkingBillCode().length() - 2);
		// Date productDate =
		// ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		List<Bom> l_material = bomservice.findBom(aufnr,
				workingbill.getProductDate(), workingbill.getWorkingBillCode());
		for (int i = 0; i < l_material.size(); i++) {
			Bom bom = l_material.get(i);
			ItermediateTestDetail it = this.itermediateTestDetailService
					.getBySidAndMid(id, bom.getMaterialCode());
			if (it != null) {
				bom.setXtestAmount(it.getTestAmount());
				bom.setXfailReason(it.getFailReason());
				bom.setXfailAmount(it.getFailAmount());
				bom.setXgoodsSzie1(it.getGoodsSzie1());
				bom.setXgoodsSzie2(it.getGoodsSzie2());
				bom.setXgoodsSzie3(it.getGoodsSzie3());
				bom.setXgoodsSzie4(it.getGoodsSzie4());
				bom.setXgoodsSzie5(it.getGoodsSzie5());
				bom.setXitid(it.getId());
				List<IpRecord> l_ir = new ArrayList<IpRecord>(it.getIpRecord());// 获取每个物料对应的不合格原因
				String sbids = "", sbnums = "";
				for (int j = 0; j < l_ir.size(); j++) {
					IpRecord ip = l_ir.get(j);
					if (ip != null) {
						sbids = sbids + ip.getCauseId() + ",";
						sbnums = sbnums + ip.getRecordNum() + ",";
					}
				}
				bom.setXrecordid(sbids);
				bom.setXrecordNum(sbnums);
			}
			list_material.add(bom);
		}
		this.list_cause = this.causeService.getBySample("3");
		this.itermediateTest = this.itermediateTestService.load(id);
		this.edit = "edit";
		return INPUT;
	}

	// 更新
	// @InputConfig(resultName = "error")
	public String creditupdate() {
		this.itermediateTestService.updateAll(itermediateTest, list_itmesg,
				list_itbug, my_id, cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡保存
	@Validations(

	)
	// @InputConfig(resultName = "error")
	public String creditsave() throws Exception {
		this.itermediateTestService.saveSubmit(itermediateTest, list_itmesg,
				list_itbug, my_id, cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		admin = adminService.getByCardnum(cardnumber);
		ids = id.split(",");
		List<ItermediateTest> list = itermediateTestService.get(ids);
		for (int i = 0; i < ids.length; i++) {
			ItermediateTest it = list.get(i);
			if ("2".equals(it.getState())) {
				return ajaxJsonErrorMessage("已经确认的无法再确认!");
			}
			if ("3".equals(it.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再确认!");
			}
			List<ItermediateTestDetail> list1 = new ArrayList<ItermediateTestDetail>(
					it.getItermediateTestDetail());
			if (list1.size() == 0) {
				return ajaxJsonErrorMessage("半成品巡检表为空,不能确认!");
			}
		}
		itermediateTestService.updateState(list, "2", cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 巡查记录列表 @author Reece 2016/3/22
	public String historylist() {
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
			if (obj.get("materialName") != null) {
				String materialName = obj.getString("materialName").toString();
				map.put("materialName", materialName);
			}
			if (obj.get("materialCode") != null) {
				String materialCode = obj.getString("materialCode").toString();
				map.put("materialCode", materialCode);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
			if (obj.get("xconfirmUser") != null) {
				String xconfirmUser = obj.getString("xconfirmUser").toString();
				map.put("xconfirmUser", xconfirmUser);
			}
			if (obj.get("xcreateUser") != null) {
				String xcreateUser = obj.getString("xcreateUser").toString();
				map.put("xcreateUser", xcreateUser);
			}
			
		}
		pager = itermediateTestDetailService.historyjqGrid(pager, map);
	
		List<ItermediateTestDetail> detailList = pager.getList();
		List<ItermediateTestDetail> lst = new ArrayList<ItermediateTestDetail>();
		try {
			for (int i = 0; i < detailList.size(); i++) {
				ItermediateTestDetail itermediateTestDetail = detailList.get(i);
//				确认人姓名
				if (itermediateTestDetail.getItermediateTest().getConfirmUser() != null) {
					itermediateTestDetail
							.setXconfirmUser(itermediateTestDetail.getItermediateTest().getConfirmUser().getName());
				}
//				创建人姓名
				if (itermediateTestDetail.getItermediateTest().getCreateUser() != null) {
					itermediateTestDetail.setXcreateUser(itermediateTestDetail.getItermediateTest().getCreateUser().getName());
				}
//				 状态描述
				itermediateTestDetail.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "itermediateTestState", itermediateTestDetail.getItermediateTest().getState()));

				lst.add(itermediateTestDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ItermediateTest.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	//excel导出
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("materialName", materialName);
		map.put("materialCode", materialCode);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		map.put("xcreateUser", xcreateUser);
		map.put("xconfirmUser", xconfirmUser);
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("组件编码");
		header.add("组件名称");
		header.add("抽检数量");
		header.add("尺寸1");
		header.add("尺寸2");
		header.add("尺寸3");
		header.add("尺寸4");
		header.add("尺寸5");
		header.add("不合格数量");
		header.add("不合格原因");
		header.add("创建人");
		header.add("确认人");
		header.add("创建时间");
		header.add("修改时间");
		header.add("状态");

		List<Object[]> workList = itermediateTestDetailService.historyExcelExport(map);
		for (int i = 0; i < workList.size(); i++) {
			Object[] obj = workList.get(i);
			ItermediateTestDetail itermediateTestDetail = (ItermediateTestDetail) obj[0];//itermediateTestDetail
			ItermediateTest itermediateTest =	(ItermediateTest) obj[1];	
//        	Admin confirmUser=(Admin) obj[2];
//        	Admin createUser=(Admin) obj[3];
        	
			Object[] bodyval = {
					itermediateTestDetail.getMaterialCode(),
					itermediateTestDetail.getMaterialName(),
					itermediateTestDetail.getTestAmount(),
					itermediateTestDetail.getGoodsSzie1(),
					itermediateTestDetail.getGoodsSzie2(),
					itermediateTestDetail.getGoodsSzie3(),
					itermediateTestDetail.getGoodsSzie4(),
					itermediateTestDetail.getGoodsSzie5(),
					itermediateTestDetail.getFailReason(),
					itermediateTestDetail.getFailAmount(),
					itermediateTest.getConfirmUser().getName()==null?0:itermediateTest.getConfirmUser().getName(),
					itermediateTest.getCreateUser().getName()==null?0:itermediateTest.getCreateUser().getName(),	
					itermediateTestDetail.getCreateDate(),
					itermediateTestDetail.getModifyDate(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"itermediateTestState", itermediateTest.getState())
							};
			body.add(bodyval);
		}

		try {
			String fileName = "半成品巡检记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("半成品巡检记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// 刷卡撤销
	public String creditundo() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		List<ItermediateTest> list = itermediateTestService.get(ids);
		for (int i = 0; i < ids.length; i++) {
			ItermediateTest it = list.get(i);
			if ("3".equals(it.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
		}
		itermediateTestService.updateState(list, "3", cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	public String show() {
		this.workingbill = this.workingBillService.get(workingBillId);
		this.list_itmesg = new ArrayList<ItermediateTestDetail>();
		this.itermediateTest = this.itermediateTestService.load(id);
		List<ItermediateTestDetail> list_itmesg = new ArrayList<ItermediateTestDetail>(
				this.itermediateTest.getItermediateTestDetail());
		if (list_itmesg.size() > 0) {
			for (int i = 0; i < list_itmesg.size(); i++) {
				ItermediateTestDetail it = list_itmesg.get(i);
				this.list_itmesg.add(it);
			}
		}
		this.show = "show";
		return INPUT;
	}

	public String getXcreateUser() {
		return xcreateUser;
	}

	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}

	public String getXconfirmUser() {
		return xconfirmUser;
	}

	public void setXconfirmUser(String xconfirmUser) {
		this.xconfirmUser = xconfirmUser;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
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

	public ItermediateTest getItermediateTest() {
		return itermediateTest;
	}

	public void setItermediateTest(ItermediateTest itermediateTest) {
		this.itermediateTest = itermediateTest;
	}

	public ItermediateTestService getItermediateTestService() {
		return itermediateTestService;
	}

	public void setItermediateTestService(
			ItermediateTestService itermediateTestService) {
		this.itermediateTestService = itermediateTestService;
	}

	// 获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "StateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public List<Cause> getList_cause() {
		return list_cause;
	}

	public void setList_cause(List<Cause> list_cause) {
		this.list_cause = list_cause;
	}

	public List<ItermediateTestDetail> getList_itmesg() {
		return list_itmesg;
	}

	public void setList_itmesg(List<ItermediateTestDetail> list_itmesg) {
		this.list_itmesg = list_itmesg;
	}

	public List<IpRecord> getList_itbug() {
		return list_itbug;
	}

	public void setList_itbug(List<IpRecord> list_itbug) {
		this.list_itbug = list_itbug;
	}

	public List<Bom> getList_material() {
		return list_material;
	}

	public void setList_material(List<Bom> list_material) {
		this.list_material = list_material;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getMy_id() {
		return my_id;
	}

	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

}
