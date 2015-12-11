package cc.jiuyi.service;

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
}