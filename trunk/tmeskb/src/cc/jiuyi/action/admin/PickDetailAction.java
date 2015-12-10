package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类-领/退料从表
 */

@ParentPackage("admin")
public class PickDetailAction extends BaseAdminAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8353535527507793596L;

	private PickDetail pickDetail;
	// 获取所有状态
	private List<Dict> allState;

	private Pick pick;

	@Resource
	private PickDetailService pickDetailService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private MaterialService materialService;
	@Resource
	private ProductsService productsServce;
	@Resource
	private PickService pickService;
	@Resource
	private PickRfcImpl pickRfcImple;

	private String productsId;
	private WorkingBill workingbill;
	private String workingBillId;

	private Admin admin;
	private Material material;
	private String matnr;
	private String[] rbg;
	private String[] pt;
	private String text;
	private String my_id;// 自定义ID
	private List<Material> materialList;
	private List<PickDetail> pickDetailList;
	private List<PickDetail> pkList;
	private List<Dict> allType;
	private String pickRfc;

	public String addAmount() {
		// for (int i = 0; i < pt.length; i++) {
		// //System.out.println(Arrays.toString(text));
		// if (!pt.equals("")) {
		// System.out.println(Arrays.toString(pt));
		// for (int j = 0; j < rbg.length; j++) {
		// if (rbg.equals("1")) {
		// pickDetail.setPickType("1");
		// } else {
		// pickDetail.setPickType("2");
		// }
		//
		// }
		// }
		// }

		pickDetail.setMaterialCode(material.getMaterialCode());
		pickDetail.setMaterialName(material.getMaterialName());
		pickDetailService.save(pickDetail);
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 列表
	public String list() {
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		Products products = productsServce.getProducts(matnr);
		materialList = new ArrayList<Material>(products.getMaterial());
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	// 查看页面
	public String view() {
		workingbill = workingBillService.get(workingBillId);
		pick = pickService.load(id);
		pkList = pickDetailService.getPickDetail(id);
		for (int i = 0; i < pkList.size(); i++) {
			PickDetail pickDetail = pkList.get(i);
			pickDetail.setXpickType(ThinkWayUtil.getDictValueByDictKey(
					dictService, "pickType", pickDetail.getPickType()));	
		}
		pkList.add(pickDetail);
		Products products = productsServce.getProducts(matnr);
		materialList = new ArrayList<Material>(products.getMaterial());
		return VIEW;
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

		List<Material> pickDetail = materialService.getMantrBom(matnr);
		List<HashMap> list = new ArrayList<HashMap>();
		for (int i = 0; i < pickDetail.size(); i++) {
			Material mate = pickDetail.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", mate.getId());
			map.put("materialCode", mate.getMaterialCode());
			map.put("materialName", mate.getMaterialName());
			list.add(map);
		}
		HashMap map1 = new HashMap();
		map1.put("list", list);
		JSONArray jsonArray = JSONArray.fromObject(map1);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		pickDetailService.updateisdel(ids, "Y");
		redirectionUrl = "pick_detail!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		pickDetail = pickDetailService.load(id);
		return INPUT;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		PickDetail persistent = pickDetailService.load(id);
		BeanUtils.copyProperties(pickDetail, persistent, new String[] { "id",
				"createDate", "modifyDate" });
		pickDetailService.update(persistent);
		redirectionUrl = "pick_detail!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(requiredStrings = {
	// @RequiredStringValidator(fieldName = "pickDetail.pickType", message ="领料类型不能为空!"),

	}, intRangeFields = { @IntRangeFieldValidator(fieldName = "pickDetail.pickAmount", min = "0", message = "领料数量不能为空!")

	}

	)
	@InputConfig(resultName = "error")
	public String creditsubmit() throws Exception {
		WorkingBill workingBill = workingBillService.get(workingBillId);
		Admin admin = adminService.getLoginAdmin();
		Pick pick = new Pick();
		pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
		pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
		pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
		pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
		pick.setMove_type("261");// 移动类型 SAP测试数据
		// pick.setBudat(admin.getProductDate());//随工单日期
		// pick.setLgort(admin.getDepartment().getTeam().getFactoryUnit().getFactoryUnitCode());//库存地点
		// SAP测试数据 单元库存地点
		// pick.setZtext(str.substring(str.length()-2,2));//抬头文本 SAP测试数据
		// 随工单位最后两位
		// pick.setWerks(admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂
		// SAP测试数据 工厂编码
		pick.setCreateDate(new Date());
		pick.setCreateUser(admin);
		pick.setWorkingbill(workingBill);
		pick.setState("1");
		boolean flag = false;
		for (int i = 0; i < pickDetailList.size(); i++) {
			PickDetail p = pickDetailList.get(i);
			if (!"".equals(p.getPickType()) && !"".equals(p.getPickAmount())) {
				flag = true;
				String s = "";
				String str = workingBill.getId();
				p.setConfirmUser(admin);
				p.setMaterialCode("10490284");
				p.setCharg("15091901");
				p.setItem_text("文本");
				p.setOrderid("100116549");
				// p.setCharg("15091901");//批号
				// p.setItem_text(str.substring(str.length()-2,2));//项目文本(随工单位最后两位)
				// p.setOrderid(str.substring(str.length()-2));//工单号(随工单位除了最后两位)
				pickDetailList.set(i, p);
			}
		}	
		if(flag == false){
			addActionError("请检查是否正确输入内容!");
			return ERROR;
		}
		pickDetailService.save(pickDetailList, pick);
		redirectionUrl="pick!list.action?workingBillId="+workingBillId;
		return SUCCESS;
	}

	
	//刷卡确认
	public String creditapproval() throws Exception {
		WorkingBill workingBill = workingBillService.get(workingBillId);
		Admin admin = adminService.getLoginAdmin();
		Pick pick = new Pick();
		pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
		pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
		pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
		pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
		pick.setMove_type("261");// 移动类型 SAP测试数据
		// pick.setBudat(admin.getProductDate());//随工单日期
		// pick.setLgort(admin.getDepartment().getTeam().getFactoryUnit().getFactoryUnitCode());//库存地点
		// SAP测试数据 单元库存地点
		// pick.setZtext(str.substring(str.length()-2,2));//抬头文本 SAP测试数据
		// 随工单位最后两位
		// pick.setWerks(admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂
		// SAP测试数据 工厂编码
		//pick.setMove_type(pickDetail.getPickType());
		//移动类型 SAP测试数据
		pick.setCreateDate(new Date());
		pick.setCreateUser(admin);
		pick.setWorkingbill(workingBill);
		pick.setConfirmUser(admin);
		pick.setState("2");
		boolean flag = false;
		for (int i = 0; i < pickDetailList.size(); i++) {
			PickDetail p = pickDetailList.get(i);
			if (!"".equals(p.getPickType()) && !"".equals(p.getPickAmount())) {
				flag = true;
				String s = "";
				String str = workingBill.getId();
				p.setConfirmUser(admin);
				p.setMaterialCode("10490284");
				p.setCharg("15091901");
				p.setItem_text("文本");
				p.setOrderid("100116549");
				// p.setCharg("15091901");//批号
				// p.setItem_text(str.substring(str.length()-2,2));//项目文本(随工单位最后两位)
				// p.setOrderid(str.substring(str.length()-2));//工单号(随工单位除了最后两位)
				pickDetailList.set(i, p);
			}
		}	
		if(flag == false){
			addActionError("请检查是否正确输入内容!");
			return ERROR;
		}
		pickDetailService.save(pickDetailList, pick);

		pkList=pickDetailService.getPickDetail(pick.getId());
		try {
			pickRfc = pickRfcImple.MaterialDocumentCrt(matnr, pick, pkList);
		} catch (IOException e) {
			addActionError("IO操作失败");
			e.printStackTrace();
			return ERROR;
		} catch (CustomerException e) {
			addActionError(e.getMsgDes());
			System.out.println(e.getMsgDes());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			addActionError("系统出现问题，请联系系统管理员");
			e.printStackTrace();
			return ERROR;
		}
		pick.setMblnr(pickRfc);
		pickService.save(pick);
		redirectionUrl="pick!list.action?workingBillId="+workingBillId;
		return SUCCESS;
	}
	
	public PickDetail getPickDetail() {
		return pickDetail;
	}

	public void setPickDetail(PickDetail pickDetail) {
		this.pickDetail = pickDetail;
	}

	public PickDetailService getPickDetailService() {
		return pickDetailService;
	}

	public void setPickDetailService(PickDetailService pickDetailService) {
		this.pickDetailService = pickDetailService;
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

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String[] getRbg() {
		return rbg;
	}

	public void setRbg(String[] rbg) {
		this.rbg = rbg;
	}

	public String[] getPt() {
		return pt;
	}

	public void setPt(String[] pt) {
		this.pt = pt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public Pick getPick() {
		return pick;
	}

	public void setPick(Pick pick) {
		this.pick = pick;
	}

	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	public List<PickDetail> getPkList() {
		return pkList;
	}

	public void setPkList(List<PickDetail> pkList) {
		this.pkList = pkList;
	}

	public String getWokingBillId() {
		return workingBillId;
	}

	public void setWokingBillId(String wokingBillId) {
		this.workingBillId = wokingBillId;
	}

	public List<PickDetail> getPickDetailList() {
		return pickDetailList;
	}

	public void setPickDetailList(List<PickDetail> pickDetailList) {
		this.pickDetailList = pickDetailList;
	}

	public String getMy_id() {
		return my_id;
	}

	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}

	public List<Dict> getAllType() {
		return dictService.getList("dictname","pickType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	

}
