package cc.jiuyi.dao;

import java.io.Serializable;
import java.util.List;

import cc.jiuyi.bean.Pager;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Dao接口 - Dao基接口
 */

public interface BaseDao<T, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);
	
	/**
	 * 根据ID数组获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String propertyName, Object value);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String[] propertyNames, Object[] propertyValues);
	
	/**
	 * 根据属性名和属性值获取实体对象集合.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象集合
	 */
	public List<T> getList(String propertyName, Object value);
	
	/**
	 * 根据属性名和属性值获取实体对象集合.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象集合
	 */
	public List<T> getList(String[] propertyNames, Object[] propertyValues);
	
	/**
	 * 根据属性名和值的object[] 返回list
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param objlist 属性值的数组集合
	 *            
	 * @return 实体对象集合
	 */
	public List<T> getList(String propertyName,Object[] objlist);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();
	
	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount();

	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String[] propertyNames, Object[] propertyValues);

	/**
	 * 保存实体对象-持久化状态.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public PK save(T entity);
	
	/**
	 * 保存活修改-非持久化状态
	 * @param entity
	 * @return
	 */
	public T merge(T entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);
	
	
	
	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);
	

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session.
	 * 
	 */
	public void flush();

	/**
	 * 清除Session.
	 * 
	 */
	public void clear();
	
	/**
	 * 清除某一对象.
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);

	/**
	 * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager);
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria);
	
	
	/**
	 * jqgrid　使用，根据传入的searchField,searchString,searchOper,返回whereSql
	 * @param searchField  属性名
	 * @param searchString  属性值
	 * @param searchOper   操作
	 * @return
	 */
	public String generateSearchSql(String searchField,String searchString, String searchOper,DetachedCriteria detachedCriteria);
}