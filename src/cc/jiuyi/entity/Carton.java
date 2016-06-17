package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类——纸箱
 * 
 */
@Entity
public class Carton extends BaseEntity {

	private static final long serialVersionUID = -8927337834611798560L;
	private Integer cartonAmount;// 纸箱总数量
	private Admin createUser;//创建人
	private Admin confirmUser;// 确认人
	private String state;// 状态
	private String E_TYPE;//S/N
	private String E_MESSAGE;//付款信息
	private String EX_MBLNR;//物料凭证
	//private String EX_EBELN;//采购订单号
	//private String ET_EBELP;//采购订单行项目号
	private Set<CartonSon>cartonsonSet;//纸箱收货-子表
	private String teamshift;//班次
	private String productDate;//生产日期
	private FactoryUnit factoryUnit;//单元    jjt
	private String bktxt;//单据编号
	private String revokedUser;//撤销人
	private String revokedUserId;//撤销人
	private String revokedUserCard;//撤销人卡号
	private String revokedTime;//撤销时间
	
	//假字段
	private String xcreateUser;//创建人的名字
	private String xconfirmUser;//确认人的名字
	private String xstate;//状态描述
	private String xteamshift;//班次
	
	public Integer getCartonAmount() {
		return cartonAmount;
	}

	public void setCartonAmount(Integer cartonAmount) {
		if(cartonAmount==null)
		{
			cartonAmount=0;
		}
		this.cartonAmount = cartonAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	@OneToMany(fetch=FetchType.LAZY,mappedBy="carton")
	public Set<CartonSon> getCartonsonSet()
	{
		return cartonsonSet;
	}

	public void setCartonsonSet(Set<CartonSon> cartonsonSet)
	{
		this.cartonsonSet = cartonsonSet;
	}

	@Transient
	public String getXconfirmUser()
	{
		return xconfirmUser;
	}

	public void setXconfirmUser(String xconfirmUser)
	{
		this.xconfirmUser = xconfirmUser;
	}

	@Transient
	public String getXcreateUser()
	{
		return xcreateUser;
	}

	public void setXcreateUser(String xcreateUser)
	{
		this.xcreateUser = xcreateUser;
	}

	@Transient
	public String getXstate()
	{
		return xstate;
	}

	public void setXstate(String xstate)
	{
		this.xstate = xstate;
	}

	public String getE_TYPE()
	{
		return E_TYPE;
	}

	public void setE_TYPE(String e_TYPE)
	{
		E_TYPE = e_TYPE;
	}

	public String getE_MESSAGE()
	{
		return E_MESSAGE;
	}

	public void setE_MESSAGE(String e_MESSAGE)
	{
		E_MESSAGE = e_MESSAGE;
	}

	public String getEX_MBLNR()
	{
		return EX_MBLNR;
	}

	public void setEX_MBLNR(String eX_MBLNR)
	{
		EX_MBLNR = eX_MBLNR;
	}

	public String getProductDate()
	{
		return productDate;
	}

	public void setProductDate(String productDate)
	{
		this.productDate = productDate;
	}

	public String getTeamshift()
	{
		return teamshift;
	}

	public void setTeamshift(String teamshift)
	{
		this.teamshift = teamshift;
	}

	public String getBktxt() {
		return bktxt;
	}

	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}

	@Transient
	public String getXteamshift()
	{
		return xteamshift;
	}

	public void setXteamshift(String xteamshift)
	{
		this.xteamshift = xteamshift;
	}


	/*public String getEX_EBELN() {
		return EX_EBELN;
	}

	public void setEX_EBELN(String eX_EBELN) {
		EX_EBELN = eX_EBELN;
	}

	public String getET_EBELP() {
		return ET_EBELP;
	}

	public void setET_EBELP(String eT_EBELP) {
		ET_EBELP = eT_EBELP;
	}*/
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	public FactoryUnit getFactoryUnit() {
		return factoryUnit;
	}
	public void setFactoryUnit(FactoryUnit factoryUnit) {
		this.factoryUnit = factoryUnit;
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
