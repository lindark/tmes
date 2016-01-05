package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.util.CustomerException;


public interface WorkingBillRfc extends BaserfcService {
	
	
	public void syncRepairorder(String startdate,String enddate,String starttime,String endtime) throws IOException, CustomerException;
	
	public void syncRepairorderAll(String startdate,String enddate,String starttime,String endtime) throws IOException, CustomerException;
}
