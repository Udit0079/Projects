<%-- 
    Document   : employeeSearchPopUp
    Created on : Jun 1, 2012, 5:02:58 PM
    Author     : Ankit Verma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<a4j:form>
    <h:panelGrid id="mainPanelEmployeeSearch1"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
        <h:panelGrid id="PanelGridEmptable1"  styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
            <h:panelGroup layout="block">
                <h:outputLabel value="Search Employee" styleClass="label"/>
                &nbsp;
                <h:inputText id="txtSearchValue1" value="#{EmployeeSearchPopUp.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                &nbsp;
                <h:outputLabel value="By" styleClass="label"/>
                &nbsp;
                <h:selectOneListbox id="ddSearchCriteria1" value="#{EmployeeSearchPopUp.empSearchCriteria}" size="1">
                    <f:selectItem itemValue="Name"/>
                    <f:selectItem itemValue="ID"/>
                </h:selectOneListbox>
                <a4j:commandButton value="Find" action="#{EmployeeSearchPopUp.getEmployeeTableData}" reRender="table1,taskList1,errMsgGeneralDetails"/>
            </h:panelGroup>

        </h:panelGrid>

        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
            <a4j:region>
                <rich:dataTable value="#{EmployeeSearchPopUp.empSearchTable}" var="dataItem"
                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                            <rich:column><h:outputText value="Address" style="text-align:left"/></rich:column>
                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                            <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.empAddress}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                    <rich:column style="text-align:center;width:40px">
                        <a4j:commandLink id="selectlink" action="#{EmployeeSearchPopUp.setEmpRowValues}"
                                         oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                                         #{rich:element('calJoiningDate')}.style=setMask();
                                         #{rich:element('calWeddingDate')}.style=setMask();
                                         #{rich:element('calAdolescentDate')}.style=setMask();
                                         #{rich:element('calRelayDate')}.style=setMask();
                                         #{rich:component('popUpGridPanel')}.hide()"

                                         reRender="mainPanel,calDateofbirth,calJoiningDate,calWeddingDate,popUpGridPanel,calAdolescentDate,calRelayDate,membershipGroup,idPanelJobDetails,idPanelreferenceDetails,idPanelDependentDetails,idPanelExperienceDetails,idPanelTransportDetails,idPanelMembershipDetails,idPanelOtherDetails,idPanelQualification,father_husband_details_panel,_moreDetailsPanel,Adolescent_moreDetailsPanel,footerPanelGeneralDetails,Permanent_moreDetailsPanel,leftPanel,Contact_moreDetailsPanel,idPanelEmpOtherDetails" focus="selectlink">
                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{dataItem}" target="#{EmployeeSearchPopUp.currentEmpItem}"/>
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="taskList1" maxPages="10" />
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
        <a4j:commandButton id="close" value="Close"  onclick="#{rich:component('popUpGridPanel')}.hide()"    oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                           #{rich:element('calJoiningDate')}.style=setMask();
                           #{rich:element('calWeddingDate')}.style=setMask();
                           #{rich:element('calAdolescentDate')}.style=setMask();
                           #{rich:element('calRelayDate')}.style=setMask();"
                           reRender="calDateofbirth,calJoiningDate,calWeddingDate,popUpGridPanel,calAdolescentDate,calRelayDate,popUpGridPanel,mainPanel"/>
    </h:panelGrid>
</a4j:form>                         