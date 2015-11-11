package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Product;
import cc.jiuyi.entity.Message.DeleteStatus;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 随工单
 */

@Repository
public class WorkingBillDaoImpl extends BaseDaoImpl<WorkingBill, String> implements WorkingBillDao {
	
	
	public Pager findPagerByjqGrid(Pager pager,Map map){
		String wheresql = super.pagerSqlByjqGrid(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(WorkingBill.class);
		if(!wheresql.equals("")){
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map != null && map.size()>0){
			if(!map.get("start").equals("") || !map.get("end").equals("")){//生产日期范围
				String start = map.get("start").equals("")? ThinkWayUtil.SystemDate() : map.get("start").toString();
				String end = map.get("end").equals("")? ThinkWayUtil.SystemDate() : map.get("end").toString();
				detachedCriteria.add(Restrictions.between("productDate", start, end));
			}
			if(!map.get("workingBillCode").equals("")){
				detachedCriteria.add(Restrictions.like("workingBillCode","%"+map.get("workingBillCode").toString()+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isdel", "N"));//取出未删除标记数据
		return super.findByPager(pager,detachedCriteria);
	}

	@Override
	public void updateWorkingBill(WorkingBill workingbill) {
		String hql="update WorkingBill set productDate = ? ,planCount = ?,matnr=?,maktx=? where workingBillCode = ?";
		getSession().createQuery(hql).setParameter(0, workingbill.getProductDate()).setParameter(1, workingbill.getPlanCount()).setParameter(2, workingbill.getMatnr()).setParameter(3, workingbill.getMaktx()).setParameter(4, workingbill.getWorkingBillCode()).executeUpdate();
	}

	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			WorkingBill workingbill = super.load(id);
			workingbill.setIsdel(oper);//标记删除
			super.update(workingbill);
		}
		
		
	}

	@Override
	public List getListWorkingBillByDate(Date productdate) {
		String hql = "from WorkingBill where productDate = ?";
		List list = getSession().createQuery(hql).setParameter(0, productdate).list();
		return list;
	}
	
	
}