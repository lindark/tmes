package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 工艺日志
 */

@Entity
public class CraftLog extends BaseEntity {
	
	private static final long serialVersionUID = -4294124902010236826L;
	private Admin operator;// 操作员
	private String info;// 日志信息
	private Craft craft;//工艺问题
	
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
	public Craft getCraft() {
		return craft;
	}
	public void setCraft(Craft craft) {
		this.craft = craft;
	}

}
