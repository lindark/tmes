package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverTop;
/**
 * Dao实现类 - 工序交接
 */
@Repository
public class ProcessHandoverDaoImpl extends BaseDaoImpl<ProcessHandover, String> implements
		ProcessHandoverDao {
		// 关联处理
		@Override
		public void delete(ProcessHandover processHandover) {
			super.delete(processHandover);
		}

		// 关联处理
		@Override
		public void delete(String id) {
			ProcessHandover processHandover = load(id);
			this.delete(processHandover);
		}

		// 关联处理
		@Override
		public void delete(String[] ids) {
			for (String id : ids) {
				ProcessHandover processHandover = load(id);
				this.delete(processHandover);
			}
		}
	@Override
	public Pager jqGrid(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProcessHandoverTop.class);
		detachedCriteria.add(Restrictions.eq("isdel", "N"));
		pagerSqlByjqGrid(pager,detachedCriteria);
		return super.findByPager(pager, detachedCriteria);
		
	}

	@Override
	public Pager jqGrid(Pager pager,Admin admin) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProcessHandoverTop.class);
		//detachedCriteria.add(Restrictions.eq("isdel", "N"));
		detachedCriteria.add(Restrictions.eq("productDate", admin.getProductDate()));
		detachedCriteria.add(Restrictions.eq("shift", admin.getShift()));
		if(admin.getTeam()!=null && admin.getTeam().getFactoryUnit() !=null){
			detachedCriteria.add(Restrictions.eq("factoryUnitCode", admin.getTeam().getFactoryUnit().getFactoryUnitCode()));
		}
		pagerSqlByjqGrid(pager,detachedCriteria);
		return super.findByPager(pager, detachedCriteria);
		
	}
//	@Override
//	public List<ProcessHandover> getProcessHandoverList(String[] propertyNames,
//			String[] propertyValues) {
//		List<ProcessHandover> processHandoverList = new ArrayList<ProcessHandover>();
//		String hql = "From ProcessHandover where ProcessHandover.processHandoverTop."+propertyNames[0]+"=? and"+propertyNames[1]+"=? and"+propertyNames[2]+"=?";
//		return getSession().createQuery(hql).setParameter(0, propertyValues[0]).setParameter(1, propertyValues[1]).setParameter(2, propertyValues[2]).list();
//	}

	@Override
	public Object[] sumAmount(String workingBillCode,String type) {
		String sql = "";
		/*if("S".equals(type)){
			sql="SELECT ROUND(SUM(PH.ACTUALHOMOUNT),3) ,ROUND(SUM(PH.UNHOMOUNT),3)  FROM PROCESSHANDOVER PH, PROCESSHANDOVERTOP PHT WHERE PH.AFTERWORKINGBILLCODE='"+workingBillCode+"' AND PH.PROCESSHANDOVERTOP_ID=PHT.ID AND PH.ISDEL='N' AND PHT.TYPE='零头数交接' AND PHT.STATE='2'";
		}else{
			sql="SELECT ROUND(SUM(PH.ACTUALHOMOUNT),3) ,ROUND(SUM(PH.UNHOMOUNT),3)  FROM PROCESSHANDOVER PH, PROCESSHANDOVERTOP PHT WHERE PH.WORKINGBILLCODE='"+workingBillCode+"' AND PH.PROCESSHANDOVERTOP_ID=PHT.ID AND PH.ISDEL='N' AND PHT.TYPE='零头数交接' AND PHT.STATE='2'";
		}*/
		sql="SELECT * FROM "
				+ "(SELECT ROUND(SUM(PH.ACTUALHOMOUNT), 3), ROUND(SUM(PH.UNHOMOUNT), 3) FROM PROCESSHANDOVER PH,PROCESSHANDOVERTOP PHT WHERE PH.AFTERWORKINGBILLCODE = '"+workingBillCode+"' AND PH.PROCESSHANDOVERTOP_ID = PHT. ID AND PH.ISDEL = 'N' AND PHT. TYPE = '零头数交接' AND PHT.STATE = '2'),"
				+ "(SELECT ROUND(SUM(PH.ACTUALHOMOUNT), 3), ROUND(SUM(PH.UNHOMOUNT), 3) FROM PROCESSHANDOVER PH,PROCESSHANDOVERTOP PHT WHERE PH.AFTERWORKINGBILLCODE = '"+workingBillCode+"' AND PH.PROCESSHANDOVERTOP_ID = PHT. ID AND PH.ISDEL = 'N' AND PHT. TYPE = '零头数交接' AND PHT.STATE = '2')";
		return (Object[])getSession().createSQLQuery(sql).uniqueResult();
	}

	@Override
	public Object[] sumSonAmount(String processid, String wbid, String matnr,String wicode,String type) {
		String sql = "";
		if("".equals(type)){
			sql="SELECT ROUND(SUM(PHS.BOMAMOUNT),3),ROUND(SUM(PHS.REPAIRAMOUNT),3) FROM PROCESSHANDOVERSON PHS,(SELECT PH.ID FROM PROCESSHANDOVER PH,PROCESSHANDOVERTOP PHT WHERE "
					+ "PH.PROCESSID='"+processid+"' AND PH.AFTERWORKINGBILL_ID='"+wbid+"' AND PH.MATNR='"+matnr+"' "
					+ "AND PH.ISDEL='N' AND PH.PROCESSHANDOVERTOP_ID=PHT.ID AND PHT.STATE='2') PP WHERE PHS.PROCESSHANDOVER_ID=PP.ID AND PHS.BOMCODE='"+wicode+"'";
		}else{
			sql="SELECT ROUND(SUM(PHS.BOMAMOUNT),3),ROUND(SUM(PHS.REPAIRAMOUNT),3) FROM PROCESSHANDOVERSON PHS,(SELECT PH.ID FROM PROCESSHANDOVER PH,PROCESSHANDOVERTOP PHT WHERE "
					+ "PH.PROCESSID='"+processid+"' AND PH.WORKINGBILL_ID='"+wbid+"' AND PH.MATNR='"+matnr+"' "
					+ "AND PH.ISDEL='N' AND PH.PROCESSHANDOVERTOP_ID=PHT.ID AND PHT.STATE='2') PP WHERE PHS.PROCESSHANDOVER_ID=PP.ID AND PHS.BOMCODE='"+wicode+"'";
		}
			
		return (Object[])getSession().createSQLQuery(sql).uniqueResult();
	}
	
	

}
