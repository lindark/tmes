package cc.jiuyi.service;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Locationonside;

/**
 * Service接口  线边仓
 */
public interface LocationonsideService extends
		BaseService<Locationonside, String> {
	public Pager getLocationPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
