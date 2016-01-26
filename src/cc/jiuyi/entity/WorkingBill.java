package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 随工单
 */

@Entity
public class WorkingBill extends BaseEntity {

	private static final long serialVersionUID = 2547319998033961001L;

	private String workingBillCode;// 随工单编号
	private String productDate;// 生产日期
	private Integer planCount;// 计划数量
	private String isdel;// 是否删除
	private String matnr;// 物料号
	private String maktx;// 物料 描述
	private Integer cartonTotalAmount;// 纸箱累计收货数量
	private Double dailyWorkTotalAmount;// 累计报工数量
	private Double totalSingleAmount;// 累计入库根量
	private Integer totalRepairinAmount;// 累计返修收货数量
	private Integer totalRepairAmount;// 累计返修数量
	private String werks;//工厂
	private Double amount;//交接数量
	private Double repairamount;//返修交接数量
	private Double afteroddamount;//接上班正常零头数
	private Double afterunoddamount;//接上班异常零头数
	private Double beforeoddamount;//交下班正常
	private Double beforeunoddamount;//交下班异常
	private Integer bomversion;//BOM版本
	private Integer processversion;//工艺路线版本
	private String aufnr;//订单号
	private String workcenter;//工作中心
	private String checknum1;//检验合格数1
	private String checknum2;//检验合格数2
	private String checknum3;//检验合格数3
	private String checknum4;//检验合格数4
	private String checknum5;//检验合格数5
	private String isHand;//是否交接完成 Y为交接完成，N为未交接完成
	
	private Set<WorkingInout> workingInoutSet;//投入产出表

	private Set<EnteringwareHouse> enteringwareHouse;
	private Set<Rework> rework;//返工
    private Set<DailyWork> dailyWork;
    private Set<Repair> repair;
    private Set<Repairin> repairin;
    private Set<HandOverProcess> afterhandoverprocessSet;//交下班
    private Set<HandOverProcess> beforhandoverprocessSet;//接上班
    private Set<Pollingtest> pollingtest;//巡检
    private Set<Sample>sample;//抽检
    private Set<Scrap>scrap;//报废
    
    private Set<Pick> pick;//领料
    private Set<ItermediateTest> itermediateTest;//半成品巡检
    private Set<OddHandOver> oddHandOverSet;//零头数交接
    private Set<PumPackHandOver> pumPackHandOverSet;//抽包异常交接
    
    /*冗余*/
    private String afterworkingBillCode;//下一随工单
    /*冗余end*/
    
    
   
    @OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
    public Set<ItermediateTest> getItermediateTest() {
		return itermediateTest;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="workingbill")
	public Set<WorkingInout> getWorkingInoutSet() {
		return workingInoutSet;
	}

	public void setWorkingInoutSet(Set<WorkingInout> workingInoutSet) {
		this.workingInoutSet = workingInoutSet;
	}

	public void setItermediateTest(Set<ItermediateTest> itermediateTest) {
		this.itermediateTest = itermediateTest;
	}
	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
    public Set<Pick> getPick() {
		return pick;
	}

