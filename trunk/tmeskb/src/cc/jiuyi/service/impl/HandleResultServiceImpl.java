package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandleResultDao;
import cc.jiuyi.entity.HandlemeansResults;
import cc.jiuyi.service.HandleResultService;

/**
 * Service实现类 -故障处理方法与结果
 *
 */

@Service
public class HandleResultServiceImpl extends BaseServiceImpl<HandlemeansResults, String>implements HandleResultService{

	@Resource
	private HandleResultDao handleResultDao;
	
	@Resource
	public void setBaseDao(HandleResultDao handleResultDao){
		super.setBaseDao(handleResultDao);
	}

	@Override
	public Pager getHandlePager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return handleResultDao.getHandlePager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		handleResultDao.updateisdel(ids,oper);
		
	}

	@Override
	public List<HandlemeansResults> getAllHandle() {
		// TODO Auto-generated method stub
		return handleResultDao.getAllHandle();
	}
	
	
	
}
