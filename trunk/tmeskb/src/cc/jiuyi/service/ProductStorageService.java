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
import cc.jiuyi.entity.ProductStorage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.CustomerException;
/**
 * Service接口 - 字典
 */

public interface ProductStorageService extends BaseService<ProductStorage, String>,WorkingInoutCalculateBase<ProductStorage> {
	public void savePS(ProductStorage ps);
	/**
	 * 分页查询
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager,Map map);
	
	public void mergeWorkingBill(List<ProductStorage> workingbillList,List<Orders> orderList,List<ProcessRoute> processrouteList,List<Bom> bomList) throws CustomerException ;

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
	public List<ProductStorage> getListWorkingBillByDate(Admin admin);

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	public List<ProductStorage> getIdsAndNames();
	
	/**
	 * 条件查询随工单
	 * @param matnr 物料号
	 * @param productDate 生产日期
	 * @param shift 班次
	 * @return
	 */
	public List<ProductStorage> findListWorkingBill(Object[] productsid,String productDate,String shift);
	
	/**
	 * 修改
	 * @param workingbill
	 */
	public void updateWorkingBill(ProductStorage workingbill);
	
	/**
	 * 根据随工单编号 获取 下一条记录
	 */
	public ProductStorage getCodeNext(String workingbillCode,String productCode,String shift);
	public ProductStorage getCodeNext(Admin admin ,String workingbillCode,String productCode,String shift);
	
	 /**
     * 根据产品Code查询随工单
     */
	public List<ProductStorage> getWorkingBillByProductsCode(String matnr);
	
	public List<ProductStorage> getListWorkingBillByDate(String productDate,String shift,String workcenter,String matnr);
	
	/**
	 * 根据生产日期、班次查询随工单
	 */
	public List<ProductStorage> findListWorkingBill(String productDate,String shift);
	
	public List<ProductStorage> getWorkingBillList(String workingBillId);

	public List<ProductStorage> getListWorkingBillByProductDate(String startdate,
			String enddate, String workcode);
}