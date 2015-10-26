package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.service.ProcessService;

/**
 * Services实现类 -工序管理
 * @author Reece
 *
 */
@Service
public class PrcocessServiceImpl extends BaseServiceImpl<Process, String> implements ProcessService{

	@Resource
	public void setBaseDao(ProcessDao procssDao){
		super.setBaseDao(procssDao);		
	}
}
