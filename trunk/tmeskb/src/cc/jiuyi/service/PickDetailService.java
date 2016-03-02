package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;

/**
 * Service接口 - 领/退料
 */

public interface PickDetailService extends BaseService<PickDetail, String>,WorkingInoutCalculateBase<PickDetail> {

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

	public String saveSubmit(List<PickDetail> pickDetailList,Pick pick);
	
	public String saveApproval(List<PickDetail> pickDetailList,Pick pick);
	
	public List<PickDetail> getPickDetail(String id);
	
	/**
	 * 修改
	 */
	public void updateAll(Pick pick,List<PickDetail> pickDetail,String cardnumber,String info);
	
	/**
	 * 刷卡撤销向投入产出表更新数据
	 * @param paramaterList
	 */
	public void updateWorkingInoutCalculateBack(List<PickDetail> paramaterList);
	
	/**
	 * 计算倍数
	 */
	public Double Calculate(Double planCount,Double pickAmount);
	
	public void updatePIckAndWork(Pick pick);
	
	public void updatePIckAndWork(Pick pick,HashMap<String, Object> map);
	
	public Pick saveApproval1(List<PickDetail> pickDetailList, Pick pick);
	
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map);
}