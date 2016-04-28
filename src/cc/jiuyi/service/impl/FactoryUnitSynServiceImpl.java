package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryUnitSynDao;
import cc.jiuyi.entity.FactoryUnitSyn;
import cc.jiuyi.service.FactoryUnitSynService;
/**
 * Service实现类 -单元同步
 */

@Service
public class FactoryUnitSynServiceImpl extends BaseServiceImpl<FactoryUnitSyn, String> implements
		FactoryUnitSynService {
	@Resource
	private FactoryUnitSynDao factoryUnitSynDao;
	
	@Resource
	public void setBaseDao(FactoryUnitSynDao factoryUnitSynDao){
		super.setBaseDao(factoryUnitSynDao);
	}

	@Override
	public Pager getFactoryUnitSynPager(Pager pager, HashMap<String, String> map) {
		return factoryUnitSynDao.getFactoryUnitSynPager(pager,map);
	}
	
	
	
}
