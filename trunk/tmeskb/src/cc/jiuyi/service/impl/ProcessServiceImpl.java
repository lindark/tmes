package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.WorkingBill;
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
		Process process = processDao.get(id);
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
		return processDao.isExistByProcessCode(processCode);
	}

	/**
	 * 查询一个带联表
	 */
	@Override
	public Process getOne(String id)
	{
		return this.processDao.getOne(id);
	}

	/**
	 * 检查工序编码是否存在
	 */
	public boolean getCk(String info)
	{
		List<Process>list=this.processDao.getCk(info);
		if(list.size()>0)
		{
			return false;
		}
		return true;
	}

	@Override
	public List<Process> findProcess(List<WorkingBill> workingbills) {
		Object[] obj = new Object[workingbills.size()];
		
		for(int i=0;i<workingbills.size();i++){
			WorkingBill workingbill = workingbills.get(i);
			String matnr = workingbill.getMatnr();
			obj[i] = matnr;
		}
		return processDao.findProcess(obj);
	}
	
	@Override
	public Integer getMaxVersion(String productid) {
		return processDao.getMaxVersion(productid);
	}

	@Override
	public List<Process> findProcessByProductsId(String id) {
		// TODO Auto-generated method stub
		return processDao.findProcessByProductsId(id);
	}

	/*public Pager getProductsList(Pager pager, HashMap<String, String> map)
	{
		return this.processDao.getProductsList(pager,map);
	}*/
	
	public List<Process> getListRoute(List<HashMap<String,Object>> hashmap){
		HashSet<Process> set = new HashSet<Process>();
		for(int i=0;i<hashmap.size();i++){
			HashMap<String,Object> map = hashmap.get(i);
			Object matnr = map.get("matnr");
			Object version = map.get("version");
			List<Process> processList = this.getListRoute(String.valueOf(matnr),version == null ? 0:(Integer)version);
			set.addAll(processList);
		}
		return new ArrayList<Process>(set);
	}
	
	public List<Process> getListRoute(String matnr,Integer version){
		return processDao.getListRoute(matnr,version);
	}

	@Override
	public List<Process> getExistProcessList() {
		// TODO Auto-generated method stub
		return processDao.getExistProcessList();
	}

	@Override
	public List<Process> getExistAndStateProcessList() {
		// TODO Auto-generated method stub
		return  processDao.getExistAndStateProcessList();
	}
}
