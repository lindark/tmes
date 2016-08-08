package cc.jiuyi.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.RollbackException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.entity.WorkingInout;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.OddHandOverService;
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
			List<ProcessHandoverSon> processHandoverSonList, String loginid) throws CustomerException {
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

			Admin admin = adminService.get(loginid);
			processHandoverTop.setPhtcreateUser(admin);
			processHandoverTop.setState("1");
			processHandoverTop.setIsdel("N");
			processHandoverTop.setType("工序交接");
			processHandoverTopService.save(processHandoverTop);
			for(int i=0;i<processHandoverList.size();i++){
				ProcessHandover processHandover = processHandoverList.get(i);
				if(processHandover!=null){
					WorkingBill wb = workingBillService.get("workingBillCode", processHandover.getWorkingBillCode());
					WorkingBill afterWb = workingBillService.get("workingBillCode", processHandover.getAfterWorkingBillCode()==null?"":processHandover.getAfterWorkingBillCode());
					if(afterWb==null)throw new RollbackException("找不到下班随工单或填写错误");
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
								processHandoverSon.setAfterWokingCode(processHandover.getAfterWorkingBillCode());
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
			List<ProcessHandoverSon> processHandoverSonList, String loginid) throws CustomerException {

			Admin admin = adminService.get(loginid);
			processHandoverTop.setPhtcreateUser(admin);
			ProcessHandoverTop processHandoverTopcopy = processHandoverTopService.get(processHandoverTop.getId());
			BeanUtils.copyProperties(processHandoverTop, processHandoverTopcopy, new String[]{"id", "createDate","isdel","state","type","budat","processHandOverSet"});
			processHandoverTopService.update(processHandoverTopcopy);
			List<ProcessHandover> phList = new ArrayList<ProcessHandover>(processHandoverTopcopy.getProcessHandOverSet());
			for(int i=0;i<processHandoverList.size();i++){
				ProcessHandover processHandover = processHandoverList.get(i);
				if(processHandover!=null){
					ProcessHandover processHandovercopy = processHandoverDao.get(processHandover.getId());
					for(ProcessHandover ph:phList){
						if(ph.getId().equals(processHandovercopy.getId())){
							phList.remove(ph);
							break;
						}
					}
					if(processHandover.getMatnr()!=null && !"".equals(processHandover.getMatnr()))continue;
					WorkingBill afterWb = workingBillService.get("workingBillCode", processHandover.getAfterWorkingBillCode()==null?"":processHandover.getAfterWorkingBillCode());
					if(afterWb==null){
						throw new RollbackException("找不到下班随工单或填写错误");
					}
					BeanUtils.copyProperties(processHandover, processHandovercopy, new String[]{"id", "createDate","processHandoverTop","workingBill","isdel","budat","ztext","mblnr","xuh","e_type","e_message","processHandoverSonSet"});
					processHandovercopy.setAfterworkingbill(afterWb);
					processHandovercopy.setProcessid(processHandoverTopcopy.getProcessid());
					processHandoverDao.update(processHandovercopy);
					for(int j=0;j<processHandoverSonList.size();j++){
						ProcessHandoverSon processHandoverSon = processHandoverSonList.get(j);
						if(processHandoverSon!=null){
							if(processHandoverSon.getBeforeWorkingCode().equals(processHandover.getWorkingBillCode())){
								processHandoverSon.setAfterWokingCode(processHandovercopy.getAfterWorkingBillCode());
								ProcessHandoverSon processHandoverSoncopy = processHandoverSonService.get(processHandoverSon.getId());
								BeanUtils.copyProperties(processHandoverSon, processHandoverSoncopy, new String[]{"id", "createDate","processHandover","isdel"});
								processHandoverSonService.update(processHandoverSoncopy);
							}
						}
					}
				}
			}
			for(ProcessHandover ph:phList){
				List<ProcessHandoverSon> phsList = new ArrayList<ProcessHandoverSon>(ph.getProcessHandoverSonSet());
				for(ProcessHandoverSon phs : phsList){
					processHandoverSonService.delete(phs);
				}
				processHandoverDao.delete(ph);
			}
	}




	@Override
	public Map<String,String> saveApproval(String cardnumber, String id1, String loginid) throws IOException, CustomerException {
		HashMap<String,String> map = new HashMap<String,String>();
		Admin admin = adminService.getByCardnum(cardnumber);
		String[] ids = id1.split(",");
		for(String id:ids){
		ProcessHandoverTop processHandoverTop = processHandoverTopService.get(id);
		String budat = null;
		for(ProcessHandover p : processHandoverTop.getProcessHandOverSet()){
			WorkingBill AfterWorkingbill = workingBillService.get("workingBillCode", p.getAfterWorkingBillCode()==null?"":p.getAfterWorkingBillCode());
			if(AfterWorkingbill==null){
				map.put("status", "E");
				map.put("massge", "请填写正确的下班随工单");
				return map;
			}
		}
		for(ProcessHandover p : processHandoverTop.getProcessHandOverSet()){
			boolean flag =false;
		//	System.out.println(p.getProductAmount());
			if(p.getProductAmount()!=null && !p.getProductAmount().equals("")){
				flag = true;
			}
			if(p.getActualHOMount()!=null && !p.getActualHOMount().equals("")){
				flag = true;
			}
			if(p.getUnHOMount()!=null && !p.getUnHOMount().equals("")){
				flag = true;
			}
			if(p.getMblnr()!=null&&!p.getMblnr().equals("")){
				continue;
			}
			WorkingBill AfterWorkingbill = workingBillService.get("workingBillCode", p.getAfterWorkingBillCode()==null?"":p.getAfterWorkingBillCode());
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
				if("工序交接".equals(p.getProcessHandoverTop().getType())){
					workinginoutservice.saveWorkinginout1(p.getWorkingBill(),ps.getBomCode(), ps.getBomDesp(),0.0d,"hand");
					workinginoutservice.saveWorkinginout1(AfterWorkingbill,ps.getBomCode(), ps.getBomDesp(),0.0d,"hand");
				}
			}
			if(flag){
				
				//try {
				ProcessHandover ProcessHandover = handoverprocessrfc.BatchProcessHandOver(p, "",loginid);
			/*	} catch (IOException e) {
					e.printStackTrace();
					map.put("staus", "E");
					map.put("massge","IO出现异常，请联系系统管理员");
					return map;
				} catch (CustomerException e) {
					e.printStackTrace();
					map.put("staus", "E");
					map.put("massge",e.getMsgDes());
					return map;
				}*/
				if(budat==null)
				budat = ProcessHandover.getBudat();
				p.setBudat(ProcessHandover.getBudat());
				p.setE_message(ProcessHandover.getE_message());
				p.setE_type(ProcessHandover.getE_type());
				p.setMblnr(ProcessHandover.getMblnr());
				processHandoverDao.update(p);
			}
		}
		processHandoverTop.setPhtconfimUser(admin);
		processHandoverTop.setBudat(budat);
		processHandoverTop.setState("2");
		processHandoverTopService.update(processHandoverTop);
		}

		map.put("status", "S");
		map.put("massge","您的操作已成功!");
		return map;
	}

	

	@Override
	public Map<String,String> saveRevoked(String cardnumber, String id, String loginid) throws IOException, CustomerException {
		HashMap<String,String> map = new HashMap<String,String>();
		Admin admin = adminService.getByCardnum(cardnumber);
		Date date = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String time = dateFormat.format(date);
//		ProcessHandoverTop processHandoverTop = processHandoverTopService.get(id);
//		if(processHandoverTop.getState().equals("1")||processHandoverTop.getState().equals("2")){
//			if(processHandoverTop.getType().equals("工序交接")){
//			processHandoverTop.setIsdel("Y");
//			processHandoverTop.setState("3");
//	//		processHandoverTop.setPhtconfimUser(admin);
//			processHandoverTop.setRevokedTime(time);
//			processHandoverTop.setRevokedUser(admin.getName());
//			processHandoverTop.setRevokedUserCard(cardnumber);
//			processHandoverTop.setRevokedUserId(admin.getId());
//			processHandoverTopService.update(processHandoverTop);
//			Set<ProcessHandover> ProcessHandoverSet = processHandoverTop.getProcessHandOverSet();
//			for(ProcessHandover p:ProcessHandoverSet){
//				p.setIsdel("Y");
//				processHandoverService.update(p);
//				Set<ProcessHandoverSon> processHandoverSonSet = p.getProcessHandoverSonSet();
//				for(ProcessHandoverSon ps:processHandoverSonSet){
//					ps.setIsdel("Y");
//					processHandoverSonService.update(ps);
//					}
//				}
//			}else{
//				processHandoverTop.setIsdel("Y");
//				processHandoverTop.setState("3");
//		//		processHandoverTop.setPhtconfimUser(admin);
//				processHandoverTop.setRevokedTime(time);
//				processHandoverTop.setRevokedUser(admin.getName());
//				processHandoverTop.setRevokedUserCard(cardnumber);
//				processHandoverTop.setRevokedUserId(admin.getId());
//				processHandoverTopService.update(processHandoverTop);
//				Set<ProcessHandover> ProcessHandoverSet = processHandoverTop.getProcessHandOverSet();
//				for(ProcessHandover p:ProcessHandoverSet){
//					p.setIsdel("Y");
//					processHandoverService.update(p);
//					Set<OddHandOver> oddHandOverSet = p.getOddHandOverSet();
//					for(OddHandOver os:oddHandOverSet){
//						os.setIsdel("Y");
//						oddHandOverService.update(os);
//						}
//					}
//				}
//			}
		map.put("status", "S");
		map.put("massge","您的操作已成功!");
		return map;
	}
	
}
