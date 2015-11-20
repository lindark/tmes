package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ArticleDao;
import cc.jiuyi.dao.CraftDao;
import cc.jiuyi.dao.DeviceDao;
import cc.jiuyi.entity.Device;
import cc.jiuyi.service.DeviceService;

/**
 * Service实现类 - 设备
 */

@Service
public class DeviceServiceImpl extends BaseServiceImpl<Device, String> implements DeviceService {

	@Resource
	private DeviceDao deviceDao;
	
	@Resource
	public void setBaseDao(DeviceDao deviceDao) {
		super.setBaseDao(deviceDao);
	}

	@Override
	public Pager getDevicePager(Pager pager, HashMap<String, String> map) {
	
		return deviceDao.getDevicePager(pager,map);
	}
}
