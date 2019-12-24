<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="bsLimitInfo">
     <a4j:region>
    <h:panelGrid id="BuySelLmtInfoPanel" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel10" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblDraweeCd" styleClass="label" value="Drawee Code"/>
                <h:inputText  value="#{BsLimitInfo.draweeCode}"id="txtDraweeCd" maxlength="10"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblBillTp" styleClass="label" value="Bill Type"/>
                <h:inputText  value="#{BsLimitInfo.billType}"id="txtBillTp" maxlength="1"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblDelFlg" styleClass="label" value="Status"/>
                <h:selectOneListbox value="#{BsLimitInfo.statusBuyerSeller}" id="ddBuyStatus" styleClass="ddlist"  size="1">

                    <f:selectItems value="#{BsLimitInfo.statusBuyerSellerOption}"/>
                    <%--<rich:toolTip for="ddBuyStatus" value="Please Select Buyer or Seller Status"></rich:toolTip>--%>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblImpCurrCd" styleClass="label" value="Import Currency Code"/>
                <h:selectOneListbox value="#{BsLimitInfo.importCurrCode}" id="ddBuyImpCurrCode"style="width:72px;" styleClass="ddlist"  size="1">

                    <f:selectItems value="#{BsLimitInfo.importCurrCodeOption}"/>
                    <%--<rich:toolTip for="ddBuyImpCurrCode" value="Please Select Buyer or Seller Currency Code"></rich:toolTip>--%>
                </h:selectOneListbox>

                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow3" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblImpLmt" styleClass="label" value="Import Limit"/>
                <h:inputText   value="#{BsLimitInfo.importLim}"id="txtImpLmt" styleClass="input" style="120px"/>
                <h:outputLabel id="lblUImpLmt" styleClass="label" value="Utilised Import Limit"/>
                <h:inputText  value="#{BsLimitInfo.utillisedImpLim}"id="txtUImpLmt" styleClass="input" style="120px"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow4" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblBuyLmt" styleClass="label" value="Buy Limit"/>
                <h:inputText  value="#{BsLimitInfo.buyLim}"id="txtBuyLmt" styleClass="input" style="120px"/>
                <h:outputLabel id="lblUBuyLmt" styleClass="label" value="Utilised Buy Limit"/>
                <h:inputText  value="#{BsLimitInfo.utillisedBuyLim}"id="txtUBuyLmt" styleClass="input" style="120px"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow5" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblExpCurrCd" styleClass="label" value="Export Currency Code"/>
                <h:selectOneListbox value="#{BsLimitInfo.exportCurrCode}" id="ddBuyExpCurrCode" style="width:72px;"styleClass="ddlist"  size="1">

                    <f:selectItems value="#{BsLimitInfo.exportCurrCodeOption}"/>
                    <%--<rich:toolTip for="ddBuyExpCurrCode" value="Please Select Buyer or Seller Currency Code"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow6" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblExpLmt" styleClass="label" value="Export Limit"/>
                <h:inputText  value="#{BsLimitInfo.exportlim}"id="txtExpLmt" styleClass="input" style="120px"/>
                <h:outputLabel id="lblUExpLmt" styleClass="label" value="Utilised Export Limit"/>
                <h:inputText  value="#{BsLimitInfo.utillisedExpLim}"id="txtUExpLmt" styleClass="input" style="120px"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="buySelRow7" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblSellLmt" styleClass="label" value="Sell Limit"/>
                <h:inputText  value="#{BsLimitInfo.sellLimit}"id="txtSellLmt" styleClass="input" style="120px"/>
                <h:outputLabel id="lblUSellLmt" styleClass="label" value="Utilised Sell Limit"/>
                <h:inputText  value="#{BsLimitInfo.utillisedSellLim}"id="txtUSellLmt" styleClass="input" style="120px"/>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
          </a4j:region>
</h:form>
