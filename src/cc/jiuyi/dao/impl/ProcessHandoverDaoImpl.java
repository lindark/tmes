package cc.jiuyi.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessHandoverDao;
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

}
