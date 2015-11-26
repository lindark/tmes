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
import cc.jiuyi.dao.PickDetailDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PickDetail;

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
		String hql="from PickDetail a inner join fetch a.pick b where b.id=?";
		return getSession().createQuery(hql).setParameter("list", id).list();
	}

	

}