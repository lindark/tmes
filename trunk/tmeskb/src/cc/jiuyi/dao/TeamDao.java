package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Team;

/**
 * Dao接口 -班组管理
 * 
 *
 */

public interface TeamDao extends BaseDao<Team,String> {
	
	
	/**
	 * 取出所有工序对象
	 * @return
	 */
	public List<Team> getTeamList();
	
	public Pager getTeamPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	/**
	 * 根据班组编码查询 
	 */
	public List<Team> getByCode(String code);

	/**
	 * 根据id联表查询
	 */
	public Team getOneById(String id);
}
