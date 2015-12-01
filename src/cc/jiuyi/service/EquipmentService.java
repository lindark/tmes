package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Equipments;

/**
 * Service接口 - 设备
 */
public interface EquipmentService extends BaseService<Equipments, String> {

	public Pager getEquipmentPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public boolean isExistByEquipmentNo(String equipmentNo);
}
