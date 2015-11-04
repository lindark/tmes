package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProductGroup;

/**
 * Dao接口
 * 产品组管理
 */
public interface ProductGroupDao extends BaseDao<ProductGroup, String> {
	public Pager getProductGroupPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
