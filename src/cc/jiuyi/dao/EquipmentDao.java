package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Equipment;

/**
 * Dao接口 - 设备
 */
public interface EquipmentDao extends BaseDao<Equipment, String> {

public Pager getEquipmentPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public boolean isExistByEquipmentNo(String equipmentNo);
}
