package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DeviceDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Device;

/**
 * Dao实现类 - 设备
 */

@Repository
public class DeviceDaoImpl extends BaseDaoImpl<Device, String> implements DeviceDao {

	public Pager getDevicePager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Device.class);
		pagerSqlByjqGrid(pager,detachedCriteria);

		if (map.size() > 0) {			
			
			if(map.get("team")!=null){
			    detachedCriteria.add(Restrictions.like("classes", "%"+map.get("team")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("productName")+"%"));
			}			
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

}
