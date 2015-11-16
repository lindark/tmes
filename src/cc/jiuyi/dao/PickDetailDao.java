package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PickDetail;

/**
 * Dao接口 -领/退料从表
 * 
 *
 */

public interface PickDetailDao extends BaseDao<PickDetail,String> {
	
	
	/**
	 * 取出所有领退料对象
	 * @return
	 */
	public List<PickDetail> getPickDetailList();
	
	public Pager getPickDetailPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<Material> getMantrBom(String matnr);
	
}
