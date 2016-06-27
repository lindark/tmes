package cc.jiuyi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.TempKaoqinDao;

import cc.jiuyi.entity.TempKaoqin;

/**
 * 临时考勤
 * @author jjt
 *
 */
@Repository
public class TempKaoqinDaoImpl extends BaseDaoImpl<TempKaoqin, String>implements TempKaoqinDao
{

	/**
	 * 根据当前的日期yyyyMMdd查询数据
	 */
	@SuppressWarnings("unchecked")
	public List<TempKaoqin> getByKqdate(String strdate)
	{
		String hql="from TempKaoqin where kqdate=? order by createDate desc";
		return this.getSession().createQuery(hql).setParameter(0, strdate).list();
	}

	/**
	 * jqGrid查询
	 */
	public Pager getTempKaoqinPager(Pager pager, HashMap<String, String> map)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempKaoqin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
			
		//班组	
		detachedCriteria.add(Restrictions.eq("team.id", map.get("teamId")));			
		//班次
		detachedCriteria.add(Restrictions.eq("classtime", map.get("shift")));
		//结束日期
		detachedCriteria.add(Restrictions.eq("productdate", map.get("productDate")));			
		
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<TempKaoqin> getTempKaoqinList(String productDate, String shift) {
		String hql = "";
		if(shift!=null && !"".equals(shift)){
			hql =  "from TempKaoqin where productdate=? and classtime=? order by createDate desc";
			return (List<TempKaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).list();
		}else{
			hql =  "from TempKaoqin where productdate=? order by createDate desc";
			return (List<TempKaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).list();
		}
	}

	/**
	 * 根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示
	 */
	@SuppressWarnings("unchecked")
	public List<TempKaoqin> getByTPS(String sameTeamId, String productDate,String shift)
	{
		String hql="from TempKaoqin where team.id=? and productdate=? and classtime=?";
		return this.getSession().createQuery(hql).setParameter(0, sameTeamId).setParameter(1, productDate).setParameter(2, shift).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TempKaoqin> getByTPSA(String sameTeamId, String productDate,String shift,String adminId)
	{
		String hql="from TempKaoqin where team.factoryUnit.id=? and productdate=? and classtime=? and emp.id=?";
		return this.getSession().createQuery(hql).setParameter(0, sameTeamId).setParameter(1, productDate).setParameter(2, shift).setParameter(3, adminId).list();
	}
	
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempKaoqin.class);
		if (!existAlias(detachedCriteria, "emp", "admin")) {
			detachedCriteria.createAlias("emp", "admin");
		}
		if(!existAlias(detachedCriteria, "admin.team", "team"))
		{
			detachedCriteria.createAlias("admin.team", "team");
		}
		if(!existAlias(detachedCriteria, "admin.team.factoryUnit", "factoryUnit"))
		{
			detachedCriteria.createAlias("admin.team.factoryUnit", "factoryUnit");
		}
		//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("productdate") != null) {
				detachedCriteria.add(Restrictions.like(
						"productdate",
						"%" + map.get("productdate") + "%"));
			}	
			if (map.get("classtime") != null) {
				detachedCriteria.add(Restrictions.like(
						"classtime",
						"%" + map.get("classtime") + "%"));
			}	
			if (map.get("empname") != null) {
				detachedCriteria.add(Restrictions.like(
						"empname",
						"%" + map.get("empname") + "%"));
			}	
			if (map.get("factoryUnitName") != null) {
				detachedCriteria.add(Restrictions.like(
						"factoryUnit.factoryUnitName",
						"%" + map.get("factoryUnitName") + "%"));
			}	
			
		}
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from TempKaoqin model join model.emp model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("productdate").equals("")) {
				if(ishead==0){
					hql+=" where model.productdate like '%"+map.get("productdate")+"%'";
					ishead=1;
				}else{
					hql+=" and model.productdate like '%"+map.get("productdate")+"%'";
				}
			}	
			if (!map.get("classtime").equals("")) {
				if(ishead==0){
					hql+=" where model.classtime like '%"+map.get("classtime")+"%'";
					ishead=1;
				}else{
					hql+=" and model.classtime like '%"+map.get("classtime")+"%'";
				}
			}	
			if (!map.get("empname").equals("")) {
				if(ishead==0){
					hql+=" where model.empname like '%"+map.get("empname")+"%'";
					ishead=1;
				}else{
					hql+=" and model.empname like '%"+map.get("empname")+"%'";
				}
			}	
			if (!map.get("factoryUnitName").equals("")) {
				if(ishead==0){
					hql+=" where model1.team.factoryUnit.factoryUnitName like '%"+map.get("factoryUnitName")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.team.factoryUnit.factoryUnitName like '%"+map.get("factoryUnitName")+"%'";
				}
			}	
			
		Query query = getSession().createQuery(hql);
	
		return query.list();
	}

	public List<TempKaoqin> getWorkNumList(String productDate, String shift,String factoryUnitCode, String workState){
		String hql = "from TempKaoqin where productdate=? and classtime=? and factoryUnitCode=? and workState=?";
		return (List<TempKaoqin>) this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift)
				.setParameter(2, factoryUnitCode).setParameter(3, workState).list();
	}
}
