package cc.jiuyi.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessFunctionDao;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 权限对象
 */

@Repository
public class CreditCardDaoImpl extends BaseDaoImpl<CreditCard, String> implements CreditCardDao {
	
	public CreditCard get(Date createdate){
		String hql = "from CreditCard a where a.createDate > ? order by a.createDate asc";
		return (CreditCard) getSession().createQuery(hql).setParameter(0, createdate).setMaxResults(1).uniqueResult();//取createdate 开始的 第一条记录
	}
	@Override
	public CreditCard get(String[] propertyNames, Object[] propertyValues) {

		if (!(propertyNames != null && propertyValues != null && propertyValues.length == propertyNames.length)) {
			throw new RuntimeException(
					"请提供正确的参数值！propertyNames与propertyValues必须一一对应!");
		} //     rtrim(replace(creditcard0_.deviceCode,chr(10),''))
		String hql = "from CreditCard as model where model."+ propertyNames[0] + " > ?  and rtrim(replace(model." + propertyNames[1] +",chr(10),'')) = ? order by model.createDate asc";
		return (CreditCard)getSession().createQuery(hql).setParameter(0, propertyValues[0]).setParameter(1, propertyValues[1].toString().trim()).setMaxResults(1).uniqueResult();
	}
	/**
	 * 根据开始时间和当前时间查询出刷卡表该时间段刷卡的人
	 */
	@SuppressWarnings("unchecked")
	public List<CreditCard> getByDate(Date startdate, Date enddate)
	{
		String hql="from CreditCard where createDate between ? and ?)";
		return this.getSession().createQuery(hql).setParameter(0, startdate).setParameter(1,enddate).list();
	}

	@Override
	public void deleteCrard() {
		//GregorianCalendar g=new GregorianCalendar();
		//g.add(g.DATE, -1);  //设置为前一天
		Calendar g = Calendar.getInstance();
		//System.out.println(g.get(Calendar.HOUR_OF_DAY));
		//System.out.println(g.HOUR_OF_DAY);
		//g.add(g.HOUR_OF_DAY, -1);
		int year = g.get(Calendar.YEAR);
		String month = (g.get(Calendar.MONTH)+1)+"";
		if(month.length()==1){
			month = "0"+month;
		}
		String date = (g.get(Calendar.DATE))+"";
		if(date.length()==1){
			date = "0"+date;
		}
		String hour = (g.get(Calendar.HOUR_OF_DAY))+"";
		if(hour.length()==1){
			hour = "0"+hour;
		}
		String sql = "INSERT INTO CREDITCARDCOPY (SELECT * FROM CREDITCARD WHERE  MODIFYDATE < TO_DATE('"+year+"-"+month+"-"+date+" "+hour+"','yyyy-MM-dd hh24'))";
		//String hql1 = "insert into CreditCardCopy (select * from CreditCard where modifyDate < to_date('"+year+"-"+month+"-01"+"','yyyy-MM-dd'))";
		getSession().createSQLQuery(sql).executeUpdate();
		
		String hql = "delete from CreditCard where modifyDate < to_date('"+year+"-"+month+"-"+date+" "+hour+"','yyyy-MM-dd hh24')";
		getSession().createQuery(hql).executeUpdate();
	}

	
}