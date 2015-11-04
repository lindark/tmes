package cc.jiuyi.dao.impl;

import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

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
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Admin.class);
		if(map != null && map.size()>0){
			if(!map.get("workingBillCode").equals("")){
				//detachedCriteria.add(Restrictions.like("workingBillCode","%"+map.get("workingBillCode").toString()+"%"));
			}
		}
		//detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}
	
	
}