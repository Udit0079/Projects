<%-- 
    Document   : InstrumentActineRegister
    Created on : 14 Aug, 2010, 2:19:39 PM
    Author     : Shipra Gupta
    Modified By: Rohit Krishna Gupta On 10/03/2011
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
            <title>Instrument Active Register</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{InstrumentActiveRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Instrument Active Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InstrumentActiveRegister.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid   id="Panel754" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id ="stxtMessage"styleClass="error" value ="#{InstrumentActiveRegister.message}"/>
                        <h:outputText id ="stxtSysMsg"styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel1" style="width:100%;text-align:left;height:30px;"  styleClass="row2">
                            <h:outputLabel id="lblBillType" styleClass="label"  value="Bill Type :"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="lstBilltype" tabindex="1" style="width:110px" styleClass="ddlist" size ="1" value="#{InstrumentActiveRegister.billType}">
                                <f:selectItems value = "#{InstrumentActiveRegister.billTypedd}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblInstrumentNo" styleClass="label"  value="Instrument No :"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtInstrmntNo" tabindex="2" maxlength="6" style="width:110px" value ="#{InstrumentActiveRegister.instNo}" styleClass="input">
                                <a4j:support action="#{InstrumentActiveRegister.getTableDetails}" event="onblur"
                                             reRender="stxtMessage,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,footerPanel,gridPanel103" focus="#{rich:clientId('calOriginaldate')}InputDate"/>
                            </h:inputText>
                            <h:outputLabel id="hide3"/>
                            <h:outputText id="hide4"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel2" style="width:100%;text-align:left;height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBookNo" styleClass="label"  value="Book No :"/>
                            <h:outputText id="stxtBookNo" styleClass="output" value = "#{InstrumentActiveRegister.bookNo}" style="color:purple;"/>
                            <h:outputLabel id="lblSeqN0" styleClass="label"  value="Seq No :"/>
                            <h:outputText id="stxtSeqNo" styleClass="output" value ="#{InstrumentActiveRegister.seqNo}" style="color:purple;"/>
                            <h:outputLabel id="lblAmount" styleClass="label"  value="Amount :"/>
                            <h:outputText id="stxtAmount" styleClass="output" value ="#{InstrumentActiveRegister.amount}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel3" style="width:100%;text-align:left;height:30px;" styleClass="row2">
                            <h:outputLabel id="lblBranchCode" styleClass="label"  value="Branch Code :"/>
                            <h:outputText id="stxtBranchCode" styleClass="output" value = "#{InstrumentActiveRegister.branchCode}" style="color:purple;"/>
                            <h:outputLabel id="lblInfavourOf" styleClass="label"  value="Infavourof :"/>
                            <h:outputText id="stxtInFavourOf" styleClass="output" value ="#{InstrumentActiveRegister.infavourOf}" style="color:purple;"/>
                            <h:outputLabel id="hide1"/>
                            <h:outputText id="hide2"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel4" style="width:100%;text-align:left;height:30px;" styleClass="row1">
                            <h:outputLabel id="lblIssueCode" styleClass="label"  value="Issue Code :"/>
                            <h:outputText id="stxtIssueCode" styleClass="output" value ="#{InstrumentActiveRegister.issueCode}" style="color:purple;"/>
                            <h:outputLabel id="lblReportingCode" styleClass="label"  value="Reporting Code :"/>
                            <h:outputText id="stxtReportingCode" styleClass="output" value ="#{InstrumentActiveRegister.reportingCode}" style="color:purple;"/>
                            <h:outputLabel id="lblDraweeCode" styleClass="label"  value="Drawee Code :"/>
                            <h:outputText id="stxtDraweeCode" styleClass="output" value ="#{InstrumentActiveRegister.draweeCode}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel5" style="width:100%;text-align:left;height:30px;" styleClass="row2">
                            <h:outputLabel id="lblOriginalDate" styleClass="label"  value="Original Date :"/>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="3" id="calOriginaldate" value ="#{InstrumentActiveRegister.orgDt}"/>
                            <h:outputLabel id="lblLossDate" styleClass="label"  value="Loss Date :"/>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="4" id="calLossdate" value ="#{InstrumentActiveRegister.lossDt}"/>
                            <h:outputLabel id="lblstatus" styleClass="label"  value="Status :"/>
                            <h:selectOneListbox id="lstStatus" tabindex="5" style="width:110px" styleClass="ddlist" size ="1"  value ="#{InstrumentActiveRegister.status}">
                                <f:selectItem itemValue="Active"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel6" style="width:100%;text-align:left;height:30px;" styleClass="row1">
                            <h:outputLabel id="lblCircularNo" styleClass="label"  value="Circular No. :"/>
                            <h:outputText id="stxtCircularNo" styleClass="output" value ="#{InstrumentActiveRegister.circularNo}" style="color:purple;"/>
                            <h:outputLabel id="lblCircularDate" styleClass="label"  value="Circular Date :"/>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="6" id="calCircularDate" value ="#{InstrumentActiveRegister.circularDt}"/>
                            <h:outputLabel id="lblSNo" styleClass="label"  value="S. No. :"/>
                            <h:outputText id="stxtSNo" styleClass="output" value="#{InstrumentActiveRegister.sno}" style="color:purple;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2"  columns="1" id="gridPanel103" width="100%" styleClass="row2" >
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                           actionListener="#{InstrumentActiveRegister.fetchCurrentRow}">
                                <a4j:actionparam name="sNo" value="{sNo}" />
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable  value ="#{InstrumentActiveRegister.instActiveObj}"
                                             rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row" var ="item"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                             #{rich:component('menu')}.show(event,{sNo:'#{item.sno}', currentRow:'#{row}'});return false;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="19"><h:outputText value="Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S No" /> </rich:column>
                                        <rich:column><h:outputText value="Book No" /></rich:column>
                                        <rich:column><h:outputText value="Seq No" /></rich:column>
                                        <rich:column><h:outputText value="Inst No" /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Branch Code" /></rich:column>
                                        <rich:column><h:outputText value="Infavour Of" /></rich:column>
                                        <rich:column><h:outputText value="issue Code" /></rich:column>
                                        <rich:column><h:outputText value="reporting Code" /></rich:column>
                                        <rich:column><h:outputText value="Drawee Code" /></rich:column>
                                        <rich:column><h:outputText value="Origin Date" /></rich:column>
                                        <rich:column><h:outputText value="Lost Dt" /></rich:column>
                                        <rich:column><h:outputText value="Circular No" /></rich:column>
                                        <rich:column><h:outputText value="Circular Dt" /></rich:column>
                                        <rich:column><h:outputText value="Caution Mar" /></rich:column>
                                        <rich:column><h:outputText value="Enter by" /></rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{item.bookNo}" /></rich:column>
                                <rich:column><h:outputText value="#{item.seqNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.instNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.billType}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.amount}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.dt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.branchcode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.infoof}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.issuecode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.repcode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.drawcode}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.orgndt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.lostdt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.cirNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.cirdt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.cautionMark}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.enterby}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InstrumentActiveRegister.select}" reRender="subbodyPanel" focus="btnUpdate">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{item}" target="#{InstrumentActiveRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InstrumentActiveRegister.currentRow}" />
                                    </a4j:commandLink>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{item}" target="#{InstrumentActiveRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InstrumentActiveRegister.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="updatePanel" autosized="true" width="200" onshow="#{rich:element('btnYesUpdate')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure To Unmark the Lost Instrument ?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYesUpdate" ajaxSingle="true" action="#{InstrumentActiveRegister.update}"
                                                                   onclick="#{rich:component('updatePanel')}.hide();" reRender="stxtMessage,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,footerPanel,gridPanel103,taskList" focus="btnRefresh"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" id="btnNoUpdate" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('btnYesDelete')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Delete this transaction?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYesDelete" ajaxSingle="true" action="#{InstrumentActiveRegister.delete}"
                                                                   oncomplete="#{rich:component('deletePanel')}.hide();" reRender="stxtMessage,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,footerPanel,gridPanel103,taskList" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" id="btnNoDelete" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnUpdate" tabindex="7" value="Updation" reRender="stxtMessage,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,footerPanel,gridPanel103,taskList" oncomplete="#{rich:component('updatePanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" tabindex="8" value="Refresh" reRender="stxtMessage,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,footerPanel,gridPanel103,taskList" action="#{InstrumentActiveRegister.setRefresh}" focus="lstBilltype"/>
                            <a4j:commandButton id="btnExit" tabindex="9" value="Exit" action="#{InstrumentActiveRegister.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
