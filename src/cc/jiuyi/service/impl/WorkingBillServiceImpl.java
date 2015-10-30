package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
//import org.springmodules.cache.annotations.CacheFlush;

/**
 * Service实现类 - 随工单
 */

@Service
public class WorkingBillServiceImpl extends BaseServiceImpl<WorkingBill, String> implements WorkingBillService {
	@Resource
	private WorkingBillDao workingbilldao;
	@Resource
	public void setBaseDao(WorkingBillDao workingbilldao) {
		super.setBaseDao(workingbilldao);
	}
	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map) {
		return workingbilldao.findPagerByjqGrid(pager, map);
	}
	@Override
	public void mergeWorkingBill(List list){
		for(int i=0;i<list.size();i++){
			WorkingBill workingbill = (WorkingBill)list.get(i);
			boolean flag = workingbilldao.isExist("workingBillCode", workingbill.getWorkingBillCode());
			if(flag)
				workingbilldao.updateWorkingBill(workingbill);
			else
				workingbilldao.save(workingbill);
		}

	}
	@Override
	public void updateisdel(String[] ids, String oper) {
		workingbilldao.updateisdel(ids, oper);
	}
	@Override
	public List getListWorkingBillByDate(String productdate) {
		return workingbilldao.getListWorkingBillByDate(productdate);
	}


	
}