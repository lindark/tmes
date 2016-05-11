package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;






import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.WorkingInoutDao;
import cc.jiuyi.entity.WorkingInout;

/**
 * Dao实现类 - 投入产出表
 */

@Repository
public class WorkingInoutDaoImpl extends BaseDaoImpl<WorkingInout, String> implements
		WorkingInoutDao {

	@Override
	public boolean isExist(String workingBillId, String materialCode) {
		String hql = "from WorkingInout a where a.workingbill.id=? and materialCode=?";
		WorkingInout workingInout = (WorkingInout) getSession()
				.createQuery(hql).setParameter(0, workingBillId)
				.setParameter(1, materialCode).uniqueResult();
		if (workingInout == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public WorkingInout findWorkingInout(String workingBillId,
			String materialCode) {
		String hql = "from WorkingInout a where workingbill.id=? and materialCode=?";
		return (WorkingInout) getSession().createQuery(hql)
				.setParameter(0, workingBillId).setParameter(1, materialCode)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<WorkingInout> findPagerByWorkingBillInout(
			HashMap<String, String> map) {
		if (!map.get("workingBillCode").equals("")) {
			String hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and workingbill.aufnr=?";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).list();
		}else{
			String hql = "from WorkingInout a where workingbill.productDate between ? and ? ";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).list();
		}
		
		
	}

	/**
	 * 获取数据
	 * 首页中点击其中一个随工单后显示其投入产出数据
	 */
	@SuppressWarnings("unchecked")
	public List<WorkingInout> findWbinoutput(String wbid)
	{
		String hql = "from WorkingInout where workingbill.id=?";
		return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0,wbid).list();
	}
	
	public static boolean isMath(String value) {
	  try {
	   Integer.parseInt(value);
	   return true;
	  } catch (NumberFormatException e) {
	   return false;
	  }
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkingInout> newFindPagerByWorkingBillInout(HashMap<String, String> map) {
		int flag = 0;
		boolean flag1;
		flag1 = isMath(map.get("xFactoryUnit"));
		if(!map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 1;
		}
		if(!map.get("workingBillCode").equals("") && map.get("xFactoryUnit").equals("")){
			flag = 2;
		}
		if(map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 3;
		}
		String hql;
		switch(flag){
		case 1:
			if(flag1){
				hql = "from WorkingInout a where (( workingbill.productDate between ? and ? ) and workingbill.aufnr=? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnitCode=?)) order by workingbill.productDate,workingbill.workcenter ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).setParameter(3, map.get("xFactoryUnit")).list();
				}else{
				hql = "from WorkingInout a where (( workingbill.productDate between ? and ? ) and workingbill.aufnr=? ) and (workingbill.workcenter in (select workcenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%')) order by workingbill.productDate,workingbill.workcenter";	
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).setParameter(3, map.get("xFactoryUnit")).list();
			}
		case 2:
			hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and workingbill.aufnr=? order by workingbill.productDate,workingbill.workcenter";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("workingBillCode")).list();
		case 3:
			if(flag1){
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select  workCenter from FactoryUnit where FactoryUnitCode=?)) order by workingbill.productDate,workingbill.workcenter ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}else{
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%')) order by workingbill.productDate,workingbill.workcenter";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}
		default:
			hql = hql = "from WorkingInout a where workingbill.productDate between ? and ? order by workingbill.productDate,workingbill.workcenter ";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).list();
		}
	}
	
	@Override
	public Pager listjqGrid(Pager pager, HashMap<String, String> map) {
		if(pager==null){
			pager = new Pager();
		}
		String[] orderbyArr = {"workingbill.workingBillCode","workingbill.productDate","workingbill.aufnr","workingbill.matnr","workingbill.maktx","workingbill.workcenter"};
		if(pager.getOrderBy()==null || !Arrays.asList(orderbyArr).contains(pager.getOrderBy())){
			pager.setOrderBy("modifyDate");
		}
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(WorkingInout.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		
		
		int flag = 0;
		boolean flag1;
		flag1 = isMath(map.get("xFactoryUnit"));
		if(!map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 1;
		}
		if(!map.get("workingBillCode").equals("") && map.get("xFactoryUnit").equals("")){
			flag = 2;
		}
		if(map.get("workingBillCode").equals("") && !map.get("xFactoryUnit").equals("")){
			flag = 3;
		}
		
		/*			
			if(flag1){
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select  workCenter from FactoryUnit where FactoryUnitCode=?)) ";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}else{
				hql = "from WorkingInout a where ( workingbill.productDate between ? and ? ) and (workingbill.workcenter in (select workCenter from FactoryUnit where FactoryUnit.factoryUnitName like '%?%'))";
				return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).setParameter(2, map.get("xFactoryUnit")).list();
			}
		*/
		
		
		if (!existAlias(detachedCriteria, "workingbill", "workingbill")) {
			detachedCriteria.createAlias("workingbill", "workingbill");
		}
		if (map.get("workingBillCode") != null && !"".equals(map.get("workingBillCode"))) {
			detachedCriteria.add(Restrictions.eq("workingbill.aufnr",map.get("workingBillCode")));
		}
		
		
		if (map.get("xFactoryUnit") != null && !"".equals(map.get("xFactoryUnit"))) 
		{
				
			detachedCriteria.add(Restrictions.in("workingbill.workcenter",getWorkcenterList(map.get("xFactoryUnit"), flag1)));
			
		}
		
		if (map.get("start") != null || map.get("end") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				//Date start = sdf.parse(map.get("start"));
				//Date end = sdf.parse(map.get("end"));
				//end = DateUtils.addDays(end, 1);
				detachedCriteria.add(Restrictions.between("workingbill.productDate",
						map.get("start"), map.get("end")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
			
		return super.findByPager(pager, detachedCriteria);
	}

	public List getWorkcenterList(String xFactoryUnit,boolean flag)
	{
		String hql="";
		if(flag)
		{
			hql="select  fu.workCenter from FactoryUnit fu where fu.factoryUnitCode="+xFactoryUnit;
		}
		else
		{
			hql="select fu.workCenter from FactoryUnit fu where fu.factoryUnitName like '%"+xFactoryUnit+"%'";
		}
		return (List<String>)getSession().createQuery(hql).list();

	}
	
}