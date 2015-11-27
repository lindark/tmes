package cc.jiuyi.service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
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
	public Pager getSamplePager(Pager pager);

	/**
	 * 新增
	 * @param sample 抽检单
	 * @param info 缺陷IDs
	 * @param info2 缺陷数量
	 * @param my_id 1保存/2确认
	 * @param admin 当前登录人
	 */
	public void saveInfo(Sample sample, String info, String info2, String my_id, Admin admin);
}
