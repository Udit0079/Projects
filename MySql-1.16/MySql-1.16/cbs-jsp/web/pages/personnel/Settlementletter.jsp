<%-- 
    Document   : Settlementletter
    Created on : Jul 20, 2011, 5:24:00 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Settlement Letter</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Settlementletter">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{Settlementletter.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Settlement Letter"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Settlementletter.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{Settlementletter.errmsg}"/>
                         <h:messages errorClass="errorMessage" infoClass="infoMessage" layout="table" globalOnly="true" showDetail="false" showSummary="true"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"  columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:80px" styleClass="ddlist" value="#{Settlementletter.operation}">
                                <f:selectItems value="#{Settlementletter.operationList}"/>
                                <a4j:support event="onblur" action="#{Settlementletter.setOperationOnBlur}" oncomplete="if(#{Settlementletter.operation=='1'}){ #{rich:component('acView1')}.show();#{rich:component('editPanel')}.hide(); } 
                                                                                                             else if(#{Settlementletter.operation=='2'}){ #{rich:component('editPanel')}.show(); #{rich:component('acView1')}.hide()} 
                                                                                                             else {  #{rich:component('editPanel')}.hide(); #{rich:component('acView1')}.hide()}
                                                                                                             #{rich:element('settlementdate')}.style=setMask();
                                                                                                             #{rich:element('dueamount')}.style=setMask();
                                                                                                             #{rich:element('dtleave')}.style=setMask();
                                                                                                              #{rich:element('ddchequedate')}.style=setMask();" reRender="acView1,editPanel,errMsg,save,delete" />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                            <h:outputText/>
                     </h:panelGrid>
                    <h:panelGrid id="panelGrid1" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Employee Name" styleClass="output"/>
                        <h:inputText id="empname" value="#{Settlementletter.empName}" style="width:120px;" maxlength="100" styleClass="input" disabled="true" />
                        <h:outputLabel value="Employee Id" styleClass="output" />
                        <h:inputText id="empid" value="#{Settlementletter.empId}" style="width:120px;" styleClass="input" maxlength="8" disabled="true" />
                        <h:outputLabel value="Designation"   styleClass="output"/>
                        <h:inputText id="designation" value="#{Settlementletter.desg}" style="width:120px;" styleClass="input"  maxlength="6" disabled="true"  />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Grade"  styleClass="output" />
                        <h:inputText id="grade" value="#{Settlementletter.grade}" style="width:120px;" styleClass="input" maxlength="200" disabled="true"  />
                        <h:outputLabel value="Department"  styleClass="output" />
                        <h:inputText id="department" value="#{Settlementletter.dept}"style="width:120px;" styleClass="input"  maxlength="200" disabled="true"  />
                        <h:outputLabel value="Block"  styleClass="output" />
                        <h:inputText id="block" value="#{Settlementletter.block}" styleClass="input" style="width:120px;" maxlength="200" disabled="true" />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Date of Joining" styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="dtjoin" styleClass="input calInstDate"  style="width:70px;setMask()" disabled="true"  maxlength="10" value="#{Settlementletter.joining}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromJoin" styleClass="label" value="DD/MM/YYYY" style="color:purple" />
                        </h:panelGroup>
                        <h:outputLabel value="Date of Leaveing" styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="dtleave" styleClass="input calInstDate"  style="width:70px;setMask()" disabled="true"  maxlength="10" value="#{Settlementletter.leaveing}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromleave" styleClass="label" value="DD/MM/YYYY" style="color:purple" />
                        </h:panelGroup>
                        <h:outputLabel id="lbl1" styleClass="label" />
                        <h:outputLabel id="lbl2" styleClass="label" />
                    </h:panelGrid>
                    <rich:panel id="richPanel1" header="Settlement Details" >
                        <h:panelGrid id="gridPanel25" width="100%">
                            <h:panelGrid id="gridPanel6" columnClasses="col3,col3,col3,col3,col3,col3" columns="6"  width="100%"   styleClass="row1" style="text-align:left">
                                <h:outputLabel value="Settlement Date" styleClass="output" />
                                <h:panelGroup>
                                    <h:inputText id="settlementdate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Settlementletter.settelement}" >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple" />
                                </h:panelGroup>
                                <h:outputLabel value="Total Amount" styleClass="output" />
                                <h:inputText id="totalamount" value="#{Settlementletter.totamt}"style="width:120px;"  styleClass="input"/>
                                <h:outputLabel value="BankName" styleClass="output"/>
                                <h:selectOneListbox id="bankname" size="1" style="width:120px" value="#{Settlementletter.bankname}" styleClass="ddlist">
                                    <f:selectItems value="#{Settlementletter.banklist}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel7" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                                <h:outputLabel value="Cheque No" styleClass="output" />
                                <h:inputText id="chequeno" value="#{Settlementletter.ddcheqno}"style="width:120px;" styleClass="input"/>
                                <h:outputLabel value="DD/Cheque Date" styleClass="output"/>
                                <h:panelGroup>
                                    <h:inputText id="ddchequedate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Settlementletter.ddchequedt}"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                   <h:messages errorClass="errorMessage" infoClass="infoMessage" layout="table" globalOnly="true" showDetail="false" showSummary="true"/>
                                    <h:outputLabel id="lblDurationFrom2" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                </h:panelGroup>
                                <h:outputLabel value="Amount" styleClass="output"/>
                                <h:inputText id="amount" value="#{Settlementletter.ddchqamt}" style="width:120px;" maxlength="50" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel8" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                                <h:outputLabel value="Department" styleClass="output"/>
                                <h:selectOneListbox id="departmentselect" size="1" style="width:120px" value="#{Settlementletter.deptDet}" styleClass="ddlist" disabled="#{Settlementletter.deptbtn}">
                                    <f:selectItems value="#{Settlementletter.deptlist}"/>
                                </h:selectOneListbox>
                                <h:outputLabel value="Due Amount" styleClass="output"/>
                                <h:inputText id="dueamount" value="#{Settlementletter.dueamt}"style="width:120px;"  styleClass="input"/>
                                <h:outputLabel value="Recovery"  styleClass="output"/>
                                <h:inputText id="recovery" value="#{Settlementletter.recovery}" style="width:120px;"  styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel9" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                                <h:outputLabel value="Net Amount" styleClass="output" />
                                <h:inputText id="netamt" value="#{Settlementletter.netamt}" style="width:120px;"  styleClass="input"/>
                                <h:outputLabel value="Comments" styleClass="output"/>
                                <h:inputText id="comments"  value="#{Settlementletter.comments}" style="width:120px;" maxlength="150" styleClass="input"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>
                 <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save"  value="Save" disabled="#{Settlementletter.save}" oncomplete=" #{rich:element('settlementdate')}.style=setMask();#{rich:element('ddchequedate')}.style=setMask();" action="#{Settlementletter.saveData}" reRender="add,save,delete,update,errMsg,empname,empid,designation,grade,department,block,dtjoin,dtleave,settlementdate,totalamount,bankname,chequeno,ddchequedate,amount,departmentselect,dueamount,recovery,netamt,comments,taskList1,operationList" >
                            </a4j:commandButton>
                            <a4j:commandButton id="delete"  value="Delete"disabled="#{Settlementletter.delete}" oncomplete=" #{rich:element('settlementdate')}.style=setMask();#{rich:element('ddchequedate')}.style=setMask();" action="#{Settlementletter.deleteData}" reRender="add,save,delete,update,errMsg,empname,empid,designation,grade,department,block,dtjoin,dtleave,settlementdate,totalamount,bankname,chequeno,ddchequedate,amount,departmentselect,dueamount,recovery,netamt,comments,taskList1,operationList">
                            </a4j:commandButton>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{Settlementletter.refreshButton}" oncomplete=" #{rich:element('settlementdate')}.style=setMask();#{rich:element('ddchequedate')}.style=setMask();" reRender="add,save,delete,update,errMsg,empname,empid,designation,grade,department,block,dtjoin,dtleave,settlementdate,totalamount,bankname,chequeno,ddchequedate,amount,departmentselect,dueamount,recovery,netamt,comments,taskList1,operationList">
                            </a4j:commandButton>
                            <a4j:commandButton id="exit" value="Exit" action="#{Settlementletter.btnExit}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
                        
                        <rich:modalPanel id="acView1" height="225" width="800" resizeable="false" moveable="false" onshow="#{rich:element('designation')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Employee Search "/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="acView1" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Settlementletter.datagridemp}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Employee Name" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empidD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empnameD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD" ajaxSingle="true" action="#{Settlementletter.selectDataEmpMethod}" oncomplete="#{rich:component('acView1')}.hide(); return false;" reRender="add,save,delete,update,empname,empid,designation,grade,department,block,dtjoin,dtleave"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{Settlementletter.currentdataemp}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab" align="left" for="taskList" maxPages="5"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                        
                        <rich:modalPanel id="editPanel" height="192" width="800" resizeable="false" moveable="false" onshow="#{rich:element('settlementdate')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Settlement Letter Details"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelinka"/>
                            <rich:componentControl for="editPanel" attachTo="closelinka" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>          
                 <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{Settlementletter.griddata}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskLista" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Department" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Due Amount" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Recovery" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Net Amount" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Comments" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empName}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.description}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.dueAmt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.recoverableAmt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.netAmt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.remarks}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlinka" action="#{Settlementletter.selectDataCurrent}" oncomplete="#{rich:component('editPanel')}.hide(); return false;" reRender="add,save,delete,update,errMsg,empname,empid,designation,grade,department,block,dtjoin,dtleave,settlementdate,totalamount,bankname,chequeno,ddchequedate,amount,departmentselect,dueamount,recovery,netamt,comments" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{Settlementletter.currentdata}"/> </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskLista" maxPages="5"/>
                        </a4j:region>
                       </h:panelGrid>
                         <h:panelGrid id="editPanelclosebtn" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="editPanelBtnPanel">
                                <a4j:commandButton id="editPanelClose" value="Close" onclick="#{rich:component('editPanel')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
        </body>
    </html>
</f:view>
 