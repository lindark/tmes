package cc.jiuyi.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapDao;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.service.ScrapService;

/**
 * 报废
 * @author gaoyf
 *
 */
@Repository
public class ScrapServiceImpl extends BaseServiceImpl<Scrap, String> implements ScrapService
{
	@Resource
	private ScrapDao scrapDao;
	@Resource
	public void setBaseDao(ScrapDao scrapDao) {
		super.setBaseDao(scrapDao);
	}
	
	/**
	 * jqGrid查询
	 */
	public Pager getScrapPager(Pager pager,String wbId)
	{
		return this.scrapDao.getScrapPager(pager,wbId);
	}
}
