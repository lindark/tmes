package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口
 * 转储管理
 */
public interface DumpDao extends BaseDao<Dump, String>{
	public Pager getDumpPager(Pager pager,HashMap<String,String> map);
	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public void updateDump(Dump dump);
	
	/**
	 * 根据物料凭证号取出对应的物料信息
	 * @return
	 */
	public List getListDumpById(String voucherId);
}
