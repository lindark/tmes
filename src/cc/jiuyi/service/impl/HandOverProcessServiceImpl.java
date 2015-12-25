package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.HandOverProcessDao;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.service.HandOverProcessService;

/**
 * Service实现类 -工序交接
 * @author Reece
 *
 */

@Service
public class HandOverProcessServiceImpl extends BaseServiceImpl<HandOverProcess, String>implements HandOverProcessService{

	@Resource
	private HandOverProcessDao handOverProcessDao;
	
	@Resource
	public void setBaseDao(HandOverProcessDao handOverProcessDao){
		super.setBaseDao(handOverProcessDao);
	}
	
	@Override
	public void delete(String id) {
		HandOverProcess handOverProcess = handOverProcessDao.load(id);
		this.delete(handOverProcess);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<HandOverProcess> getHandOverProcessList() {		
		return handOverProcessDao.getHandOverProcessList();
	}

	@Override
	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return handOverProcessDao.getHandOverProcessPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		handOverProcessDao.updateisdel(ids, oper);
		
	}
	
	@Override
	public void saveorupdate(List<HandOverProcess> handoverprocessList) {
		for(HandOverProcess handoverprocess : handoverprocessList){
			//String[] propertyNames = {"bom.materialCode","process.id","beforworkingbill.id"};
			//Object[] propertyValues = {handoverprocess.getMaterialCode(), handoverprocess.getProcess().getId(),handoverprocess.getBeforworkingbill().getId()};
			//HandOverProcess handover = handOverProcessDao.get(propertyNames, propertyValues);
			HandOverProcess handover = this.findhandover(handoverprocess.getMaterialCode(), handoverprocess.getProcessid(), handoverprocess.getBeforworkingbill().getId());
			if(handover != null){
				if(handoverprocess.getAmount() <=0){
					handOverProcessDao.delete(handover);
				}else{
					handoverprocess.setId(handover.getId());
					handOverProcessDao.merge(handoverprocess);//对象不属于持久化
				}
			}
			else{
				if(handoverprocess.getAmount() > 0){
					handOverProcessDao.save(handoverprocess);
				}
			}
				
		}
		
	}

	@Override
	public HandOverProcess findhandoverBypro(String materialCode,
			String processid, String matnr) {
		return handOverProcessDao.findhandoverBypro(materialCode, processid, matnr);
	}
	
	public List<HandOverProcess> getList(String propertyName, Object[] objlist,String orderBy,String ordertype) {
		return handOverProcessDao.getList(propertyName, objlist, orderBy, ordertype);
	}

	@Override
	public HandOverProcess findhandover(String materialCode, String processid,
			String matnrid) {
		return handOverProcessDao.findhandover(materialCode, processid, matnrid);
	}
	
	
}
