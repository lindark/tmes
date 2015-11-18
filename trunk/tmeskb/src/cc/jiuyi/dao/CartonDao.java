package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Material;

/**
 * Dao接口 纸箱
 */
public interface CartonDao extends BaseDao<Carton, String> {
	public Pager getCartonPager(Pager pager, HashMap<String, String> map,
			String workingbillId);

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);
}
