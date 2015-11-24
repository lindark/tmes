package cc.jiuyi.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.WorkingBill;

/**
 * Dao接口 - 随工单
 */

public interface WorkingBillDao extends BaseDao<WorkingBill, String> {
	
	/**
	 * 分页查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,Map map);
	
	public void updateWorkingBill(WorkingBill workingbill);
	
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据生产日期取出对应的随工单信息
	 * @return
	 */
	public List getListWorkingBillByDate(Admin admin);

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	public List<WorkingBill> getIdsAndNames();
	
	/**
	 * 条件查询随工单
	 * @param matnr 物料号
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @return
	 */
	public List<WorkingBill> findListWorkingBill(String matnr,String productDate,String shift);
}