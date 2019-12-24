<%--

    Document   : DailyGlbMasterHO
    Created on : Oct 25, 2010, 3:50:45 PM
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Daily GLB Master</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyGlbMasterHO.newDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Daily GLB Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyGlbMasterHO.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" style="text-align:center;" styleClass="row2">
                        <h:outputText id="txtError" styleClass="error" value="#{DailyGlbMasterHO.error}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2"  style="height:30px;" columnClasses="col8,col8" width="100%">
                        <h:panelGrid id="gridPanel2" columns="2" columnClasses="col8,col8" style="height:30px;text-align:center" styleClass="row1" width="100%">
                            <h:panelGroup>
                                <h:outputLabel id="lblType" styleClass="label" value="Type"/>
                                <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" value="#{DailyGlbMasterHO.type}">
                                    <f:selectItems value="#{DailyGlbMasterHO.typeList}"/>
                                    <a4j:support event="onblur"  action="#{DailyGlbMasterHO.onChange()}"  reRender="txtError"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col2,col2,col7,col7" columns="4" id="gridPanel10" width="100%">
                        <h:panelGrid columnClasses="col9,col9" styleClass="row2" columns="2" id="gridPanel19" width="100%">
                            <h:outputLabel id="lblCode" styleClass="label" value="Group Code :"/>
                            <h:inputText id="txtCode" styleClass="input" value="#{DailyGlbMasterHO.groupCode}" style="width:60px;" maxlength="4">
                                <a4j:support action="#{DailyGlbMasterHO.gCode()}" event="onblur" focus="txtSGroup" reRender="txtCode,txtError,txtSGroup,txtSubSub,txtSubSubSub,txtDescript,ddAcNo,txtAcNo,txtAccNo,ddAcNo1,txtAcNo1,txtAccNo1,gridPanel9"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" styleClass="row2" columns="2" id="gridPanel12" width="100%">
                            <h:outputLabel id="lblSGroup" styleClass="label" value="Sub Group Code :"/>
                            <h:panelGroup id="groupPanel8" layout="block">
                                <h:inputText id="txtSGroup" styleClass="input" value="#{DailyGlbMasterHO.subGroupCode}"style="width:60px;" >
                                    <a4j:support action="#{DailyGlbMasterHO.subGroupCodeFunc()}" event="onblur" reRender="txtSGroup,ddAcNo,txtAcNo,txtAccNo,ddAcNo1,txtAcNo1,txtAccNo1,txtError"/>
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" styleClass="row2" columns="2" id="gridPanel14" width="100%">
                            <h:outputLabel id="lblSubSub" styleClass="label"  value="Sub Sub Group Code :"/>
                            <h:panelGroup id="groupPanel18" layout="block">
                                <h:inputText id="txtSubSub" styleClass="input" value="#{DailyGlbMasterHO.subSubGroupCode}"style="width:60px;">
                                    <a4j:support action="#{DailyGlbMasterHO.subSubGroupCodeFunc()}" event="onblur" reRender="txtSubSub,ddAcNo,txtAcNo,txtAccNo,ddAcNo1,txtAcNo1,txtAccNo1,txtError"/>
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" styleClass="row2" columns="2" id="gridPanel141" width="100%">
                            <h:outputLabel id="lblSubSubSub" styleClass="label"  value="Sub Sub Sub Group Code:"/>
                            <h:panelGroup id="groupPanel181" layout="block">
                                <h:inputText id="txtSubSubSub" styleClass="input" style="width:60px;" value="#{DailyGlbMasterHO.subSubSubGroupCode}">
                                    <a4j:support action="#{DailyGlbMasterHO.subSubSubGroupCodeFunc()}" event="onblur" reRender="txtSubSubSub,gridPanel15,gridPanel55,gridPanel9,txtError"/>
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col14" columns="2" id="gridPanel21" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDescript" styleClass="label"  value="Description :"/>
                        <h:inputText id="txtDescript" styleClass="input" style="width:452px" value="#{DailyGlbMasterHO.description}">
                            <a4j:support  action="#{DailyGlbMasterHO.descriptionFunc()}" event="onblur" reRender="txtError,txtDescript"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel15" style="height:30px;"  width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel151" style="height:30px;" width="100%" styleClass="row2">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Sub Code :"/>    
                            <h:inputText id="txtAcNo" maxlength="8" disabled="#{DailyGlbMasterHO.disableSubCode}"styleClass="input" style="width:150px;" value="#{DailyGlbMasterHO.subCodeText}">
                                <a4j:support action="#{DailyGlbMasterHO.getGlSubCodeDesc}" event="onblur" focus="txtAccNo" reRender="txtAccNo,txtError,txtAcNo,txtAcNo,txtAccNo,subCode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel1511" style="height:30px;" width="100%" styleClass="row2">
                            <h:outputLabel id="subCode" value="#{DailyGlbMasterHO.newSubCode}" styleClass="label"/>
                            <h:inputText id="txtAccNo" disabled="#{DailyGlbMasterHO.disableSubCode}"styleClass="input" value="#{DailyGlbMasterHO.subCodeDesc}" style="width:150px;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel55" style="height:30px;" width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel551" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAcNo1" styleClass="label" value="Sub Sub Code :"/>
                            <h:inputText id="txtAcNo1" maxlength="8" disabled="#{DailyGlbMasterHO.disableSubSubCode}"styleClass="input"  style="width:150px" value="#{DailyGlbMasterHO.subSubCodeText}">
                                <a4j:support event="onblur"  action="#{DailyGlbMasterHO.getGlSubSubCodeDesc}"  focus="txtAccNo1"reRender="txtAccNo1,txtError,txtAcNo1,txtAccNo1,subsubcode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel552" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="subsubcode" value="#{DailyGlbMasterHO.newSubSubCode}" styleClass="label"/>
                            <h:inputText id="txtAccNo1" disabled="#{DailyGlbMasterHO.disableSubSubCode}"styleClass="input" style="width:150px;" value="#{DailyGlbMasterHO.subSubCodeDesc}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel9" style="height:30px;" width="100%">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel91" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAcNo2" styleClass="label" value="Sub Sub Sub Code :"/>    
                            <h:inputText id="txtAcNo2" maxlength="8" styleClass="input" disabled="#{DailyGlbMasterHO.disableSubSubSubCode}" style="width:150px" value="#{DailyGlbMasterHO.subSubSubCodeAcc}">
                                <a4j:support event="onblur"  action="#{DailyGlbMasterHO.getGlSubSubSubCodeDesc}" focus="txtAccNo2" reRender="txtAccNo2,txtError,txtAcNo2,txtAccNo2,subsubsubcode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="gridPanel92" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="subsubsubcode" value="#{DailyGlbMasterHO.newSubSubSubCode}" styleClass="label"/>
                            <h:inputText id="txtAccNo2" styleClass="input"disabled="#{DailyGlbMasterHO.disableSubSubSubCode}" style="width:150px;" value="#{DailyGlbMasterHO.subSubSubCodeDesc}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid styleClass="row2" columns="1" id="gridPanel51" width="100%">
                        <h:outputLabel id="lblCodeDes" styleClass=".dr-pnl-b1" value="Code Description :"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel25" columns="2" columnClasses="col9,col9" width="100%">
                        <h:panelGrid id="gridPanel27" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblACType" styleClass="label" value="A/C Type :"/>
                            <h:selectOneListbox id="ddACType" styleClass="ddlist" size="1" style="width:40%" value="#{DailyGlbMasterHO.acType}">
                                <f:selectItems value="#{DailyGlbMasterHO.acctDescOption}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel22" columns="2" columnClasses="col9,col9" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblACStatus" styleClass="label" value="A/C Status :"/>
                            <h:selectOneListbox id="ddACStatus" styleClass="ddlist" size="1" style="width:40%" value="#{DailyGlbMasterHO.acStatus}" >
                                <f:selectItems value="#{DailyGlbMasterHO.acStatusList}"/>
                                <%--    <a4j:support action="#{DailyGlbMasterHO.getDetailsForTable}" event="onblur" reRender="btnPost,taskList,txtError,txtCode,txtSGroup,txtSubSub,txtSubSubSub,txtDescript,txtAcNo,txtAccNo1,txtAcNo2,txtAccNo2,btnPost"/> --%>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="vtop" columns="1" id="gridPanel34" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable value="#{DailyGlbMasterHO.dglbmasList}" var="dataItem" rowClasses="gridrow2, gridrow2" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
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
                                    <rich:column><h:outputText value="Delete"/></rich:column>
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
                                <a4j:commandLink ajaxSingle="true" id="selectlink" onclick="#{rich:component('deletePanel')}.show();">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DailyGlbMasterHO.dailyGlbMast}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        <rich:modalPanel id="deletePanel" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to delete the record permanently?" style="padding-right:15px;" />
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
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DailyGlbMasterHO.deleteCurrentData}"
                                                                   onclick="#{rich:component('deletePanel')}.hide();" reRender="taskList,txtError"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPost" value="Save" action="#{DailyGlbMasterHO.saveGriddata}" reRender="btnPost,taskList,txtError,txtCode,txtSGroup,txtSubSub,txtSubSubSub,txtDescript,txtAcNo,txtAccNo1,txtAcNo2,txtAccNo2,btnPost,subCode,subsubcode,subsubsubcode" focus="errorPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DailyGlbMasterHO.refreshForm}" reRender="errorPanel,gridPanel1,gridPanel10,gridPanel21,gridPanel15,gridPanel55,gridPanel25,gridPanel34,gridPanel9,subCode,subsubcode,subsubsubcode"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{DailyGlbMasterHO.btnExit_action}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
