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
import cc.jiuyi.dao.ModelDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Model;

/**
 * Dao实现类 - 工模
 */

@Repository
public class ModelDaoImpl extends BaseDaoImpl<Model, String> implements ModelDao {

	public Pager getModelPager(Pager pager, HashMap<String, String> map) {
		//String wheresql = modelpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Model.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		/*if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}*/
		if (map.size() > 0) {
			/*if(map.get("state")!=null){
			    detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}*/

			if(map.get("teamId")!=null){
			    detachedCriteria.add(Restrictions.like("teamId", "%"+map.get("teamId")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("productName", "%"+map.get("productName")+"%"));
			}
			
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
  /*
	public String modelpagerSql(Pager pager) {
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
		return wheresql;
	}*/
	
}
