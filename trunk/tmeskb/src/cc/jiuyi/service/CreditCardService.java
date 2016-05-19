package cc.jiuyi.service;

import java.util.Date;

import cc.jiuyi.entity.CreditCard;


/**
 * Service接口 - 刷卡记录
 */

public interface CreditCardService extends BaseService<CreditCard, String> {

	public CreditCard get(Date createDate);
	public CreditCard get(String[] propertyNames, Object[] propertyValues);
	public void deleteCrard();
}