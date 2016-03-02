package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.util.CustomerException;

public interface DeptpickRfc  extends BaserfcService{
/**
 * 部门领料接口
 */
public Deptpick deptpickCrt(String testrun,HashMap<String,String> map,List<Deptpick> deptpickList) throws IOException;
}
