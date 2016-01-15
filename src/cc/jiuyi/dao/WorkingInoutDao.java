package cc.jiuyi.dao;


import cc.jiuyi.entity.WorkingInout;

/**
 * Dao接口 -投入产出表
 * 
 *
 */

public interface WorkingInoutDao extends BaseDao<WorkingInout,String> {
	
	/**
	 * 根据随工单ID和物料编码查询
	 */
	public boolean isExist(String workingBillId,String materialCode);
}
