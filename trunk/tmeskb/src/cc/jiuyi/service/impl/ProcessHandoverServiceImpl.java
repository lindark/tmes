package cc.jiuyi.service.impl;

import java.io.IOException;
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
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ProcessHandoverService;
import cc.jiuyi.service.ProcessHandoverSonService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.CustomerException;
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
	private WorkingInoutService workinginoutservice;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	
	@Resource
	public void setBaseDao(ProcessHandoverDao processHandoverDao){
		super.setBaseDao(processHandoverDao);
	}
	
	
	
	
	@Override
	public Pager jqGrid(Pager pager) {
		
		return processHandoverDao.jqGrid(pager);
	}

	@Override
	public Pager jqGrid(Pager pager, Admin admin) {
		return processHandoverDao.jqGrid(pager,admin);
	}


	@Override
	public void saveProcessHandover(ProcessHandoverTop processHandoverTop,
			List<ProcessHandover> processHandoverList,
			List<ProcessHandoverSon> processHandoverSonList, String loginid) {
//		Admin admin = adminService.get(loginid);
//		processHandoverTop.setCreateUser(admin);
//		processHandoverTop.setState("1");
//		processHandoverTop.setIsdel("N");
//		processHandoverTop.setType("工序交接");
//		processHandoverTopService.save(processHandoverTop);
//		for(int i=0;i<processHandoverList.size();i++){
//			ProcessHandover processHandover = processHandoverList.get(i);
//			if(processHandover!=null){
//				WorkingBill wb = workingBillService.get("workingBillCode", processHandover.getWorkingBillCode());
//				processHandover.setWorkingBill(wb);
//				processHandover.setProcessHandoverTop(processHandoverTop);
//				processHandoverDao.save(processHandover);
//				for(int j=0;j<processHandoverSonList.size();j++){
//					ProcessHandoverSon processHandoverSon = processHandoverSonList.get(j);
//					if(processHandoverSon!=null){
//						if(processHandoverSon.getBeforeWorkingCode().equals(processHandover.getWorkingBillCode())){
//							processHandoverSon.setProcessHandover(processHandover);
//							processHandoverSonService.save(processHandoverSon);
		try {
			Admin admin = adminService.get(loginid);
			processHandoverTop.setCreateUser(admin);
			processHandoverTop.setState("1");
			processHandoverTop.setIsdel("N");
			processHandoverTop.setType("工序交接");
			processHandoverTopService.save(processHandoverTop);
			for(int i=0;i<processHandoverList.size();i++){
				ProcessHandover processHandover = processHandoverList.get(i);
				if(processHandover!=null){
					WorkingBill wb = workingBillService.get("workingBillCode", processHandover.getWorkingBillCode());
					WorkingBill afterWb = workingBillService.get("workingBillCode", processHandover.getAfterWorkingBillCode());
					processHandover.setWorkingBill(wb);
					processHandover.setAfterworkingbill(afterWb);
					processHandover.setProcessid(processHandoverTop.getProcessid());
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
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
	}




	@Override
	public void updateProcessHandover(ProcessHandoverTop processHandoverTop,
			List<ProcessHandover> processHandoverList,
			List<ProcessHandoverSon> processHandoverSonList, String loginid) {
		Admin admin = adminService.get(loginid);
		processHandoverTop.setCreateUser(admin);
		ProcessHandoverTop processHandoverTopcopy = processHandoverTopService.get(processHandoverTop.getId());
		BeanUtils.copyProperties(processHandoverTop, processHandoverTopcopy, new String[]{"id", "createDate","isdel","state","type"});
		processHandoverTopService.update(processHandoverTopcopy);
		for(int i=0;i<processHandoverList.size();i++){
			ProcessHandover processHandover = processHandoverList.get(i);
			if(processHandover!=null){
				ProcessHandover processHandovercopy = processHandoverDao.get(processHandover.getId());
				BeanUtils.copyProperties(processHandover, processHandovercopy, new String[]{"id", "createDate","processHandoverTop","workingBill","isdel"});
				WorkingBill afterWb = workingBillService.get("workingBillCode", processHandover.getAfterWorkingBillCode());
				processHandovercopy.setAfterworkingbill(afterWb);
				processHandovercopy.setProcessid(processHandoverTopcopy.getProcessid());
				processHandoverDao.update(processHandovercopy);
				for(int j=0;j<processHandoverSonList.size();j++){
					ProcessHandoverSon processHandoverSon = processHandoverSonList.get(j);
					if(processHandoverSon!=null){
						if(processHandoverSon.getBeforeWorkingCode().equals(processHandover.getWorkingBillCode())){
							ProcessHandoverSon processHandoverSoncopy = processHandoverSonService.get(processHandoverSon.getId());
							BeanUtils.copyProperties(processHandoverSon, processHandoverSoncopy, new String[]{"id", "createDate","processHandover","isdel"});
							processHandoverSonService.update(processHandoverSoncopy);
						}
					}
				}
			}
			
		}
	}




	@Override
	public String saveApproval(String cardnumber, String id, String loginid) {
		Admin admin = adminService.getByCardnum(cardnumber);
		ProcessHandoverTop processHandoverTop = processHandoverTopService.get(id);
		String budat = null;
		for(ProcessHandover p : processHandoverTop.getProcessHandOverSet()){
			boolean flag =false;
			if(p.getProductAmount()!=null && p.getProductAmount().equals("")){
				flag = true;
			}
			if(p.getActualHOMount()!=null && !p.getActualHOMount().equals("")){
				flag = true;
			}
			if(p.getUnHOMount()!=null && !p.getUnHOMount().equals("")){
				flag = true;
			}
			
			boolean flag1 = workinginoutservice.isExist(p.getWorkingBill().getId(), p.getMatnr());
			if(!flag1){//如果不存在，新增  --- 上一随工单信息
				WorkingInout workinginout = new WorkingInout();
				workinginout.setWorkingbill(p.getWorkingBill());
				workinginout.setMaterialCode(p.getMatnr());
				workinginout.setMaterialName(p.getMaktx());
				workinginoutservice.save(workinginout);
			}
			WorkingBill AfterWorkingbill = workingBillService.get("workingBillCode", p.getAfterWorkingBillCode());
			if(AfterWorkingbill==null){
				return "请填写正确的下班随工单";
			}
			boolean flag2 = workinginoutservice.isExist(AfterWorkingbill.getId(),p.getMatnr());
			if(!flag2){//如果不存在,新增 --- 下一随工单信息
				WorkingInout workinginout = new WorkingInout();
				workinginout.setWorkingbill(AfterWorkingbill);
				workinginout.setMaterialCode(p.getMatnr());
				workinginout.setMaterialName(p.getMaktx());
				workinginoutservice.save(workinginout);
			}
			for(ProcessHandoverSon ps:p.getProcessHandoverSonSet()){
				if(ps.getBomAmount()!=null && !ps.getBomAmount().equals("")){
					flag = true;
				}
				if(ps.getRepairNumber()!=null && !ps.getRepairNumber().equals("")){
					flag = true;
				}
				if(ps.getCqamount()!=null && !ps.getCqamount().equals("")){
					flag = true;
				}
				if(ps.getCqrepairamount()!=null && !ps.getCqrepairamount().equals("")){
					flag = true;
				}
			}
			if(flag){
				ProcessHandover ProcessHandover;
				try {
					ProcessHandover = handoverprocessrfc.BatchProcessHandOver(p, "",loginid);
				} catch (IOException e) {
					e.printStackTrace();
					return "IO出现异常，请联系系统管理员";
				} catch (CustomerException e) {
					e.printStackTrace();
					return e.getMsgDes();
				}
				if(budat==null)
				//budat = ProcessHandover.getBudat();
				//p.setBudat(ProcessHandover.getBudat());
				p.setE_message(ProcessHandover.getE_message());
				p.setE_type(ProcessHandover.getE_type());
				p.setMblnr(ProcessHandover.getMblnr());
				processHandoverDao.update(p);
			}
		}
		processHandoverTop.setConfimUser(admin);
		processHandoverTop.setBudat(budat);
		processHandoverTop.setState("2");
		processHandoverTopService.update(processHandoverTop);
		return "您的操作已成功!";
	}

	


	
}
