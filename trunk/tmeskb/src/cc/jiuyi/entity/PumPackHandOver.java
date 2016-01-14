package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
 
/**
 * 实体类 -抽包异常交接
 */
@Entity
public class PumPackHandOver extends BaseEntity {
	private static final long serialVersionUID = -5222443445114313847L;
	private String materialCode;//生产订单编码
	private Double actualHOMount;//实际交接数量
	private Double actualBomMount;//实际物料数量
	private String submitCode;//提交人编号
	private String submitName;//提交人姓名
	private String sureCode;//提交人编号
	private String sureName;//确认人姓名
	private String state;//状态(1、未确认，2、已确认)
	private String mblnr;//物料凭证号 
	
	private WorkingBill workingBill;//随工单 
	private HandOver handOver;//主表
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public Double getActualHOMount() {
		return actualHOMount;
	}
	public void setActualHOMount(Double actualHOMount) {
		this.actualHOMount = actualHOMount;
	}
	public Double getActualBomMount() {
		return actualBomMount;
	}
	public void setActualBomMount(Double actualBomMount) {
		this.actualBomMount = actualBomMount;
	}
	public String getSubmitCode() {
		return submitCode;
	}
	public void setSubmitCode(String submitCode) {
		this.submitCode = submitCode;
	}
	public String getSubmitName() {
		return submitName;
	}
	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}
	public String getSureCode() {
		return sureCode;
	}
	public void setSureCode(String sureCode) {
		this.sureCode = sureCode;
	}
	public String getSureName() {
		return sureName;
	}
	public void setSureName(String sureName) {
		this.sureName = sureName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getWorkingBill() {
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public HandOver getHandOver() {
		return handOver;
	}
	public void setHandOver(HandOver handOver) {
		this.handOver = handOver;
	}
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	
}
