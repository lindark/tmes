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
import cc.jiuyi.dao.PollingtestDao;
import cc.jiuyi.entity.Pollingtest;

/**
 * Dao接口 - 巡检
 */
@Repository
public class PollingtestDaoImpl extends BaseDaoImpl<Pollingtest, String>
		implements PollingtestDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Pollingtest.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingBill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Pollingtest pollingtest = super.load(id);
			pollingtest.setIsDel(oper);// 标记删除
			super.update(pollingtest);
		}

	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Pollingtest.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (!existAlias(detachedCriteria, "workingBill", "workingBill")) {
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		if (!existAlias(detachedCriteria, "pollingtestUser", "pollingtestUser")) {
			detachedCriteria.createAlias("pollingtestUser", "pollingtestUser");
		}
		if (!existAlias(detachedCriteria, "confirmUser", "confirmUser")) {
			detachedCriteria.createAlias("confirmUser", "confirmUser");
		}
		if (map.size() > 0) {			
			if (map.get("workingBillCode") != null) {
				detachedCriteria.add(Restrictions.like("workingBill.workingBillCode", "%"
						+ map.get("workingBillCode") + "%"));
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like("workingBill.maktx", "%"
						+ map.get("maktx") + "%"));
			}
			if (map.get("start") != null || map.get("end") != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date start = sdf.parse(map.get("start"));
					Date end = sdf.parse(map.get("end"));
					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.between("createDate",
							start, end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.eq(
						"state",map.get("state")));
			}
			if (map.get("pollingtestUserName") != null) {
				detachedCriteria.add(Restrictions.like(
						"pollingtestUser.name",
						"%" + map.get("pollingtestUserName") + "%"));
			}
			if (map.get("confirmUserName") != null) {
				detachedCriteria.add(Restrictions.like(
						"confirmUser.name",
						"%" + map.get("confirmUserName") + "%"));
			}
			
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public List<Pollingtest> getUncheckList() {
		String hql = "from Pollingtest where state='2'";
		return this.getSession().createQuery(hql).list();
	}

	
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from Pollingtest model join model.workingBill model1 join model.pollingtestUser model2 join model.confirmUser model3";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {			
			if (!map.get("workingBillCode").equals("")) {
				if(ishead==0){
					hql+=" where model1.workingBillCode like '%"+map.get("workingBillCode")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.workingBillCode like '%"+map.get("workingBillCode")+"%'";
				}
			}	
			if (!map.get("maktx").equals("")) {
				if(ishead==0){
					hql+=" where model1.maktx like '%"+map.get("maktx")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.maktx like '%"+map.get("maktx")+"%'";
				}
			}	
			if (!map.get("pollingtestUserName").equals("")) {
				if(ishead==0){
					hql+=" where model2.name like '%"+map.get("pollingtestUserName")+"%'";
					ishead=1;
				}else{
					hql+=" and model2.name like '%"+map.get("pollingtestUserName")+"%'";
				}
			}	
			if (!map.get("confirmUserName").equals("")) {
				if(ishead==0){
					hql+=" where model3.name like '%"+map.get("confirmUserName")+"%'";
					ishead=1;
				}else{
					hql+=" and model3.name like '%"+map.get("confirmUserName")+"%'";
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
					end = DateUtils.addDays(end, 1);
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
		}
		
		Query query = getSession().createQuery(hql);
		
		if(parameters.get("start")!=null){
			query.setParameter("start", parameters.get("start"));
		}
		if(parameters.get("end") != null){
			query.setParameter("end", parameters.get("end"));
		}
		
		return query.list();
	}

	
}
