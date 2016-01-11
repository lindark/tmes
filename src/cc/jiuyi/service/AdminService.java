package cc.jiuyi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;

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
	public Pager findPagerByjqGrid(Pager pager,Map map,String departid);

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

}