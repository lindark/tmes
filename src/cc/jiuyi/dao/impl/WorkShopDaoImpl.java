package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.WorkShopDao;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.WorkShop;

/**
 * Dao实现类 - workShop
 */

@Repository
public class WorkShopDaoImpl extends BaseDaoImpl<WorkShop, String> implements
		WorkShopDao {

	@Override
	public void delete(String id) {
		WorkShop workShop = load(id);
		this.delete(workShop);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WorkShop> getWorkShopList() {
		String hql = "From WorkShop workShop order by workShop.id asc workShop.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getWorkShopPager(Pager pager, HashMap<String, String> map,String factoryName) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(WorkShop.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "factory", "factory"))
			detachedCriteria.createAlias("factory", "factory");//表名，别名

		if (map.size() > 0) {
			if(map.get("workShopCode")!=null){
			    detachedCriteria.add(Restrictions.like("workShopCode", "%"+map.get("workShopCode")+"%"));
			}		
			if(map.get("workShopName")!=null){
				detachedCriteria.add(Restrictions.like("workShopName", "%"+map.get("workShopName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			
			if(map.get("factoryName1")!=null){								
				detachedCriteria.add(Restrictions.like("factory.factoryName", "%"+map.get("factoryName1")+"%"));
			}			
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			WorkShop workShop=super.load(id);
			workShop.setIsDel(oper);//标记删除
			super.update(workShop);
		}
		
	}

	@SuppressWarnings("unchecked")
	public boolean isExistByWorkShopCode(String workShopCode) {
		String hql = "from WorkShop workShop where lower(workShop.workShopCode) = lower(?)";
		WorkShop workShop = (WorkShop) getSession().createQuery(hql).setParameter(0, workShopCode).uniqueResult();
		if (workShop != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Factory> getAllFactory(){
		String hql = "from Factory factory where lower(factory.isDel) = lower(?)";
		List<Factory> factoryList=getSession().createQuery(hql).setParameter(0, "N").list();
		return factoryList;
	}
}