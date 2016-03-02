package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pick;

/**
 * Service接口 - 领/退料主表
 */

public interface PickService extends BaseService<Pick, String>,WorkingInoutCalculateBase<Pick> {

	/**
	 * 取出所有Pick对象
	 * 
	 * @return
	 */
	public List<Pick> getPickList();

	public Pager getPickPager(String workingBillId,Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void saveConfirm(List<Pick> list,Admin admin,String stu,String mblnr);
	
	public void saveRepeal(List<Pick> list,Admin admin,String stu);
	
	public void updateSap(String pk,String ex_mblnr);
	
	
	/**
	 * 根据随工单号找出所有的Pick对象
	 * @param WorkingBillId
	 * @return
	 */
	public List<Pick> getPickByWorkingbillId(String WorkingBillId);

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
}