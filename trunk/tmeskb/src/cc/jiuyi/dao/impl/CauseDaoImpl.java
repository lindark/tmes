package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.CauseDao;
import cc.jiuyi.entity.Cause;

/**
 * Dao接口 - 呼叫原因
 */
@Repository
public class CauseDaoImpl extends BaseDaoImpl<Cause, String> implements
		CauseDao {
	public Pager getCausePager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Cause.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if (map.get("causeCode") != null) {
				detachedCriteria.add(Restrictions.like("causeCode",
						"%" + map.get("causeCode") + "%"));
			}
			if (map.get("causeType") != null) {
				detachedCriteria.add(Restrictions.eq("causeType",
						map.get("causeType")));
			}
			if (map.get("causeName") != null) {
				detachedCriteria.add(Restrictions.like("causeName",
						"%" + map.get("causeName") + "%"));
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.eq("state", map.get("state")));
			}
			
		}
		
		
		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);

	}

//	public String callreasonpagerSql(Pager pager) {
//		String wheresql = "";
//		Integer ishead = 0;
//		if (pager.is_search() == true && pager.getRules() != null) {
//			List list = pager.getRules();
//			for (int i = 0; i < list.size(); i++) {
//				if (ishead == 1) {
//					wheresql += " " + pager.getGroupOp() + " ";
//				}
//				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
//				wheresql += " "
//						+ super.generateSearchSql(to.getField(), to.getData(),
//								to.getOp(), null) + " ";
//				ishead = 1;
//			}
//
//		}
//		wheresql=wheresql.replace("state='启用'", "state='1'");
//		wheresql=wheresql.replace("state='未启用'", "state='2'");
//		return wheresql;
//	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Cause cause = super.load(id);
			cause.setIsDel(oper);// 标记删除
			super.update(cause);
		}

	}

	/**
	 * 获取缺陷表中关于抽检的内容
	 */
	@SuppressWarnings("unchecked")
	public List<Cause> getBySample(String type)
	{
		String hql="from Cause where isDel='N' and causeType=? order by causeCode";
		return this.getSession().createQuery(hql).setParameter(0, type).list();
	}
}
