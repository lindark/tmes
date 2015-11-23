package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 实体类 - 刷卡
 */
@Entity
public class SwiptCard extends BaseEntity{

	private static final long serialVersionUID = -7213486323153832426L;
	
	private String type;//刷卡类型
	
	private Set<Admin> adminSet;//
	
	private Abnormal abnormal;//异常
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(fetch = FetchType.LAZY,mappedBy="swiptCard")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	
	
}
