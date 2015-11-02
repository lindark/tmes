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
	private Integer storageAmout;//入库数量
	//private String ConfirmUser;//确认人 ？？
	private Date storageDate;//入库时间
	private String State;//状态
	private String isdel;//是否删除
	
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
	
	
	public Date getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	public Integer getStorageAmout() {
		return storageAmout;
	}
	public void setStorageAmout(Integer storageAmout) {
		this.storageAmout = storageAmout;
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