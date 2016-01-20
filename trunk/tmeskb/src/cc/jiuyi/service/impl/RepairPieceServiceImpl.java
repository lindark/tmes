package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
}
