package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 报工
 */

@Entity
@Searchable
@Table(name = "DailyWork")
public class DailyWork extends BaseEntity {

	private static final long serialVersionUID = 8446572478950753825L;
	private double enterAmount;// 报工箱数
	private String State;// 状态
	private String isDel;// 是否删除
	private Admin confirmUser;// 确认人
	private Admin createUser;// 创建人
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	private String createName;// 创建人的名字
	private String step;
	private String orderid;
	private WorkingBill workingbill;// 随工单
	private String e_type;// S/E
	private String e_message;// 反馈消息
	private String responseName;// 责任工序名称
	private String CONF_NO;//确认号
	private String CONF_CNT;//计数器
	private String wb;//文本
	private String workingbillCode;
	private String maktx;//产品描述
	private String processCode;//工序编码
	private String moudle;//模具
	
	private String productDate;//生产日期
	private String matnr;//产品编码
	private String xmoudle;//模具描述
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		if (state == null) {
			state = "2";
		}
		State = state;
	}

	public Double getEnterAmount() {
		return enterAmount;
	}

	public void setEnterAmount(Double enterAmount) {
		this.enterAmount = enterAmount;
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

	@Transient
	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Transient
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public String getWb() {
		return wb;
	}

	public void setWb(String wb) {
		this.wb = wb;
	}


	@Transient
	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public String getCONF_NO() {
		return CONF_NO;
	}

	public void setCONF_NO(String conf_no) {
		CONF_NO = conf_no;
	}

	public String getCONF_CNT() {
		return CONF_CNT;
	}

	public void setCONF_CNT(String conf_cnt) {
		CONF_CNT = conf_cnt;
	}

	@Transient
	public String getWorkingbillCode() {
		return workingbillCode;
	}

	public void setWorkingbillCode(String workingbillCode) {
		this.workingbillCode = workingbillCode;
	}

	@Transient
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	@Transient
	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	@Transient
	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	@Transient
	public String getXmoudle() {
		return xmoudle;
	}

	public void setXmoudle(String xmoudle) {
		this.xmoudle = xmoudle;
	}

	
}