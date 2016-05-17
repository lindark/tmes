package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
/**
 * 实体类 - 工序交接子类
 */
@Entity
@Table(name = "ProcessHandoverSon")
public class ProcessHandoverSon extends BaseEntity {
	private static final long serialVersionUID = -8286559049968869353L;
	
	private ProcessHandover processHandover;//主表
	private String bomCode;//子件编码
	private String bomDesp;//子件描述
	private String beforeWorkingCode;//上班随工单
	private String afterWokingCode;//下班随工单
	private String bomAmount;//合格数量
	private String materialAmount;//条子数量
	private String productAmount;//产品数量
//	private String qualifiedNumber;//合格数量
	private String repairNumber;//返修数量
	private Double amount;//交接数量
	private Double cqsl;//裁切倍数
    private Double cqamount;//裁切后正常交接数量
    private Double cqrepairamount;//裁切后返修交接数量
	private Double repairamount;//返修交接数量
	private String isdel;// 是否删除
	
	/**
	 * 假字段
	 */

    

	@ManyToOne(fetch = FetchType.LAZY)
	public ProcessHandover getProcessHandover() {
		return processHandover;
	}

	public void setProcessHandover(ProcessHandover processHandover) {
		this.processHandover = processHandover;
	}

	public String getBomCode() {
		return bomCode;
	}

	public void setBomCode(String bomCode) {
		this.bomCode = bomCode;
	}

	public String getBomDesp() {
		return bomDesp;
	}

	public void setBomDesp(String bomDesp) {
		this.bomDesp = bomDesp;
	}

	public String getAfterWokingCode() {
		return afterWokingCode;
	}

	public void setAfterWokingCode(String afterWokingCode) {
		this.afterWokingCode = afterWokingCode;
	}

	public String getBomAmount() {
		return bomAmount;
	}

	public void setBomAmount(String bomAmount) {
		this.bomAmount = bomAmount;
	}
	

	public String getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(String materialAmount) {
		this.materialAmount = materialAmount;
	}

	public String getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	public String getBeforeWorkingCode() {
		return beforeWorkingCode;
	}

	public void setBeforeWorkingCode(String beforeWorkingCode) {
		this.beforeWorkingCode = beforeWorkingCode;
	}

//	public String getQualifiedNumber() {
//		return qualifiedNumber;
//	}
//
//	public void setQualifiedNumber(String qualifiedNumber) {
//		this.qualifiedNumber = qualifiedNumber;
//	}

	public String getRepairNumber() {
		return repairNumber;
	}

	public void setRepairNumber(String repairNumber) {
		this.repairNumber = repairNumber;
	}

	public Double getCqsl() {
		return cqsl;
	}

	public void setCqsl(Double cqsl) {
		this.cqsl = cqsl;
	}

	public Double getCqamount() {
		return cqamount;
	}

	public void setCqamount(Double cqamount) {
		this.cqamount = cqamount;
	}

	public Double getCqrepairamount() {
		return cqrepairamount;
	}

	public void setCqrepairamount(Double cqrepairamount) {
		this.cqrepairamount = cqrepairamount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRepairamount() {
		return repairamount;
	}

	public void setRepairamount(Double repairamount) {
		this.repairamount = repairamount;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		if(isdel==null){
			isdel="N";
		}
		this.isdel = isdel;
	}
	
	
	
}
