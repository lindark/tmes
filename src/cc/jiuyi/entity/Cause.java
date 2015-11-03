package cc.jiuyi.entity;

import javax.persistence.Entity;

import org.compass.annotations.Searchable;

/**
 * 实体类——缺陷代码管理
 *
 */
@Entity
@Searchable
public class Cause extends BaseEntity {

	private static final long serialVersionUID = -7677927911812294207L;
	
	private String causeCode;//原因编码
	private String causeType;//原因类型
	private String causeName;//原因名称
	private String isDel;//是否删除
	public String getCauseCode() {
		return causeCode;
	}
	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}
	public String getCauseType() {
		return causeType;
	}
	public void setCauseType(String causeType) {
		this.causeType = causeType;
	}
	public String getCauseName() {
		return causeName;
	}
	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		if(isDel == null){
			isDel = "N";
		}
		this.isDel = isDel;
	}
	
}
