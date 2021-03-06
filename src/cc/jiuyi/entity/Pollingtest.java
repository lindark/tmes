package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类——巡检
 * 
 */

@Entity
//@Searchable
public class Pollingtest extends BaseEntity {

	private static final long serialVersionUID = 8764118594876211575L;
	private Integer pollingtestAmount;// 巡检数量
	private Integer qualifiedAmount;// 合格数量
	private String passedPercent;// 合格率
	private String craftWork;// 工艺确认
	private Integer curingTime1;// 硫化时间1
	private Integer curingTime2;// 硫化时间2
	private Integer curingTime3;// 硫化时间3
	private Integer curingTime4;// 硫化时间4
	private Integer settingTime1;// 固化时间1
	private Integer settingTime2;// 固化时间2
	private Integer settingTime3;// 固化时间3
	private Integer settingTime4;// 固化时间4
	private String size1;// 尺寸1
	private String size2;// 尺寸2
	private String size3;// 尺寸3
	private Admin pollingtestUser;// 巡检人
	private Admin confirmUser;// 确认人
	private String moudle;//模具组号
	private String revokedUser;//撤销人
	private String revokedUserId;//撤销人
	private String revokedUserCard;//撤销人卡号
	private String revokedTime;//撤销时间

	private Set<PollingtestRecord> pollingtestRecord;// 巡检缺陷记录表
	private WorkingBill workingBill;// 随工单

	private String isDel;// 是否删除
	private String state;// 状态
	
	private String productDate;// 生产日期
	private String shift;// 班次
	
	//假字段
	private String craftWorkRemark;// 工艺确认描述
	private String stateRemark;// 状态描述
	private String pollingtestUserName;// 巡检人的名字
	private String confirmUserName;// 确认人的名字
	private String workingBillCode;
	private String workingBillId;
	private String maktx;//产品描述
	private String matnr;//产品编号

	public Integer getPollingtestAmount() {
		return pollingtestAmount;
	}

	public void setPollingtestAmount(Integer pollingtestAmount) {
		this.pollingtestAmount = pollingtestAmount;
	}

	public Integer getQualifiedAmount() {
		return qualifiedAmount;
	}

	public void setQualifiedAmount(Integer qualifiedAmount) {
		this.qualifiedAmount = qualifiedAmount;
	}

	public String getPassedPercent() {
		return passedPercent;
	}

	public void setPassedPercent(String passedPercent) {
		this.passedPercent = passedPercent;
	}

	public String getCraftWork() {
		return craftWork;
	}

	public void setCraftWork(String craftWork) {
		this.craftWork = craftWork;
	}

	public Integer getCuringTime1() {
		return curingTime1;
	}

	public void setCuringTime1(Integer curingTime1) {
		this.curingTime1 = curingTime1;
	}

	public Integer getCuringTime2() {
		return curingTime2;
	}

	public void setCuringTime2(Integer curingTime2) {
		this.curingTime2 = curingTime2;
	}

	public Integer getCuringTime3() {
		return curingTime3;
	}

	public void setCuringTime3(Integer curingTime3) {
		this.curingTime3 = curingTime3;
	}

	public Integer getCuringTime4() {
		return curingTime4;
	}

	public void setCuringTime4(Integer curingTime4) {
		this.curingTime4 = curingTime4;
	}

	public Integer getSettingTime1() {
		return settingTime1;
	}

	public void setSettingTime1(Integer settingTime1) {
		this.settingTime1 = settingTime1;
	}

	public Integer getSettingTime2() {
		return settingTime2;
	}

	public void setSettingTime2(Integer settingTime2) {
		this.settingTime2 = settingTime2;
	}

	public Integer getSettingTime3() {
		return settingTime3;
	}

	public void setSettingTime3(Integer settingTime3) {
		this.settingTime3 = settingTime3;
	}

	public Integer getSettingTime4() {
		return settingTime4;
	}

	public void setSettingTime4(Integer settingTime4) {
		this.settingTime4 = settingTime4;
	}

	public String getSize1() {
		return size1;
	}

	public void setSize1(String size1) {
		this.size1 = size1;
	}

	public String getSize2() {
		return size2;
	}

	public void setSize2(String size2) {
		this.size2 = size2;
	}

	public String getSize3() {
		return size3;
	}

	public void setSize3(String size3) {
		this.size3 = size3;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getPollingtestUser() {
		return pollingtestUser;
	}

	public void setPollingtestUser(Admin pollingtestUser) {
		this.pollingtestUser = pollingtestUser;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="pollingtest")
	public Set<PollingtestRecord> getPollingtestRecord() {
		return pollingtestRecord;
	}

	public void setPollingtestRecord(Set<PollingtestRecord> pollingtestRecord) {
		this.pollingtestRecord = pollingtestRecord;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingBill() {
		return workingBill;
	}

	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}
	
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getConfirmUser() {
		return confirmUser;
	}



	public void setConfirmUser(Admin confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			isDel = "N";
		}
		this.isDel = isDel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null || state.equals("")) {
			state = "2";
		}
		this.state = state;
	}
	
	
	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}

	@Transient
	public String getCraftWorkRemark() {
		return craftWorkRemark;
	}

	public void setCraftWorkRemark(String craftWorkRemark) {
		this.craftWorkRemark = craftWorkRemark;
	}

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getPollingtestUserName() {
		return pollingtestUserName;
	}

	public void setPollingtestUserName(String pollingtestUserName) {
		this.pollingtestUserName = pollingtestUserName;
	}

	@Transient
	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	@Transient
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	@Transient
	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	@Transient
	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	
	@Transient
	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
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
