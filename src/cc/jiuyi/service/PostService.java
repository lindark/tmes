package cc.jiuyi.service;

import java.util.HashMap;
import java.util.List;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.entity.Post;

/**
 * Service接口 - 工序管理
 */

public interface PostService extends BaseService<Post, String> {

	/**
	 * 取出所有Post对象
	 * 
	 * @return
	 */
	public List<Post> getPostList();

	public Pager getPostPager(Pager pager, HashMap<String, String> map);
	
	/**
	 * 标记删除
	 * @param ids
	 * @param oper Y/N
	 */
	public void updateisdel(String[] ids,String oper);

}