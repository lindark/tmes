package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.action.cron.ExtremelyMessage;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.SwiptCardService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 交接主表
 */
@ParentPackage("admin")
public class HandOverAction extends BaseAdminAction {

	private List<WorkingBill> workingbillList;
	private String cardnumber;
	
	@Resource
	private AdminService adminservice;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private HandOverProcessService handOverProcessService;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	@Resource
	private KaoqinService kaoqinservice;
	
	/**
	 * 刷卡提交
	 * @return
	 */
	public String creditsubmit(){
//		Admin admin = adminservice.getLoginAdmin();
//		workingbillList = workingbillservice.getListWorkingBillByDate(admin);
//		Object[] workingbillIdList = new Object[workingbillList.size()];
//		for(int i=0;i<workingbillList.size();i++){
//			WorkingBill workingbill = workingbillList.get(i);
//			workingbillIdList[i] = workingbill.getId();
//		}
//		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//取出当班所有的工序交接记录
		
		//TODO 工序交接刷卡提交未完成
		return null;
	}
	
	/**
	 * 刷卡确认
	 * @return
	 */
	public String creditapproval(){
		Admin admin = adminservice.getLoginAdmin();//获取当前登录身份
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份当班的所有随工单
		//List<String> workingbillIdList = new ArrayList<String>();

		Object[] workingbillIdList = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			workingbillIdList[i] = workingbill.getId();
		}
		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//根据随工单获取所有的工序交接记录

		for(int i=0; i<handoverprocessList.size() ;i++){//用于处理如果有一半成功，一半失败的处理
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			if(!StringUtil.isEmpty(handoverprocess.getMblnr()))
				handoverprocessList.remove(i);
		}
		try {
			Boolean flag = true;
			String message = "";
			List<HandOverProcess> handList02 = handoverprocessrfc.BatchHandOver(handoverprocessList,"X");//尝试调用
			for(HandOverProcess handoverprocess : handList02){
				String e_type = handoverprocess.getE_type();
				if(e_type.equals("E")){ //如果有一行发生了错误
					flag = false;
					message +=handoverprocess.getMaterialCode()+":"+handoverprocess.getE_message();
				}
			}
			if(!flag)
				return ajaxJsonErrorMessage(message);
			else{
				flag = true;
				List<HandOverProcess> handList03 = handoverprocessrfc.BatchHandOver(handoverprocessList, "");//执行
				for(HandOverProcess handoverprocess : handList03){
					String e_type = handoverprocess.getE_type();
					if(e_type.equals("E")){ //如果有一行发生了错误
						flag = false;
						message +=handoverprocess.getMaterialCode()+":"+handoverprocess.getE_message();
					}else{
						handOverProcessService.merge(handoverprocess);
					}
				}
				if(!flag)
					return ajaxJsonErrorMessage(message);
			}
			
		//以上跟SAP接口完全没有问题，成功后
			kaoqinservice.mergeAdminafterWork();
			//TODO 工序交接下班未测试
			
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功");
	}

	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}

	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	
	
}
