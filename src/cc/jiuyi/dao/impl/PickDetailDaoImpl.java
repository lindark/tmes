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
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.PickDetail;

/**
 * Dao实现类 - PickDetail
 */

@Repository
public class PickDetailDaoImpl extends BaseDaoImpl<PickDetail, String> implements
		PickDetailDao {

	@Override
	public void delete(String id) {
		PickDetail pickDetail = load(id);
		this.delete(pickDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<PickDetail> getPickDetailList() {
		String hql = "From PickDetail pickDetail order by pickDetail.id asc pickDetail.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getPickDetailPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PickDetail.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
//		if (map.size() > 0) {
//			if(map.get("pickDetailCode")!=null){
//			    detachedCriteria.add(Restrictions.like("pickDetailCode", "%"+map.get("pickDetailCode")+"%"));
//			}		
//			if(map.get("pickDetailName")!=null){
//				detachedCriteria.add(Restrictions.like("pickDetailName", "%"+map.get("pickDetailName")+"%"));
//			}
//			if(map.get("state")!=null){
//				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
//			}
//			if(map.get("start")!=null||map.get("end")!=null){
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				try{
//					Date start=sdf.parse(map.get("start"));
//					Date end=sdf.parse(map.get("end"));
//					detachedCriteria.add(Restrictions.between("createDate", start, end));
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			PickDetail pickDetail=super.load(id);
			pickDetail.setIsDel(oper);//标记删除
			super.update(pickDetail);
		}
		
	}

	@Override
	public List<PickDetail> getPickDetail(String id) {
		String hql="from PickDetail where pick.id=?";
		return getSession().createQuery(hql).setParameter(0, id).list();
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PickDetail.class);
			if (!existAlias(detachedCriteria, "pick", "pick")) {
				detachedCriteria.createAlias("pick", "pick");
			}
			if (!existAlias(detachedCriteria, "pick.workingbill", "workingbill")) {
				detachedCriteria.createAlias("pick.workingbill", "workingbill");
			}
			//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
			if (map.size() > 0) {
				if (map.get("maktx") != null) {
					detachedCriteria.add(Restrictions.like(
							"workingbill.maktx",
							"%" + map.get("maktx") + "%"));
				}	
				if (map.get("materialCode") != null) {
					detachedCriteria.add(Restrictions.like(
							"materialCode",
							"%" + map.get("materialCode") + "%"));
				}	
				if (map.get("state") != null) {
					detachedCriteria.add(Restrictions.like(
							"pick.state",
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
//						end = DateUtils.addDays(end, 1);
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
//						end = DateUtils.addDays(end, 1);
						detachedCriteria.add(Restrictions.between("createDate", start, end));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from PickDetail model join model.pick model1 join model1.workingbill model2";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("maktx").equals("")) {
				if(ishead==0){
					hql+=" where model2.maktx like '%"+map.get("maktx")+"%'";
					ishead=1;
				}else{
					hql+=" and model2.maktx like '%"+map.get("maktx")+"%'";
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
					hql+=" where model1.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.state like '%"+map.get("state")+"%'";
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

	
	public List<PickDetail> finddetailByapp(String workingbillid,String state){
		String hql="from PickDetail model where model.pick.workingbill.id = ? and model.pick.state = ?";
		return getSession().createQuery(hql).setParameter(0, workingbillid).setParameter(1, state).list();
	}
	
	public Object[] sumAmount(String wbid,String pkstate ,String wicode){
		String sql = "SELECT TRUNC(SUM(CASE WHEN PD.PICKTYPE='261' THEN TO_NUMBER(PD.PICKAMOUNT) ELSE 0 END),3) ,TRUNC(SUM(CASE WHEN PD.PICKTYPE='262' THEN TO_NUMBER(PD.PICKAMOUNT) ELSE 0 END),3) FROM PICKDETAIL PD ,PICK PK WHERE PD.ID='"+wbid+"' AND PD.MATERIALCODE='"+wicode+"' AND PD.PICK_ID=PK.ID AND PK.STATE='"+pkstate+"'";
		return (Object[])getSession().createSQLQuery(sql).uniqueResult();
	}

}