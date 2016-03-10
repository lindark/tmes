package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import cc.jiuyi.util.ThinkWayUtil;

/**
 * 实体类 - 入库
 */

@Entity
public class EnteringwareHouse extends BaseEntity {

	private static final long serialVersionUID = -3066164332463929036L;
	private Integer totalSingleAmount;// 累计根量
	private Double storageAmount;// 入库数量
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String State;// 状态
	private String isdel;// 是否删除
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String workingbillCode;
	private String maktx;//产品描述
	private String batch;//批次
	private String moudle;//模具

	private WorkingBill workingbill;// 随工单
	
	private String budat;//过账日期
	private String werks;//工厂
	private String lgort;//库存地点
	private String ztext;//文本
	private String moveType;//移动类型 
	private String ex_mblnr;//凭证号
	private String e_message; 
	private String e_type;
	
	private String factory;
	private String workshop;
	private String location;
	
	private String matnr;
	private String xmoudle;
	private String productDate;

	
	@Transient
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	@Transient
	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	@Transient
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		if(state == null){
			state = "2";
		}
		State = state;
	}

	public Integer getTotalSingleAmount() {
		return totalSingleAmount;
	}

	public void setTotalSingleAmount(Integer totalSingleAmount) {
		this.totalSingleAmount = totalSingleAmount;
	}

	public Double getStorageAmount() {
		return storageAmount;
	}

	public void setStorageAmount(Double storageAmount) {
		this.storageAmount = storageAmount;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		if (isdel == null)
			this.isdel = "N";
		else
			this.isdel = isdel;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Transient
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}

	@Transient
	public String getWorkingbillCode() {
		return workingbillCode;
	}

	public void setWorkingbillCode(String workingbillCode) {
		this.workingbillCode = workingbillCode;
	}

	@Transient
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	@Transient
	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	@Transient
	public String getBudat() {
		return budat;
	}

	public void setBudat(String budat) {
		this.budat = budat;
	}

	@Transient
	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	@Transient
	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	@Transient
	public String getZtext() {
		return ztext;
	}

	public void setZtext(String ztext) {
		this.ztext = ztext;
	}

	@Transient
	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getEx_mblnr() {
		return ex_mblnr;
	}

	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
	}

	@Transient
	public String getE_message() {
		return e_message;
	}

	public void setE_message(String e_message) {
		this.e_message = e_message;
	}

	@Transient
	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	@Transient
	public String getXmoudle() {
		return xmoudle;
	}

	public void setXmoudle(String xmoudle) {
		this.xmoudle = xmoudle;
	}

	@Transient
	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	
	
}