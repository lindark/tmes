package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingBillService;
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
	@Resource
	private WorkingBillService wbService;
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
	
	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 * @return
	 */
	public String getwbinoutput()
	{
		JSONArray jsonstr = new JSONArray();
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		try{	
			List<WorkingInout> workingInoutList = workinginoutservice.findWbinoutput(wbid);
			for(int i=0;i<workingInoutList.size();i++){
				JSONObject map = new JSONObject();
				WorkingInout workinginout = workingInoutList.get(i);
				WorkingBill workingbill = workinginout.getWorkingbill();
				String aufnr = workingbill.getAufnr();
				map.put(strlen[0], workingbill.getWorkingBillCode());
				map.put(strlen[1], workinginout.getMaterialCode());
				map.put(strlen[2], workingbill.getPlanCount());
				map.put(strlen[3], workingbill.getAfteroddamount());//接上班零头数
				map.put(strlen[4], workingbill.getAfterunoddamount());//接上班异常零头数
				map.put(strlen[5], workinginout.getRecipientsAmount());//领用数
				map.put(strlen[7],workingbill.getTotalSingleAmount());//入库数
				map.put(strlen[8],workingbill.getBeforeoddamount());//交下班零头数
				map.put(strlen[17],workingbill.getBeforeunoddamount());//交下班异常零头数
				map.put(strlen[9],workinginout.getScrapNumber());//报废数
				map.put(strlen[10],workingbill.getTotalRepairAmount());//返修数量
				map.put(strlen[11],workingbill.getTotalRepairinAmount());//返修收货数量
				map.put(strlen[12],workingbill.getProductDate());//生产日期
				map.put(strlen[13],workingbill.getWorkingBillCode().substring(workingbill.getWorkingBillCode().length()-2,workingbill.getWorkingBillCode().length()));//班次
				map.put(strlen[14],workingbill.getAufnr());//生产订单号
				
				List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(), workinginout.getMaterialCode(), workingbill.getWorkingBillCode());
				Double bomamount = 0.00d;
				for(Bom bom :bomList){
					bomamount +=bom.getMaterialAmount();
				}
				map.put(strlen[6],ArithUtil.round(ArithUtil.div(workingbill.getPlanCount(),bomamount),2));//倍数 = 随工单计划数量 / bom数量  保留2位小数
				
				Double dwyl = ArithUtil.round(ArithUtil.div(bomamount, workingbill.getPlanCount()), 2);//单位用量
				map.put(strlen[15],dwyl);//组件单位用量 = BOM需求数量  / 随工单计划数量 保留2位小数
				Double afteroddamount = ThinkWayUtil.null2o(workingbill.getAfteroddamount());//接上班零头数
				Double afterunoddamount = ThinkWayUtil.null2o(workingbill.getAfterunoddamount());//接上班异常零头数
				Double totalsingleamount = ThinkWayUtil.null2o(workingbill.getTotalSingleAmount());//入库数
				Double beforeoddamount = ThinkWayUtil.null2o(workingbill.getBeforeoddamount());//交下班零头数
				Double beforeunoddamount = ThinkWayUtil.null2o(workingbill.getBeforeunoddamount());//交下班异常零头数
				Double dbjyhgs = ArithUtil.sub(ArithUtil.sub(ArithUtil.add(ArithUtil.add(afteroddamount, afterunoddamount),totalsingleamount),beforeoddamount),beforeunoddamount);//当班检验合格数 = 接上班零头数 + 接上班异常零头数 + 入库数 - 交下班零头数 - 交下班异常零头数
				map.put(strlen[16],dbjyhgs);//当班检验合格数
				map.put(strlen[18],"");//一次合格率 TODO 此处计算一次合格率，现在无法计算
				
				Double trzsl = 0.00d;
				Double cczsl = 0.00d;
				trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(workinginout.getRecipientsAmount()));//领用数
				trzsl = ArithUtil.sub(trzsl, ThinkWayUtil.null2o(workinginout.getScrapNumber()));//报废数
				
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalSingleAmount()));//入库数
				cczsl = ArithUtil.add(cczsl,ThinkWayUtil.null2o(workingbill.getTotalRepairinAmount()));//返修收货数量
				cczsl = ArithUtil.round(ArithUtil.mul(cczsl, dwyl),2);
				
				for(int y=0;y<jsonarray.size();y++){
					JSONObject json = (JSONObject) jsonarray.get(y);
					String name = json.getString("name");
					int firstls = StringUtils.indexOf(name, "GXJSBZC_");
					int firstls00 = StringUtils.indexOf(name, "GXJXBZC_");
					if(firstls >= 0){//如果找到，表示是接上班工序交接
						String processid = StringUtils.substringAfter(name, "GXJSBZC_");//获取接上班ID
						String[] propertyNames = {"processid","afterworkingbill.id","materialCode"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
						HandOverProcess handoverprocess = handoverprocessservice.get(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(handoverprocess != null){
							zcjjsl = handoverprocess.getAmount();//正常交接数量
							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
							map.put("GXJSBZC_"+processid,"0" );//正常交接数量
							map.put("GXJSBFX_"+processid, "0");//返修交接数量
						}
						map.put("GXJSBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJSBFX_"+processid,fxjjsl);//返修交接数量
						
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.add(trzsl, ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					if(firstls00>=0){//交下班
						String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
						String[] propertyNames = {"processid","beforworkingbill.id","materialCode"};
						String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
						HandOverProcess handoverprocess = handoverprocessservice.get(propertyNames, propertyValues);
						Double zcjjsl = 0.00d;
						Double fxjjsl = 0.00d;
						if(handoverprocess != null){
							zcjjsl = handoverprocess.getAmount();//正常交接数量
							fxjjsl = handoverprocess.getRepairAmount();//返修交接数量
						}
						map.put("GXJXBZC_"+processid,zcjjsl);//正常交接数量
						map.put("GXJXBFX_"+processid,fxjjsl);//返修交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(zcjjsl));//投入:正常交接数量
						trzsl = ArithUtil.sub(trzsl,ThinkWayUtil.null2o(fxjjsl));//投入:返修交接数量
					}
					//int firstls1 = StringUtils
					
				}
				
				map.put(strlen[19],trzsl);//投入总数量 = 领用数 + 接上班正常和返修数量
				map.put(strlen[20],cczsl);//产出总数量 = (入库数 + 返修收货数量)*单位用量  保留2位小数
				map.put(strlen[21],ArithUtil.sub(trzsl, cczsl));//数量差异= 投入总数量 - 产出总数量
				Double jhdcl = ArithUtil.round(ArithUtil.div(dbjyhgs, workingbill.getPlanCount())*100,2);//计划达成率
				map.put(strlen[22],jhdcl+"%");//计划达成率 = 当班检验合格数 / 计划数  
				jsonstr.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
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