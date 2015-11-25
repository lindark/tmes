package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Team;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.util.ThinkWayUtil;


/**
 * 后台Action类-班组管理
 */

@ParentPackage("admin")
public class TeamAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Team team;
	private FactoryUnit factoryUnit;// 单元
	private List<Team> teamList;//班组
	// 获取所有状态
	private List<Dict> allState;

	@Resource
	private TeamService teamService;
	@Resource
	private DictService dictService;
	@Resource
	private FactoryUnitService fuService;

	private String info;// 前端传的值

	// 添加
	public String add() {
		return INPUT;
	}

	// browser
	public String browser() {
		teamList = teamService.getAll();
		return "browser1";
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		return LIST;
	}

	/**
	 * 指定(新增/修改页面的弹框)页面
	 */
	public String list2() {
		return "alert";
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			if (pager == null) {
				pager = new Pager();
				pager.setOrderType(OrderType.asc);
				pager.setOrderBy("orderList");
			}
			if (pager.is_search() == true && filters != null) {// 需要查询条件
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map m = new HashMap();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}

			if (pager.is_search() == true && Param != null) {// 普通搜索功能
																// 此处处理普通查询结果
																// Param
																// 是表单提交过来的json
																// 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				// 班组编码
				if (obj.get("teamCode") != null) {
					System.out.println("obj=" + obj);
					String teamCode = obj.getString("teamCode").toString();
					map.put("teamCode", teamCode);
				}
				// 班级名称
				if (obj.get("teamName") != null) {
					String teamName = obj.getString("teamName").toString();
					map.put("teamName", teamName);
				}
				// 状态
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
				// 单元名称
				if (obj.get("xfactoryUnitName") != null) {
					String xfactoryUnitName = obj.getString("xfactoryUnitName")
							.toString();
					map.put("xfactoryUnitName", xfactoryUnitName);
				}
				// 车间名称
				if (obj.get("xworkShopName") != null) {
					String xworkShopName = obj.getString("xworkShopName")
							.toString();
					map.put("xworkShopName", xworkShopName);
				}
				// 工厂名称
				if (obj.get("xfactoryName") != null) {
					String xfactoryName = obj.getString("xfactoryName")
							.toString();
					map.put("xfactoryName", xfactoryName);
				}
			}

			pager = teamService.getTeamPager(pager, map);
			List<Team> teamList = pager.getList();
			List<Team> lst = new ArrayList<Team>();
			for (int i = 0; i < teamList.size(); i++) {
				Team team = (Team) teamList.get(i);
				team.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "teamState", team.getState()));
				team.setXfactoryUnitName(team.getFactoryUnit()
						.getFactoryUnitName());// 单元名称
				team.setXworkShopName(team.getFactoryUnit().getWorkShop()
						.getWorkShopName());// 车间名称
				team.setXfactoryName(team.getFactoryUnit().getWorkShop()
						.getFactory().getFactoryName());// 工厂名称
				lst.add(team);
			}
			pager.setList(lst);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "factoryUnit" });
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加/修改页面中的弹框--单元名称
	 */
	public String ajlist2() {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			if (pager == null) {
				pager = new Pager();
				pager.setOrderType(OrderType.asc);
				pager.setOrderBy("orderList");
			}
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
																// 此处处理普通查询结果
																// Param
																// 是表单提交过来的json
																// 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				// 单元编码
				if (obj.get("factoryUnitCode") != null) {
					String factoryUnitCode = obj.getString("factoryUnitCode")
							.toString();
					map.put("factoryUnitCode", factoryUnitCode);
				}
				// 单元名称
				if (obj.get("factoryUnitName") != null) {
					String factoryUnitName = obj.getString("factoryUnitName")
							.toString();
					map.put("factoryUnitName", factoryUnitName);
				}
				// 车间名称
				if (obj.get("workShopName") != null) {
					String workShopName = obj.getString("workShopName")
							.toString();
					map.put("workShopName", workShopName);
				}
				// 工厂名称
				if (obj.get("factoryName") != null) {
					String factoryName = obj.getString("factoryName")
							.toString();
					map.put("factoryName", factoryName);
				}
			}

			pager = teamService.getFuPager(pager, map);// 查询单元
			List<FactoryUnit> fuList = pager.getList();
			List<FactoryUnit> lst = new ArrayList<FactoryUnit>();
			for (int i = 0; i < fuList.size(); i++) {
				FactoryUnit fu = fuList.get(i);
				fu.setWorkShopName(fu.getWorkShop().getWorkShopName());// 车间名称
				fu.setFactoryName(fu.getWorkShop().getFactory()
						.getFactoryName());// 工厂名称
				lst.add(fu);
			}
			pager.setList(lst);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Team.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		teamService.updateisdel(ids, "Y");
		// for (String id:ids){
		// Process process=processService.load(id);
		// }
		redirectionUrl = "team!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		team = teamService.getOneById(id);
		team.setXfactoryUnitId(team.getFactoryUnit().getId());// 单元ID
		team.setXfactoryUnitName(team.getFactoryUnit().getFactoryUnitName());// 单元名称
		return INPUT;
	}

	// 更新
	public String update() {
		Team t1 = teamService.load(id);
		BeanUtils.copyProperties(team, t1, new String[] { "id" });// 除了id不修改，其他都修改，自动完成设值操作
		teamService.update(t1);
		redirectionUrl = "team!list.action";
		return SUCCESS;
	}

	// 保存
	public String save() {
		team.setCreateDate(new Date());
		teamService.save(team);
		redirectionUrl = "team!list.action";
		return SUCCESS;
	}

	/**
	 * 保存/修改时，判断班组编码是否重复
	 */
	public String getCk() {
		List<Team> l = teamService.getByCode(info);
		if (l.size() > 0) {
			return this.ajaxJsonErrorMessage("e");
		}
		return this.ajaxJsonSuccessMessage("s");
	}

	/**
	 */
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public TeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	// 获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "StateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public FactoryUnit getFactoryUnit() {
		return factoryUnit;
	}

	public void setFactoryUnit(FactoryUnit factoryUnit) {
		this.factoryUnit = factoryUnit;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}
	

}
