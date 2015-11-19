package cc.jiuyi.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UnitConversionDao;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.service.UnitConversionService;

/**
 * Service实现类 -单位转化
 * @author Reece
 *
 */

@Service
public class UnitConversionServiceImpl extends BaseServiceImpl<UnitConversion, String>implements UnitConversionService{

	@Resource
	private UnitConversionDao unitConversionDao;
	
	@Resource
	public void setBaseDao(UnitConversionDao unitConversionDao){
		super.setBaseDao(unitConversionDao);
	}
	
	@Override
	public void delete(String id) {
		UnitConversion unitConversion = unitConversionDao.load(id);
		this.delete(unitConversion);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<UnitConversion> getUnitConversionList() {		
		return unitConversionDao.getUnitConversionList();
	}

	@Override
	public Pager getUnitConversionPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return unitConversionDao.getUnitConversionPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		unitConversionDao.updateisdel(ids, oper);
		
	}

	@Override
	public Integer getSingleConversationRatio(String unitDescription,
			String convertUnit) {
		return unitConversionDao.getSingleConversationRatio(unitDescription, convertUnit);
	}

	
}
