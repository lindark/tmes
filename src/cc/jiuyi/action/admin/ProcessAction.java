package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
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
	private List<WorkingBill>ar;
	private String info;
	
	@Resource
	private ProcessService processService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService wbService;
	
	//是否已存在ajax验证
	public String checkProcesssCode()
	{
		String processCode=process.getProcessCode();
		if(processService.isExistByProccessCode(processCode)){
			return ajaxText("false");
		}else{
			return ajaxText("true");
		}
	}
	
	//添加
	public String add(){
		//ar=getIdsAndNames();
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
		
		HashMap<String, String> map = new HashMap<String, String>();
		try
		{
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
				//产品编码
				if(obj.get("xproductnum")!=null)
				{
					map.put("xproductnum", obj.get("xproductnum").toString());
				}
				//产品名称
				if(obj.get("xproductname")!=null)
				{
					map.put("xproductname", obj.get("xproductname").toString());
				}
			}

			pager = processService.getProcessPager(pager, map);
			List<Process> processList = pager.getList();
			List<Process> list2 = new ArrayList<Process>();
			for (int i = 0; i < processList.size(); i++) 
			{
				Process p =processList.get(i);
				p.setXproductnum(p.getProducts().getProductsCode());//产品编码
				p.setXproductname(p.getProducts().getProductsName());//产品名称
				p.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "processState", p.getState()));
				list2.add(p);
			}
			pager.setList(list2);
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setExcludes(new String[]{"products"});//除去联级products属性 gaoyf
			JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
			System.out.println(jsonArray.get(0).toString());
			return ajaxJson(jsonArray.get(0).toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
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

	//查询一个带联表
	public Process getOne(String id)
	{
		Process p=this.processService.getOne(id);
		return p;
	}
	//编辑
	public String edit()
	{
		try
		{
			//ar=getIdsAndNames();
			process=getOne(id);
			return INPUT;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
		
	//更新
	@InputConfig(resultName = "error")
	public String update()
	{
		Process persistent = processService.load(id);
		//BeanUtils.copyProperties(process, persistent, new String[] { "id","createDate", "modifyDate"});
		persistent.setModifyDate(new Date());
		processService.save(persistent);
		redirectionUrl = "process!list.action";
		return SUCCESS;
	}
		
	//保存
	/*@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "process.processCode", message = "工序编号不允许为空!"),
					@RequiredStringValidator(fieldName = "process.processName", message = "工序名称不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")*/
	public String save()
	{
		process.setIsDel("N");
		process.setCreateDate(new Date());
		processService.save(process);
		redirectionUrl="process!list.action";
		return SUCCESS;	
	}
		
	/**
	 * 查询产品表中的id 和 产品名称maktx
	 */
	/*public List<WorkingBill> getIdsAndNames()
	{
		List<WorkingBill> l=null;
		try
		{
			l=this.wbService.getIdsAndNames();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return l;
	}*/
	
	/**
	 * 检查工序编码是否存在
	 */
	public String getCk()
	{
		try
		{
			if(!processService.getCk(info))
			{
				return this.ajaxJsonErrorMessage("e");
			}
			return this.ajaxJsonSuccessMessage("s");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	/***/
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

	public List<WorkingBill> getAr()
	{
		return ar;
	}

	public void setAr(List<WorkingBill> ar)
	{
		this.ar = ar;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}
	
}
