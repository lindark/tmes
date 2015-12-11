package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——纸箱
 * 
 */
@Entity
@Searchable
public class Carton extends BaseEntity {

	private static final long serialVersionUID = -8927337834611798560L;
	private String cartonCode;// 纸箱编码
	private String cartonDescribe;// 纸箱描述
	private Integer cartonAmount;// 纸箱数量
	private String state;// 状态
	private String isDel;// 是否删除
	private Admin confirmUser;// 确认人
	private Admin createUser;//创建人
	private String stateRemark;// 状态描述
	private String adminName;//确认人的名字
	private String createName;//创建人的名字
	private String move_type;//移动类型
	private String lgort;//库存地点
	private String werks;//工厂
	private String xuh;//序号
	private String budat;//过账日期
	private String lifnr;//供应商编码
	private String charg;//批次
	private String e_type;//S/N
	private String e_message;//付款信息
	private String ex_mblnr;//物料凭证
	private WorkingBill workingbill;// 随工单
	private String workingbillCode;
	private String maktx;//产品描述

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getCartonCode() {
		return cartonCode;
	}

	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}

	public String getCartonDescribe() {
		return cartonDescribe;
	}

	public void setCartonDescribe(String cartonDescribe) {
		this.cartonDescribe = cartonDescribe;
	}

	public Integer getCartonAmount() {
		return cartonAmount;
	}

	public void setCartonAmount(Integer cartonAmount) {
		this.cartonAmount = cartonAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null) {
			state = "2";
		}
		this.state = state;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
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

	public String getMove_type() {
		return move_type;
	}

	public void setMove_type(String move_type) {
		this.move_type = move_type;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getXuh() {
		return xuh;
	}

	public void setXuh(String xuh) {
		this.xuh = xuh;
	}

	public String getBudat() {
		return budat;
	}

	public void setBudat(String budat) {
		this.budat = budat;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	public String getE_message() {
		return e_message;
	}

	public void setE_message(String e_message) {
		this.e_message = e_message;
	}

	public String getEx_mblnr() {
		return ex_mblnr;
	}

	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
	}

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
	
}