	public void setPick(Set<Pick> pick) {
		this.pick = pick;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
    public Set<Repairin> getRepairin() {
		return repairin;
	}

	public void setRepairin(Set<Repairin> repairin) {
		this.repairin = repairin;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
    public Set<Repair> getRepair() {
		return repair;
	}

	public void setRepair(Set<Repair> repair) {
		this.repair = repair;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
	public Set<DailyWork> getDailyWork() {
		return dailyWork;
	}

	public void setDailyWork(Set<DailyWork> dailyWork) {
		this.dailyWork = dailyWork;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
	public Set<Rework> getRework() {
		return rework;
	}

	public void setRework(Set<Rework> rework) {
		this.rework = rework;
	}

	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
	public Set<EnteringwareHouse> getEnteringwareHouse() {
		return enteringwareHouse;
	}

	public void setEnteringwareHouse(Set<EnteringwareHouse> enteringwareHouse) {
		this.enteringwareHouse = enteringwareHouse;
	}
	
	@OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
	public Set<Pollingtest> getPollingtest() {
		return pollingtest;
	}

	public void setPollingtest(Set<Pollingtest> pollingtest) {
		this.pollingtest = pollingtest;
	}

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public Integer getPlanCount() {
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	// @Column(name = "isdel", columnDefinition = "enum default N")
	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		if (isdel == null)
			this.isdel = "N";
		else
			this.isdel = isdel;
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

	public Integer getCartonTotalAmount() {
		return cartonTotalAmount;
	}

	public void setCartonTotalAmount(Integer cartonTotalAmount) {
		if (cartonTotalAmount == null) {
			cartonTotalAmount = 0;
		}
		this.cartonTotalAmount = cartonTotalAmount;
	}

	public Double getDailyWorkTotalAmount() {
		return dailyWorkTotalAmount;
	}

	public void setDailyWorkTotalAmount(Double dailyWorkTotalAmount) {
		if (dailyWorkTotalAmount == null) {
			dailyWorkTotalAmount = 0.0;
		}
		this.dailyWorkTotalAmount = dailyWorkTotalAmount;
	}

	public Double getTotalSingleAmount() {
		return totalSingleAmount;
	}

	public void setTotalSingleAmount(Double totalSingleAmount) {
		if (totalSingleAmount == null) {
			totalSingleAmount = 0d;
		}
		this.totalSingleAmount = totalSingleAmount;
	}

	public Integer getTotalRepairinAmount() {
		return totalRepairinAmount;
	}

	public void setTotalRepairinAmount(Integer totalRepairinAmount) {
		if (totalRepairinAmount == null) {
			totalRepairinAmount = 0;
		}
		this.totalRepairinAmount = totalRepairinAmount;
	}

	public Integer getTotalRepairAmount() {
		return totalRepairAmount;
	}

	public void setTotalRepairAmount(Integer totalRepairAmount) {
		if (totalRepairAmount == null) {
			totalRepairAmount = 0;
		}
		this.totalRepairAmount = totalRepairAmount;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="afterworkingbill")
	public Set<HandOverProcess> getAfterhandoverprocessSet() {
		return afterhandoverprocessSet;
	}

	public void setAfterhandoverprocessSet(
			Set<HandOverProcess> afterhandoverprocessSet) {
		this.afterhandoverprocessSet = afterhandoverprocessSet;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="beforworkingbill")
	public Set<HandOverProcess> getBeforhandoverprocessSet() {
		return beforhandoverprocessSet;
	}

	public void setBeforhandoverprocessSet(
			Set<HandOverProcess> beforhandoverprocessSet) {
		this.beforhandoverprocessSet = beforhandoverprocessSet;
	}

	@Transient
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="workingBill")
	public Set<Sample> getSample()
	{
		return sample;
	}

	public void setSample(Set<Sample> sample)
	{
		this.sample = sample;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="workingBill")
	public Set<Scrap> getScrap()
	{
		return scrap;
	}

	public void setScrap(Set<Scrap> scrap)
	{
		this.scrap = scrap;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	@Transient
	public String getAfterworkingBillCode() {
		return afterworkingBillCode;
	}

	public void setAfterworkingBillCode(String afterworkingBillCode) {
		this.afterworkingBillCode = afterworkingBillCode;
	}

	public Integer getBomversion() {
		return bomversion;
	}

	public void setBomversion(Integer bomversion) {
		this.bomversion = bomversion;
	}

	public Integer getProcessversion() {
		return processversion;
	}

	public void setProcessversion(Integer processversion) {
		this.processversion = processversion;
	}

	public Double getRepairamount() {
		return repairamount;
	}

	public void setRepairamount(Double repairamount) {
		this.repairamount = repairamount;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	@OneToMany(mappedBy = "workingBill", fetch = FetchType.LAZY)
	public Set<OddHandOver> getOddHandOverSet() {
		return oddHandOverSet;
	}

	public void setOddHandOverSet(Set<OddHandOver> oddHandOverSet) {
		this.oddHandOverSet = oddHandOverSet;
	}
	
	@OneToMany(mappedBy = "workingBill", fetch = FetchType.LAZY)
	public Set<PumPackHandOver> getPumPackHandOverSet() {
		return pumPackHandOverSet;
	}

	public void setPumPackHandOverSet(Set<PumPackHandOver> pumPackHandOverSet) {
		this.pumPackHandOverSet = pumPackHandOverSet;
	}
	public String getChecknum1() {
		return checknum1;
	}
	public void setChecknum1(String checknum1) {
		this.checknum1 = checknum1;
	}
	public String getChecknum2() {
		return checknum2;
	}
	public void setChecknum2(String checknum2) {
		this.checknum2 = checknum2;
	}
	public String getChecknum3() {
		return checknum3;
	}
	public void setChecknum3(String checknum3) {
		this.checknum3 = checknum3;
	}
	public String getChecknum4() {
		return checknum4;
	}
	public void setChecknum4(String checknum4) {
		this.checknum4 = checknum4;
	}
	public String getChecknum5() {
		return checknum5;
	}
	public void setChecknum5(String checknum5) {
		this.checknum5 = checknum5;
	}
	public Double getAfteroddamount() {
		return afteroddamount;
	}
	public void setAfteroddamount(Double afteroddamount) {
		this.afteroddamount = afteroddamount;
	}
	public Double getAfterunoddamount() {
		return afterunoddamount;
	}
	public void setAfterunoddamount(Double afterunoddamount) {
		this.afterunoddamount = afterunoddamount;
	}
	public Double getBeforeoddamount() {
		return beforeoddamount;
	}
	public void setBeforeoddamount(Double beforeoddamount) {
		this.beforeoddamount = beforeoddamount;
	}
	public Double getBeforeunoddamount() {
		return beforeunoddamount;
	}
	public void setBeforeunoddamount(Double beforeunoddamount) {
		this.beforeunoddamount = beforeunoddamount;
	}
	public String getIsHand() {
		return isHand;
	}
	public void setIsHand(String isHand) {
		if(isHand == null)
			isHand="N";
		this.isHand = isHand;
	}

}