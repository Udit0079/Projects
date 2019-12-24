/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin.customer;

import com.cbs.dto.customer.CBSBuyerSellerLimitDetailsTO;
import com.cbs.dto.customer.CBSCustNREInfoTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.web.controller.admin.CustomerDetail;
import com.cbs.web.delegate.CustomerMasterDelegate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public class BsLimitInfo {

    CustomerMasterDelegate masterDelegate;
    CustomerDetail customerDetail;
    String draweeCode;
    String billType;
    String importCurrCode;
    String importLim;
    String buyLim;
    String exportCurrCode;
    String exportlim;
    String sellLimit;
    String utillisedImpLim;
    String utillisedBuyLim;
    String utillisedExpLim;
    String utillisedSellLim;
    String statusBuyerSeller;
    List<SelectItem> statusBuyerSellerOption;
    List<SelectItem> importCurrCodeOption;
    List<SelectItem> exportCurrCodeOption;

    /** Creates a new instance of BsLimitInfo */
    public BsLimitInfo() {
        initializeForm();
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

    public CustomerMasterDelegate getMasterDelegate() {
        return masterDelegate;
    }

    public void setMasterDelegate(CustomerMasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    public String getStatusBuyerSeller() {
        return statusBuyerSeller;
    }

    public void setStatusBuyerSeller(String statusBuyerSeller) {
        this.statusBuyerSeller = statusBuyerSeller;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBuyLim() {
        return buyLim;
    }

    public void setBuyLim(String buyLim) {
        this.buyLim = buyLim;
    }

    public String getDraweeCode() {
        return draweeCode;
    }

    public void setDraweeCode(String draweeCode) {
        this.draweeCode = draweeCode;
    }

    public String getExportCurrCode() {
        return exportCurrCode;
    }

    public void setExportCurrCode(String exportCurrCode) {
        this.exportCurrCode = exportCurrCode;
    }

    public String getExportlim() {
        return exportlim;
    }

    public void setExportlim(String exportlim) {
        this.exportlim = exportlim;
    }

    public String getImportLim() {
        return importLim;
    }

    public void setImportLim(String importLim) {
        this.importLim = importLim;
    }

    public String getImportCurrCode() {
        return importCurrCode;
    }

    public void setImportCurrCode(String importCurrCode) {
        this.importCurrCode = importCurrCode;
    }

    public String getSellLimit() {
        return sellLimit;
    }

    public void setSellLimit(String sellLimit) {
        this.sellLimit = sellLimit;
    }

    public String getUtillisedBuyLim() {
        return utillisedBuyLim;
    }

    public void setUtillisedBuyLim(String utillisedBuyLim) {
        this.utillisedBuyLim = utillisedBuyLim;
    }

    public String getUtillisedExpLim() {
        return utillisedExpLim;
    }

    public void setUtillisedExpLim(String utillisedExpLim) {
        this.utillisedExpLim = utillisedExpLim;
    }

    public String getUtillisedImpLim() {
        return utillisedImpLim;
    }

    public void setUtillisedImpLim(String utillisedImpLim) {
        this.utillisedImpLim = utillisedImpLim;
    }

    public String getUtillisedSellLim() {
        return utillisedSellLim;
    }

    public void setUtillisedSellLim(String utillisedSellLim) {
        this.utillisedSellLim = utillisedSellLim;
    }

    public List<SelectItem> getExportCurrCodeOption() {
        return exportCurrCodeOption;
    }

    public void setExportCurrCodeOption(List<SelectItem> exportCurrCodeOption) {
        this.exportCurrCodeOption = exportCurrCodeOption;
    }

    public List<SelectItem> getImportCurrCodeOption() {
        return importCurrCodeOption;
    }

    public void setImportCurrCodeOption(List<SelectItem> importCurrCodeOption) {
        this.importCurrCodeOption = importCurrCodeOption;
    }

    public List<SelectItem> getStatusBuyerSellerOption() {
        return statusBuyerSellerOption;
    }

    public void setStatusBuyerSellerOption(List<SelectItem> statusBuyerSellerOption) {
        this.statusBuyerSellerOption = statusBuyerSellerOption;
    }

    public void initializeForm() {
        try {
            masterDelegate = new CustomerMasterDelegate();
            statusBuyerSellerOption = new ArrayList<SelectItem>();
            statusBuyerSellerOption.add(new SelectItem("0", "--Select--"));
            statusBuyerSellerOption.add(new SelectItem("Y", "Yes"));
            statusBuyerSellerOption.add(new SelectItem("N", "No"));
            importCurrCodeOption = new ArrayList<SelectItem>();
            importCurrCodeOption.add(new SelectItem("0", "--Select--"));
            exportCurrCodeOption = new ArrayList<SelectItem>();
            exportCurrCodeOption.add(new SelectItem("0", "--Select--"));
            List<CbsRefRecTypeTO> listMarritalStatus = masterDelegate.getFunctionValues("176");
            for (CbsRefRecTypeTO recTypeTO : listMarritalStatus) {
                importCurrCodeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
                exportCurrCodeOption.add(new SelectItem(recTypeTO.getCbsRefRecTypePKTO().getRefCode(), recTypeTO.getRefDesc()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void selectFieldValues() {
        try {
            CBSBuyerSellerLimitDetailsTO detailsTO = masterDelegate.getBsLimitDetailsByCustId(getCustomerDetail().getCustId());
            if (detailsTO != null) {
                if (detailsTO.getDraweeCode() == null || detailsTO.getDraweeCode().equalsIgnoreCase("")) {
                    this.setDraweeCode("");
                } else {
                    this.setDraweeCode(detailsTO.getDraweeCode());
                }
                if (detailsTO.getBillType() == null || detailsTO.getBillType().equalsIgnoreCase("")) {
                    this.setBillType("");
                } else {
                    this.setBillType(detailsTO.getBillType());
                }
                if (detailsTO.getStaus() == null || detailsTO.getStaus().equalsIgnoreCase("")) {
                    this.setStatusBuyerSeller("0");
                } else {
                    this.setStatusBuyerSeller(detailsTO.getStaus());
                }
                if (detailsTO.getImpCurrencyCodeCCY() == null || detailsTO.getImpCurrencyCodeCCY().equalsIgnoreCase("")) {
                    this.setImportCurrCode("0");
                } else {
                    this.setImportCurrCode(detailsTO.getImpCurrencyCodeCCY());
                }
                if (detailsTO.getImportLimit() == null) {
                    this.setImportLim("0.0");
                } else {
                    this.setImportLim(detailsTO.getImportLimit().toString());
                }
                if (detailsTO.getUtilisedImportLimit() == null) {
                    this.setUtillisedImpLim("0.0");
                } else {
                    this.setUtillisedImpLim(detailsTO.getUtilisedImportLimit().toString());
                }
                if (detailsTO.getBuyLimit() == null) {
                    this.setBuyLim("");
                } else {
                    this.setBuyLim(detailsTO.getBuyLimit().toString());
                }
                if (detailsTO.getUtilisedBuyLimit() == null) {
                    this.setUtillisedBuyLim("");
                } else {
                    this.setUtillisedBuyLim(detailsTO.getUtilisedBuyLimit().toString());
                }
                if (detailsTO.getExpCurrencyCodeCCY() == null || detailsTO.getExpCurrencyCodeCCY().equalsIgnoreCase("")) {
                    this.setExportCurrCode("0");
                } else {
                    this.setExportCurrCode(detailsTO.getExpCurrencyCodeCCY());
                }
                if (detailsTO.getExportLimit() == null) {
                    this.setExportlim("0.0");
                } else {
                    this.setExportlim(detailsTO.getExportLimit().toString());
                }

                if (detailsTO.getUtilisedExportLimit() == null) {
                    this.setUtillisedExpLim("0.0");
                } else {
                    this.setUtillisedExpLim(detailsTO.getUtilisedExportLimit().toString());
                }
                if (detailsTO.getSellLimit() == null) {
                    this.setSellLimit("0.0");
                } else {
                    this.setSellLimit(detailsTO.getSellLimit().toString());
                }
                if (detailsTO.getUtilisedSellLimit() == null) {
                    this.setUtillisedSellLim("");
                } else {
                    this.setUtillisedSellLim(detailsTO.getUtilisedSellLimit().toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            getCustomerDetail().setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setImportLim("0.0");
        this.setUtillisedImpLim("0.0");
        this.setBuyLim("0.0");
        this.setUtillisedBuyLim("0.0");
        this.setExportlim("0.0");
        this.setUtillisedExpLim("0.0");
        this.setSellLimit("0.0");
        this.setUtillisedSellLim("0.0");
        this.setDraweeCode("");
        this.setBillType("");
        this.setStatusBuyerSeller("0");

        this.setImportCurrCode("0");
        this.setExportCurrCode("0");


    }
}
