package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.ModelDao;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;

/**
 * Dao实现类 - 工模
 */

@Repository
public class ModelDaoImpl extends BaseDaoImpl<Model, String> implements ModelDao {

	public Pager getModelPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Model.class);
		pagerSqlByjqGrid(pager,detachedCriteria);

		if (map.size() > 0) {

			if(map.get("teamId")!=null){
			    detachedCriteria.add(Restrictions.like("teamId", "%"+map.get("teamId")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("productName", "%"+map.get("productName")+"%"));
			}
			
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}
	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Model model=super.load(id);
			model.setIsDel(oper);//标记删除
			super.update(model);
		}
   }
  
}
