package cc.jiuyi.service;

import cc.jiuyi.entity.OddHandOver;
/**
 * Service接口 - 零头数交接
 */
public interface OddHandOverService extends BaseService<OddHandOver, String> {
	
	/**
	 * 根据随工单编号读取零头数交接
	 * @param workingBillCode
	 * @return
	 */
	public OddHandOver findHandOver(String workingBillCode);
}
