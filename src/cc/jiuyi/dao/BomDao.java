package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Bom;


/**
 * Dao接口 - 产品Bom
 */
public interface BomDao extends BaseDao<Bom, String> {

	/**
	 * 获取最高版本号
	 * @return
	 */
	public Integer getMaxVersion(String productid);
}
