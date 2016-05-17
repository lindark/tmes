package cc.jiuyi.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.compass.annotations.Searchable;

@Entity
//@Searchable
public class DumpDetail extends BaseEntity {
	
	private static final long serialVersionUID = -98273463438281L;
	private String voucherId;// 物料凭证号
	private String mjahr;// 凭证年度
	private String zeile;// 行项目
	private String matnr;// 物料编码
	private String maktx;// 物料描述
	private String werks;// 工厂
	private String lgort;// 库存地点
	private String charg;// 批号
	private String meins;// 基本单位
	private String menge;// 数量
	private Dump dump;// 转储单
	private String move_type;//移动类型
	
	private Date deliveryDate;// 过账日期
	private String deliveryTime;// 过账时间
	
	private String state;// 状态
	private String stateRemark;// 状态描述
	private String adminName;// 确认人的名字
	
	private String psaddress;//配送库存地点编码
	private String lgpla;//仓位//配送库存地点仓位编码
	private String verme;//库存数量
	private String jsaddress;//接收地点编码
	
	private String mantd;//客户端编号
	private String rowno;//存放ID
	private String lgtyp;//仓储类型
	private String lqnum;//数量
	private String lenum;//仓储单位编号
	private String sequ;//整数
	private String nlpla;//目的地仓位
	private String isDone;//是否已经从中转仓计算过

	public String getMenge() {
		return menge;
	}

	public void setMenge(String menge) {
		this.menge = menge;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getMjahr() {
		return mjahr;
	}

	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}

	public String getZeile() {
		return zeile;
	}

	public void setZeile(String zeile) {
		this.zeile = zeile;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
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

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Dump getDump() {
		return dump;
	}

	public void setDump(Dump dump) {
		this.dump = dump;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateRemark() {
		return stateRemark;
	}

	public void setStateRemark(String stateRemark) {
		this.stateRemark = stateRemark;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPsaddress()
	{
		return psaddress;
	}

	public void setPsaddress(String psaddress)
	{
		this.psaddress = psaddress;
	}

	public String getLgpla()
	{
		return lgpla;
	}

	public void setLgpla(String lgpla)
	{
		this.lgpla = lgpla;
	}

	public String getVerme()
	{
		return verme;
	}

	public void setVerme(String verme)
	{
		this.verme = verme;
	}

	public String getMantd()
	{
		return mantd;
	}

	public void setMantd(String mantd)
	{
		this.mantd = mantd;
	}

	public String getRowno()
	{
		return rowno;
	}

	public void setRowno(String rowno)
	{
		this.rowno = rowno;
	}

	public String getLgtyp()
	{
		return lgtyp;
	}

	public void setLgtyp(String lgtyp)
	{
		this.lgtyp = lgtyp;
	}

	public String getLenum()
	{
		return lenum;
	}

	public void setLenum(String lenum)
	{
		this.lenum = lenum;
	}

	public String getSequ()
	{
		return sequ;
	}

	public void setSequ(String sequ)
	{
		this.sequ = sequ;
	}

	public String getNlpla()
	{
		return nlpla;
	}

	public void setNlpla(String nlpla)
	{
		this.nlpla = nlpla;
	}

	public String getMove_type()
	{
		return move_type;
	}

	public void setMove_type(String move_type)
	{
		this.move_type = move_type;
	}

	public String getJsaddress()
	{
		return jsaddress;
	}

	public void setJsaddress(String jsaddress)
	{
		this.jsaddress = jsaddress;
	}

	public String getLqnum()
	{
		return lqnum;
	}

	public void setLqnum(String lqnum)
	{
		this.lqnum = lqnum;
	}

	public String getIsDone()
	{
		return isDone;
	}

	public void setIsDone(String isDone)
	{
		this.isDone = isDone;
	}

}
