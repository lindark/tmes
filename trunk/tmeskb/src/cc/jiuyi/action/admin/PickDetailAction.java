
package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.MatStockRfc;
import cc.jiuyi.sap.rfc.impl.MatStockRfcImpl;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

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
	@Resource
	private MatStockRfc matstockrfc;
	@Resource
	private BomService bomService;

	private String productsId;
	private WorkingBill workingbill;
	private String workingBillId;

	private Admin admin;
	private Material material;
	private String matnr;
	private String my_id;// 自定义ID
	private List<Bom> bomList;
	private List<PickDetail> pickDetailList;
	private List<PickDetail> pkList;
	private List<Pick> pickList=new ArrayList<Pick>();;
	private List<Dict> allType;
	private List<Pick> pickRfc;
	private String info;//领退料类型/移动类型
	private String cardnumber;//卡号
	private String pickId;//页面传过来主表id
	private String labst;//库存地点
	
	

	// 添加
	public String add() {
		return LIST;
	}

	// 列表
	public String list() {
		workingbill = workingBillService.get(workingBillId);
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		bomList = bomService.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
		/** 调SAP接口取库存数量 **/
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();		
		try {
			for (int i = 0; i < bomList.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Bom bom = bomList.get(i);
				map.put("matnr", bom.getMaterialCode());
				map.put("lgort", admin.getDepartment().getTeam().getFactoryUnit().getWarehouse());		
				list.add(map);
			}			
			List<HashMap<String, String>> data = matstockrfc.getMatStockList(list);
			for (int i = 0; i < data.size(); i++) {
				String matnr = data.get(i).get("matnr");
				String labst = data.get(i).get("labst");
				for (int j = 0; j < bomList.size(); j++) {
					Bom bom = bomList.get(j);
					if(matnr.equals(bom.getMaterialCode())){
						bom.setStockAmount(labst);
					}
					Material mt = materialService.get("materialCode", bom.getMaterialCode());
					if(mt==null){
						bom.setCqmultiple("1");
						bom.setCqhStockAmount(bom.getStockAmount());
					}else{
						if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
							bom.setCqmultiple("1");
							bom.setCqhStockAmount(bom.getStockAmount());
						}else{
							bom.setCqmultiple(mt.getCqmultiple());
							BigDecimal multiple = new BigDecimal(mt.getCqmultiple());
							BigDecimal stockAmount = new BigDecimal(bom.getStockAmount());
							BigDecimal total = multiple.multiply(stockAmount);
							bom.setCqhStockAmount(total.toString());
						}
					}
					bomList.set(j, bom);					
				}			
			}
			
			Collections.sort(bomList); 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
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
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		bomList = bomService.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
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
		pick = pickService.load(id);
		workingbill = workingBillService.get(workingBillId);
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String aufnr = workingbill.getAufnr();
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		bomList = bomService.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
		/** 调SAP接口取库存数量 **/
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();		
		try {
			for (int i = 0; i < bomList.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Bom bom = bomList.get(i);
				map.put("matnr", bom.getMaterialCode());
				map.put("lgort", admin.getDepartment().getTeam().getFactoryUnit().getWarehouse());		
				list.add(map);
			}			
			List<HashMap<String, String>> data = matstockrfc.getMatStockList(list);
			for (int j = 0; j < bomList.size(); j++) {
				Bom bom = bomList.get(j);
				String materialCode = bom.getMaterialCode();
				String[] propertyNames = {"pick.id","materialCode"};
				Object[] propertyValues = {id,materialCode};
				PickDetail pickdetail = pickDetailService.get(propertyNames, propertyValues);
				for (int i = 0; i < data.size(); i++) {
					String matnr = data.get(i).get("matnr");//1
					String labst = data.get(i).get("labst");
					if(matnr.equals(materialCode)){
						bom.setStockAmount(labst);
					}
				}
				if(pickdetail == null)
					continue;
				bom.setPickAmount(pickdetail.getPickAmount());
				bom.setPickDetailid(pickdetail.getId());
				bom.setCqhStockAmount(pickdetail.getCqhStockAmount());
				bom.setCqmultiple(pickdetail.getCqmultiple());
				bom.setCqPickAmount(pickdetail.getCqPickAmount());
				bomList.set(j, bom);
			}
			Collections.sort(bomList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	// 更新
	//@InputConfig(resultName = "error")
	public String creditupdate() {
		pick = pickService.get(pickId);//根据页面传过来的主表id拿主表对象
	    this.pickDetailService.updateAll(pick, pickDetailList, cardnumber,info);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 保存
	@Validations(requiredStrings = {
	// @RequiredStringValidator(fieldName = "pickDetail.pickType", message ="领料类型不能为空!"),

	}, intRangeFields = { @IntRangeFieldValidator(fieldName = "pickDetail.pickAmount", min = "0", message = "领料数量不能为空!")

	}
	)
	//@InputConfig(resultName = "error")
	public String creditsubmit() throws Exception {
		WorkingBill workingBill = workingBillService.get(workingBillId);
		String workingBillCode = workingBill.getWorkingBillCode();
		Admin admin = adminService.getByCardnum(cardnumber);
		admin = adminService.get(admin.getId());
		Pick pick = new Pick();
//		pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
//		pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
//		pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
//		pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
		pick.setMove_type(info);//移动类型 
		pick.setBudat(workingBill.getProductDate());//随工单日期
		pick.setLgort(admin.getDepartment().getTeam().getFactoryUnit().getWarehouse());//库存地点SAP测试数据 单元库存地点
		pick.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
	    pick.setWerks(admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
		pick.setCreateDate(new Date());
		pick.setCreateUser(admin);
		pick.setWorkingbill(workingBill);
		pick.setState("1");
		boolean flag = false;
		List<PickDetail> pickDetailList1= new ArrayList<PickDetail>();
		for (int i = 0; i < pickDetailList.size(); i++) {
			PickDetail p = pickDetailList.get(i);
			if(p!=null){
				if(p.getCqPickAmount()!=null && !"".equals(p.getCqPickAmount())){
					String[] s = p.getCqPickAmount().split(",");
					p.setCqPickAmount(s[0]);
				}
			}
			if (!"".equals(info) && !"".equals(p.getPickAmount()) && !"0".equals(p.getPickAmount())) {
				flag = true;
				p.setConfirmUser(admin);
//				p.setMaterialCode("10490284");
//				p.setCharg("15091901");
//				p.setItem_text("文本");
//				p.setOrderid("100116549");
				p.setPickType(info);
//				p.setCharg("15091901");//批号
				p.setItem_text(workingBillCode.substring(workingBillCode.length()-2));//项目文本(随工单位最后两位)
				p.setOrderid(workingBillCode.substring(0,workingBillCode.length()-2));//工单号(随工单位除了最后两位)
				pickDetailList1.add(p);
			}
		}	
		if(flag == false){
			return ajaxJsonErrorMessage("输入内容有误,数量不能为0且必须选择对应操作类型!");
		}

		/**同时保存主从表**/
		pickDetailService.saveSubmit(pickDetailList1, pick);
		
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	
	//刷卡确认
	public String creditapproval(){
		String message="";
		WorkingBill workingBill = workingBillService.get(workingBillId);
		String workingBillCode = workingBill.getWorkingBillCode();
		Admin admin = adminService.getByCardnum(cardnumber);
		admin = adminService.get(admin.getId());
		Pick pick=new Pick();
//		pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
//		pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
//		pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
//		pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
//		pick.setMove_type(info);// 移动类型 SAP测试数据
		pick.setBudat(workingBill.getProductDate());//随工单日期
		pick.setLgort(admin.getDepartment().getTeam().getFactoryUnit().getWarehouse());//库存地点
		pick.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 随工单位最后两位
		pick.setWerks(admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂
		// SAP测试数据 工厂编码
		pick.setMove_type(info);
		// 移动类型 SAP测试数据
		pick.setCreateDate(new Date());
		pick.setCreateUser(admin);
		pick.setWorkingbill(workingBill);
		pick.setConfirmUser(admin);
		pick.setState("1");
		boolean flag = false;
		List<PickDetail> pickDetailList1= new ArrayList<PickDetail>();
		for (int i = 0; i < pickDetailList.size(); i++) {
			PickDetail p = pickDetailList.get(i);
			if (!"".equals(info) && !"".equals(p.getPickAmount()) &&!"0".equals(p.getPickAmount())) {
				flag = true;
				String s = "";
//				String str = workingBill.getId();
				p.setConfirmUser(admin);
//				p.setMaterialCode("10490284");
//				p.setCharg("15091901");
//				p.setItem_text("文本");
//				p.setOrderid("100116549");
				p.setPickType(info);
				p.setMaterialCode(p.getMaterialCode());//物料编码
				p.setItem_text(workingBillCode.substring(workingBillCode.length()-2));//项目文本(随工单位最后两位)
				//System.out.println("项目文本:"+p.getItem_text());
				p.setOrderid(workingBillCode.substring(0,workingBillCode.length()-2));//工单号(随工单位除了最后两位)
				//System.out.println("工单号:"+p.getOrderid());
				pickDetailList1.add(p);
			}
		}	
		if(flag == false){
			return ajaxJsonErrorMessage("输入内容有误,数量不能为0且必须选择对应操作类型!");
		}
		String pk=pickDetailService.saveApproval(pickDetailList1, pick); 
		Pick pick3=pickService.get(pk);
		pickList.add(pick);
		pkList=pickDetailService.getPickDetail(pick3.getId());
		for (int i = 0; i < pkList.size(); i++) {
			PickDetail pickDetail=pkList.get(i);
			pickDetail.setXh(pk);
		}
		
		try {
			Boolean flag1 = true;
			pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", pickList, pkList);
			for(Pick pick2 : pickRfc){
				String e_type = pick2.getE_type();
				if(e_type.equals("E")){ //如果有一行发生了错误
					flag1 = false;
					message +=pick2.getE_message();
				}
			}
			if(!flag1)
				return ajaxJsonErrorMessage(message);
			else{
				flag1 = true;
				pickRfc = pickRfcImple.BatchMaterialDocumentCrt("", pickList, pkList);
				for(Pick pick2 : pickRfc){
					String e_type = pick2.getE_type();
					String e_message=pick2.getE_message();
					String ex_mblnr=pick2.getEx_mblnr();
					if(e_type.equals("E")){ //如果有一行发生了错误
						flag1 = false;
						message +=pick2.getE_message();
					}else{
						Pick pickReturn=pickService.get(pk);
						pickReturn.setE_message(e_message);
						pickReturn.setEx_mblnr(ex_mblnr);
						pickReturn.setE_type(e_type);
						pickReturn.setMove_type(info);
						pickReturn.setState("2");
						pickService.update(pickReturn);
					}
				}
				if(!flag1)
					return ajaxJsonErrorMessage(message);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功");
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

	public String getProductsId() {
		return productsId;
	}

	public void setProductsId(String productsId) {
		this.productsId = productsId;
	}

	public List<Bom> getBomList() {
		return bomList;
	}

	public void setBomList(List<Bom> bomList) {
		this.bomList = bomList;
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

	public List<Pick> getPickList() {
		return pickList;
	}

	public void setPickList(List<Pick> pickList) {
		this.pickList = pickList;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getPickId() {
		return pickId;
	}

	public void setPickId(String pickId) {
		this.pickId = pickId;
	}

	public String getLabst() {
		return labst;
	}

	public void setLabst(String labst) {
		this.labst = labst;
	}



}
