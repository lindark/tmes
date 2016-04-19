package cc.jiuyi.dao.impl;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ProcessHandoverSonDao;
import cc.jiuyi.entity.ProcessHandoverSon;
/**
 * Dao实现类 - 工序交接子类
 */
@Repository
public class ProcessHandoverSonDaoImpl extends BaseDaoImpl<ProcessHandoverSon, String> implements
		ProcessHandoverSonDao {
	// 关联处理
	@Override
	public void delete(ProcessHandoverSon processHandoverSon) {
		super.delete(processHandoverSon);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		ProcessHandoverSon processHandoverSon = load(id);
		this.delete(processHandoverSon);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			ProcessHandoverSon processHandoverSon = load(id);
			this.delete(processHandoverSon);
		}
	}
}
