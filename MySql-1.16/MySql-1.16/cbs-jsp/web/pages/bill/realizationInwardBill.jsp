<%--
    Document   : realizationInwardBill
    Created on : May 12, 2010, 10:50:30 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Realization Inward Bill</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{realizationInwardBill.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Realization Inward Bill"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{realizationInwardBill.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="gridPanelam" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{realizationInwardBill.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type"><font class="required">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBillType" styleClass="ddlist" size="1" style="width: 103px" value="#{realizationInwardBill.billType}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="BP"/>

                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y1" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblBillNo" styleClass="label" value="Bill No." ><font class="required">*</font></h:outputLabel>
                            <h:inputText id="txtBillNo" styleClass="input"style="width:100px" value="#{realizationInwardBill.billNo}">


                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1z" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblYear" styleClass="label" value="Year" ><font class="required">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddYear" styleClass="ddlist" size="1" style="width: 100px" value="#{realizationInwardBill.years}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{realizationInwardBill.year}" />
                                <a4j:support action="#{realizationInwardBill.setFieldDisplayValues}"  event="onchange"
                                             reRender="gridPanelas,gridPanelat,stxtInstNo,stxtInstAmount,stxtInstDate,stxtAccountNo,stxtName,gridPanelaq,stxtOtherBankComm,stxtOtherBankPostageAmt,ddAlphaCode,stxtMsg,gridPanelar,stxth" limitToList="true" focus="txtOurCharges"/>

                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblInstAmount" styleClass="label" value="Inst Amount" />
                            <h:outputText id="stxtInstAmount" styleClass="output" value="#{realizationInwardBill.instAmount}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelaz" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblInstNo" styleClass="label" value="Inst No" />
                            <h:outputText id="stxtInstNo" styleClass="output" value="#{realizationInwardBill.instNumbers}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelay" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblInstDate" styleClass="label" value="Inst Date" />
                            <h:outputText id="stxtInstDate" styleClass="output" value="#{realizationInwardBill.instDate}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelax" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No" />
                            <h:outputText id="stxtAccountNo" styleClass="output" value="#{realizationInwardBill.accountNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelaw" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblName" styleClass="label" value="Name" />
                            <h:outputText id="stxtName" styleClass="output" value="#{realizationInwardBill.custName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelav" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblOtherBankComm" styleClass="label" value="Other Bank Comm" />
                            <h:outputText id="stxtOtherBankComm" styleClass="output" value="#{realizationInwardBill.bankCumu}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelau" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblOtherBankPostageAmt" styleClass="label" value="Other Bank Postage Amt" />
                            <h:outputText id="stxtOtherBankPostageAmt" styleClass="output" value="#{realizationInwardBill.postage}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelat" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblOurCharges" styleClass="label" value="Our Charges" rendered="#{realizationInwardBill.panelReason == 'False'}" />
                            <h:inputText id="txtOurCharges" styleClass="input"style="width:100px" rendered="#{realizationInwardBill.panelReason == 'False'}" value="#{realizationInwardBill.ourCharges}">
                                <a4j:support action="#{realizationInwardBill.txtOurChargesOnChange}"  event="onblur" reRender="gridPanelas,gridPanelat" limitToList="true" focus="btnPass" />

                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                            <h:outputLabel id="lblReturnCharges" styleClass="label" value="Return Charges" rendered="#{realizationInwardBill.panelReason == 'True'}"/>
                            <h:inputText id="txtReturnCharges" styleClass="input"style="width:100px" rendered="#{realizationInwardBill.panelReason == 'True'}" value="#{realizationInwardBill.returnCharges}">
                                <a4j:support event="onblur"  reRender="ddResonForCancel" limitToList="true" focus="ddResonForCancel"/>

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelas" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAmtToBeDebited" styleClass="label"  value="Amt. To Be Debited" rendered="#{realizationInwardBill.panelReason == 'False'}"/>
                            <h:inputText id="txtAmtToBeDebited" styleClass="input"style="width:100px" rendered="#{realizationInwardBill.panelReason == 'False'}" value="#{realizationInwardBill.amtToBeDebited}" disabled="true">

                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                            <h:outputLabel id="lblResonForCancel" styleClass="label" value="Reson For Cancel" rendered="#{realizationInwardBill.panelReason == 'True'}"/>
                            <h:selectOneListbox id="ddResonForCancel" styleClass="ddlist" size="1" style="width: 200px" value="#{realizationInwardBill.reasonForCancel1}" rendered="#{realizationInwardBill.panelReason == 'True'}" >
                                <f:selectItems value="#{realizationInwardBill.reasonfCancel}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelar" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccountNoToBeCredited" styleClass="label" rendered="#{realizationInwardBill.panalAlpha == 'False'}" value="Account No To Be Credited" />
                            <h:outputLabel id="lblAlphaCode" styleClass="label" value="Alpha Code" rendered="#{realizationInwardBill.panalAlpha == 'True'}" />
                            <h:outputText id="stxtAccountNoToBeCredited" styleClass="output" rendered="#{realizationInwardBill.panalAlpha == 'False'}" value="#{realizationInwardBill.acToBeCredited}"/>
                            <h:selectOneListbox id="ddAlphaCode" styleClass="ddlist" size="1" style="width: 100px" rendered="#{realizationInwardBill.panalAlpha == 'True'}" value="#{realizationInwardBill.alphaCodes}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{realizationInwardBill.alphaCode}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelaq" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputText id="stxth" styleClass="output" rendered="#{realizationInwardBill.panalAlpha == 'False'}" value="#{realizationInwardBill.ho}" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:130px;">
                        <h:panelGroup id="p4">
                            <a4j:region>
                                <rich:dataTable value="#{realizationInwardBill.realizationBill}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="List Of Ibc's To Be Realized" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Bill No." /></rich:column>
                                            <rich:column><h:outputText value="Bill Type" /></rich:column>
                                            <rich:column><h:outputText value="Remm Type" /></rich:column>
                                            <rich:column><h:outputText value="FYear" /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Inst. No." /></rich:column>
                                            <rich:column><h:outputText value="Amount" /></rich:column>
                                            <rich:column><h:outputText value="Commission" /></rich:column>
                                            <rich:column><h:outputText value="Postage" /></rich:column>
                                            <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                            <rich:column><h:outputText value="Bank Name" /></rich:column>
                                            <rich:column><h:outputText value="Bank Address" /></rich:column>
                                            <rich:column><h:outputText value="Enter By" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.billNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.remType}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.fYear}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.instAmount}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.commu}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem.postage}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.bankAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnSignature" value="Signature" style="width:60px;" disabled="true" >

                            </a4j:commandButton>
                            <a4j:commandButton id="btnPass" value="Pass" style="width:60px;" action="#{realizationInwardBill.passClick}" reRender="ddBillType,btnPass,txtBillNo,passPanel,pass1Panel,ddYear,stxtMsg"   oncomplete="if(#{realizationInwardBill.varPayBy==4}){#{rich:component('passPanel')}.show();} else if(#{realizationInwardBill.varPayBy==3}){#{rich:component('pass1Panel')}.show();}" focus="">

                            </a4j:commandButton>
                            <a4j:commandButton id="btnDishonor" value="Dishonor" style="width:70px;" action="#{realizationInwardBill.dishonorButtonClick}" reRender="gridPanelas,gridPanelat" oncomplete="#{rich:component('dishonorPanel')}.show()">

                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{realizationInwardBill.exitForm}" style="width:60px;" >

                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" style="width:60px;" action="#{realizationInwardBill.clearText}" reRender="stxtInstNo,stxtInstDate,stxtInstAmount,ddBillType,txtBillNo,ddYear,ddAlphaCode,txtOurCharges,txtAmtToBeDebited,txtReturnCharges,ddResonForCancel,stxtAccountNo,stxtOtherBankComm,stxtOtherBankPostageAmt,stxtName,stxtAccountNoToBeCredited,stxtUser,gridPanel103,stxtMsg,gridPanelat,gridPanelas" focus="ddBillType">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="dishonorPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                        <rich:componentControl for="dishonorPanel" attachTo="hidelink2" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure, You Want to Dishonor this Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{realizationInwardBill.dishonorYesButtonClick}"
                                                       oncomplete="#{rich:component('dishonorPanel')}.hide();" reRender="stxtInstNo,stxtInstAmount,ddBillType,stxtOtherBankComm,stxtOtherBankPostageAmt,txtBillNo,ddYear,txtOurCharges,txtAmtToBeDebited,txtReturnCharges,ddResonForCancel,stxtAccountNo,stxtMsg,stxtAccountNoToBeCredited,stxtInstDate,stxtName,gridPanel103" focus="txtReturnCharges"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('dishonorPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="passPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink4" />
                        <rich:componentControl for="passPanel" attachTo="hidelink4" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Instrument is Not Issued And Press Yes for Pass  And Press No for Cancel ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{realizationInwardBill.passYesClick}"
                                                       oncomplete="#{rich:component('passPanel')}.hide();" reRender="stxtInstNo,stxtInstAmount,ddBillType,stxtOtherBankComm,stxtOtherBankPostageAmt,txtBillNo,ddYear,txtOurCharges,txtAmtToBeDebited,txtReturnCharges,ddResonForCancel,stxtAccountNo,stxtMsg,stxtAccountNoToBeCredited,stxtInstDate,stxtName,gridPanel103"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('passPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="pass1Panel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="pass1Panel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You want To Pass This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{realizationInwardBill.passYesClick}"
                                                       oncomplete="#{rich:component('pass1Panel')}.hide();" reRender="stxtInstNo,stxtInstAmount,ddBillType,stxtOtherBankComm,stxtOtherBankPostageAmt,txtBillNo,ddYear,txtOurCharges,txtAmtToBeDebited,txtReturnCharges,ddResonForCancel,stxtAccountNo,stxtMsg,stxtAccountNoToBeCredited,stxtInstDate,stxtName,gridPanel103"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('pass1Panel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
