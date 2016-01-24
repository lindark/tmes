package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.entity.WorkingInout;

/**
 * Service接口 -投入产出表
 */

public interface WorkingInoutService extends BaseService<WorkingInout, String>{

	/**
	 * 根据随工单ID和物料编码查询
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
}