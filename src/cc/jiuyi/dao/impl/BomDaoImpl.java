package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.BomDao;
import cc.jiuyi.entity.Bom;
/**
 * Dao实现类 - 产品Bom
 */

@Repository
public class BomDaoImpl  extends BaseDaoImpl<Bom, String> implements BomDao {

	
	@SuppressWarnings("unchecked")
	public List<Bom> getBomList(String aufnr,Integer version,String shift){
		String hql="from Bom where orders.aufnr = ? and version = ? and shift = ? and isDel='N' ";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).setParameter(2, shift).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Bom> getBomList(String aufnr,Integer version){
		String hql="from Bom where orders.aufnr = ? and version = ? and isDel='N' ";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Bom> getBomList(String aufnr,Integer version,String materialCode,String shift){
		String hql="from Bom where orders.aufnr = ? and version = ? and materialCode = ? and shift = ? and isDel='N'";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).setParameter(2, materialCode).setParameter(3, shift).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Bom> getBomList1(String aufnr,Integer version,String materialCode){
		String hql="from Bom where orders.aufnr = ? and version = ? and materialCode = ? and isDel='N' ";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, version).setParameter(2, materialCode).list();
	}
	
	public Integer getMaxVersion(String matnr,String productDate){
		//String hql="select max(a.version) from Bom a where a.orders.matnr = ? and a.effectiveDate <= ? and isDel='N' ";
		String hql="select max(a.version) from Bom a where a.orders.id = ? and a.isDel='N' ";
		//return (Integer)getSession().createQuery(hql).setParameter(0, matnr).setParameter(1, productDate).uniqueResult();
		return (Integer)getSession().createQuery(hql).setParameter(0, matnr).uniqueResult();
	}
	
	
	@Override
	public Integer getMaxversion(String orderId, String productDate) {
		//String hql="select max(a.version) from Bom a where a.orders.id = ? and a.effectiveDate <= ? and a.isDel='N' ";
		String hql="select max(a.version) from Bom a where a.orders.id = ? and a.isDel='N' ";
		//return (Integer)getSession().createQuery(hql).setParameter(0, orderId).setParameter(1, productDate).uniqueResult();
		return (Integer)getSession().createQuery(hql).setParameter(0, orderId).uniqueResult();
	}
	
	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "orders", "orders")){
			detachedCriteria.createAlias("orders", "orders");
		}
		if (map.size() > 0) {
			
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like("materialCode",
						"%" + map.get("materialCode") + "%"));
			}
			if (map.get("materialName") != null) {
				detachedCriteria.add(Restrictions.like("materialName",
						"%" + map.get("materialName") + "%"));
			}
			if(map.get("productsCode") != null){
				detachedCriteria.add(Restrictions.like("orders.matnr",
						"%" + map.get("productsCode") + "%"));
			}
			if(map.get("productsName") != null){
				detachedCriteria.add(Restrictions.like("orders.maktx",
						"%" + map.get("productsName") + "%"));
			}
			if(map.get("shift") != null){
				detachedCriteria.add(Restrictions.like("shift",
						"%" + map.get("shift") + "%"));
			}
			if(map.get("oerderCode")!=null){								
				detachedCriteria.add(Restrictions.like("orders.aufnr", "%"+map.get("oerderCode")+"%"));
			}	
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Integer getMaxVersion(String aufnr) {
		String hql="select max(a.version) from Bom a where a.orders.aufnr = ? and a.isDel='N'";
		return (Integer)getSession().createQuery(hql).setParameter(0, aufnr).uniqueResult();
	}
	

	

	/**
	 * jqGrid:(根据:子件编码/名称,凭证,版本号)查询
	 */
	public Pager getPieceByCondition(Pager pager, HashMap<String, String> map,String aufnr, Integer maxversion)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		//产品
		if(!super.existAlias(detachedCriteria, "orders", "orders"))
		{
			detachedCriteria.createAlias("orders", "orders");
		}
		if(map.size()>0)
		{
			//子件编码
			if(map.get("piececode")!=null)
			{
			    detachedCriteria.add(Restrictions.like("materialCode","%"+ map.get("piececode")+"%"));
			}
			//子件名称
			if(map.get("piecename")!=null)
			{
				detachedCriteria.add(Restrictions.eq("materialName", map.get("piecename")));
			}
		}
		detachedCriteria.add(Restrictions.eq("orders.aufnr", aufnr));
		detachedCriteria.add(Restrictions.eq("version", maxversion));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	/**
	 * jqGrid:(根据:子件编码/名称,凭证,版本号)查询
	 */
	public Pager getPieceByCondition(Pager pager, HashMap<String, String> map,String aufnr, Integer maxversion,String shift)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		//产品
		if(!super.existAlias(detachedCriteria, "orders", "orders"))
		{
			detachedCriteria.createAlias("orders", "orders");
		}
		if(map.size()>0)
		{
			//子件编码
			if(map.get("piececode")!=null)
			{
			    detachedCriteria.add(Restrictions.like("materialCode","%"+ map.get("piececode")+"%"));
			}
			//子件名称
			if(map.get("piecename")!=null)
			{
				detachedCriteria.add(Restrictions.eq("materialName", map.get("piecename")));
			}
		}
		detachedCriteria.add(Restrictions.eq("orders.aufnr", aufnr));
		detachedCriteria.add(Restrictions.eq("version", maxversion));
		detachedCriteria.add(Restrictions.eq("shift", shift));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public String getMaterialName(String materialCode) {
		String hql="select distinct(b.materialName) from Bom b where b.materialCode = ? and b.isDel='N' ";
		return (String)getSession().createQuery(hql).setParameter(0,materialCode).uniqueResult();
	}

	@Override
	public Pager findPagerByOrders(Pager pager, HashMap<String, String> map,
			List<String> idList) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Bom.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "orders", "orders")){
			detachedCriteria.createAlias("orders", "orders");
		}
		
        if (map.size() > 0) {
			
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like("materialCode",
						"%" + map.get("materialCode") + "%"));
			}
			if (map.get("materialName") != null) {
				detachedCriteria.add(Restrictions.like("materialName",
						"%" + map.get("materialName") + "%"));
			}
			
		}
        
        detachedCriteria.add(Restrictions.in("orders.id", idList));
        detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Integer getMaxversion(String shif, String orderId,
			String materialCode) {
		String hql="select max(a.version) from Bom a where a.orders.id =? and a.materialCode=? and a.isDel='N' ";
		return (Integer)getSession().createQuery(hql).setParameter(0, orderId).setParameter(1, materialCode).uniqueResult();
	}

	@Override
	public List<Bom> getBomListRFC(String aufnr, Integer maxversion) {
		String hql="from Bom where orders.aufnr = ? and version = ? and isDel='N' ";
		return getSession().createQuery(hql).setParameter(0, aufnr).setParameter(1, maxversion).list();
	}
}
