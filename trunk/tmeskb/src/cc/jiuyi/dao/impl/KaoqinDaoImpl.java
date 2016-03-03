package cc.jiuyi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.entity.Kaoqin;

/**
 * 考勤
 * @author gaoyf
 *
 */
@Repository
public class KaoqinDaoImpl extends BaseDaoImpl<Kaoqin, String>implements KaoqinDao
{

	/**
	 * 根据当前的日期yyyyMMdd查询数据
	 */
	@SuppressWarnings("unchecked")
	public List<Kaoqin> getByKqdate(String strdate)
	{
		String hql="from Kaoqin where kqdate=? order by createDate desc";
		return this.getSession().createQuery(hql).setParameter(0, strdate).list();
	}

	/**
	 * jqGrid查询
	 */
	public Pager getKaoqinPager(Pager pager, HashMap<String, String> map)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Kaoqin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(map.size()>0)
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			//班组
			if(map.get("teams")!=null)
			{
			    detachedCriteria.add(Restrictions.like("teams", "%"+map.get("teams")+"%"));
			}
			//班次
			if(map.get("classtime")!=null)
			{
				detachedCriteria.add(Restrictions.eq("classtime", map.get("classtime")));
			}
			//开始日期
			if(map.get("start")!=null)
			{
				try
				{
					Date start=sdf.parse(map.get("start"));
					detachedCriteria.add(Restrictions.ge("createDate", start));//大于等于
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
			//结束日期
			if(map.get("end")!=null)
			{
				try
				{
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.le("createDate", end));//大于等于
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
		}
		return super.findByPager(pager, detachedCriteria);
	}

	/**
	 * 根据员工主键ID查询员工
	 */
	public Kaoqin getByCardnumAndSameday(String cardNum,String strdate)
	{
		String hql="from Kaoqin where cardNum=? and kqdate=?";
		return (Kaoqin) this.getSession().createQuery(hql).setParameter(0, cardNum).setParameter(1, strdate).uniqueResult();
	}
}