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
import cc.jiuyi.dao.CraftDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.WorkShop;

/**
 * Dao实现类 - 工模
 */

@Repository
public class CraftDaoImpl extends BaseDaoImpl<Craft, String> implements CraftDao {

	public Pager getCraftPager(Pager pager, HashMap<String, String> map) {
		String wheresql = craftpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Craft.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if (map.size() > 0) {
			/*if(map.get("state")!=null){
			    detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}*/

			if(map.get("classes")!=null){
			    detachedCriteria.add(Restrictions.like("classes", "%"+map.get("classes")+"%"));
			}
			/*
			if(map.get("workShopName")!=null){
				detachedCriteria.add(Restrictions.like("workShopName", "%"+map.get("workShopName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}*/
		}
		System.out.println("k");
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String craftpagerSql(Pager pager) {
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

}
