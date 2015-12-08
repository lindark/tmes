package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
/**
 * 抽检缺陷记录表
 * @author gaoyf
 *
 */
@Entity
public class SampleRecord extends BaseEntity
{
	private static final long serialVersionUID = 5538674990202335393L;

	/**
	 * 本表字段
	 */
	private String recordNum;//缺陷数量
	private String recordDescription;//缺陷描述
	private Sample sample;//抽检表id
	private String causeId;//记录缺陷表的id
	private String isDel;
	private String istoDel;//默认都为Y，修改的时候标记为N，为N的不删除，为Y的删除
	/**
	 * get/set
	 */
	//缺陷数量
	@Column
	public String getRecordNum()
	{
		return recordNum;
	}
	public void setRecordNum(String recordNum)
	{
		this.recordNum = recordNum;
	}
	//缺陷描述
	@Column
	public String getRecordDescription()
	{
		return recordDescription;
	}
	public void setRecordDescription(String recordDescription)
	{
		this.recordDescription = recordDescription;
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
	
	@Column
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Sample getSample()
	{
		return sample;
	}
	public void setSample(Sample sample)
	{
		this.sample = sample;
	}
	
}
