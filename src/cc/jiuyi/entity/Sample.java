package cc.jiuyi.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
/**
 * 抽检
 * @author gaoyf
 *
 */
@Entity
public class Sample extends BaseEntity
{
	private static final long serialVersionUID = 9186240221551341354L;
	private String state;//状态 0未确认  1已确认
	private String sampleNum;//抽检数量
	private String qulified;//合格数量
	private String qulifiedRate;//合格率
	private String sampleType;//抽检类型
	private Admin sampler;//抽检人
	private Admin comfirmation;//确认人
	private WorkingBill workingBill;//随工单
	private String isDel;
	private Set<SampleRecord>sampleRecord;//抽检缺陷记录表
	
	//不在数据库生成的字段，用在查询页面
	private String xsampler;//抽检人
	private String xcomfirmation;//确认人
	private String xproductnum;//产品编号
	private String xproductname;//产品名称
	private String xstate;//状态描述--页面显示
	private String xsampletype;//抽检类型
	
	@Column(length=20)
	public String getState()
	{
		return state;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	@Column(length=50)
	public String getSampleNum()
	{
		return sampleNum;
	}
	
	public void setSampleNum(String sampleNum)
	{
		this.sampleNum = sampleNum;
	}
	
	@Column(length=50)
	public String getQulified()
	{
		return qulified;
	}
	
	public void setQulified(String qulified)
	{
		this.qulified = qulified;
	}
	
	@Column(length=50)
	public String getQulifiedRate()
	{
		return qulifiedRate;
	}
	
	public void setQulifiedRate(String qulifiedRate)
	{
		this.qulifiedRate = qulifiedRate;
	}
	
	@Column(length=100)
	public String getSampleType()
	{
		return sampleType;
	}
	
	public void setSampleType(String sampleType)
	{
		this.sampleType = sampleType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getSampler()
	{
		return sampler;
	}
	
	public void setSampler(Admin sampler)
	{
		this.sampler = sampler;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getComfirmation()
	{
		return comfirmation;
	}
	
	public void setComfirmation(Admin comfirmation)
	{
		this.comfirmation = comfirmation;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public WorkingBill getWorkingBill()
	{
		return workingBill;
	}
	public void setWorkingBill(WorkingBill workingBill)
	{
		this.workingBill = workingBill;
	}
	
	@Column
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		if (isDel == null) {
			this.isDel = "N";
		} else
			this.isDel = isDel;
	}
	
	@Transient
	public String getXsampler()
	{
		return xsampler;
	}
	
	public void setXsampler(String xsampler)
	{
		this.xsampler = xsampler;
	}
	
	@Transient
	public String getXcomfirmation()
	{
		return xcomfirmation;
	}
	
	public void setXcomfirmation(String xcomfirmation)
	{
		this.xcomfirmation = xcomfirmation;
	}
	
	
	@Transient
	public String getXproductnum()
	{
		return xproductnum;
	}

	public void setXproductnum(String xproductnum)
	{
		this.xproductnum = xproductnum;
	}

	@Transient
	public String getXproductname()
	{
		return xproductname;
	}

	public void setXproductname(String xproductname)
	{
		this.xproductname = xproductname;
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
	@Transient
	public String getXsampletype()
	{
		return xsampletype;
	}
	public void setXsampletype(String xsampletype)
	{
		this.xsampletype = xsampletype;
	}
	//抽检缺陷记录
	@OneToMany(fetch=FetchType.LAZY,mappedBy="sample")
	public Set<SampleRecord> getSampleRecord()
	{
		return sampleRecord;
	}

	public void setSampleRecord(Set<SampleRecord> sampleRecord)
	{
		this.sampleRecord = sampleRecord;
	}
	
	
}
