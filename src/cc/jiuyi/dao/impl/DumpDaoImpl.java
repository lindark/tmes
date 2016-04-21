package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口 - 转储管理
 */
@Repository
public class DumpDaoImpl extends BaseDaoImpl<Dump, String> implements DumpDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Dump.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if (map.get("voucherId") != null) {
				detachedCriteria.add(Restrictions.like(
						"voucherId",
						"%" + map.get("voucherId") + "%"));
			}	
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"materialCode",
						"%" + map.get("materialCode") + "%"));
			}	
			if (map.get("start") != null && map.get("end")==null) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					Date start=sd.parse(map.get("start"));
					Date now=sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("start") == null && map.get("end")!=null ) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
					Date end=sd.parse(map.get("end"));
					Date now=sd.parse(sd.format(new Date()));
					now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.between("createDate", end, now));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"state",
						"%" + map.get("state") + "%"));
			}	
			if(map.get("start")!=null && map.get("end")!=null){
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
		return super.findByPager(pager, detachedCriteria);
	}
	
	public List<Dump> historyExcelExport(HashMap<String,String> map){
		String hql="from Dump model";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("voucherId").equals("")) {
				if(ishead==0){
					hql+=" where model.voucherId like '%"+map.get("voucherId")+"%'";
					ishead=1;
				}else{
					hql+=" and model.voucherId like '%"+map.get("voucherId")+"%'";
				}
			}	
			if (!map.get("materialCode").equals("")) {
				if(ishead==0){
					hql+=" where model.materialCode like '%"+map.get("materialCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model.materialCode like '%"+map.get("materialCode")+"%'";
				}
			}	
			if (!map.get("state").equals("")) {
				if(ishead==0){
					hql+=" where model.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model.state like '%"+map.get("state")+"%'";
				}
			}	
			if(!map.get("start").equals("") && !map.get("end").equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					System.out.println(map.get("start")); 
					if(ishead==0){
						hql+=" where model.createDate between :start and :end";
						ishead=1;
					}else{
						hql+=" and model.createDate between :start and :end";
					}
					parameters.put("start", start);
					parameters.put("end", end);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		Query query = getSession().createQuery(hql);
		
		if(parameters.get("start") != null){
			query.setParameter("start", parameters.get("start"));
		}
		if(parameters.get("end") != null){
			query.setParameter("end", parameters.get("end"));
		}
		
		return query.list();
	}


	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Dump dump = super.load(id);
			dump.setIsDel(oper);// 标记删除
			super.update(dump);
		}
	}

	/**
	 * jqgrid查询
	 */
	public Pager getAlllist(Pager pager,Admin admin)
	{
		String productionDate=admin.getProductDate();//生产日期
		String shift=admin.getShift();//班次
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Dump.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(productionDate!=null&&!"".equals(productionDate)&&shift!=null&&!"".equals(shift))
		{
			detachedCriteria.add(Restrictions.or(Restrictions.or(Restrictions.eq("state", "2"),Restrictions.eq("state", "3")),Restrictions.and(Restrictions.eq("productionDate", productionDate), Restrictions.eq("shift", shift))));
		}
		Restrictions.eq("isDel", "N");//未删除
		return super.findByPager(pager, detachedCriteria);
	}

	/**
	 * 查询明细表当前生产日期和班次下的同物料编码的已确认的领料数量
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getMengeByConditions(Admin emp)
	{
		String productionDate=emp.getProductDate();//生产日期
		String shift=emp.getShift();//班次
		String hql="";
		if(emp.getTeam().getFactoryUnit()!=null){
			String funid = emp.getTeam().getFactoryUnit().getId();
			if(productionDate!=null&&!"".equals(productionDate)&&shift!=null&&!"".equals(shift))
			{
				hql="select materialCode,sum(allcount) from Dump where state='1' and isDel='N' and productionDate='"+productionDate+"' and shift='"+shift+"' and factoryUnitId='"+funid+"' group by materialCode";
			}
			else
			{
				hql="select materialCode,sum(allcount) from Dump where state='1' and isDel='N' group by materialCode and factoryUnitId='"+funid+"'";
			}
			return this.getSession().createQuery(hql).list();
		}else{
			List<Object[]> list = new ArrayList<Object[]>();
			return list;
		}
		
	}
}
