package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.OddHandOverDao;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.WorkingBillService;
/**
 * Service实现类 - 零头数交接
 */

@Service
public class OddHandOverServiceImpl extends BaseServiceImpl<OddHandOver, String> implements
		OddHandOverService {
	@Resource
	private OddHandOverDao oddHandOverDao;
	@Resource
	private WorkingBillService workingbillservice;
	
	@Resource
	public void setBaseDao(OddHandOverDao oddHandOverDao) {
		super.setBaseDao(oddHandOverDao);
	}

	public OddHandOver findHandOver(String workingBillCode) {
		return oddHandOverDao.findHandOver(workingBillCode);
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return oddHandOverDao.historyjqGrid(pager, map);
	}

	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return oddHandOverDao.historyExcelExport(map);
	}

}
