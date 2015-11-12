package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.CraftDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.service.CraftService;

/**
 * Service实现类 - 工艺
 */

@Service
public class CraftServiceImpl extends BaseServiceImpl<Craft, String> implements CraftService {

	@Resource
	private CraftDao craftDao;
	
	@Resource
	public void setBaseDao(CraftDao craftDao) {
		super.setBaseDao(craftDao);
	}


	@Override
	public Pager getCraftPager(Pager pager, HashMap<String, String> map) {
		return craftDao.getCraftPager(pager,map);
	}
}


