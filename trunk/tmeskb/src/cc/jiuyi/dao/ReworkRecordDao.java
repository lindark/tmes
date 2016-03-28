package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.ReworkRecord;

/**
 * Dao接口 - 返工记录表
 */
public interface ReworkRecordDao extends BaseDao<ReworkRecord, String> {

	
	/**
	 * 取出所有报工对象
	 * @return
	 */
	public List<ReworkRecord> getReworkRecordList();
	
	
	public Pager getReworkRecordPager(Pager pager,HashMap<String,String> map,String id);
	

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 取出返工单记录
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	/**
	 * 打印返工记录Excel
	 * @param map
	 * @return
	 */
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
	
	public List<ReworkRecord> getReworkRecord(String id);
}
