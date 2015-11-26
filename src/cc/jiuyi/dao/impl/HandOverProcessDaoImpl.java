package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.HandOverProcessDao;
import cc.jiuyi.entity.HandOverProcess;

/**
 * Dao实现类 - HandOverProcess
 */

@Repository
public class HandOverProcessDaoImpl extends BaseDaoImpl<HandOverProcess, String> implements
		HandOverProcessDao {

	

	@SuppressWarnings("unchecked")
	public List<HandOverProcess> getHandOverProcessList() {
		String hql = "From HandOverProcess handOverProcess order by handOverProcess.id asc handOverProcess.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getHandOverProcessPager(Pager pager, HashMap<String, String> map) {
		String wheresql = handOverProcesspagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(HandOverProcess.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		// System.out.println(map.size());
		if (map.size() > 0) {
			if (map.get("state") != null) {
				detachedCriteria.add(Restrictions.like("state",
						"%" + map.get("state") + "%"));
			}
			if (map.get("start") != null || map.get("end") != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date start = sdf.parse(map.get("start"));
					Date end = sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate",
							start, end));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String handOverProcesspagerSql(Pager pager) {
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

	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			HandOverProcess handOverProcess = super.load(id);
			handOverProcess.setIsDel(oper);// 标记删除
			super.update(handOverProcess);
		}

	}
	
	public HandOverProcess findhandoverBypro(String materialCode,String processid,String matnr){
		String hql="from HandOverProcess a where a.material.materialCode = ? and a.process.id=? and a.beforworkingbill.matnr=?";
		return (HandOverProcess) getSession().createQuery(hql).setParameter(0, materialCode).setParameter(1, processid).setParameter(2, matnr).uniqueResult();
	}
	
	
	
}