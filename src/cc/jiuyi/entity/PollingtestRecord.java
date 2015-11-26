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

}
