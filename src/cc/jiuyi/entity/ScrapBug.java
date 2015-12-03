package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 报废缺陷记录
 * @author gaoyf
 *
 */
@Entity
public class ScrapBug extends BaseEntity
{
	private static final long serialVersionUID = -7698524445210399963L;

	private String isDel;//是否删除
	private String istoDel;//默认都为Y，修改的时候标记为N，为N的不删除，为Y的删除
	private String sbbugNum;//缺陷数量
	private String sbbugContent;//缺陷内容
	private String scrapMessageId;//报废信息id
	private Scrap scrap;//报废表Id外键
	
	/**get/set*/
	@Column
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
	@Column
	public String getIstoDel()
	{
		return istoDel;
	}
	public void setIstoDel(String istoDel)
	{
		if(istoDel==null)
		{
			istoDel="Y";
		}
		this.istoDel = istoDel;
	}
	@Column
	public String getSbbugNum()
	{
		return sbbugNum;
	}
	public void setSbbugNum(String sbbugNum)
	{
		this.sbbugNum = sbbugNum;
	}
	@Column
	public String getSbbugContent()
	{
		return sbbugContent;
	}
	public void setSbbugContent(String sbbugContent)
	{
		this.sbbugContent = sbbugContent;
	}
	@Column
	public String getScrapMessageId()
	{
		return scrapMessageId;
	}
	public void setScrapMessageId(String scrapMessageId)
	{
		this.scrapMessageId = scrapMessageId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Scrap getScrap()
	{
		return scrap;
	}
	public void setScrap(Scrap scrap)
	{
		this.scrap = scrap;
	}
}
