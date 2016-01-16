package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

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
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.service.HandOverProcessService;
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
	private Pager pager;
	
	private String jsondata;

	public String list(){
		
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		
		
		
		/**处理接上班**/
		List<Process> processList00 = processservice.getAll();
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = process.getProcessName();
			String name = "GXJSB_"+process.getId();
			String index="GXJSB_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 end**/

		
//		String[] nameobj1= {"id","workingBillCode","materialCode","recipientsAmount"};
//		String[] indexobj = {"id","workingbill.workingBillCode","materialCode","recipientsAmount"};
//		String[] labelobj={"ID","随工单编号","子件编号","领用数量"};
//		JSONArray jsonarray = new JSONArray();
//		for(int i=0;i<nameobj.length;i++){
//			JSONObject jsonobject = new JSONObject();
//			jsonobject.put("name", nameobj[i]);
//			jsonobject.put("index", indexobj[i]);
//			jsonobject.put("label", labelobj[i]);
//			jsonobject.put("width", 200);
//			jsonarray.add(jsonobject);
//		}
//		jsondata = jsonarray.toString();
		
		return LIST;
	}
	
	public String ajlist(){//投入产出逻辑处理
		
		pager = workinginoutservice.findByPager(pager);
		List<WorkingInout> workingInoutList = (List<WorkingInout>)pager.getList();
		for(int i=0;i<workingInoutList.size();i++){
			WorkingInout workinginout = workingInoutList.get(i);
			
		}
		
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(WorkingInout.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		
//		JSONArray jsonarray = JSONArray.fromObject(jsondata);
//		for(int i=0;i<jsonarray.size();i++){
//			JSONObject jsonobject = (JSONObject)jsonarray.get(i);
//			String index = (String)jsonobject.get("index");
//			String laststr = StringUtils.substringAfter(index, "_");
//			HandOverProcess handoverprocess = handoverprocessservice.get("processid",laststr);
//			
//		}
		return ajaxJson(jsonArray.get(0).toString());
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
