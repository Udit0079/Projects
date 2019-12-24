<%-- 
    Document   : legalDocumentMaster
    Created on : May 14, 2010, 4:04:41 PM
    Author     : Jitendra kumar chaudhary
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Legal Document Master</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{legalDocumentMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Legal Document Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{legalDocumentMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="gridPanel15" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMasterForLegalDocuments" styleClass="label" value="Master For Legal Documents" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15q2" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                <h:outputText id="stxtMsg" styleClass="error" value="#{legalDocumentMaster.message}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15q" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblAccountNature" styleClass="label" value="Account Nature"><font class="required">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAccountNature" styleClass="ddlist" size="1" style="width: 103px" value="#{legalDocumentMaster.acctNature}" tabindex="1" >
                                    <a4j:support action="#{legalDocumentMaster.getTableDetails}"  event="onblur"  focus="txtLegalDocument"
                                                 reRender="p4,stxtMsg" limitToList="true" />
                                    <f:selectItem itemValue="--SELECT--"/>
                                    <f:selectItems value="#{legalDocumentMaster.acctNatureOption}" />
                                </h:selectOneListbox>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel18" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblLegalDocument" styleClass="label" value="Legal Document"/>
                                <h:inputText id="txtLegalDocument"  styleClass="input" style="width:130px" value="#{legalDocumentMaster.legalDoct}" tabindex="2" >
                                    <a4j:support  event="onblur"  focus="btnSave"  limitToList="true" />                                    
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15q1" style="height:30px;" styleClass="row2" width="100%">

                            </h:panelGrid>                          

                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:135px;">
                            <h:panelGroup id="p4">
                                <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                   actionListener="#{legalDocumentMaster.fetchCurrentRow}">
                                        <a4j:actionparam name="acNo" value="{acNo}" />
                                        <a4j:actionparam name="row" value="{currentRow}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{legalDocumentMaster.legalDocuments}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="2" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="6"><h:outputText value="Legal Document master" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account Type" /></rich:column>
                                                <rich:column><h:outputText value="Legal Document" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column>
                                                <rich:column><h:outputText value="Code" /></rich:column>
                                                <rich:column><h:outputText value="Tran Time" /></rich:column>
                                                <rich:column><h:outputText value="Action" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.accType}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.legalDocument}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.code}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.tranTime}" /></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{legalDocumentMaster.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{legalDocumentMaster.currentRow}" />
                                            </a4j:commandLink>                                            
                                        </rich:column>

                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20" />
                                </a4j:region>
                                <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                    <f:facet name="header">
                                        <h:outputText value="Are You Sure, You Want to Delete this Record?" style="padding-right:15px;" />
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                            <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" ajaxSingle="true" action="#{legalDocumentMaster.delete}"
                                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="p4,gridPanel103,stxtMsg" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGroup>
                        </h:panelGrid>

                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton tabindex="3" id="btnSave" value="Save" style="width:60px;" action="#{legalDocumentMaster.save}" reRender="stxtMsg,txtLegalDocument,gridPanel103,ddAccountNature,taskList" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" style="width:60px;" action="#{legalDocumentMaster.clearText}" tabindex="5" reRender="txtLegalDocument,taskList,ddAccountNature,gridPanel103,stxtMsg" focus="ddAccountNature"/>
                            <a4j:commandButton id="btnExit" value="Exit" style="width:60px;" action="#{legalDocumentMaster.exitForm}" tabindex="4" reRender="txtLegalDocument,taskList,ddAccountNature,gridPanel103,stxtMsg"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
