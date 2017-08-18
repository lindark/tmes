package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.SarkDao;
import cc.jiuyi.entity.Sark;

/**
 * Dao接口 - 纸箱
 */
@Repository
public class SarkDaoImpl extends BaseDaoImpl<Sark, String> implements
		SarkDao {

	/**
	 * jqgrid查询
	 */
	public Pager getSarkPager(Pager pager,String productDate,String teamshift,String unitId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Sark.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.or(Restrictions.eq("state", "2"),
											 Restrictions.and(Restrictions.and(Restrictions.eq("productDate", productDate),
													 		  				   Restrictions.eq("teamshift", teamshift)
													 		  				   ),
													 		  Restrictions.eq("factoryUnit.id", unitId)
													 		 )
											)
						    );//只查询未确认的及当前生产日期和班次的数据
		return super.findByPager(pager, detachedCriteria);
	}

	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Sark sark = super.load(id);
			//sark.setIsDel(oper);// 标记删除
			super.update(sark);
		}

	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Sark.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
				detachedCriteria.createAlias("workingbill", "workingbill");
			}
			if (map.get("maktx") != null) {
				detachedCriteria.add(Restrictions.like(
						"workingbill.maktx",
						"%" + map.get("maktx") + "%"));
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager findSarkByPager(Pager pager, HashMap<String, String> mapcheck) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Sark.class);
		//pagerSqlByjqGrid(pager, detachedCriteria);
		if (mapcheck.size() > 0) {
			if (mapcheck.get("state") != null && !"".equals(mapcheck.get("state"))) {
				detachedCriteria.add(Restrictions.eq(
						"state",
						mapcheck.get("state")));
			}	
			if(mapcheck.get("start")!=null && !"".equals(mapcheck.get("start")) && mapcheck.get("end")!=null && !"".equals(mapcheck.get("end"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					String d1 = mapcheck.get("start")+" 00:00:00";
					Date start=sdf.parse(d1);
					String d = mapcheck.get("end")+" 23:59:59";
					Date end=sdf.parse(d);
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}else if((mapcheck.get("start")==null || "".equals(mapcheck.get("start"))) && (mapcheck.get("end")!=null && !"".equals(mapcheck.get("end")))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try{
					String d = mapcheck.get("end")+" 23:59:59";
					Date end=sdf.parse(d);
					detachedCriteria.add(Restrictions.le("createDate", end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}else if((mapcheck.get("start")!=null && !"".equals(mapcheck.get("start"))) && (mapcheck.get("end")==null || "".equals(mapcheck.get("end")))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(mapcheck.get("start"));
					detachedCriteria.add(Restrictions.ge("createDate", start));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if (mapcheck.get("mntr") != null && !"".equals(mapcheck.get("mntr"))) {
				detachedCriteria.add(Restrictions.eq(
						"EX_MBLNR",
						mapcheck.get("mntr")));
			}
			if (mapcheck.get("unitId") != null && !"".equals(mapcheck.get("unitId"))) 
			{
				detachedCriteria.add(Restrictions.eq(
						"factoryUnit.id",
						mapcheck.get("unitId")));
			}
		}
		return super.findByPager(pager, detachedCriteria);
		
	}

}
