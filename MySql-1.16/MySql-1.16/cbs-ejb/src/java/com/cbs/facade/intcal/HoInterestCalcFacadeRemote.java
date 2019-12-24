/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.facade.intcal;

import com.cbs.dto.HoIntObj;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface HoInterestCalcFacadeRemote {
    
    public Map<String, List<HoIntObj>> hoIntCalculation(String brnCode, String qEnd, float roi)throws ApplicationException;
    
    public String hoIntPosting(String orgBrnCode, String brnCode, String drHead, String crHead, double amt,String userName, String qEnd)throws ApplicationException;
}
