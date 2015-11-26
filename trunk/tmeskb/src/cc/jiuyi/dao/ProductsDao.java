package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;

/**
 * Dao接口 -产品管理
 * 
 *
 */

public interface ProductsDao extends BaseDao<Products,String> {
	
	
	/**
	 * 取出所有产品对象
	 * @return
	 */
	public List<Products> getProductsList();
	
	public Pager getProductsPager(Pager pager,HashMap<String,String>map);
	public Pager getProductsPager2(Pager pager,HashMap<String,String>map,String id);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据产品编码判断此产品是否存在
	 */
	public boolean isExistByProductsCode(String productsCode);
	
	/**
	 * 根据物料编码判断此物料组是否存在
	 */
	public boolean isExistByMaterialGroup(String materialGroup);
	
	public List<Process> getAllProcess();
	public List<Material> getAllMaterial();
	
	public Products getProducts(String matnr);
	
}
