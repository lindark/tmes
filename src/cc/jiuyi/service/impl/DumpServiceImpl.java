package cc.jiuyi.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.dao.DumpDetailDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.util.CustomerException;

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
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpDetailDao dumpDetailDao;
	
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
		for (int i = 0; i < ids.length; i++) {
			Dump dump = dumpDao.get("voucherId", ids[i]);
			try {
				List<DumpDetail> dDList = dumpRfc.findMaterialDocumentByMblnr(ids[i]);
				for (int j = 0; j < dDList.size(); j++) {
					DumpDetail dumpDetail = dDList.get(j);
					dumpDetail.setDump(dump);
					dumpDetailDao.save(dumpDetail);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CustomerException e) {
				e.printStackTrace();
			}
		}
		
	}


}
