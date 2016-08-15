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
import cc.jiuyi.dao.ScrapMessageDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.ScrapMessage;

/**
 * 报废信息
 * @author gaoyf
 *
 */
@Repository
public class ScrapMessageDaoImpl extends BaseDaoImpl<ScrapMessage, String> implements ScrapMessageDao
{

	/**
	 * 根据scrap表id和物料表id查询
	 */
	public ScrapMessage getBySidAndMid(String sid, String mid)
	{
		String hql="from ScrapMessage where isDel='N' and scrap_id=? and smmatterNum=?";
		return (ScrapMessage) this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, mid).uniqueResult();
	}

	@Override
	public Pager getMessagePager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ScrapMessage.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "scrap", "scrap")){
			detachedCriteria.createAlias("scrap", "scrap");//表名，别名*/							
		}
		if (map.size() > 0) {
			if(map.get("smmatterNum")!=null){
			    detachedCriteria.add(Restrictions.like("smmatterNum", "%"+map.get("smmatterNum")+"%"));
			}	
			if(map.get("smmatterDes")!=null){
			    detachedCriteria.add(Restrictions.like("smmatterDes", "%"+map.get("smmatterDes")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("scrap.state", "%"+map.get("state")+"%"));
			}	
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					Date start=sdf.parse(map.get("start")+" 00:00:00");
					Date end=sdf.parse(map.get("end")+" 23:59:59");
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
		String hql="from ScrapMessage model join model.scrap model1";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (map.size() > 0) {
			if (!map.get("smmatterNum").equals("")) {
				if(ishead==0){
					hql+=" where model.smmatterNum like '%"+map.get("smmatterNum")+"%'";
					ishead=1;
				}else{
					hql+=" and model.smmatterNum like '%"+map.get("smmatterNum")+"%'";
				}
			}	
			if (!map.get("smmatterDes").equals("")) {
				if(ishead==0){
					hql+=" where model.smmatterDes like '%"+map.get("smmatterDes")+"%'";
					ishead=1;
				}else{
					hql+=" and model.smmatterDes like '%"+map.get("smmatterDes")+"%'";
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
