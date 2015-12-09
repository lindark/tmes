package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.util.CustomerException;

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

	//刷卡确认
	public void updateState(List<Carton> list,String workingbillid) throws IOException, CustomerException;
	
	//刷卡撤销
	public void updateState2(List<Carton> list,String workingbillid);
}
