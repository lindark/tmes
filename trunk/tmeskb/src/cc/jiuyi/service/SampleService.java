package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Sample;

/**
 * 抽检
 * @author gaoyf
 *
 */
public interface SampleService extends BaseService<Sample, String>
{

	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager,String wbId);

	/**
	 * 新增
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 */
	public void saveInfo(Sample sample, String info, String info2, String my_id);

	/**
	 * 确认/撤销
	 * @param list
	 * @param newstate
	 */
	public void updateState(List<Sample> list, String newstate);

	/**
	 * 
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 */
	public void updateInfo(Sample sample, String info, String info2, String my_id);
}
