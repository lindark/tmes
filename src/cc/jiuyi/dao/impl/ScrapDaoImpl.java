package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ScrapDao;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.WorkingBill;

/**
 * 报废
 * @author gaoyf
 *
 */
@Repository
public class ScrapDaoImpl extends BaseDaoImpl<Scrap, String> implements ScrapDao
{

	/**
	 * jqGrid查询
	 */
	public Pager getScrapPager(Pager pager,String wbId)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Scrap.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "workingBill", "workingBill"))
		{
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("workingBill.id", wbId));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Scrap> getUnCheckList() {
		String hql="from Scrap where state='1'";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Scrap.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
				detachedCriteria.createAlias("workingbill", "workingbill");
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("maktx") + "%"));
			}	
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	public List<Scrap> getScrapList(WorkingBill workingBill){
		String hql="from Scrap where workingBill=? and isDel = 'N' and state = '2'";
		return this.getSession().createQuery(hql).setParameter(0, workingBill).list();
	}
}
