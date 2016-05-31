package cc.jiuyi.action.admin;

import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.util.ThinkWayUtil;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("admin")
public class CreditCardAction
  extends BaseAdminAction
{
  private static final long serialVersionUID = 3458281237366633447L;
  public static Logger log = Logger.getLogger(CreditCard.class);
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
	    //System.out.println(ip);
	    log.info(ip);
	   // ip="192.168.37.24";//测试机
	   // ip="192.168.40.40";
	   //String serverName = request.getServerName();
	    //System.out.println(serverName);
	    HashMap<String, String> map = new HashMap();
	    boolean flag = true;
	    int i=0;
	    	CardManagement cardment = (CardManagement)this.cardmanagementservice.getByIp(ip==null?"":ip);
		    if(cardment==null){
		    	map.put("status", "no");
		    	map.put("cardnumber", "当前ip未找到对应设备");
		    	//System.out.println("------------------------------no=1");
		    	log.info("-------"+this.createDate+"-------"+ip+"-----no=1");
		    	return ajaxJson(map);
		    }
		    String[] propertyNames = { "createDate", "deviceCode" };
	         Object[] propertyValues = { this.createDate, cardment.getPosCode() };
		    while(flag){ 	 
		         CreditCard creditCard = this.creditCardService.get(propertyNames,propertyValues);
		         if (creditCard == null)
		         {
		          // map.put("status", "no");
		        	// System.out.println("------------------------------no=2");
		        	 log.info("-------"+this.createDate+"-------"+ip+"-----no=2");
		        	 try {
		        		 Thread.sleep(1000);//等待一秒
		        		 ++i;
		        		 if(i ==20)flag=false;
					} catch (Exception e) {
						e.printStackTrace();
					} 
		         }
		         else
		         {
		        	 map.put("status", "yes");
		             map.put("cardnumber", creditCard.getCardNumber());
		            // System.out.println("------------------------------cardnumber="+map.get("cardnumber"));
		             log.info("-------"+this.createDate+"-------"+ip+"-----cardnumber="+map.get("cardnumber"));
		             flag = false;
		         }
		    }
	    return ajaxJson(map);
//	  HashMap<String, String> map = new HashMap();
//	  map.put("status", "yes");
//      map.put("cardnumber", "2657143831");
//      return ajaxJson(map);
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
