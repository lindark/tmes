package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.ProcessRoute;


/**
 * Dao接口 - 工艺路线
 */
public interface ProcessRouteDao extends BaseDao<ProcessRoute, String> {

	/**
	 * 获取最高版本号
	 * @return
	 */
	public Integer getMaxVersion(String productid);
}
