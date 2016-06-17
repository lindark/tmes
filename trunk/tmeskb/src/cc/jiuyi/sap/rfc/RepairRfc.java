package cc.jiuyi.sap.rfc;

import java.io.IOException;
import java.util.List;

import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.util.CustomerException;

public interface RepairRfc  extends BaserfcService{
/**
 * 反修接口
 */
public Repair repairCrt(String testrun,Repair repair,List<RepairPiece> list_rp) throws IOException,CustomerException;
//public Repair revokedRepairCrt(String testrun,Repair repair, List<RepairPiece> list_rp)throws IOException, CustomerException;
public Repair revokedRepairCrt(Repair repair,String testrun,String cardnumber)throws IOException,CustomerException;
}
