package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 整改情况跟踪
 */
@Entity
public class FlowingRectify extends BaseEntity{

	private static final long serialVersionUID = -7213423223153812423L;
	
	private String content;//内容
	private Admin operater;//操作人
	private Quality quality;//质量问题单
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne(fetch = FetchType.LAZY)	
	public Admin getOperater() {
		return operater;
	}
	public void setOperater(Admin operater) {
		this.operater = operater;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	
	
	
}
