package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.LocatHandOverDao;
import cc.jiuyi.entity.LocatHandOver;
import cc.jiuyi.service.LocatHandOverService;
/**
 * Service实现类 线边仓
 */
@Service
public class LocatHandOverServiceImpl extends BaseServiceImpl<LocatHandOver, String> implements
		LocatHandOverService {

	@Resource
	LocatHandOverDao locatHandOverDao;
	@Resource
	public void setBaseDao( LocatHandOverDao  locatHandOverDao) {
		super.setBaseDao(locatHandOverDao);
	}
	
	
}
