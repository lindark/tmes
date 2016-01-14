package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.util.CustomerException;

/**
 * Service接口 纸箱
 */
public interface CartonService extends BaseService<Carton, String> {
	/**
	 * jqgrid查询
	 * @param pager
	 * @return
	 */
	public Pager getCartonPager(Pager pager);

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
	public String updateState(List<Carton> list, String workingbillid,
			String cardnumber) throws IOException, CustomerException;

	// 刷卡撤销
	public void updateState2(List<Carton> list, String workingbillid,
			String cardnumber);

	/**
	 * 新增保存
	 * @param list_cs
	 * @param cardnumber
	 */
	public void saveData(List<CartonSon> list_cs, String cardnumber);

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--新增前
	 * @return
	 */
	public List<CartonSon> getBomByConditions();

	/**
	 * 获取bom中随工单对应的以5开头的各个物料--编辑
	 * @return
	 */
	public List<CartonSon> getBomByConditions_edit(String id);

	/**
	 * 修改
	 * @param list_cs
	 * @param cardnumber
	 */
	public void updateData(List<CartonSon> list_cs, String id);

	/**
	 * 与SAP交互
	 * @param ids
	 * @return
	 */
	public String updateToSAP(String[] ids,String cardnumber)throws IOException,CustomerException;
}
