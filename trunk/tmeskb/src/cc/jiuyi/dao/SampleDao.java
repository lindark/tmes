package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.Sample;

/**
 * 抽检
 * @author gaoyf
 *
 */
public interface SampleDao extends BaseDao<Sample, String>
{

	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager,String wbId);

	
	/**
	 * 取出所有未确认的抽检单
	 * @return
	 */
	public List<Sample> getUncheckList();
	
	public Pager getSamplePager(Pager pager,HashMap<String,String>map);
	
	/**
	 * Excel打印
	 * @param map
	 * @return
	 */
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}
