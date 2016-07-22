package cc.jiuyi.dao;


import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
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
	 * 根据生产订单号加班次得到入库数
	 */
	public double getStorageByAufnr(String aufnr);
	
	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 */
	public List<WorkingInout> findWbinoutput(String wbid);
	
	public List<WorkingInout> newFindPagerByWorkingBillInout(HashMap<String, String> map) ;
	
	public Pager listjqGrid(Pager pager, HashMap<String, String> map);
	
	public List<String[]> sumAmount(String unit,String aufnr,String start,String end,List<String[]> processList);
	public List<String[]> sumAmountSY(HashMap<String,List<String>> map);
	public List<String[]> findProcess();
}
