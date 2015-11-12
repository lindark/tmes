package cc.jiuyi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 字典
 */

public interface WorkingBillService extends BaseService<WorkingBill, String> {
	/**
	 * 分页查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,Map map);
	
	public void mergeWorkingBill(List list);

	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据生产日期取出对应的随工单信息
	 * @return
	 */
	public List getListWorkingBillByDate(Admin admin);
	
}