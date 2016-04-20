package cc.jiuyi.dao;

import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Carton;
/**
 * Dao接口 纸箱
 */
public interface CartonDao extends BaseDao<Carton, String>
{
	
	/**
	 * jqgrid查询
	 */
	public Pager getCartonPager(Pager pager,String productDate,String teamshift,String unitId);
	
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
	public Pager findCartonByPager(Pager pager,HashMap<String,String> mapcheck);
}
