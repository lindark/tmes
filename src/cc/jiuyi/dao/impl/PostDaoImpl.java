package cc.jiuyi.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PostDao;
import cc.jiuyi.entity.Post;

/**
 * Dao实现类 - Post
 */

@Repository
public class PostDaoImpl extends BaseDaoImpl<Post, String> implements PostDao {

	@Override
	public void delete(String id) {
		Post post = load(id);
		this.delete(post);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getPostList() {
		String hql = "From Post post order by post.id asc post.crateDate desc";
		return getSession().createQuery(hql).list();
	}

	public Pager getPostPager(Pager pager, HashMap<String, String> map) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Post.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if (map.size() > 0) {
			if(map.get("postCode")!=null){
			    detachedCriteria.add(Restrictions.like("postCode", "%"+map.get("postCode")+"%"));
			}		
			if(map.get("postName")!=null){
				detachedCriteria.add(Restrictions.like("postName", "%"+map.get("postName")+"%"));
			}
			if(map.get("state")!=null){
				detachedCriteria.add(Restrictions.like("state", "%"+map.get("state")+"%"));
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
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		return super.findByPager(pager, detachedCriteria);
	}



	@Override
	public void updateisdel(String[] ids, String oper) {
		for(String id:ids){
			Post post=super.load(id);
			post.setIsDel(oper);//标记删除
			super.update(post);
		}
		
	}

	/**
	 * 查询所有岗位
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getAllList()
	{
		String hql="from Post where isDel='N' and state='1'";
		return this.getSession().createQuery(hql).list();
	}

	/**
	 * 查询岗位数据
	 */
	public Pager getAllPost(Pager pager,HashMap<String, String> map)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Post.class);
		pagerSqlByjqGrid(pager,detachedCriteria);
		if(map!=null&&map.size()>0)
		{
			//岗位编码
			if(map.get("stationcode")!=null)
			{
				detachedCriteria.add(Restrictions.like("postCode", "%"+map.get("stationcode").toString()+"%"));
			}
			//岗位名称
			if(map.get("stationname")!=null)
			{
				detachedCriteria.add(Restrictions.like("postName", "%"+map.get("stationname").toString()+"%"));
			}
		}
		detachedCriteria.add(Restrictions.eq("isDel", "N"));//取出未删除标记数据
		detachedCriteria.add(Restrictions.eq("state", "1"));//已启用
		return super.findByPager(pager, detachedCriteria);
	}

	/**
	 * 查询所有未删除的岗位
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getAllPost()
	{
		String hql="from Post where isDel='N'";
		return this.getSession().createQuery(hql).list();
	}
}