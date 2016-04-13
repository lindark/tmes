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
import cc.jiuyi.entity.Admin;
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
public class WorkingBillDaoImpl extends BaseDaoImpl<WorkingBill, String>
		implements WorkingBillDao {

	public Pager findPagerByjqGrid(Pager pager, Map map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(WorkingBill.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map != null && map.size() > 0) {
			if (!map.get("start").equals("") || !map.get("end").equals("")) {// 生产日期范围
				String start = map.get("start").equals("") ? ThinkWayUtil
						.SystemDate() : map.get("start").toString();
				String end = map.get("end").equals("") ? ThinkWayUtil
						.SystemDate() : map.get("end").toString();
				detachedCriteria.add(Restrictions.between("productDate", start,
						end));
			}
			if (!map.get("workingBillCode").equals("")) {
				detachedCriteria.add(Restrictions.like("workingBillCode", "%"
						+ map.get("workingBillCode").toString() + "%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isdel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateWorkingBill(WorkingBill workingbill) {
		String hql = "update WorkingBill set productDate = ? ,planCount = ?,matnr=?,maktx=?,werks=?,workcenter=?,isdel='N' where workingBillCode = ?";
		getSession().createQuery(hql)
				.setParameter(0, workingbill.getProductDate())
				.setParameter(1, workingbill.getPlanCount())
				.setParameter(2, workingbill.getMatnr())
				.setParameter(3, workingbill.getMaktx())
				.setParameter(4, workingbill.getWerks())
				.setParameter(5, workingbill.getWorkcenter())
				.setParameter(6, workingbill.getWorkingBillCode())
				.executeUpdate();
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			WorkingBill workingbill = super.load(id);
			workingbill.setIsdel(oper);// 标记删除
			super.update(workingbill);
		}

	}

	
	@SuppressWarnings("unchecked")
	public List<WorkingBill> getListWorkingBillByDate(String productDate,String shift,String workcenter,String matnr) {
		String hql = "from WorkingBill where productDate = ? and workingBillCode like ? and workcenter = ? and matnr = ?";
		List<WorkingBill> list = getSession().createQuery(hql)
				.setParameter(0, productDate)
				.setParameter(1, "%" + shift)
				.setParameter(2, workcenter)
				.setParameter(3, matnr).list();
		return list;
	}

	/**
	 * 查询随工单表中的id 和 产品名称maktx
	 */
	@SuppressWarnings("unchecked")
	public List<WorkingBill> getIdsAndNames() {
		String hql = " from WorkingBill";
		return this.getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<WorkingBill> findListWorkingBill(Object[] productsid,
			String productDate, String shift) {
		String hql = "from WorkingBill where productDate = ? and workingBillCode like ? and matnr in (:list)";
		return getSession().createQuery(hql)
				.setParameter(0, productDate).setParameter(1, "%" + shift).setParameterList("list", productsid)
				.list();
	}
	
	
	public WorkingBill getCodeNext(String workingbillCode,List<String> aufnrList,String formtdate) {
		String hql="from WorkingBill where workingBillCode > ? and aufnr in (:list) order by workingbillCode asc";
		return (WorkingBill) getSession().createQuery(hql).setParameter(0, workingbillCode).setParameterList("list", aufnrList).setMaxResults(1).uniqueResult();
	}
	public WorkingBill getCodeNext(String workingbillCode,String matnr){
		String hql="from WorkingBill where workingBillCode > ? and matnr = ? order by workingBillCode asc";
		//return getSession().createQuery(hql).setParameter(0, workingbillCode).setParameter(1, matnr).setMaxResults(1).uniqueResult();
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingBill> getWorkingBillByProductsCode(String matnr) {
		String hql="from WorkingBill where matnr = ? ";
		return getSession().createQuery(hql).setParameter(0, matnr).list();
	}

	@SuppressWarnings("unchecked")
	public List<WorkingBill> findWorkingBill(String workcenter,String productDate,String shift) {
		String hql="from WorkingBill where productDate = ? and workingBillCode like ? and workcenter = ? and isdel = 'N' order by maktx asc";
		return getSession().createQuery(hql).setParameter(0, productDate).setParameter(1, "%"+shift).setParameter(2, workcenter).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WorkingBill> findListWorkingBill(String productDate, String shift) {
		if(productDate!=null && !"".equals(productDate)){
			if(shift==null || "".equals(shift)){
				String hql = "from WorkingBill where productDate = ? ";
				return getSession().createQuery(hql)
						.setParameter(0, productDate).list();
			}else{
				String hql = "from WorkingBill where productDate = ? and workingBillCode like ? ";
				return getSession().createQuery(hql)
						.setParameter(0, productDate).setParameter(1, "%" + shift).list();
			}
		}else{
			return null;
		}
	
	}

	@Override
	public List<WorkingBill> getWorkingBillList(String workingBillId) {
		String hql = "From WorkingBill workingBill where workingBill.id = ?";
		return getSession().createQuery(hql).setParameter(0, workingBillId).list();
	}
	

}