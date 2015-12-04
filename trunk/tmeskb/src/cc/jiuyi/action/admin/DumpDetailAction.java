package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.DumpDetailService;
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
	
	@Resource
	private DumpDetailService dumpDetailService;
	@Resource
	private DumpRfcImpl dumpRfc;
	
	public String list() {
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			List<DumpDetail> dDList = dumpRfc.findMaterialDocumentByMblnr(dumpId);
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

}
