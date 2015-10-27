package cc.jiuyi.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Dict;
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

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Process> getProcessList() {
		String hql = "From Process process order by process.id asc process.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getDictPager(Pager pager) {
		String wheresql = dictpagerSql(pager);
		if(!wheresql.equals("")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dict.class);
			//detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
			return super.findByPager(pager, detachedCriteria);
		}else{
			return super.findByPager(pager);
		}
     }
	
	public String dictpagerSql(Pager pager){
		String wheresql = "";
		Integer ishead=0;
		if(pager.is_search()==true){
			List list = pager.getRules();
			for(int i=0;i<list.size();i++){
				if(ishead==1){
					wheresql += " "+pager.getGroupOp()+" ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo)list.get(i);
				wheresql+=" "+super.generateSearchSql(to.getField(), to.getData(), to.getOp())+" ";
				ishead = 1;
			}
			
		}
		System.out.println("wheresql:"+wheresql);
		return wheresql;
	}
}