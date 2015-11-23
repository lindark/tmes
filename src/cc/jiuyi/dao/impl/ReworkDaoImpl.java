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
import cc.jiuyi.dao.ReworkDao;
import cc.jiuyi.entity.Rework;

/**
 * Dao实现类 - Rework
 */

@Repository
public class ReworkDaoImpl extends BaseDaoImpl<Rework, String> implements ReworkDao {

	@Override
	public void delete(String id) {
		Rework rework = load(id);
		this.delete(rework);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Rework> getReworkList() {
		String hql = "From Rework rework order by rework.id asc rework.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getReworkPager(Pager pager, HashMap<String, String> map,String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Rework.class);
	/*	if(!this.existAlias(detachedCriteria, "workingbill", "workingbill"))
		{
			detachedCriteria.createAlias("workingbill", "workingbill");
		}
		//责任人
		if(!this.existAlias(detachedCriteria, "duty", "duty"))
		{
			detachedCriteria.createAlias("duty", "duty");
		}*/
		//确认人
		/*if(!this.existAlias(detachedCriteria, "confirmUser", "confirmUser"))
		{
			detachedCriteria.createAlias("confirmUser", "confirmUser");
		}*/
		//创建人
//		if(!this.existAlias(detachedCriteria, "createUser", "createUser"))
//		{
//			detachedCriteria.createAlias("createUser", "createUser");
//		}
		//修改人
/*		if(!this.existAlias(detachedCriteria, "modifyUser", "modifyUser"))
		{
			detachedCriteria.createAlias("modifyUser", "modifyUser");
		}*/
		pagerSqlByjqGrid(pager,detachedCriteria);	
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Rework rework=super.load(id);
			rework.setIsDel(oper);//标记删除
			super.update(rework);
		}
		
	}
}