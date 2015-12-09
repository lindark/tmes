package cc.jiuyi.entity;

import javax.persistence.Entity;

/**
 * 实体类 - 刷卡记录表
 */

@Entity
public class CreditCard extends BaseEntity{
	private static final long serialVersionUID = -4784728979003952240L;
	private String deviceCode;//刷卡机编号
	private String cardNumber;//卡号
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
}