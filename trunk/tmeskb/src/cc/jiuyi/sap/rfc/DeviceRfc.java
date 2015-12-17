package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.util.CustomerException;

public interface DeviceRfc extends BaserfcService{
	public String DeviceCrt(Device device,List<DeviceStep> step,List<DeviceModlue> module) throws IOException,CustomerException;
}
