package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 实体类 - 随工单
 */

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WorkingBill extends BaseEntity {

	private static final long serialVersionUID = 2547319998033961001L;

	private String workingBillCode;// 随工单编号
	private String productDate;// 生产日期
	private Integer planCount;// 计划数量
	private String isdel;// 是否删除
	private String matnr;// 物料号
	private String maktx;// 物料 描述
	private Integer cartonTotalAmount;// 纸箱累计收货数量
	private Integer dailyWorkTotalAmount;// 累计报工数量
	private Integer totalSingleAmount;// 累计入库根量
	private Integer totalRepairinAmount;// 累计返修收货数量
	private Integer totalRepairAmount;// 累计返修数量
	
	private Integer amount;//交接数量

	private Set<EnteringwareHouse> enteringwareHouse;
	private Set<Rework> rework;//返工
    private Set<DailyWork> dailyWork;
    private Set<Carton> carton;
    private Set<Repair> repair;
    private Set<Repairin> repairin;
    private Set<HandOverProcess> afterhandoverprocessSet;//交下班
    private Set<HandOverProcess> beforhandoverprocessSet;//接上班
    private Set<Pollingtest> pollingtest;//巡检
    private Set<Sample>sample;//抽检
    private Set<Scrap>scrap;//报废
    
    private Set<Pick> pick;//领料
    private Set<ItermediateTest> itermediateTest;//半成品巡检
    
    
    
    @OneToMany(mappedBy = "workingbill", fetch = FetchType.LAZY)
    public Set<ItermediateTest> getItermediateTest() {
		return itermediateTest;
	}

	public void setItermediateTest(Set<ItermediateTest> itermediateTest) {
		this.itermediateTest = itermediateTest;
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
    public Set<Carton> getCarton() {
		return carton;
	}

	public void setCarton(Set<Carton> carton) {
		this.carton = carton;
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

	public Integer getDailyWorkTotalAmount() {
		return dailyWorkTotalAmount;
	}

	public void setDailyWorkTotalAmount(Integer dailyWorkTotalAmount) {
		if (dailyWorkTotalAmount == null) {
			dailyWorkTotalAmount = 0;
		}
		this.dailyWorkTotalAmount = dailyWorkTotalAmount;
	}

	public Integer getTotalSingleAmount() {
		return totalSingleAmount;
	}

	public void setTotalSingleAmount(Integer totalSingleAmount) {
		if (totalSingleAmount == null) {
			totalSingleAmount = 0;
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
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
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
}