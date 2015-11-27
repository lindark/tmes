package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 日志
 */

@Entity
public class UnusualLog extends BaseEntity {

	private static final long serialVersionUID = -4292124902020236826L;
	private Admin operator;// 操作员
	private String info;// 日志信息
	private Quality quality;//质量问题
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getOperator() {
		return operator;
	}
	public void setOperator(Admin operator) {
		this.operator = operator;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	
	
}
