package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ReworkRecord;

/**
 * Service接口 - 返工记录表
 */

public interface ReworkRecordService extends BaseService<ReworkRecord, String> {

	/**
	 * 取出所有Rework对象
	 * 
	 * @return
	 */
	public List<ReworkRecord> getReworkRecordList();

	public Pager getReworkRecordPager(Pager pager, HashMap<String, String> map,String workingbillId);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void saveRepeal(List<ReworkRecord> list,Admin admin,String stu);

}