package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 实体类——工位
 * 
 */
@Entity
public class Station extends BaseEntity
{
	
	private static final long serialVersionUID = 3920035198883978250L;
	
	private String code;//编码
	private String name;//名称
	private String isWork;//是否启用
	private String isDel;//是否删除
	private Post posts;//岗位
	
	//假字段
	private String xposts;//岗位
	private String xisWork;//是否启用
	
	/**=========get/set=============*/
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getIsWork()
	{
		return isWork;
	}
	public void setIsWork(String isWork)
	{
		this.isWork = isWork;
	}
	public String getIsDel()
	{
		return isDel;
	}
	public void setIsDel(String isDel)
	{
		if(isDel==null)
		{
			isDel="N";
		}
		this.isDel = isDel;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Post getPosts()
	{
		return posts;
	}
	public void setPosts(Post posts)
	{
		this.posts = posts;
	}
	@Transient
	public String getXposts()
	{
		return xposts;
	}
	public void setXposts(String xposts)
	{
		this.xposts = xposts;
	}
	@Transient
	public String getXisWork()
	{
		return xisWork;
	}
	public void setXisWork(String xisWork)
	{
		this.xisWork = xisWork;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
}
