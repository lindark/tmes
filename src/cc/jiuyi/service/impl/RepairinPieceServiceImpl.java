package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.RepairinPieceDao;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.service.RepairinPieceService;

/**
 * 返修收货--组件
 * @author lenovo
 *
 */
@Service
public class RepairinPieceServiceImpl extends BaseServiceImpl<RepairinPiece, String>implements RepairinPieceService
{

	@Resource
	private RepairinPieceDao rpDao;
	@Resource
	public void setBaseDao(RepairinPieceDao rpDao) {
		super.setBaseDao(rpDao);
	}
}
