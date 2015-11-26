package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PickDetail;

/**
 * Service接口 - 半成品巡检Service接口
 */

public interface ItermediateTestDetailService extends BaseService<ItermediateTestDetail, String> {

	/**
	 * 取出所有ItermediateTestDetail对象
	 * 
	 * @return
	 */
	public List<ItermediateTestDetail> getItermediateTestDetailList();

	public Pager getItermediateTestDetailPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	public void save(List<ItermediateTestDetail> itermediateTestDetailList,String woringBillId);
	
	public List<ItermediateTestDetail> getItermediateTestDetail(String id);
}