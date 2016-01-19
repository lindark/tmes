package cc.jiuyi.dao;

import cc.jiuyi.entity.OddHandOver;
/**
 * Dao接口 - 零头交接
 */
public interface OddHandOverDao extends BaseDao<OddHandOver, String> {

	/**
	 * 根据随工单编号读取零头数交接
	 * @param workingBillCode
	 * @return
	 */
	public OddHandOver findHandOver(String workingBillCode);
}
