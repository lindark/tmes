package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.PositionManagement;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UpDown;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.MatStockRfc;
import cc.jiuyi.sap.rfc.UpDownRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PositionManagementService;
import cc.jiuyi.service.UpDownServcie;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;


/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class UpDownAction extends BaseAdminAction {

	private static final long serialVersionUID = -7909258717886487018L;
	private static Logger log = Logger.getLogger(UpDownAction.class);  
	@Resource
	private UpDownServcie updownservice;
	@Resource
	private AdminService adminservice;
	@Resource
	private UpDownRfc undownrfc;
	@Resource
	private DictService dictservice;
	@Resource
	private PositionManagementService positionManagementService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private BomService bomService;
	@Resource
	private MatStockRfc matstockrfc;
	@Resource
	private MaterialService materialService;
	@Resource
	private AdminService adminService;
	@Resource
	private PickDetailService pickDetailService;
	
	private Pager pager;
	private UpDown updown;
	private String type;//类型
	private String lgpla;//发出仓位
	private String lgplaun;//接收仓位
	private String loginid;
	private String cardnumber;
	private String materialCode;//物料编码
	private String materialDesp;//物料描述
	private List<Locationonside> locationonsideList;
	private List<UpDown> updownList;
	private List<Object[]> updownObjList;
	private String matnr;
	private String maktx;
	private String tanum;
	private String end;
	private String start;
	private String isud;//判断页面
	private String warehouse;//页面传入参数，超市仓库
	private String charg;//批次
	private List<PositionManagement> positionManagementList1;
	private List<String> positionManagementList;
	private String workingBillId;//随工单id;
	private WorkingBill workingbill;//随工单
	private List<WorkingBill> workingBillList;//随工单集合
	private List<Bom> bomList;
	private List<PickDetail> pickDetailList;
	private List<Dict> allDetails;// 来料说明
	
	
	// 超市领用记录 @author Reece 2016/3/22
		public String history(){
			return "history";
		}
		
		
	//超市领用列表 @author Reece 2016/3/22
		public String historylist() {
			HashMap<String, String> map = new HashMap<String, String>();
			if (pager.getOrderBy().equals("")) {
				pager.setOrderType(OrderType.desc);
				pager.setOrderBy("modifyDate");
			}
			if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
				if (!filters.equals("")) {
					JSONObject filt = JSONObject.fromObject(filters);
					Pager pager1 = new Pager();
					Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
					m.put("rules", jqGridSearchDetailTo.class);
					pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
					pager.setRules(pager1.getRules());
					pager.setGroupOp(pager1.getGroupOp());
				}
			}
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("matnr") != null) {
					String matnr = obj.getString("matnr").toString();
					map.put("matnr", matnr);
				}
				if (obj.get("maktx") != null) {
					String maktx = obj.getString("maktx").toString();
					map.put("maktx", maktx);
				}
				if (obj.get("tanum") != null) {
					String tanum = obj.getString("tanum").toString();
					map.put("tanum", tanum);
				}
				if (obj.get("start") != null) {
					String start = obj.getString("start").toString();
					map.put("start", start);
				}
				if (obj.get("end") != null) {
					String end = obj.getString("end").toString();
					map.put("end", end);
				}
				if (obj.get("start") != null && obj.get("end") != null) {
					String start = obj.get("start").toString();
					String end = obj.get("end").toString();
					map.put("start", start);
					map.put("end", end);
				}
				//上下架类型
				if (obj.get("type") != null) {
					String type = obj.getString("type").toString();
					map.put("type", type);
				}
			}
			pager = updownservice.historyjqGrid(pager, map);
			List<UpDown> updownList = pager.getList();
			List<UpDown> lst = new ArrayList<UpDown>();
			for (int i = 0; i < updownList.size(); i++) {
				UpDown updown = updownList.get(i);
				updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
				updown.setShiftx(ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()));
				updown.setAdminname(updown.getAppvaladmin().getName());
				lst.add(updown);
			}
			pager.setList(lst);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UpDown.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		}
		
		// Excel导出 @author Reece 2016/3/22
				public String excelexport() {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("matnr", matnr);
					map.put("maktx", maktx);
					map.put("tanum", tanum);
					map.put("start", start);
					map.put("end", end);
					map.put("type", type);
					
					List<String> header = new ArrayList<String>();
					List<Object[]> body = new ArrayList<Object[]>();
					header.add("生产日期");
					header.add("班次");
					header.add("组件编码");
					header.add("组件描述");
					header.add("批次");
					header.add("类型");
					
					header.add("发出仓位");
					header.add("接收仓位");
					header.add("库存地点");
					header.add("数量");

					header.add("转储单号");
					header.add("行项目号");
					header.add("创建日期");
					header.add("确认人");

					List<UpDown> upList = updownservice.historyExcelExport(map);
					for (int i = 0; i < upList.size(); i++) {
						UpDown updown = upList.get(i);

						Object[] bodyval = {
								updown.getProductDate(),
								updown.getShift()==null ?"":
									ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()),
								updown.getMatnr(),
								updown.getMaktx(),
								updown.getCharg(),
								ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()),
								
								updown.getUplgpla(),
								updown.getDownlgpla(),
								updown.getLgort(),
								updown.getAmount(),
								
								updown.getTanum(),
								updown.getTapos(),
								updown.getCreateDate(),
								updown.getAppvaladmin() ==null?"":updown.getAppvaladmin().getName(),
								};
						body.add(bodyval);
					}

					try {
						String excelName;
						if("UD".equals(isud)){
							 excelName="上下架记录表";
						}else{
							 excelName="超市领用记录表";
						}
						String fileName = excelName + ".xls";
						setResponseExcel(fileName);
						ExportExcel.exportExcel(excelName, header, body, getResponse()
								.getOutputStream());
						getResponse().getOutputStream().flush();
						getResponse().getOutputStream().close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
	
	
	public String add(){
		//检查数据完整性
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		if(admin.getProductDate() == null || admin.getShift() == null){
			addActionError("生产日期和班次必须绑定后才可以使用");
			return ERROR;
		}
		
		if("up".equals(type)){//上架
			String delivery = admin.getTeam().getFactoryUnit().getDelivery();
			if(ThinkWayUtil.null2String(delivery).equals("")){
				addActionError("当前单元未维护代发货仓位!");
				return ERROR;
			}
			this.lgpla = delivery;
		}else if("down".equals(type)){//下架
			String delivery = admin.getTeam().getFactoryUnit().getDelivery();
			if(ThinkWayUtil.null2String(delivery).equals("")){
				addActionError("当前单元未维护代发货仓位!");
				return ERROR;
			}
			this.lgplaun=delivery;
		}else if("updown".equals(type)){
			
		}else{
			addActionError("请输入正确的类型!");
			return ERROR;
		}
		if("up".equals(type) || "down".equals(type))
			materialCode = materialCode == null ?"303":ThinkWayUtil.null2String(materialCode);
		else if("updown".equals(type))
			materialCode = "";
		int matnrlen = materialCode.length();
		materialDesp = ThinkWayUtil.null2String(materialDesp);
		
		
		if(ThinkWayUtil.null2String(this.lgpla).equals("")){
			return INPUT;
		}
		//业务处理
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();//工厂
		String lgort = admin.getTeam().getFactoryUnit().getWarehouse();//库存地点
		//materialCode = ThinkWayUtil.null2String(materialCode);
		//String lgpla = this.lgpla ==null?"":this.lgpla;//仓位
		String lgpla = this.lgpla;
		try{
			List<HashMap<String,String>> hashList = updownservice.upmaterList(werks, lgort, "", lgpla,materialDesp);//物料编码在查询出来之后在处理
			locationonsideList = new ArrayList<Locationonside>();
			for(int i=0;i<hashList.size();i++){
				HashMap<String,String> hashmap = hashList.get(i);
				Locationonside locationonside = new Locationonside();
				
				String matnr01 = hashmap.get("matnr");//物料编码
				String maktx01 = hashmap.get("maktx");//物料描述
				
				String matnr02 = StringUtils.substring(matnr01, 0, matnrlen);
				if(!materialCode.equals(matnr02)){
					continue;
				}
				
				locationonside.setLocationCode(lgort);//库存地点
				locationonside.setMaterialCode(matnr01);//物料编码
				locationonside.setMaterialName(maktx01);//物料描述
				locationonside.setCharg(hashmap.get("charg"));//批次
				locationonside.setAmount(hashmap.get("verme"));//数量
				locationonsideList.add(locationonside);
			}
			Collections.sort(locationonsideList); 
		}catch(IOException e){
			e.printStackTrace();
			log.error(e);
			addActionError("IO操作失败");
			return ERROR;
		}catch(CustomerException e){
			e.printStackTrace();
			log.error(e);
			addActionError(e.getMsgDes());
			return ERROR;
		}
		
		return INPUT;
	}
	
	public String trim(){
		//检查数据完整性
		workingbill = workingBillService.get(workingBillId);
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		if(admin.getProductDate() == null || admin.getShift() == null){
			addActionError("生产日期和班次必须绑定后才可以使用");
			return ERROR;
		}
		
//		if("up".equals(type)){//上架
//			String delivery = admin.getTeam().getFactoryUnit().getDelivery();
//			if(ThinkWayUtil.null2String(delivery).equals("")){
//				addActionError("当前单元未维护代发货仓位!");
//				return ERROR;
//			}
//			this.lgpla = delivery;
//		}else
//		if("down".equals(type)){//下架
			String delivery = admin.getTeam().getFactoryUnit().getDelivery();
//			if(ThinkWayUtil.null2String(delivery).equals("")){
//				addActionError("当前单元未维护代发货仓位!");
//				return ERROR;
//			}
//			this.lgpla = delivery;
			this.lgplaun=delivery;
//		}else if("updown".equals(type)){
//			
//		}else{
//			addActionError("请输入正确的类型!");
//			return ERROR;
//		}
//		if("up".equals(type) || "down".equals(type))
//			materialCode = materialCode == null ?"303":ThinkWayUtil.null2String(materialCode);
			materialCode = "";
//		else if("updown".equals(type))
//			materialCode = "";
		int matnrlen = materialCode.length();
		materialDesp = ThinkWayUtil.null2String(materialDesp);
		
		
		if(ThinkWayUtil.null2String(this.lgpla).equals("")){
			return INPUT;
		}
		//Bom获取
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
			List<HashMap<String, String>> data = matstockrfc.getMatStockList(im_type,list);
			for (int i = 0; i < data.size(); i++) {
				String matnr = data.get(i).get("matnr");
				String labst = data.get(i).get("labst");
				for (int j = 0; j < bomList.size(); j++) {
					Bom bom = bomList.get(j);
					if(matnr.equals(bom.getMaterialCode())){
						bom.setStockAmount(labst);
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
			}
			
			Collections.sort(bomList); 
		
		//业务处理
		werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();//工厂
		lgort = admin.getTeam().getFactoryUnit().getWarehouse();//库存地点
		//materialCode = ThinkWayUtil.null2String(materialCode);
		//String lgpla = this.lgpla ==null?"":this.lgpla;//仓位
		lgpla = this.lgpla;//仓位
			List<HashMap<String,String>> hashList = updownservice.upmaterList(werks, lgort, "", lgpla,materialDesp);//物料编码在查询出来之后在处理
			//
			
			
			//
			locationonsideList = new ArrayList<Locationonside>();
			for(int i=0;i<hashList.size();i++){
				HashMap<String,String> hashmap = hashList.get(i);
				Locationonside locationonside = new Locationonside();
				
				String matnr01 = hashmap.get("matnr");//物料编码
				String maktx01 = hashmap.get("maktx");//物料描述
				String matnr02 = StringUtils.substring(matnr01, 0, matnrlen);
//				if(!materialCode.equals(matnr02)){
//					continue;
//				}
				boolean flag = false;
				for(int j=0;j<bomList.size();j++){
					if(((bomList.get(j).getMaterialCode()).equals(matnr01))){
						flag = true;
						break;
					}
				}
				if(flag){
				locationonside.setLocationCode(lgort);//库存地点
				locationonside.setMaterialCode(matnr01);//物料编码
				locationonside.setMaterialName(maktx01);//物料描述
				locationonside.setCharg(hashmap.get("charg"));//批次
				locationonside.setAmount(hashmap.get("verme"));//数量
				locationonsideList.add(locationonside);
				}
			}
			Collections.sort(locationonsideList); 
		}catch(IOException e){
			e.printStackTrace();
			log.error(e);
			addActionError("IO操作失败");
			return ERROR;
		}catch(CustomerException e){
			e.printStackTrace();
			log.error(e);
			addActionError(e.getMsgDes());
			return ERROR;
		}
		
		return INPUT;
	}
	/**
	 * 刷卡保存
	 * @return
	 */
	public String creditsave(){
		//检查数据完整性
		Admin admin = adminservice.get(loginid);
		Admin admin1 = adminservice.getByCardnum(cardnumber);
		if(admin.getProductDate() == null || admin.getShift() == null){
			return ajaxJsonErrorMessage("生产日期和班次必须绑定后才可以使用");
		}
		if(updownList==null || updownList.size()<=0){
			return ajaxJsonErrorMessage("请至少填写一行数据");
		}
		if(lgpla == null){
			return ajaxJsonErrorMessage("发出仓位必须填写");
		}
		if(lgplaun ==null){
			return ajaxJsonErrorMessage("目的地仓位必须填写");
		}
		//数据类型定义
		HashMap<String,String> hash = new HashMap<String,String>();
		List<HashMap<String,String>> hashList = new ArrayList<HashMap<String,String>>();
		//业务处理
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		String lgort = admin.getTeam().getFactoryUnit().getWarehouse();
//		String lgpla = admin.getTeam().getFactoryUnit().getDelivery();//仓位
		String flag = "";
		if(type.equals("up"))
			flag = "0";
		else if(type.equals("down"))
			flag = "2";
		else if(type.equals("updown")){
			flag = "3";
		}else
			return ajaxJsonErrorMessage("请正确填写类型!");
		
		hash.put("werks", werks);
		hash.put("lgort", lgort);
//		hash.put("lgpla", lgpla);
		hash.put("flag", flag);
		
		for(int i=0;i<updownList.size();i++){
			HashMap<String,String> hashmap = new HashMap<String,String>();
			UpDown updown = updownList.get(i);
			if(ThinkWayUtil.null2o(updown.getDwnum()) <= 0){
				updownList.remove(i);
				i--;
				continue;
			}
			hashmap.put("matnr", updown.getMatnr());//物料号
			hashmap.put("charg", updown.getCharg());//批次
			hashmap.put("lgpla", lgpla);//发出仓位
			hashmap.put("dwnum", ""+updown.getDwnum());
			hashmap.put("nlpla", lgplaun);//目的地仓位
			hashList.add(hashmap);
		}
		//接口调用
		try {
			List<HashMap<String,String>> maplist = undownrfc.undown(hash, hashList);
			for(int i=0;i<updownList.size();i++){
				UpDown updown = updownList.get(i);
				for(int y=0;y<maplist.size();y++){
					HashMap<String,String> map = maplist.get(y);
					String matnr = map.get("matnr");//物料号
					String charg = map.get("charg");//批次
					String tanum = map.get("tanum");//转储单号
					String tapos = map.get("tapos");//转出单行项目号
					if(ThinkWayUtil.null2String(updown.getMatnr()).equals(ThinkWayUtil.null2String(matnr)) &&
					   ThinkWayUtil.null2String(updown.getCharg()).equals(ThinkWayUtil.null2String(charg))){
						updown.setTanum(tanum);
						updown.setTapos(tapos);
					}
				}
				updown.setUplgpla(lgpla);
				updown.setDownlgpla(lgplaun);
				updown.setType(type);
				updown.setCharg(updown.getCharg());
				updown.setAppvaladmin(admin1);//保存人
				updown.setProductDate(admin.getProductDate());//将生产日期和班次写入
				updown.setShift(admin.getShift());
				updown.setFactoryUnit(admin.getTeam().getFactoryUnit());//添加的单元 jjt
				updownList.set(i, updown);
			}
			updownservice.save(updownList);
			if(workingBillId != null && !workingBillId.equals("")){
				WorkingBill workingBill = workingBillService.get(workingBillId);
				String workingBillCode = workingBill.getWorkingBillCode();

				lgpla = ThinkWayUtil.null2String(lgpla);
				Pick pick = new Pick();
//				pick.setBudat("2015-11-01");// SAP测试数据 随工单的日期
//				pick.setLgort("2201");// 库存地点 SAP测试数据 单元库存地点
//				pick.setZtext("测试凭证");// 抬头文本 SAP测试数据 随工单位最后两位
//				pick.setWerks("1000");// 工厂 SAP测试数据 工厂编码
				pick.setMove_type("261");//移动类型 
				pick.setBudat(workingBill.getProductDate());//随工单日期
				pick.setLgort(admin1.getTeam().getFactoryUnit().getWarehouse());//库存地点SAP测试数据 单元库存地点
				pick.setZtext(workingBillCode.substring(workingBillCode.length()-2));//抬头文本 SAP测试数据随工单位最后两位
			    pick.setWerks(admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
				pick.setCreateDate(new Date());
				pick.setCreateUser(admin1);
				pick.setWorkingbill(workingBill);
				pick.setState("1");
				boolean button = false;
				List<PickDetail> pickDetailList1= new ArrayList<PickDetail>();
				for (int i = 0; i < updownList.size(); i++) {
					UpDown updown = updownList.get(i);
					PickDetail p = new PickDetail();
					/*if(p!=null){
						if(p.getCqPickAmount()!=null && !"".equals(p.getCqPickAmount())){
							String[] s = p.getCqPickAmount().split(",");
							p.setCqPickAmount(s[0]);
						}
					}*/
					if (!"".equals(p.getPickAmount()) && !"0".equals(p.getPickAmount())) {
						button = true;
						
//						if("261".equals(info)){//领料
//							Double pickamount = Double.parseDouble(ThinkWayUtil.null2o(p.getPickAmount()));
//							Double stockamount = Double.parseDouble(ThinkWayUtil.null2o(p.getStockAmount()));
//							if(pickamount > stockamount){
//								return ajaxJsonErrorMessage("领用数量不能大于库存数量!");
//							}
//						}
						p.setMaterialCode(updown.getMatnr());
						p.setMaterialName(updown.getMaktx());
						p.setCharg(updown.getCharg());
						p.setMaterialState(ThinkWayUtil.getDictValueByDictKey(dictservice, "materialdetail", updown.getDetail()));
						p.setStockAmount((updown.getDwnum()).toString());
						p.setOrderid(workingBillCode.substring(0,workingBillCode.length()-2));
						p.setItem_text(workingBillCode.substring(workingBillCode.length()-2));
						Material mt = materialService.get("materialCode", p.getMaterialCode());
						if(mt==null){
							p.setCqmultiple("1");
							p.setCqhStockAmount(p.getStockAmount());
						}else{
							if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
								p.setCqmultiple("1");
								p.setCqhStockAmount(p.getStockAmount());
							}else{
								p.setCqmultiple(mt.getCqmultiple());
								BigDecimal multiple = new BigDecimal(mt.getCqmultiple());
								BigDecimal stockAmount = new BigDecimal(p.getStockAmount());
								BigDecimal total = multiple.multiply(stockAmount);
								p.setCqhStockAmount(total.toString());
							}
						}
						p.setCqPickAmount((updown.getDwnum().toString()));
						BigDecimal a = new BigDecimal(p.getCqhStockAmount());
						BigDecimal b = new BigDecimal(p.getCqmultiple());
						BigDecimal c = a.divide(b,3);
						p.setPickAmount(c.toString());
						p.setPickType("261");
						
						pickDetailList1.add(p);
					}
				}	
				if(button == false){
					return ajaxJsonErrorMessage("输入内容有误,数量不能为0且必须选择对应操作类型!");
				}

				/**同时保存主从表**/
				pickDetailService.saveSubmit(pickDetailList1, pick);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
			return ajaxJsonErrorMessage("IO操作失败!");
		} catch (CustomerException e) {
			e.printStackTrace();
			log.error(e.getMsgDes());
			return ajaxJsonErrorMessage(e.getMsgDes());
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	public String list(){
		return LIST;
	}
	
	public String cslist(){
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		UpDown updown = new UpDown();
		updown.setType("updown");
		updown.setProductDate(admin.getProductDate());
		updown.setShift(admin.getShift());
		updownObjList = updownservice.findUpdowngroupby(updown);
		
		return "cslist";
	}
	
	public String ajlist(){
		
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		
		//此处固定查询
		List<String> list = new ArrayList<String>();
		list.add("up");
		list.add("down");
		
		pager = updownservice.findByPager(pager,admin,list);
		
		List<UpDown> updownList = pager.getList();
		List<UpDown> updownList1 = new ArrayList<UpDown>();
		for(int i=0;i<updownList.size();i++){
			UpDown updown = updownList.get(i);
			updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
			updown.setShiftx(ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()));
			updown.setAdminname(updown.getAppvaladmin().getName());
			updownList1.add(updown);
		}
		pager.setList(updownList1);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UpDown.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	//超市领料
	public String csajlist(){
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		
		if(Param !=null){
			JSONObject jsonobject = JSONObject.fromObject(Param);
			if(jsonobject.get("pager.keyword") != null){
				pager.setProperty(jsonobject.get("pager.property").toString());
				pager.setKeyword(jsonobject.get("pager.keyword").toString());
			}
		}
		
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		
		//此处固定查询
		List<String> list = new ArrayList<String>();
		list.add("updown");//超市领料
		pager = updownservice.findByPager(pager,admin,list);
		
		List<UpDown> updownList = pager.getList();
		List<UpDown> updownList1 = new ArrayList<UpDown>();
		for(int i=0;i<updownList.size();i++){
			UpDown updown = updownList.get(i);
			updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
			updown.setShiftx(ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()));
			updown.setAdminname(updown.getAppvaladmin().getName());
			updownList1.add(updown);
		}
		pager.setList(updownList1);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UpDown.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	//查看
	public String view(){
		updown = updownservice.load(id);
		updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
		return VIEW;
	}
	
	
	//ajax下拉框
//	public String ajselector(){
//		if (pager.getOrderBy().equals("")) {
//			pager.setOrderType(OrderType.desc);
//			pager.setOrderBy("modifyDate");
//		}
//		Admin admin = adminservice.getLoginAdmin();
//		admin = adminservice.get(admin.getId());
//		
//		List<UpDown> updownList = pager.getList();
//		List<UpDown> updownList1 = new ArrayList<UpDown>();
//		for(int i=0;i<updownList.size();i++){
//			UpDown updown = updownList.get(i);
////			updown.setTypex(ThinkWayUtil.getDictValueByDictKey(dictservice, "updown", updown.getType()));
////			updown.setShiftx(ThinkWayUtil.getDictValueByDictKey(dictservice, "kaoqinClasses", updown.getShift()));
////			updown.setAdminname(updown.getAppvaladmin().getName());
//			updownList1.add(updown);
//		}
//		pager.setList(updownList1);
//		JsonConfig jsonConfig=new JsonConfig();   
//		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
//		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UpDown.class));//排除有关联关系的属性字段  
//		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
//		System.out.println(jsonArray.get(0).toString());
//		return ajaxJson(jsonArray.get(0).toString());
//	}

	

	public Pager getPager() {
		return pager;
	}
	
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLgpla() {
		return lgpla;
	}

	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}

	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}

	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}

	public UpDown getUpdown() {
		return updown;
	}

	public void setUpdown(UpDown updown) {
		this.updown = updown;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public List<UpDown> getUpdownList() {
		return updownList;
	}

	public void setUpdownList(List<UpDown> updownList) {
		this.updownList = updownList;
	}

	public String getLgplaun() {
		return lgplaun;
	}

	public void setLgplaun(String lgplaun) {
		this.lgplaun = lgplaun;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialDesp() {
		return materialDesp;
	}

	public void setMaterialDesp(String materialDesp) {
		this.materialDesp = materialDesp;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getTanum() {
		return tanum;
	}

	public void setTanum(String tanum) {
		this.tanum = tanum;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}


	public String getIsud() {
		return isud;
	}


	public void setIsud(String isud) {
		this.isud = isud;
	}


	public List<Object[]> getUpdownObjList() {
		return updownObjList;
	}


	public void setUpdownObjList(List<Object[]> updownObjList) {
		this.updownObjList = updownObjList;
	}


	public UpDownServcie getUpdownservice() {
		return updownservice;
	}


	public void setUpdownservice(UpDownServcie updownservice) {
		this.updownservice = updownservice;
	}


	public AdminService getAdminservice() {
		return adminservice;
	}


	public void setAdminservice(AdminService adminservice) {
		this.adminservice = adminservice;
	}


	public UpDownRfc getUndownrfc() {
		return undownrfc;
	}


	public void setUndownrfc(UpDownRfc undownrfc) {
		this.undownrfc = undownrfc;
	}


	public DictService getDictservice() {
		return dictservice;
	}


	public void setDictservice(DictService dictservice) {
		this.dictservice = dictservice;
	}
	
	public String getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}


	public PositionManagementService getPositionManagementService() {
		return positionManagementService;
	}


	public void setPositionManagementService(
			PositionManagementService positionManagementService) {
		this.positionManagementService = positionManagementService;
	}


	public List<String> getPositionManagementList() {
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		PositionManagement positionManagement = new PositionManagement();
		positionManagement.setFactoryUnit(admin.getTeam().getFactoryUnit());
		List<String> PositionManagementList= positionManagementService.getPositionList(positionManagement);
		return PositionManagementList;
	}


	public void setPositionManagementList(
			List<String> positionManagementList) {
		this.positionManagementList = positionManagementList;
	}


	public List<PositionManagement> getPositionManagementList1() {
		positionManagementList1 = positionManagementService.getPositionManagementList();
		return positionManagementList1;
	}


	public void setPositionManagementList1(
			List<PositionManagement> positionManagementList1) {
		this.positionManagementList1 = positionManagementList1;
	}
	
	public String trimget(){
		try{
		List<String> positionManagementList = positionManagementService.getPositionList(warehouse);
		JSONArray jsonArray = JSONArray.fromObject(positionManagementList);
		System.out.println(jsonArray.toString());
		return ajaxJson(jsonArray.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public String getWorkingBillId() {
		return workingBillId;
	}


	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}


	public List<WorkingBill> getWorkingBillList() {
		List<WorkingBill> workingBillList = new ArrayList();
		workingBillList = workingBillService.getWorkingBillList(workingBillId);
		return workingBillList;
	}


	public void setWorkingBillList(List<WorkingBill> workingBill) {
		this.workingBillList = workingBillList;
	}


	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}


	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}


	public WorkingBill getWorkingbill() {
		return workingbill;
	}


	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}


	public List<Bom> getBomList() {
		return bomList;
	}


	public void setBomList(List<Bom> bomList) {
		this.bomList = bomList;
	}


	public BomService getBomService() {
		return bomService;
	}


	public void setBomService(BomService bomService) {
		this.bomService = bomService;
	}


	public MatStockRfc getMatstockrfc() {
		return matstockrfc;
	}


	public void setMatstockrfc(MatStockRfc matstockrfc) {
		this.matstockrfc = matstockrfc;
	}


	public MaterialService getMaterialService() {
		return materialService;
	}


	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}


	public AdminService getAdminService() {
		return adminService;
	}


	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}


	public PickDetailService getPickDetailService() {
		return pickDetailService;
	}


	public void setPickDetailService(PickDetailService pickDetailService) {
		this.pickDetailService = pickDetailService;
	}


	public List<PickDetail> getPickDetailList() {
		return pickDetailList;
	}


	public void setPickDetailList(List<PickDetail> pickDetailList) {
		this.pickDetailList = pickDetailList;
	}


	public String getCharg() {
		return charg;
	}


	public void setCharg(String charg) {
		this.charg = charg;
	}

	// 字典获取
	public List<Dict> getAllDetails() {
		return dictservice.getList("dictname", "materialdetail");
	}

	public void setAllDetails(List<Dict> allDetails) {
		this.allDetails = allDetails;
	}
	


	


	
	
//	public List<String> trimWareHouseList(String warehouse){
//		List<String> positionManagementList = positionManagementService.getPositionList(warehouse);
//		JSONArray json = JSONArray.fromObject(positionManagementList);
//		return json;
//	}
	/**
	 * 创建 领/退料记录
	 * @return
	 * @throws Exception
	 */
	
	
}