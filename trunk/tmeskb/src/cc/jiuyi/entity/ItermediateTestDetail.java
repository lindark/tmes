package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;

/**
 * 实体类 - 半成品巡检从表
 * @param args
 */


@Entity
@Table(name = "ItermediateTestDetail")
public class ItermediateTestDetail extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 845139855839428494L;
	
	private String materialCode;//组件编码
	private String materialName;//组件名称
	private Double testAmount;//抽检数量
    private Double passAmount;//合格数量
    private String failReason;//不合格原因
    private Double failAmount;//不合格数量
    private String goodsSzie1;//尺寸1
    private String goodsSzie2;//尺寸2
    private String goodsSzie3;//尺寸3
    private String goodsSzie4;//尺寸4
    private String goodsSzie5;//尺寸5
    private String isDel;//是否删除
    private String stateRemark;//状态描述
    private Admin confirmUser;//确认人
    
    /*
     * 假字段
     */
    private String xconfirmUser;// 确认人的名字
 

	private String xcreateUser;//创建人的名字
    
   
	
	
	
	
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
	
	private ItermediateTest itermediateTest;//半成品巡检主表
    private Set<IpRecord> ipRecord;//半成品巡检缺陷记录表
	
    
    
    @OneToMany(fetch=FetchType.LAZY,mappedBy="itermediateTestDetail")
	public Set<IpRecord> getIpRecord() {
		return ipRecord;
	}
	public void setIpRecord(Set<IpRecord> ipRecord) {
		this.ipRecord = ipRecord;
	}	

	@ManyToOne(fetch = FetchType.LAZY)
	public ItermediateTest getItermediateTest() {
		return itermediateTest;
	}
	public void setItermediateTest(ItermediateTest itermediateTest) {
		this.itermediateTest = itermediateTest;
	}
	
	
	
	public Double getTestAmount() {
		return testAmount;
	}
	public void setTestAmount(Double testAmount) {
		this.testAmount = testAmount;
	}
	public Double getPassAmount() {
		return passAmount;
	}
	public void setPassAmount(Double passAmount) {
		this.passAmount = passAmount;
	}
	public String getGoodsSzie1() {
		return goodsSzie1;
	}
	public void setGoodsSzie1(String goodsSzie1) {
		this.goodsSzie1 = goodsSzie1;
	}
	public String getGoodsSzie2() {
		return goodsSzie2;
	}
	public void setGoodsSzie2(String goodsSzie2) {
		this.goodsSzie2 = goodsSzie2;
	}
	public String getGoodsSzie3() {
		return goodsSzie3;
	}
	public void setGoodsSzie3(String goodsSzie3) {
		this.goodsSzie3 = goodsSzie3;
	}
	public String getGoodsSzie4() {
		return goodsSzie4;
	}
	public void setGoodsSzie4(String goodsSzie4) {
		this.goodsSzie4 = goodsSzie4;
	}
	public String getGoodsSzie5() {
		return goodsSzie5;
	}
	public void setGoodsSzie5(String goodsSzie5) {
		this.goodsSzie5 = goodsSzie5;
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
	
	
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Double getFailAmount() {
		return failAmount;
	}
	public void setFailAmount(Double failAmount) {
		this.failAmount = failAmount;
	}
	
	
}
