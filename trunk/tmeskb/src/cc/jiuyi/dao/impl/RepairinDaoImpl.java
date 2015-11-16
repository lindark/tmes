package cc.jiuyi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.RepairinDao;
import cc.jiuyi.entity.Repairin;

/**
 * Dao接口 - 返修收货
 */
@Repository
public class RepairinDaoImpl extends BaseDaoImpl<Repairin, String> implements RepairinDao{
	public Pager getRepairinPager(Pager pager,HashMap<String,String> map) {
		String wheresql = dumppagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Repairin.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map.size()>0){
			if(map.get("receiveAmount")!=null){
				detachedCriteria.add(Restrictions.like("receiveAmount", "%"+map.get("receiveAmount")+"%"));								
			}
			if(map.get("totalAmount")!=null){
				detachedCriteria.add(Restrictions.like("totalAmount", "%"+map.get("totalAmount")+"%"));								
			}
			if(map.get("confirmUser")!=null){
				detachedCriteria.add(Restrictions.like("confirmUser", "%"+map.get("confirmUser")+"%"));								
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));								
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);

	}

	public String dumppagerSql(Pager pager) {
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
								to.getOp(), null) + " ";
				ishead = 1;
			}

		}
		System.out.println("wheresql:" + wheresql);
		return wheresql;
	}
	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			Repairin repairin = super.load(id);
			repairin.setIsDel(oper);//标记删除
			super.update(repairin);
		}	
	}
}
