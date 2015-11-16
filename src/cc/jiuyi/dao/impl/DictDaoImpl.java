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
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dict.class);
			pagerSqlByjqGrid(pager,detachedCriteria);

			return super.findByPager(pager,detachedCriteria);
		
	}

	@Override
	public String getDictValueByDictKey(Object dictname, Object dictkey) {
		// TODO Auto-generated method stub
		String hql = "from Dict dict where dict.dictname=? and dictkey=?order by dict.dictkey asc";
		// System.out.println(hql);
		return ((Dict) getSession().createQuery(hql).setParameter(0, dictname)
				.setParameter(1, dictkey).list().get(0)).getDictvalue();
	}

	//获取dict的html标签,状态
	@SuppressWarnings("unchecked")
	@Override
	public List<Dict> getSate(String dictname)
	{
		String hql="from Dict as a where a.dictname=?";
		return this.getSession().createQuery(hql).setParameter(0, dictname).list();
	}
	
	
	
}