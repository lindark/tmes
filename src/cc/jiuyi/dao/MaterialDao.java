
package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;

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
	
	
	/**
	 * 获取所有产品对象
	 */
    public List<Products> getProductsList();
	
    /**
     * 根据随工单中的产品编码查组件Bom
     * @param matnr
     * @return
     */
    public List<Material> getMantrBom(String matnr);
    
    /**
     * 根据随工单中的产品编码查组件Bom
     * @param matnr
     * @return
     */
    public List<Material> getMantrBom(Object[] matnrs);

    /**
     * 根据物料id查询是否存在
     * @param code
     * @return
     */
	public boolean getByCode(String code,FactoryUnit factoryunit);

	/**
	 * 根据物料编码查询
	 */
	public Material getByNum(String materialCode,FactoryUnit factoryunit);

	public List<Material> getMaterialList(String materialCode,FactoryUnit factoryunit);
	
	/**
     * 根据物料id查询是否存在
     * @param code
     * @return
     */
	public boolean getByCode(String code);

	/**
	 * 根据物料编码查询
	 */
	public Material getByNum(String materialCode);
}
