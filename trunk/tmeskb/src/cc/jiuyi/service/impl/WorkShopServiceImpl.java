package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.WorkShopDao;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.entity.Product;
import cc.jiuyi.service.WorkShopService;

/**
 * Service实现类 -车间管理
 * @author Reece
 *
 */

@Service
public class WorkShopServiceImpl extends BaseServiceImpl<WorkShop, String>implements WorkShopService{

	@Resource
	private WorkShopDao workShopDao;
	
	@Resource
	public void setBaseDao(WorkShopDao workShopDao){
		super.setBaseDao(workShopDao);
	}
	
	@Override
	public void delete(String id) {
		WorkShop workShop = workShopDao.load(id);
		this.delete(workShop);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<WorkShop> getWorkShopList() {		
		return workShopDao.getWorkShopList();
	}

	@Override
	public Pager getWorkShopPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return workShopDao.getWorkShopPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		workShopDao.updateisdel(ids, oper);
		
	}

	
}
