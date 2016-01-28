package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ArithUtil;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

@ParentPackage("admin")
public class WorkingInoutAction extends BaseAdminAction {

	private static final long serialVersionUID = 4881226445354414940L;
	@Resource
	private ProcessService processservice;
	@Resource
	private HandOverProcessService handoverprocessservice;
	@Resource
	private WorkingInoutService workinginoutservice;
	@Resource
	private BomService bomservice;
	private Pager pager;
	private String start;
	private String end;
	private String workingBillCode;
	
	private String jsondata;
	
	private String[] strlen = {"workingBillCode","materialCode","planCount","afteroddamount","afterunoddamount","recipientsAmount","multiple","totalSingleAmount",
								"afterFraction","scrapNumber","totalRepairAmount","totalRepairinAmount","productDate","shift","aufnr","zjdwyl","dbjyhgs","beforeunoddamount","ychgl",
								"trzsl","cczsl","slcy","jhdcl"};
	private String[] lavenlen={"随工单编号","子件编码","计划数量","接上班零头数","接上班异常零头数","领用数","倍数","入库数",
								"交下班零头数","报废数","成型异常表面维修数","成型维修返回数","生产日期","班次","生产订单号","组件单位用量","当班检验合格数","交下班异常零头数","一次合格率",
								"投入总数量","产出总数量","数量差异","计划达成率"};
	public String list(){
		
		JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
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
				String workingBillCode = ThinkWayUtil.null2String(param.get("workingBillCode"));//随工单
				mapcheck.put("start", start);
				mapcheck.put("end", end);
				mapcheck.put("workingBillCode", workingBillCode);
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
		}
		JSONArray jsonstr = new JSONArray();
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		jsonstr =workinginoutservice.findInoutByJsonData(jsonarray, mapcheck, strlen);
		
		return ajaxJson(jsonstr.toString());
	}
	
	//Excel 导出
	public String excelexport(){
		JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
		
		List<String> header = new ArrayList<String>();
		for(int i=0;i<jsonarray.size();i++){
			JSONObject jsonobject = (JSONObject)jsonarray.get(i);
			String label = jsonobject.get("label").toString();
			header.add(label);
		}
		List<String[]> body = new ArrayList<String[]>();
		String[] str = {"1","2","3"};
		body.add(str);
		try {
			ExportExcel.exportExcel("ceshi", header, body, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//setResponseExcel("ceshi");//设置页面为 Excel
		return null;
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
	
	
}
