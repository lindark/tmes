package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Model;
import cc.jiuyi.util.CustomerException;

public interface ModeRfc extends BaserfcService{
	public String ModelCrt(String iscrtOrchange,Model model,List<DeviceStep> step,List<DeviceModlue> module) throws IOException,CustomerException;
}
