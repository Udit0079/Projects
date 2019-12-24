<%-- 
    Document   : MutualFundRedeem
    Created on : Jun 14, 2017, 11:06:14 AM
    Author     : Admin
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
            <title>Mutual Fund Redemption Form</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {                   
                    setMask();
                });
                var row;
            </script>
        </head>   
        <body>   
            <a4j:form id="MutualFundRedeem">                
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MutualFundRedeem.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Mutual Fund Redemption Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MutualFundRedeem.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{MutualFundRedeem.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" value="#{MutualFundRedeem.function}" disabled="#{MutualFundRedeem.disFunction}">
                                <f:selectItems value="#{MutualFundRedeem.functionList}"/>
                                <a4j:support event="onblur" actionListener="#{MutualFundRedeem.getBankList}" focus="ddMBank"
                                             reRender="lblMsg,ddMBank,ddFunction,saveRegion"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                     </h:panelGrid>
                     <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                          <h:outputLabel id="lblMBank" styleClass="label" value="Mutual Fund Bank"/>
                          <h:selectOneListbox id="ddMBank" styleClass="ddlist" size="1" value="#{MutualFundRedeem.mBank}">
                              <f:selectItems value="#{MutualFundRedeem.mBankList}"/>
                              <a4j:support event="onblur" actionListener="#{MutualFundRedeem.addOrVerifyOperation}" focus="#{MutualFundRedeem.focusId}"
                                             reRender="lblPAcno,ddPAcNo,lblMsg,Row3,Row4,Row5,Row6,Row7,redeemPanelGrid,btnDelete"/>
                          </h:selectOneListbox>
                          <h:outputLabel id="lblPAcno" styleClass="label" value="Pending Sequence No." style="display:#{MutualFundRedeem.inputDisFlag};"/>
                          <h:selectOneListbox id="ddPAcNo" size="1" styleClass="ddlist" value="#{MutualFundRedeem.pendingSeqNo}" style="width:100px;display:#{MutualFundRedeem.inputDisFlag};">
                                <f:selectItems value="#{MutualFundRedeem.pendingSeqNoList}"/>
                                <a4j:support actionListener="#{MutualFundRedeem.getUnAuthSeqNoDetail}" event="onblur" focus="btnSave" reRender="lblMsg,Row3,Row4,Row5,Row7,Row6,saveRegion,redeemPanelGrid,txtAmt,txtRemAmt,txtPartialAmt,txtRedeemAmt,userFillPartialAmt,txtRedeemAmt"/>
                          </h:selectOneListbox>
                     </h:panelGrid>
                      <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAmt" styleClass="label" value="Total Amount"></h:outputLabel>
                            <h:outputText id="txtAmt" styleClass="input" value="#{MutualFundRedeem.amt}" />
                            <h:outputLabel id="lblRemAmt" styleClass="label" value="Total Remaining Amount"></h:outputLabel>
                            <h:outputText id="txtRemAmt" styleClass="input" value="#{MutualFundRedeem.totalRemainingAmt}" />             
                      </h:panelGrid>
                      <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPartialAmt" styleClass="label" value="Partial Amount"></h:outputLabel>
                            <h:inputText id="txtPartialAmt" styleClass="input" value="#{MutualFundRedeem.partialAmt}" disabled="#{MutualFundRedeem.disPartialAmt}" >
                            </h:inputText>
                            <h:outputLabel id="lblRedeemAmt" styleClass="label" value="Partial Redeem Amount"></h:outputLabel>
                            <h:inputText id="txtRedeemAmt" styleClass="input" value="#{MutualFundRedeem.partialRedeemAmt}" disabled="#{MutualFundRedeem.disPartialRedAmt}">
                                 <a4j:support actionListener="#{MutualFundRedeem.getRedeemAlert}" event="onblur" reRender="lblMsg,txtCrAcNo,stxtCrAcNewNo,stxtCrAcDesc"/>      
                            </h:inputText> 
                       </h:panelGrid>
                       <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblCrAcNo" styleClass="label" value="Credit Account No."></h:outputLabel>
                            <h:inputText id="txtCrAcNo" styleClass="input" size="#{MutualFundRedeem.acNoMaxLen}" maxlength="#{MutualFundRedeem.acNoMaxLen}" value="#{MutualFundRedeem.crAcNo}" disabled="#{MutualFundRedeem.disCrAcNo}">
                                <a4j:support actionListener="#{MutualFundRedeem.getNewCrAcNo}" event="onblur" focus="txtDrAcNo" reRender="lblMsg,stxtCrAcNewNo,stxtCrAcDesc"/>                 
                            </h:inputText>
                            <h:outputText id="stxtCrAcNewNo" styleClass="output" value="#{MutualFundRedeem.crAcNewNo}"/>
                            <h:outputText id="stxtCrAcDesc" styleClass="output" value="#{MutualFundRedeem.crAcDesc}"/>
                       </h:panelGrid>
                       <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblDrAcNo" styleClass="label" value=" Debit Account No."></h:outputLabel>
                            <h:inputText id="txtDrAcNo" styleClass="input" size="#{MutualFundRedeem.acNoMaxLen}" maxlength="#{MutualFundRedeem.acNoMaxLen}" value="#{MutualFundRedeem.drAcNo}" disabled="#{MutualFundRedeem.disDrAcNo}">
                                <a4j:support actionListener="#{MutualFundRedeem.getNewDrAcNo}" event="onblur" focus="txtRemark" reRender="lblMsg,stxtDrAcNewNo,stxtDrAcDesc"/>
                            </h:inputText>
                            <h:outputText id="stxtDrAcNewNo" styleClass="output" value="#{MutualFundRedeem.drAcNewNo}"/>
                            <h:outputText id="stxtDrAcDesc" styleClass="output" value="#{MutualFundRedeem.drAcDesc}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblRemark" styleClass="label" value="Details"></h:outputLabel>
                            <h:inputText id="txtRemark" styleClass="input" value="#{MutualFundRedeem.remark}" disabled="#{MutualFundRedeem.disRemark}">
                                <a4j:support event="onblur"/>
                            </h:inputText>    
                           <h:outputLabel/>
                           <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2" columns="1" id="redeemPanelGrid" width="100%" styleClass="row1" 
                                     style="border:1px ridge #BED6F8;" >
                            <a4j:region>
                                <rich:dataTable value ="#{MutualFundRedeem.tableList}"
                                                rowClasses="row1, row2" id = "taskList" rows="7" columnsWidth="100" rowKeyVar="row" var ="item"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"  
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Redemption Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No"/> </rich:column>
                                            <rich:column><h:outputText value="Control No."/></rich:column>
                                            <rich:column><h:outputText value="Total Amount"/></rich:column>
                                            <rich:column><h:outputText value="Total Remaining Amt"/></rich:column>
                                            <rich:column><h:outputText value="Creation Date"/></rich:column>
                                            <rich:column><h:outputText value="Days" /></rich:column>
                                            <rich:column><h:outputText value="Marking" /></rich:column>
                                            <%-- <rich:column><h:outputText value="Select"/></rich:column>  --%>
                                            <rich:column><h:outputText value="Fill Partial Amount"/></rich:column>   
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.sNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.ctrlNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.faceValue}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.totalRemainingAmt}"/></rich:column>
                                    <rich:column style="text-align:right;"><h:outputText value="#{item.purchaseDt}"/></rich:column>
                                    <rich:column style="text-align:right;"><h:outputText value="#{item.fdrNo}"/></rich:column>
                                    <rich:column style="text-align:right;"><h:outputText value="#{item.intOpt}"/></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                  <%--      <h:selectBooleanCheckbox value="#{item.selectChk}" disabled="#{MutualFundRedeem.disGrid}">
                                            <a4j:support event="onclick" action="#{MutualFundRedeem.setTableDataToForm}" reRender="txtAmt,txtRemAmt,lblMsg">
                                                <f:setPropertyActionListener value="#{item}" target="#{MutualFundRedeem.currentItem}"/>
                                            </a4j:support>
                                        </h:selectBooleanCheckbox>   --%>
                                  <h:inputText id="userFillPartialAmt" value="#{item.partialAmtInput}" styleClass="input" disabled="#{MutualFundRedeem.disGrid}">
                                      <a4j:support event="onblur" action="#{MutualFundRedeem.setTableDataToForm}" reRender="txtAmt,txtRemAmt,lblMsg,txtPartialAmt,userFillPartialAmt">
                                           <f:setPropertyActionListener value="#{item}" target="#{MutualFundRedeem.currentItem}"/>
                                      </a4j:support>
                                  </h:inputText>                                  
                                </rich:column>                                
                                </rich:dataTable> 
                            </a4j:region>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </h:panelGrid>                        
                    </h:panelGrid>                  
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:region id="saveRegion">                            
                                <a4j:commandButton id="btnSave" value="#{MutualFundRedeem.btnValue}" oncomplete="#{rich:component('saveAuthPanel')}.show()" reRender="btnSave" focus="btnExit" disabled="#{MutualFundRedeem.saveDisable}"/>
                                <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" reRender="lblMsg,mainPanel" disabled="#{MutualFundRedeem.deleteDisable}"/>
                                <a4j:commandButton id="btnrefresh"  value="Refresh" action="#{MutualFundRedeem.refreshButtonAction}" reRender="mainPanel,lblMsg,txtRedeemAmt,txtPartialAmt,txtRemAmt,txtAmt"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{MutualFundRedeem.btnExit}" reRender="mainPanel"/>
                            </a4j:region>    
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="saveAuthPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To #{MutualFundRedeem.btnValue}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{MutualFundRedeem.saveAction}" reRender="lblMsg,mainPanel,saveAuthPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('saveAuthPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnDelNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{MutualFundRedeem.deleteAction}" reRender="lblMsg,mainPanel,deletePanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDelNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>