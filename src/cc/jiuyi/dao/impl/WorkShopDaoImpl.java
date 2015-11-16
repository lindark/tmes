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

	public Pager getWorkShopPager(Pager pager, HashMap<String, String> map) {
		String wheresql = workShoppagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(WorkShop.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
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
			/*
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}*/
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String workShoppagerSql(Pager pager) {
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
		wheresql=wheresql.replace("state='启用'", "state='1'");
		wheresql=wheresql.replace("state='未启用'", "state='2'");
		return wheresql;
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
}