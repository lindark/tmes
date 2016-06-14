package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;


import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.util.CustomerException;


public interface WorkingBillRfc extends BaserfcService {
	
	
	public void syncRepairorder(String startdate,String enddate,String starttime,String endtime) throws IOException, CustomerException;
	
	public void syncRepairorderAll(String startdate,String enddate,String starttime,String endtime,String aufnr,String workshopcode,List<UnitdistributeProduct> unitdistributeList,String workcode) throws IOException, CustomerException;
}
