package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
	private ScrapMessage scrapMessage;//报废信息id
	private String causeId;//缺陷id
	
	//假字段
	private String xbugids;//报废原因id的字符串
	private String xbugnums;//报废数量字符串
	
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
	public String getCauseId()
	{
		return causeId;
	}
	public void setCauseId(String causeId)
	{
		this.causeId = causeId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public ScrapMessage getScrapMessage()
	{
		return scrapMessage;
	}
	public void setScrapMessage(ScrapMessage scrapMessage)
	{
		this.scrapMessage = scrapMessage;
	}
	
	@Transient
	public String getXbugids()
	{
		return xbugids;
	}
	public void setXbugids(String xbugids)
	{
		this.xbugids = xbugids;
	}
	@Transient
	public String getXbugnums()
	{
		return xbugnums;
	}
	public void setXbugnums(String xbugnums)
	{
		this.xbugnums = xbugnums;
	}
	
	
}
