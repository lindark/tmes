package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;

/**
 * Dao接口 - 产品Bom
 */
public interface BomDao extends BaseDao<Bom, String> {

	/**
	 * 根据生产订单和版本获取BOM集合
	 * @param aufnr 订单号
	 * @param maxversion 版本号
	 * @return
	 */
	public List<Bom> getBomList(String aufnr,Integer maxversion,String shift);
	public List<Bom> getBomList(String aufnr,Integer maxversion,String materialCode,String shift);
	
	public Pager findPagerByjqGrid(Pager pager,HashMap<String,String>map);

	public Pager findPagerByOrders(Pager pager,HashMap<String,String>map,List<String> idList);
	public Integer getMaxVersion(String matnr,String productDate);
	public List<Bom> getBomList(String aufnr,Integer version);
	public List<Bom> getBomList1(String aufnr,Integer version,String materialCode);
	public List<Bom> getBomListRFC(String aufnr,Integer maxversion);
	/**
	 * 根据订单id和生产日期获取最大版本号
	 * @param aufnr 订单号
	 * @param maxversion 版本号
	 * @return
	 * */
	public Integer getMaxversion(String orderId,String productDate);
	
	/**
	 * 根据班次和生产订单号和物料编码获取最大版本号
	 * @param aufnr 订单号
	 * @param maxversion 版本号
	 * @return   link
	 * */
	public Integer getMaxversion(String shif,String orderId,String materialCode);
	
	/**
	 * 根据生产订单获取最高版本号
	 * @param aufnr
	 * @return
	 */
	public Integer getMaxVersion(String aufnr);
	

	/**
	 * jqGrid:(根据:子件编码/名称,凭证,版本号)查询
	 * @param pager
	 * @param map
	 * @param workingBillId
	 * @return
	 */
	public Pager getPieceByCondition(Pager pager, HashMap<String, String> map,String aufnr, Integer maxversion);
	public Pager getPieceByCondition(Pager pager, HashMap<String, String> map,String aufnr, Integer maxversion,String shift);
	
	/*根据组件编码获取组件名称
	 * */
	public String getMaterialName(String materialCode);
}
