package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Factory;
/**
 * Service接口 - 工序管理
 */

public interface FactoryService extends BaseService<Factory, String> {

	/**
	 * 取出所有factory对象
	 * @return
	 */
	public List<Factory> getFactoryList();
	
	public Pager getFactoryPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public boolean isExistByFactoryCode(String factoryCode);
	/**
	 * 获取有的工厂
	 */
	public List<Factory> getFactoryListByY();
	
	/**
	 * 根据工厂编码找工厂描述
	 */
	public Factory getFactoryByCode(String factoryCode);
}