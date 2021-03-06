package cc.jiuyi.dao.impl;

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
import cc.jiuyi.dao.DeptpickDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;

/**
 * Dao实现类 - 部门领料
 */
@Repository
public class DeptpickDaoImpl extends BaseDaoImpl<Deptpick, String> implements
		DeptpickDao {

	
	public Pager findByPager(Pager pager, Admin admin) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deptpick.class);
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit()!=null)
		detachedCriteria.add(Restrictions.eq("factoryUnit.id", admin.getTeam().getFactoryUnit().getId()));
		return findByPager(pager, detachedCriteria);
	}
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deptpick.class);

		//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("materialCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"materialCode",
						"%" + map.get("materialCode") + "%"));
			}	
			if (map.get("ex_mblnr") != null) {
				detachedCriteria.add(Restrictions.like(
						"ex_mblnr",
						"%" + map.get("ex_mblnr") + "%"));
			}	
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"state",
						"%" + map.get("state") + "%"));
			}
			if (map.get("start") != null && map.get("end")==null) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date start=sd.parse(map.get("start")+" 00:00:00");
						//Date now=sd.parse(sd.format(new Date()));
					//now = DateUtils.addDays(now, 1);
					detachedCriteria.add(Restrictions.ge("createDate", start));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("start") == null && map.get("end")!=null ) {
				try {
					SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date end=sd.parse(map.get("end")+" 23:59:59");
					//Date now=sd.parse(sd.format(new Date()));
					//now = DateUtils.addDays(now, 1);
//					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.le("createDate", end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(map.get("start")!=null && map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
//					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	public List<Deptpick> historyExcelExport(HashMap<String,String> map){
		String hql="from Deptpick model";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("ex_mblnr").equals("")) {
				if(ishead==0){
					hql+=" where model.ex_mblnr like '%"+map.get("ex_mblnr")+"%'";
					ishead=1;
				}else{
					hql+=" and model.ex_mblnr like '%"+map.get("ex_mblnr")+"%'";
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
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
					//System.out.println(map.get("start")); 
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

}
