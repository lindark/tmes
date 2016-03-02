package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Mouldmaterial;

/**
 * Service接口 - 设备
 */
public interface MouldmaterialService extends BaseService<Mouldmaterial, String> {

	public Pager getMouldmaterialPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
}
