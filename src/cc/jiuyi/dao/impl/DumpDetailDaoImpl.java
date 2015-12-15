package cc.jiuyi.dao.impl;

import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDetailDao;
import cc.jiuyi.entity.DumpDetail;

@Repository
public class DumpDetailDaoImpl extends BaseDaoImpl<DumpDetail, String> implements DumpDetailDao {
	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,String dumpId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DumpDetail.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("dump.id", dumpId));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
}
