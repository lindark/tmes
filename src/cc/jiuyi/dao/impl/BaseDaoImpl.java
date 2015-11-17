package cc.jiuyi.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.Subcriteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao实现类 - Dao实现类基类
 */

@Repository
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private Class<T> entityClass;
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName() + " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}
	
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}
	

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}
	
	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}
	
	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) { 
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}

	@Override
	public String generateSearchSql(String searchField,
			String searchString, String searchOper,DetachedCriteria detachedCriteria) {
        String wheresql=""; 
        String propertyString="";
        String tableName="";
        if (searchField != null && searchString != null  
                & searchString.length() > 0 && searchOper != null) {  
			if (searchField.contains(".")) { 
				String propertyPrefix = StringUtils.substringBeforeLast(searchField, ".");
				String propertySuffix = StringUtils.substringAfterLast(searchField, ".");
				tableName = StringUtils.substringAfterLast(propertyPrefix, ".");
				if(tableName.equals(""))//表示不是存在多个表的情况
					tableName = propertyPrefix;
				if(!this.existAlias(detachedCriteria, propertyPrefix, tableName))//判断alias 是否已经有创建过,没有创建过，及创建，已经创建过就不需要创建了，不处理同一表的关联处理
					detachedCriteria.createAlias(propertyPrefix, tableName);
				propertyString= tableName+"."+propertySuffix;
			}else{
				propertyString= searchField;
			}
            if ("eq".equals(searchOper)) {  //等于
            	detachedCriteria.add(Restrictions.eq(propertyString, searchString));
            } else if ("ne".equals(searchOper)) { //不等于
            	detachedCriteria.add(Restrictions.ne(propertyString, searchString));
            } else if ("lt".equals(searchOper)) { //小于
            	detachedCriteria.add(Restrictions.lt(propertyString, searchString));
            } else if ("le".equals(searchOper)) { //小于等
            	detachedCriteria.add(Restrictions.le(propertyString, searchString));
            } else if ("gt".equals(searchOper)) { //大于
            	detachedCriteria.add(Restrictions.gt(propertyString, searchString));
            } else if ("ge".equals(searchOper)) { //大于等
            	detachedCriteria.add(Restrictions.ge(propertyString, searchString));
            } else if ("bw".equals(searchOper)) { //已...开始
            	detachedCriteria.add(Restrictions.like(propertyString, searchString+"%"));
            } else if ("bn".equals(searchOper)) { //不已...开始
            	detachedCriteria.add(Restrictions.not(Restrictions.like(propertyString, searchString+"%")));
            } else if ("ew".equals(searchOper)) { //结束于
            	detachedCriteria.add(Restrictions.like(propertyString, "%"+searchString));
            } else if ("en".equals(searchOper)) { //不结束于
            	detachedCriteria.add(Restrictions.not(Restrictions.like(propertyString, "%"+searchString)));
            } else if ("cn".equals(searchOper)) { //包含
            	detachedCriteria.add(Restrictions.like(propertyString, "%"+searchString+"%"));
            } else if ("nc".equals(searchOper)) { //不包含  
            	detachedCriteria.add(Restrictions.not(Restrictions.like(propertyString, "%"+searchString+"%")));
            } else if ("in".equals(searchOper)) { //属于
            	detachedCriteria.add(Restrictions.sqlRestriction(searchString + " in ("+propertyString+")"));
            } else if ("ni".equals(searchOper)) { //不属于
            	detachedCriteria.add(Restrictions.sqlRestriction(searchString + " not in ("+propertyString+")"));
            }   
            
        }  
        return wheresql;  
	}
	
	public void pagerSqlByjqGrid(Pager pager,DetachedCriteria detachedCriteria){
		String wheresql = "";
		Integer ishead=0;
		if(pager.is_search()==true && pager.getRules() != null){
			List list = pager.getRules();
			for(int i=0;i<list.size();i++){
				jqGridSearchDetailTo to = (jqGridSearchDetailTo)list.get(i);
				wheresql+=" "+this.generateSearchSql(to.getField(), to.getData(), to.getOp(),detachedCriteria)+" ";
			}
		}
	}

	public boolean existAlias(Criteria c, String path, String alias){
  	  Iterator itm = ((CriteriaImpl)c).iterateSubcriteria();
  	  while(itm.hasNext()){
  	   Subcriteria sub =  (Subcriteria)itm.next();
  	   if(alias.equals(sub.getAlias()) || path.equals(sub.getPath())){
  	    return true;
  	   }
  	  }
  	  return false;
  	 }

	/**
	 * 通过反射获取 alias 是否已经创建过
	 * @param c
	 * @param path
	 * @param alias
	 * @return
	 */
	 public boolean existAlias(DetachedCriteria c, String path, String alias){
	  Class clazz = c.getClass(); //获取 class
	   Field field = null;
	   CriteriaImpl ci =null;
		try {
			field = clazz.getDeclaredField("criteria");//获取 criteria 的所有方法，包括private
			field.setAccessible(true); //设置访问权限，可以访问final 修饰符的属性或方法
  	    ci = (CriteriaImpl)field.get(c);
	    	
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return existAlias(ci, path, alias);
	 }

}