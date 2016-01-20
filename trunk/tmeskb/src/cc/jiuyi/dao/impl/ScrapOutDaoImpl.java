package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ProcessDao;
import cc.jiuyi.dao.ProductsDao;
import cc.jiuyi.dao.ScrapOutDao;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.ScrapOut;

/**
 * Dao实现类 - 报废产出对照表
 */

@Repository
public class ScrapOutDaoImpl extends BaseDaoImpl<ScrapOut, String> implements ScrapOutDao {
	

	@Override
	public void delete(String id) {
		ScrapOut scrapOut = load(id);
		this.delete(scrapOut);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	public Pager getScrapOutPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(ScrapOut.class);	
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if(map.get("productsCode")!=null){
			    detachedCriteria.add(Restrictions.like("productsCode", "%"+map.get("productsCode")+"%"));
			}		
			if(map.get("productsName")!=null){
				detachedCriteria.add(Restrictions.like("productsName", "%"+map.get("productsName")+"%"));
			}
			if(map.get("materialCode")!=null){
				detachedCriteria.add(Restrictions.like("materialCode", "%"+map.get("materialCode")+"%"));
			}
			if(map.get("materialName")!=null){
				detachedCriteria.add(Restrictions.like("materialName", "%"+map.get("materialName")+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			ScrapOut scrapOut = super.load(id);
			scrapOut.setIsDel(oper);// 标记删除
			super.update(scrapOut);
		}

	}


	@Override
	public List<ScrapOut> getExistScrapOutList() {
		String hql = "From ScrapOut scrapOut where isDel='N' order by scrapOut.id asc scrapOut.crateDate desc";
		return getSession().createQuery(hql).list();
	}


}