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
import cc.jiuyi.dao.SampleDao;

import cc.jiuyi.entity.Sample;
/**
 * 抽检
 * @author gaoyf
 *
 */
@Repository
public class SampleDaoImpl extends BaseDaoImpl<Sample, String> implements SampleDao
{
	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public Pager getSamplePager(Pager pager,String wbId)
	{
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Sample.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if(!existAlias(detachedCriteria, "workingBill", "workingBill"))
		{
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));
		detachedCriteria.add(Restrictions.eq("workingBill.id", wbId));
		return super.findByPager(pager,detachedCriteria);
	}

	/** 
	 * 抽检表
	 *  查询页面
	 */
	@Override
	public List<Sample> getUncheckList() {
		String hql="from Sample where state='1'";
		return this.getSession().createQuery(hql).list();
	}
	
	public Pager getSamplePager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Sample.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!existAlias(detachedCriteria, "workingBill", "workingBill"))
		{
			detachedCriteria.createAlias("workingBill", "workingBill");
		}
		
		if (map.size() > 0) {
			if (map.get("xproductname") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingBill.maktx",
						"%" + map.get("xproductname") + "%"));
			}
			if (map.get("xproductnum") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingBill.matnr",
						"%" + map.get("xproductnum") + "%"));
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
					end = DateUtils.addDays(end, 1);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			if (map.get("xsampler") != null) {
				if(!existAlias(detachedCriteria, "sampler", "admin"))
				{
					detachedCriteria.createAlias("sampler", "admin");
				}
				detachedCriteria.add(Restrictions.like(
						"admin.name",
						"%" + map.get("xsampler") + "%"));
			}	
			if (map.get("xcomfirmation") != null) {
				if(!existAlias(detachedCriteria, "comfirmation", "Admin"))
				{
					detachedCriteria.createAlias("comfirmation", "Admin");
				}
				detachedCriteria.add(Restrictions.like(
						"Admin.name",
						"%" + map.get("xcomfirmation") + "%"));
			}	
		}
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	/**
	 * 打印excel
	 */
	@Override
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from Sample model join model.workingBill model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("xproductname").equals("")) {
				if(ishead==0){
					hql+=" where model1.maktx like '%"+map.get("xproductname")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.maktx like '%"+map.get("xproductname")+"%'";
				}
			}
			if (!map.get("xproductnum").equals("")) {
				if(ishead==0){
					hql+=" where model1.matnr like '%"+map.get("xproductnum")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.matnr like '%"+map.get("xproductnum")+"%'";
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
			if (!map.get("xsampler").equals("")) {
				if(ishead==0){
					hql+=" where model.sampler like '%"+map.get("xsampler")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.sampler like '%"+map.get("xsampler")+"%'";
				}
			}
			if (!map.get("xcomfirmation").equals("")) {
				if(ishead==0){
					hql+=" where model.comfirmation like '%"+map.get("xcomfirmation")+"%'";
					ishead=1;
				}else{
					hql+=" and model.comfirmation like '%"+map.get("xcomfirmation")+"%'";
				}
			}
		}
		hql += " ORDER BY model1.productDate DESC,model1.workcenter,model1.teamName ";
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
