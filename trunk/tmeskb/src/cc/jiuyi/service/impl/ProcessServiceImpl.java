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
import cc.jiuyi.entity.Products;
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
	public List<Process> findProcessByProductsId(String id) {
		// TODO Auto-generated method stub
		return processDao.findProcessByProductsId(id);
	}

	/*public Pager getProductsList(Pager pager, HashMap<String, String> map)
	{
		return this.processDao.getProductsList(pager,map);
	}*/
}
