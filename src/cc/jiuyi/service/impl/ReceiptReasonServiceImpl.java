package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ReceiptReasonDao;
import cc.jiuyi.entity.ReceiptReason;
import cc.jiuyi.service.ReceiptReasonService;

/**
 * Service实现类 -单据原因管理
 *
 */

@Service
public class ReceiptReasonServiceImpl extends BaseServiceImpl<ReceiptReason, String>implements ReceiptReasonService{

	@Resource
	private ReceiptReasonDao receiptReasonDao;
	
	@Resource
	public void setBaseDao(ReceiptReasonDao receiptReasonDao){
		super.setBaseDao(receiptReasonDao);
	}

	@Override
	public Pager getReceiptReasonPager(Pager pager, HashMap<String, String> map) {
		return receiptReasonDao.getReceiptReasonPager(pager,map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		receiptReasonDao.updateisdel(ids, oper);
		
	}

	@Override
	public List<ReceiptReason> getReceiptReasonByType(String type) {
		// TODO Auto-generated method stub
		return receiptReasonDao.getReceiptReasonByType(type);
	}

}
