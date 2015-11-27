package cc.jiuyi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.entity.IpRecord;
import cc.jiuyi.service.IpRecordService;

/**
 * Service实现类 巡检从表
 */
@Service
@Transactional
public class IpRecordServiceImpl extends
		BaseServiceImpl<IpRecord, String> implements
		IpRecordService {

}
