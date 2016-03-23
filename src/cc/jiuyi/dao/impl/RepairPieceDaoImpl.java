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
import cc.jiuyi.dao.RepairPieceDao;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.RepairPiece;

/**
 * 返修--组件
 * @author lenovo
 *
 */
@Repository
public class RepairPieceDaoImpl extends BaseDaoImpl<RepairPiece, String> implements RepairPieceDao
{

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RepairPiece.class);
		if (!existAlias(detachedCriteria, "repair", "repair")) {
			detachedCriteria.createAlias("repair", "repair");
		}
		if (!existAlias(detachedCriteria, "repair.workingbill", "workingbill")) {
			detachedCriteria.createAlias("repair.workingbill", "workingbill");
		}
		//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("maktx") + "%"));
			}	
			if (map.get("mblnr") != null) {
				detachedCriteria.add(Restrictions.like(
						"repair.EX_MBLNR",
						"%" + map.get("mblnr") + "%"));
			}	
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like(
						"repair.state",
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
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return super.findByPager(pager,detachedCriteria);
	}
	
	
	public List<Object[]> historyExcelExport(HashMap<String,String> map){
		String hql="from RepairPiece model join model.repair model1 join model1.workingbill model2";
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
			if (!map.get("mblnr").equals("")) {
				if(ishead==0){
					hql+=" where model1.EX_MBLNR like '%"+map.get("mblnr")+"%'";
					ishead=1;
				}else{
					hql+=" and model1.EX_MBLNR like '%"+map.get("mblnr")+"%'";
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
}
