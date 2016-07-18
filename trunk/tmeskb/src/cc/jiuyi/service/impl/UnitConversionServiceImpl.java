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
		return unitConversionDao.getUnitConversionPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		unitConversionDao.updateisdel(ids, oper);
		
	}

	@Override
	public Integer getSingleConversationRatio(String unitDescription,
			String convertUnit) {
		return unitConversionDao.getSingleConversationRatio(unitDescription, convertUnit);
	}

	@Override
	public Integer getRatioByCode(String unitCode) {
		return unitConversionDao.getRatioByCode(unitCode);
	}

	@Override
	public UnitConversion getRatioByMatnr(String matnr,String unitCode) {
		return unitConversionDao.getRatioByMatnr(matnr,unitCode);
	}

	public void saveorupdate(List<UnitConversion> unitConversionList) {
		for(int i=0;i<unitConversionList.size();i++){
			UnitConversion unit = unitConversionList.get(i);
			String[] propertyNames = {"unitCode","matnr"};
			Object[] propertyValues = {unit.getUnitCode(),unit.getMatnr()};
			UnitConversion unitconversion = this.get(propertyNames,propertyValues);
			if(unitconversion != null){
				unit.setId(unitconversion.getId());
			}
			unitConversionDao.merge(unit);
			
		}
	}

	@Override
	public Object sumAmount(String matnr) {
		return unitConversionDao.sumAmount(matnr);
	}
	
	

	
}
