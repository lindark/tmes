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
	public List<WorkingBill> findListWorkingBill(Object[] productsid,String productDate,String shift);


	/**
	 * 根据随工单编号 获取 下一条记录
	 */
	public WorkingBill getCodeNext(String workingbillCode,List<String> aufnrList,String formtdate);
	
	
    /**
     * 根据产品Code查询随工单
     */
	public List<WorkingBill> getWorkingBillByProductsCode(String matnr);
	
	/**
	 * 根据订单号获取随工单集合
	 * @param orderId
	 * @return
	 */
	public List<WorkingBill> findWorkingBill(String workcenter,String productDate,String shift);
	
	public List<WorkingBill> getListWorkingBillByDate(String productDate,String shift,String workcenter,String matnr);
	/**
	 * 根据生产日期、班次查询随工单
	 */
	public List<WorkingBill> findListWorkingBill(String productDate,String shift);
	
	public List<WorkingBill> getWorkingBillList(String workingBillId);
}