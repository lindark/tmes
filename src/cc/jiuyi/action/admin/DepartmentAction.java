package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.util.ThinkWayUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

/**
 * 后台Action类 - 部门管理 
 */

@ParentPackage("admin")
public class DepartmentAction extends BaseAdminAction {

	private static final long serialVersionUID = 3142941564892040221L;
	@Resource
	private DepartmentService deptservice;
	@Resource
	private FactoryService factoryservice;
	@Resource
	private WorkShopService workshopservice;
	@Resource
	private FactoryUnitService factoryunitservice;
	@Resource
	private TeamService teamservice;
	@Resource
	private DictService dictService;
	
	private List<Department> list;
	private Department department;
	private String pid;//父节点id
	private String pname;//父节点名称
	private String factoryid;
	private String workshopid;
	private String factoryunitid;
	private List<Department> list_dept;
	private String deptid;//部门主键
	private String isadd;
	private String deptcode;//部门编码
	private String loginid;//登录人的ID
	/**
	 * 跳转List 页面
	 * @return
	 */
	public String list(){
		list = deptservice.getAllDept();
		return "list";
	}
	
	public String browser(){
		list = deptservice.getAllByHql(id);
		return "browser";
	}
	
	public String listajax(){
		list = deptservice.getAllByHql(id);
		for(int i =0; i < list.size();i++){
			Department department  = (Department)list.get(i);
			department.setAdmin(null);
			department.setChildDept(null);
			department.setParentDept(null);
			list.set(i, department);
		}
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
		System.out.println(jsonArray.toString());
		return ajaxJson(jsonArray.toString());
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String add(){
		Department depart = deptservice.get(pid);
		pname = depart.getDeptName();
		return "input";
	}
	/**
	 * 修改页面
	 * @return
	 */
	public String edit(){
		department = deptservice.load(id);//获取的当前节点的属性
		return "input";
	}
	
	
	/**
	 * 获取factory的json集合
	 * @return
	 */
	public String getFactory(){
		
		List<Factory> factoryList = factoryservice.getList("isDel", "N");
		JSONArray json = new JSONArray();
		for(Factory factory : factoryList){
			JSONObject jo = new JSONObject();
			jo.put("name", factory.getFactoryName());
			jo.put("value", factory.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("factory", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取workshop的json集合
	 * @return
	 */
	public String getWorkshop(){
		String[] proName={"isDel","factory.id"};
		String[] proValue={"N",factoryid};
		List<WorkShop> workshopList = workshopservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(WorkShop workshop : workshopList){
			JSONObject jo = new JSONObject();
			jo.put("name", workshop.getWorkShopName());
			jo.put("value", workshop.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("workshop", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取factoryunit的json集合
	 * @return
	 */
	public String getFactoryunit(){
		String[] proName={"isDel","workShop.id"};
		String[] proValue={"N",workshopid};
		List<FactoryUnit> factoryunitList = factoryunitservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(FactoryUnit factoryunit : factoryunitList){
			JSONObject jo = new JSONObject();
			jo.put("name", factoryunit.getFactoryUnitName());
			jo.put("value", factoryunit.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("factoryunit", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取team的json集合
	 * @return
	 */
	public String getTeam(){
		String[] proName={"isDel","factoryUnit.id"};
		String[] proValue={"N",factoryunitid};
		List<Team> teamList = teamservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(Team team : teamList){
			JSONObject jo = new JSONObject();
			jo.put("name", team.getTeamName());
			jo.put("value", team.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("team", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		id = deptservice.save(department);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门添加成功");
		jsonMap.put("id", id);//将id 返回
		jsonMap.put("deptName", department.getDeptName());//将name 返回
		jsonMap.put("parentDept", department.getParentDept().getId());//将父节点返回
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		if(department.getParentDept().getId().equals(""))//如果传过来的是空字符串，将parentdept赋值成空
			department.setParentDept(null);
		Department persistent = deptservice.load(department.getId());
		BeanUtils.copyProperties(department, persistent, new String[] {"id", "isDel"});
		deptservice.update(persistent);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门修改成功");
		jsonMap.put("id", department.getId());//将id 返回
		jsonMap.put("deptName", department.getDeptName());//将name 返回
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ids = id.split(",");
		deptservice.updateisdel(ids, "Y");//删除
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门删除成功");
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 检查部门名称
	 * @return
	 */
	public String CheckDeptName(){
		//暂时不需要使用
		return ajaxText("true");
	}
	
	/**=================================================*/
	/**
	 * 部门管理:跳转到list列表页面
	 */
	public String alllist()
	{
		this.list_dept=this.deptservice.getAllDept();//查询所有未删除的部门
		return "alllist";
	}
	
	/**
	 * 部门管理:查询所有部门
	 */
	public String ajlist()
	{
		if(pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if(pager.is_search()==true && filters != null&&!"".equals(filters))
		{
			if(!filters.equals(""))
			{
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		pager=this.deptservice.getAllDept(pager,deptid);
		@SuppressWarnings("unchecked")
		List<Department>list1=pager.getList();
		for(int i=0;i<list1.size();i++)
		{
			Department d=list1.get(i);
			//创建人
			if(d.getCreater()!=null)
			{
				d.setXcreater(d.getCreater().getName());
			}
			//部门负责人
			if(d.getDeptLeader()!=null)
			{
				d.setXdeptLeader(d.getDeptLeader().getName());
			}
			//上级部门
			if(d.getParentDept()!=null)
			{
				d.setXparentDept(d.getParentDept().getDeptName());
			}
			//是否启用
			if(d.getIsWork()!=null)
			{
				d.setXisWork(ThinkWayUtil.getDictValueByDictKey(dictService, "departmentIsWork", d.getIsWork()));
			}
		}
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Department.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 添加
	 */
	public String adddept()
	{
		isadd="toadd";
		return "inputdept";
	}
	
	/**
	 * 编辑
	 */
	public String editdept()
	{
		this.department=this.deptservice.get(id);
		return "inputdept";
	}
	
	/**
	 * 添加保存
	 */
	public String savedept()
	{
		this.deptservice.saveInfo(department,loginid);
		redirectionUrl = "department!alllist.action";
		return SUCCESS;
	}
	
	/**
	 * 修改保存
	 */
	public String updatedept()
	{
		this.deptservice.updateInfo(department);
		redirectionUrl = "department!alllist.action";
		return SUCCESS;
	}
	/**
	 *  删除
	 */
	public String deletedept()
	{
		ids = id.split(",");
		this.deptservice.updateisdel(ids, "Y");
		redirectionUrl = "department!alllist.action";
		return SUCCESS;
	}
	
	/**
	 * 检查部门编码是否重复
	 */
	public String checkcode()
	{
		Department d=this.deptservice.getByCode(deptcode);
		if(d!=null)
		{
			if(id!=null&&d.getId().equals(id))
			{
				return this.ajaxJsonSuccessMessage("S");
			}
			return this.ajaxJsonErrorMessage("E");
		}
		return this.ajaxJsonSuccessMessage("S");
	}
	
	/**===========================================================*/

	public List<Department> getList() {
		return list;
	}
	public void setList(List<Department> list) {
		this.list = list;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getFactoryid() {
		return factoryid;
	}
	public void setFactoryid(String factoryid) {
		this.factoryid = factoryid;
	}
	public String getWorkshopid() {
		return workshopid;
	}
	public void setWorkshopid(String workshopid) {
		this.workshopid = workshopid;
	}
	public String getFactoryunitid() {
		return factoryunitid;
	}
	public void setFactoryunitid(String factoryunitid) {
		this.factoryunitid = factoryunitid;
	}
	public List<Department> getList_dept()
	{
		return list_dept;
	}
	public void setList_dept(List<Department> list_dept)
	{
		this.list_dept = list_dept;
	}

	public String getDeptid()
	{
		return deptid;
	}

	public void setDeptid(String deptid)
	{
		this.deptid = deptid;
	}

	public String getIsadd()
	{
		return isadd;
	}

	public void setIsadd(String isadd)
	{
		this.isadd = isadd;
	}

	public String getDeptcode()
	{
		return deptcode;
	}

	public void setDeptcode(String deptcode)
	{
		this.deptcode = deptcode;
	}

	public String getLoginid()
	{
		return loginid;
	}

	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}
}