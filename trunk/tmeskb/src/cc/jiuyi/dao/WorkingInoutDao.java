package cc.jiuyi.dao;


import cc.jiuyi.entity.WorkingInout;

/**
 * Dao接口 -投入产出表
 * 
 *
 */

public interface WorkingInoutDao extends BaseDao<WorkingInout,String> {
	
	/**
	 * 根据随工单ID和物料编码查询投入产出表是否存在
	 */
	public boolean isExist(String workingBillId,String materialCode);
	
	/**
	 * 根据随工单ID和物料编码查询投入产出表
	 */
	public WorkingInout findWorkingInout(String workingBillId,String materialCode);
}
