package cc.jiuyi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
