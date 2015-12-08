package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.ReworkDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.Product;
import cc.jiuyi.service.ReworkService;

/**
 * Service实现类 -返工
 * @author Reece
 *
 */

@Service
public class ReworkServiceImpl extends BaseServiceImpl<Rework, String>implements ReworkService{

	@Resource
	private ReworkDao reworkDao;
	
	@Resource
	public void setBaseDao(ReworkDao reworkDao){
		super.setBaseDao(reworkDao);
	}
	
	@Override
	public void delete(String id) {
		Rework rework = reworkDao.load(id);
		this.delete(rework);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Rework> getReworkList() {		
		return reworkDao.getReworkList();
	}

	@Override
	public Pager getReworkPager(Pager pager, HashMap<String, String> map,String workingbillId) {
		// TODO Auto-generated method stub
		return reworkDao.getReworkPager(pager, map,workingbillId);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		reworkDao.updateisdel(ids, oper);
		
	}

	@Override
	public void saveRepeal(List<Rework> list, Admin admin, String stu) {
		for (int i = 0; i < list.size(); i++) {
			Rework rework=list.get(i);
			rework.setState(stu);
			rework.setConfirmUser(admin);
			reworkDao.update(rework);	
		}
		
	}

	
}
