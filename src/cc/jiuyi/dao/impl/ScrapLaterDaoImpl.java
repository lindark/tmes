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
import cc.jiuyi.dao.ScrapLaterDao;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;

/**
 * 报废后产出记录
 * @author gaoyf
 *
 */
@Repository
public class ScrapLaterDaoImpl extends BaseDaoImpl<ScrapLater, String> implements ScrapLaterDao
{

	/**
	 * 根据主表id获取产出表数据
	 */
	@SuppressWarnings("unchecked")
	public List<ScrapLater> getSlBySid(String sid)
	{
		String hql="from ScrapLater where scrap.id=?";
		return this.getSession().createQuery(hql).setParameter(0, sid).list();
	}
	
	@Override
	public Pager getLaterPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ScrapLater.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "scrap", "scrap")){
			detachedCriteria.createAlias("scrap", "scrap");//表名，别名*/							
		}
		if (map.size() > 0) {					
			if(map.get("slmatterNum")!=null){
				detachedCriteria.add(Restrictions.like("slmatterNum", "%"+map.get("slmatterNum")+"%"));
			}
			if(map.get("slmatterDes")!=null){
				detachedCriteria.add(Restrictions.like("slmatterDes", "%"+map.get("slmatterDes")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("scrap.state", "%"+map.get("state")+"%"));
			}	
			if(map.get("start")!=null||map.get("end")!=null){
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
		if(!super.existAlias(detachedCriteria, "scrap.workingBill", "workingBill")){
			detachedCriteria.createAlias("scrap.workingBill", "workingBill");//表名，别名*/							
		}
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql="from ScrapLater model join model.scrap model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("slmatterNum").equals("")) {
				if(ishead==0){
					hql+=" where model.slmatterNum like '%"+map.get("slmatterNum")+"%'";
					ishead=1;
				}else{
					hql+=" and model.slmatterNum like '%"+map.get("slmatterNum")+"%'";
				}
			}	
			if (!map.get("slmatterDes").equals("")) {
				if(ishead==0){
					hql+=" where model.slmatterDes like '%"+map.get("slmatterDes")+"%'";
					ishead=1;
				}else{
					hql+=" and model.slmatterDes like '%"+map.get("slmatterDes")+"%'";
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
