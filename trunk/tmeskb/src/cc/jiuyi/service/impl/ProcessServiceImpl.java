package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Product;
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

	@Override
	public List<Process> getProcessList() {		
		return processDao.getProcessList();
	}

	@Override
	public Pager getProcessPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return processDao.getProcessPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		processDao.updateisdel(ids, oper);
		
	}

	@Override
	public boolean isExistByProccessCode(String processCode) {
		// TODO Auto-generated method stub
		return processDao.isExistByProcessCode(processCode);
	}

	
}
