package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.service.CreditCardService;

@ParentPackage("admin")
public class CreditCardAction
  extends BaseAdminAction
{
  private static final long serialVersionUID = 3458281237366633447L;
  public static Logger log = Logger.getLogger(CreditCardAction.class);
  
  private String username;
  @Resource
  private CreditCardService creditCardService;
  @Resource
  private CardManagementService cardmanagementservice;
  private Date createDate;
  @Resource
  private AdminService adminService;
  
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
	  
//		HttpServletRequest request = getRequest();
//		    String ip = ThinkWayUtil.getIp2(request);
//		    //System.out.println(ip);
//		    log.info(ip);
//		   // ip="192.168.37.24";//测试机
//		   // ip="192.168.40.40";
//		   //String serverName = request.getServerName();
//		    //System.out.println(serverName);
//		    HashMap<String, String> map = new HashMap();
//		    boolean flag = true;
//		    int i=0;
//		    	CardManagement cardment = (CardManagement)this.cardmanagementservice.getByIp(ip==null?"":ip);
//			    if(cardment==null){
//			    	map.put("status", "no");
//			    	map.put("cardnumber", "当前ip未找到对应设备");
//			    	//System.out.println("------------------------------no=1");
////			    	log.info("-------"+this.createDate+"-------"+ip+"-----no=1");
//			    	return ajaxJson(map);
//			    }
//			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			    String date = sdf.format(this.createDate);
////			    log.info("----111---"+cardment.getPosCode()+"---2222----"+date+"-----"+"flag="+flag);
////			    System.out.println("----111---"+cardment.getPosCode()+"---2222----"+date+"-----"+"flag="+flag);
//			    String[] propertyNames = { "createDate", "deviceCode" };
//		         Object[] propertyValues = { this.createDate, cardment.getPosCode() };
//			    while(flag){ 	 
//			         CreditCard creditCard = creditCardService.get(propertyNames,propertyValues);
////			         System.out.println("----333---"+creditCard.getCardNumber()+"---444----");
////			         log.info("----333---"+creditCard.getCardNumber()+"---444----");
//			         if (creditCard.getId()==null)
//			         {
//			          // map.put("status", "no");
//			        	// System.out.println("------------------------------no=2");
//			        	 try {
//			        		 Thread.sleep(1000);//等待一秒
//			        		 ++i;
////			        		 System.out.println("-------"+this.createDate+"-------"+ip+"-----no=2");
//			        		 log.info("-------"+this.createDate+"-------"+ip+"-----no=2");
//			        		 if(i ==20){
//			        			 flag=false;
//			        		 }
//						} catch (Exception e) {
//							e.printStackTrace();
//						} 
//			         }
//			         else
//			         {
//			        	 map.put("status", "yes");
//			             map.put("cardnumber", creditCard.getCardNumber());
////			             System.out.println("------------------------------cardnumber="+map.get("cardnumber"));
//			             flag = false;
//			         }
//			    }
//			    log.info("-------"+this.createDate+"-------"+ip+"-----cardnumber="+map.get("cardnumber"));
//			    
//			    if(map.get("cardnumber")!=null && !"".equals(map.get("cardnumber"))){
////			    	System.out.println("---------------------"+map.get("cardnumber")+"---------------------");
//		       		  Admin admin = adminService.get("cardNumber",map.get("cardnumber"));
////		       		  System.out.println("---------------------"+admin.getWorkNumber()+"---------------------");
//		       		  if(admin!=null){
//		       			  map.put("statuses", "success");
//		       			  map.put("worknumber", admin.getWorkNumber());
//		       		  }else{
//		       			  map.put("statuses", "error");
//		       			  map.put("worknumber", "当前卡号未找到员工");
//		       		  }
//		       	  }else{
//		       		  map.put("statuses", "error");
//		       		  map.put("worknumber", "未获取到卡号");
//		       	  }
//		    return ajaxJson(map);
		  HashMap<String, String> map = new HashMap();
		  map.put("status", "yes");
	      map.put("cardnumber", "2660719303");//2657143831  2572961828
	      return ajaxJson(map);
	  	}
  public String getUsername(){
	  System.out.println("username--------------------------------------------");
	  Map<String,String> map = new HashMap<String,String>();
	  if(username!=null && !"".equals(username)){
		  Admin admin = adminService.get("cardNumber",username);
		  if(admin!=null){
			  map.put("status", "success");
			  map.put("message", admin.getWorkNumber());
		  }else{
			  map.put("status", "error");
			  map.put("message", "当前卡号未找到员工");
		  }
	  }else{
		  map.put("status", "error");
		  map.put("message", "未获取到卡号");
	  }
	  JSONObject obj = JSONObject.fromObject(map);
	  return ajax(obj.toString(),"text/html");
  }
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
public void setUsername(String username) {
	this.username = username;
}
}
