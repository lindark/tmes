package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Repairin;
import cc.jiuyi.util.CustomerException;

public interface RepairInRfc  extends BaserfcService{
	public List<Repairin> repairInCrt(List<Repairin> list)throws IOException,CustomerException;
}
