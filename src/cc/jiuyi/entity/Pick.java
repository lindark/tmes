package cc.jiuyi.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 领料/退料主表
 * @param args
 */


@Entity
@Table(name = "Pick")
public class Pick extends BaseEntity{

	private static final long serialVersionUID = 6541260331669237601L;
	private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Admin createUser;
    private Admin confirmUser;
	private Set<PickDetail> pickDetail;//领料从表
	
	private WorkingBill workingbill;//随工单
	private String xconfirmUser;//确认人名
	private String xcreateUser;// 创建人
	private String move_type;//移动类型
	private String budat;//过账日期
	private String werks;//工厂
	private String lgort;//库存地点
	private String ztext;//抬头文本
	private String mblnr;//rfc返回值
	
	
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

    
    
    @OneToMany(mappedBy = "pick", fetch = FetchType.LAZY)
	public Set<PickDetail> getPickDetail() {
		return pickDetail;
	}
	public void setPickDetail(Set<PickDetail> pickDetail) {
		this.pickDetail = pickDetail;
	}
	
	
	
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getZtext() {
		return ztext;
	}
	public void setZtext(String ztext) {
		this.ztext = ztext;
	}
	public String getMove_type() {
		return move_type;
	}
	public void setMove_type(String move_type) {
		this.move_type = move_type;
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

   
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}

    
	
}
