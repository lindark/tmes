package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ItermediateTestDetailDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.ItermediateTestDetail;

/**
 * Dao实现类 - ItermediateTestDetail
 */

@Repository
public class ItermediateTestDetailDaoImpl extends BaseDaoImpl<ItermediateTestDetail, String> implements
		ItermediateTestDetailDao {

	@Override
	public void delete(String id) {
		ItermediateTestDetail itermediateTestDetail = load(id);
		this.delete(itermediateTestDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<ItermediateTestDetail> getItermediateTestDetailList() {
		String hql = "From ItermediateTestDetail itermediateTestDetail order by itermediateTestDetail.id asc itermediateTestDetail.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getItermediateTestDetailPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ItermediateTestDetail.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
//		if (map.size() > 0) {
//			if(map.get("itermediateTestDetailCode")!=null){
//			    detachedCriteria.add(Restrictions.like("itermediateTestDetailCode", "%"+map.get("itermediateTestDetailCode")+"%"));
//			}		
//			if(map.get("itermediateTestDetailName")!=null){
//				detachedCriteria.add(Restrictions.like("itermediateTestDetailName", "%"+map.get("itermediateTestDetailName")+"%"));
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
			ItermediateTestDetail itermediateTestDetail=super.load(id);
			itermediateTestDetail.setIsDel(oper);//标记删除
			super.update(itermediateTestDetail);
		}
		
	}

	@Override
	public List<ItermediateTestDetail> getItermediateTestDetail(String id) {
		String hql="from ItermediateTestDetail a inner join fetch a.itermediateTest b where b.id=?";
		return getSession().createQuery(hql).setParameter("list", id).list();
	}

	

}