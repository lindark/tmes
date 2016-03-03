package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.MouldmaterialDao;
import cc.jiuyi.entity.Mouldmaterial;
import cc.jiuyi.service.MouldmaterialService;

/**
 * Service实现类 - 模具物料管理
 */

@Service
public class MouldmaterialServiceImpl extends BaseServiceImpl<Mouldmaterial, String> implements MouldmaterialService {

	@Resource
	private MouldmaterialDao mouldDao;
	
	@Resource
	public void setBaseDao(MouldmaterialDao mouldDao) {
		super.setBaseDao(mouldDao);
	}

	@Override
	public Pager getMouldmaterialPager(Pager pager, HashMap<String, String> map) {
	
		return mouldDao.getMouldmaterialPager(pager, map);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		mouldDao.updateisdel(ids, oper);
	}
	
}
