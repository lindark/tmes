package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ScrapLaterDao;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.service.ScrapLaterService;

/**
 * 报废产出后记录
 * @author lenovo
 *
 */
@Repository
public class ScrapLaterServiceImpl extends BaseServiceImpl<ScrapLater, String> implements ScrapLaterService
{
	@Resource
	private ScrapLaterDao slDao;
	@Resource
	public void setBaseDao(ScrapLaterDao slDao) {
		super.setBaseDao(slDao);
	}
	/**
	 * 根据主表id获取产出表数据
	 */
	public List<ScrapLater> getSlBySid(String sid)
	{
		return this.slDao.getSlBySid(sid);
	}

}
