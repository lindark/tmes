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
	
	private String[] strlen = {"workingBillCode","materialCode"};
	private String[] lavenlen={"随工单编号","子件编码"};
	public String list(){
		
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		nameobj.add(strlen[0]);labelobj.add(lavenlen[0]);indexobj.add(strlen[0]);
		nameobj.add(strlen[1]);labelobj.add(lavenlen[1]);indexobj.add(strlen[1]);
		
		/**处理接上班**/
		List<Process> processList00 = processservice.getAll();
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = "接上班"+process.getProcessName();
			String name ="GXJSB_"+process.getId();
			String index="GXJSB_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		/**处理接上班 end**/
		
		
//		String[] nameobj1= {"id","workingBillCode","materialCode","recipientsAmount"};
//		String[] indexobj = {"id","workingbill.workingBillCode","materialCode","recipientsAmount"};
//		String[] labelobj={"ID","随工单编号","子件编号","领用数量"};
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<nameobj.size();i++){
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("name", nameobj.get(i));
			jsonobject.put("index", indexobj.get(i));
			jsonobject.put("label", labelobj.get(i));
			jsonobject.put("width", 200);
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
			//workinginout.setWorkingBillCode(workinginout.getWorkingbill().getWorkingBillCode());
			map.put(strlen[0], workinginout.getWorkingbill().getWorkingBillCode());
			map.put(strlen[1], workinginout.getMaterialCode());
			
			for(int y=0;y<jsonarray.size();y++){
				JSONObject json = (JSONObject) jsonarray.get(y);
				String name = json.getString("name");
				int firstls = StringUtils.indexOf(name, "GXJSB_");
				if(firstls >= 0){//如果找到，表示是接上班工序交接
					String processid = StringUtils.substringAfter(name, "GXJSB_");//获取接上班ID
					String[] propertyNames = {"processid","afterworkingbill.id","materialCode"};
					String[] propertyValues={processid,workinginout.getWorkingbill().getId(),workinginout.getMaterialCode()};
					HandOverProcess handoverprocess = handoverprocessservice.get(propertyNames, propertyValues);
					if(handoverprocess == null)
						map.put("GXJSB_"+processid,"0" );//正常交接数量
					else
						map.put("GXJSB_"+processid,handoverprocess.getAmount() );//正常交接数量
				}
				
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
