package cc.jiuyi.dao;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Post;

/**
 * Dao接口 -岗位管理
 * 
 *
 */

public interface PostDao extends BaseDao<Post,String> {
	
	
	/**
	 * 取出所有工序对象
	 * @return
	 */
	public List<Post> getPostList();
	
	public Pager getPostPager(Pager pager,HashMap<String,String>map);

	/**
	 * 标记删除
	 * @param id
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

	/**
	 * 查询所有岗位
	 */
	public List<Post> getAllList();

	/**
	 * 查询岗位数据
	 */
	public Pager getAllPost(Pager pager);

	/**
	 * 查询所有未删除的岗位
	 */
	public List<Post> getAllPost();
}
