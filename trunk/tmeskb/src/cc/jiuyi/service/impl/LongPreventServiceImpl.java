package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.LongPreventDao;
import cc.jiuyi.entity.LongtimePreventstep;
import cc.jiuyi.service.LongPreventService;

/**
 * Service实现类 -长期预防措施管理
 *
 */

@Service
public class LongPreventServiceImpl extends BaseServiceImpl<LongtimePreventstep, String>implements LongPreventService{

	@Resource
	private LongPreventDao longPreventDao;
	
	@Resource
	public void setBaseDao(LongPreventDao longPreventDao){
		super.setBaseDao(longPreventDao);
	}

	@Override
	public Pager getLongPreventPager(Pager pager, HashMap<String, String> map) {
		return longPreventDao.getLongPreventPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		longPreventDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<LongtimePreventstep> getAllLongPrevent() {
		// TODO Auto-generated method stub
		return longPreventDao.getAllLongPrevent();
	}
}
