package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 异常日志
 */

@Entity
public class AbnormalLog extends BaseEntity {

	private static final long serialVersionUID = -4234122902010236826L;
	private Admin operator;// 操作员
	//private String info;// 日志信息
	private String type;//日志类型
	private Abnormal abnormal;//异常

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
