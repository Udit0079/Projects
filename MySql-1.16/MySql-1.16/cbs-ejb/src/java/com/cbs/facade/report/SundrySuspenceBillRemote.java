package com.cbs.facade.report;

import com.cbs.dto.report.SundrySuspencePojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SundrySuspenceBillRemote {

    public List<SundrySuspencePojo> getSundryData(String brncode, int trantype, String todate, String fromdt);

    public java.lang.String getBranchNameandAddress(int brncode);
}
