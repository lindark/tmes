package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Quality;

/**
 * Dao接口 - 质量
 */
public interface QualityDao extends BaseDao<Quality, String> {

	public Pager getQualityPager(Pager pager,HashMap<String,String> map,String id,String team);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
	
	public Pager findByPager(Pager pager,HashMap<String,String> map,String id);
	
	public List<Object[]> historyExcelExport(HashMap<String, String> map,String id,String teamid);
}
