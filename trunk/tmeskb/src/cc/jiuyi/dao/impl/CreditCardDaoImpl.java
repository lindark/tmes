package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.CreditCardDao;
import cc.jiuyi.entity.CreditCard;

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
		try {
			String hql = "from CreditCard as model where rtrim(replace(model." + propertyNames[1] +",chr(10),''))= ?  order by model.createDate desc";
			CreditCard cc = (CreditCard)getSession().createQuery(hql).setParameter(0, propertyValues[1].toString().trim()).setMaxResults(1).uniqueResult();
//			System.out.println("------1111111111111------");
			if(cc!=null){
//				System.out.println("------222222222------"+cc.getDeviceCode()+"--------"+cc.getCardNumber());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Long d1 = Long.parseLong(sdf.format((Date)propertyValues[0]));
				Long d2 = Long.parseLong(sdf.format(cc.getCreateDate()));
//				System.out.println("d1-------"+d1+"----------------d2"+d2);
				if(d2>=d1){
//					System.out.println("------------dddddddddddddd22222222>ddd11111------------");
					return cc;
				}else{
//					System.out.println("------------dddddddddddddd11111111>ddd2222222------------");
					return new CreditCard();
				}
			}else{
				return new CreditCard();
			}
			
//			return cc;
		} catch (Exception e) {
			e.printStackTrace();return new CreditCard();
		}
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