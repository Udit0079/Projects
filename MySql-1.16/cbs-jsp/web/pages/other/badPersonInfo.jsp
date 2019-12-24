<%-- 
    Document   : badPersonInfo
    Created on : Apr 8, 2016, 11:05:28 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Sanctions master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BadPersonInfo.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Sanctions master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BadPersonInfo.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{BadPersonInfo.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{BadPersonInfo.function}">
                                    <f:selectItems value="#{BadPersonInfo.functionList}"/>
                                    <a4j:support action="#{BadPersonInfo.chgFunction}" event="onblur" oncomplete="setMask();" reRender="message,stxtcustAdd,stxtcustDob,table,btnSave"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblSections" styleClass="label" value="Sections" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddSections" styleClass="ddlist" size="1" style="width:150px;" value="#{BadPersonInfo.sections}">
                                    <f:selectItems value="#{BadPersonInfo.sectionsList}"/>
                                    <a4j:support event="onblur" action="#{BadPersonInfo.sectionsAction}" oncomplete="setMask();" reRender="mainPanel,message,Row2,Row3,Row4,Row5,Row6,table,btnSave"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblPersonId" styleClass="label" value="Person Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="stxtPersonId" styleClass="input" style="width:80px;" value="#{BadPersonInfo.badPerId}"maxlength="12" onkeydown="this.value=this.value.toUpperCase();">
                                    <a4j:support action="#{BadPersonInfo.chgSections}" event="onblur" oncomplete="setMask();" reRender="message,Row2,Row3,Row4,Row5,Row6,table,btnSave"/>
                                </h:inputText> 
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblTitle" styleClass="label" value="Title" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="stxtTitle" styleClass="input" style="width:80px;" value="#{BadPersonInfo.title}" maxlength="12" onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblName" styleClass="label" value="Name" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtName" styleClass="input" style="width:150px;" value="#{BadPersonInfo.name}" onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblDob" styleClass="label" value="Dob" ><font class="required" style="color:red;">*</font> </h:outputLabel> 
                                <h:inputText id="txtDob" styleClass="input chkDt" value="#{BadPersonInfo.dob}" style="width:80px;" >
                                    <a4j:support oncomplete="setMask();"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblPob" styleClass="label" value="Pob" ><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:inputText id="txtPob" styleClass="input" style="width:150px;" value="#{BadPersonInfo.pob}" onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblDesig" styleClass="label" value="Designation"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:inputText id="txtDesig" styleClass="input" style="width:150px;" value="#{BadPersonInfo.designation}" onkeydown="this.value=this.value.toUpperCase();"/>    
                                <h:outputLabel id="lblAddress" styleClass="label" value="Address"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:inputText id="txtAddress" styleClass="input" style="width:150px;" value="#{BadPersonInfo.address}" onkeydown="this.value=this.value.toUpperCase();"/>    
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblListedNo" styleClass="label" value="Listed No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtListedNo" styleClass="input" style="width:100px;" value="#{BadPersonInfo.listedNo}" onkeydown="this.value=this.value.toUpperCase();"/>    
                                <h:outputLabel id="lblgoodQuality" styleClass="label" value="Good Quality" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="stxtgoodQuality" styleClass="input" style="width:150px;" value="#{BadPersonInfo.goodQuality}" onkeydown="this.value=this.value.toUpperCase();" /> 
                                <h:outputLabel id="lbllowQuality" styleClass="label" value="Low Quality"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="stxtlowQuality" styleClass="input" style="width:150px;" value="#{BadPersonInfo.lowQuality}" onkeydown="this.value=this.value.toUpperCase();"/> 
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblPassportNo" styleClass="label" value="Passport No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtPassportNo" styleClass="input" style="width:100px;" value="#{BadPersonInfo.passportNo}"onkeydown="this.value=this.value.toUpperCase();"/> 
                                <h:outputLabel id="lblNationality" styleClass="label" value="Nationality"><font class="required" style="color:red;">*</font> </h:outputLabel>
                                <h:selectOneListbox id="ddNationality" styleClass="ddlist" size="1" style="width:100px;" value="#{BadPersonInfo.nationality}">
                                    <f:selectItems value="#{BadPersonInfo.nationalityList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblNationIdNo" styleClass="label" value="National Identification No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtNationIdNo" styleClass="input" style="width:150px;" value="#{BadPersonInfo.nationalIdNo}" onkeydown="this.value=this.value.toUpperCase();"/>
                                <%--h:panelGroup id="nationalId">
                                    <h:selectOneRadio id="ddAllNation" style="width:140px;" styleClass="label" value="#{BadPersonInfo.national}">
                                     <f:selectItems value="#{BadPersonInfo.nationalList}"/>
                                        <a4j:support  action="#{BadPersonInfo.checkBoxFunc}" event="onchange" reRender="txtNationalityId"/>   
                                    </h:selectOneRadio>
                                </h:panelGroup>
                            <h:inputText id="txtNationalityId" styleClass="input" style="width:80px;" value="#{BadPersonInfo.nationalName}" disabled="#{BadPersonInfo.disableNational}"onkeydown="this.value=this.value.toUpperCase();"/--%>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblOtherInfo" styleClass="label" value="Other Information" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtOtherInfo" styleClass="input" style="width:150px;" value="#{BadPersonInfo.otherInfo}"onkeydown="this.value=this.value.toUpperCase();"/>    
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{BadPersonInfo.gridDetail}" var="dataItem"
                                                    rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="13"><h:outputText value="Bad Person Details"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Sections"/></rich:column>
                                                <rich:column><h:outputText value="Id No."/></rich:column>
                                                <rich:column><h:outputText value="Customer Name"/></rich:column>
                                                <rich:column><h:outputText value="Dob"/></rich:column>
                                                <rich:column><h:outputText value="Pob"/></rich:column>
                                                <rich:column><h:outputText value="Designation"/></rich:column>
                                                <rich:column><h:outputText value="Address"/></rich:column>
                                                <rich:column><h:outputText value="Good Quality"/></rich:column>
                                                <rich:column><h:outputText value="Low Quality"/></rich:column>
                                                <rich:column><h:outputText value="Passport No."/></rich:column>
                                                <rich:column><h:outputText value="Nationality"/></rich:column>
                                                <rich:column><h:outputText value="Enter By"/></rich:column>
                                                <rich:column><h:outputText value="Enter Date"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.sec}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.perId}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.custName}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.dob}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.pob}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.designation}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.address}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.goodQuality}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.lowQuality}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.passportNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.nationality}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enterDate}"/></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave" value="#{BadPersonInfo.btnValue}" action="#{BadPersonInfo.populateMessage}" disabled="#{BadPersonInfo.btnFlag}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="leftPanel,mainPanel,processPanel,confirmid"/>
                                    <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BadPersonInfo.refreshForm}" oncomplete="setMask();" reRender="mainPanel"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{BadPersonInfo.exitBtnAction}" reRender="mainPanel"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <a4j:region id="processActionRegion">
                    <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{BadPersonInfo.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{BadPersonInfo.saveMasterDetail}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="message,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>