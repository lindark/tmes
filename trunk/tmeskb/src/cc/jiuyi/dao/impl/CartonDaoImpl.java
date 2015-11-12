package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.CartonDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Locationonside;

/**
 * Dao接口 - 纸箱
 */
@Repository
public class CartonDaoImpl extends BaseDaoImpl<Carton, String> implements
		CartonDao {

	@Override
	public Pager getCartonPager(Pager pager, HashMap<String, String> map) {
		String wheresql = cartonpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Carton.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String cartonpagerSql(Pager pager) {
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
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Carton carton = super.load(id);
			carton.setIsDel(oper);// 标记删除
			super.update(carton);
		}

	}

}
