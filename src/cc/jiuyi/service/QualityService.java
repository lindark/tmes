package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Quality;


/**
 * Service接口 - 质量问题
 */
public interface QualityService extends BaseService<Quality, String> {

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
