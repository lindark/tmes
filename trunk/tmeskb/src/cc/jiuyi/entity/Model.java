package cc.jiuyi.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类 - 工模维修单
 */
@Entity
@Table(name = "jigfix")
public class Model extends BaseEntity{

	private static final long serialVersionUID = -7213423823153832326L;
	
	private String productCode;//产品编号
	private String productName;//产品名称
	private String type;//种类
	private String teamId;//班组
	private String initiator;//提报人
	private String failDescript;//不良现象描述
	private Integer defaltNo;//缺陷数量
	private String insepector;//检验员
	private String fixer;//维修人员
	
	private Date confirmTime;//确认时间
	private Date arriveTime;//到场时间
	private Date noticeTime;//通知时间
	private Integer fixTime;//维修时间
	private String faultCause;//故障原因
	private String resolve;//处理方法及结果
	private String measure;//预防措施
	
	private String createUser;//创建人
	private String modifyUser;//修改人
	private String isDel;//是否删除
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getFailDescript() {
		return failDescript;
	}
	public void setFailDescript(String failDescript) {
		this.failDescript = failDescript;
	}
	public Integer getDefaltNo() {
		return defaltNo;
	}
	public void setDefaltNo(Integer defaltNo) {
		this.defaltNo = defaltNo;
	}
	public String getInsepector() {
		return insepector;
	}
	public void setInsepector(String insepector) {
		this.insepector = insepector;
	}
	public String getFixer() {
		return fixer;
	}
	public void setFixer(String fixer) {
		this.fixer = fixer;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	public Integer getFixTime() {
		return fixTime;
	}
	public void setFixTime(Integer fixTime) {
		this.fixTime = fixTime;
	}
	public String getFaultCause() {
		return faultCause;
	}
	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
	}
	public String getResolve() {
		return resolve;
	}
	public void setResolve(String resolve) {
		this.resolve = resolve;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
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
