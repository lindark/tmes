package cc.jiuyi.service.impl;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ProcessHandoverTopDao;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.service.ProcessHandoverTopService;
/**
 * Service实现类 -工序交接
 *
 */
@Service
public class ProcessHandoverTopServiceImpl extends BaseServiceImpl<ProcessHandoverTop, String>
		implements ProcessHandoverTopService {
	@Resource
	private ProcessHandoverTopDao processHandoverTopDao;
	@Resource
	public void setBaseDao(ProcessHandoverTopDao processHandoverTopDao){
		super.setBaseDao(processHandoverTopDao);
	}
}
