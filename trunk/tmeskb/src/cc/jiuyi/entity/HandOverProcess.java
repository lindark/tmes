package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 实体类 - 工序交接
 * @param args
 */


@Entity
@Table(name = "HandOverProcess")
public class HandOverProcess extends BaseEntity{
	
	private static final long serialVersionUID = -3676085256696731274L;
	
	
	private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Double amount;//正常交接数量
    private String materialCode;//物料编码
    private String materialName;//物料名称
    private String processid;//工序id
    private Double repairAmount;//返修交接数量
    
    private Double cqamount;//裁切后正常交接数量
    private Double cqrepairAmount;//裁切后返修交接数量
    private Double cqsl;//裁切倍数
   
    /*冗余字段*/
    private String processName;//工序名称
    private String beforworkingbillCode;//上班随工单编码
    private String afterworkingbillCode;//下班随工单
    private Double actualAmount;//实际零头数交接数量
    private Double unAmount;//实际异常交接数量
    
    /*冗余字段end*/
    //private Material material;//物料组件
    private WorkingBill afterworkingbill;//下班随工单
    private WorkingBill beforworkingbill;//上班随工单
    private Admin saveadmin;//保存人
    private Admin submitadmin;//提交人
    private Admin approvaladmin;//确认人
    private String e_type;
    private String e_message;
    private String mblnr;
    private HandOver handover;//交接主表

    @ManyToOne(fetch = FetchType.LAZY)
	public HandOver getHandover() {
		return handover;
	}
	public void setHandover(HandOver handover) {
		this.handover = handover;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
        if(isDel==null){
        	isDel="N";
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		if(amount == null)
			amount = 0d;
		this.amount = amount;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getAfterworkingbill() {
		return afterworkingbill;
	}
	
	public void setAfterworkingbill(WorkingBill afterworkingbill) {
		this.afterworkingbill = afterworkingbill;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getBeforworkingbill() {
		return beforworkingbill;
	}
	public void setBeforworkingbill(WorkingBill beforworkingbill) {
		this.beforworkingbill = beforworkingbill;
	}
	@Transient
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@Transient
	public String getBeforworkingbillCode() {
		return beforworkingbillCode;
	}
	public void setBeforworkingbillCode(String beforworkingbillCode) {
		this.beforworkingbillCode = beforworkingbillCode;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getSaveadmin() {
		return saveadmin;
	}
	public void setSaveadmin(Admin saveadmin) {
		this.saveadmin = saveadmin;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getSubmitadmin() {
		return submitadmin;
	}
	public void setSubmitadmin(Admin submitadmin) {
		this.submitadmin = submitadmin;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Admin getApprovaladmin() {
		return approvaladmin;
	}
	public void setApprovaladmin(Admin approvaladmin) {
		this.approvaladmin = approvaladmin;
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
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	@Transient
	public String getAfterworkingbillCode() {
		return afterworkingbillCode;
	}
	public void setAfterworkingbillCode(String afterworkingbillCode) {
		this.afterworkingbillCode = afterworkingbillCode;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	
	public Double getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(Double repairAmount) {
		this.repairAmount = repairAmount;
	}
	@Transient
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public Double getUnAmount() {
		return unAmount;
	}
	public void setUnAmount(Double unAmount) {
		this.unAmount = unAmount;
	}
	public Double getCqamount() {
		return cqamount;
	}
	public void setCqamount(Double cqamount) {
		this.cqamount = cqamount;
	}
	public Double getCqrepairAmount() {
		return cqrepairAmount;
	}
	public void setCqrepairAmount(Double cqrepairAmount) {
		this.cqrepairAmount = cqrepairAmount;
	}
	public Double getCqsl() {
		return cqsl;
	}
	public void setCqsl(Double cqsl) {
		this.cqsl = cqsl;
	}

	
	
	
   
	
    
	
}
