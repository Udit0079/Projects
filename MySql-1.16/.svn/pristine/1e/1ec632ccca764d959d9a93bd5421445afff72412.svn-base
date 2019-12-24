<%--

    Document   : DailyGLBMaster
    Created on : Oct 25, 2010, 3:50:45 PM
    Author     : Sudhir Kr Bisht
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Daily GLB Master Update</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyGLBMasterUpdate.newDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Daily GLB Master Update"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyGLBMasterUpdate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" style="text-align:center;" styleClass="row2">
                        <h:outputText id="txtError" styleClass="error" value="#{DailyGLBMasterUpdate.error}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelType"  columnClasses="col7,col3,col2,col2"columns="4" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:panelGroup id="p1" layout="block">
                            <h:outputLabel id="lblType" styleClass="label"  value="Type"/>
                            <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" style="width:50%" value="#{DailyGLBMasterUpdate.type}">
                                <f:selectItems value="#{DailyGLBMasterUpdate.typeList}"/>
                                <a4j:support action="#{DailyGLBMasterUpdate.clickOnType}" event="onchange" focus="ddGroupCode" reRender="ddGroupCode,ddSubGroupCode,ddSubSubGroupCode,ddSubSubSubGroupCode,gridPanel15,gridPanel55,gridPanel9,taskList"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col2,col7,col7" columns="4" id="gridPanel10" width="100%">
                        <h:panelGrid columnClasses="col8,col8" styleClass="row2" columns="2" id="gridPanel19" width="100%">
                            <h:outputLabel id="lblCode" styleClass="label" value="Group Code :"/>
                            <h:inputText id="txtGroupCode" styleClass="input" disabled="true" style="width:150px" value="#{DailyGLBMasterUpdate.groupCode}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" styleClass="row2" columns="2" id="gridPanel12" width="100%">
                            <h:outputLabel id="lblSGroup" styleClass="label" value="Sub Group Code :"/>
                            <h:inputText id="txtSubGroupCode" styleClass="input" disabled="true" style="width:150px" value="#{DailyGLBMasterUpdate.subGroupCode}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" styleClass="row2" columns="2" id="gridPanel14" width="100%">
                            <h:outputLabel id="lblSubSub" styleClass="label"  value="Sub Sub Group Code :"/>
                            <h:inputText id="txtSubSubGroupCode" styleClass="input" disabled="true" style="width:150px" value="#{DailyGLBMasterUpdate.subSubGroupCode}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" styleClass="row2" columns="2" id="gridPanel141" width="100%">
                            <h:outputLabel id="lblSubSubSub" styleClass="label"  value="Sub Sub Sub Group Code:"/>
                            <h:inputText id="txtSubSubSubGroupCode" styleClass="input" disabled="true" style="width:150px" value="#{DailyGLBMasterUpdate.subSubSubGroupCode}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col14" columns="2" id="gridPanel21" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDescript" styleClass="label"  value="Description :"/>
                        <h:inputText id="txtDescript"  styleClass="input" style="width:452px" value="#{DailyGLBMasterUpdate.description}">
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel15" style="height:30px;" width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel151" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Sub Code :"/>    
                            <h:inputText id="txtAcNo"styleClass="input"  maxlength="8" style="width:150px;" disabled="#{DailyGLBMasterUpdate.disableSubCodeText}" value="#{DailyGLBMasterUpdate.subCode1}">
                                <a4j:support action="#{DailyGLBMasterUpdate.getGlSubCodeDesc}" event="onblur" reRender="txtAccNo,txtError,newsubcode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel152" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="newsubcode" styleClass="label" value="#{DailyGLBMasterUpdate.newSubCode}"/>
                            <h:inputText id="txtAccNo" styleClass="input" style="width:150px;" disabled="#{DailyGLBMasterUpdate.disableSubCodeText}" value="#{DailyGLBMasterUpdate.subCode2}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel55" style="height:30px;" width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel553" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAcNo1" styleClass="label" value="Sub Sub Code :"/>
                            <h:inputText id="txtAcNo1" styleClass="input" maxlength="8" style="width:150px" disabled="#{DailyGLBMasterUpdate.disableSSCText}" value="#{DailyGLBMasterUpdate.subSubCode1}">
                                <a4j:support action="#{DailyGLBMasterUpdate.getGlSubSubCodeDesc}" event="onblur" reRender="txtAccNo1,txtError,newsubsubcode"/>
                            </h:inputText>
                       </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel554" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="newsubsubcode" styleClass="label" value="#{DailyGLBMasterUpdate.newSubSubCode}"/>
                            <h:inputText id="txtAccNo1" styleClass="input" style="width:150px" disabled="#{DailyGLBMasterUpdate.disableSSCText}" value="#{DailyGLBMasterUpdate.subSubCode2}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel9" style="height:30px;" width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel91" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAcNo2" styleClass="label" value="Sub Sub Sub Code :"/>    
                            <h:inputText id="txtAcNo2" styleClass="input" maxlength="8" style="width:150px" disabled="#{DailyGLBMasterUpdate.disableSSSCText}" value="#{DailyGLBMasterUpdate.subSubSubCode1}">
                                <a4j:support action="#{DailyGLBMasterUpdate.getGlSubSubSubCodeDesc}" event="onblur" reRender="txtAccNo2,txtError,newsubsubsubcode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel92" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="newsubsubsubcode" styleClass="label" value="#{DailyGLBMasterUpdate.newSubSubSubCode}"/>
                            <h:inputText id="txtAccNo2" styleClass="input" style="width:150px" disabled="#{DailyGLBMasterUpdate.disableSSSCText}" value="#{DailyGLBMasterUpdate.subSubSubCode2}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid styleClass="row1" columns="1" id="gridPanel51" width="100%">
                        <h:outputLabel id="lblCodeDes" styleClass="label" value="Code Description :"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel25" columns="2" columnClasses="col9,col9" width="100%" styleClass="row2">
                        <h:panelGrid id="gridPanel27" columns="2" columnClasses="col9,col9" style="height:30px;" width="100%">
                            <h:outputLabel id="lblACType" styleClass="label" value="A/C Type :"/>
                            <h:selectOneListbox id="ddACType"  disabled="true"styleClass="ddlist" size="1" style="width:40%" value="#{DailyGLBMasterUpdate.acType}">
                                <f:selectItems value="#{DailyGLBMasterUpdate.acctDescOption}"/> 
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel22" columns="2" columnClasses="col9,col9" style="height:30px;" width="100%">
                            <h:outputLabel id="lblACStatus" styleClass="label" value="A/C Status :"/>
                            <h:selectOneListbox id="ddACStatus"styleClass="ddlist" size="1" style="width:40%" value="#{DailyGLBMasterUpdate.acStatus}">
                                <f:selectItems value="#{DailyGLBMasterUpdate.acStatusList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="vtop" columns="1" id="gridPanel34" width="100%" styleClass="row2" style="height:200px;">
                        <%--rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Select" ajaxSingle="true" actionListener="#{DailyGLBMasterUpdate.fetchCurrentRow}">
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu--%>
                        <a4j:region>
                        <rich:dataTable var="dataItem" value="#{DailyGLBMasterUpdate.dglbMasUpdate}" rowClasses="gridrow2, gridrow2" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="16"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"/></rich:column>
                                    <rich:column><h:outputText value="Group Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Group Code" /></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Group Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Sub Group Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Description"/></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Description"/></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Sub Code"/></rich:column>
                                    <rich:column><h:outputText value="Sub Sub Sub Description"/></rich:column>
                                    <rich:column><h:outputText value="Description"/></rich:column>
                                    <rich:column><h:outputText value="A/C Type"/></rich:column>
                                    <rich:column><h:outputText value="A/C Status"/></rich:column>
                                    <rich:column><h:outputText value="GLB A/C Type"/></rich:column>
                                    <rich:column><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.sNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.grpCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.subGrpCode}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.subSubGrpCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.subSubSubGrpCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sCodeDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.ssCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.ssCodeDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sssCode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sssCodeDesc}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.desc}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.actType}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.actStatus}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.glbActType}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" reRender="mainPanel" action="#{DailyGLBMasterUpdate.setRowValues}">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DailyGLBMasterUpdate.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>                        
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnUpdate" value="Update" action="#{DailyGLBMasterUpdate.updateRecord}" reRender="txtError,taskList,mainPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DailyGLBMasterUpdate.refreshForm}" reRender="panelType,gridPanel10,gridPanel21,gridPanel15,gridPanel55,gridPanel9,gridPanel25,gridPanel34,errorPanel,newsubcode,newsubsubcode,newsubsubsubcode"/>
                            <a4j:commandButton id="btnClose" value="Exit"  action="#{DailyGLBMasterUpdate.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
