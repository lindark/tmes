package cc.jiuyi.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;

/**
 * Service接口 - 产品Bom
 */

public interface BomService extends BaseService<Bom, String> {

	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);

	
	
	/**
	 * 根据订单编码 + 随工单生产日期 获取BOM集合
	 * @param aufnr
	 * @param productDate
	 * @return
	 */
	public List<Bom> findBom(String aufnr,String productDate);
	
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
}