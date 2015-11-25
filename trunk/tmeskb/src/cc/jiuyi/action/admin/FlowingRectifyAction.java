package cc.jiuyi.action.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.FlowingRectifyService;
import cc.jiuyi.service.QualityService;

/**
 * 后台Action类 - 整改情况跟踪
 */
@ParentPackage("admin")
public class FlowingRectifyAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323213806324131043L;
	
	private Quality quality;
	private FlowingRectify flowingRectify;
	private String flowingId;
	
	@Resource
	private QualityService qualityService;	
	@Resource
	private FlowingRectifyService flowingRectifyService;
	@Resource
	private AdminService adminService;
		
	public String save() {		
		 Admin admin = adminService.getLoginAdmin();
		 String id[]=ids[0].split(",");
		 quality = qualityService.load(id[0]);
		 String content[]=id[1].split("=");
		 
        if(id.length>2){
        	FlowingRectify persistent = flowingRectifyService.load(id[2]);
			persistent.setContent(content[1]);
			flowingRectifyService.update(persistent);
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, "操作成功");
			jsonMap.put(ID, persistent.getId());
			return ajaxJson(jsonMap);
        }else{
        	FlowingRectify flowingRectify1 = new FlowingRectify();
            flowingRectify1.setOperater(admin);
            flowingRectify1.setQuality(quality);
            flowingRectify1.setContent(content[1]);
            flowingRectifyService.save(flowingRectify1);
            
            Map<String, String> jsonMap = new HashMap<String, String>();
    		jsonMap.put(STATUS, SUCCESS);
    		jsonMap.put(MESSAGE, "保存成功");
    		jsonMap.put(ID, flowingRectify1.getId());
    		System.out.println(flowingRectify1.getId());
    		return ajaxJson(jsonMap);
        }
	    
	}
	
	
	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}


    public FlowingRectify getFlowingRectify() {
		return flowingRectify;
	}


	public void setFlowingRectify(FlowingRectify flowingRectify) {
		this.flowingRectify = flowingRectify;
	}


	public String getFlowingId() {
		return flowingId;
	}


	public void setFlowingId(String flowingId) {
		this.flowingId = flowingId;
	}
	
	
}
