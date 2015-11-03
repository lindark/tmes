package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;

/**
 * Dao接口 -产品Bom管理
 * 
 *
 */

public interface MaterialDao extends BaseDao<Material,String> {
	
	
	/**
	 * 取出所有产品Bom对象
	 * @return
	 */
	public List<Material> getMaterialList();
	
	public Pager getMaterialPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
