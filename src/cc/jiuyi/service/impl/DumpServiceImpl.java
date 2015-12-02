package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.AdminService;
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
	private AdminService adminService;
	@Resource
	public void setBaseDao(DumpDao dumpDao){
		super.setBaseDao(dumpDao);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		dumpDao.updateisdel(ids, oper);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return dumpDao.findPagerByjqGrid(pager, map);
	}

	@Override
	public void confirmDump(String[] ids, List<Dump> dumpList) {
		Admin admin = adminService.getLoginAdmin();
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dumpList.size(); j++) {
				Dump dump = dumpList.get(j);
				if(ids[i].equals(dump.getVoucherId())){
					dump.setConfirmUser(admin);
					dump.setState("1");
					dumpDao.save(dump);
				}
			}			
		}
		
	}


}
