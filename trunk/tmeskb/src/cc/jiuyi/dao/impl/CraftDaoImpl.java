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

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Craft.class);
		pagerSqlByjqGrid(pager,detachedCriteria);

		if (map.size() > 0) {			
			
			if(map.get("team")!=null){
			    detachedCriteria.add(Restrictions.like("classes", "%"+map.get("team")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("productName")+"%"));
			}			
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

}
