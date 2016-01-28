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
}