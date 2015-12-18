package cc.jiuyi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class DeviceModlue extends BaseEntity{
	private static final long serialVersionUID = 12324567898765432L;
	private String material;//物料号
	private String menge;//数量
	private String postp;//项目类别
	private String meins;//基本单位
	private String vornr;//工序 
	private Device device;//设备
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getPostp() {
		return postp;
	}
	public void setPostp(String postp) {
		this.postp = postp;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	public String getVornr() {
		return vornr;
	}
	public void setVornr(String vornr) {
		this.vornr = vornr;
	}
	
	
}
