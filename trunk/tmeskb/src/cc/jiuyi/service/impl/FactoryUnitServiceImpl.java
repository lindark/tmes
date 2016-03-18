package cc.jiuyi.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CardManagementDao;
import cc.jiuyi.dao.FactoryUnitDao;
import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Products;
import cc.jiuyi.service.FactoryUnitService;

/**
 * Service实现类 -单元管理
 * @author Reece
 *
 */

@Service
public class FactoryUnitServiceImpl extends BaseServiceImpl<FactoryUnit, String>implements FactoryUnitService{

	@Resource
	private FactoryUnitDao factoryUnitDao;
	
	@Resource
	public void setBaseDao(FactoryUnitDao factoryUnitDao){
		super.setBaseDao(factoryUnitDao);
	}
	
	@Resource
	private CardManagementDao cmdao;
	
	@Override
	public void delete(String id) {
		FactoryUnit factoryUnit = factoryUnitDao.load(id);
		this.delete(factoryUnit);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<FactoryUnit> getFactoryUnitList() {		
		return factoryUnitDao.getFactoryUnitList();
	}

	@Override
	public Pager getFactoryUnitPager(Pager pager, HashMap<String, String> map) {
		return factoryUnitDao.getFactoryUnitPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		factoryUnitDao.updateisdel(ids, oper);
		
	}

	@Override
	public boolean isExistByFactoryUnitCode(String factoryUnitCode) {
		return factoryUnitDao.isExistByFactoryUnitCode(factoryUnitCode);
	}

	@Override
	public List<Products> getAllProducts() {
		return factoryUnitDao.getAllProducts();
	}

	/**
	 *  获取单元中的成本中心
	 */
	public Pager getCostCenter(Pager pager,String type)
	{
		return this.factoryUnitDao.getCostCenter(pager,type);
	}

	@Override
	public FactoryUnit getUnitByWorkCenter(String workCenter) {
		return factoryUnitDao.getUnitByWorkCenter(workCenter);
	}

	/**
	 * 根据ip获取单元
	 */
	public FactoryUnit getById(String ip)
	{
		CardManagement cm=this.cmdao.getByIp(ip);
		if(cm!=null&&cm.getFactoryunit()!=null)
		{
			return cm.getFactoryunit();
		}
		return null;
	}

	@Override
	public Pager getAllList(Pager pager, HashMap<String, String> map) {
		return this.factoryUnitDao.getAllList(pager,map);
	}
}
