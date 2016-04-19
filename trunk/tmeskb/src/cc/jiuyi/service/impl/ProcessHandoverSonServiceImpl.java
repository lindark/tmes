package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.ProcessHandoverSonDao;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.service.ProcessHandoverSonService;
/**
 * Service实现类 -工序交接子类
 *
 */
@Service
public class ProcessHandoverSonServiceImpl extends BaseServiceImpl<ProcessHandoverSon, String>
		implements ProcessHandoverSonService {
	@Resource
	private ProcessHandoverSonDao processHandoverSonDao;
	@Resource
	public void setBaseDao(ProcessHandoverSonDao processHandoverSonDao){
		super.setBaseDao(processHandoverSonDao);
	}
}
