package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Process;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class ProcessAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Process process;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private ProcessService processService;
	@Resource
	private DictService dictService;
	
	//是否已存在ajax验证
	public String checkProcesssCode(){
		String processCode=process.getProcessCode();
		if(processService.isExistByProccessCode(processCode)){
			return ajaxText("false");
		}else{
			return ajaxText("true");
		}
	}
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
//		List<Process> processList = pager.getList();
//		for (Process process1 : processList) {
//			process1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"processState", process1.getState()));
//		}
		//dictService.getDictValueByDictKey("processState", process.getState());
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("processCode") != null) {
				System.out.println("obj=" + obj);
				String processCode = obj.getString("processCode").toString();
				map.put("processCode", processCode);
			}
			if (obj.get("processName") != null) {
				String processName = obj.getString("processName").toString();
				map.put("processName", processName);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = processService.getProcessPager(pager, map);
			List<Process> processList = pager.getList();
			List<Process> lst = new ArrayList<Process>();
			for (int i = 0; i < processList.size(); i++) {
				Process process = (Process) processList.get(i);
				process.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "processState", process.getState()));
				lst.add(process);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		processService.updateisdel(ids, "Y");
//		for (String id:ids){
//			Process process=processService.load(id);
//		}
		redirectionUrl = "process!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			process= processService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Process persistent = processService.load(id);
			BeanUtils.copyProperties(process, persistent, new String[] { "id","createDate", "modifyDate"});
			processService.update(persistent);
			redirectionUrl = "process!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "process.processCode", message = "工序编号不允许为空!"),
					@RequiredStringValidator(fieldName = "process.processName", message = "工序名称不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		processService.save(process);
		redirectionUrl="process!list.action";
		return SUCCESS;	
	}
		


	public Process getProcess() {
		return process;
	}


	public void setProcess(Process process) {
		this.process = process;
	}


	public ProcessService getProcessService() {
		return processService;
	}


	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}


	//获取所有状态
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
	
	
}
