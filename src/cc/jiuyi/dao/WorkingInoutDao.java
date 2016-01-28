package cc.jiuyi.dao;


import java.util.HashMap;
import java.util.List;

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
	
	/**
	 * 根据搜索条件查询投入产出表
	 */
	public List<WorkingInout> findPagerByWorkingBillInout(HashMap<String,String> map);

	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 */
	public List<WorkingInout> findWbinoutput(String wbid);
}
