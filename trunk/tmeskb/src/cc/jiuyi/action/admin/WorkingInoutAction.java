package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Process;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.ProcessService;

@ParentPackage("admin")
public class WorkingInoutAction extends BaseAdminAction {

	private static final long serialVersionUID = 4881226445354414940L;
	@Resource
	private ProcessService processservice;
	@Resource
	private HandOverProcessService handoverprocessservice;
	
	private String jsondata;

	public String list(){
		List<String> nameobj = new ArrayList<String>();
		List<String> labelobj = new ArrayList<String>();
		List<String> indexobj = new ArrayList<String>();
		List<Process> processList00 = processservice.getAll();
		for(int i=0;i<processList00.size();i++){
			Process process = processList00.get(i);
			String label = process.getProcessName();
			String name = "GX_"+process.getId();
			String index="GX_"+process.getId();
			indexobj.add(index);
			nameobj.add(name);
			labelobj.add(label);
		}
		
		
		
		//String[] nameobj = {"id","workingBillCode","matnr","maktx","productDate","planCount"};
		//String[] indexobj= {"id","workingBillCode","matnr","maktx","productDate","planCount"};
		//String[] labelobj= {"ID","随工单编号","物料号","物料描述","生产日期","数量"};
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<nameobj.size();i++){
			String name = nameobj.get(i);
			String index = indexobj.get(i);
			String label = labelobj.get(i);
			JSONObject jsonobject00 = new JSONObject();
			jsonobject00.put("name", name);
			jsonobject00.put("index", index);
			jsonobject00.put("label", label);
			//jsonobject00.put("sorttype", value);
			jsonobject00.put("width", 200);
			jsonarray.add(jsonobject00);
		}
		System.out.println(jsonarray.toString());
		
		jsondata = jsonarray.toString();
		return LIST;
	}
	
	public String ajlist(){//投入产出逻辑处理
		
		JSONArray jsonarray = JSONArray.fromObject(jsondata);
		for(int i=0;i<jsonarray.size();i++){
			JSONObject jsonobject = (JSONObject)jsonarray.get(i);
			String index = (String)jsonobject.get("index");
			String laststr = StringUtils.substringAfter(index, "_");
			HandOverProcess handoverprocess = handoverprocessservice.get("processid",laststr);
			
		}
		
		
		
		return null;
	}
	

	public String getJsondata() {
		return jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	
	
}
