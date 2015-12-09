package cc.jiuyi.dao;

import java.util.Date;
import java.util.HashMap;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.CreditCard;


/**
 * Dao接口 - 刷卡记录表
 */
public interface CreditCardDao extends BaseDao<CreditCard, String> {

	
	public CreditCard get(Date createdate);
}
