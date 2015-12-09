package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 半成品巡检主表
 * @param args
 */


@Entity
@Table(name = "ItermediateTest")
public class ItermediateTest extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2957823789649683279L;
	
	private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Admin createUser;
    private Admin confirmUser;
	private Set<ItermediateTestDetail> itermediateTestDetail;//领料从表
	
	private WorkingBill workingbill;//随工单
	private String xconfirmUser;//确认人名
	private String xcreateUser;// 创建人
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}
	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    public Admin getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Admin createUser) {
		this.createUser = createUser;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}

	
    @OneToMany(mappedBy = "itermediateTest", fetch = FetchType.LAZY)
	public Set<ItermediateTestDetail> getItermediateTestDetail() {
		return itermediateTestDetail;
	}
	public void setItermediateTestDetail(
			Set<ItermediateTestDetail> itermediateTestDetail) {
		this.itermediateTestDetail = itermediateTestDetail;
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
	public String getXconfirmUser() {
		return xconfirmUser;
	}
	public void setXconfirmUser(String xconfirmUser) {
		this.xconfirmUser = xconfirmUser;
	}
	
	@Transient
	public String getXcreateUser() {
		return xcreateUser;
	}
	public void setXcreateUser(String xcreateUser) {
		this.xcreateUser = xcreateUser;
	}

   

    
	
}
