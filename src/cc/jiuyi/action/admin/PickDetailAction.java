
package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.MatStockRfc;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.ProcessHandoverAllService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**@author Reece 2016/3/5
 * 后台Action类-领/退料从表
 */

@ParentPackage("admin")
public class PickDetailAction extends BaseAdminAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8353535527507793596L;
	public static Logger log = Logger.getLogger(PickDetailAction.class);
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
	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private ProcessHandoverAllService processHandoverAllService;
	
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
	private String loginId;
	private String type;
	

	// 添加
	public String add() {
		return LIST;
	}

	// 列表
	public String list() {
		Admin admin = adminService.get(loginId);
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
		if(!ThinkWayUtil.isPass(admin)){
			addActionError("您当前未上班,不能进行退领料操作!");
			return ERROR;
		}
		
		List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
		if(lists!=null && lists.size() != 0){
			addActionError("当前班次总体交接已完成!");
			return ERROR;
		}
		workingbill = workingBillService.get(workingBillId);
		String lgort = admin.getTeam().getFactoryUnit().getWarehouse();//库存地点
		String lgpla = admin.getTeam().getFactoryUnit().getDelivery();//仓位
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		String im_type = "";
		if(!ThinkWayUtil.null2String(lgpla).equals(""))
			im_type = "";
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
				map.put("lgort", lgort);//库存地点
				map.put("lgpla", lgpla);//仓位
				map.put("werks", werks);
				list.add(map);
			}
			bomList = new ArrayList();
			List<HashMap<String, String>> data = matstockrfc.getMatStockList(im_type,list);
			for (int i = 0; i < data.size(); i++) {
				String matnr = data.get(i).get("matnr");
				String labst = data.get(i).get("labst");
				String maktx = data.get(i).get("maktx");
				String charg = data.get(i).get("charg");
				
//				for (int j = 0; j < bomList.size(); j++) {
//					Bom bom = bomList.get(j);
//					if(matnr.equals(bom.getMaterialCode())){
//						bom.setStockAmount(labst);
//						Material mt = materialService.get("materialCode", bom.getMaterialCode());
//						if(mt==null){
//							bom.setCqmultiple("1");
//							bom.setCqhStockAmount(bom.getStockAmount());
//						}else{
//							if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
//								bom.setCqmultiple("1");
//								bom.setCqhStockAmount(bom.getStockAmount());
//							}else{
//								bom.setCqmultiple(mt.getCqmultiple());
//								BigDecimal multiple = new BigDecimal(mt.getCqmultiple());
//								BigDecimal stockAmount = new BigDecimal(bom.getStockAmount());
//								BigDecimal total = multiple.multiply(stockAmount);
//								bom.setCqhStockAmount(total.toString());
//							}
//						}
//						bomList.set(j, bom);
//					}
//				}
				Bom bom = new Bom();
				bom.setMaterialName(maktx);
				bom.setMaterialCode(matnr);
				bom.setStockAmount(labst);
				bom.setXcharg(charg);
				FactoryUnit factoryUnit = factoryUnitService.get("factoryUnitCode", workingbill.getWorkcenter());
				Material mt = materialService.getByNum(matnr, factoryUnit);
//				Material mt = materialService.get("materialCode", matnr);
				if(mt==null){
					bom.setCqmultiple("1");
					bom.setCqhStockAmount(labst);
				}else{
					if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
						bom.setCqmultiple("1");
						bom.setCqhStockAmount(labst);
					}else{
						bom.setCqmultiple(mt.getCqmultiple());
						BigDecimal multiple = new BigDecimal(mt.getCqmultiple());
						BigDecimal stockAmount = new BigDecimal(labst);
						BigDecimal total = multiple.multiply(stockAmount);
						bom.setCqhStockAmount(total.toString());
					}
				}
				bomList.add(bom);
				
			}
			
			Collections.sort(bomList, new  Comparator<Bom>() {

				@Override
				public int compare(Bom o1, Bom o2) {
					int map1value = Integer.parseInt(o1.getMaterialCode());
	                int map2value =  Integer.parseInt(o2.getMaterialCode());
	                return map1value - map2value;
				}
			
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		return LIST;
	}
	
	// 列表
		public String editList() {
			Admin admin = adminService.get(loginId);
			
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
			if(!ThinkWayUtil.isPass(admin)){
				addActionError("您当前未上班,不能进行退领料操作!");
				return ERROR;
			}
			
			List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
			if(lists!=null && lists.size() != 0){
				addActionError("当前班次总体交接已完成!");
				return ERROR;
			}
			
			workingbill = workingBillService.get(workingBillId);
			String lgort = admin.getTeam().getFactoryUnit().getWarehouse();//库存地点
			String lgpla = admin.getTeam().getFactoryUnit().getDelivery();//仓位
			String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
			String im_type = "";
			if(!ThinkWayUtil.null2String(lgpla).equals(""))
				im_type = "";
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
					map.put("lgort", lgort);//库存地点
					map.put("lgpla", lgpla);//仓位
					map.put("werks", werks);
					list.add(map);
				}
				bomList = new ArrayList();
				List<HashMap<String, String>> data = matstockrfc.getMatStockList(im_type,list);
				for (int i = 0; i < data.size(); i++) {
					String matnr = data.get(i).get("matnr");
					String labst = data.get(i).get("labst");
					String maktx = data.get(i).get("maktx");
					String charg = data.get(i).get("charg");
					Bom bom = new Bom();
					/**
					 * 循环数据库，查出有填写裁切后领退料数量数据的记录
					 */
					workingbill = workingBillService.get(workingBillId);
					pick = pickService.load(id);
					pkList = pickDetailService.getPickDetail(id);
					for (int j = 0; j < pkList.size(); j++) {
						PickDetail pickDetail = pkList.get(j);
//						pickDetail.setXpickType(ThinkWayUtil.getDictValueByDictKey(dictService, "pickType", pickDetail.getPickType()));	
//						if((pickDetail.getMaterialCode()).equals(data.get(i).get("matnr")) && 
//								(pickDetail.getCharg()).equals(data.get(i).get("charg"))){
//							if(pickDetail.getCqPickAmount() != null && !pickDetail.getCqPickAmount().equals("")){
						if(charg != null && !charg.equals("")){
							if((pickDetail.getMaterialCode()).equals(data.get(i).get("matnr")) && 
									(pickDetail.getCharg()).equals(data.get(i).get("charg"))){
								bom.setCqPickAmount(pickDetail.getCqPickAmount());
							}		
						}else{
							if((pickDetail.getMaterialCode()).equals(data.get(i).get("matnr"))){
							bom.setCqPickAmount(pickDetail.getCqPickAmount());
							}
						}
					}
					
					bom.setMaterialName(maktx);
					bom.setMaterialCode(matnr);
					bom.setStockAmount(labst);
					bom.setXcharg(charg);
					FactoryUnit factoryUnit = factoryUnitService.get("factoryUnitCode", workingbill.getWorkcenter());
					Material mt = materialService.getByNum(matnr, factoryUnit);
//					Material mt = materialService.get("materialCode", matnr);
					if(mt==null){
						bom.setCqmultiple("1");
						bom.setCqhStockAmount(labst);
					}else{
						if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
							bom.setCqmultiple("1");
							bom.setCqhStockAmount(labst);
						}else{
							bom.setCqmultiple(mt.getCqmultiple());
							BigDecimal multiple = new BigDecimal(mt.getCqmultiple());
							BigDecimal stockAmount = new BigDecimal(labst);
							BigDecimal total = multiple.multiply(stockAmount);
							bom.setCqhStockAmount(total.toString());
						}
					}
					bomList.add(bom);
					
				}
				
				Collections.sort(bomList, new  Comparator<Bom>() {

					@Override
					public int compare(Bom o1, Bom o2) {
						int map1value = Integer.parseInt(o1.getMaterialCode());
		                int map2value =  Integer.parseInt(o2.getMaterialCode());
		                return map1value - map2value;
					}
				
				});
				
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
	
	//历史查看
	public String historyView() {
		pick = pickService.load(id);
		workingbill = pick.getWorkingbill();
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
		//System.out.println(jsonArray.get(0).toString());
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
		
		String lgort = admin.getTeam().getFactoryUnit().getWarehouse();//库存地点
		String lgpla = admin.getTeam().getFactoryUnit().getDelivery();//仓位
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		String im_type = "";
		if(!ThinkWayUtil.null2String(lgpla).equals(""))
			im_type = "X";
		
		//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		bomList = bomService.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
		/** 调SAP接口取库存数量 **/
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();		
		try {
			for (int i = 0; i < bomList.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Bom bom = bomList.get(i);
				map.put("matnr", bom.getMaterialCode());
				map.put("lgort", lgort);//库存地点
				map.put("lgpla", lgpla);//仓位
				map.put("werks", werks);
				list.add(map);
			}			
			List<HashMap<String, String>> data = matstockrfc.getMatStockList(im_type,list);
			for (int j = 0; j < bomList.size(); j++) {
				Bom bom = bomList.get(j);
				String materialCode = bom.getMaterialCode();
				String[] propertyNames = {"pick.id","materialCode"};
				Object[] propertyValues = {id,materialCode};
				PickDetail pickdetail = pickDetailService.get(propertyNames, propertyValues);
				for (int i = 0; i < data.size(); i++) {
					String matnr = data.get(i).get("matnr");//1
					String labst = data.get(i).get("labst");
					String charg = data.get(i).get("charg");
					if(matnr.equals(materialCode)){
						bom.setStockAmount(labst);
					}
				}
				if(pickdetail == null){
					FactoryUnit factoryUnit = factoryUnitService.get("factoryUnitCode", workingbill.getWorkcenter());
					Material mt = materialService.getByNum(bom.getMaterialCode(), factoryUnit);
//					Material mt = materialService.get("materialCode", bom.getMaterialCode());
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
				}else{
					bom.setPickAmount(pickdetail.getPickAmount());
					bom.setPickDetailid(pickdetail.getId());
					bom.setCqhStockAmount(pickdetail.getCqhStockAmount());
					bom.setCqmultiple(pickdetail.getCqmultiple());
					bom.setCqPickAmount(pickdetail.getCqPickAmount());
					bom.setXcharg(pickdetail.getCharg());
				}
				
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
		Admin admin1 = adminService.get(loginId);
		String lgpla = admin1.getTeam().getFactoryUnit().getDelivery();//仓位
		lgpla = ThinkWayUtil.null2String(lgpla);
		
		Pick pick = new Pick();
//		pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
//		pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
//		pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
//		pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
		pick.setMove_type(info);//移动类型 
		pick.setBudat(workingBill.getProductDate());//随工单日期
		pick.setLgort(admin1.getTeam().getFactoryUnit().getWarehouse());//库存地点SAP测试数据 单元库存地点
		pick.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
	    pick.setWerks(admin1.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
		pick.setCreateDate(new Date());
		pick.setCreateUser(admin);
		pick.setWorkingbill(workingBill);
		pick.setState("1");
		boolean flag = false;
		List<PickDetail> pickDetailList1= new ArrayList<PickDetail>();
		for (int i = 0; i < pickDetailList.size(); i++) {
			PickDetail p = pickDetailList.get(i);
			/*if(p!=null){
				if(p.getCqPickAmount()!=null && !"".equals(p.getCqPickAmount())){
					String[] s = p.getCqPickAmount().split(",");
					p.setCqPickAmount(s[0]);
				}
			}*/
			if (!"".equals(info) && !"".equals(p.getPickAmount()) && !"0".equals(p.getPickAmount())) {
				flag = true;
				
				if("261".equals(info)){//领料
					Double pickamount = Double.parseDouble(ThinkWayUtil.null2o(p.getPickAmount()));
					Double stockamount = Double.parseDouble(ThinkWayUtil.null2o(p.getStockAmount()));
					if(pickamount > stockamount){
						return ajaxJsonErrorMessage("领用数量不能大于库存数量!");
					}
				}
				
				
				p.setConfirmUser(admin);
//				p.setMaterialCode("10490284");
//				p.setCharg("15091901");
//				p.setItem_text("文本");
//				p.setOrderid("100116549");
				p.setPickType(info);
				p.setLgpla(lgpla);
//				p.setCharg("15091901");//批号
				p.setItem_text(workingBillCode.substring(workingBillCode.length()-2));//项目文本(随工单位最后两位)
				p.setOrderid(workingBillCode.substring(0,workingBillCode.length()-2));//工单号(随工单位除了最后两位)
				pickDetailList1.add(p);
			}
			Collections.sort(pickDetailList1);
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
		if(!"".equals(pickId)&&pickId!=null){
			try {
				ids = pickId.split(",");
				Pick p  = pickService.get(pickId);
				String message = "";
				List<Pick> list = new ArrayList<Pick>();
				list.add(p);
				admin = adminService.getByCardnum(cardnumber);
				//Admin admin1 = adminService.get(loginId);
				
				pkList = pickDetailService.getPickDetail(pickId);
				pick = pickDetailService.saveApproval1(pickDetailList,p);
				List<PickDetail> pickdetailList =new ArrayList<PickDetail>(pick.getPickDetail());
					Boolean flag = true;
					pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", list,
							pickdetailList);
					for (Pick pick2 : pickRfc) {
						if(pick2.getEx_mblnr()!=null && !"".equals(pick2.getEx_mblnr())){
							log.info("---X----ex_mblnr---"+pick2.getEx_mblnr());
						}
						String e_type = pick2.getE_type();
						if (e_type.equals("E")) { // 如果有一行发生了错误
							flag = false;
							message += pick2.getE_message();
						}
					}
					if (!flag)
						return ajaxJsonErrorMessage(message);
					else {
						flag = true;
						List<Pick>pickRfc1 = new ArrayList<Pick>();
						pickRfc1 = pickRfcImple.BatchMaterialDocumentCrt("", list,
								pickdetailList);
						for (Pick pick2 : pickRfc1) {
							String e_type = pick2.getE_type();
							String e_message = pick2.getE_message();
							String ex_mblnr = pick2.getEx_mblnr();
							// String move_type = pick2.getMove_type();
							if (e_type.equals("E")) { // 如果有一行发生了错误
								flag = false;
								message += pick2.getE_message();
							} else {
								log.info("ex_mblnr-----"+ex_mblnr);
								if(ex_mblnr==null || "".equals(ex_mblnr)){
									flag = false;
									message += pick2.getWorkingbill().getWorkingBillCode()+"未返回凭证;";
								}else{
									Pick pickReturn = pickService.get(pick2.getId());
									pickReturn.setE_message(e_message);
									pickReturn.setEx_mblnr(ex_mblnr);
									pickReturn.setE_type(e_type);
									// pickReturn.setMove_type(move_type);
									pickReturn.setState("2");
									pickReturn.setConfirmUser(admin);
									HashMap<String, Object> map = new HashMap<String, Object>();
									pickDetailService.updatePIckAndWork(pickReturn, map);
								}
							}
						}
						if (!flag)
							return ajaxJsonErrorMessage(message);
					}
				} catch (IOException e) {
					LOG.equals(e);
					e.printStackTrace();
					return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
				} catch (Exception e) {
					LOG.equals(e);
					e.printStackTrace();
					return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
				}

				return ajaxJsonSuccessMessage("您的操作已成功!");
		}else{
			String message="";
			WorkingBill workingBill = workingBillService.get(workingBillId);
			String workingBillCode = workingBill.getWorkingBillCode();
			Admin admin = adminService.getByCardnum(cardnumber);
			Admin admin1 = adminService.get(loginId);
			String lgpla = admin1.getTeam().getFactoryUnit().getDelivery();//仓位
			lgpla = ThinkWayUtil.null2String(lgpla);
			Pick pick=new Pick();
//			pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
//			pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
//			pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
//			pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
//			pick.setMove_type(info);// 移动类型 SAP测试数据
			pick.setBudat(workingBill.getProductDate());//随工单日期
			pick.setLgort(admin1.getTeam().getFactoryUnit().getWarehouse());//库存地点
			pick.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 随工单位最后两位
			pick.setWerks(admin1.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂
			// SAP测试数据 工厂编码
			pick.setMove_type(info);
			// 移动类型 SAP测试数据
			pick.setCreateDate(new Date());
			pick.setCreateUser(admin);
			pick.setWorkingbill(workingBill);
			//pick.setConfirmUser(admin);
			pick.setState("1");
			boolean flag = false;
			List<PickDetail> pickDetailList1= new ArrayList<PickDetail>();
			for (int i = 0; i < pickDetailList.size(); i++) {
				PickDetail p = pickDetailList.get(i);
				if (!"".equals(info) && !"".equals(p.getPickAmount()) &&!"0".equals(p.getPickAmount())) {
					flag = true;
					String s = "";
//					String str = workingBill.getId();
					p.setConfirmUser(admin);
//					p.setMaterialCode("10490284");
//					p.setCharg("15091901");
//					p.setItem_text("文本");
//					p.setOrderid("100116549");
					p.setPickType(info);
					p.setLgpla(lgpla);
//					p.setCharg(p.getCharg());
					p.setMaterialCode(p.getMaterialCode());//物料编码
					p.setItem_text(workingBillCode.substring(workingBillCode.length()-2));//项目文本(随工单位最后两位)
					//System.out.println("项目文本:"+p.getItem_text());
					p.setCharg(p.getCharg());
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
					if(pick2.getEx_mblnr()!=null && !"".equals(pick2.getEx_mblnr())){
						log.info("---X----ex_mblnr---"+pick2.getEx_mblnr());
					}
					String e_type = pick2.getE_type();
					if(e_type.equals("E")){ //如果有一行发生了错误
						flag1 = false;
						message +=pick2.getE_message();
					}
				}
				if(!flag1){
					return ajaxJsonErrorMessage(message);
				}
				else{
					flag1 = true;
					List<Pick>pickRfc1 = new ArrayList<Pick>();
					pickRfc1 = pickRfcImple.BatchMaterialDocumentCrt("", pickList, pkList);
					for(Pick pick2 : pickRfc1){
						String e_type = pick2.getE_type();
						String e_message=pick2.getE_message();
						String ex_mblnr=pick2.getEx_mblnr();
						if(e_type.equals("E")){ //如果有一行发生了错误
							flag1 = false;
							message +=pick2.getE_message();
						}else{
							log.info("ex_mblnr-----"+ex_mblnr);
							if(ex_mblnr==null || "".equals(ex_mblnr)){
								flag1 = false;
								message += pick2.getWorkingbill().getWorkingBillCode()+"未返回凭证;";
							}else{
								Pick pickReturn=pickService.get(pk);
								pickReturn.setE_message(e_message);
								pickReturn.setEx_mblnr(ex_mblnr);
								pickReturn.setE_type(e_type);
								pickReturn.setMove_type(info);
								pickReturn.setState("2");
								pickReturn.setConfirmUser(admin);
								pickService.update(pickReturn);
							}
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
