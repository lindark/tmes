package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.PickDetailDao;
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
		String wheresql = pickDetailpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PickDetail.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
		if (map.size() > 0) {
//			if(map.get("pickDetailCode")!=null){
//			    detachedCriteria.add(Restrictions.like("pickDetailCode", "%"+map.get("pickDetailCode")+"%"));
//			}		
//			if(map.get("pickDetailName")!=null){
//				detachedCriteria.add(Restrictions.like("pickDetailName", "%"+map.get("pickDetailName")+"%"));
//			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
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
		return super.findByPager(pager, detachedCriteria);
	}

	public String pickDetailpagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp()) + " ";
				ishead = 1;
			}

		}
		//System.out.println("wheresql:" + wheresql);
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			PickDetail pickDetail=super.load(id);
			pickDetail.setIsDel(oper);//标记删除
			super.update(pickDetail);
		}
		
	}

//	@SuppressWarnings("unchecked")
//	public boolean isExistByPickDetailCode(String pickDetailCode) {
//		String hql="from PickDetail pickDetail where lower(pickDetail.pickDetailCode)=lower(?)";
//		PickDetail pickDetail=(PickDetail)getSession().createQuery(hql).setParameter(0, pickDetailCode).uniqueResult();
//		System.out.println(hql);
//		if(pickDetail !=null){
//			return true;
//		}else{
//			return false;
//		}
//	}
}