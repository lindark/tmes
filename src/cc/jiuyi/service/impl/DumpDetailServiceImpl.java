package cc.jiuyi.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDetailDao;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.service.DumpDetailService;

@Service
@Transactional
public class DumpDetailServiceImpl extends BaseServiceImpl<DumpDetail, String>
		implements DumpDetailService {
	@Resource
	private DumpDetailDao dumpDetailDao;

	@Resource
	public void setBaseDao(DumpDetailDao dumpDetailDao) {
		super.setBaseDao(dumpDetailDao);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		return dumpDetailDao.findPagerByjqGrid(pager, map);
	}

}
