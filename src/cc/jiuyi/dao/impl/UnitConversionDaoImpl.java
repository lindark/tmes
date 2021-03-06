package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.UnitConversionDao;
import cc.jiuyi.entity.UnitConversion;

/**
 * Dao实现类 - unitConversion
 */

@Repository
public class UnitConversionDaoImpl extends BaseDaoImpl<UnitConversion, String>
		implements UnitConversionDao {

	@Override
	public void delete(String id) {
		UnitConversion unitConversion = load(id);
		this.delete(unitConversion);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UnitConversion> getUnitConversionList() {
		String hql = "From UnitConversion unitConversion order by unitConversion.id asc unitConversion.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getUnitConversionPager(Pager pager, HashMap<String, String> map) {
	//	String wheresql = unitConversionpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(UnitConversion.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		/*if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}*/
		// System.out.println(map.size());
		if (map.size() > 0) {
			if (map.get("unitCode") != null) {
				detachedCriteria.add(Restrictions.like("unitCode",
						"%" + map.get("unitCode") + "%"));
			}
			if (map.get("matnr") != null) {
				detachedCriteria.add(Restrictions.like("matnr", "%"
						+ map.get("matnr") + "%"));
			}
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like("state",
						"%" + map.get("state") + "%"));
			}
			/*if (map.get("start") != null || map.get("end") != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date start = sdf.parse(map.get("start"));
					Date end = sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate",
							start, end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	/*public String unitConversionpagerSql(Pager pager) {
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
								to.getOp(), null) + " ";
				ishead = 1;
			}

		}
		// System.out.println("wheresql:" + wheresql);
		return wheresql;
	}
*/
	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
//			UnitConversion unitConversion = super.load(id);
//			unitConversion.setIsDel(oper);// 标记删除
			super.delete(id);
//			super.update(unitConversion);
		}

	}

	@Override
	public Integer getSingleConversationRatio(String unitDescription,
			String convertUnit) {
		String hql = "select conversationRatio from UnitConversion where unitDescription=? and convertUnit=?";
		return (Integer) getSession().createQuery(hql).setParameter(0, unitDescription).setParameter(1, convertUnit).uniqueResult();
	}

	@Override
	public Integer getRatioByCode(String unitCode) {
		String hql = "select conversationRatio from UnitConversion where unitCode=?";
		return (Integer) getSession().createQuery(hql).setParameter(0, unitCode).uniqueResult();
	}

	@Override
	public UnitConversion getRatioByMatnr(String matnr,String unitCode) {
		String hql = "select u from UnitConversion u where u.matnr=? and u.unitCode=?";
		return (UnitConversion) getSession().createQuery(hql).setParameter(0, matnr).setParameter(1, unitCode).uniqueResult();
	}

	@Override
	public Object sumAmount(String matnr) {
		String sql = "SELECT ROUND(1/C.RD,2) RO FROM (SELECT (CASE WHEN TO_NUMBER(CONVERSATIONRATIO) >0 THEN CONVERSATIONRATIO ELSE 1 END) RD  FROM UNITCONVERSION WHERE MATNR='"+matnr+"') C";
		return (Object)getSession().createSQLQuery(sql).uniqueResult();
	}
}