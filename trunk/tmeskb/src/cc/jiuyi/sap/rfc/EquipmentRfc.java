package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Equipments;
import cc.jiuyi.util.CustomerException;

public interface EquipmentRfc extends BaserfcService{
	public List<Equipments> getEquipment()throws IOException,CustomerException;;
}
