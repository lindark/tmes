package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.DeviceLogDao;
import cc.jiuyi.entity.DeviceLog;
import cc.jiuyi.service.DeviceLogService;

/**
 * Service实现类 - 设备问题日志
 */

@Service
public class DeviceLogServiceImpl extends BaseServiceImpl<DeviceLog, String> implements DeviceLogService {

	
	@Resource
	private DeviceLogDao deviceLogDao;
	
	@Resource
	public void setBaseDao(DeviceLogDao deviceLogDao) {
		super.setBaseDao(deviceLogDao);
	}
}
