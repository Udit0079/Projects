<%--
    Document   : InstumentLostRegister
    Created on : Aug 6, 2010, 5:03:10 PM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
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
            <title>Instrument Lost Active Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InstrumentLostRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Instrument Lost Active Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InstrumentLostRegister.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{InstrumentLostRegister.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columns="1" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="Row41" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblfuntion" styleClass="label"  value="Function"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddfuntion" styleClass="ddlist" size="1" style="width:100px" value="#{InstrumentLostRegister.function}" >
                                    <f:selectItems value="#{InstrumentLostRegister.functionList}"/>
                                    <a4j:support action="#{InstrumentLostRegister.changeFunction}" event="onblur" ajaxSingle="true" reRender="footerPanel" limitToList="true" />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblBillType" styleClass="label"  value="Bill Type"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddbiltype" styleClass="ddlist" size="1" style="width:100px" value="#{InstrumentLostRegister.billType}" >
                                    <f:selectItems value="#{InstrumentLostRegister.billTypeList}"/>
                                    <a4j:support action="#{InstrumentLostRegister.getTableDetails}"  event="onblur" reRender="gridPanel103,scroller" limitToList="true" />
                                </h:selectOneListbox>
                                <h:outputText/>
                                <h:outputText/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblInstrumentNo" styleClass="label"  value="Instrument No"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtInstrmntNo" style="width: 100px" value="#{InstrumentLostRegister.instrumentno}" styleClass="input" />
                                <h:outputLabel id="lblOriginDate" styleClass="label"  value="Origin Date"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calOrigDate" value="#{InstrumentLostRegister.orignDate}" inputSize="10"/>
                                <h:outputLabel id="lblIssueCode" styleClass="label"  value="Issue Branch"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddIssuecode" styleClass="ddlist"  size="1" style="width:100px" value="#{InstrumentLostRegister.issueCode}">
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{InstrumentLostRegister.brCodeList}"/>
                                    <a4j:support action="#{InstrumentLostRegister.getInstDetails}"  event="onblur" reRender="txtInfavourOf,txtSeqNo,txtAmount,ddDraweeCode" limitToList="true" />
                                </h:selectOneListbox>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblSeqNo" styleClass="label"  value="Seq No"></h:outputLabel>
                                <h:outputText id="txtSeqNo" value="#{InstrumentLostRegister.sequeNo}" styleClass="output"/>
                                <h:outputLabel id="lblAmount" styleClass="label"  value="Amount"></h:outputLabel>
                                <h:outputText id="txtAmount"  value="#{InstrumentLostRegister.amount}" styleClass="output"/>
                                <h:outputLabel id="lblInfavourOf" styleClass="label"  value="In favour Of"></h:outputLabel>
                                <h:outputText id="txtInfavourOf"  value="#{InstrumentLostRegister.infavourOf}" styleClass="output"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblReportingCode" styleClass="label"  value="Reporting Branch"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddReportCode" styleClass="ddlist"   size="1" style="width:100px" value="#{InstrumentLostRegister.reportingCode}">
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{InstrumentLostRegister.brCodeList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDraweeCode" styleClass="label"  value="Drawee Branch"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddDraweeCode" styleClass="ddlist"  size="1" style="width:100px" value="#{InstrumentLostRegister.drCode}" >
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{InstrumentLostRegister.brCodeList}"/>
                                </h:selectOneListbox>
                                <h:outputText/>
                                <h:outputText/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col23,col2,col23,col2,col23,col2" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblCircularNo" styleClass="label"  value="Circular No"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCircularNo" style="width: 100px" value="#{InstrumentLostRegister.circularNo}" styleClass="input"/>
                                <h:outputLabel id="lblCircularDate" styleClass="label"  value="Circular Date"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calCircularDate" value="#{InstrumentLostRegister.circularDate}" inputSize="10"/>
                                <h:outputLabel id="lblLossDate" styleClass="label"  value="Loss Date"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calLossDate" value="#{InstrumentLostRegister.lossDate}" inputSize="10"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                        <a4j:region>
                            <rich:dataTable value="#{InstrumentLostRegister.instrReg}" var="item" rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Seq No" /></rich:column>
                                        <rich:column><h:outputText value="Inst No" /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Infavour Of" /></rich:column>
                                        <rich:column><h:outputText value="Issue Code" /></rich:column>
                                        <rich:column><h:outputText value="Reporting Code" /></rich:column>
                                        <rich:column><h:outputText value="Drawee Code" /></rich:column>
                                        <rich:column><h:outputText value="Origin Dt" /></rich:column>
                                        <rich:column><h:outputText value="Lost Dt" /></rich:column>
                                        <rich:column><h:outputText value="Circular No" /></rich:column>
                                        <rich:column><h:outputText value="Circular Dt" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column rendered="#{InstrumentLostRegister.function == 'V'}"><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="Enter by" /></rich:column>
                                        <rich:column rendered="#{InstrumentLostRegister.function == 'E'}"><h:outputText value="Delete" /></rich:column>
                                        <rich:column rendered="#{InstrumentLostRegister.function != 'L'}"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.seqNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.instNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.billType}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.amount}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.dt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.infoof}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.issuecode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.repcode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.drawcode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.orgndt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.lostdt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.cirNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.cirdt}" /></rich:column>
                                <rich:column><h:outputText value="#{item.status}" /></rich:column>
                                <rich:column rendered="#{InstrumentLostRegister.function == 'V'}"><h:outputText value="#{item.auth}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.enterby}" /></rich:column>
                                <rich:column rendered="#{InstrumentLostRegister.function == 'E'}">
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{InstrumentLostRegister.currentRow}" />
                                        <f:setPropertyActionListener value="#{item}" target="#{InstrumentLostRegister.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column rendered="#{InstrumentLostRegister.function != 'L'}">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InstrumentLostRegister.select}" reRender="subbodyPanel,footerPanel,BtnPanel,stxtMsg,ddbiltype,txtBookNo,ddAcctNo,ddIssuecode,calOrigDate,txtCircularNo,txtInstrmntNo,txtSeqNo,txtInfavourOf,ddReportCode,calLossDate,calCircularDate,txtAmount,ddDraweeCode">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{InstrumentLostRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InstrumentLostRegister.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                             <f:facet name="header">
                                <h:outputText value="Confirmation DialogBox" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2"><h:outputText value="Are you sure to delete this details?"/></td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:region id="yesBtn">
                                                    <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{InstrumentLostRegister.delete}" oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtMsg,subbodyPanel" />
                                                </a4j:region>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="savePanel" autosized="true" width="250"  onshow="#{rich:element('saveYes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation DialogBox" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="#{InstrumentLostRegister.showMsg}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:region id="yesBtn">
                                                    <a4j:commandButton id="saveYes" value="Yes" ajaxSingle="true" action="#{InstrumentLostRegister.saveBtnAction}"
                                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="taskList,stxtMsg,subbodyPanel" />
                                                </a4j:region>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="saveNo" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="#{InstrumentLostRegister.btnValue}" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{InstrumentLostRegister.flag}" reRender="taskList,stxtMsg,subbodyPanel" />
                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{InstrumentLostRegister.resetButton}"  reRender="mainPanel" focus="ddbiltype"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InstrumentLostRegister.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>