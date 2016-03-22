package cc.jiuyi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.util.CustomerException;

/**
 * Service接口
 * 转储管理
 */
public interface DumpService extends BaseService<Dump, String>{
	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @param map
	 * @return
	 */
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map);
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void saveDump(String[] ids,List<Dump> dumpList,String cardnumber,String loginid)throws IOException, CustomerException;
	
	/**
	 * jqgrad查询
	 */
	public Pager getAlllist(Pager pager,Admin admin);
	
	/**
	 * 新增保存
	 * @param list_dd
	 * @param fuid
	 */
	public String saveInfo(List<DumpDetail> list_dd, String fuid,String cardnumber,String materialcode);
	
	/**
	 * 修改保存
	 * @param list_dd
	 * @param fuid
	 * @param dumpid
	 * @return
	 */
	public void updateInfo(List<DumpDetail> list_dd, String fuid,String dumpid);
	
	/**
	 * 与SAP交互及修改本地状态
	 * @param dumpid
	 * @return
	 */
	public String updateToSAP(Dump dump,List<DumpDetail>ddlist,Admin admin)throws IOException, CustomerException;
	
	/**
	 * 刷卡撤销
	 * @param list
	 */
	public void updateData(List<Dump> list);
	
	/**
	 * 查询明细表当前生产日期和班次下的同物料编码的已确认的领料数量
	 * @param emp
	 * @return
	 */
	public List<HashMap<String,String>> getMengeByConditions(Admin emp);
	
	public List<Dump> historyExcelExport(HashMap<String,String> map);
	
}
