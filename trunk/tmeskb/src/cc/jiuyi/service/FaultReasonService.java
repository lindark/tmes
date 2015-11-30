package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;

/**
 * Service接口 - 
 */
public interface FaultReasonService extends BaseService<FaultReason, String> {

	public Pager getFaultReasonPager(Pager pager,HashMap<String,String>map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<FaultReason> getAllFaultReason();
}
