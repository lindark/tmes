package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ReportTxt;
import cc.jiuyi.util.ThinkWayUtil;

@ParentPackage("admin")
public class WorkingInoutAction extends BaseAdminAction {

	private static final long serialVersionUID = 4881226445354414940L;
	@Resource
	private WorkingInoutService workinginoutservice;
	
	private Pager pager;
	private String start;
	private String end;
	private String workingBillCode;	
	private String wbid;
	private String xFactoryUnit;
	private String info;
	
	private String jsondata;
	
	private String[] strlen = {"workingbill.workingBillCode","materialCode","planCount","afteroddamount","afterunoddamount","recipientsAmount","multiple","totalSingleAmount",
								"afterFraction","scrapNumber","totalRepairAmount","totalRepairinAmount","workingbill.productDate","shift","workingbill.aufnr","zjdwyl","dbjyhgs","beforeunoddamount","ychgl",
								"trzsl","cczsl","slcy","jhdcl","workingbill.matnr","workingbill.maktx","materialName","totalAmount","isHand","jycl","workingbill.workcenter","teamCode","teamName",
								"bulkProductMount","needAttendance","actulAttendance","zhuren","fuzhuren","minister","deputy","workHours"};
	private String[] lavenlen={"随工单编号","子件编码","计划数量","接/零头","接/零头/返","领用","倍数","入库",
								"交/零头","报废","返修发货","返修收货","生产日期","班次","生产订单号","用量","生产数","交/零头/返","一次合格率",
								"投入汇总","产出汇总","差异","计划达成率","物料编码","物料描述","组件描述","报工","单据状态","校验差异","单元",
								"班组编码","班组名称","待包装数量","应出勤人数","实出勤人数","主任","副主任","部长","副总","工作小时"};
	public String list(){
		
		Integer[] sunxulen={};
		
		JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
//		for(int i=0;i<jsonarray.size();i++){
//			JSONObject jo = jsonarray.getJSONObject(i);
//			jo.getString("name");
//		}
		jsondata = jsonarray.toString();
		return LIST;
	}
	
