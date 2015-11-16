package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 根据组件编码判断此组件是否存在
	 */
	public boolean isExistByMaterialCode(String materialCode);
	
//	/**
//	 * 获取所有产品编码和产品名称
//	 */
//    public List<Map<String,String>> getProductsCode(String productsCode);
	
	

}
