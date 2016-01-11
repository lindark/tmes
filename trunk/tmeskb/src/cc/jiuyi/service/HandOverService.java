package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Area;
import cc.jiuyi.entity.HandOver;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 交接主表
 */

public interface HandOverService extends BaseService<HandOver, String> {
	
	public void saveandgx(Admin admin,List<HandOverProcess> handoverprocess);
	
	public void updateHand(List<HandOverProcess> handoverprocessList,String mblnr1,String handoverId,Admin admin);
}