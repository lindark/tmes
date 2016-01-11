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
import cc.jiuyi.entity.HandOver;
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
import cc.jiuyi.service.HandOverService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.SwiptCardService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 交接主表
 */
@ParentPackage("admin")
public class HandOverAction extends BaseAdminAction {

	private List<WorkingBill> workingbillList;
	private String cardnumber;
	private String loginid;//当前登录人的ID
	
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
	@Resource
	private HandOverService handOverService;
	
	/**
	 * 刷卡提交
	 * @return
	 */
	public String creditsubmit(){
		Admin admin = adminservice.getByCardnum(cardnumber);//获取当前登录身份
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份当班的所有随工单
		Object[] workingbillIdList = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			workingbillIdList[i] = workingbill.getId();
		}
		
		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//根据随工单获取所有的工序交接记录
		for(HandOverProcess handoverprocess : handoverprocessList){
			String state = handoverprocess.getState();
			if(!state.equals("approval")){//如果状态 不等于 审批完成
				return ajaxJsonErrorMessage("对不起，有工序未交接完成!");
			}
		}
		
		/**主表修改状态和修改人**/
		handOverService.saveandgx(admin,handoverprocessList);
		return ajaxJsonSuccessMessage("您的操作已成功");
	}
	
	/**
	 * 刷卡确认
	 * @return
	 */
	public String creditapproval(){
		Admin admin = adminservice.getByCardnum(cardnumber);//获取当前登录身份
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取登录身份当班的所有随工单
		Object[] workingbillIdList = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			workingbillIdList[i] = workingbill.getId();
		}
		List<HandOverProcess> handoverprocessList = handOverProcessService.getList("beforworkingbill.id", workingbillIdList);//根据随工单获取所有的工序交接记录
		if(handoverprocessList == null){
			return ajaxJsonErrorMessage("未找到任何交接记录");
		}
		try {
			String mblnr="";
			String handoverId="";
			List<String> aufnrList = new ArrayList<String>();
			for(int i=0;i<handoverprocessList.size();i++){//取出所有的不同aufnr 的集合
				HandOverProcess handoverprocess = handoverprocessList.get(i);
				String aufnr = handoverprocess.getBeforworkingbill().getAufnr();//生产订单号
				HandOver handover = handoverprocess.getHandover();
				String state = handover.getState();
				if(!state.equals("2")){
					return ajaxJsonErrorMessage("对不起,交接状态未处于提交状态，无法确认");
				}
				handoverId = handover.getId();
				if(!aufnrList.contains(aufnr)){
					aufnrList.add(aufnr);
				}
			}
			
			List<HandOverProcess> handoverprocessList01 = null;
			for(int i=0;i<aufnrList.size();i++){
				handoverprocessList01 = new ArrayList<HandOverProcess>();
				String aufnr = aufnrList.get(i);
				for(int y=0;y<handoverprocessList.size();y++){
					HandOverProcess handoverprocess = handoverprocessList.get(y);
					if(aufnr.equals(handoverprocess.getBeforworkingbill().getAufnr())){
						handoverprocessList01.add(handoverprocess);
					}
				}
				if(!mblnr.equals(""))
					mblnr+=",";
				String mblnr1 = handoverprocessrfc.BatchHandOver(handoverprocessList01, "",loginid);//执行
				mblnr+=mblnr1;
				handOverService.updateHand(handoverprocessList01,mblnr1,handoverId,admin);
			}
			//以上跟SAP接口完全没有问题，成功后
			kaoqinservice.mergeAdminafterWork(admin,handoverId);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题");
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

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	
}
