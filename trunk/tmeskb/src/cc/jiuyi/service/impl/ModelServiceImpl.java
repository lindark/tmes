package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.dao.ModelDao;
import cc.jiuyi.entity.Model;
import cc.jiuyi.service.ModelService;

/**
 * Service实现类 - 工模维修单
 */

@Service
public class ModelServiceImpl extends BaseServiceImpl<Model, String> implements ModelService {

	@Resource
	private ModelDao modelDao;
	
	@Resource
	public void setBaseDao(ModelDao modelDao) {
		super.setBaseDao(modelDao);
	}

	@Override
	public Pager getModelPager(Pager pager, HashMap<String, String> map) {
		return modelDao.getModelPager(pager,map);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		modelDao.updateisdel(ids, oper);
	}
}
