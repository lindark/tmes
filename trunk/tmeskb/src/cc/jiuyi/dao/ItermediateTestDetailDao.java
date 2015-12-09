package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ItermediateTestDetail;
/**
 * Dao接口 - 半成品巡检从表Dao接口
 * 
 *
 */

public interface ItermediateTestDetailDao extends BaseDao<ItermediateTestDetail,String> {
	
	
	/**
	 * 取出所有领退料对象
	 * @return
	 */
	public List<ItermediateTestDetail> getItermediateTestDetailList();
	
	public Pager getItermediateTestDetailPager(Pager pager,HashMap<String,String>map);

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
	public List<ItermediateTestDetail> getItermediateTestDetail(String id);
	
	
	/**
	 * 根据主表id和物料表id查询
	 */
	ItermediateTestDetail getBySidAndMid(String sid,String mid);
}
