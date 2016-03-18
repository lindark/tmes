package cc.jiuyi.dao.impl;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UpDownDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.UpDown;

/**
 * Dao实现类 - 上架/下架
 */

@Repository
public class UpDownDaoImpl extends BaseDaoImpl<UpDown, String> implements
		UpDownDao {
	
	
	public Pager findByPager(Pager pager,Admin admin,List<String> list) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UpDown.class);
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		detachedCriteria.add(Restrictions.in("type", list));
		return super.findByPager(pager, detachedCriteria);
	}
	
}