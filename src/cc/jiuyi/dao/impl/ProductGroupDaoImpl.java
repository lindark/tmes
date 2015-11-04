package cc.jiuyi.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ProductGroupDao;
import cc.jiuyi.entity.ProductGroup;

/**
 * Dao接口 - 产品组
 */
@Repository
public class ProductGroupDaoImpl extends BaseDaoImpl<ProductGroup, String> implements ProductGroupDao{
	public Pager getProductGroupPager(Pager pager,HashMap<String,String> map) {
		String wheresql = dumppagerSql(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ProductGroup.class);
		if (!wheresql.equals("")) {
			detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
		}
		if(map.size()>0){
			if(map.get("productGroupCode")!=null){
				detachedCriteria.add(Restrictions.like("productGroupCode", "%"+map.get("productGroupCode")+"%"));								
			}
			if(map.get("productGroupName")!=null){
				detachedCriteria.add(Restrictions.like("productGroupName", "%"+map.get("productGroupName")+"%"));								
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
								to.getOp()) + " ";
				ishead = 1;
			}

		}
		System.out.println("wheresql:" + wheresql);
		return wheresql;
	}
	@Override
	public void updateisdel(String[] ids,String oper) {
		for(String id : ids){
			ProductGroup productGroup = super.load(id);
			productGroup.setIsDel(oper);//标记删除
			super.update(productGroup);
		}
		
		
	}
}
