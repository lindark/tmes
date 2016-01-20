package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.util.CustomerException;

/**
 * 返修收获
 * 
 * @author lenovo
 * 
 */
public interface RepairInRfc extends BaserfcService
{

	public Repairin repairinCrt(String testrun,Repairin repairin, List<RepairinPiece> list_rp)
			throws IOException, CustomerException;

}
