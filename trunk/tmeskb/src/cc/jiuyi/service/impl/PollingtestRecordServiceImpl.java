package cc.jiuyi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.dao.PollingtestRecordDao;
import cc.jiuyi.entity.PollingtestRecord;
import cc.jiuyi.service.PollingtestRecordService;

/**
 * Service实现类 巡检从表
 */
@Service
@Transactional
public class PollingtestRecordServiceImpl extends
		BaseServiceImpl<PollingtestRecord, String> implements
		PollingtestRecordService {
	@Resource
	private PollingtestRecordDao pollingtestRecordDao;

	@Resource
	public void setBaseDao(PollingtestRecordDao pollingtestRecordDao) {
		super.setBaseDao(pollingtestRecordDao);
	}

	@Override
	public List<PollingtestRecord> findByPollingtestId(String id) {
		return pollingtestRecordDao.findByPollingtestId(id);
	}

}
