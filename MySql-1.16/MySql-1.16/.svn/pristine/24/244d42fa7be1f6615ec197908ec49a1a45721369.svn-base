<%-- 
    Document   : PoDdAdBookAuth
    Created on : Dec 2, 2010, 12:41:28 PM
    Author     : root
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
            <title>PoDdAdBookAuth</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{PoDdAdBookAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{PoDdAdBookAuth.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid    columns="2" columnClasses="col9,col9" id="subbodyPanel1" style="width:100%">
                        <h:panelGrid id="leftPanel" columns="1" style="width:100%" >
                             <h:panelGrid  columns="1" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{PoDdAdBookAuth.message}"/>
                </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                                <h:outputText id="bIssuAuth" styleClass="output" value="Book Issue Authorization"/>
                            </h:panelGrid>
                            <h:panelGrid columns="2"  id="Name" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblName" styleClass="headerLabel"  value="Details About Book Issue"  style="width:100%;text-align:left;"></h:outputLabel>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col9,col9"  style="width:100%;text-align:left;"  columns="2" id="input"  styleClass="row1">
                                 <h:outputLabel id="lblsno" styleClass="headerLabel"  value="S.No"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:inputText id="input1" styleClass="output"  disabled="true" style="color:black;width:70px" value="#{PoDdAdBookAuth.dabbkissue}"  >
                                   </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid columns="2" columnClasses="col9,col9"  id="Particulars1" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblParticulars" styleClass="headerLabel"  value="Particulars"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputLabel id="lblDetails" styleClass="headerLabel"  value="Details"  style="width:100%;text-align:left;"></h:outputLabel>
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="InstCode1" columnClasses="col9,col9"  styleClass="row1" style="height:30px"  width="100%"  >
                                <h:outputLabel id="lblInstCode" styleClass="headerLabel"  value="Inst.Code"  ></h:outputLabel>
                                <h:outputText id="txtInstCode" styleClass="output"  value="#{PoDdAdBookAuth.instcode}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="StabCode1" columnClasses="col9,col9"  styleClass="row2"  style="height:30px"  width="100%"  >
                                <h:outputLabel id="lblStabCode" styleClass="headerLabel"  value="Stab Code"  ></h:outputLabel>
                                <h:outputText id="txtStabCode" styleClass="output"  style="width:100%;text-align:center;" value="#{PoDdAdBookAuth.stabcode}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="AmountForm1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lblAmountForm" styleClass="headerLabel"  value="Amount Form" ></h:outputLabel>
                                <h:outputText id="txtAmountForm" styleClass="output" value="#{PoDdAdBookAuth.amtform}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="AmountTo1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblAmountTo" styleClass="headerLabel"  value="Amount To"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtAmountTo" styleClass="output" value="#{PoDdAdBookAuth.amtto}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="IssueDate1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lblissueDate" styleClass="headerLabel"  value="Issue Date"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtissueDate" styleClass="output" value="#{PoDdAdBookAuth.issuedate}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="NumberFrom1" columnClasses="col9,col9"  style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblNumberFrom" styleClass="headerLabel"  value="Number From"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtNumberFrom" styleClass="output" value="#{PoDdAdBookAuth.numfrom}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="NumberTo1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lblNumberTo" styleClass="headerLabel"  value="Number To"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtNumberTo" styleClass="output" value="#{PoDdAdBookAuth.numto}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="Leaves1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblLeaves" styleClass="headerLabel"  value="Leaves"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtLeaves" styleClass="output" value="#{PoDdAdBookAuth.leaves}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="PrintLot1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                <h:outputLabel id="lblPrintLot" styleClass="headerLabel"  value="Print Lot / SAN"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtPrintLot" styleClass="output" value="#{PoDdAdBookAuth.printlot}" />
                            </h:panelGrid>

                            <h:panelGrid columns="2"  id="IssueBy1" columnClasses="col9,col9" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                <h:outputLabel id="lblIssueBy" styleClass="headerLabel"  value="Issue By"  style="width:100%;text-align:left;"></h:outputLabel>
                                <h:outputText id="txtIssueBy" styleClass="output" value="#{PoDdAdBookAuth.issueby}" />
                            </h:panelGrid>

                        </h:panelGrid>


                            <h:panelGrid columnClasses="vtop" columns="1" id="rightPanel" style="width:100%;height:505px;text-align:center;background-color:#e8eef7">
                                <h:outputText styleClass="output" value="Pending List To Be Authorized"/>
                                <a4j:region>
                                    <rich:dataTable value="#{PoDdAdBookAuth.incis}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                        <f:facet name="header">
                                            <rich:columnGroup  >
                                                <rich:column colspan="8"><h:outputText value="Select List"  /></rich:column>
                                                <rich:column breakBefore="true"  ><h:outputText  value="Instcode"  /></rich:column>
                                                <rich:column><h:outputText value="Sno"/></rich:column>
                                                <rich:column><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column ><h:outputText value="#{dataItem.instcode}" /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.sno}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink ajaxSingle="true" id="selectlink" limitToList="true" reRender="txtInstCode,txtStabCode,txtAmountForm,txtAmountTo,txtissueDate,txtNumberFrom,txtNumberTo,txtLeaves,txtPrintLot,txtIssueBy,stxtMsg,btnDelete,btnautherised,ggrid,input1" actionListener="#{PoDdAdBookAuth.setRowValues}" >
                                                          <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{PoDdAdBookAuth.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{PoDdAdBookAuth.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                    </rich:dataTable>
                                    <rich:datascroller  align="left" for="taskList"  maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnDelete" disabled="#{PoDdAdBookAuth.flag1=='true'}" value="Delete" oncomplete="#{rich:component('savePanel')}.show() "/>
                            <a4j:commandButton id="btnautherised" disabled="#{PoDdAdBookAuth.flag1=='true'}" value="Authorize" oncomplete="#{rich:component('autrise')}.show() "   />                           
                            <a4j:commandButton id="btnRefresh" value="Refresh" limitToList="true" action="#{PoDdAdBookAuth.reFresh}"  reRender="txtInstCode,txtStabCode,txtAmountForm,txtAmountTo,txtissueDate,txtNumberFrom,txtNumberTo,txtLeaves,txtPrintLot,txtIssueBy,stxtMsg,input1,btnDelete,btnautherised,rightPanel">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{PoDdAdBookAuth.exitFrm}">
                            </a4j:commandButton>
                        </h:panelGroup>  
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:outputText value="Processing" />
                </f:facet>
                <h:outputText value="Wait Please..." />
                <h:outputText value="Process is running..." />
            </rich:modalPanel>
            <rich:messages>   </rich:messages>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Delete This Record" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                        <rich:componentControl for="savePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{PoDdAdBookAuth.dleteOpeartion}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="txtInstCode,txtStabCode,txtAmountForm,txtAmountTo,txtissueDate,txtNumberFrom,txtNumberTo,txtLeaves,txtPrintLot,txtIssueBy,stxtMsg,input1,btnDelete,btnautherised,rightPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

             <rich:modalPanel id="autrise" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Authorize This Record" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="autrise" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{PoDdAdBookAuth.authOpeartion}"
                                                       oncomplete="#{rich:component('autrise')}.hide();" reRender="txtInstCode,txtStabCode,txtAmountForm,txtAmountTo,txtissueDate,txtNumberFrom,txtNumberTo,txtLeaves,txtPrintLot,txtIssueBy,stxtMsg,input1,btnDelete,btnautherised,rightPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('autrise')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
