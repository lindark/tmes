package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Repair;

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
	public void updateState(List<Carton> list, String statu,
			String workingbillid);
}
