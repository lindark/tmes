package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PickDao;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.service.PickService;

/**
 * Service实现类 -领/退料主表
 * @author Reece
 *
 */

@Service
public class PickServiceImpl extends BaseServiceImpl<Pick, String>implements PickService{

	@Resource
	private PickDao pickDao;
	
	@Resource
	public void setBaseDao(PickDao pickDao){
		super.setBaseDao(pickDao);
	}
	
	@Override
	public void delete(String id) {
		Pick pick = pickDao.load(id);
		this.delete(pick);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Pick> getPickList() {		
		return pickDao.getPickList();
	}

	@Override
	public Pager getPickPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return pickDao.getPickPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		pickDao.updateisdel(ids, oper);
		
	}
	
}
