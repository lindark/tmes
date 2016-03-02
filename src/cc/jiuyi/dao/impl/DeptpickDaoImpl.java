package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DeptpickDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;

/**
 * Dao实现类 - 部门领料
 */
@Repository
public class DeptpickDaoImpl extends BaseDaoImpl<Deptpick, String> implements
		DeptpickDao {

	
	public Pager findByPager(Pager pager, Admin admin) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Deptpick.class);
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		return findByPager(pager, detachedCriteria);
	}
}
