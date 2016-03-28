package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Rework;
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

	public Pager getReworkRecordPager(Pager pager, HashMap<String, String> map,String id);
	
	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	public void saveRepeal(List<ReworkRecord> list, Admin admin, String stu);

	/**
	 * 刷卡提交
	 * @param cardnumber
	 * @param workingBillId
	 * @param reworkId
	 * @param reworkCount
	 * @param reworkRecord
	 * @return reworkId
	 */
	public String saveSubmit(String cardnumber, String workingBillId,
			String reworkId, Integer reworkCount, ReworkRecord reworkRecord);
	
	public String saveApproval(String cardnumber,ReworkRecord reworkRecord);
	
	/**
	 * 获得返工表记录
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	/**
	 * 打印返工记录Excel表
	 */
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
	
	public List<ReworkRecord> getReworkRecord(String id);
}