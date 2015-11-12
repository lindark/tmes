package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Team;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


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
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private TeamService teamService;
	@Resource
	private DictService dictService;
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("teamCode") != null) {
				System.out.println("obj=" + obj);
				String teamCode = obj.getString("teamCode").toString();
				map.put("teamCode", teamCode);
			}
			if (obj.get("teamName") != null) {
				String teamName = obj.getString("teamName").toString();
				map.put("teamName", teamName);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = teamService.getTeamPager(pager, map);
			List<Team> teamList = pager.getList();
			List<Team> lst = new ArrayList<Team>();
			for (int i = 0; i < teamList.size(); i++) {
				Team team = (Team) teamList.get(i);
				team.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "teamState", team.getState()));
				
				//team.setAdmin(null);
				lst.add(team);
			}
			
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		teamService.updateisdel(ids, "Y");
//		for (String id:ids){
//			Process process=processService.load(id);
//		}
		redirectionUrl = "team!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			team= teamService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Team persistent = teamService.load(id);
			BeanUtils.copyProperties(team, persistent, new String[] { "id","createDate", "modifyDate"});
			teamService.update(persistent);
			redirectionUrl = "team!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "team.teamCode", message = "班组编号不允许为空!"),
						@RequiredStringValidator(fieldName = "team.teamName", message = "班组名称不允许为空!")
				  }
				  
		)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		teamService.save(team);
		redirectionUrl="team!list.action";
		return SUCCESS;	
	}
		
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


	//获取所有状态
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
	
	
}
