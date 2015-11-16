package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;

/**
 * Service接口 - 产品Bom管理
 */

public interface MaterialService extends BaseService<Material, String> {

	/**
	 * 取出所有Material对象
	 * 
	 * @return
	 */
	public List<Material> getMaterialList();
	
	/**
	 * 取出所有Products对象
	 *
	 */
	public List<Products> getProductsList();

	public Pager getMaterialPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	
	/**
	 * 根据组件编码判断此组件是否存在
	 */
	public boolean isExistByMaterialCode(String materialCode);

}