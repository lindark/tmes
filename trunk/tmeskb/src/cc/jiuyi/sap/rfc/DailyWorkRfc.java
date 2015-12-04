package cc.jiuyi.sap.rfc;

import java.io.IOException;

import cc.jiuyi.util.CustomerException;

public interface DailyWorkRfc extends BaserfcService{
	public void SetDailyWork(String orderid,String step,String menge)throws IOException, CustomerException;
}
