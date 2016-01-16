package cc.jiuyi.dao.impl;

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

}