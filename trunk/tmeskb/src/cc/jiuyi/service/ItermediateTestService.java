package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.IpRecord;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;

/**
 * Service接口 - 半成品巡检主表
 */

public interface ItermediateTestService extends BaseService<ItermediateTest, String> {

	/**
	 * 取出所有ItermediateTest对象
	 * 
	 * @return
	 */
	public List<ItermediateTest> getItermediateTestList();

	public Pager getItermediateTestPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	
	/**
	 * 确认或撤销
	 * @param list
	 * @param stus
	 */
	public void updateState(List<ItermediateTest> list, String stus);
	
	/**
	 * 刷卡保存
	 */
	public void saveSubmit(ItermediateTest itermediateTest,
			List<ItermediateTestDetail> list_itmesg, List<IpRecord> list_itbug,String my_id);

	
	/**
	 * 修改
	 */
	public void updateAll(ItermediateTest itermediateTest,
			List<ItermediateTestDetail> list_itmesg, List<IpRecord> list_itbug,String my_id);

}