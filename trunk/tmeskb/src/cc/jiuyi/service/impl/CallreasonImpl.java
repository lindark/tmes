package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.CallreasonDao;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.service.CallreasonService;

/**
 * Service实现类 呼叫原因
 */
@Service
@Transactional
public class CallreasonImpl extends BaseServiceImpl<Callreason, String> implements CallreasonService {
	@Resource
	private CallreasonDao callreasonDao;
	@Resource
	public void setBaseDao(CallreasonDao callreasonDao){
		super.setBaseDao(callreasonDao);
	}
	
	public Pager getCallreasonPager(Pager pager,HashMap<String,String> map){
		return callreasonDao.getCallreasonPager(pager,map);
	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		callreasonDao.updateisdel(ids, oper);
	}


}
