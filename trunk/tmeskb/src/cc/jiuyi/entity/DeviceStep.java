package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class DeviceStep extends BaseEntity{
	private String vornr;//工序
	private String arbpl;//工作中心
	private String werks;//工厂
	private String steus;//控制码 （订单类型）PM01,PM02
	private String work_activity;//涉及作业的工作
	private String duration;//作业正常期间
	private String description;//工序短文本
	private Device device;//设备
	
	
	public String getVornr() {
		return vornr;
	}
	public void setVornr(String vornr) {
		this.vornr = vornr;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getArbpl() {
		return arbpl;
	}
	public void setArbpl(String arbpl) {
		this.arbpl = arbpl;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getSteus() {
		return steus;
	}
	public void setSteus(String steus) {
		this.steus = steus;
	}
	public String getWork_activity() {
		return work_activity;
	}
	public void setWork_activity(String work_activity) {
		this.work_activity = work_activity;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
