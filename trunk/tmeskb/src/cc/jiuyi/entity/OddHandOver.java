package cc.jiuyi.entity;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 -零头数交接
 */
@Entity
public class OddHandOver extends BaseEntity {
	private static final long serialVersionUID = -5222443445114313847L;
	
	private String materialCode;//物料编码
	private String materialDesp;//物料描述
	private Double actualHOMount;//实际零头数交接数量
	private Double unHOMount;//实际异常交接数量
	private Double actualBomMount;//实际物料数量
	private Double unBomMount;//实际异常物料数量
	private String submitCode;//提交人编号
	private String submitName;//提交人姓名
	private String sureCode;//确认编号
	private String sureName;//确认人姓名
	private String state;//状态(1、已提交，2、已确认)
	private String mblnr;//物料凭证号 
	private String beforeWokingCode;//上班随工单
	private String afterWorkingCode;//下班随工单
	
	
	private WorkingBill workingBill;//随工单 
	private HandOver handOver;//主表
	
	private String workingBillCode;
	private String maktx;
	private String productDate;
	private String stateRemark;
	
	
	
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	public WorkingBill getWorkingBill() {
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSubmitName() {
		return submitName;
	}
	public void setSubmitName(String submitName) {
		this.submitName = submitName;
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
	public String getSubmitCode() {
		return submitCode;
	}
	public void setSubmitCode(String submitCode) {
		this.submitCode = submitCode;
	}
	public String getSureCode() {
		return sureCode;
	}
	public void setSureCode(String sureCode) {
		this.sureCode = sureCode;
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
	public Double getUnHOMount() {
		return unHOMount;
	}
	public void setUnHOMount(Double unHOMount) {
		this.unHOMount = unHOMount;
	}
	public Double getUnBomMount() {
		return unBomMount;
	}
	public void setUnBomMount(Double unBomMount) {
		this.unBomMount = unBomMount;
	}
	public String getBeforeWokingCode() {
		return beforeWokingCode;
	}
	public void setBeforeWokingCode(String beforeWokingCode) {
		this.beforeWokingCode = beforeWokingCode;
	}
	public String getAfterWorkingCode() {
		return afterWorkingCode;
	}
	public void setAfterWorkingCode(String afterWorkingCode) {
		this.afterWorkingCode = afterWorkingCode;
	}
	public String getMaterialDesp() {
		return materialDesp;
	}
	public void setMaterialDesp(String materialDesp) {
		this.materialDesp = materialDesp;
	}
	
	@Transient
	public String getWorkingBillCode() {
		return workingBillCode;
	}
	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}
	
	@Transient
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	@Transient
	public String getProductDate() {
		return productDate;
	}
	
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	

	
	
}
