package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 半成品巡检缺陷记录表
 * @author Reece
 *
 */
@Entity
public class IpRecord extends BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3009668499108713824L;
	
	
	private String recordNum;//缺陷数量
	private String recordDescription;//缺陷描述
	private ItermediateTestDetail itermediateTestDetail;//半成品巡检从表--外键
	
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
	
	//半成品巡检从表--外键
	@ManyToOne(fetch=FetchType.LAZY)
	public ItermediateTestDetail getItermediateTestDetail() {
		return itermediateTestDetail;
	}
	public void setItermediateTestDetail(ItermediateTestDetail itermediateTestDetail) {
		this.itermediateTestDetail = itermediateTestDetail;
	}
	
	
}
