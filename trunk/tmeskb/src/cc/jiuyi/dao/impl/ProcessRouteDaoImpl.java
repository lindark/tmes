package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessRouteDao;
import cc.jiuyi.entity.ProcessRoute;

/**
 * Dao实现类 - 工艺路线
 */

@Repository
public class ProcessRouteDaoImpl extends BaseDaoImpl<ProcessRoute, String>
		implements ProcessRouteDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ProcessRoute.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if (map.get("processCode") != null) {
				detachedCriteria.add(Restrictions.like("processCode",
						"%" + map.get("processCode") + "%"));
			}
			if (map.get("processName") != null) {
				detachedCriteria.add(Restrictions.like("processName",
						"%" + map.get("processName") + "%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	

	@SuppressWarnings("unchecked")
	public List<ProcessRoute> getProcessRouteList(String aufnr, Integer version) {
		String hql="from ProcessRoute where orders.aufnr = ? and version = ?";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).list();
	}
	
	public Integer getMaxVersion(String orderid,String productDate){
		String hql="select max(a.version) from ProcessRoute a where a.orders.id = ? ";/** a.effectiveDate <= ? */
		return (Integer)getSession().createQuery(hql).setParameter(0, orderid).uniqueResult();/**setParameter(1, productDate)*/
	}
	
	public Integer getMaxVersion(String aufnr){
		String hql="select max(a.version) from ProcessRoute a where a.orders.aufnr = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, aufnr).uniqueResult();
	}
	
	public Integer getMaxVersionNew(String aufnr){
		String hql="select max(a.version) from ProcessRoute a where a.orders.id = ?";
		return (Integer)getSession().createQuery(hql).setParameter(0, aufnr).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getMaxVersion(List<String> orderidList){
		String hql="select max(a.version),a.orders.id from ProcessRoute a where a.orders.id in (:list) group by a.orders.id";
		return (List<Object[]>)getSession().createQuery(hql).setParameterList("list", orderidList).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getMaxVersion1(String workcenter){
		String hql="select max(a.version),a.orders.id from ProcessRoute a where a.workCenter = ? group by a.orders.id";
		return getSession().createQuery(hql).setParameter(0, workcenter).list();
		
	}

	/**
	 * 生产订单号,版本号,编码查询一条工艺路线
	 */
	public ProcessRoute getOneByConditions(String aufnr, Integer maxversion,
			String processCode)
	{
		String hql="from ProcessRoute where orders.aufnr = ? and version = ? and processCode=?";
		return (ProcessRoute) getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, maxversion).setParameter(2, processCode).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public String getProcess(List<String> processRouteIdList,String steus) {
		String hql="select max(p.processCode) from ProcessRoute p where (p.steus = ? or p.steus='PP03') and  p.id in(:list) order by p.processCode desc";
		return (String)getSession().createQuery(hql).setParameter(0, steus).setParameterList("list", processRouteIdList).uniqueResult();
	}
	
	@Override
	public String getProcessName(String processCode) {
		String hql="select p.processName from ProcessRoute p where p.processCode = ? ";
		return (String)getSession().createQuery(hql).setParameter(0,processCode).uniqueResult();
	}
	
	public ProcessRoute getProcessRoute(Integer version,String orderid){
		String hql="from ProcessRoute a where a.version = ? and a.orders.id = ? order by a.processCode asc";
		return (ProcessRoute) getSession().createQuery(hql).setParameter(0, version).setParameter(1, orderid).setMaxResults(1).uniqueResult();
	}
	
}
