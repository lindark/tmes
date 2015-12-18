package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.DeviceStepDao;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.service.DeviceStepService;

/**
 * Service实现类 - 设备工序
 */

@Service
public class DeviceStepServiceImpl extends BaseServiceImpl<DeviceStep, String> implements DeviceStepService {

	@Resource
	private DeviceStepDao deviceStepDao;
	
	@Resource
	public void setBaseDao(DeviceStepDao deviceStepDao) {
		super.setBaseDao(deviceStepDao);
	}
}
