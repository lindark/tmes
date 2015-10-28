package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.service.AbnormalService;

/**
 * Service实现类 - 异常
 */

@Service
public class AbnormalServiceImpl extends BaseServiceImpl<Abnormal, String> implements AbnormalService {

	@Resource
	private AbnormalDao abnormalDao;
	
	@Resource
	public void setBaseDao(AbnormalDao abnormalDao) {
		super.setBaseDao(abnormalDao);
	}
}
