package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.UpDown;
import cc.jiuyi.sap.rfc.UpDownRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.UpDownServcie;
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
				updown.setAppvaladmin(admin1);//保存人
				updown.setProductDate(admin.getProductDate());//将生产日期和班次写入
				updown.setShift(admin.getShift());
				updownList.set(i, updown);
			}
			updownservice.save(updownList);
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




	
	
	
}