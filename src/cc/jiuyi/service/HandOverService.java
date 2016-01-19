package cc.jiuyi.service;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Area;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.CustomerException;

/**
 * Service接口 - 交接主表
 */

public interface HandOverService extends BaseService<HandOver, String>,WorkingInoutCalculateBase<HandOver> {
	
	public void saveandgx(Admin admin,List<HandOverProcess> handoverprocess,List<OddHandOver> oddHandOverList);
	
	public void updateHand(List<HandOverProcess> handoverprocessList,String mblnr1,String handoverId,Admin admin);
	
	
	
}