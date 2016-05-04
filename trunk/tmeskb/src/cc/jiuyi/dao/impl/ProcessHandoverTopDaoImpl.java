package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ProcessHandoverTopDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.ProcessHandoverTop;
/**
 * Dao实现类 - 工序交接
 */
@Repository
public class ProcessHandoverTopDaoImpl extends BaseDaoImpl<ProcessHandoverTop, String> implements
		ProcessHandoverTopDao {

	@Override
	public List<ProcessHandoverTop> getPHT(Admin admin) {
		if(admin!=null){
			String hql = "from ProcessHandoverTop processHandoverTop where productDate=? and shift=? and createUser.id=? and isDel=?";
			return (List<ProcessHandoverTop>)getSession().createQuery(hql).setParameter(0, admin.getProductDate()).setParameter(1, admin.getShift()).setParameter(2, admin.getId()).setParameter(3, "N").list();
		}else{
			return null;
		}
	}
}
