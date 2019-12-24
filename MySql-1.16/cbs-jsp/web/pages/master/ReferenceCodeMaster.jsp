<%--
    Document   : RefrenceCodeMaster
    Created on : Dec 12, 2010, 2:24:35 PM
    Author     : admin
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
            <title>Reference Code Master</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="ReferenceCodeMaster">

                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="header" style="width:100%;text-align:center;" styleClass="header">

                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ReferenceCodeMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Reference Code Master"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ReferenceCodeMaster.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses=",col1,col2,col1,col2,col1,col2" columns="2" id="PanelMessage" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="lblMsg" styleClass="error" style="width:100px;" value="#{ReferenceCodeMaster.message}" />
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1" columns="2" id="PanelFunction" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="LblFunction" styleClass="label"  value="Function"/>
                        <h:selectOneListbox id="function" styleClass="ddlist" required="true"  style="width:100px;" value="#{ReferenceCodeMaster.functionFlag}" size="1">
                            <f:selectItems value="#{ReferenceCodeMaster.functionOption}"/>
                            <a4j:support event="onblur" action="#{ReferenceCodeMaster.clearfun}" focus="txtRefRecNo" reRender="lblMsg,mainPanel,PanelCodeMaster"  />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col2,col1,col2,col1,col2" columns="6" id="PanelCodeMaster" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="lblRefRecNo" styleClass="label"  value="Reference Rec No"/>

                        <h:inputText id="txtRefRecNo" style="width:100px;"value="#{ReferenceCodeMaster.refRecNo}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ReferenceCodeMaster.flag1}" maxlength="3" styleClass="input">
                            <a4j:support action="#{ReferenceCodeMaster.setDataInCodeMaster}" event="onblur"  reRender="ReferenceCodeMasterTable,txtRefCode,txtRefDesc,dataScroll" focus="txtRefCode" />
                        </h:inputText>

                        <h:outputLabel id="lblRefCode" styleClass="label"  value="Reference Code"/>
                        <h:inputText id="txtRefCode" style="width:100px;"value="#{ReferenceCodeMaster.refCode}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{ReferenceCodeMaster.flag2}" maxlength="6" styleClass="input"/>

                        <h:outputLabel id="lblRefDesc" styleClass="label"  value="Reference Description"/>

                        <h:inputText id="txtRefDesc" style="width:100px;" value="#{ReferenceCodeMaster.refDesc}"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{ReferenceCodeMaster.flag3}" maxlength="60" styleClass="input">
                            <a4j:support action="#{ReferenceCodeMaster.setDataInCodeMasterTable}" event="onblur" reRender="lblMsg,panalRefCodeMaster,PanelCodeMaster" focus="btnSave"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col7" columns="1" id="panalRefCodeMaster" width="100%" styleClass="row2" style="height:136px;">
                        <rich:dataTable  value="#{ReferenceCodeMaster.codeMasterList}" var="dataItem"
                                         rowClasses="row1, row2" id="ReferenceCodeMasterTable" rows="10" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Ref Rec No" /></rich:column>
                                    <rich:column><h:outputText value="Reference Code" /></rich:column>
                                    <rich:column><h:outputText value="Reference Desc" /></rich:column>
                                    <rich:column><h:outputText value="Created by user id" /></rich:column>
                                    <rich:column><h:outputText value="Creation Date" /></rich:column>
                                    <rich:column><h:outputText value="Last Updated by User id" /></rich:column>
                                    <rich:column><h:outputText value="Last Updated Date" /></rich:column>
                                    <rich:column><h:outputText value="No of Modifications" /></rich:column>
                                    <rich:column><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.refRecNoTab}"  /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.refCodeTab}"  /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.refDescTab}"  /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.createByUseridTab}"  /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.creationDateTab}"  /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.lastUpdatedByTab}"  /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.lastUpdateDateTab}"  /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.modificationsTab}"  /></rich:column>
                            <rich:column>
                                <a4j:commandLink ajaxSingle="true" id="selectlink" action ="#{ReferenceCodeMaster.selectFromCodeMasterTable}"  reRender="lblMsg,ReferenceCodeMasterTable,PanelCodeMaster" >
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{row}" target="#{ReferenceCodeMaster.currentRowCodeMaster}"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{ReferenceCodeMaster.currentItemCodeMaster}" />                            
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="dataScroll" align="left" for="ReferenceCodeMasterTable" maxPages="20" />
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{ReferenceCodeMaster.saveReferenceCodeMaster}" reRender="lblMsg,mainPanel" />
                             <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{ReferenceCodeMaster.refresh}"  reRender="mainPanel" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ReferenceCodeMaster.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
