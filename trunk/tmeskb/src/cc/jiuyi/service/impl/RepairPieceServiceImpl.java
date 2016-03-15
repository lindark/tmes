package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.RepairPieceDao;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.service.RepairPieceService;

/**
 * 返修--组件
 * @author lenovo
 *
 */
@Service
public class RepairPieceServiceImpl extends BaseServiceImpl<RepairPiece, String>implements RepairPieceService
{

	@Resource
	private RepairPieceDao rpDao;
	@Resource
	public void setBaseDao(RepairPieceDao rpDao) {
		super.setBaseDao(rpDao);
	}
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		return rpDao.historyjqGrid(pager, map);
	}
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		return rpDao.historyExcelExport(map);
	}
}
