package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Team;

/**
 * Service接口 - 班组管理
 */

public interface TeamService extends BaseService<Team, String> {

	/**
	 * 取出所有Team对象
	 * 
	 * @return
	 */
	public List<Team> getTeamList();

	public Pager getTeamPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}