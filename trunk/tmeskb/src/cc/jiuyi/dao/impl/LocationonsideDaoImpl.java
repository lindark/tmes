package cc.jiuyi.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.LocationonsideDao;
import cc.jiuyi.entity.Locationonside;

/**
 * Dao接口 - 管理员 线边仓
 */
@Repository
public class LocationonsideDaoImpl extends BaseDaoImpl<Locationonside, String>
		implements LocationonsideDao {
	public Pager getLocationPager(Pager pager) {
		String wheresql = locationpagerSql(pager);
		if (!wheresql.equals("")) {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Locationonside.class);
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
			return super.findByPager(pager, detachedCriteria);
		} else {
			return super.findByPager(pager);
		}

	}

	public String locationpagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true) {
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
		System.out.println("wheresql:" + wheresql);
		return wheresql;
	}
}
