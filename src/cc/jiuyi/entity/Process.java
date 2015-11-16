package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 工序
 * @param args
 */


@Entity
@Searchable
@Table(name = "Process")
public class Process extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String processCode;//工序编码
    private String processName;//工序名称
    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    
    private WorkingBill workingBill;//随工单
    
    private String xproductnum;//产品编码
    private String xproductname;//产品名称
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
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
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingBill()
	{
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill)
	{
		this.workingBill = workingBill;
	}
	@Transient
	public String getXproductnum()
	{
		return xproductnum;
	}
	public void setXproductnum(String xproductnum)
	{
		this.xproductnum = xproductnum;
	}
	@Transient
	public String getXproductname()
	{
		return xproductname;
	}
	public void setXproductname(String xproductname)
	{
		this.xproductname = xproductname;
	}

   

    
	
}
