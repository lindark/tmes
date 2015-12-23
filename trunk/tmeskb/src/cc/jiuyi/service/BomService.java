package cc.jiuyi.service;

import java.util.List;

import cc.jiuyi.entity.Bom;

/**
 * Service接口 - 产品Bom
 */

public interface BomService extends BaseService<Bom, String> {
	
	/**
	 * 根据Bom 集合 merge
	 * @param BomList
	 */
	public void mergeBom(List<Bom> bomList,String productid);
	
	/**
	 * 获取最高版本号
	 */
	public Integer getMaxVersionByid(String productid);
}