package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Material;
import cc.jiuyi.service.CartonService;

/**
 * Service实现类 纸箱
 */
@Service
@Transactional
public class CartonServiceImpl extends BaseServiceImpl<Carton, String>
		implements CartonService {

	@Resource
	private CartonDao cartonDao;

	@Resource
	public void setBaseDao(CartonDao cartonDao) {
		super.setBaseDao(cartonDao);
	}

	@Override
	public Pager getCartonPager(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		return cartonDao.getCartonPager(pager, map, workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		cartonDao.updateisdel(ids, oper);

	}

}
