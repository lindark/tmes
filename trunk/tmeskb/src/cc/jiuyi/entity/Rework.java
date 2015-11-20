package cc.jiuyi.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 返工
 * @param args
 */


@Entity
@Searchable
@Table(name = "Rework")
public class Rework extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -604526246040449331L;
	
	private Integer reworkCount;//翻包次数
	private Integer reworkAmount;//翻包数量
	private Integer defectAmount;//缺陷数量
	private String isQualified;//是否合格
	private String problem;//问题描述
	private String rectify;//整改方案
	private String isCompelete;//是否完工
	private Timestamp completeDate;//完工时间
    private String state;//返工状态
    private String isDel;//是否删除
	private Admin duty;//责任人
	private Admin confirmUser;//确认人
    private Admin createUser;//创建人
    private Admin modifyUser;//修改人
    
    private String xduty;//责任人名
    private String xcreateUser;//创建人名
    private String xconfirmUser;//确认人名
    private String xmodifyUser;//修改人名
    
    
    private String stateRemark;//返工状态描述
    private String isQualifieds;//是否合格描述
    private String isCompeletes;//是否完工描述
   
  
    private WorkingBill workingbill;//随工单
    
    private String productsCode;//产品编码
    private String productsName;//产品名称
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
    
	public Integer getReworkCount() {
		return reworkCount;
	}
	public void setReworkCount(Integer reworkCount) {
		this.reworkCount = reworkCount;
	}
	public Integer getReworkAmount() {
		return reworkAmount;
	}
	public void setReworkAmount(Integer reworkAmount) {
		this.reworkAmount = reworkAmount;
	}
	public Integer getDefectAmount() {
		return defectAmount;
	}
	public void setDefectAmount(Integer defectAmount) {
		this.defectAmount = defectAmount;
	}
	public String getIsQualified() {
		return isQualified;
	}
	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getRectify() {
		return rectify;
	}
	public void setRectify(String rectify) {
		this.rectify = rectify;
	}
	public String getIsCompelete() {
		return isCompelete;
	}
	public void setIsCompelete(String isCompelete) {
		this.isCompelete = isCompelete;
	}
	public Timestamp getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Timestamp completeDate) {
		this.completeDate = completeDate;
	}
	


	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(state==null){
			state="1";
		}
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
	
	 @Transient
	public String getIsQualifieds() {
		return isQualifieds;
	}
	public void setIsQualifieds(String isQualifieds) {
		this.isQualifieds = isQualifieds;
	}
	
	@Transient
	public String getIsCompeletes() {
		return isCompeletes;
	}
	public void setIsCompeletes(String isCompeletes) {
		this.isCompeletes = isCompeletes;
	}

	@Transient
	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	@Transient
	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}
	@Transient
	public String getXduty() {
		return xduty;
	}

	public void setXduty(String xduty) {
		this.xduty = xduty;
	}
	@Transient
	public String getXcreateUser() {
		return xcreateUser;
	}

	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}
	@Transient
	public String getXconfirmUser() {
		return xconfirmUser;
	}

	public void setXconfirmUser(String xconfirmUser) {
		this.xconfirmUser = xconfirmUser;
	}
	@Transient
	public String getXmodifyUser() {
		return xmodifyUser;
	}

	public void setXmodifyUser(String xmodifyUser) {
		this.xmodifyUser = xmodifyUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getDuty() {
		return duty;
	}

	public void setDuty(Admin duty) {
		this.duty = duty;
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
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Admin modifyUser) {
		this.modifyUser = modifyUser;
	}

//------------


    
   
	
}
