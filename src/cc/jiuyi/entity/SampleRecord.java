package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 抽检缺陷记录表
 * 
 * @author gaoyf
 * 
 */
@Entity
public class SampleRecord extends BaseEntity {
	private static final long serialVersionUID = 5538674990202335393L;

	/**
	 * 本表字段
	 */
	private String recordNum;// 缺陷数量
	private String recordDescription;// 缺陷描述
	private String sampleId;// 抽检表id
	private String causeId;// 记录缺陷表的id
	private String isDel;
	private String istoDel;// 平常都为空，修改的时候用来做标记，有标记的不删除，没有标记的删除

	/**
	 * get/set
	 */
	// 缺陷数量
	@Column
	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	// 缺陷描述
	@Column
	public String getRecordDescription() {
		return recordDescription;
	}

	public void setRecordDescription(String recordDescription) {
		this.recordDescription = recordDescription;
	}

	@Column
	public String getCauseId() {
		return causeId;
	}

	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}

	@Column
	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
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
	public String getIstoDel() {
		return istoDel;
	}

	public void setIstoDel(String istoDel) {
		if (istoDel == null) {
			istoDel = "Y";
		}
		this.istoDel = istoDel;
	}

}
