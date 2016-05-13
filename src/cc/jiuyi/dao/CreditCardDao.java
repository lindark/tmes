package cc.jiuyi.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.CreditCard;


/**
 * Dao接口 - 刷卡记录表
 */
public interface CreditCardDao extends BaseDao<CreditCard, String> {

	
	public CreditCard get(Date createdate);

	/**
	 * 根据开始时间和当前时间查询出刷卡表该时间段刷卡的人
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public List<CreditCard> getByDate(Date startdate, Date enddate);
	
	public void deleteCrard();
}
