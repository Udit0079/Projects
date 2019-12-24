/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.constant;

import com.cbs.utils.CbsUtil;

/**
 *
 * @author root
 */
public enum SiplConstant {

    //   public static final String GLHO  =  CbsAcCodeConstant.GL_ACCNO.getAcctCode() +"00070001";
    //   public static final String GLBPIntRec =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00250201";
//    public static final String GLBCComm =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00260101";
//    public static final String GLBillPay = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00520201";
//    public static final String GLDraftPay = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00070201";
//    public static final String GLCASEPAY =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00060201";
//    public static final String GLHOPAY =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00070001";
//    public static final String GLBillCol = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+ "00130101";
//    public static final String GLBillLodg =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00620101";
//    public static final String GLChequeRet = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00261501";
//    public static final String GLSundry = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00060801";
//    public static final String BnkGuarCr =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00143101";
//    public static final String BnkGuarDr =CbsAcCodeConstant.GL_ACCNO.getAcctCode()+ "00633101";
//    public static final String GLService = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+ "00260501";
//    public static final String GLLcComm =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00260601";
//    public static final String GLBgComm =  CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00261201";
//    public static final String glMiscIncStR = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00250001";
//    public static final String glMiscIncEndR = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00300001";
//    public static final String PAY_ORDER = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"00060201";
//    public static final String GL_2000 = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"002000";
//    public static final String GL_3000 = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"003000";
//    public static final String GL_55 = CbsAcCodeConstant.GL_ACCNO.getAcctCode()+"0055";
    /**
     * ***************************************************** OLD VALUES START
     * ******************************************************
     */
    /**
     * ***************************************************** OLD VALUES END
     * ******************************************************
     */
    /**
     * ***************************************************** NEW VALUES START
     * ******************************************************
     */
    /**
     * ***************************************************** NEW VALUES END
     * ******************************************************
     */
    //GL_INCOME_ST("002501"),
    GL_INCOME_ST("302501") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "302501";
            } else {
                return "002501";
            }
        }
    },
    //GL_INCOME_END("003000"),
    GL_INCOME_END("399999") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "399999";
            } else {
                return "003000";
            }
        }
    },
    //GL_EXP_ST("002000"),
    GL_EXP_ST("402000") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "402000";
            } else {
                return "002000";
            }
        }
    },
    //GL_EXP_END("002500"),
    GL_EXP_END("499999") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "499999";
            } else {
                return "002500";
            }
        }
    },
    //GL_PL_ST("002000"),
    GL_PL_ST("302500") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "302500";
            } else {
                return "002000";
            }
        }
    },
    //GL_PL_END("003000"),
    GL_PL_END("499999") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "499999";
            } else {
                return "003000";
            }
        }
    },
    GL_CASH("060055") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "060055";
            } else {
                return "060055";
            }
        }
    },
    ISO_HEAD("INTERSOLE ACCOUNT") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "INTERSOLE ACCOUNT";
            } else {
                return "INTERSOLE ACCOUNT";
            }
        }
    },
    HO_HEAD("HEAD OFFICE") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "HEAD OFFICE";
            } else {
                return "HEAD OFFICE";
            }
        }
    },
    CLG_AD_HEAD("500") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "500";
            } else {
                return "500";
            }
        }
    },
    GL_CSH_OTH("005502") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "200501";
            } else {
                return "005502";
            }
        }
    },
    GL_INT_PAY_ST("100800") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "100800";
            } else {
                return "009300";
            }
        }
    },
    //GL_INT_PAY_END("009399"),
    GL_INT_PAY_END("100899") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "100899";
            } else {
                return "009399";
            }
        }
    },
    //GL_INT_REC_ST("009400"),
    GL_INT_REC_ST("209900") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "209900";
            } else {
                return "009400";
            }
        }
    },
    //GL_INT_REC_END("009499"),
    GL_INT_REC_END("209999") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "209999";
            } else {
                return "009499";
            }
        }
    },
    //GL_SUN_CR_ST("009200"),
    GL_SUN_CR_ST("109200") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "109200";
            } else {
                return "009200";
            }
        }
    },
    //GL_SUN_CR_END("009299"),
    GL_SUN_CR_END("109299") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "109299";
            } else {
                return "009299";
            }
        }
    },
    //GL_SUN_DR_ST("009100"),
    GL_SUN_DR_ST("209200") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "209200";
            } else {
                return "009100";
            }
        }
    },
    //GL_SUN_DR_END("009199"),
    GL_SUN_DR_END("209299") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "209299";
            } else {
                return "009199";
            }
        }
    },
    //GL_BANKER_CA_ST("005700"),
    GL_BANKER_CA_ST("201000") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "201000";
            } else {
                return "005700";
            }
        }
    },
    //GL_BANKER_CA_END("005799"),
    GL_BANKER_CA_END("201699") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "201699";
            } else {
                return "005799";
            }
        }
    },
    //GL_BANKER_FD_ST("005900"),
    GL_BANKER_FD_ST("205100") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "205100";
            } else {
                return "005900";
            }
        }
    },
    //GL_BANKER_FD_END("005999"),
    GL_BANKER_FD_END("205699") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "205699";
            } else {
                return "005999";
            }
        }
    },
    //GL_PRO_PREV_ST("005200"),
    GL_PRO_PREV_ST("102500") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "102500";
            } else {
                return "005200";
            }
        }
    },
    //GL_PRO_PREV_END("005299"),
    GL_PRO_PREV_END("102549") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "102549";
            } else {
                return "005299";
            }
        }
    },
    GL_CSH_HEAD("200500") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "200500";
            } else {
                return "005501";
            }
        }
    },
    PRE_YEAR_LOSS("006600") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "006600";
            } else {
                return "006600";
            }
        }
    },
    //GL_PRO_PREV_END("001600"),
    PRE_YEAR_PROFIT("001600") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "001600";
            } else {
                return "001600";
            }
        }
    },
    SERVICE_BR_AL_CODE("HO") {
        @Override
        public String getValue() {
            if (CbsUtil.newGlPattern) {
                return "HO";
            } else {
                return "HO";
            }
        }
    },
    GL_LIAB_START("100000"),
    
    GL_LIAB_END("199999"),
    
    GL_ASSET_START("200000"),
    
    GL_ASSET_END("299999"),
    
    GL_PATTERN("NEW");
    private String value;

    SiplConstant(String value) {
        this.value = value;
    }
    String acctCode;

    public String getValue() {
        return this.value;
    }
}