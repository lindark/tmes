package cc.jiuyi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.WorkingBillService;
/**
 * Service实现类 - 零头数交接
 */

@Service
public class OddHandOverServiceImpl extends BaseServiceImpl<OddHandOver, String> implements
		OddHandOverService {
	@Resource
	private OddHandOverDao oddHandOverDao;
	@Resource
	private ProcessHandoverDao processHandoverDao;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource 
	private AdminService adminService;
	@Resource 
	private ProcessHandoverTopService processHandoverTopService;

	public void setBaseDao(OddHandOverDao oddHandOverDao) {
		super.setBaseDao(oddHandOverDao);
	}

	public OddHandOver findHandOver(String workingBillCode) {
		return oddHandOverDao.findHandOver(workingBillCode);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return oddHandOverDao.historyjqGrid(pager, map);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return oddHandOverDao.historyExcelExport(map);
	}

	@Override
	public void updateOddHandOver(ProcessHandoverTop processHandoverTop,List<ProcessHandover> processHandoverList,
			List<OddHandOver> OddHandOverList, String loginid) {

		Admin admin = adminService.get(loginid);
		processHandoverTop.setCreateUser(admin);
		ProcessHandoverTop processHandoverTopcopy = processHandoverTopService.get(processHandoverTop.getId());
		BeanUtils.copyProperties(processHandoverTop, processHandoverTopcopy, new String[]{"id", "createDate","isdel","state","type"});
		processHandoverTopService.update(processHandoverTopcopy);
		for(int i=0;i<processHandoverList.size();i++){
			ProcessHandover processHandover = processHandoverList.get(i);
			if(processHandover!=null){
				ProcessHandover processHandovercopy = processHandoverDao.get(processHandover.getId());
				BeanUtils.copyProperties(processHandover, processHandovercopy, new String[]{"id", "createDate","processHandoverTop","workingBill"});
				processHandoverDao.update(processHandovercopy);
				for(int j=0;j<OddHandOverList.size();j++){
					OddHandOver oddHandOver = OddHandOverList.get(j);
					if(oddHandOver!=null){
						if(oddHandOver.getBeforeWokingCode().equals(processHandover.getWorkingBillCode())){
							OddHandOver oddHandOver1 = oddHandOverDao.get(oddHandOver.getId());
							BeanUtils.copyProperties(oddHandOver, oddHandOver1, new String[]{"id", "createDate","processHandover"});
							Double actualHOMount = processHandover.getActualHOMount();
							Double unHOMount = processHandover.getUnHOMount();
							if(actualHOMount != null){
								BigDecimal amount = new BigDecimal(actualHOMount);
								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
						//		Double mount = amount.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
								oddHandOver1.setActualBomMount(mount);
							}
							if(unHOMount != null){
								BigDecimal amount = new BigDecimal(unHOMount);
								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
					//			Double mount = amount.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
								oddHandOver1.setUnBomMount(mount);
							}
							oddHandOverDao.update(oddHandOver1);
						}
					}
				}
			}
			
		}
	
	}

	@Override
	public OddHandOver findById(String id) {
		OddHandOver oodHandOver = oddHandOverDao.get(id);
		return oodHandOver;
	}

	@Override
	public void updateOddHandOver(OddHandOver oddHandOver) {
		oddHandOverDao.update(oddHandOver);
	}

	@Override
	public void saveOddHandOverList(ProcessHandoverTop processHandoverTop,
			List<OddHandOver> OddHandOverList,
			List<ProcessHandover> processHandoverList, String loginid) {

		Admin admin = adminService.get(loginid);
		processHandoverTop.setCreateUser(admin);
		processHandoverTop.setState("1");
		processHandoverTop.setIsdel("N");
		processHandoverTop.setType("零头数交接");
		processHandoverTopService.save(processHandoverTop);
//		for(int i=0;i<processHandoverList.size();i++){
//			ProcessHandover processHandover = processHandoverList.get(i);
//			if(processHandover!=null){
//				WorkingBill wb = workingbillservice.get("workingBillCode", processHandover.getWorkingBillCode());
//				processHandover.setWorkingBill(wb);
//				processHandover.setProcessHandoverTop(processHandoverTop);
//				processHandoverDao.save(processHandover);
//				for(int j=0;j<OddHandOverList.size();j++){
//					OddHandOver oddHandOver = OddHandOverList.get(j);
//					if(oddHandOver!=null){
//					oddHandOver.setBeforeWokingCode(processHandover.getWorkingBillCode());
//					oddHandOver.setAfterWorkingCode(processHandover.getAfterWorkingBillCode());
//					Double actualHOMount = processHandover.getActualHOMount();
//					Double unHOMount = processHandover.getUnHOMount();
//						if(oddHandOver.getBeforeWokingCode().equals(processHandover.getWorkingBillCode())){
//							if(actualHOMount != null){
//								BigDecimal amount = new BigDecimal(actualHOMount);
//								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
//								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
//								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
//						//		Double mount = amount.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//								oddHandOver.setActualBomMount(mount);
//							}
//							if(unHOMount != null){
//								BigDecimal amount = new BigDecimal(unHOMount);
//								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
//								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
//								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
//					//			Double mount = amount.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
//								oddHandOver.setUnBomMount(mount);
//							}
//							oddHandOver.setProcessHandover(processHandover);
//							oddHandOverDao.save(oddHandOver);
//						}
//					}
//				}
//			}
//			
//		}
		for(int i=0;i<processHandoverList.size();i++){
			ProcessHandover processHandover = processHandoverList.get(i);
			if(processHandover!=null){
				WorkingBill wb = workingbillservice.get("workingBillCode", processHandover.getWorkingBillCode());
				processHandover.setWorkingBill(wb);
				processHandover.setProcessHandoverTop(processHandoverTop);
				processHandoverDao.save(processHandover);
				for(int j=0;j<OddHandOverList.size();j++){
					OddHandOver oddHandOver = OddHandOverList.get(j);
					Double actualHOMount = processHandover.getActualHOMount();
					Double unHOMount = processHandover.getUnHOMount();
					if(oddHandOver!=null){
						if(oddHandOver.getBeforeWokingCode().equals(processHandover.getWorkingBillCode())){
							if(actualHOMount != null){
								BigDecimal amount = new BigDecimal(actualHOMount);
								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
								oddHandOver.setActualBomMount(mount);
							}
							if(unHOMount != null){
								BigDecimal amount = new BigDecimal(unHOMount);
								BigDecimal bmAmount = new BigDecimal(oddHandOver.getMaterialAmount());
								BigDecimal plAmount = new BigDecimal(processHandover.getPlanCount());
								Double mount = amount.multiply((bmAmount).divide(plAmount,3,BigDecimal.ROUND_HALF_UP)).doubleValue();
								oddHandOver.setUnBomMount(mount);
							}
							oddHandOver.setProcessHandover(processHandover);
							oddHandOver.setBeforeWokingCode(processHandover.getWorkingBillCode());
							oddHandOver.setAfterWorkingCode(processHandover.getAfterWorkingBillCode());
							oddHandOverDao.save(oddHandOver);
						}
					}
				}
			}
		}
	}

}
