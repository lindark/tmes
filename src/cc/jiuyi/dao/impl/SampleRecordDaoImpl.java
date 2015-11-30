package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.SampleRecordDao;
import cc.jiuyi.entity.SampleRecord;
/**
 * 抽检单
 * @author gaoyf
 *
 */
@Repository
public class SampleRecordDaoImpl extends BaseDaoImpl<SampleRecord, String> implements SampleRecordDao
{

	/**
	 * 根据抽检id获取缺陷记录
	 */
	@SuppressWarnings("unchecked")
	public List<SampleRecord> getBySampleId(String id)
	{
		String hql="from SampleRecord a where a.isDel='N' and a.sampleId=?";
		return this.getSession().createQuery(hql).setParameter(0, id).list();
	}

	/**
	 * 根据抽检单id和缺陷表id查询缺陷记录表是否存在，存在更新，不存在新增
	 */
	@SuppressWarnings("unchecked")
	public SampleRecord getBySidAndCid(String sid, String cid)
	{
		String hql="from SampleRecord a where a.sampleId=? and a.causeId=?";
		List<SampleRecord> list=this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, cid).list();
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据抽检单id和标记查询该缺陷记录
	 */
	@SuppressWarnings("unchecked")
	public List<SampleRecord> getBySidAndMark(String sid, String istodel)
	{
		String hql="from SampleRecord a where a.sampleId=? and a.istoDel=?";
		return this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, istodel).list();
	}
}
