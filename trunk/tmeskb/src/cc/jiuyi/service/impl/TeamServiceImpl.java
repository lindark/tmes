package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.FactoryUnitDao;
import cc.jiuyi.dao.TeamDao;
import cc.jiuyi.entity.Team;
import cc.jiuyi.service.TeamService;

/**
 * Service实现类 -班组管理
 * @author Reece
 *
 */

@Service
public class TeamServiceImpl extends BaseServiceImpl<Team, String>implements TeamService{

	@Resource
	private TeamDao teamDao;
	
	@Resource
	private FactoryUnitDao fuDao;
	
	@Resource
	public void setBaseDao(TeamDao teamDao){
		super.setBaseDao(teamDao);
	}
	
	@Override
	public void delete(String id) {
		Team team = teamDao.load(id);
		this.delete(team);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Team> getTeamList() {		
		return teamDao.getTeamList();
	}

	@Override
	public Pager getTeamPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return teamDao.getTeamPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		teamDao.updateisdel(ids, oper);
		
	}

	/**
	 * 分页条件查询单元
	 */
	@Override
	public Pager getFuPager(Pager pager, HashMap<String, String> map)
	{
		return this.fuDao.getFuPager(pager,map);
	}

	/**
	 * 根据班组编码查询 
	 */
	public List<Team> getByCode(String code)
	{
		return this.teamDao.getByCode(code);
	}
	
	/**
	 * 根据id联表查询
	 */
	public Team getOneById(String id)
	{
		return this.teamDao.getOneById(id);
	}
}
