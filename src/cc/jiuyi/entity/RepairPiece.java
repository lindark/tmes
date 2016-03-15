package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 返修--组件
 * @author lenovo
 *
 */
@Entity
public class RepairPiece extends BaseEntity
{
	private static final long serialVersionUID = 369610835326079316L;
	
	private String rpcode;//组件编码
	private String rpname;//组件名称
	private Double productnum;//产品数量
	private Double piecenum;//组件数量
	private String rpcount;//组件总数量
	private String ITEM_TEXT;//项目文本
	private Repair repair;//主表
	
	//冗余字段
	private String state;
	private String stateRemark;
	private String confirmUser;
	private String createName;
	private String workingbillCode;
	private String productDate;
    private String maktx;
    private String repairPart;
    private Integer repairAmount;
    private String duty;
    private String mblnr;
    
	
	
	public String getRpcode()
	{
		return rpcode;
	}
	public void setRpcode(String rpcode)
	{
		this.rpcode = rpcode;
	}
	public String getRpname()
	{
		return rpname;
	}
	public void setRpname(String rpname)
	{
		this.rpname = rpname;
	}
	public String getRpcount()
	{
		return rpcount;
	}
	public void setRpcount(String rpcount)
	{
		this.rpcount = rpcount;
	}
	public String getITEM_TEXT()
	{
		return ITEM_TEXT;
	}
	public void setITEM_TEXT(String iTEM_TEXT)
	{
		ITEM_TEXT = iTEM_TEXT;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Repair getRepair()
	{
		return repair;
	}
	public void setRepair(Repair repair)
	{
		this.repair = repair;
	}
	public Double getProductnum()
	{
		return productnum;
	}
	public void setProductnum(Double productnum)
	{
		this.productnum = productnum;
	}
	public Double getPiecenum()
	{
		return piecenum;
	}
	public void setPiecenum(Double piecenum)
	{
		this.piecenum = piecenum;
	}
	
	@Transient
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	@Transient
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	@Transient
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
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
	@Transient
	public String getRepairPart() {
		return repairPart;
	}
	public void setRepairPart(String repairPart) {
		this.repairPart = repairPart;
	}
	@Transient
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	@Transient
	public Integer getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(Integer repairAmount) {
		this.repairAmount = repairAmount;
	}
	
	@Transient
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	@Transient
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	
	
	
	
	
	
	
}
