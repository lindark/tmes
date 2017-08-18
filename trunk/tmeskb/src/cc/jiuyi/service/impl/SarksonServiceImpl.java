package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.SarksonDao;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.service.SarksonService;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Service
public class SarksonServiceImpl extends BaseServiceImpl<SarkSon, String> implements SarksonService
{
	@Resource
	public SarksonDao sarksonDao;
	@Resource
	public void setBaseDao(SarksonDao sarksonDao) {
		super.setBaseDao(sarksonDao);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sarksonDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sarksonDao.historyExcelExport(map);
	}
}
