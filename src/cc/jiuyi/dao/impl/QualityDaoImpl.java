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
import cc.jiuyi.dao.QualityDao;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Dao实现类 - 质量
 */

@Repository
public class QualityDaoImpl extends BaseDaoImpl<Quality, String> implements QualityDao {
    
	public Pager getQualityPager(Pager pager, HashMap<String, String> map,String id) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Quality.class);
		pagerSqlByjqGrid(pager,detachedCriteria);

		if(!super.existAlias(detachedCriteria, "products", "products")){
			detachedCriteria.createAlias("products", "products");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "team", "team")){
			detachedCriteria.createAlias("team", "team");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "creater", "creater")){
			detachedCriteria.createAlias("creater", "creater");//表名，别名*/							
		}
		
		if(!super.existAlias(detachedCriteria, "receiver", "receiver")){
			detachedCriteria.createAlias("receiver", "receiver");//表名，别名*/							
		}
		
		if (map.size() > 0) {
			if(map.get("team")!=null){
			    detachedCriteria.add(Restrictions.like("team.teamName", "%"+map.get("team")+"%"));
			}
			
			if(map.get("productName")!=null){
			    detachedCriteria.add(Restrictions.like("products.productsName", "%"+map.get("productName")+"%"));
			}	
		}
		detachedCriteria.add(Restrictions.or(Restrictions.eq("creater.id", id), Restrictions.eq("receiver.id", id)));
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	
	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Quality quality=super.load(id);
			quality.setIsDel(oper);//标记删除
			super.update(quality);
		}
   }
	
}
