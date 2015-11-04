package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CardManagementDao;
import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.service.CardManagementService;

/**
 * Service实现类 -刷卡设备管理
 * @author Reece
 *
 */

@Service
public class CardManagementServiceImpl extends BaseServiceImpl<CardManagement, String>implements CardManagementService{

	@Resource
	private CardManagementDao cardManagementDao;
	
	@Resource
	public void setBaseDao(CardManagementDao cardManagementDao){
		super.setBaseDao(cardManagementDao);
	}
	
	@Override
	public void delete(String id) {
		CardManagement cardManagement = cardManagementDao.load(id);
		this.delete(cardManagement);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<CardManagement> getCardManagementList() {		
		return cardManagementDao.getCardManagementList();
	}

	@Override
	public Pager getCardManagementPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return cardManagementDao.getCardManagementPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		cardManagementDao.updateisdel(ids, oper);
		
	}

	
}
