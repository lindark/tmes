package cc.jiuyi.action.admin;

import java.util.ArrayList;
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
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingInoutService;
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
	private OddHandOverService oddhandoverservice;
	private Pager pager;
	
	private String jsondata;
	
	private String[] strlen = {"workingBillCode","materialCode","planCount","actualBomMount","afterunoddamount","recipientsAmount","multiple","totalSingleAmount",
								"afterFraction"};
	private String[] lavenlen={"随工单编号","子件编码","计划数量","包装零头数(接上班)","抽包异常零头数(接上班)","领用数","倍数","入库数",
								"交下班零头数"};
	public String list(){
		
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		nameobj.add(strlen[0]);labelobj.add(lavenlen[0]);indexobj.add(strlen[0]);
		nameobj.add(strlen[1]);labelobj.add(lavenlen[1]);indexobj.add(strlen[1]);
		nameobj.add(strlen[2]);labelobj.add(lavenlen[2]);indexobj.add(strlen[2]);
		nameobj.add(strlen[3]);labelobj.add(lavenlen[3]);indexobj.add(strlen[3]);
		nameobj.add(strlen[4]);labelobj.add(lavenlen[4]);indexobj.add(strlen[4]);
		nameobj.add(strlen[5]);labelobj.add(lavenlen[5]);indexobj.add(strlen[5]);
		nameobj.add(strlen[6]);labelobj.add(lavenlen[6]);indexobj.add(strlen[6]);
		nameobj.add(strlen[7]);labelobj.add(lavenlen[7]);indexobj.add(strlen[7]);
		nameobj.add(strlen[8]);labelobj.add(lavenlen[8]);indexobj.add(strlen[8]);
		
		List<Process> processList00 = processservice.getAll();
		/**处理接上班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(正常)";
			String name ="GXJSBZC_"+process.getId();
			String index="GXJSBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (正常)end**/
		/**处理交下班(正常)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(正常)";
			String name ="GXJXBZC_"+process.getId();
			String index="GXJXBZC_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班(正常)end**/
		
		
		/**处理接上班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName()+"(返修)";
			String name ="GXJSBFX_"+process.getId();
			String index="GXJSBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 (返修)end**/
		/**处理交下班(返修)**/
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "交下班"+process.getProcessName()+"(返修)";
			String name ="GXJXBFX_"+process.getId();
			String index="GXJXBFX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理交下班 (返修)end**/
		
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<nameobj.size();i++){
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("name", nameobj.get(i));
			jsonobject.put("index", indexobj.get(i));
			jsonobject.put("label", labelobj.get(i));
			jsonobject.put("width", 150);
			jsonobject.put("fixed", true);
			jsonarray.add(jsonobject);
		}
		jsondata = jsonarray.toString();
		
		return LIST;
	}
	
	public String ajlist(){//投入产出逻辑处理
		
		JSONArray jsonstr = new JSONArray();
		
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		List<WorkingInout> workingInoutList = workinginoutservice.getAll();
		try{
		for(int i=0;i<workingInoutList.size();i++){
			JSONObject map = new JSONObject();
			WorkingInout workinginout = workingInoutList.get(i);
			WorkingBill workingbill = workinginout.getWorkingbill();
			//workinginout.setWorkingBillCode(workinginout.getWorkingbill().getWorkingBillCode());
			map.put(strlen[0], workingbill.getWorkingBillCode());
			map.put(strlen[1], workinginout.getMaterialCode());
			map.put(strlen[2], workingbill.getPlanCount());
			map.put(strlen[3], workingbill.getAfteroddamount());//接上班包装数
			map.put(strlen[4], workingbill.getAfterunoddamount());//接上班异常包装数
			map.put(strlen[5], workinginout.getRecipientsAmount());//领用数
			map.put(strlen[6], workinginout.getMultiple());//倍数
			map.put(strlen[7],workingbill.getTotalSingleAmount());//入库数
			map.put(strlen[8],workinginout.getBeforeFraction());//交下班零头数
			//map.put(strlen[3], );
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
					if(handoverprocess == null){
						map.put("GXJSBZC_"+processid,"0" );//正常交接数量
						map.put("GXJSBFX_"+processid, "0");//返修交接数量
					}else{
						map.put("GXJSBZC_"+processid,handoverprocess.getAmount() );//正常交接数量
						map.put("GXJSBFX_"+processid,handoverprocess.getRepairAmount());//返修交接数量
					}
				}
				if(firstls00>=0){//交下班
					String processid = StringUtils.substringAfter(name, "GXJXBZC_");//获取交下班ID
					String[] propertyNames = {"processid","beforworkingbillCode.id","materialCode"};
					String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
					HandOverProcess handoverprocess = handoverprocessservice.get(propertyNames, propertyValues);
					if(handoverprocess == null){
						map.put("GXJXBZC_"+processid, "0");//正常交下班数量
						map.put("GXJXBFX_"+processid, "0");//返修交接数量
					}else{
						map.put("GXJXBZC_"+processid,handoverprocess.getAmount() );//正常交接数量
						map.put("GXJXBFX_"+processid,handoverprocess.getRepairAmount());//返修交接数量
					}
				}
				//int firstls1 = StringUtils
				
			}
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
	
	
}
