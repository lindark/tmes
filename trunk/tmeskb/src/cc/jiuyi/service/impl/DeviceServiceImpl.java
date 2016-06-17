package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
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
	public Pager getDevicePager(Pager pager, HashMap<String, String> map,String id,String team) {
	
		return deviceDao.getDevicePager(pager,map,id,team);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		deviceDao.updateisdel(ids, oper);
	}

	@Override
	public Pager findByPager(Pager pager,HashMap<String, String> map, String id) {
		// TODO Auto-generated method stub
		return deviceDao.findByPager(pager,map,id);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map,
			String id, String teamid) {
		// TODO Auto-generated method stub
		return deviceDao.historyExcelExport(map,id,teamid);
	}
}
