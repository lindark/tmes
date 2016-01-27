package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;

/**
 * Dao接口 - 管理员
 */

public interface AdminDao extends BaseDao<Admin, String> {
	
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
	 * jqgrid 分页
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,Map map,List list);
	
	public Pager getAdminPager(Pager pager,Map map);

	/**
	 * 根据班组ID获取该班组下的所有员工
	 * @param tid
	 * @return
	 */
	public List<Admin> getByTeamId(String tid);

	/**
	 * jqgrid分页条件查询-考勤
	 */
	public Pager getEmpPager(Pager pager, HashMap<String, String> map,Admin admin);

	/**
	 * 根据卡号查询员工
	 */
	public Admin getByCardnum(String cardNumber);

	/**
	 * 根据卡号 班组ID查询
	 * @param cardNumber
	 * @param teamid
	 * @return
	 */
	public Admin getByCardnumAndTeamid(String cardNumber, String teamid);
	
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

}