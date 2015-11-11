package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Products;

/**
 * Service接口 - 产品管理
 */

public interface ProductsService extends BaseService<Products, String> {

	/**
	 * 取出所有Products对象
	 * 
	 * @return
	 */
	public List<Products> getProductsList();

	public Pager getProductsPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	
	/**
	 * 根据ProductsCode判断Products是否存在
	 */
	public boolean isExistByProductsCode(String productsCode);
}