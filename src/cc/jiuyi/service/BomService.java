package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.WorkingBill;

/**
 * Service接口 - 产品Bom
 */

public interface BomService extends BaseService<Bom, String> {

	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);

	public Pager findPagerByOrders(Pager pager,HashMap<String, String> map,List<String> idList);
	
	/**
	 * 根据订单编码 + 随工单生产日期 获取BOM集合
	 * @param aufnr
	 * @param productDate
	 * @return
	 */
	public List<Bom> findBom(String aufnr,String productDate,String workingBillCode);
	
	/**
	 * 根据生产订单获取最高版本号
	 * @param aufnr
	 * @return
	 */
	public Integer getMaxVersion(String aufnr);
	
	/**
	 * 根据生产订单和版本获取BOM集合
	 * @param aufnr 订单号
	 * @param maxversion 版本号
	 * @return
	 */
	public List<Bom> getBomList(String aufnr,Integer maxversion);
	
	public List<Bom> getBomListRFC(String aufnr,Integer maxversion,String type);

	/**
	 * jqGrid:(根据:子件编码/名称,随工单)查询
	 * @param pager
	 * @param map
	 * @param workingBillId
	 * @return
	 */
	public Pager getPieceByCondition(Pager pager, HashMap<String, String> map,
			WorkingBill wb);
	
	/**
	 * 根据订单id和生产日期获取最大版本号
	 * @param aufnr 订单号
	 * @param maxversion 版本号
	 * @return
	 * */
	public Integer getMaxversion(String orderId,String productDate);
	
	public List<Bom> findBom(String aufnr,String productDate,String materialCode,String workingBillCode);
	
	
	/*根据组件编码获取组件名称
	 * */
	public String getMaterialName(String materialCode);

	/**
	 * 根据订单号,生产日期,以"5"开关的查询
	 * @param aufnr
	 * @param productDate
	 * @param string
	 * @return
	 */
	public Bom getBomByConditions(String aufnr, String productDate,String num,String workingBillCode);
	public List<Bom> getBomByConditions2(String aufnr, String productDate,String num,String workingBillCode);
	
	
	
	
	public Object sumAmount(String aufnr,String productDate,String materialCode,String workingBillCode);
}