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
import cc.jiuyi.dao.UpDownDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.UpDown;

/**
 * Dao实现类 - 上架/下架
 */

@Repository
public class UpDownDaoImpl extends BaseDaoImpl<UpDown, String> implements
		UpDownDao {
	
	
	public Pager findByPager(Pager pager,Admin admin,List<String> list) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UpDown.class);
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		detachedCriteria.add(Restrictions.in("type", list));
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override//@author Reece 2016/3/22
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UpDown.class);

		//detachedCriteria.add(Restrictions.eq("workingbill.id", "4028c781532c74d701532ca1986e0014"));//测试
		if (map.size() > 0) {
			if (map.get("matnr") != null) {
				detachedCriteria.add(Restrictions.like(
						"matnr",
						"%" + map.get("matnr") + "%"));
			}	
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"maktx",
						"%" + map.get("maktx") + "%"));
			}	
			if (map.get("tanum") != null) {
				detachedCriteria.add(Restrictions.like(
						"tanum",
						"%" + map.get("tanum") + "%"));
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
	
	//@author Reece 2016/3/22
	public List<UpDown> historyExcelExport(HashMap<String,String> map){
		String hql="from UpDown model";
		Integer ishead=0;
		Map<String,Object> parameters = new HashMap<String,Object>();
			if (!map.get("matnr").equals("")) {
				if(ishead==0){
					hql+=" where model.matnr like '%"+map.get("matnr")+"%'";
					ishead=1;
				}else{
					hql+=" and model.matnr like '%"+map.get("matnr")+"%'";
				}
			}	
			if (!map.get("maktx").equals("")) {
				if(ishead==0){
					hql+=" where model.maktx like '%"+map.get("maktx")+"%'";
					ishead=1;
				}else{
					hql+=" and model.maktx like '%"+map.get("maktx")+"%'";
				}
			}	
			if (!map.get("tanum").equals("")) {
				if(ishead==0){
					hql+=" where model.tanum like '%"+map.get("tanum")+"%'";
					ishead=1;
				}else{
					hql+=" and model.tanum like '%"+map.get("tanum")+"%'";
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