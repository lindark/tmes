package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.service.FactoryService;

/**
 * Service实现类 -工序管理
 * @author Reece
 *
 */

@Service
public class FactoryServiceImpl extends BaseServiceImpl<Factory, String>implements FactoryService{

	@Resource
	private FactoryDao factoryDao;
	
	@Resource
	public void setBaseDao(FactoryDao FactoryDao){
		super.setBaseDao(FactoryDao);
	}
	
	@Override
	public void delete(String id) {
		Factory Factory = factoryDao.load(id);
		this.delete(Factory);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Factory> getFactoryList() {		
		return factoryDao.getFactoryList();
	}

	@Override
	public Pager getFactoryPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return factoryDao.getFactoryPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		factoryDao.updateisdel(ids, oper);
	}

	@Override
	public boolean isExistByFactoryCode(String factoryCode) {
		// TODO Auto-generated method stub
		return factoryDao.isExistByFactoryCode(factoryCode);
	}


	
}
