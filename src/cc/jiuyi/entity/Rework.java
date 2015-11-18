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
	private String duty;//责任人
	private String confirmUer;//确认人
    private String state;//返工状态
    private String isDel;//是否删除
    
    
    private String stateRemark;//返工状态描述
    private String isQualifieds;//是否合格描述
    private String isCompeletes;//是否完工描述
  
    private WorkingBill workingbill;//随工单
    private Admin admin;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
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
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getConfirmUer() {
		return confirmUer;
	}
	public void setConfirmUer(String confirmUer) {
		this.confirmUer = confirmUer;
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


    
   
	
}
