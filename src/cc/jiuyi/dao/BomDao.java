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
	 * 获取最高版本号,根据产品id
	 * @return
	 */
	public Integer getMaxVersionByid(String productid);
	
	/**
	 * 获取最高版本号,根据产品产品编码
	 * @param productCode
	 * @return
	 */
	
	public Integer getMaxVersionBycode(String productCode);
	
	/**
	 * 根据产品编码获取 bom 信息
	 * @param productCode
	 * @return
	 */
	public List<Bom> getBomByProductCode(String productCode,String materialCode,Integer version);
	
	/**
	 * 根据 产品ID 和 版本号获取指定的版本
	 * @param productid 产品ID
	 * @param version 版本
	 * @return
	 */
	public List<Bom> getListByid(String productid,Integer version);
	
	/**
	 * 根据 产品编码 和 版本号获取指定的版本
	 * @param productid 产品ID
	 * @param version 版本
	 * @return
	 */
	public List<Bom> getListBycode(String productcode,Integer version);
	

	/**
	 * 获取最高版本号的Bom清单
	 */
	public List<Bom> getBomListByMaxVersion(Integer version);
	
	
	public Pager findPagerByjqGrid(Pager pager,HashMap<String,String>map);
}
