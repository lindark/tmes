package cc.jiuyi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.WorkingBill;


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
	public List<Bom> getBomList(String aufnr,Integer maxversion);
	
	
	public Pager findPagerByjqGrid(Pager pager,HashMap<String,String>map);

	
	public Integer getMaxVersion(String matnr,String productDate);
	
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
}
