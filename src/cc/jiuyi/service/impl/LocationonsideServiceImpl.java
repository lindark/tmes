package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.LocationonsideDao;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.service.LocationonsideService;

/**
 * Service实现类 线边仓
 */
@Service
@Transactional
public class LocationonsideServiceImpl extends
		BaseServiceImpl<Locationonside, String> implements
		LocationonsideService {
	@Resource
	private LocationonsideDao locationonsideDao;

	@Resource
	public void setBaseDao(LocationonsideDao locationonsideDao) {
		super.setBaseDao(locationonsideDao);
	}
	
	public Pager getLocationPager(Pager pager){
		return locationonsideDao.getLocationPager(pager);
	}

}
