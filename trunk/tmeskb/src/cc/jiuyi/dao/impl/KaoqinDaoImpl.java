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
import cc.jiuyi.dao.KaoqinDao;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.TempKaoqin;

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
			/*if(map.get("teams")!=null)
			{
			    detachedCriteria.add(Restrictions.like("teams", "%"+map.get("teams")+"%"));
			}*/
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

	@SuppressWarnings("unchecked")
	public List<Kaoqin> getKaoqinList(String productDate, String shift) {
		String hql = "";
		if(shift!=null && !"".equals(shift)){
			hql =  "from Kaoqin where productdate=? and classtime=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).list();
		}else{
			hql =  "from Kaoqin where productdate=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).list();
		}
	}

	/**
	 * 根据班组和班次和生产日期查询考勤记录是否已存在,如果存在则在返回中给提示
	 */
	@SuppressWarnings("unchecked")
	public List<Kaoqin> getByTPS(String sameTeamId, String productDate,String shift)
	{
		String hql="from Kaoqin where team.id=? and productdate=? and classtime=?";
		return this.getSession().createQuery(hql).setParameter(0, sameTeamId).setParameter(1, productDate).setParameter(2, shift).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Kaoqin> getByTPSA(String sameTeamId, String productDate,String shift,String adminId)
	{
		String hql="from Kaoqin where team.factoryUnit.id=? and productdate=? and classtime=? and emp.id=?";
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
		if(!existAlias(detachedCriteria, "team.factoryUnit", "factoryUnit"))
		{
			detachedCriteria.createAlias("team.factoryUnit", "factoryUnit");
		}
		//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("productdate") != null) {
				detachedCriteria.add(Restrictions.like(
						"productdate",
						"%" + map.get("productdate") + "%"));
			}	
			/*if (map.get("classtime") != null) {
				detachedCriteria.add(Restrictions.like(
						"classtime",
						"%" + map.get("classtime") + "%"));
			}*/
			if (map.get("empname") != null) {
				detachedCriteria.add(Restrictions.like(
						"empname",
						"%" + map.get("empname") + "%"));
			}
			if (map.get("empworkNumber") != null) {
				detachedCriteria.add(Restrictions.like(
						"cardNumber",
						"%" + map.get("empworkNumber") + "%"));
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

	@Override
	public List<Kaoqin> getKaoqinList(String productDate, String shift,String factoryUnitCode) {
		String hql = "";
		if( !"".equals(shift) && "".equals(factoryUnitCode) ){
			hql =  "from Kaoqin where productdate=? and classtime=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).list();
		}else if(!"".equals(shift) && !"".equals(factoryUnitCode)){
			hql =  "from Kaoqin where productdate=? and classtime=? and factoryUnitCode=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift).setParameter(2, factoryUnitCode).list();
		}else if("".equals(shift) && !"".equals(factoryUnitCode)){
			hql =  "from Kaoqin where productdate=? and factoryUnitCode=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, factoryUnitCode).setParameter(1,factoryUnitCode).list();
		}else{
			hql =  "from Kaoqin where productdate=? order by createDate desc";
			return (List<Kaoqin>)this.getSession().createQuery(hql).setParameter(0, productDate).list();	
		}
	}

	public List<Kaoqin> getWorkNumList(String productDate, String shift,String factoryUnitCode, String workState){
		String hql = "from Kaoqin where productdate=? and classtime=? and factoryUnitCode=? and workState=?";
		return (List<Kaoqin>) this.getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, shift)
				.setParameter(2, factoryUnitCode).setParameter(3, workState).list();
	}
	
	public void updateWorkHours(String workHours,String productdate,String classtime,Team team){
		 String hql = "update Kaoqin set workHours=? where productdate=? and classtime=? and team=?";
		 this.getSession().createQuery(hql).setParameter(0, workHours).setParameter(1, productdate).setParameter(2, classtime).setParameter(3, team).executeUpdate();
	 }
}
