<%-- 
    Document   : transactiondelete
    Created on : Aug 19, 2011, 2:26:59 PM
    Author     : Sudhir Kr Bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Maintenanace Of DDS Entries</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="TransactionDelete"/>
            <a4j:form id="Form">
                <h:panelGrid id="tranDeletePanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="label" value="#{TransactionDelete.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Maintenanace Of DDS Entries"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="label" value="#{TransactionDelete.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errMsg51" value="#{TransactionDelete.message}"  styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid id="pn1" columnClasses="col2,col7,col2,col7" columns="4" styleClass="row2">
                        <h:outputLabel id="acType" styleClass="label" value="Agent Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="acTypeList" styleClass="ddlist" size="1" style="width:120px" value="#{TransactionDelete.actype}">
                                <f:selectItems value="#{TransactionDelete.acTypeList}"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="acCodeList" styleClass="ddlist" size="1" style="width:150px" value="#{TransactionDelete.acCode}">
                                <f:selectItems value="#{TransactionDelete.acCodeList}"/>
                                <a4j:support event="onblur" action="#{TransactionDelete.cmbAgcodeKeyDown}" oncomplete="if(#{TransactionDelete.flag=='false'})
                                             {#{rich:element('ddsGrid')}.style.display=none;}
                                             else {#{rich:element('ddsGrid')}.style.display='';}"
                                             reRender="agentNameText,errMsg51,ddsGrid,ddsList,totalAmount1"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="agentName" styleClass="label" value="Agent Name"/>
                        <h:outputLabel id="agentNameText" styleClass="label" value="#{TransactionDelete.name}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2" columns="2" id="tranDelete" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="totalAmount" styleClass="label" value="Total Amount"/>
                        <h:outputText id="totalAmount1" styleClass="msg" value="#{TransactionDelete.totalAmount}"/>
                    </h:panelGrid>

                    <h:panelGrid id="ddsGrid" style="width:100%;height:0px;display:none;" columnClasses="vtop" styleClass="row2">
                        <rich:dataTable id="ddsList" value="#{TransactionDelete.transGrid}" var="dataItem" rowClasses="gridrow1, gridrow2" rows="10"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="10"><h:outputText value=""/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                    <rich:column ><h:outputText value="Receipt No"/></rich:column>
                                    <rich:column width="180px" ><h:outputText value="Account No"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="Amount"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="Rec No"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="Token Paid"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="Token No"/></rich:column>
                                    <rich:column width="180px"><h:outputText value="Enter By"/></rich:column>
                                    <%--rich:column width="180px"><h:outputText value="Delete"/></rich:column--%>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataItem.sno}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.receiptNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.accountNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.crAmt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.recno}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.tokenPaid}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.tokenNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.enterBy}"/>
                            </rich:column>
                            <%--rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  ajaxSingle="true" id="selectlink1" oncomplete="#{rich:component('rowDeleteConfPanel1')}.show();#{rich:element('ddsGrid')}.style.display='';" reRender="ddsGrid,ddsList,errMsg51">
                                    <h:graphicImage id="imagerender1" value="/resources/images/delete.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TransactionDelete.currentTranDeleteTable}"/>
                                </a4j:commandLink>
                            </rich:column--%>
                        </rich:dataTable>
                        <rich:datascroller id="stopDataScroll" align="left" for="ddsList" maxPages="20"/>
                    </h:panelGrid>

                    <h:panelGrid id="deleteFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="deleteBtnPanel">
                            <a4j:commandButton id="deleteAll" value="Delete All"  oncomplete="#{rich:component('rowDeleteConfPanel')}.show()" reRender="totalAmount1,rowDeleteConfPanel,ddsGrid,ddsList,errMsg51,acTypeList"/>
                            <a4j:commandButton id="Refresh" value="Refresh" action="#{TransactionDelete.refresh}" oncomplete="#{rich:element('ddsGrid')}.style.display=none;" reRender="totalAmount1,ddsGrid,ddsList,errMsg51,acTypeList,agentNameText,acCodeList"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{TransactionDelete.exitBtnAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <rich:modalPanel id="rowDeleteConfPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                        <f:facet name="header">
                            <h:outputText value="Confirmation DialogBox" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to delete all records in table?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:region id="yesBtn">
                                                <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{TransactionDelete.deleteAllRows}"
                                                                   onclick="#{rich:component('rowDeleteConfPanel')}.hide();"
                                                                   oncomplete="if(#{TransactionDelete.flag=='false'})
                                                                   {#{rich:element('ddsGrid')}.style.display=none;}
                                                                   else {#{rich:element('ddsGrid')}.style.display='';}" reRender="totalAmount1,ddsGrid,ddsList,errMsg51,acTypeList,agentNameText,acCodeList"/>
                                            </a4j:region>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton id="No" value="No" onclick="#{rich:component('rowDeleteConfPanel')}.hide();return false;"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>

                    <rich:modalPanel id="rowDeleteConfPanel1" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                        <f:facet name="header">
                            <h:outputText value="Confirmation DialogBox" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to delete the selected row?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:region id="yesBtn">
                                                <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{TransactionDelete.deleteRows}"
                                                                   onclick="#{rich:component('rowDeleteConfPanel1')}.hide();"
                                                                   oncomplete="if(#{TransactionDelete.flag=='false'})
                                                                   {#{rich:element('ddsGrid')}.style.display=none;}
                                                                   else {#{rich:element('ddsGrid')}.style.display='';}" reRender="totalAmount1,ddsGrid,ddsList,errMsg51,acTypeList,agentNameText,acCodeList"/>
                                            </a4j:region>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton id="No" value="No" onclick="#{rich:component('rowDeleteConfPanel1')}.hide();return false;"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
