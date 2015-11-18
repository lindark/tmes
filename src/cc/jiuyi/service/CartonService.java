package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Material;

/**
 * Service接口 纸箱
 */
public interface CartonService extends BaseService<Carton, String> {
	public Pager getCartonPager(Pager pager, HashMap<String, String> map,
			String workingbillId);

	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);
}
