package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Mouldmaterial;

/**
 * Dao接口 - 设备
 */
public interface MouldmaterialDao extends BaseDao<Mouldmaterial, String> {

   public Pager getMouldmaterialPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
}
