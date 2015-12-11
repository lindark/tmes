package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.DumpDao;
import cc.jiuyi.entity.Dump;

/**
 * Dao接口 - 转储管理
 */
@Repository
public class DumpDaoImpl extends BaseDaoImpl<Dump, String> implements DumpDao {

	@Override
	public Pager findPagerByjqGrid(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Dump.class);
		pagerSqlByjqGrid(pager, detachedCriteria);
		if (map.size() > 0) {
			if(map.get("workingbillCode")!=null){
			    detachedCriteria.add(Restrictions.eq("workingbillCode", map.get("workingbillCode")));
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));// 取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		for (String id : ids) {
			Dump dump = super.load(id);
			dump.setIsDel(oper);// 标记删除
			super.update(dump);
		}
	}

}
