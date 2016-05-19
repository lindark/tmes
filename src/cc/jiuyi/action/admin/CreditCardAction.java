package cc.jiuyi.action.admin;

import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.util.ThinkWayUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("admin")
public class CreditCardAction
  extends BaseAdminAction
{
  private static final long serialVersionUID = 3458281237366633447L;
  @Resource
  private CreditCardService creditCardService;
  @Resource
  private CardManagementService cardmanagementservice;
  private Date createDate;
  
  public String getCredit() {
	  /*HttpServletRequest request = getRequest();
    String ip = ThinkWayUtil.getIp2(request);
    System.out.println(ip);
    String serverName = request.getServerName();
    System.out.println(serverName);
    
    HashMap<String, String> map = new HashMap();
    CreditCard creditCard = this.creditCardService.get(this.createDate);
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
    }
    return ajaxJson(map);
  */
	  HttpServletRequest request = getRequest();
	    String ip = ThinkWayUtil.getIp2(request);
	    System.out.println(ip);
	    //ip="192.168.40.40";
	    String serverName = request.getServerName();
	    System.out.println(serverName);
	    HashMap<String, String> map = new HashMap();
	    CardManagement cardment = (CardManagement)this.cardmanagementservice.getByIp(ip==null?"":ip);
	    if(cardment==null){
	    	map.put("status", "no");
	    	System.out.println("------------------------------no=1");
	    	return ajaxJson(map);
	    }else{
	    	 String[] propertyNames = { "createDate", "deviceCode" };
	         Object[] propertyValues = { this.createDate, cardment.getPosCode() };
	         CreditCard creditCard = this.creditCardService.get(propertyNames,propertyValues);
	         if (creditCard == null)
	         {
	           map.put("status", "no");
	           System.out.println("------------------------------no=2");
	           return ajaxJson(map);
	         }
	         else
	         {
	        	 map.put("status", "yes");
	             map.put("cardnumber", creditCard.getCardNumber());
	             System.out.println("------------------------------cardnumber="+map.get("cardnumber"));
	             return ajaxJson(map);
	         }
	    }
  	}
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
}
