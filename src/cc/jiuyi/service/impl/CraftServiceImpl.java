package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CraftDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.service.CraftService;

/**
 * Service实现类 - 工艺
 */

@Service
public class CraftServiceImpl extends BaseServiceImpl<Craft, String> implements CraftService {

	@Resource
	private CraftDao craftDao;
	
	@Resource
	public void setBaseDao(CraftDao craftDao) {
		super.setBaseDao(craftDao);
	}


	@Override
	public Pager getCraftPager(Pager pager, HashMap<String, String> map,String id,String team) {
		return craftDao.getCraftPager(pager,map,id,team);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		craftDao.updateisdel(ids, oper);
	}


	@Override
	public Pager findByPager(Pager pager, HashMap<String, String> map,String id) {
		// TODO Auto-generated method stub
		return craftDao.findByPager(pager,map,id);
	}


	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map,
			String id, String teamid) {
		// TODO Auto-generated method stub
		return craftDao.historyExcelExport(map,id,teamid);
	}
}


