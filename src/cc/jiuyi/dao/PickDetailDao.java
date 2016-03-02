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
	
	
	/**
	 * 根据id取领料从表数据
	 * @param id
	 * @return
	 */
	public List<PickDetail> getPickDetail(String id);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
