package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.QualityDao;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.QualityService;

/**
 * Service实现类 - 质量问题通知单
 */

@Service
public class QualityServiceImpl extends BaseServiceImpl<Quality, String> implements QualityService {

	@Resource
	private QualityDao qualityDao;
	
	@Resource
	public void setBaseDao(QualityDao qualityDao) {
		super.setBaseDao(qualityDao);
	}
	
	public Pager getQualityPager(Pager pager,HashMap<String,String> map,String id,String team){
		return qualityDao.getQualityPager(pager,map,id,team);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		qualityDao.updateisdel(ids, oper);
	}

	public Pager findByPager(Pager pager,HashMap<String,String> map, String id) {
		return qualityDao.findByPager(pager,map,id);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map,String id,String teamid) {
		return qualityDao.historyExcelExport(map,id,teamid);
	}
	
	
}
