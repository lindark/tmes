package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ProductGroup;

/**
 * Service接口
 * 产品组管理
 */
public interface ProductGroupService extends BaseService<ProductGroup, String> {
	public Pager getProductGroupPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
