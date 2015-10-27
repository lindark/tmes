package cc.jiuyi.action.admin;

import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cc.jiuyi.service.ProcessService;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Process;

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
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		System.out.println("ok");
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
		pager = processService.findByPager(pager);
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
		processService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功");
	}

	
	//编辑
		public String edit(){
			process= processService.load(id);
			return INPUT;	
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
