package cc.jiuyi.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.SampleDao;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.service.SampleService;

/**
 * 抽检
 * @author gaoyf
 *
 */
@Repository
public class SampleServiceImpl extends BaseServiceImpl<Sample, String> implements SampleService
{
	@Resource
	private SampleDao sampleDao;

	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager)
	{
		return this.sampleDao.getSamplePager(pager);
	}
	
}
