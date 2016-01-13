package cc.jiuyi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Brand;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.ProcessRoute;
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
	
	public void mergeWorkingBill(List<WorkingBill> workingbillList,List<Orders> orderList,List<ProcessRoute> processrouteList,List<Bom> bomList);

	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * 根据登录身份获取当班随工单信息
	 * @return
	 */
	public List<WorkingBill> getListWorkingBillByDate(Admin admin);

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
	 * 修改
	 * @param workingbill
	 */
	public void updateWorkingBill(WorkingBill workingbill);
	
	/**
	 * 根据随工单编号 获取 下一条记录
	 */
	public WorkingBill getCodeNext(String workingbillCode);
	
	
	 /**
     * 根据产品Code查询随工单
     */
	public List<WorkingBill> getWorkingBillByProductsCode(String matnr);
	
}