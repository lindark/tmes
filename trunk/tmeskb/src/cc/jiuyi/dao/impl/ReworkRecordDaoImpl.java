package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
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
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ReworkRecordDao;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.ReworkRecord;

/**
 * Dao实现类 - ReworkRecord
 */

@Repository
public class ReworkRecordDaoImpl extends BaseDaoImpl<ReworkRecord, String> implements ReworkRecordDao {

	@Override
	public void delete(String id) {
		ReworkRecord reworkRecord = load(id);
		this.delete(reworkRecord);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ReworkRecord> getReworkRecordList() {
		String hql = "From ReworkRecord reworkRecord order by reworkRecord.id asc reworkRecord.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getReworkRecordPager(Pager pager, HashMap<String, String> map,String id) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ReworkRecord.class);
		/**if(!this.existAlias(detachedCriteria, "workingbill", "workingbill"))
		{
			detachedCriteria.createAlias("workingbill", "workingbill");
		}**/
		/*//责任人
		if(!this.existAlias(detachedCriteria, "duty", "duty"))
		{
			detachedCriteria.createAlias("duty", "duty");
		}
		//确认人
		/*if(!this.existAlias(detachedCriteria, "confirmUser", "confirmUser"))
		{
			detachedCriteria.createAlias("confirmUser", "confirmUser");
		}*/
		//创建人
//		if(!this.existAlias(detachedCriteria, "createUser", "createUser"))
//		{
//			detachedCriteria.createAlias("createUser", "createUser");
//		}
		//修改人
/*		if(!this.existAlias(detachedCriteria, "modifyUser", "modifyUser"))
		{
			detachedCriteria.createAlias("modifyUser", "modifyUser");
		}*/
		pagerSqlByjqGrid(pager,detachedCriteria);	
		//detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("rework.id", id));
		//detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			ReworkRecord reworkRecord=super.load(id);
			reworkRecord.setIsDel(oper);//标记删除
			super.update(reworkRecord);
		}
		
	}
	
	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReworkRecord.class);
		if (!existAlias(detachedCriteria, "rework", "rework")) {
			detachedCriteria.createAlias("rework", "rework");
		}
		if (!existAlias(detachedCriteria, "rework.workingbill", "workingbill")) {
			detachedCriteria.createAlias("rework.workingbill", "workingbill");
		}
//		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if (map.get("productsName") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("productsName") + "%"));
			}
			if (map.get("productsCode") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.matnr",
						"%" + map.get("productsCode") + "%"));
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"rework.state",
						"%" + map.get("state") + "%"));
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
			if(map.get("start")!=null && map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	@Override
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from ReworkRecord model join model.rework model1 join model1.workingbill model2";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("productsCode").equals("")) {
				if(ishead==0){
					hql+=" where model2.mantr like '%"+map.get("productsCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model2.mantr like '%"+map.get("productsCode")+"%'";
				}
			}
			if (!map.get("productsName").equals("")) {
				if(ishead==0){
					hql+=" where model2.materialCode like '%"+map.get("productsName")+"%'";
					ishead=1;
				}else{
					hql+=" and model2.materialCode like '%"+map.get("productsName")+"%'";
				}
			}	
			if (!map.get("state").equals("")) {
				if(ishead==0){
					hql+=" where model1.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.state like '%"+map.get("state")+"%'";
				}
			}	
			if(!map.get("start").equals("") && !map.get("end").equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
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
	
	@Override
	public List<ReworkRecord> getReworkRecord(String id) {
		String hql="from ReworkRecord where rework.id=?";
		return getSession().createQuery(hql).setParameter(0, id).list();
	}

}