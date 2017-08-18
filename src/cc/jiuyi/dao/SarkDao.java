package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Sark;
/**
 * Dao接口 纸箱
 */
public interface SarkDao extends BaseDao<Sark, String>
{
	
	/**
	 * jqgrid查询
	 */
	public Pager getSarkPager(Pager pager,String productDate,String teamshift,String unitId);
	
	public Pager historyjqGrid(Pager pager, HashMap<String,String> map);

	/**
	 * 标记删除
	 * 
	 * @param id
	 * @param oper
	 *            Y/N
	 */
	public void updateisdel(String[] ids, String oper);
	/**
	 * 根据条件查询
	 */
	public Pager findSarkByPager(Pager pager,HashMap<String,String> mapcheck);
}
