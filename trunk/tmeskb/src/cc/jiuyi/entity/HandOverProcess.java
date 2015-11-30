package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Searchable
@Table(name = "HandOverProcess")
public class HandOverProcess extends BaseEntity{
	
	private static final long serialVersionUID = -3676085256696731274L;
	
	
	private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Integer amount;//数量
    
    /*冗余字段*/
    private String processName;//工序名称
    private String materialCode;//物料编码
    private String materialName;//物料名称
    private String beforworkingbillCode;//上班随工单编码
    /*冗余字段end*/
    
    private Process process;//工序
    private Material material;//物料组件
    private WorkingBill afterworkingbill;//下班随工单
    private WorkingBill beforworkingbill;//上班随工单
    private Admin saveadmin;//保存人
    private Admin submitadmin;//提交人
    private Admin approvaladmin;//确认人

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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		if(amount == null)
			amount = 0;
		this.amount = amount;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
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
	@Transient
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@Transient
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
	
	
	
	
   
	
    
	
}