	public String ajlist(){//投入产出逻辑处理
		HashMap<String,String> mapcheck = new HashMap<String,String>();
		if(Param != null){//普通搜索功能
			if(!Param.equals("")){
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject param = JSONObject.fromObject(Param);
				String start = ThinkWayUtil.null2String(param.get("start"));
				String end = ThinkWayUtil.null2String(param.get("end"));
				String xFactoryUnit = ThinkWayUtil.null2String(param.get("info"));
				String workingBillCode = ThinkWayUtil.null2String(param.get("workingBillCode"));//随工单
				mapcheck.put("start", start);
				mapcheck.put("end", end);
				mapcheck.put("workingBillCode", workingBillCode);
				mapcheck.put("xFactoryUnit", xFactoryUnit);
			} 
		}else{
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String d = sdf.format(date);
				start = d;
				end = d;
			String startDate = ThinkWayUtil.null2String(start);
			String endDate = ThinkWayUtil.null2String(end);
			String wbc = ThinkWayUtil.null2String(workingBillCode);//随工单
			mapcheck.put("start", startDate);
			mapcheck.put("end", endDate);
			mapcheck.put("workingBillCode", wbc);
			mapcheck.put("xFactoryUnit", "");
		}
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		pager = workinginoutservice.listjqGrid(pager,mapcheck,jsonarray, strlen);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig
				.setExcludes(ThinkWayUtil.getExcludeFields(WorkingInout.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
		/*JSONArray jsonstr = new JSONArray();
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		jsonstr =workinginoutservice.findInoutByJsonData(jsonarray, mapcheck, strlen,1);
		return ajaxJson(jsonstr.toString());*/
	}
	
	//Excel 导出
	public String excelexport(){
		JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		for(int i=0;i<jsonarray.size();i++){
			JSONObject jsonobject = (JSONObject)jsonarray.get(i);
			String label = jsonobject.get("label").toString();
			header.add(label);
		}
		
		HashMap<String,String> mapcheck = new HashMap<String,String>();
		mapcheck.put("start", start);
		mapcheck.put("end", end);
		mapcheck.put("workingBillCode", workingBillCode);
		if(info==null){
			info ="";
		}
		mapcheck.put("xFactoryUnit", info);
		
		JSONArray jsonstr =workinginoutservice.findInoutByJsonData(jsonarray, mapcheck, strlen,1);
		
		for(int i=0;i<jsonstr.size();i++){
			JSONObject jsonobject = (JSONObject) jsonstr.get(i);
			Object[] str = new Object[header.size()];
			for(int y=0;y<jsonarray.size();y++){
				JSONObject jsonobj = (JSONObject) jsonarray.get(y);
				String index = jsonobj.get("index").toString();
				Object obj = jsonobject.get(index);//根据header 获取 内容
				str[y] = obj;
			}
			body.add(str);
		}
		
		/***Excel 下载****/
		try {
			//String path = getRequest().getSession().getServletContext().getRealPath("");//获取路径
			//path+="/excel/投入产出.xls";
			String fileName="投入产出表"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("投入产出表", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Excel 导出
	public String excelexport1(){
		//long starttime = new Date().getTime();
		List<String[]> processList = workinginoutservice.findProcess();
 		List<String[]> bodys = workinginoutservice.sumAmount(workingBillCode,info,start,end,processList);
 		String[] strarry = new String[]{};
 		if(bodys!=null && bodys.size()>0){
 			Object[] objs = bodys.get(0);
 			List<String> strList = new ArrayList<String>();
 			strList.add("单元");strList.add("班组");strList.add("主任");strList.add("副主任");strList.add("生产日期");strList.add("班次");strList.add("生产订单号");
 			strList.add("物料编码");strList.add("物料描述");strList.add("子件编码");strList.add("组件描述");strList.add("用量");strList.add("计划数量");strList.add("领用");
 			if(processList!=null&&processList.size()>0){
 	 			int k = processList.size();
 	 			for(int i = 0;i<k;i++){
 	 				Object[] arry = processList.get(i);
 					String karry = arry[1].toString();
 					strList.add("接/"+karry);strList.add("接/"+karry+"/返");
 	 				
 	 			}
 	 			for(int i = 0;i<k;i++){
 	 				Object[] arry = processList.get(i);
 					String karry = arry[1].toString();
 					strList.add("交/"+karry);strList.add("交/"+karry+"/返");
 	 				
 	 			}
 	 			strList.add("报废");strList.add("接/零头");strList.add("接/零头/返");strList.add("入库");strList.add("交/零头");strList.add("交/零头/返");strList.add("返修发货");strList.add("返修收货");
 	 			strList.add("生产数");strList.add("一次合格率");strList.add("投入汇总");strList.add("产出汇总");strList.add("差异");strList.add("计划达成率");strList.add("单据状态");
 	 			strList.add("报工");strList.add("检验差异");strList.add("应出勤人数");strList.add("实出勤人数");
 	 			strarry =  strList.toArray( new String[0] );
 			}else{
 	 			strarry = new String[]{
 	 	 				"单元","班组","主任","副主任","生产日期","班次","生产订单号","物料编码","物料描述","子件编码","组件描述","用量","计划数量","领用","报废",
 	 	 				"接/零头","接/零头/返","入库","交/零头","交/零头/返","返修发货","返修收货","生产数","一次合格率","投入汇总","产出汇总","差异"
 	 	 				,"计划达成率","单据状态","报工","检验差异","应出勤人数","实出勤人数"
 	 	 		};
 	 		}
 		}else{
 			strarry = new String[]{
 	 				"单元","班组","主任","副主任","生产日期","班次","生产订单号","物料编码","物料描述","子件编码","组件描述","用量","计划数量","领用","报废","接/零头","接/零头/返","入库","交/零头","交/零头/返","返修发货","返修收货","生产数","一次合格率","投入汇总","产出汇总","差异"
 	 				,"计划达成率","单据状态","报工","检验差异","应出勤人数","实出勤人数"
 	 		};
 		}
 		List<String[]> body = new ArrayList<String[]>();
 		body.add(strarry);
 		body.addAll(bodys);
		long sendtime = new Date().getTime();
		//System.out.println("-----1---"+(sendtime-starttime));
		//starttime = new Date().getTime();
		ReportTxt r = new ReportTxt();
		r.txt(body);
		//starttime = new Date().getTime();
		//System.out.println("----2----"+(sendtime-starttime));

		
		return null;
	}

	
	/**
	 * 获取数据前
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 * @return
	 */
	public String beforegetwbinoutput()
	{
		wbid=id;
		JSONArray jsonarray = workinginoutservice.showInoutJsonData1(strlen,lavenlen);
		jsondata = jsonarray.toString();
		return "ioput";
	}
	
	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 * @return
	 */
	public String getwbinoutput(){
		JSONArray jsonstr = new JSONArray();
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		HashMap<String,String> mapcheck = new HashMap<String,String>();
		mapcheck.put("wbid", wbid);
		jsonstr =workinginoutservice.findInoutByJsonData(jsonarray, mapcheck, strlen,2);
		return ajaxJson(jsonstr.toString());
	}

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
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

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	public String getWbid(){
		return wbid;
	}

	public void setWbid(String wbid)
	{
		this.wbid = wbid;
	}

	public String getxFactoryUnit() {
		return xFactoryUnit;
	}

	public void setxFactoryUnit(String xFactoryUnit) {
		this.xFactoryUnit = xFactoryUnit;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
