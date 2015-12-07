package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.util.CustomerException;

public interface DailyWorkRfc extends BaserfcService{
	public void SetDailyWork(String orderid,String step,String menge)throws IOException, CustomerException;
	public List<DailyWork> BatchSetDailyWork(List<DailyWork> dailywork) throws IOException, CustomerException;
}
