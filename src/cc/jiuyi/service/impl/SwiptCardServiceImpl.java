package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.SwiptCardDao;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.service.SwiptCardService;

/**
 * Service实现类 - 刷卡
 */

@Service
public class SwiptCardServiceImpl extends BaseServiceImpl<SwiptCard, String> implements SwiptCardService {

	@Resource
	private SwiptCardDao swiptCardDao;
	
	@Resource
	public void setBaseDao(SwiptCardDao swiptCardDao) {
		super.setBaseDao(swiptCardDao);
	}
}
