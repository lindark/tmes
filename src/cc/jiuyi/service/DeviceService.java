package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Device;

/**
 * Service接口 - 设备
 */
public interface DeviceService extends BaseService<Device, String> {

	public Pager getDevicePager(Pager pager, HashMap<String, String> map,String id);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
