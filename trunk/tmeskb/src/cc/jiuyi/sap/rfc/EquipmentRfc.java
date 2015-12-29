package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Equipment;
import cc.jiuyi.util.CustomerException;

public interface EquipmentRfc extends BaserfcService{
	public List<Equipment> getEquipment(String equipmentNo,String equipmentName)throws IOException,CustomerException;;
}
