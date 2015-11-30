package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.FaultReason;

/**
 * Dao接口 - 故障原因
 */
public interface FaultReasonDao extends BaseDao<FaultReason, String> {

	public Pager getFaultReasonPager(Pager pager,HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public List<FaultReason> getAllFaultReason();
}
