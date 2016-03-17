package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 管理员
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource
	private AdminDao adminDao;
	@Resource
	private DepartmentDao departmentdao;
	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}
	@Resource
	private CreditCardDao ccDao;
	@Resource
	private UnitdistributeProductService ubpService;
	@Resource
	private UnitdistributeModelService ubmService;
	@Resource
	private PostService postService;

	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}
	
	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	@Override
	public boolean isExistByCardNumber(String cardNumber) {
		return adminDao.isExistByCardNumber(cardNumber);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}
	
	/**
	 * 根据id获取直接上级
	 * 
	 */
	public Admin getAdminById(String id){
		return adminDao.getAdminById(id);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager,String departid) {
		List<String> idList = new ArrayList<String>();
		if(departid!=null)
		{
			Department department = departmentdao.get(departid);
			List<Department> list = departmentdao.getChildrenById(departid, null);
			list.add(department);
			for(Department dt:list){
				idList.add(dt.getId());
			}
		}
		return adminDao.findPagerByjqGrid(pager,idList);
	}

	@Override
	public Pager getAdminPager(Pager pager, Map map) {
		return adminDao.getAdminPager(pager,map);
	}

	/**
	 * 根据班组ID获取该 班组下的所有员工
	 */
	public List<Admin>getByTeamId(String tid)
	{
		return this.adminDao.getByTeamId(tid);
	}
	
	/**
	 * 根据员工部门id获取部长
	 */
	public List<Admin>getByAdminId(String id)
	{
		return this.adminDao.getByAdminId(id);
	}
	
	/**
	 * 根据员工部门id获取主任
	 */
	public List<Admin> getDirectorByDeptId(String id)
	{
		return this.adminDao.getDirectorByDeptId(id);
	}
	
	/**
	 * 根据员工部门id获取副总
	 */
	public List<Admin> getManagerByDeptId(String id)
	{
		return this.adminDao.getManagerByDeptId(id);
	}

	/**
	 * jqgrid分页条件查询
	 */
	public Pager getEmpPager(Pager pager, HashMap<String, String> map,Admin admin)
	{
		return this.adminDao.getEmpPager(pager,map,admin);
	}
	
	/**
	 * 根据卡号查询员工
	 */
	public Admin getByCardnum(String cardNumber)
	{
		return this.adminDao.getByCardnum(cardNumber);
	}

	/**
	 * 根据开始时间和当前时间查询出刷卡表该时间段刷卡的人,并更新admin表对应的员工
	 */
	public void updateByCreditCard(Date startdate, Date enddate,String teamid)
	{
		List<CreditCard>list=this.ccDao.getByDate(startdate, enddate);
		if(list!=null)
		{
			for(int i=0;i<list.size();i++)
			{
				CreditCard cc=list.get(i);
				//根据卡号 班组ID查询
				Admin a=this.adminDao.getByCardnumAndTeamid(cc.getCardNumber(),teamid);
				if(a!=null)
				{
					//过滤已上班的或者代班的
					if(!"2".equals(a.getWorkstate())&&!"6".equals(a.getWorkstate()))
					{
						a.setWorkstate("2");
						//是否是代班的
						if(a.getIsdaiban().equals(teamid))
						{
							a.setWorkstate("6");
						}
						a.setModifyDate(new Date());
						this.update(a);
					}
				}
			}
		}
	}

	/**
	 * 查询本班组的员工及在本班代班的员工
	 */
	public Pager getEmpAjlist(Pager pager, String tid)
	{
		return this.adminDao.getEmpAjlist(pager,tid);
	}

	/**
	 * 根据卡号 班组ID查询
	 */
	public Admin getByCardnumAndTeamid(String cardnumber, String teamid)
	{
		return this.adminDao.getByCardnumAndTeamid(cardnumber,teamid);
	}
	
	/**
	 * 查询所有在职员工
	 */
	public List<Admin>getAllList()
	{
		return this.adminDao.getAllList();
	}

	/**
	 * 添加员工
	 */
	public void saveInfo(Admin admin, String unitdistributeProducts,String unitdistributeModels,List<Role> roleList,String loginid)
	{
		//当前登录人
		Admin a=this.get(loginid);
		//工作范围添加
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();
		if(unitdistributeProducts!=null){
			String[]ids=unitdistributeProducts.split(",");
			for(int i=0;i<ids.length;i++){
				UnitdistributeProduct unitpro=ubpService.get(ids[i].trim());
				productList.add(unitpro);
			}
			admin.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));

		}else{
			admin.setUnitdistributeProductSet(null);
		}
		
		//模具组号添加
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if(unitdistributeModels!=null){
			String[] id=unitdistributeModels.split(",");
			for(int i=0;i<id.length;i++){
				UnitdistributeModel unitMod=ubmService.get(id[i].trim());
				modelList.add(unitMod);
			}
			admin.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));

		}else{
			admin.setUnitdistributeModelSet(null);
		}
		if(admin.getUsername()!=null&&!"".equals(admin.getUsername()))
		{
			admin.setUsername(admin.getUsername().toLowerCase());
		}
		admin.setLoginFailureCount(0);
		admin.setEmpCreater(a);//创建人
		admin.setIsAccountLocked(false);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		if(admin.getIsAccountEnabled()==null)
		{
			admin.setIsAccountEnabled(false);
		}
		if(roleList!=null)
		{
			admin.setRoleSet(new HashSet<Role>(roleList));//管理角色
		}
		if(admin.getPassword()!=null&&!"".equals(admin.getPassword()))
		{
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			admin.setPassword(passwordMd5);
		}
		this.save(admin);
	}

	/**
	 * 修改员工信息
	 */
	public void updateInfo(Admin admin, String unitdistributeProducts,String unitdistributeModels, List<Role> roleList)
	{
		Admin a=this.get(admin.getId());
		//工作范围添加
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();
		if (unitdistributeProducts != null&& !("").equals(unitdistributeProducts))
		{
			String[] ids = unitdistributeProducts.split(",");
			for (int i = 0; i < ids.length; i++)
			{
				UnitdistributeProduct unitpro = ubpService.get(ids[i].trim());
				productList.add(unitpro);
			}
			a.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));

		}
		else
		{
			a.setUnitdistributeProductSet(null);
		}

		// 模具组号添加
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if (unitdistributeModels != null && !("").equals(unitdistributeModels))
		{
			String[] id = unitdistributeModels.split(",");
			for (int i = 0; i < id.length; i++)
			{
				UnitdistributeModel unitMod = ubmService.get(id[i].trim());
				modelList.add(unitMod);
			}
			a.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));
		}
		else
		{
			a.setUnitdistributeModelSet(null);
		}
		if(admin.getPassword()!=null&&!"".equals(admin.getPassword()))
		{
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			a.setPassword(passwordMd5);
		}
		a.setWorkNumber(admin.getWorkNumber());//工号
		a.setParentAdmin(admin.getParentAdmin());//上级
		a.setIdentityCard(admin.getIdentityCard());//身份证号
		a.setPost(admin.getPost());//岗位
		a.setIsDel(admin.getIsDel());//是否离职
		a.setPhoneNo(admin.getPhoneNo());//联系电话
		a.setEmail(admin.getEmail());//E-Mail
		a.setRoleSet(new HashSet<Role>(roleList));//管理角色
		a.setIsAccountEnabled(admin.getIsAccountEnabled());//是否启用
		a.setDepartment(admin.getDepartment());//部门
		a.setName(admin.getName());//姓名
		a.setCardNumber(admin.getCardNumber());//卡号
		a.setModifyDate(new Date());
		this.update(a);
	}

	/**
	 * 查询所有员工
	 */
	public Pager getAllEmp(Pager pager,HashMap<String, String> map,String deptid,int my_id)
	{
		List<String> list_str = new ArrayList<String>();
		if(deptid!=null&&!"".equals(deptid))
		{
			Department department = departmentdao.get(deptid);
			List<Department> list = departmentdao.getChildrenById(deptid, null);
			list.add(department);
			for(Department dt:list)
			{
				list_str.add(dt.getId());
			}
		}
		return this.adminDao.getAllEmp(pager,map,list_str,my_id);
	}

	/**
	 * 查询所有未离职的,已启用的员工
	 */
	public Pager getAllWorkEmp(Pager pager, HashMap<String, String> map)
	{
		return this.adminDao.getAllWorkEmp(pager,map);
	}

	/**
	 * 人员信息维护:修改
	 */
	public void updateEmpRy(Admin admin, String unitdistributeProducts,String unitdistributeModels)
	{
		Admin a=this.get(admin.getId());
		//工作范围
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();
		if (unitdistributeProducts != null&& !("").equals(unitdistributeProducts))
		{
			String[] ids = unitdistributeProducts.split(",");
			for (int i = 0; i < ids.length; i++)
			{
				UnitdistributeProduct unitpro = ubpService.get(ids[i].trim());
				productList.add(unitpro);
			}
			a.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));

		}
		else
		{
			a.setUnitdistributeProductSet(null);
		}

		// 模具组号
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if (unitdistributeModels != null && !("").equals(unitdistributeModels))
		{
			String[] id = unitdistributeModels.split(",");
			for (int i = 0; i < id.length; i++)
			{
				UnitdistributeModel unitMod = ubmService.get(id[i].trim());
				modelList.add(unitMod);
			}
			a.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));
		}
		else
		{
			a.setUnitdistributeModelSet(null);
		}
		//姓名 性别  身份证号 联系电话 工号 卡号 E-mail 直接上级 部门 岗位 模具组号 工作范围 班组 是否离职 入职日期
		// 
		a.setName(admin.getName());//姓名
		a.setSex(admin.getSex());//性别
		a.setIdentityCard(admin.getIdentityCard());//身份证号
		a.setPhoneNo(admin.getPhoneNo());//联系电话
		a.setWorkNumber(admin.getWorkNumber());//工号
		a.setCardNumber(admin.getCardNumber());//卡号
		a.setEmail(admin.getEmail());//E-Mail
		a.setParentAdmin(admin.getParentAdmin());//上级
		a.setDepartment(admin.getDepartment());//部门
		a.setPost(admin.getPost());//岗位
		a.setTeam(admin.getTeam());//班组
		a.setIsDel(admin.getIsDel());//是否离职
		a.setStartWorkDate(admin.getStartWorkDate());//入职日期
		a.setModifyDate(new Date());//修改日期
		this.update(a);
	}

	/**
	 * 检验工号和卡号是否重复
	 */
	public String getChecknum(String worknumber, String cardnumber,String id)
	{
		List<Admin>list1=this.adminDao.getByNumber(worknumber,id,1);
		List<Admin>list2=this.adminDao.getByNumber(cardnumber,id,2);
		if(list1.size()>0&&list2.size()>0)
		{
			return "wc";
		}
		if(list1.size()>0)
		{
			return "w";
		}
		if(list2.size()>0)
		{
			return "c";
		}
		return "success";
	}

	/**
	 * 人员权限维护
	 */
	public void updateEmpQx(Admin admin, List<Role> roleList,String loginid)
	{
		Admin a=this.get(admin.getId());
		//登录名
		if(admin.getUsername()!=null&&!"".equals(admin.getUsername()))
		{
			a.setUsername(admin.getUsername());
		}
		//密码
		if(admin.getPassword()!=null&&!"".equals(admin.getPassword()))
		{
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			a.setPassword(passwordMd5);
		}
		//角色管理
		if(roleList!=null)
		{
			a.setRoleSet(new HashSet<Role>(roleList));//管理角色
		}
		a.setIsAccountEnabled(admin.getIsAccountEnabled());//是否启用//是否启用
		a.setModifyDate(new Date());//修改日期
		//创建人
		if(a.getEmpCreaterqx()==null&&loginid!=null)
		{
			Admin loginer=this.get(loginid);//登录人
			a.setEmpCreaterqx(loginer);
		}
		this.update(a);
	}
}