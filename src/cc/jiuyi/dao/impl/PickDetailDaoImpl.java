package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.util.ThinkWayUtil;

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
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"pick.state",
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from PickDetail model join model.pick model1 join model1.workingbill model2";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (map.get("maktx") != null) {
				if(ishead==0){
					hql+=" where model2.maktx like '%"+map.get("maktx")+"%'";
					ishead=1;
				}else{
					hql+=" and model2.maktx like '%"+map.get("maktx")+"%'";
				}
			}	
			if (map.get("state") != null) {
				if(ishead==0){
					hql+=" where model1.state like '%"+map.get("state")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.state like '%"+map.get("state")+"%'";
				}
			}	
			if(map.get("start")!=null && map.get("end")!=null){
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