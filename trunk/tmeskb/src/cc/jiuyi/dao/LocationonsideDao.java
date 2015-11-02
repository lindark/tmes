package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Locationonside;

/**
 * Dao接口 - 管理员 线边仓
 */
public interface LocationonsideDao extends BaseDao<Locationonside, String> {
	public Pager getLocationPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
