package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;
import cc.jiuyi.entity.Process;
import cc.jiuyi.util.CustomerException;

public interface ProcessRfc extends BaserfcService {
	public List<Process> findProcess(String matnr,String werks,String date) throws IOException,CustomerException;
	
}
