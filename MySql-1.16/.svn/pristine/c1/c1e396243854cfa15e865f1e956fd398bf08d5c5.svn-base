<%-- 
    Document   : memberRoleMapping
    Created on : 2 Feb, 2018, 3:13:44 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
<html>
    <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Member Role Mapping</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{MemberRoleMapping.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Member Role Mapping"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MemberRoleMapping.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{MemberRoleMapping.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Rowfunction" style="width:100%;text-align:left;" styleClass="row2">
                             <h:outputLabel id="lblfunction" styleClass="label" value="Function"><font class="required" style="color:red;" title="Sector">*</font></h:outputLabel>
                                                <h:selectOneListbox value="#{MemberRoleMapping.function}"id="ddfunction" styleClass="ddlist"  size="1" style="width :120px "  title="Member designation/Role (416)">
                                                    <f:selectItems  value="#{MemberRoleMapping.functionOption}"/>
                                                    <a4j:support action="#{MemberRoleMapping.onblurfunction}" event="onblur"
                                                   reRender="lblMsg,tableGrid,taskList,btnDelete" />
                                                 </h:selectOneListbox>
                             <h:outputLabel></h:outputLabel>
                             <h:outputText></h:outputText>
                             <h:outputLabel></h:outputLabel>
                             <h:outputText></h:outputText>
                            
                        </h:panelGrid>
                       <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                          <h:outputLabel id="lblsector" styleClass="label" value="Designation/Role"><font class="required" style="color:red;" title="Sector">*</font></h:outputLabel>
                                                <h:selectOneListbox value="#{MemberRoleMapping.designation}"id="ddSector" styleClass="ddlist" disabled="#{MemberRoleMapping.disabledesignation}"  size="1" style="width :120px;"  title="Member designation/Role (416)">
                                                    <f:selectItems  value="#{MemberRoleMapping.designationOption}"/>
                                                    <a4j:support action="#{MemberRoleMapping.onblurrefDesignation}" event="onblur"
                                                   reRender="lblMsg,txtref,tableGrid,taskList,btnDelete" />
                                                 </h:selectOneListbox>  
                            <h:outputLabel id="lblrefNo" styleClass="label" value="Ref.No."/>                   
                           <h:inputText id="txtref" styleClass="input" maxlength="12" value="#{MemberRoleMapping.refNo}" disabled="#{MemberRoleMapping.disabledrefNo}" style="width:90px;" >
                                    <a4j:support action="#{MemberRoleMapping.onblurrefDetail}" event="onblur"
                                                   reRender="lblMsg" />
                                </h:inputText>                              
                           <h:outputLabel id="lblFolioNo" styleClass="label" value="Name"/>                   
                           <h:inputText id="txtname" styleClass="input" maxlength="35" value="#{MemberRoleMapping.name}" style="width:150px;" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support action="#{MemberRoleMapping.onblurFolioDetails}" event="onblur"
                                                   reRender="lblMsg" />
                                </h:inputText>                     
                         </h:panelGrid>
                     <h:panelGrid columnClasses="col1,col1,col1,col2,col1,col2" columns="6" id="Row4" style="width:100%;text-align:left;display:#{MemberRoleMapping.displayDesignation2}" styleClass="row2">
                            <h:outputLabel id="lblsector1" styleClass="label" value="Designation/Role"><font class="required" style="color:red;" title="Sector">(for modification)</font></h:outputLabel>
                                                <h:selectOneListbox value="#{MemberRoleMapping.designation1}"id="ddSector1" styleClass="ddlist"  size="1" style="width :120px "  title="Member designation/Role (416)">
                                                    <f:selectItems  value="#{MemberRoleMapping.designationOption1}"/>
                                                 </h:selectOneListbox>
                                                <h:outputLabel></h:outputLabel>
                           <h:outputText></h:outputText>
                           <h:outputLabel></h:outputLabel>
                           <h:outputText></h:outputText>
                          </h:panelGrid>
                           
                      </h:panelGrid>
                          
                     <h:panelGrid columnClasses="vtop" columns="1" id="tableGrid" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{MemberRoleMapping.gridDetail}" var="dataItem"
                                                    rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" 
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="10"><h:outputText value="Member Details"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Designation Type"/></rich:column>
                                                <rich:column><h:outputText value="Ref Id."/></rich:column>
                                                <rich:column><h:outputText value="Member Name"/></rich:column>
                                                <rich:column><h:outputText value="Date"/></rich:column>
                                                <rich:column rendered="#{MemberRoleMapping.selectRender}"><h:outputText value="Select"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.degination}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.refid}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.name}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.date}"/></rich:column>
                                        <rich:column style="text-align:center;width:40px" rendered="#{MemberRoleMapping.selectRender}">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{MemberRoleMapping.selectOperation}"  reRender="normalPanel,message,lblsector1,txtname,txtref,ddSector1,ddSector,Row4">
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{MemberRoleMapping.currentItem}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnDelete" value="Delete"  action="#{MemberRoleMapping.deleteBtnAction}" disabled="#{MemberRoleMapping.deleteflag}" reRender="mainPanel" focus="btnExit"/>
                            <a4j:commandButton id="btnSave" value="Save"  action="#{MemberRoleMapping.saveBtnAction}" reRender="mainPanel" focus="btnExit"/>
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{MemberRoleMapping.refresh}"  reRender="mainPanel,stxtName,stxtFatherName,Row2,btnSave,lblMsg,stxtfolioShow,Row6,Row4" focus="txtShareholder" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{MemberRoleMapping.exitForm}" reRender="leftPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        
    </body>
</html>
</f:view>
