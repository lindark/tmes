package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ItermediateTestDetailDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.ScrapMessage;

/**
 * Dao实现类 - ItermediateTestDetail
 */

@Repository
public class ItermediateTestDetailDaoImpl extends
		BaseDaoImpl<ItermediateTestDetail, String> implements
		ItermediateTestDetailDao {

	@Override
	public void delete(String id) {
		ItermediateTestDetail itermediateTestDetail = load(id);
		this.delete(itermediateTestDetail);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItermediateTestDetail> getItermediateTestDetailList() {
		String hql = "From ItermediateTestDetail itermediateTestDetail order by itermediateTestDetail.id asc itermediateTestDetail.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getItermediateTestDetailPager(Pager pager,
			HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ItermediateTestDetail.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			ItermediateTestDetail itermediateTestDetail = super.load(id);
			itermediateTestDetail.setIsDel(oper);// 标记删除
			super.update(itermediateTestDetail);
		}

	}

	@Override
	public List<ItermediateTestDetail> getItermediateTestDetail(String id) {
		String hql = "from ItermediateTestDetail a inner join fetch a.itermediateTest b where b.id=?";
		return getSession().createQuery(hql).setParameter(0, id).list();
	}

	@Override
	/**
	 * 根据主表id和物料表id查询
	 */
	public ItermediateTestDetail getBySidAndMid(String sid, String mid) {
		String hql = "from ItermediateTestDetail where isDel='N' and itermediateTest.id=? and materialCode=?";
		return (ItermediateTestDetail) this.getSession().createQuery(hql)
				.setParameter(0, sid).setParameter(1, mid).uniqueResult();
	}

	@Override
	public Pager historyjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ItermediateTestDetail.class);
		try {
			if (!existAlias(detachedCriteria, "itermediateTest",
					"itermediateTest")) {
				detachedCriteria.createAlias("itermediateTest",
						"itermediateTest");
			}
			
				
			// detachedCriteria.add(Restrictions.eq("workingbill.id",
			// "4028c781532c74d701532ca1986e0014"));//测试
			if (map.size() > 0) {
				if (map.get("materialName") != null) {
					detachedCriteria.add(Restrictions.like("materialName",
							"%" + map.get("materialName") + "%"));
				}
				if (map.get("materialCode") != null) {
					detachedCriteria.add(Restrictions.like("materialCode", "%"
							+ map.get("materialCode") + "%"));
				}
				if (map.get("state") != null) {
					detachedCriteria.add(Restrictions.like(
							"itermediateTest.state", "%" + map.get("state")
									+ "%"));
				}
				if (map.get("start") != null && map.get("end") != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date start = sdf.parse(map.get("start"));
						Date end = sdf.parse(map.get("end"));
						end = DateUtils.addDays(end, 1);
						detachedCriteria.add(Restrictions.between("createDate",
								start, end));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (map.get("xconfirmUser") != null) {
					if (!existAlias(detachedCriteria, "itermediateTest.confirmUser",
							"confirmUser")) {
						detachedCriteria.createAlias("itermediateTest.confirmUser",
								"confirmUser");
					}
					detachedCriteria.add(Restrictions.like("confirmUser.name",
							"%" + map.get("xconfirmUser") + "%"));
				}
				if (map.get("xcreateUser") != null) {
					if (!existAlias(detachedCriteria, "itermediateTest.createUser",
							"createUser")) {
						detachedCriteria.createAlias("itermediateTest.createUser",
								"createUser");
					}
					detachedCriteria.add(Restrictions.like("createUser.name",
							"%" + map.get("xcreateUser") + "%"));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	/**
	 * excel导出
	 */
	public List<Object[]> historyExcelExport(HashMap<String, String> map) {
		String hql = "from ItermediateTestDetail model join model.itermediateTest model1 left join model1.confirmUser model2 left join model1.createUser model3";
		//
		Integer ishead = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!map.get("materialName").equals("")) {
			if (ishead == 0) {
				hql += " where model.materialName like '%" + map.get("materialName") + "%'";
				ishead = 1;
			} else {
				hql += " and model.materialName like '%" + map.get("materialName") + "%'";
			}
		}
		if (!map.get("materialCode").equals("")) {
			if (ishead == 0) {
				hql += " where model.materialCode like '%"
						+ map.get("materialCode") + "%'";
				ishead = 1;
			} else {
				hql += " and model.materialCode like '%"
						+ map.get("materialCode") + "%'";
			}
		}
		if (!map.get("xcreateUser").equals("")) {
			if (ishead == 0) {
				hql += " where model2.name like '%" + map.get("xconfirmUser") + "%'";
				ishead = 1;
			} else {
				hql += " and model2.name like '%" + map.get("xconfirmUser") + "%'";
			}
		}
		if (!map.get("xcreateUser").equals("")) {
			if (ishead == 0) {
				hql += " where model3.name like '%"
						+ map.get("xcreateUser") + "%'";
				ishead = 1;
			} else {
				hql += " and model3.name like '%"
						+ map.get("xcreateUser") + "%'";
			}
		}
		if (!map.get("state").equals("")) {
			if (ishead == 0) {
				hql += " where model1.state like '%" + map.get("state") + "%'";
				ishead = 1;
			} else {
				hql += " and model1.state like '%" + map.get("state") + "%'";
			}
		}
		if (!map.get("start").equals("") && !map.get("end").equals("")) {
 			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {

				Date start = sdf.parse(map.get("start")+" 00:00:00");
				Date end = sdf.parse(map.get("end")+" 23:59:59");
				//System.out.println(map.get("start"));
				if (ishead == 0) {
					hql += " where model.createDate between :start and :end";
					ishead = 1;
				} else {
					hql += " and model.createDate between :start and :end";
				}
				parameters.put("start", start);
				parameters.put("end", end);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Query query = getSession().createQuery(hql);

		if (parameters.get("start") != null) {
			query.setParameter("start", parameters.get("start"));
		}
		if (parameters.get("end") != null) {
			query.setParameter("end", parameters.get("end"));
		}

		return query.list();
	}

}