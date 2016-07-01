package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;

/**
 * Service接口 返修
 */
public interface RepairService extends BaseService<Repair, String>{
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);

	/**
	 * 标记删除
	 * 
	 * @param ids
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);

	public String updateState(List<Repair> list, String statu,
			String workingbillid,String cardnumber);

	/**
	 * 与SAP交互没有问题,更新本地数据库
	 */
	public void updateMyData(Repair r_return, String cardnumber,int my_id,String wbid);

	/**
	 * 获取物料表中包含list1中的数据
	 * @param list1
	 * @return
	 */
	public List<Bom> getIncludedByMaterial(List<Bom> list1,int plancount,FactoryUnit factoryunit);

	/**
	 * 新增
	 * @param repair
	 * @param cardnumber
	 */
	public void saveData(Repair repair, String cardnumber,List<RepairPiece>list_rp,List<Bom>list_bom,Admin admin);

	/**
	 * 修改
	 * @param id
	 */
	public void updateData(Repair repair,List<RepairPiece>list_rp,String cardnumber,List<Bom>list_bom,Admin admin);

}
