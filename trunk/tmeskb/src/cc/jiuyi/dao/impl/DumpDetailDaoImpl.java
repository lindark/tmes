package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDetailDao;
import cc.jiuyi.entity.DumpDetail;

@Repository
public class DumpDetailDaoImpl extends BaseDaoImpl<DumpDetail, String> implements DumpDetailDao {
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,String dumpId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DumpDetail.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("dump.id", dumpId));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public Pager findDumpDetailByPager(Pager pager,
			HashMap<String, String> mapcheck) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DumpDetail.class);
		if (mapcheck.size() > 0) {
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
			if (mapcheck.get("voucherId") != null && !"".equals(mapcheck.get("voucherId"))) {
				detachedCriteria.add(Restrictions.eq(
						"voucherId",
						mapcheck.get("voucherId")));
			}
			if (mapcheck.get("materialCode") != null && !"".equals(mapcheck.get("materialCode"))) {
				detachedCriteria.add(Restrictions.like("matnr",mapcheck.get("materialCode"),MatchMode.START));
			}
			if (mapcheck.get("materialName") != null && !"".equals(mapcheck.get("materialName"))) {
				detachedCriteria.add(Restrictions.like("maktx",mapcheck.get("materialName"),MatchMode.START));
			}
		}
		
		return super.findByPager(pager, detachedCriteria);
	}
	
}
