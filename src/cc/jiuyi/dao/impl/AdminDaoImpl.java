package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 管理员
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map,List list){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "department", "department"))
			detachedCriteria.createAlias("department", "department");
		detachedCriteria.add(Restrictions.in("department.id", list));//取出未子部门
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	public Pager getAdminPager(Pager pager,Map map,String adminDeptName){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(!super.existAlias(detachedCriteria, "department", "department"))
			detachedCriteria.createAlias("department", "department");
		if (map.size() > 0) {
			if(map.get("adminName")!=null){
			    detachedCriteria.add(Restrictions.like("name", "%"+map.get("adminName")+"%"));
			}		
			if(map.get("adminDeptName")!=null){
				detachedCriteria.add(Restrictions.like("department.deptName", "%"+map.get("adminDeptName")+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
}