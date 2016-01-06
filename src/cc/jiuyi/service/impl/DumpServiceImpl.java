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
public class DumpServiceImpl extends BaseServiceImpl<Dump, String> implements
		DumpService {
	@Resource
	private DumpDao dumpDao;
	@Resource
	private AdminService adminService;
	@Resource
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpDetailDao dumpDetailDao;

	@Resource
	public void setBaseDao(DumpDao dumpDao) {
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

	/**
	 * 刷卡确认
	 */
	@Override
	public void saveDump(String[] ids, List<Dump> dumpList,String cardnumber,String loginid) throws IOException, CustomerException {
		//先将转储单保存到数据库
		Admin admin = adminService.getByCardnum(cardnumber);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dumpList.size(); j++) {
				Dump dump = dumpList.get(j);
				if (ids[i].equals(dump.getVoucherId())) {
					dump.setConfirmUser(admin);
					dump.setState("1");
					dumpDao.save(dump);
				}
			}
		}
		//再保存相关明细
		for (int i = 0; i < ids.length; i++) {
			Dump dump = dumpDao.get("voucherId", ids[i]);
			//调用sap函数接口，根据凭证号找到所有相关明细
			List<DumpDetail> dDList = dumpRfc
					.findMaterialDocumentByMblnr(ids[i],loginid);
			//将明细保存到本地数据库
			for (int j = 0; j < dDList.size(); j++) {
				DumpDetail dumpDetail = dDList.get(j);
				dumpDetail.setDump(dump);
				dumpDetailDao.save(dumpDetail);
			}
		}

	}

}
