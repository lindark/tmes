package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOverProcess;

/**
 * Service接口 - 工序交接
 */

public interface HandOverProcessService extends BaseService<HandOverProcess, String>{

	/**
	 * 取出所有HandOverProcess对象
	 * 
	 * @return
	 */
	public List<HandOverProcess> getHandOverProcessList();

	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	/**
	 * 保存或更行集合对象
	 * @param handoverprocessList
	 * @return
	 */
	public void saveorupdate(List<HandOverProcess> handoverprocessList,String state,String cardNumber);
	
	/**
	 * 根据组件编码，工序ID，产品编码获取对象
	 * @param materialCode 组件编码
	 * @param processid 工序ID
	 * @param matnr 产品编码
	 * @return
	 */
	public HandOverProcess findhandoverBypro(String materialCode,String processid,String matnr,String workingBillId);
	
	/**
	 * 根据propertyName + objlist 的结果集 排序
	 * @param propertyName
	 * @param objlist
	 * @param orderBy
	 * @param ordertype
	 * @return
	 */
	public List<HandOverProcess> getList(String propertyName, Object[] objlist,String orderBy,String ordertype);
	
	/**
	 * 根据物料编码，工序id，产品ID 获取工序交接记录
	 * @param materialCode	物料编码
	 * @param processid	工序ID
	 * @param matnrid 产品ID
	 * @return
	 */
	public HandOverProcess findhandover(String materialCode, String processid,String matnrid);
	
	/**
	 * 刷卡保存，刷卡提交，刷卡确认
	 * @param handoverprocessList  操作的 交接集合
	 * @param state 做的什么操作
	 * @return
	 */
	public String savehandover(List<HandOverProcess> handoverprocessList,String state,String cardNumber);
	

}