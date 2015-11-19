package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AbnormalDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Factory;

/**
 * Dao实现类 - 异常
 */

@Repository
public class AbnormalDaoImpl  extends BaseDaoImpl<Abnormal, String> implements AbnormalDao {

	public Pager getAbnormalPager(Pager pager, HashMap<String, String> map) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Abnormal.class);
		pagerSqlByjqGrid(pager,detachedCriteria);		
		//detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
}
