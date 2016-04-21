package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ScrapLaterService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 报废产出后记录
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class ScrapLaterAction extends BaseAdminAction
{

	private static final long serialVersionUID = -4470013702473708615L;
	
	@Resource
	private ScrapLaterService scrapLaterService;
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService wbService;//随工单
	@Resource
	private DictService dictService;//字典表
	@Resource
	private TempKaoqinService tempKaoqinService;
	
	private Admin admin;
	private WorkingBill workingbill;//随工单
	private String slmatterNum;
	private String state;
	private String start;
	private String end;
	private String slmatterDes;
	
	private String wbId;//随工单id	
	
	
	/**
	 * 当前随工单的报废数单数据
	 */
	public String list()
	{
		this.admin=this.adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行报废操作!");
			return ERROR;
		}
		this.workingbill=this.wbService.get(wbId);
		return LIST;
	}
	
	// 报废产出记录
	public String history() {
		return "history";
	}
		
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("slmatterNum") != null) {
				String slmatterNum = obj.getString("slmatterNum")
						.toString();
				map.put("slmatterNum", slmatterNum);
			}
			if (obj.get("slmatterDes") != null) {
				String slmatterDes = obj.getString("slmatterDes")
						.toString();
				map.put("slmatterDes", slmatterDes);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state")
						.toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}			
		}
		pager = scrapLaterService.getLaterPager(pager, map);
		List<ScrapLater> scrapLaterList = pager.getList();
		List<ScrapLater> lst = new ArrayList<ScrapLater>();
		for (int i = 0; i < scrapLaterList.size(); i++) {
			ScrapLater scrapLater = (ScrapLater) scrapLaterList
					.get(i);	
			scrapLater.setWorkingbill(scrapLater.getScrap().getWorkingBill().getWorkingBillCode());
			scrapLater.setProductName(wbService.get(
					scrapLater.getScrap().getWorkingBill().getId()).getMaktx());
			scrapLater.setProductNo(wbService.get(
					scrapLater.getScrap().getWorkingBill().getId()).getMatnr());
			scrapLater.setState(scrapLater.getScrap().getState());
			scrapLater.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "scrapState", scrapLater.getScrap().getState()));
			lst.add(scrapLater);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(ScrapLater.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	
	//Excel导出 @author Reece 2016/3/8
		public String excelexport(){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("slmatterNum", slmatterNum);
			map.put("slmatterDes", slmatterDes);
			map.put("state", state);
			map.put("start", start);
			map.put("end", end);
			
			
			List<String> header = new ArrayList<String>();
			List<Object[]> body = new ArrayList<Object[]>();
	        header.add("随工单号");
	        header.add("产品名称");
	        header.add("产品编码");
	        header.add("物料编码");
	        header.add("物料描述");
	        header.add("报废产出日期");
	        header.add("物料数量");
	        header.add("状态");
	        
	        List<Object[]> workList = scrapLaterService.historyExcelExport(map);
	        for(int i=0;i<workList.size();i++){
	        	Object[] obj = workList.get(i);
	        	ScrapLater scrapLater = (ScrapLater) obj[0];
	        	Scrap scrap = (Scrap)obj[1];
	        	
	        	Object[] bodyval = {scrap.getWorkingBill().getWorkingBillCode(),scrap.getWorkingBill().getMaktx(),scrap.getWorkingBill().getMatnr()
	        			            ,scrapLater.getSlmatterNum(),scrapLater.getSlmatterDes()
	        						,scrapLater.getCreateDate()
	        						,scrapLater.getSlmatterCount()
	        						//,dailywork.getCreateDate(),dailywork.getCreateUser()==null?"":dailywork.getCreateUser().getName()
	        						,ThinkWayUtil.getDictValueByDictKey(dictService, "scrapState", scrap.getState())};
	        	body.add(bodyval);
	        }
			
			try {
				String fileName = "报废产出记录表"+".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("报废产出记录表", header, body, getResponse().getOutputStream());
				getResponse().getOutputStream().flush();
			    getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}		

	public Admin getAdmin() {
		return admin;
	}


	public void setAdmin(Admin admin) {
		this.admin = admin;
	}


	public String getWbId() {
		return wbId;
	}


	public void setWbId(String wbId) {
		this.wbId = wbId;
	}
	
	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "scrapState");
	}

	public String getSlmatterNum() {
		return slmatterNum;
	}

	public void setSlmatterNum(String slmatterNum) {
		this.slmatterNum = slmatterNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getSlmatterDes() {
		return slmatterDes;
	}

	public void setSlmatterDes(String slmatterDes) {
		this.slmatterDes = slmatterDes;
	}

	
}
