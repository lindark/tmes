package cc.jiuyi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.RepairDao;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Repair;

/**
 * Dao接口 - 返修
 */
@Repository
public class RepairDaoImpl extends BaseDaoImpl<Repair, String> implements
		RepairDao {
	public Pager getRepairPager(Pager pager, HashMap<String, String> map) {
		String wheresql = repairpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Repair.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if (map.size() > 0) {
			if (map.get("repairPart") != null) {
				detachedCriteria.add(Restrictions.like("repairPart",
						"%" + map.get("repairPart") + "%"));
			}
			if (map.get("processResponse") != null) {
				detachedCriteria.add(Restrictions.like("processResponse", "%"
						+ map.get("processResponse") + "%"));
			}
			if (map.get("duty") != null) {
				detachedCriteria.add(Restrictions.like("duty",
						"%" + map.get("duty") + "%"));
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like("state",
						"%" + map.get("state") + "%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);

	}

	public String repairpagerSql(Pager pager) {
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
		System.out.println("wheresql:" + wheresql);
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Repair repair = super.load(id);
			repair.setIsDel(oper);// 标记删除
			super.update(repair);
		}

	}
}
