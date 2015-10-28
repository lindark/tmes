package cc.jiuyi.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口 - 管理员 转储管理
 */
@Repository
public class DumpDaoImpl extends BaseDaoImpl<Dump, String> implements DumpDao {
	public Pager getDumpPager(Pager pager) {
		String wheresql = dumppagerSql(pager);
		if (!wheresql.equals("")) {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Dump.class);
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
			return super.findByPager(pager, detachedCriteria);
		} else {
			return super.findByPager(pager);
		}

	}

	public String dumppagerSql(Pager pager) {
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
