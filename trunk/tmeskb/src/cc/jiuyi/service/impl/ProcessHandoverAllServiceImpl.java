package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverAllDao;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.service.ProcessHandoverAllService;

/**
 * Service实现类 -总体工序交接
 *
 */
@Service
public class ProcessHandoverAllServiceImpl extends BaseServiceImpl<ProcessHandoverAll, String>
		implements ProcessHandoverAllService{
	@Resource
	private ProcessHandoverAllDao processHandoverAllDao ;
	
	@Resource
	public void setBaseDao(ProcessHandoverAllDao processHandoverAllDao){
		super.setBaseDao(processHandoverAllDao);
	}
	
	@Override
	public void saveAllProcess(Admin admin) {
		ProcessHandoverAll processHandoverAll = new ProcessHandoverAll();
		processHandoverAll.setIsdel("N");
		processHandoverAll.setProductDate(admin.getProductDate());
		processHandoverAll.setShift(admin.getShift());
		processHandoverAll.setState("1");
		processHandoverAll.setPhaCreateUser(admin);
		if(admin.getTeam()!=null){
			processHandoverAll.setTeamid(admin.getTeam().getId());
		}
		processHandoverAllDao.save(processHandoverAll);
	}

	@Override
	public Pager jqGrid(Pager pager, Admin admin) {
		return processHandoverAllDao.jqGrid(pager,admin);
	}
}
