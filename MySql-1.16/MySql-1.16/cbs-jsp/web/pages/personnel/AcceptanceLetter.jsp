<%-- 
    Document   : AcceptanceLetter
    Created on : Jul 20, 2011, 10:47:32 AM
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
            <title>Acceptance Letter</title>
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
            <a4j:form id="acceptanceletter">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:messages />
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{Acceptanceletter.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Acceptance Letter"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Acceptanceletter.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{Acceptanceletter.msg}"/>
                        <h:messages />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"  columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width: 80px" styleClass="ddlist" value="#{Acceptanceletter.operation}">
                                <f:selectItems value="#{Acceptanceletter.operationList}"/>
                                <a4j:support event="onblur" action="#{Acceptanceletter.setOperationOnBlur}" oncomplete="if(#{Acceptanceletter.operation=='1'}){ #{rich:component('acView1')}.show();#{rich:component('editPanel')}.hide(); } 
                                                                                                             else if(#{Acceptanceletter.operation=='2'}){ #{rich:component('editPanel')}.show(); #{rich:component('acView1')}.hide()} 
                                                                                                             else {  #{rich:component('editPanel')}.hide(); #{rich:component('acView1')}.hide()}
                                                                                                             #{rich:element('resignationdate')}.style=setMask();
                                                                                                             #{rich:element('resignationacceptdate')}.style=setMask();
                                                                                                             #{rich:element('seprationadate')}.style=setMask();"  reRender="acView1,errMsg,save,delete"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                            <h:outputText/>
                     </h:panelGrid>
                    <h:panelGrid id="gridPanel3" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Employee Name"  styleClass="output" />
                        <h:inputText id="empName" value="#{Acceptanceletter.empname}" style="width:120px" styleClass="input" disabled="true" />
                        <h:outputLabel value="Employee Id"  styleClass="output"/>
                        <h:inputText id="empId"  disabled="true" styleClass="input" style="width:120px" maxlength="8" value="#{Acceptanceletter.empid}"/>
                        <h:outputLabel value="Designation"   styleClass="output"/>
                        <h:inputText id="designation" disabled="true" styleClass="input" style="width:120px" maxlength="200" value="#{Acceptanceletter.desgcodedesc}" />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Grade"  styleClass="output" />
                        <h:inputText id="grade" disabled="true" styleClass="input" style="width:120px" maxlength="200" value="#{Acceptanceletter.gradecodedesc}" />
                        <h:outputLabel value="Department"  styleClass="output" />
                        <h:inputText id="department" disabled="true" styleClass="input" style="width:120px" maxlength="200" value="#{Acceptanceletter.deptcodedesc}"/>
                        <h:outputLabel value="Notise Period"  styleClass="output" />
                        <h:inputText id="notisePeriod" disabled="true"styleClass="input" style="width:120px" maxlength="8" value="#{Acceptanceletter.noticeperiod}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                       
                        <h:outputLabel value="Date Of Joining"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="dateofjoining" styleClass="input calInstDate" disabled="true" style="width:70px;setMask()" maxlength="10" value="#{Acceptanceletter.joindate}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFrom11" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText/>
                        <h:outputText />
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <rich:panel id="richPanel1" header="Sepration Details" >
                        <h:panelGrid id="gridPanel7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left">
                            <h:outputLabel value="Type"   styleClass="output"/>
                            <h:selectOneListbox id="type" styleClass="ddlist" size="1" style="width:120px" value="#{Acceptanceletter.sepacode}">
                                <f:selectItems value="#{Acceptanceletter.seprationtype}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Resignation Date"  styleClass="output" />
                            <h:panelGroup>
                                <h:inputText id="resignationdate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Acceptanceletter.resignation}"  >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel value="Reason"  styleClass="output" />
                            <h:inputText id="reason" value="#{Acceptanceletter.reason}" styleClass="input" maxlength="50" style="width:120px"/>

                        </h:panelGrid>
                        <h:panelGrid id="gridPanel8" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left">
                           
                            <h:outputLabel value="Resignation Accept Date"   styleClass="output" />
                            <h:panelGroup>
                                <h:inputText id="resignationacceptdate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Acceptanceletter.resigaccept}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFrom2" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel value="Separation Date"  styleClass="output"/>
                            <h:panelGroup>
                                <h:inputText id="seprationadate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Acceptanceletter.sepration}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFrom3" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputText/>
                            <h:outputText/>
                        </h:panelGrid>
                    </rich:panel>
                <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save"  disabled="#{Acceptanceletter.save}"value="Save" action="#{Acceptanceletter.saveData}" oncomplete="#{rich:element('resignationdate')}.style=setMask();
                                               #{rich:element('resignationacceptdate')}.style=setMask();
                                               #{rich:element('seprationadate')}.style=setMask();" reRender="add,save,update,delete,taskList1,errMsg,empName,empId,designation,grade,department,notisePeriod,dateofjoining,type,resignationdate,reason,resignationacceptdate,seprationadate,operationList" >
                            </a4j:commandButton>
                           
                            <a4j:commandButton id="delete"  value="Delete" disabled="#{Acceptanceletter.delete}"
                                               oncomplete="#{rich:element('resignationdate')}.style=setMask();
                                               #{rich:element('resignationacceptdate')}.style=setMask();
                                               #{rich:element('seprationadate')}.style=setMask();" action="#{Acceptanceletter.deleteData}" reRender="add,save,update,delete,taskList1,errMsg,empName,empId,designation,grade,department,notisePeriod,dateofjoining,type,resignationdate,reason,resignationacceptdate,seprationadate,taskList1,operationList" >
                            </a4j:commandButton>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{Acceptanceletter.refreshbutton}" 
                                               oncomplete="#{rich:element('resignationdate')}.style=setMask();
                                               #{rich:element('resignationacceptdate')}.style=setMask();
                                               #{rich:element('seprationadate')}.style=setMask();"
                                              reRender="add,save,update,delete,taskList1,errMsg,empName,empId,designation,grade,department,notisePeriod,dateofjoining,type,resignationdate,reason,resignationacceptdate,seprationadate,taskList1,operationList">
                            </a4j:commandButton>
                            <a4j:commandButton id="exit" value="Exit" action="#{Acceptanceletter.btnExit}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                 </a4j:form>         
                        
                <rich:modalPanel id="acView1" height="260" width="800" resizeable="true" moveable="false" onshow="#{rich:element('searchtext')}.focus();" >
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
                        <h:panelGrid id="SubPanelgid1" columns="4" styleClass="row1" style="text-align:left" width="100%">
                            <h:outputLabel value="Serarch"/>
                            <h:panelGroup id="btnSearch">
                                <h:selectOneListbox id="searchcriteria" styleClass="ddlist" size="1" style="width:80px" value="#{Acceptanceletter.searchflag}">
                                    <f:selectItem itemValue="Emp-Id"/>
                                    <f:selectItem itemValue="Emp Name"/>
                                </h:selectOneListbox>
                                <h:inputText id="searchtext" value="#{Acceptanceletter.searchtext}" onkeyup="this.value = this.value.toUpperCase();" maxlength="50" style="width:100px" >
                                    <a4j:support  event="onblur" action="#{Acceptanceletter.onfindMethod}"  reRender="taskList,datascrollerTab">
                                    </a4j:support>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Acceptanceletter.datagridemp}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="3" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Employee Name" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Address" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Mobile No" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empidD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empnameD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empaddD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empphD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD" action="#{Acceptanceletter.selectDataEmpMethod}" oncomplete="#{rich:component('acView1')}.hide();#{rich:element('resignationdate')}.style=setMask();
                                               #{rich:element('resignationacceptdate')}.style=setMask();
                                               #{rich:element('seprationadate')}.style=setMask(); return false;" reRender="add,save,update,delete,empName,empId,designation,grade,department,notisePeriod,dateofjoining"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{Acceptanceletter.selectdataEmpdetail}"/>
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
                        
                <rich:modalPanel id="editPanel" height="215" width="800" resizeable="true" moveable="false" >
                    <f:facet name="header">
                        <h:outputText value="Acceptance Letter Details"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                            <rich:componentControl for="editPanel" attachTo="closelink2" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                            <h:panelGrid id="gridPanelTable" styleClass="row1" columnClasses="vtop" style=" width:100%;height:158px">
                        <a4j:region>
                            <rich:dataTable  value="#{Acceptanceletter.datagrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskLista" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Reason" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Resignation Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Resignation Acceptance Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Sepration Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Update/Delete" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empname}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.reason}"style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.resignation}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.resigaccept}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.sepration}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlinka" action="#{Acceptanceletter.selectData}" oncomplete="#{rich:component('editPanel')}.hide();#{rich:element('resignationdate')}.style=setMask();
                                               #{rich:element('resignationacceptdate')}.style=setMask();
                                               #{rich:element('seprationadate')}.style=setMask(); return false;" reRender="add,save,update,delete,empName,empId,designation,grade,department,notisePeriod,dateofjoining,type,resignationdate,reason,resignationacceptdate,seprationadate">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{Acceptanceletter.selectdata}"/> </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskLista" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                       <h:panelGrid id="editPanelFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="editPanelPanela">
                                <a4j:commandButton id="editPanelClose" value="Close" onclick="#{rich:component('editPanel')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                </a4j:form>
          </rich:modalPanel>
                        
        </body>
    </html>
</f:view>
