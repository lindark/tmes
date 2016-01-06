package cc.jiuyi.action.admin;

import java.io.IOException;
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
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.DumpDetailService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 转储管理 明细
 * 
 */
@ParentPackage("admin")
public class DumpDetailAction extends BaseAdminAction {

	private static final long serialVersionUID = -1826268855260790250L;
	
	private DumpDetail dumpDetail;
	private String dumpId;
	private String loginid;
	
	@Resource
	private DumpDetailService dumpDetailService;
	@Resource
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpService dumpService;
	
	public String list() {
		return LIST;
	}
	
	public String history(){
		return "history";
	}
	
	public String historylist(){
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
		dumpId = dumpService.get("voucherId", dumpId).getId();
		pager = dumpDetailService.findPagerByjqGrid(pager, map, dumpId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(DumpDetail.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			List<DumpDetail> dDList = dumpRfc.findMaterialDocumentByMblnr(dumpId,loginid);
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(DumpDetail.class));//排除有关联关系的属性字段 
			JSONArray jsonArray = JSONArray.fromObject(dDList,jsonConfig);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("list", jsonArray);
			return ajaxJson(jsonobject.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO操作失败");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
	}
	
	public DumpDetail getDumpDetail() {
		return dumpDetail;
	}
	public void setDumpDetail(DumpDetail dumpDetail) {
		this.dumpDetail = dumpDetail;
	}
	public String getDumpId() {
		return dumpId;
	}
	public void setDumpId(String dumpId) {
		this.dumpId = dumpId;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	
}
