package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类 - 工艺维修单
 */
@Entity
@Table(name = "processfix")
public class Craft extends BaseEntity{

	private static final long serialVersionUID = -3213423223153832326L;
	
	private String cabinetCode;//机台号
	private String productsName;//产品名称
	private String classes;//班组
	private String unusualDescription_make;//异常描述_制造
	private String treatmentMeasure_make;//制造处理措施
	private String resultCode_make;//制造处理结果
	
	private String unusualDescription_process;//工艺异常分析
	private String repairName;//维修人员
	private String treatmentMeasure_process;//工艺处理措施
	private String resultCode_process;//工艺处理结果
	
	private String createUser;//创建人
	private String modifyUser;//修改人
	private String isDel;//是否删除
	
	public String getCabinetCode() {
		return cabinetCode;
	}
	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}
	public String getProductsName() {
		return productsName;
	}
	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getUnusualDescription_make() {
		return unusualDescription_make;
	}
	public void setUnusualDescription_make(String unusualDescription_make) {
		this.unusualDescription_make = unusualDescription_make;
	}
	public String getTreatmentMeasure_make() {
		return treatmentMeasure_make;
	}
	public void setTreatmentMeasure_make(String treatmentMeasure_make) {
		this.treatmentMeasure_make = treatmentMeasure_make;
	}
	public String getResultCode_make() {
		return resultCode_make;
	}
	public void setResultCode_make(String resultCode_make) {
		this.resultCode_make = resultCode_make;
	}
	public String getUnusualDescription_process() {
		return unusualDescription_process;
	}
	public void setUnusualDescription_process(String unusualDescription_process) {
		this.unusualDescription_process = unusualDescription_process;
	}
	public String getRepairName() {
		return repairName;
	}
	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	public String getTreatmentMeasure_process() {
		return treatmentMeasure_process;
	}
	public void setTreatmentMeasure_process(String treatmentMeasure_process) {
		this.treatmentMeasure_process = treatmentMeasure_process;
	}
	public String getResultCode_process() {
		return resultCode_process;
	}
	public void setResultCode_process(String resultCode_process) {
		this.resultCode_process = resultCode_process;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
}
