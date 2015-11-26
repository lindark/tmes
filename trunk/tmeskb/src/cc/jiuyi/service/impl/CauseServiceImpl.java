package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.service.CauseService;

/**
 * Service实现类 呼叫原因
 */
@Service
@Transactional
public class CauseServiceImpl extends BaseServiceImpl<Cause, String> implements
		CauseService {
	@Resource
	private CauseDao causeDao;

	@Resource
	public void setBaseDao(CauseDao causeDao) {
		super.setBaseDao(causeDao);
	}

	public Pager getCausePager(Pager pager, HashMap<String, String> map) {
		return causeDao.getCausePager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		causeDao.updateisdel(ids, oper);
	}
	/**
	 * 获取缺陷表中关于抽检的内容
	 */
	@Override
	public List<Cause> getBySample(String type)
	{
		return this.causeDao.getBySample(type);
	}
}
