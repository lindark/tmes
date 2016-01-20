package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
}
