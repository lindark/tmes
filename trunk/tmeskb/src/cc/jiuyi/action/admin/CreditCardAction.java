package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionContext;

import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 刷卡记录
 */

@ParentPackage("admin")
public class CreditCardAction extends BaseAdminAction {

	private static final long serialVersionUID = 3458281237366633447L;
	
	@Resource
	private CreditCardService creditCardService;
	@Resource
	private CardManagementService cardmanagementservice;
	
	private Date createDate;
	
	public String getCredit(){
		HttpServletRequest request = getRequest();
		String ip = ThinkWayUtil.getIp2(request);
		System.out.println(ip);
		String serverName = request.getServerName();
		System.out.println(serverName);
		
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("status", "yes");
		map.put("cardnumber", "2660432599");
		//CreditCard creditCard = creditCardService.get(createDate);
		//if(creditCard == null){//未找到
//			map.put("status", "no");
		//	map.put("status", "yes");
		//	map.put("cardnumber", "2660432599");
	//	}else{
			map.put("status", "yes");
			map.put("cardnumber", "2660432599");
			/*String deviceCode = creditCard.getDeviceCode();//刷卡机编号
			deviceCode = StringUtils.substringBefore(deviceCode, "\n");
			
			String [] propertyNames = {"posCode","isDel"};
			Object [] propertyValues = {deviceCode,"N"};
			CardManagement cardment = cardmanagementservice.get(propertyNames,propertyValues);
			if(cardment == null){
				map.put("status", "no");
			}else{
				String pcip = cardment.getPcIp();
				if(ip.equals(pcip)){
					map.put("status", "yes");
					map.put("cardnumber", creditCard.getCardNumber());//卡号
				}else{
					map.put("status", "no");
				}
			}*/
	//	}
		
		return ajaxJson(map);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}