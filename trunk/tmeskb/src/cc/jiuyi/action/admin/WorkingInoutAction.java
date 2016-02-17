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

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ExportExcel;
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
		jsonstr =workinginoutservice.findInoutByJsonData(jsonarray, mapcheck, strlen,1);
		
		return ajaxJson(jsonstr.toString());
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
		
		
		try {
			String path = getRequest().getSession().getServletContext().getRealPath("");//获取路径
			path+="/excel/投入产出.xls";
			ExportExcel.exportExcel("投入产出表导出", header, body, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//setResponseExcel("ceshi");//设置页面为 Excel
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
		JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
		jsondata = jsonarray.toString();
		return "ioput";
	}
	
	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 * @return
	 */
	public String getwbinoutput()
	{
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

	public String getWbid()
	{
		return wbid;
	}

	public void setWbid(String wbid)
	{
		this.wbid = wbid;
	}
}
