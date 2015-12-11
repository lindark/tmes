package cc.jiuyi.dao;

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
}