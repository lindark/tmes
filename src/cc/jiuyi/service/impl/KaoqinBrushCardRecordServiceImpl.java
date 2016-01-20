package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.KaoqinBrushCardRecordDao;
import cc.jiuyi.entity.KaoqinBrushCardRecord;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.KaoqinBrushCardRecordService;

/**
 * 开启考勤(刷卡)记录
 * @author gaoyf
 *
 */
@Service
public class KaoqinBrushCardRecordServiceImpl extends BaseServiceImpl<KaoqinBrushCardRecord, String> implements KaoqinBrushCardRecordService
{
	@Resource
	private KaoqinBrushCardRecordDao kqBCRDao;
	@Resource
	private AdminService adminService;
	@Resource
	public void setBaseDao(KaoqinBrushCardRecordDao kqBCRDao) {
		super.setBaseDao(kqBCRDao);
	}
}
