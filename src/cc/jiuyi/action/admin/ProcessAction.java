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
import cc.jiuyi.entity.Process;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;


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
	
	@Resource
	private ProcessService processService;
	@Resource
	private DictService dictService;
	
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
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
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
		
		if(pager.is_search()==true && Param != null){//普通搜索功能
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject filt=JSONObject.fromObject(Param);
			Pager pager2=new Pager();
			Map m=new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager2 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager2.getRules());
			pager.setGroupOp(pager2.getGroupOp());
			
		}
		
		pager = processService.getProcessPager(pager);
		List<Process> processList=pager.getList();
		List<Process> lst = new ArrayList<Process>();
		for(int i=0;i<processList.size();i++){
			Process process = (Process)processList.get(i);
			process.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "processState", process.getState()));
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
		for (String id:ids){
			Process process=processService.load(id);
		}
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
//	@Validations(
//			requiredStrings = {
//					@RequiredStringValidator(fieldName = "process.processCode", message = "工序编码不允许为空!"), 
//					@RequiredStringValidator(fieldName = "process.processName", message = "工序名称不允许为空!")
//			  }
//			)
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
	
	
}
