package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.impl.PickRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Reece 2016/3/2
 * 后台Action类-领/退料主表
 */

@ParentPackage("admin")
public class PickAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6045295823911487260L;
	public static Logger log = Logger.getLogger(PickAction.class);

	private static final String UNCHECK="1";//未确认
	private static final String CONFIRMED = "2";//已确认
	private static final String REPEAL = "3";//已撤销

	private Pick pick;
	// 获取所有状态
	private List<Dict> allState;
	private Admin admin;

	
	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private PickService pickService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private PickDetailService pickDetailService;
	@Resource
	private AdminService adminService;
	@Resource
	private PickRfcImpl pickRfcImple;

	private String workingBillId;
	private WorkingBill workingbill;
	private String matnr;
	private List<PickDetail> pkList;
	private List<Pick> pickRfc;
	private String cardnumber;//卡号
	private String str;//当前状态
	private String maktx;//物料编码
	private String start;//日期起始点
	private String end;//日期结束点
	private String state;//状态
	private String materialCode;//物料编码
	private String charg;//批次
	private String type;//类型

	//领退料记录 @author Reece 2016/3/3
	public String history(){
		return "history";
	}
	
	//领退料记录列表 @author Reece 2016/3/3
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
			if (obj.get("maktx") != null) {
				String maktx = obj.getString("maktx").toString();
				map.put("maktx", maktx);
			}
			if (obj.get("materialCode") != null) {
				String materialCode = obj.getString("materialCode").toString();
				map.put("materialCode", materialCode);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
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
		}
		pager = pickDetailService.historyjqGrid(pager, map);
		List<PickDetail> detailList = pager.getList();
		List<PickDetail> lst = new ArrayList<PickDetail>();
		try {
			for (int i = 0; i < detailList.size(); i++) {
				PickDetail pickDetail = detailList.get(i);
				pickDetail.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
						"pickState", pickDetail.getPick().getState()));
				if (pickDetail.getPick().getConfirmUser() != null) {
					pickDetail.setXconfirmUser(pickDetail.getPick().getConfirmUser().getName());
				}
				if (pickDetail.getPick().getCreateUser() != null) {
					pickDetail.setXcreateUser(pickDetail.getPick().getCreateUser().getName());
				}
				if (pickDetail.getPick().getMove_type() != null) {
					pickDetail.setXpickType((ThinkWayUtil.getDictValueByDictKey(
							dictService, "pickType", pickDetail.getPick().getMove_type())));
				}
				if (pickDetail.getPick().getEx_mblnr() != null) {
					pickDetail.setXmblnr(pickDetail.getPick().getEx_mblnr());
				}
				pickDetail.setProductDate(workingBillService.get(
						 pickDetail.getPick().getWorkingbill().getId()).getProductDate());
				pickDetail.setMaktx(workingBillService.get(
						 pickDetail.getPick().getWorkingbill().getId()).getMaktx());
				pickDetail.setWorkingbillCode(workingBillService.get(
						 pickDetail.getPick().getWorkingbill().getId()).getWorkingBillCode());
				pickDetail.setMatnr(workingBillService.get(
						pickDetail.getPick().getWorkingbill().getId()).getMatnr());
				lst.add(pickDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(PickDetail.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	//Excel导出 @author Reece 2016/3/3
	public String excelexport(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("materialCode", materialCode);
		map.put("maktx", maktx);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
        header.add("随工单号");
        header.add("产品编码");
        header.add("产品名称");
        header.add("生产日期");
        header.add("领退料类型");
        header.add("领退数量");
        header.add("裁切领退数");
        header.add("组件编码");
        header.add("组件描述");
        header.add("物料凭证号");
        header.add("创建日期");
        header.add("创建人");
        header.add("确认人");
        header.add("状态");
        
        List<Object[]> pickdetailList = pickDetailService.historyExcelExport(map);
        for(int i=0;i<pickdetailList.size();i++){
        	Object[] obj = pickdetailList.get(i);
        	PickDetail pickdetail = (PickDetail) obj[0];//pickdetail
        	Pick pick = (Pick)obj[1];//pick
        	WorkingBill workingbill = (WorkingBill)obj[2];//workingbill
        	
        	Object[] bodyval = {workingbill.getWorkingBillCode(),workingbill.getMatnr()
        			            ,workingbill.getMaktx(),workingbill.getProductDate()
        			            ,ThinkWayUtil.getDictValueByDictKey(dictService, "pickType", pick.getMove_type())
        						,pickdetail.getPickAmount(),pickdetail.getCqPickAmount(),pickdetail.getMaterialCode()
        						,pickdetail.getMaterialName(),pickdetail.getPick().getEx_mblnr(),pickdetail.getCreateDate(),pick.getCreateUser()==null?"":pick.getCreateUser().getName()
        						,pick.getConfirmUser()==null?"":pick.getConfirmUser().getName(),ThinkWayUtil.getDictValueByDictKey(dictService, "pickState", pick.getState())};
        	body.add(bodyval);
        }
		
		try {
			String fileName = "领退料记录表"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("领退料记录表", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
		    getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// 添加
	public String add() {
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
		/*admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行领料操作!");
			return ERROR;
		}	*/	
		this.workingbill = workingBillService.get(workingBillId);	
		
		/*if(this.workingbill.getIsHand().equals("Y")){
			addActionError("当日交接已完成，领退料不能使用");
			return ERROR;
		}*/
		
		
        List<PickDetail> pickDetailList = new ArrayList<PickDetail>();
		//根据随工单找出所有的除了未确认的领料主表对象
		List<Pick> pickList = pickService.getPickByWorkingbillId(workingBillId);
		for (int i = 0; i < pickList.size(); i++) {
			Pick pick = pickList.get(i);
			if(pick.getState()=="3"){
				//撤销且与SAP交互的和已经确认的全部取出来
				pickList.remove(pick);			
			}	
		}
		
		//取出所有的主表的从表
		for (int i = 0; i < pickList.size(); i++) {
			Pick pick = pickList.get(i);
			List<PickDetail> pkdList = new ArrayList<PickDetail>(
					pick.getPickDetail());
			for (int j = 0; j < pkdList.size(); j++) {
				PickDetail pickDetail = pkdList.get(j);
				pickDetailList.add(pickDetail);
			}
		}

		String stamp = "";
		String temp="";
		Double sum = 0d;
		str = "";
		Collections.sort(pickDetailList);// 对 pickDetailList进行排序

		//List<String> matercodeList = new ArrayList<String>();
		for(int i=0; i <pickDetailList.size();i++){
			PickDetail pickdetail = pickDetailList.get(i);
			
			if(!temp.equals(pickdetail.getMaterialCode())){
				temp = pickdetail.getMaterialCode();
			}else{
				continue;
			}
					
			for(int y=0;y<pickDetailList.size();y++){
				PickDetail pickdetail1 = pickDetailList.get(y);
				if(pickdetail1.getMaterialCode().equals(pickdetail.getMaterialCode())){
					if("261".equals(pickdetail1.getPickType())){//领料
						sum += Double.parseDouble(pickdetail1.getPickAmount());
					}else{//退料
						sum -= Double.parseDouble(pickdetail1.getPickAmount());
					}
				}
			}
			
			if(!stamp.equals(pickdetail.getMaterialCode())){
				stamp = pickdetail.getMaterialCode();
				str+=pickdetail.getMaterialName()+":";
				str+=sum+"</br>";
			}
			
			sum=0d;
		}
	
		return LIST;
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

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
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
		}
		pager = pickService.getPickPager(workingBillId,pager, map);
		List<Pick> pickList = pager.getList();
		List<Pick> lst = new ArrayList<Pick>();
		for (int i = 0; i < pickList.size(); i++) {
			Pick pick = (Pick) pickList.get(i);
			pick.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
					"pickState", pick.getState()));
			if (pick.getConfirmUser() != null) {
				pick.setXconfirmUser(pick.getConfirmUser().getName());
			}
			if (pick.getCreateUser() != null) {
				pick.setXcreateUser(pick.getCreateUser().getName());
			}
			if (pick.getMove_type() != null) {
				pick.setMove_type(ThinkWayUtil.getDictValueByDictKey(
						dictService, "pickType", pick.getMove_type()));
			}
			lst.add(pick);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Pick.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		pickService.updateisdel(ids, "Y");
		for (String id : ids) {
			Pick pick = pickService.load(id);
		}
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		pick = pickService.load(id);
		return INPUT;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		Pick persistent = pickService.load(id);
		BeanUtils.copyProperties(pick, persistent, new String[] { "id",
				"createDate", "modifyDate" });
		pickService.update(persistent);
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		pickService.save(pick);
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	// 刷卡确认
	public String creditapproval() {
		
		try {
		ids = id.split(",");
		String message = "";
		String pickId = "";
		List<Pick> list = pickService.get(ids);
		admin = adminService.getByCardnum(cardnumber);
		
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
		if(!ThinkWayUtil.isPass(admin)){
			return ajaxJsonErrorMessage("您当前未上班,不能进行退领料操作!");			
		}
		
		List<PickDetail> pickdetailList = new ArrayList<PickDetail>();
		List<Pick> pickList = new ArrayList<Pick>();
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			pickId = pick.getId();
			if (REPEAL.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
			if (CONFIRMED.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已确认的不需要再次确认!");
			}
			if(pick.getEx_mblnr() != null && !"".equals(pick.getEx_mblnr())){
				continue;
			}
			pickList.add(pick);
			pkList = pickDetailService.getPickDetail(pickId);
			for (int j = 0; j < pkList.size(); j++) {
				PickDetail pickDetail = pkList.get(j);
				pickDetail.setXh(pickId);
				pickdetailList.add(pickDetail);
			}
		}
		
			Boolean flag = true;
			pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", pickList,
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
				List <Pick> pickRfclist = new ArrayList<Pick>();
				pickRfclist = pickRfcImple.BatchMaterialDocumentCrt("", pickList,
						pickdetailList);
				for (Pick pick2 : pickRfclist) {
					String e_type = pick2.getE_type();
					String e_message = pick2.getE_message();
					String ex_mblnr = pick2.getEx_mblnr();
					// String move_type = pick2.getMove_type();
					if (e_type.equals("E")) { // 如果有一行发生了错误
						flag = false;
						message += pick2.getE_message();
					} else{
						log.info("id="+pick2.getId()+"---ex_mblnr-----------"+ex_mblnr);
						if(ex_mblnr==null || "".equals(ex_mblnr)){
							flag = false;
							message += pick2.getWorkingbill().getWorkingBillCode()+"未返回凭证;";
						}else{
							Pick pickReturn = pickService.get(pick2.getId());
							pickReturn.setE_message(e_message);
							pickReturn.setEx_mblnr(ex_mblnr);
							pickReturn.setE_type(e_type);
							// pickReturn.setMove_type(move_type);
							pickReturn.setState(CONFIRMED);
							pickReturn.setConfirmUser(admin);
							HashMap<String, Object> map = new HashMap<String, Object>();
							pickDetailService.updatePIckAndWork(pickReturn, map);
							/**监听日志**/
							List<PickDetail> lst = new ArrayList<PickDetail>(pickReturn.getPickDetail());
							for (int j = 0; j < lst.size(); j++) {
								PickDetail pld = lst.get(j);
								log.info("更新类型为:"+pld.getPickType()+"mlnbr="+pickReturn.getEx_mblnr()+"更新值为:"+pld.getPickAmount());	//modify 2016/3/17	
							}
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
	}

	// 刷卡撤销
	public synchronized String creditundo() {
		String str="";
		admin = adminService.getByCardnum(cardnumber);
		
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
		if(!ThinkWayUtil.isPass(admin)){
			return ajaxJsonErrorMessage("您当前未上班,不能进行退领料操作!");			
		}
		
		ids = id.split(",");
		String message = "";
		String pickId="";
		List<Pick> list = pickService.get(ids);
		List<PickDetail> pickdetailList=new ArrayList<PickDetail>();
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			if(str.equals("")){
				str=pick.getState();
			}else if(!pick.getState().equals(str)){
				return ajaxJsonErrorMessage("请选择同一状态的记录进行撤销!"); 
			}
		}	
		
		for (int i = 0; i < list.size(); i++) {
			Pick pick = list.get(i);
			pickId=pick.getId();
			//已经撤销的不用再撤销
			if (REPEAL.equals(pick.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
			//未确认的不调用接口
			if(UNCHECK.equals(pick.getState())){
				str="1";
			}	
			//已确认的调用SAP接口
			if(CONFIRMED.equals(pick.getState())){
				str="2";
//				return ajaxJsonErrorMessage("已确认的不可以再撤销!");
           }
		}

		if (str.equals("1")) {
			for (int i = 0; i < list.size(); i++) {
				Pick pick = list.get(i);
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
				pick.setState(REPEAL);
//				pick.setConfirmUser(admin);
				pick.setRevokedUser(admin.getName());
				pick.setRevokedTime(time);
				pick.setRevokedUserCard(admin.getCardNumber());
				pick.setRevokedUserId(admin.getId());
				pickService.update(pick);
				//pickDetailService.updatePIckAndWork(pick);//往投入产出表
			}
			return ajaxJsonSuccessMessage("您的操作已成功!");
		}
		
		List<Pick> listsap = new ArrayList<Pick>();
		if (str.equals("2")) {
			for (int i = 0; i < list.size(); i++) {
				Pick pick = list.get(i);
				if (!"262".equals(pick.getMove_type())) {
					pick.setMove_type("262");
				} else {
					pick.setMove_type("261");
				}

				listsap.add(pick);

				pkList = pickDetailService.getPickDetail(pick.getId());
				List<Pick> list1 = pickService.get(ids);
				for (int j = 0; j < pkList.size(); j++) {
					PickDetail pickDetail = pkList.get(j);
					pickDetail.setXh(pick.getId());
					pickdetailList.add(pickDetail);
				}
			}
		}
			try {
				Boolean flag = true;
				pickRfc = pickRfcImple.BatchMaterialDocumentCrt("X", listsap,
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
					pickRfc = new ArrayList<Pick>();
					pickRfc = pickRfcImple.BatchMaterialDocumentCrt("", listsap,pickdetailList);
					for (Pick pick2 : pickRfc) {
						String e_type = pick2.getE_type();
						String e_message = pick2.getE_message();
						String ex_mblnr = pick2.getEx_mblnr();
						//String move_type = pick2.getMove_type();
						if (e_type.equals("E")) { // 如果有一行发生了错误
							flag = false;
							message += pick2.getE_message();
						} else {
							log.info("ex_mblnr-----------"+ex_mblnr);
							if(ex_mblnr==null || "".equals(ex_mblnr)){
								flag = false;
								message += pick2.getWorkingbill().getWorkingBillCode()+"未返回凭证;";
							}else{
								Pick pickReturn = pickService.get(pick2.getId());
								pickReturn.setE_message(e_message);
								pickReturn.setEx_mblnr(ex_mblnr+"/"+pickReturn.getEx_mblnr());
								pickReturn.setE_type(e_type);
								//pickReturn.setMove_type(move_type);
								Date date = new Date(); 
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
								String time = dateFormat.format(date); 
								pickReturn.setState(REPEAL);
//								pickReturn.setConfirmUser(admin);
								pickReturn.setRevokedUser(admin.getName());
								pickReturn.setRevokedTime(time);
								pickReturn.setRevokedUserCard(admin.getCardNumber());
								pickReturn.setRevokedUserId(admin.getId());
								pickDetailService.updatePIckAndWork(pickReturn);
								/**监听日志**/
								List<PickDetail> lst = new ArrayList<PickDetail>(pickReturn.getPickDetail());
								for (int j = 0; j < lst.size(); j++) {
									PickDetail pld = lst.get(j);
									log.info("撤销!!!!更新类型为:"+pld.getPickType()+"更新值为:"+pld.getPickAmount());	//modify 2016/3/17	
								}
							}
						}
					}
					if (!flag)
						return ajaxJsonErrorMessage(message);
				}
				//自动创建一条领料信息
			} catch (IOException e) {
				e.printStackTrace();
				return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
			} catch (Exception e) {
				e.printStackTrace();
				return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
			}
		return ajaxJsonSuccessMessage("您的操作已成功!");
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

	public Pick getPick() {
		return pick;
	}

	public void setPick(Pick pick) {
		this.pick = pick;
	}

	public PickService getPickService() {
		return pickService;
	}

	public void setPickService(PickService pickService) {
		this.pickService = pickService;
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

	public PickRfcImpl getPickRfcImple() {
		return pickRfcImple;
	}

	public void setPickRfcImple(PickRfcImpl pickRfcImple) {
		this.pickRfcImple = pickRfcImple;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public List<PickDetail> getPkList() {
		return pkList;
	}

	public void setPkList(List<PickDetail> pkList) {
		this.pkList = pkList;
	}

	public List<Pick> getPickRfc() {
		return pickRfc;
	}

	public void setPickRfc(List<Pick> pickRfc) {
		this.pickRfc = pickRfc;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
