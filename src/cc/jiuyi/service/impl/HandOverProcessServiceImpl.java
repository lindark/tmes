package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandOverProcessDao;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.service.HandOverProcessService;

/**
 * Service实现类 -工序交接
 * @author Reece
 *
 */

@Service
public class HandOverProcessServiceImpl extends BaseServiceImpl<HandOverProcess, String>implements HandOverProcessService{

	@Resource
	private HandOverProcessDao handOverProcessDao;
	
	@Resource
	public void setBaseDao(HandOverProcessDao handOverProcessDao){
		super.setBaseDao(handOverProcessDao);
	}
	
	@Override
	public void delete(String id) {
		HandOverProcess handOverProcess = handOverProcessDao.load(id);
		this.delete(handOverProcess);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<HandOverProcess> getHandOverProcessList() {		
		return handOverProcessDao.getHandOverProcessList();
	}

	@Override
	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return handOverProcessDao.getHandOverProcessPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		handOverProcessDao.updateisdel(ids, oper);
		
	}
	
	@Override
	public void save(List<HandOverProcess> handoverprocessList) {
		for(HandOverProcess handoverprocess : handoverprocessList){
			handOverProcessDao.save(handoverprocess);
		}
		
	}
	
}
