package cc.jiuyi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 半成品巡检缺陷记录表
 * 
 * @author Reece
 * 
 */
@Entity
public class IpRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3009668499108713824L;

	private String isDel;// 是否删除
	private String istoDel;// 默认都为Y，修改的时候标记为N，为N的不删除，为Y的删除
	private String recordNum;// 缺陷数量
	private String recordDescription;// 缺陷描述
	private ItermediateTestDetail itermediateTestDetail;// 半成品巡检从表--外键
	private String causeId;// 缺陷ID

	// 假字段
	private String xbugids;// 不合格原因id的字符串
	private String xbugnums;// 不合格数量字符串

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

	@Column
	public String getCauseId() {
		return causeId;
	}

	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}

	@Transient
	public String getXbugids() {
		return xbugids;
	}

	public void setXbugids(String xbugids) {
		this.xbugids = xbugids;
	}

	@Transient
	public String getXbugnums() {
		return xbugnums;
	}

	public void setXbugnums(String xbugnums) {
		this.xbugnums = xbugnums;
	}

	// 半成品巡检从表--外键
	@ManyToOne(fetch = FetchType.LAZY)
	public ItermediateTestDetail getItermediateTestDetail() {
		return itermediateTestDetail;
	}

	public void setItermediateTestDetail(
			ItermediateTestDetail itermediateTestDetail) {
		this.itermediateTestDetail = itermediateTestDetail;
	}

}
