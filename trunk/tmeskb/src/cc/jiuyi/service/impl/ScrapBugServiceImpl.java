package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ScrapBugDao;
import cc.jiuyi.entity.ScrapBug;
import cc.jiuyi.service.ScrapBugService;

/**
 * 报废--缺陷记录
 * @author lenovo
 *
 */
@Repository
public class ScrapBugServiceImpl extends BaseServiceImpl<ScrapBug, String> implements ScrapBugService
{
	@Resource
	private ScrapBugDao sbDao;
	@Resource
	public void setBaseDao(ScrapBugDao sbDao) {
		super.setBaseDao(sbDao);
	}
}
