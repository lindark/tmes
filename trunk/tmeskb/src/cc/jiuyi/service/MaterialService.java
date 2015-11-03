package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;

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

	public Pager getMaterialPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}