package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.QualityProblemDescriptionDao;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.QualityProblemDescription;
import cc.jiuyi.service.QualityProblemDescriptionService;

/**
 * Service实现类 -故障原因管理
 *
 */

@Service
public class QualityProblemDescriptionServiceImpl extends BaseServiceImpl<QualityProblemDescription, String>implements QualityProblemDescriptionService{

	@Resource
	private QualityProblemDescriptionDao qualityProblemDescriptionDao;
	
	@Resource
	public void setBaseDao(QualityProblemDescriptionDao qualityProblemDescriptionDao){
		super.setBaseDao(qualityProblemDescriptionDao);
	}

	@Override
	public Pager getQualityProblemDescriptionPager(Pager pager, HashMap<String, String> map) {
		return qualityProblemDescriptionDao.getQualityProblemDescriptionPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		qualityProblemDescriptionDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<QualityProblemDescription> getAllQualityProblemDescription() {
		// TODO Auto-generated method stub
		return qualityProblemDescriptionDao.getAllQualityProblemDescription();
	}
}
