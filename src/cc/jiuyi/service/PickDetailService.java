package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PickDetail;

/**
 * Service接口 - 领/退料
 */

public interface PickDetailService extends BaseService<PickDetail, String> {

	/**
	 * 取出所有PickDetail对象
	 * 
	 * @return
	 */
	public List<PickDetail> getPickDetailList();

	public Pager getPickDetailPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	public void save(List<PickDetail> pickDetailList,String woringBillId);
}