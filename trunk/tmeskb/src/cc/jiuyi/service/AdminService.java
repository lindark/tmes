package cc.jiuyi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.Station;

/**
 * Service接口 - 管理员
 */

public interface AdminService extends BaseService<Admin, String> {

	/**
	 * 获取当前登录管理员,若未登录则返回null.
	 * 
	 * @return 当前登录管理员对象
	 */
	public Admin getLoginAdmin();
	
	/**
	 * 获取当前登录管理员(从数据库中加载),若未登录则返回null.
	 * 
	 * @return 当前登录管理员对象
	 */
	public Admin loadLoginAdmin();
	
	/**
	 * 根据登录名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	/**
	 * 根据卡号判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByCardNumber(String cardNumber);
	
	/**
	 * 根据登录名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);
	
	/**
	 * jqgrid分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,String departid);

	public Pager getAdminPager(Pager pager,Map map);

	/**
	 * 根据班组ID获取该班组下的员工
	 * @param id
	 * @return
	 */
	public List<Admin> getByTeamId(String tid);

	/**
	 * jqgrid分页条件查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getEmpPager(Pager pager, HashMap<String, String> map,Admin admin);

	/**
	 * 根据卡号查询员工
	 * @param cardNumber
	 * @return
	 */
	public Admin getByCardnum(String cardNumber);

	/**
	 * 根据开始时间和当前时间查询出刷卡表该时间段刷卡的人,并更新admin表对应的员工
	 * @param startdate
	 * @param enddate
	 */
	public void updateByCreditCard(Date startdate, Date enddate,String teamid);
	
	
	/**
	 * 根据员工部门id获取部长
	 * @param id
	 * @return
	 */
	public List<Admin> getByAdminId(String id);
	
	/**
	 * 根据员工部门id获取主任
	 * @param id
	 * @return
	 */
	public List<Admin> getDirectorByDeptId(String id);
	
	/**
	 * 根据员工部门id获取副总
	 * @param id
	 * @return
	 */
	public List<Admin> getManagerByDeptId(String id);
	
	/**
	 * 根据id获取直接上级
	 * 
	 */
	public Admin getAdminById(String id);

	/**
	 * 查询本班组的员工及在本班代班的员工
	 * @param pager
	 * @param tid
	 * @return
	 */
	public Pager getEmpAjlist(Pager pager, String tid);

	/**
	 * 根据卡号 班组ID查询
	 * @param cardnumber
	 * @param teamid
	 * @return
	 */
	public Admin getByCardnumAndTeamid(String cardnumber, String teamid);

	/**
	 * 查询所有在职员工
	 */
	public List<Admin> getAllList();

	/**
	 * 添加员工
	 * @param admin
	 * @param unitdistributeProducts
	 * @param unitdistributeModels
	 * @param roleList 
	 */
	public void saveInfo(Admin admin, String unitdistributeProducts,String unitdistributeModels, List<Role> roleList,String loginid,String stationids);

	/**
	 * 修改员工信息
	 * @param admin
	 * @param unitdistributeProducts
	 * @param unitdistributeModels
	 * @param roleList
	 */
	public void updateInfo(Admin admin, String unitdistributeProducts,String unitdistributeModels, List<Role> roleList);

	/**
	 * 
	 * @param pager
	 * @param deptid 
	 * @param map
	 * @param my_id 1:查询所有  2:查询已维护过登录等权限的人员  3:查询未维护过的人员
	 * @return
	 */
	public Pager getAllEmp(Pager pager,HashMap<String, String> map, String deptid,int my_id);

	/**
	 * 查询所有未离职的,已启用的员工
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager getAllWorkEmp(Pager pager, HashMap<String, String> map);

	/**
	 * 人员信息维护:修改
	 * @param admin
	 * @param roleList 
	 * @param unitdistributeModels 
	 * @param unitdistributeProducts 
	 */
	public void updateEmpRy(Admin admin, String unitdistributeProducts, String unitdistributeModels,String stationids);

	/**
	 * 检验工号和卡号是否重复
	 * @param worknumber工号
	 * @param cardnumber卡号
	 * @return
	 */
	public String getChecknum(String worknumber, String cardnumber,String id);

	/**
	 * 人员权限维护
	 * @param admin
	 * @param roleList 管理角色
	 */
	public void updateEmpQx(Admin admin, List<Role> roleList,String loginid);

	/**
	 * 根据用户名查询
	 * @param username 用户名
	 * @return
	 */
	public Admin getByUsername(String username);

	/**
	 * 假删除
	 * @param id ids
	 */
	public void updateToDel(String id);

	/**
	 * 根据条件查询
	 * @param admin 
	 * @return
	 */
	public List<Admin> getAllByConditions(Admin admin);
}