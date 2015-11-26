package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.ItermediateTest;

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
	

	
}