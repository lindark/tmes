package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Sark;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.util.CustomerException;

/**
 * Service接口 纸箱
 */
public interface SarkService extends BaseService<Sark, String> {
	/**
	 * jqgrid查询
	 * @param pager
	 * @return
	 */
	public Pager getSarkPager(Pager pager,String loginid);

	public Pager historyjqGrid(Pager pager, HashMap<String, String> map);

	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	// 刷卡确认
	public String updateState(List<Sark> list, String workingbillid,
			String cardnumber) throws IOException, CustomerException;

	// 刷卡撤销
	public void updateState2(List<Sark> list, String workingbillid,
			String cardnumber);
	
	
	// 刷卡撤销 by Reece
	public void updateCancel(List<Sark> list,String cardnumber);

	/**
	 * 新增保存
	 * @param list_cs
	 * @param cardnumber
	 */
	public void saveData(List<SarkSon> list_cs, String cardnumber,String loginid,String bktxt);

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--新增前
	 * @return
	 */
	public List<SarkSon> getBomByConditions();

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--编辑
	 * @return
	 */
	public List<SarkSon> getBomByConditions_edit(String id);

	/**
	 * 修改
	 * @param list_cs
	 * @param cardnumber
	 */
	public void updateData(List<SarkSon> list_cs, String id,String bktxt);

	/**
	 * 与SAP交互
	 * @param ids
	 * @return
	 */
	public String updateToSAP(String[] ids,String cardnumber,String loginid)throws IOException,CustomerException;
	/**
	 * 与SAP交互  刷卡确认 -- link 
	 * @param ids
	 * @return
	 */
	public String updateToSAPNew(String[] ids,String cardnumber,String loginid)throws IOException,CustomerException;
	/**
	 * 与SAP交互    刷卡撤销 -- link
	 * @param ids
	 * @return
	 */
	public String updateToSAPReturn(String[] ids,String cardnumber,String loginid)throws IOException,CustomerException;

	/**
	 * 查看
	 * @param id
	 * @return
	 */
	public List<SarkSon> getToShow(String id);
	/**
	 * 根据条件查询
	 */
	public Pager findSarkByPager(Pager pager,HashMap<String,String> mapcheck);
	
	

}
