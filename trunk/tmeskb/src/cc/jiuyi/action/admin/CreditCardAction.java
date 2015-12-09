package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.CreditCard;
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
	
	private Date createDate;
	
	public String getCredit(){
		HashMap<String, String> map = new HashMap<String,String>();
		CreditCard creditCard = creditCardService.get(createDate);
		if(creditCard == null){//未找到
			map.put("status", "no");
		}else{
			map.put("status", "yes");
			map.put("cardnumber", creditCard.getCardNumber());//卡号
		}
		return ajaxJson(map);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}