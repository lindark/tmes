package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * 实体类 - 入库
 */

@Entity
public class EnteringwareHouse extends BaseEntity {

	
	private static final long serialVersionUID = -3066164332463929036L;
	private Integer totalSingleAmout;//累计根量
	private Integer totalCaseAmount;//累计箱数
	//private String ConfirmUser;//确认人 ？？
	private String State;//状态
	private String isdel;//是否删除
	
	public Integer getTotalCaseAmount() {
		return totalCaseAmount;
	}
	public void setTotalCaseAmount(Integer totalCaseAmount) {
		this.totalCaseAmount = totalCaseAmount;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public Integer getTotalSingleAmout() {
		return totalSingleAmout;
	}
	public void setTotalSingleAmout(Integer totalSingleAmout) {
		this.totalSingleAmout = totalSingleAmout;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		if(isdel == null)
			this.isdel="N";
		else
			this.isdel = isdel;
	}
	
	
	
	
}