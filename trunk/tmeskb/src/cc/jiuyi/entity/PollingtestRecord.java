package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.compass.annotations.Searchable;

/**
 * 实体类——巡检从表
 * 
 */
@Entity
@Searchable
public class PollingtestRecord extends BaseEntity {

	private static final long serialVersionUID = 128480212829968597L;
	private String recordNum;// 缺陷数量
	private String recordDescription;// 缺陷描述
	private Pollingtest pollingtest;// 巡检表--从表
	private String causeId;//记录缺陷表的id
	private String isDel;
	private String istoDel;//平常都为空，修改的时候用来做标记，有标记的不删除，没有标记的删除

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

	public String getRecordDescription() {
		return recordDescription;
	}

	public void setRecordDescription(String recordDescription) {
		this.recordDescription = recordDescription;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Pollingtest getPollingtest() {
		return pollingtest;
	}

	public void setPollingtest(Pollingtest pollingtest) {
		this.pollingtest = pollingtest;
	}

	public String getCauseId() {
		return causeId;
	}

	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	public String getIstoDel() {
		return istoDel;
	}

	public void setIstoDel(String istoDel) {
		if(istoDel==null)
		{
			istoDel="Y";
		}
		this.istoDel = istoDel;
	}

}
