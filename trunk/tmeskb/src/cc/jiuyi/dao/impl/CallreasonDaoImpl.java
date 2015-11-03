package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.CallreasonDao;
import cc.jiuyi.entity.Callreason;

/**
 * Dao接口 - 呼叫原因
 */
@Repository
public class CallreasonDaoImpl extends BaseDaoImpl<Callreason, String> implements CallreasonDao{
	public Pager getCallreasonPager(Pager pager,HashMap<String,String> map) {
		String wheresql = callreasonpagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Callreason.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map.size()>0){
			if(map.get("callType")!=null){
				detachedCriteria.add(Restrictions.like("callType", "%"+map.get("callType")+"%"));								
			}
			if(map.get("callReason")!=null){
				detachedCriteria.add(Restrictions.like("callReason", "%"+map.get("callReason")+"%"));								
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));								
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);

	}

	public String callreasonpagerSql(Pager pager) {
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
		return wheresql;
	}
	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			Callreason callreason = super.load(id);
			callreason.setIsDel(oper);//标记删除
			super.update(callreason);
		}
		
		
	}
}
