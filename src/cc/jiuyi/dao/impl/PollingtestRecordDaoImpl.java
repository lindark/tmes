package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PollingtestRecordDao;
import cc.jiuyi.entity.PollingtestRecord;

/**
 * Dao接口 - 巡检从表
 */
@Repository
public class PollingtestRecordDaoImpl extends
		BaseDaoImpl<PollingtestRecord, String> implements PollingtestRecordDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map,
			String workingbillId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(PollingtestRecord.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("workingbill.id", workingbillId));
		return super.findByPager(pager, detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingtestRecord> findByPollingtestId(String id) {
		String hql = "from PollingtestRecord  where isDel='N' and pollingtest_id=?";
		return getSession().createQuery(hql).setParameter(0, id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PollingtestRecord getBySidAndCid(String sid, String cid) {
		String hql="from PollingtestRecord where pollingtest_id=? and causeId=?";
		List<PollingtestRecord> list=this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, cid).list();
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingtestRecord> getBySidAndMark(String sid, String istodel) {
		String hql="from PollingtestRecord where pollingtest_id=? and istoDel=?";
		return this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, istodel).list();
	}

}
