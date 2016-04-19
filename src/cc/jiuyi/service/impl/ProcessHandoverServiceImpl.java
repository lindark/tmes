package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ProcessHandoverService;
import cc.jiuyi.service.ProcessHandoverSonService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.WorkingBillService;
/**
 * Service实现类 -工序交接
 *
 */
@Service
public class ProcessHandoverServiceImpl extends BaseServiceImpl<ProcessHandover, String>
		implements ProcessHandoverService {
	@Resource
	private ProcessHandoverDao processHandoverDao;
	@Resource
	private AdminService adminService;
	@Resource
	private ProcessHandoverTopService processHandoverTopService;
	@Resource
	private ProcessHandoverSonService processHandoverSonService;
	@Resource
	private WorkingBillService workingBillService;
	
	
	@Resource
	public void setBaseDao(ProcessHandoverDao processHandoverDao){
		super.setBaseDao(processHandoverDao);
	}
	
	
	
	
	@Override
	public Pager jqGrid(Pager pager) {
		
		return processHandoverDao.jqGrid(pager);
	}




	@Override
	public void saveProcessHandover(ProcessHandoverTop processHandoverTop,
			List<ProcessHandover> processHandoverList,
			List<ProcessHandoverSon> processHandoverSonList, String loginid) {
		Admin admin = adminService.get(loginid);
		processHandoverTop.setCreateUser(admin);
		processHandoverTop.setState("1");
		processHandoverTop.setIsdel("N");
		processHandoverTopService.save(processHandoverTop);
		for(int i=0;i<processHandoverList.size();i++){
			ProcessHandover processHandover = processHandoverList.get(i);
			if(processHandover!=null){
				WorkingBill wb = workingBillService.get("workingBillCode", processHandover.getWorkingBillCode());
				processHandover.setWorkingBill(wb);
				processHandover.setProcessHandoverTop(processHandoverTop);
				processHandoverDao.save(processHandover);
				for(int j=0;j<processHandoverSonList.size();j++){
					ProcessHandoverSon processHandoverSon = processHandoverSonList.get(j);
					if(processHandoverSon!=null){
						if(processHandoverSon.getBeforeWorkingCode().equals(processHandover.getWorkingBillCode())){
							processHandoverSon.setProcessHandover(processHandover);
							processHandoverSonService.save(processHandoverSon);
						}
					}
				}
			}
			
		}
	}




	@Override
	public void updateProcessHandover(ProcessHandoverTop processHandoverTop,
			List<ProcessHandover> processHandoverList,
			List<ProcessHandoverSon> processHandoverSonList, String loginid) {
		Admin admin = adminService.get(loginid);
		processHandoverTop.setCreateUser(admin);
		ProcessHandoverTop processHandoverTopcopy = processHandoverTopService.get(processHandoverTop.getId());
		BeanUtils.copyProperties(processHandoverTop, processHandoverTopcopy, new String[]{"id", "createDate","isdel","state"});
		processHandoverTopService.update(processHandoverTopcopy);
		for(int i=0;i<processHandoverList.size();i++){
			ProcessHandover processHandover = processHandoverList.get(i);
			if(processHandover!=null){
				ProcessHandover processHandovercopy = processHandoverDao.get(processHandover.getId());
				BeanUtils.copyProperties(processHandover, processHandovercopy, new String[]{"id", "createDate","processHandoverTop","workingBill"});
				processHandoverDao.update(processHandovercopy);
				for(int j=0;j<processHandoverSonList.size();j++){
					ProcessHandoverSon processHandoverSon = processHandoverSonList.get(j);
					if(processHandoverSon!=null){
						if(processHandoverSon.getBeforeWorkingCode().equals(processHandover.getWorkingBillCode())){
							ProcessHandoverSon processHandoverSoncopy = processHandoverSonService.get(processHandoverSon.getId());
							BeanUtils.copyProperties(processHandoverSon, processHandoverSoncopy, new String[]{"id", "createDate","processHandover"});
							processHandoverSonService.update(processHandoverSoncopy);
						}
					}
				}
			}
			
		}
	}
}
