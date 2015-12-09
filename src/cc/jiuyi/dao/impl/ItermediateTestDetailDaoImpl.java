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
import cc.jiuyi.dao.ItermediateTestDetailDao;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.ScrapMessage;

/**
 * Dao实现类 - ItermediateTestDetail
 */

@Repository
public class ItermediateTestDetailDaoImpl extends BaseDaoImpl<ItermediateTestDetail, String> implements
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

	public Pager getItermediateTestDetailPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ItermediateTestDetail.class);
		pagerSqlByjqGrid(pager,detachedCriteria);	
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			ItermediateTestDetail itermediateTestDetail=super.load(id);
			itermediateTestDetail.setIsDel(oper);//标记删除
			super.update(itermediateTestDetail);
		}
		
	}

	@Override
	public List<ItermediateTestDetail> getItermediateTestDetail(String id) {
		String hql="from ItermediateTestDetail a inner join fetch a.itermediateTest b where b.id=?";
		return getSession().createQuery(hql).setParameter("list", id).list();
	}

	@Override
	/**
	 * 根据主表id和物料表id查询
	 */
	public ItermediateTestDetail getBySidAndMid(String sid, String mid) {
		String hql="from ItermediateTestDetail where isDel='N' and itermediateTest_id=? and materialId=?";
		return (ItermediateTestDetail)this.getSession().createQuery(hql).setParameter(0, sid).setParameter(1, mid).uniqueResult();
	}

	
	

}