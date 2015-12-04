package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;

/**
 * Service接口 - 领/退料主表
 */

public interface PickService extends BaseService<Pick, String> {

	/**
	 * 取出所有Pick对象
	 * 
	 * @return
	 */
	public List<Pick> getPickList();

	public Pager getPickPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void saveConfirm(List<Pick> list,Admin admin,String stu,String mblnr);
	
	public void saveRepeal(List<Pick> list,Admin admin,String stu);
	
}