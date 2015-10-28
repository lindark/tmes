package cc.jiuyi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Product;
import cc.jiuyi.entity.Message.DeleteStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - dict
 */

@Repository
public class DictDaoImpl extends BaseDaoImpl<Dict, String> implements DictDao {
	
	// 关联处理
	@Override
	public void delete(Dict dict) {
		super.delete(dict);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Dict dict = load(id);
		this.delete(dict);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Dict dict = load(id);
			this.delete(dict);
		}
	}
	
	//根据dictName和dictkey找到dict对象
	public Dict getDict(String dictname,String dictkey) {
		String hql = "from Dict as dict where dict.dictname = ? and dict.dictkey = ? ";
		return (Dict) getSession().createQuery(hql).setParameter(0, dictname).setParameter(1, dictkey).uniqueResult();
	}
	
	
	public Pager getDictPager(Pager pager,Map map) {
			String wheresql = dictpagerSql(pager);
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dict.class);
			if(!wheresql.equals("")){
				detachedCriteria.add(Restrictions.sqlRestriction(wheresql));
			}
			if(map != null){
//				detachedCriteria.add(Restrictions.like(propertyName, "%"+dfd+"%"));
//				detachedCriteria.add(Restrictions.b);
			}
			return super.findByPager(pager,detachedCriteria);
		
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

	@Override
	public String getDictValueByDictKey(Object dictname, Object dictkey) {
		// TODO Auto-generated method stub
		String hql = "from Dict dict where dict.dictname=? and dictkey=?order by dict.dictkey asc";
		// System.out.println(hql);
		return ((Dict) getSession().createQuery(hql).setParameter(0, dictname)
				.setParameter(1, dictkey).list().get(0)).getDictvalue();
	}
	
	
	
}