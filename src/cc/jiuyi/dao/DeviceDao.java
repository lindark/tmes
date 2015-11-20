package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Device;

/**
 * Dao接口 - 设备
 */
public interface DeviceDao extends BaseDao<Device, String> {

	public Pager getDevicePager(Pager pager, HashMap<String, String> map);
}
