package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.Rework;

/**
 * Dao接口 - 返工
 */
public interface ReworkDao extends BaseDao<Rework, String> {

	
	/**
	 * 取出所有报工对象
	 * @return
	 */
	public List<Rework> getReworkList();
	
	
	public Pager getReworkPager(Pager pager,HashMap<String,String> map);
	

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);
}
