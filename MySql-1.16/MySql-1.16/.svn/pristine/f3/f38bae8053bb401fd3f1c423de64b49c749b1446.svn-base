<%-- 
    Document   : ManagementDetails
    Created on : Jul 22, 2014, 5:41:33 PM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Management Details</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ManagementDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Management Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ManagementDetails.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="stxtmessage" styleClass="headerLabel" value="#{ManagementDetails.message}"  style="width:100%;color:red"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblCustId" styleClass="label" value="Customerid"/>
                        <h:inputText value="#{ManagementDetails.custid}"id="txtCustId"maxlength="40" styleClass="input" style="100px">
                            <a4j:support actionListener="#{ManagementDetails.getCustomerIdInformation}"  event="onblur" 
                                         oncomplete="{#{rich:element('ddCustStatus')}.focus();}" reRender="lbltitle,ddTitle,lblCustName,txtCustName,lblGender,ddGender,
                                         lblAddr1,txtAddr1,lblAddr2,txtAddr2,lblPrVillage,txtPrVillage,lblPrBlock,txtPrBlock,lblPrCity,ddPrCity,lblPrState,
                                         ddPrState,lblPrCountry,ddPrCountry,lblPrPostal,txtPrPostal,lblPrPhone,txtPrPhone,lblEmplEmail,txtEmplEmail,
                                         lblcustStatus,ddCustStatus,lblQual,ddQual,lblFromDate,txtFromDate,lblToDate,txtToDate,
                                         lblDesg,ddDsg,lblDirRel,ddDirrel,lblDirCustId,txtDirCustID,btnSave,btnUpdate,btnRefresh,btnExit"/>
                        </h:inputText>
                        <h:outputLabel id="lbltitle" styleClass="label" value="Title">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddTitle" styleClass="ddlist" value="#{ManagementDetails.titleType}" size="1">
                            <f:selectItems value="#{ManagementDetails.titleTypeOption}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText value="#{ManagementDetails.custName}" maxlength="200" id="txtCustName" styleClass="input" style="90px" onkeydown="this.value=this.value.toUpperCase();"/>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblGender" styleClass="label" value="Gender">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddGender" styleClass="ddlist" value="#{ManagementDetails.genderType}" size="1">
                            <f:selectItems value="#{ManagementDetails.genderTypeOption}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAddr1" styleClass="label" value="Address Line1">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText value="#{ManagementDetails.add1}" id="txtAddr1" maxlength="255" styleClass="input" style="70px" onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblAddr2" styleClass="label" value="Address Line2"/>
                        <h:inputText value="#{ManagementDetails.add2}" id="txtAddr2" maxlength="255" styleClass="input" style="70px" onkeydown="this.value=this.value.toUpperCase();"/>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblPrVillage" styleClass="label" value="Village / Sector"/>
                        <h:inputText value="#{ManagementDetails.pVillage}" id="txtPrVillage" maxlength="45" styleClass="input" style="70px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblPrBlock" styleClass="label" value="Block"/>
                        <h:inputText value="#{ManagementDetails.pBlock}" id="txtPrBlock" maxlength="45" styleClass="input" style="70px"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblPrCity" styleClass="label" value="City/District">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{ManagementDetails.city}" id="ddPrCity" styleClass="ddlist" size="1">
                            <f:selectItems value="#{ManagementDetails.cityOption}"/>                            
                        </h:selectOneListbox>                                                                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblPrState" styleClass="label" value="State">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{ManagementDetails.state}" id="ddPrState" style="width:72px;" styleClass="ddlist" size="1">
                            <f:selectItems value="#{ManagementDetails.stateOption}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPrCountry" styleClass="label" value="Country">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{ManagementDetails.country}" id="ddPrCountry" styleClass="ddlist" size="1">
                            <f:selectItems value="#{ManagementDetails.countryOption}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPrPostal" styleClass="label" value="Postal Code"/>
                        <h:inputText value="#{ManagementDetails.postalCode}"id="txtPrPostal" maxlength="6" styleClass="input" style="10px"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblPrPhone" styleClass="label" value="Phone No"/>
                        <h:inputText  value="#{ManagementDetails.phoneNo}"id="txtPrPhone" maxlength="15" styleClass="input" style="40px"/>
                        <h:outputLabel id="lblEmplEmail" styleClass="label" value="Email Id"/>
                        <h:inputText value="#{ManagementDetails.empEmail}"id="txtEmplEmail" maxlength="40" styleClass="input" style="100px"/>
                        <h:outputLabel id="lblcustStatus" styleClass="label" value="Director Status">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox value="#{ManagementDetails.custStatus}" id="ddCustStatus" styleClass="ddlist" size="1">
                            <f:selectItems value="#{ManagementDetails.statusOption}"/>                            
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblQual" styleClass="label" value="Qualification">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddQual" styleClass="ddlist" size="1" style="width: 100px" value="#{ManagementDetails.qualVal}">
                            <f:selectItems value="#{ManagementDetails.qualList}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDate" styleClass="headerLabel"  value="Term Since">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText id="txtFromDate"  styleClass="input calInstDate" style="width:60px"  maxlength="10" value="#{ManagementDetails.fromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="Term Upto">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText id="txtToDate"   styleClass="input calInstDate" style="width:60px" maxlength="10"  value="#{ManagementDetails.toDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13,col13,col13" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblDesg" styleClass="label" value="Designation">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddDsg" styleClass="ddlist" size="1" style="width: 100px" value="#{ManagementDetails.dsgVal}">
                            <f:selectItems value="#{ManagementDetails.dsgList}"/>
                            <a4j:support actionListener="#{ManagementDetails.onblurDesgValue}" event="onblur" reRender="ddDirrel,ddDirrel,txtDirCustID,stxtmessage" oncomplete="setMask();" focus="ddDirrel"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDirRel" styleClass="label" value="Director Relative"/>
                        <h:selectOneListbox id="ddDirrel" styleClass="ddlist" size="1" style="width: 100px" value="#{ManagementDetails.directorRelative}">
                            <f:selectItems value="#{ManagementDetails.directorRelList}"/>
                            <%--<a4j:support actionListener="#{ManagementDetails.onblurDesgValue}" event="onblur" reRender="txtDirCustID,stxtmessage" oncomplete="setMask();" />--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDirCustId" styleClass="label" value="Director's CustID"/>
                        <h:inputText id="txtDirCustID"   styleClass="input DirCustId" style="width:60px" maxlength="10"  value="#{ManagementDetails.directorId}"/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{ManagementDetails.tableList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Management Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Title"/></rich:column>
                                        <rich:column><h:outputText value="Name"/></rich:column>
                                        <rich:column><h:outputText value="Designation"/></rich:column>
                                        <rich:column><h:outputText value="Qualification"/></rich:column>
                                        <rich:column><h:outputText value="Address Line1"/></rich:column>
                                        <rich:column><h:outputText value="Address Line2"/></rich:column>
                                        <rich:column><h:outputText value="Email"/></rich:column>
                                        <rich:column><h:outputText value="Term Since"/></rich:column>
                                        <rich:column><h:outputText value="Term Upto"/></rich:column>
                                        <rich:column><h:outputText value="Director Relation"/></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.title}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.desgDetail}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.eduDetail}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.addrLine1}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.addrLine2}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.emailID}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.joinDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.expDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dirRelativeDetail}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ManagementDetails.setTableDataToForm}" reRender="mainPanel" focus="txtCustName">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ManagementDetails.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="Row11" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="Row12" layout="block" >
                            <a4j:commandButton id="btnSave" disabled="#{ManagementDetails.sflag}" value="Save" action="#{ManagementDetails.saveDetail}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnUpdate" disabled="#{ManagementDetails.uflag}" value="Update" action="#{ManagementDetails.updateDetail}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ManagementDetails.reFresh}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ManagementDetails.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>                    
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>                    
                </rich:modalPanel>            
            </a4j:form>
        </body>
    </html>
</f:view>