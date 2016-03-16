package cc.jiuyi.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PostDao;
import cc.jiuyi.entity.Post;
import cc.jiuyi.service.PostService;

/**
 * Service实现类 -工序管理
 * @author Reece
 *
 */

@Service
public class PostServiceImpl extends BaseServiceImpl<Post, String>implements PostService{

	@Resource
	private PostDao postDao;
	
	@Resource
	public void setBaseDao(PostDao postDao){
		super.setBaseDao(postDao);
	}
	
	@Override
	public void delete(String id) {
		Post post = postDao.load(id);
		this.delete(post);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Post> getPostList() {		
		return postDao.getPostList();
	}

	@Override
	public Pager getPostPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return postDao.getPostPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		postDao.updateisdel(ids, oper);
		
	}

	/**
	 * 查询所有岗位
	 */
	public List<Post> getAllList()
	{
		return this.postDao.getAllList();
	}

	/**
	 * 查询岗位数据
	 */
	public Pager getAllPost(Pager pager)
	{
		return this.postDao.getAllPost(pager);
	}

	
}
