package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.DumpService;

/**
 * Service实现类 转储管理
 */
@Service
@Transactional
public class DumpServiceImpl extends BaseServiceImpl<Dump, String> implements DumpService{
	@Resource
	private DumpDao dumpDao;
	@Resource
	public void setBaseDao(DumpDao dumpDao){
		super.setBaseDao(dumpDao);
	}
	
	public Pager getDumpPager(Pager pager,HashMap<String,String> map){
		return dumpDao.getDumpPager(pager,map);
	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		dumpDao.updateisdel(ids, oper);
	}

	@Override
	public void mergeDump(List list) {
		for(int i=0;i<list.size();i++){
			Dump dump = (Dump) list.get(i);
			boolean flag = dumpDao.isExist("voucherId", dump.getVoucherId());
			if(flag)
				dumpDao.updateDump(dump);
			else
				dumpDao.save(dump);
		}
		
	}

	@Override
	public List getListDumpById(String voucherId) {
		return dumpDao.getListDumpById(voucherId);
	}

}
