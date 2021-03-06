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
 * @param
 */


@Entity
//@Searchable
@Table(name = "Pick")
public class Pick extends BaseEntity{

	private static final long serialVersionUID = 1219337885128932022L;
	

    private String state;//状态
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Admin createUser;
    private Admin modifyUser;
    private Admin confirmUser;
    private String pickType;
	private Set<PickDetail> pickDetail;//领料从表
	
	private WorkingBill workingbill;//随工单
	private String xconfirmUser;//确认人名
	private String xcreateUser;// 创建人
	private String move_type;//移动类型
	private String budat;//过账日期
	private String werks;//工厂
	private String lgort;//库存地点
	private String ztext;//抬头文本
	private String xuh;//序号
	private String e_type;
	private String e_message;
	private String ex_mblnr;
	private String mblnr;
	private String revokedUser;//撤销人
	private String revokedUserId;//撤销人
	private String revokedUserCard;//撤销人卡号
	private String revokedTime;//撤销时间
	
	private String maktx;//产品描述
	private String workingbillCode;//随工单编号

	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}
	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	
	
	public String getXuh() {
		return xuh;
	}
	public void setXuh(String xuh) {
		this.xuh = xuh;
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

	@ManyToOne(fetch = FetchType.LAZY)
    public Admin getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(Admin modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	public String getE_type() {
		return e_type;
	}
	public void setE_type(String e_type) {
		this.e_type = e_type;
	}
	public String getE_message() {
		return e_message;
	}
	public void setE_message(String e_message) {
		this.e_message = e_message;
	}
	public String getEx_mblnr() {
		return ex_mblnr;
	}
	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
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
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
	}
	
	@Transient
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	@Transient
	public String getWorkingbillCode() {
		return workingbillCode;
	}
	public void setWorkingbillCode(String workingbillCode) {
		this.workingbillCode = workingbillCode;
	}
	public String getRevokedUser() {
		return revokedUser;
	}
	public void setRevokedUser(String revokedUser) {
		this.revokedUser = revokedUser;
	}
	public String getRevokedUserId() {
		return revokedUserId;
	}
	public void setRevokedUserId(String revokedUserId) {
		this.revokedUserId = revokedUserId;
	}
	public String getRevokedUserCard() {
		return revokedUserCard;
	}
	public void setRevokedUserCard(String revokedUserCard) {
		this.revokedUserCard = revokedUserCard;
	}
	public String getRevokedTime() {
		return revokedTime;
	}
	public void setRevokedTime(String revokedTime) {
		this.revokedTime = revokedTime;
	}
	
	
}
