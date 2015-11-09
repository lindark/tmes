package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.ParseException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.entity.Process;

/**
 * Dao实现类 - process
 */

@Repository
public class ProcessDaoImpl extends BaseDaoImpl<Process, String> implements
		ProcessDao {

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

	public Pager getProcessPager(Pager pager, HashMap<String, String> map) {
		String wheresql = processpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Process.class);
		if (!wheresql.equals("")) {
			// detachedCriteria.createAlias("dict", "dict");
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		//System.out.println(map.size());
		if (map.size() > 0) {
			if(map.get("processCode")!=null){
			    detachedCriteria.add(Restrictions.like("processCode", "%"+map.get("processCode")+"%"));
			}		
			if(map.get("processName")!=null){
				detachedCriteria.add(Restrictions.like("processName", "%"+map.get("processName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
			}
			if(map.get("start")!=null||map.get("end")!=null){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date start=sdf.parse(map.get("start"));
					Date end=sdf.parse(map.get("end"));
					detachedCriteria.add(Restrictions.between("createDate", start, end));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}		
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	public String processpagerSql(Pager pager) {
		String wheresql = "";
		Integer ishead = 0;
		if (pager.is_search() == true && pager.getRules() != null) {
			List list = pager.getRules();
			for (int i = 0; i < list.size(); i++) {
				if (ishead == 1) {
					wheresql += " " + pager.getGroupOp() + " ";
				}
				jqGridSearchDetailTo to = (jqGridSearchDetailTo) list.get(i);
				wheresql += " "
						+ super.generateSearchSql(to.getField(), to.getData(),
								to.getOp()) + " ";
				ishead = 1;
			}

		}
		//System.out.println("wheresql:" + wheresql);
		return wheresql;
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Process process=super.load(id);
			process.setIsDel(oper);//标记删除
			super.update(process);
		}
		
	}

	@SuppressWarnings("unchecked")
	public boolean isExistByProcessCode(String processCode) {
		String hql="from Process process where lower(process.processCode)=lower(?)";
		Process process=(Process)getSession().createQuery(hql).setParameter(0, processCode).uniqueResult();
		System.out.println(hql);
		if(process !=null){
			return true;
		}else{
			return false;
		}
	}
}