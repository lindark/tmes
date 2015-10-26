package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.service.ProcessService;

/**
 * Service实现类 -工序管理
 * @author Reece
 *
 */
@Service
public class ProcessServiceImpl extends BaseServiceImpl<Process, String>implements ProcessService{

	@Resource
	private ProcessDao processDao;
	
	@Resource
	public void setBaseDao(ProcessDao processDao){
		super.setBaseDao(processDao);
	}
	
	@Override
	public void delete(String id) {
		Process process = processDao.load(id);
		this.delete(process);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}
	
}
