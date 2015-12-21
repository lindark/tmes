package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.DeviceModlueDao;
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.service.DeviceModlueService;

/**
 * Service实现类 - 设备维修物料（组件）
 */

@Service
public class DeviceModlueServiceImpl extends BaseServiceImpl<DeviceModlue, String> implements DeviceModlueService {

	@Resource
	private DeviceModlueDao deviceModlueDao;
	
	@Resource
	public void setBaseDao(DeviceModlueDao deviceModlueDao) {
		super.setBaseDao(deviceModlueDao);
	}
}
