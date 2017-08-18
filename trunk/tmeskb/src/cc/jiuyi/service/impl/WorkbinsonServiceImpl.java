package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.WorkbinsonDao;
import cc.jiuyi.entity.WorkbinSon;
import cc.jiuyi.service.WorkbinsonService;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Service
public class WorkbinsonServiceImpl extends BaseServiceImpl<WorkbinSon, String> implements WorkbinsonService
{
	@Resource
	public WorkbinsonDao workbinsonDao;
	@Resource
	public void setBaseDao(WorkbinsonDao workbinsonDao) {
		super.setBaseDao(workbinsonDao);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return workbinsonDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return workbinsonDao.historyExcelExport(map);
	}
}
