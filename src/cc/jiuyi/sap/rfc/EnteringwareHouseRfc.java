package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.util.CustomerException;

public interface EnteringwareHouseRfc extends BaserfcService{
	public List<EnteringwareHouse> CartonCrt(String testrun,List<EnteringwareHouse> list) throws IOException,CustomerException;
}
