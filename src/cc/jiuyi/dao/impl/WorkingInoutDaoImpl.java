package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;



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

	@Override
	public List<WorkingInout> findPagerByWorkingBillInout(
			HashMap<String, String> map) {
		if (!map.get("workingBillCode").equals("")) {
			String hql = "from WorkingInout a where workingbill.workingBillCode=? and ( workingbill.productDate between ? and ? )";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("workingBillCode")).setParameter(1, map.get("start")).setParameter(2, map.get("end")).list();
		}else{
			String hql = "from WorkingInout a where workingbill.productDate between ? and ? ";
			return (List<WorkingInout>) getSession().createQuery(hql).setParameter(0, map.get("start")).setParameter(1, map.get("end")).list();
		}
		
		
	}

}