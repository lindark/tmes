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
	private Sample sample;//抽检表--外键
	
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
	//抽检表--外键
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
