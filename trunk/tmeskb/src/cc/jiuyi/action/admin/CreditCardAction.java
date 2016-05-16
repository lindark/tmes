package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionContext;

import cc.jiuyi.action.cron.WorkingBillJobAll;
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
	public static Logger log = Logger.getLogger(CreditCardAction.class);
	@Resource
	private CreditCardService creditCardService;
	@Resource
	private CardManagementService cardmanagementservice;
	
	private Date createDate;
	
	public String getCredit(){

	    HttpServletRequest request = getRequest();
	    String ip = ThinkWayUtil.getIp2(request);
	    //String ip = "192.168.19.17";
	    log.info("ip="+ip);
	    //System.out.println(ip);
	    String serverName = request.getServerName();
	    log.info("serverName="+serverName);
	    //System.out.println(serverName);
	    
	    HashMap<String, String> map =	 new HashMap();
	    map.put("status", "yes");
	   // map.put("cardnumber", "2657143831");
	    map.put("cardnumber", "2661135367");
	   /* CreditCard creditCard = this.creditCardService.get(this.createDate);
	    if (creditCard == null)
	    {
	      map.put("status", "no");
	    }
	    else
	    {
	      String deviceCode = creditCard.getDeviceCode();
	      deviceCode = StringUtils.substringBefore(deviceCode, "\n");
	      
	      String[] propertyNames = { "posCode", "isDel" };
	      Object[] propertyValues = { deviceCode, "N" };
	      CardManagement cardment = (CardManagement)this.cardmanagementservice.get(propertyNames, propertyValues);
	      if (cardment == null)
	      {
	        map.put("status", "no");
	      }
	      else
	      {
	        String pcip = cardment.getPcIp();
	        if (ip.equals(pcip))
	        {
	          map.put("status", "yes");
	          map.put("cardnumber", creditCard.getCardNumber());
	        }
	        else
	        {
	          map.put("status", "no");
	        }
	      }
	    }*/
		return ajaxJson(map);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}