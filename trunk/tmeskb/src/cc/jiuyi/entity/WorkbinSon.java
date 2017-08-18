package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 纸箱收货子表
 * @author lenovo
 *
 */
@Entity
public class WorkbinSon extends BaseEntity
{

	private static final long serialVersionUID = -3497011097392288625L;

	
	private String werks;//工厂
	private String shipmentsDate;//发货日期
	private String jslgort;//库存地点
	private String jslgortDesc;//库存地点
	private String matnr;//物料编码
	private String matnrdesc;//物料描述
	private String wscount;//料箱数量
	
	private Workbin workbin;//纸箱收货-主表
	
	private String LIFNR;//供应商
	private String BUDAT;//过账日期
	private String wbid;//随工单ID
	private String wbcode;//随工单编码
	private String productcode;//产品编码
	private String productname;//产品名称
	private String EBELN;//采购订单号
	private String EBELP;//采购订单行项目号
	private String bktxt;//交货单号
	
	
	//假字段
	
	private String MOVE_TYPE;//移动类型
	private String xcstotal;//累计数量
	private String state;//状态
	private String stateRemark;//状态描述
	private String confirmUser;//确认人
	private String createUser;//创建人
	private String mblnr;//物料凭证号
	private String productDate;//生产日期
	private String xstate;// 状态
	
	private String xteamshift;//班次
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Workbin getWorkbin() {
		return workbin;
	}
	public void setWorkbin(Workbin workbin) {
		this.workbin = workbin;
	}
	
	public String getLIFNR()
	{
		return LIFNR;
	}
	public void setLIFNR(String lIFNR)
	{
		LIFNR = lIFNR;
	}
	
	@Transient
	public String getMOVE_TYPE()
	{
		return MOVE_TYPE;
	}
	public void setMOVE_TYPE(String mOVE_TYPE)
	{
		MOVE_TYPE = mOVE_TYPE;
	}
	public String getBUDAT()
	{
		return BUDAT;
	}
	public void setBUDAT(String bUDAT)
	{
		BUDAT = bUDAT;
	}
	public String getWbid()
	{
		return wbid;
	}
	public void setWbid(String wbid)
	{
		this.wbid = wbid;
	}
	@Transient
	public String getXcstotal()
	{
		return xcstotal;
	}
	public void setXcstotal(String xcstotal)
	{
		this.xcstotal = xcstotal;
	}
	public String getWbcode()
	{
		return wbcode;
	}
	public void setWbcode(String wbcode)
	{
		this.wbcode = wbcode;
	}
	public String getProductcode()
	{
		return productcode;
	}
	public void setProductcode(String productcode)
	{
		this.productcode = productcode;
	}
	public String getProductname()
	{
		return productname;
	}
	public void setProductname(String productname)
	{
		this.productname = productname;
	}
	
	@Transient
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Transient
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	
	
	@Transient
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Transient
	public String getStateRemark() {
		return stateRemark;
	}
	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}
	
	@Transient
	public String getMblnr() {
		return mblnr;
	}
	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}
	
	public String getBktxt() {
		return bktxt;
	}
	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	
	
	@Transient
	public String getXteamshift() {
		return xteamshift;
	}
	public void setXteamshift(String xteamshift) {
		this.xteamshift = xteamshift;
	}
	public String getEBELN() {
		return EBELN;
	}
	public void setEBELN(String eBELN) {
		EBELN = eBELN;
	}
	public String getEBELP() {
		return EBELP;
	}
	public void setEBELP(String eBELP) {
		EBELP = eBELP;
	}
	public String getShipmentsDate() {
		return shipmentsDate;
	}
	public void setShipmentsDate(String shipmentsDate) {
		this.shipmentsDate = shipmentsDate;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getJslgort() {
		return jslgort;
	}
	public void setJslgort(String jslgort) {
		this.jslgort = jslgort;
	}
	public String getJslgortDesc() {
		return jslgortDesc;
	}
	public void setJslgortDesc(String jslgortDesc) {
		this.jslgortDesc = jslgortDesc;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMatnrdesc() {
		return matnrdesc;
	}
	public void setMatnrdesc(String matnrdesc) {
		this.matnrdesc = matnrdesc;
	}
	public String getWscount() {
		return wscount;
	}
	public void setWscount(String wscount) {
		this.wscount = wscount;
	}
	@Transient
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	
	@Transient
	public String getXstate() {
		return xstate;
	}
	public void setXstate(String xstate) {
		this.xstate = xstate;
	}
	

	
}
