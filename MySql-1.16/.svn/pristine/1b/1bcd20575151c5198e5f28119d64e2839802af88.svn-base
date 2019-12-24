/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.to.other;

import com.hrms.entity.payroll.HrLeaveRegister;
import java.math.BigInteger;
import java.util.Comparator;

public class CommonComparator implements Comparator<HrLeaveRegister> {

    public int compare(HrLeaveRegister obj1, HrLeaveRegister obj2) {
        return new BigInteger(obj1.getHrLeaveRegisterPK().getLeavRegCode().substring(3)).
                compareTo(new BigInteger(obj2.getHrLeaveRegisterPK().getLeavRegCode().substring(3)));
    }
}
