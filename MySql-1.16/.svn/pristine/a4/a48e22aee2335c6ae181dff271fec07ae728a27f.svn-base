<%-- 
    Document   : sharetransfer
    Created on : 26 Aug, 2011, 3:41:57 PM
    Author     : Zeeshan Waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Share Transfer</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="ShareTransfer"/>
            <a4j:form id="Form">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareTransfer.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Share Transfer"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareTransfer.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ShareTransfer.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" style="width:100%;" >
                                <rich:panel id="detailPanel" header="Transferor Details" style="text-align:center;">
                                    <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel1" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblfolioleft" styleClass="label"  value="Folio No :"/>
                                        <h:inputText id="txtfolioleft" style="width: 90px"  value="#{ShareTransfer.folioNoLeftShow}" maxlength="12" styleClass="input">
                                            <a4j:support action="#{ShareTransfer.transferorNameDetail}" event="onblur" reRender="stxtfolioleftShow,stxtfolioleft,gridPanelIssue,stxtMsg,txtfolioleft" oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" />
                                        </h:inputText>
                                        <h:outputText id="stxtfolioleftShow" styleClass="output" value="#{ShareTransfer.folioNoLeft}"/>
                                        <h:outputText id="stxtfolioleft" styleClass="output" value="#{ShareTransfer.folioLeftName}"/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelIssue" width="100%" styleClass="row2" style="height:168px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{ShareTransfer.shareTransferLeft}" var="item1"
                                                            rowClasses="row1, row2" id="IssueList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup> <rich:column colspan="9"> </rich:column>
                                                        <rich:column breakBefore="true"> <h:outputText value="Cert. No"/></rich:column>
                                                        <rich:column><h:outputText value="No Of Shares"/> </rich:column>
                                                        <rich:column><h:outputText value="No From"/></rich:column>
                                                        <rich:column><h:outputText value="No To"/></rich:column>
                                                        <rich:column><h:outputText value="Issue Date"/> </rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{item1.certNo}"/> </rich:column>
                                                <rich:column><h:outputText value="#{item1.noOfshares}"/></rich:column>
                                                <rich:column><h:outputText value="#{item1.noFrom}"/> </rich:column>
                                                <rich:column><h:outputText value="#{item1.noTo}"/></rich:column>
                                                <rich:column><h:outputText value="#{item1.issueDt}"/></rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller id="scrollerIssue" align="left" for="IssueList" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </rich:panel>
                            </h:panelGrid>
                            <h:panelGrid id="rightPanel" columns="1" style="width:100%;">
                                <rich:panel id="detailPanel1" header="Transferee Details" style="text-align:center;">
                                    <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Panel2" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblfolioright" styleClass="label"  value="Folio No :"/>
                                        <h:inputText id="txtfolioright" style="width: 90px"  value="#{ShareTransfer.folioNoRightShow}" maxlength="12" styleClass="input">
                                            <a4j:support action="#{ShareTransfer.transfereeNameDetail}"  event="onblur" oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" reRender="stxtfoliorightShow,stxtMsg,stxtfolioright,Stocktable"/>
                                        </h:inputText>
                                        <h:outputText id="stxtfoliorightShow" styleClass="output" value="#{ShareTransfer.folioNoRight}" />
                                        <h:outputText id="stxtfolioright" styleClass="output" value="#{ShareTransfer.folioRightName}" />
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{ShareTransfer.shareTransferRight}" var="item"
                                                            rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup> <rich:column colspan="5"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="Cert. No"/> </rich:column>
                                                        <rich:column ><h:outputText value="No Of Shares"/></rich:column>
                                                        <rich:column ><h:outputText value="No From"/></rich:column>
                                                        <rich:column ><h:outputText value="No To" /></rich:column>
                                                        <rich:column > <h:outputText value="Issue Date"/></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{item.certNo}"/></rich:column>
                                                <rich:column><h:outputText value="#{item.noOfshares}"/></rich:column>
                                                <rich:column><h:outputText value="#{item.noFrom}"/></rich:column>
                                                <rich:column> <h:outputText value="#{item.noTo}"/> </rich:column>
                                                <rich:column> <h:outputText value="#{item.issueDt}"/> </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </rich:panel>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow5" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblFromCertificate" styleClass="label" value="From Certificate"/>
                            <h:inputText id="txtFromCertificate" style="width: 80px"  value="#{ShareTransfer.fromCertificate}" maxlength="6" styleClass="input">
                                <a4j:support action="#{ShareTransfer.checkCertificateNoDetail}" event="onblur" 
                                             oncomplete="if(#{ShareTransfer.message=='Please Fill From Certificate'}){#{rich:element('txtFromCertificate')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Enter Numeric Value in From Certificate'}){#{rich:element('txtFromCertificate')}.focus();}
                                             else if(#{ShareTransfer.message=='This certifate not authorized'}){#{rich:element('txtFromCertificate')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Check From Certificate No Entered'}){#{rich:element('txtFromCertificate')}.focus();}
                                             else{#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             #{rich:element('calIntTransferDate')}.style=setMask();" 
                                             reRender="stxtMsg,matRow6,txtSharesToBeTransferred,txtFromCertificate"/>
                            </h:inputText>
                            <h:outputLabel id="lblSharesToBeTransferred" styleClass="label" value="Shares To Be Transferred"/>
                            <h:inputText id="txtSharesToBeTransferred" styleClass="input" style="width:90px" value="#{ShareTransfer.sharesToBeTransferred}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support action="#{ShareTransfer.checkShareNo}" event="onblur" 
                                             oncomplete="if(#{ShareTransfer.message=='Please Fill From Certificate'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Enter Numeric Value in From Certificate'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Fill Shares To Be Transferred'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Enter Numeric Value in Shares To Be Transferred'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else if(#{ShareTransfer.message=='Shares To Be Transferred exceeds present in the Transferor Details table'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else if(#{ShareTransfer.message=='Enter the valid no of Shares To Be Transferred'}){#{rich:element('txtSharesToBeTransferred')}.focus();}
                                             else{#{rich:element('txtShareNoFrom')}.focus();}
                                             #{rich:element('calIntTransferDate')}.style=setMask();" 
                                             reRender="stxtMsg,matRow6,btnSave"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow6" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblShareNoFrom" styleClass="label" value="Share No From"/>
                            <h:inputText id="txtShareNoFrom" style="width: 90px"  value="#{ShareTransfer.shareNoFrom}"styleClass="input" disabled="#{ShareTransfer.fromDisable}">
                                <a4j:support action="#{ShareTransfer.checkShareNoFrom}" event="onblur" 
                                             oncomplete="if(#{ShareTransfer.message=='Please Fill Share No From'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Enter Numeric Value in Share No From'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Fill Share No From'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Enter Numeric Value in Share No From'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else if(#{ShareTransfer.message=='Please enter valid  Share No From'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else if(#{ShareTransfer.message=='Please Check The Share No From Entered'}){#{rich:element('txtShareNoFrom')}.focus();}
                                             else{#{rich:element('calIntTransferDate')}.focus();}
                                             #{rich:element('calIntTransferDate')}.style=setMask();" 
                                             reRender="stxtMsg,matRow6,btnSave" focus="calIntTransferDate"/>
                            </h:inputText>
                            <h:outputLabel id="lblShareNoTo" styleClass="label" value="Share No To"/>
                            <h:inputText id="txtShareNoTo" styleClass="input" style="width:90px" value="#{ShareTransfer.shareNoTo}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareTransfer.toDisable}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow7" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblTransferDate" styleClass="label" value="Transfer Date"/>
                            <h:panelGroup id="groupPanelTransferDate" layout="block">
                                <h:inputText id="calIntTransferDate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10"  value="#{ShareTransfer.transferDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblTransferDateStyle" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblTransfereeCertNo" styleClass="label" value="Transferee Cert No"/>
                            <h:panelGroup id="groupPanelTransfereeCertNo" layout="block">
                                <h:selectOneListbox id="ddTransfereeCertNo" styleClass="ddlist"  size="1" style="width:70px" value="#{ShareTransfer.transfereeCertNo}">
                                    <f:selectItems value="#{ShareTransfer.transfereeNoType}"/>
                                    <a4j:support action="#{ShareTransfer.transfereeCertificateNum}" event="onblur" oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" reRender="txtTransfereeCertNo,groupPanelTransferorCertNo" focus="ddTransferorCertNo"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtTransfereeCertNo" styleClass="input" style="width:70px" value="#{ShareTransfer.transfereeCertNoTxt}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ShareTransfer.transfereeCertNoTxtDisable}" />
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow8" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblTransferorCertNo" styleClass="label" value="Transferor Cert No"/>
                            <h:panelGroup id="groupPanelTransferorCertNo" layout="block">
                                <h:selectOneListbox id="ddTransferorCertNo" styleClass="ddlist"  size="1" style="width:70px" value="#{ShareTransfer.transferorCertNo}" disabled="#{ShareTransfer.transferorCertNoDisable}">
                                    <f:selectItems value="#{ShareTransfer.transferorNoType}"/>
                                    <a4j:support action="#{ShareTransfer.transferorCertificateNum}" event="onblur" oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" reRender="footerPanel,txtTransferorCertNo" focus="txtRemarks"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtTransferorCertNo" styleClass="input" style="width:70px" value="#{ShareTransfer.transferorCertNoTxt}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"disabled="#{ShareTransfer.transferorCertNoTxtDisable}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"/>
                            <h:inputText id="txtRemarks" styleClass="input" style="width:150px" value="#{ShareTransfer.remarks}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"/>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{ShareTransfer.saveButtonAction}"  oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" reRender="stxtMsg,mainPanel" disabled="#{ShareTransfer.saveDisable}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ShareTransfer.refreshButtonAction}"  oncomplete="#{rich:element('calIntTransferDate')}.style=setMask();" reRender="mainPanel,stxtMsg"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ShareTransfer.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </f:view>




