package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CartonsonDao;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.service.CartonsonService;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Service
public class CartonsonServiceImpl extends BaseServiceImpl<CartonSon, String> implements CartonsonService
{
	@Resource
	public CartonsonDao cartonsonDao;
	@Resource
	public void setBaseDao(CartonsonDao cartonsonDao) {
		super.setBaseDao(cartonsonDao);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return cartonsonDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return cartonsonDao.historyExcelExport(map);
	}
}
