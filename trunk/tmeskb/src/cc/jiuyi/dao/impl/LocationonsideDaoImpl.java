package cc.jiuyi.dao.impl;

import java.util.HashMap;
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
	public Pager getLocationPager(Pager pager,HashMap<String,String> map) {
		String wheresql = dumppagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Locationonside.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map.size()>0){
			if(map.get("locationCode")!=null){
				detachedCriteria.add(Restrictions.like("locationCode", "%"+map.get("locationCode")+"%"));								
			}
			if(map.get("locationName")!=null){
				detachedCriteria.add(Restrictions.like("locationName", "%"+map.get("locationName")+"%"));								
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);

	}

	public String dumppagerSql(Pager pager) {
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
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			Locationonside locationonside = super.load(id);
			locationonside.setIsDel(oper);//标记删除
			super.update(locationonside);
		}
		
	}
}
