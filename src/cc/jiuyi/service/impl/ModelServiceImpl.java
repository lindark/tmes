package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
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
	public Pager getModelPager(Pager pager, HashMap<String, String> map,String id,String team) {
		return modelDao.getModelPager(pager,map,id,team);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		modelDao.updateisdel(ids, oper);
	}

	@Override
	public Pager findByPager(Pager pager,HashMap<String, String> map, String id) {
		// TODO Auto-generated method stub
		return modelDao.findByPager(pager,map,id);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map,
			String id, String teamid) {
		return modelDao.historyExcelExport(map,id,teamid);
	}
}
