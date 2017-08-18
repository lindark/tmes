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
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Sark;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.service.SarksonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 衬板收货子表
 * @author lenovo
 *
 */
@ParentPackage("admin")
public class SarksonAction extends BaseAdminAction
{

	private static final long serialVersionUID = -4945211952856238371L;
	
	/**=============variable,object,interface  start===================*/
	@Resource
	private SarksonService sarksonService;
	@Resource
	private DictService dictService;
	/**=============end  variabke,object,interface=====================*/
	

	private String MATNR;
	private String productname;
	private String end;
	private String start;
	private String state;
	private String bktxt;
	
	
	
	/**=====================method  start==============================*/
	
	
	// 衬板记录表 @author Reece 2016/3/11
	public String history() {
		return "history";
	}

	
	// 衬板记录表 @author Reece 2016/3/10
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
		try {
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("MATNR") != null) {
					String MATNR = obj.getString("MATNR").toString();
					map.put("MATNR", MATNR);
				}
				if (obj.get("productname") != null) {
					String productname = obj.getString("productname").toString();
					map.put("productname", productname);
				}
				if (obj.get("start") != null) {
					String start = obj.getString("start").toString();
					map.put("start", start);
				}
				if (obj.get("end") != null) {
					String end = obj.getString("end").toString();
					map.put("end", end);
				}
				if (obj.get("start") != null && obj.get("end") != null) {
					String start = obj.get("start").toString();
					String end = obj.get("end").toString();
					map.put("start", start);
					map.put("end", end);
				}
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
				if (obj.get("bktxt") != null) {
					String bktxt = obj.getString("bktxt").toString();
					map.put("bktxt", bktxt);
				}
			}
			pager = sarksonService.historyjqGrid(pager, map);
			List<SarkSon> sarkList = pager.getList();
			List<SarkSon> lst = new ArrayList<SarkSon>();
			for (int i = 0; i < sarkList.size(); i++) {
				SarkSon sarkSon = (SarkSon) sarkList.get(i);
				sarkSon.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "sarkState", sarkSon.getSark().getState()));
				sarkSon.setState(sarkSon.getSark().getState());
				if (sarkSon.getSark().getConfirmUser() != null) {
					sarkSon.setConfirmUser(sarkSon.getSark().getConfirmUser().getName());
				}
				sarkSon.setCreateUser(sarkSon.getSark().getCreateUser().getName());
				if(sarkSon.getSark().getEX_MBLNR() != null){
					sarkSon.setMblnr(sarkSon.getSark().getEX_MBLNR());
				}
				sarkSon.setBktxt(sarkSon.getSark().getBktxt());
				sarkSon.setXteamshift(ThinkWayUtil.getDictValueByDictKey(
						dictService, "kaoqinClasses", sarkSon.getSark().getTeamshift()));
				lst.add(sarkSon);
			}
		
			pager.setList(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(SarkSon.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	// Excel导出 @author Reece 2016/3/14
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MATNR", MATNR);
		map.put("productname", productname);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		map.put("bktxt", bktxt);

		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("随工单号");
		header.add("产品名称");
		header.add("生产日期");
		header.add("班次");
		header.add("物料编码");
		header.add("物料名称");
		header.add("衬板数量");
		header.add("库存地点");
		header.add("物料凭证号");
		header.add("单据编号");
		header.add("收货日期");
		header.add("创建人");
		header.add("确认人");
		header.add("状态");

		List<Object[]> workList = sarksonService.historyExcelExport(map);
		for (int i = 0; i < workList.size(); i++) {
			Object[] obj = workList.get(i);
			SarkSon sarkSon = (SarkSon)obj[0];
			Sark sark = (Sark)obj[1];
			Object[] bodyval = {
					sarkSon.getWbcode(),
					sarkSon.getProductname(),
					sarkSon.getBUDAT(),
					ThinkWayUtil.getDictValueByDictKey(
							dictService, "kaoqinClasses", sarkSon.getSark().getTeamshift()),
					sarkSon.getMATNR(),
					sarkSon.getMATNRDES(),
					sarkSon.getCscount(),
					sarkSon.getLGORT(),
					sarkSon.getSark().getEX_MBLNR() == null ? "":sarkSon.getSark().getEX_MBLNR(),
					sark.getBktxt(),
					sarkSon.getCreateDate(),
					sarkSon.getSark().getCreateUser() == null ? "" : sarkSon.getSark()
							.getCreateUser().getName(),
				    sarkSon.getSark().getConfirmUser() == null ? "" : sarkSon.getSark()
							.getConfirmUser().getName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"sarkState", sarkSon.getSark().getState())};
			body.add(bodyval);
		}

		try {
			String fileName = "衬板收货记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("衬板收货记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**=====================end  method================================*/
	
	/**=====================get/set  start==============================*/
	

	public String getMATNR() {
		return MATNR;
	}


	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}


	public String getProductname() {
		return productname;
	}


	public void setProductname(String productname) {
		this.productname = productname;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getBktxt() {
		return bktxt;
	}


	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}

	
	/**=====================end  get/set================================*/
	
	
	
}
