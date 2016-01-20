package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.UnitConversion;


public interface UnitConversionRfc extends BaserfcService {

	public List<UnitConversion> findUnitConeversion() throws IOException;
		
}
