package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口
 * 转储管理
 */
public interface DumpDao extends BaseDao<Dump, String>{
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
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	/**
	 * jqgrad查询
	 */
	public Pager getAlllist(Pager pager,Admin admin);
	
	/**
	 * 查询明细表当前生产日期和班次下的同物料编码的已确认的领料数量
	 * @param emp
	 * @return
	 */
	public List<Object[]> getMengeByConditions(Admin emp);
	
	public List<Dump> historyExcelExport(HashMap<String,String> map);
}
