package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.BrandDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.MemberRankDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.MemberRank;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.BrandService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
//import org.springmodules.cache.annotations.CacheFlush;

/**
 * Service实现类 - 随工单
 */

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, String> implements DepartmentService {
	
	@Resource
	private DepartmentDao departmentdao;
	
	@Resource
	public void setBaseDao(DepartmentDao departmentdao) {
		super.setBaseDao(departmentdao);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		departmentdao.updateisdel(ids, oper);
	}

	@Override
	public List getAllByHql() {
		return departmentdao.getAllByHql();
	}



}