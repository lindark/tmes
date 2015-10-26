package cc.jiuyi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;


/**
 * Dao实现类 - process
 */

@Repository
public class ProcessDaoImpl extends BaseDaoImpl<Process, String> implements ProcessDao {
	
	
	@Override
	public void delete(String id) {
		Process process = load(id);
		this.delete(process);
	}
	
	
	// 重写方法，删除的同时清除关联
		@Override
		public void delete(String[] ids) {
			for (String id : ids) {
				this.delete(id);
			}
   }


		@Override
		@SuppressWarnings("unchecked")
		public List<Process> getProcessList() {
			String hql="From Process process order by process.id asc process.crateDate desc";
			return getSession().createQuery(hql).list();
		}
	
		
	
}