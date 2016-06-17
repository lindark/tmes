package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Craft;

/**
 * Dao接口 - 工模
 */
public interface CraftDao extends BaseDao<Craft, String> {

	public Pager getCraftPager(Pager pager,HashMap<String,String>map,String id,String team);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public Pager findByPager(Pager pager,HashMap<String, String> map,String id);
	
	public List<Object[]> historyExcelExport(HashMap<String, String> map,String id,String teamid);
}
