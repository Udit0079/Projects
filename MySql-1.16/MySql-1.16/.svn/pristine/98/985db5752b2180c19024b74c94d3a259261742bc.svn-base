<%-- 
    Document   : slabmaster
    Created on : Aug 12, 2011, 4:16:54 PM
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
            <title>Slab Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="slabMasterForm">
                <h:panelGrid id="slabMasterPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{SlabMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Slab Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SlabMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel"   width="100%" style="height:1px;text-align:center" styleClass="row2">
                        <h:outputText id="errMsg51" value="#{SlabMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4"   id="slabGridPanel" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{SlabMaster.function}" style="width:86px"  disabled="#{SlabMaster.disablegrup}">
                                <f:selectItems value="#{SlabMaster.functionList}"/>
                                <a4j:support event="onblur" action="#{SlabMaster.onChangeFunction}" focus="purposeList" reRender="dexcriptionList,errMsg51"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="DescriptionLabel" styleClass="output" value="Designation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="dexcriptionList" value="#{SlabMaster.desgId}" disabled="#{SlabMaster.disablegrup}" styleClass="ddlist" size="1" style="width:120px" >
                                <f:selectItems value="#{SlabMaster.desgIdList}"/>
                                <a4j:support event="onblur"  action="#{SlabMaster.onblurDesignation}"   reRender="errMsg51,slabGridpanelSalry,slabGridPanel8,slabMasterBatchGrid"/>
                            </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4"  id="slabGridpanelSalry" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="lblslarySlab" styleClass="output" value="Basic Salary Slab"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddslarySlab" styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.salarySlabId}"  disabled="#{SlabMaster.disablegrup}">
                                <f:selectItems value="#{SlabMaster.salarySlabIdList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="natureLabel" styleClass="output" value="Nature"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="natureList"  styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.natureList}"  disabled="#{SlabMaster.disablegrup}">
                                <f:selectItems value="#{SlabMaster.natureListBox}"/>
                                <a4j:support event="onblur" action="#{SlabMaster.slabCriteriaInitialData}" oncomplete="if(#{SlabMaster.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else {  #{rich:component('popUpGridPanel')}.hide(); }"
                                             reRender="slabMasterPanel,errMsg51"/>
                            </h:selectOneListbox>
                     </h:panelGrid>
                     <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridPanel8" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="purposeLabel" styleClass="output" value="Purpose"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="purposeList" disabled="#{SlabMaster.disablePurposeList}" styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.purposeList}">
                                <f:selectItems value="#{SlabMaster.purposeListBox}"/>
                                <a4j:support event="onblur" action="#{SlabMaster.getDescription}" reRender="slabMasterPanel,errMsg51" focus="dexcriptionList"></a4j:support>
                            </h:selectOneListbox>   
                            <h:outputLabel/>
                            <h:outputLabel/>
                     </h:panelGrid>
                     <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4"  id="slabGridPane4" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="fromAmount" styleClass="output" value="From Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="10" id="fromAmountText" disabled="#{SlabMaster.disableFromAmount}" styleClass="input" value="#{SlabMaster.slabFromAmount}" style="width:120px">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:outputLabel id="toAmount" styleClass="output" value="To Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="10" id="toAmountText" disabled="#{SlabMaster.disableToAmount}" styleClass="input" value="#{SlabMaster.slabToAmount}" style="width:120px">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                      </h:panelGrid>
                      <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridPanel9" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="baseComp" styleClass="output" value="Base Component"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="baseCompList" styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.baseComponent}">
                                <f:selectItems value="#{SlabMaster.earnigslabMasterDescList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="depnComp" styleClass="output" value="Dependent Component"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="depnCompList" styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.depnComponent}">
                                <f:selectItems value="#{SlabMaster.slabMasterDescList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                      </h:panelGrid>
                      <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridPanel7" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="SlabCriteria" styleClass="output" value="Slab Criteria"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="slabCriteriaList" styleClass="ddlist" size="1" style="width:120px" value="#{SlabMaster.slabCriteria}">
                                <f:selectItems value="#{SlabMaster.slabCriteriaList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="slabCriteriaAmount" styleClass="output" value="Slab Criteria Percent/Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="8" id="slabCriteriaAmt" styleClass="input" value="#{SlabMaster.slabCriteriaAmount}">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                      </h:panelGrid>
                      <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="slabGridPanel10" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="lblmaxCompAmt" styleClass="output" value="Component Max Amount"/>
                            <h:inputText maxlength="10" id="txtmaxCompAmt" styleClass="input" value="#{SlabMaster.slabMaxAmount}" style="width:120px">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                           <h:outputLabel id="lblminCompAmt" styleClass="output" value="Component Min Amount"/>
                           <h:inputText maxlength="10" id="txtMinCompAmt" styleClass="input" value="#{SlabMaster.slabMinAmount}" style="width:120px">
                                <a4j:support event="onblur"/>
                           </h:inputText>
                      </h:panelGrid>
                      <h:panelGrid id="slabMasterBatchGrid" width="100%" style="width:100%;" columnClasses="vtop">
                            <rich:dataTable  id="slabBatchList" var="dataItem" value="#{SlabMaster.slabMasterBatchList}"
                                             rowClasses="gridrow1, gridrow2" rows="9"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                             <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px"><h:outputText value="Slab Code" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Salary Slab Id" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Purpose" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Base Component" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Dependent Component" /></rich:column>
                                        <rich:column width="80px"><h:outputText value="Start Range"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="End Range"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Criteria"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Percent/Amount"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Min Amount"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Max Amount"/></rich:column>
                                        <rich:column width="55px"><h:outputText value="Applied"/></rich:column>
                                        <rich:column width="35px"><h:outputText value="Select"/></rich:column> 
                                      <%-- <rich:column width="35px"><h:outputText value="Remove"/></rich:column>--%>
                                    </rich:columnGroup>
                             </f:facet>
                                <rich:column><h:outputText value="#{dataItem.getHrSlabMasterPK().slabCode}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.getHrSlabMasterPK().salarySlabId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.getHrSlabMasterPK().purposeCode}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.baseComponent}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.depnComponent}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rangeFrom}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rangeTo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteria}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteriaAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteriaMinAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteriaMaxAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.appFlg}"/></rich:column>
                                <rich:column style="text-align:center">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" limitToList="true" action="#{SlabMaster.setSlabMasterDataToform}" 
                                                     reRender="slabMasterPanel,slabList,slabMasterGrid,save,delete,errMsg51,purposeList,fromAmountText,toAmountText,baseCompList,depnCompList,slabCriteriaList,slabCriteriaAmt,txtmaxCompAmt,txtMinCompAmt,optionList" >
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SlabMaster.slabMasterBatchcurrentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="selectlink" value="Select this row."/>
                                </rich:column> 
                                <%-- <rich:column style="text-align:center">
                                    <a4j:commandLink ajaxSingle="true" id="removelink" limitToList="true" action="#{SlabMaster.removeSlabMasterFromGrid}" oncomplete="#{rich:component('popUpGridPanel')}.hide()"
                                                     reRender="slabMasterBatchGrid,errMsg51" >
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SlabMaster.slabMasterBatchcurrentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="selectlink" value="Select to remove this row."/>
                                </rich:column> --%>   
                            </rich:dataTable>
                            <rich:datascroller id="stopDataScroll" align="left" for="slabBatchList" maxPages="10"/>
                      </h:panelGrid>
                        <h:panelGrid  id="stopPaymentFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:region id="processActionRegion">
                                <h:panelGroup id="stopPaymentBtnPanel">
                                    <a4j:commandButton id="add" value="Add" action="#{SlabMaster.addSlabMasterToGrid}" reRender="slabMasterPanel,purposeList,natureList,slabMasterBatchGrid,slabBatchList,errMsg51,save,delete,slabCriteriaAmt,slabCriteriaList,toAmountText,fromAmountText,baseCompList,depnCompList,txtmaxCompAmt,toAmountText,txtMinCompAmt,txtmaxCompAmt"/>
                                    <a4j:commandButton id="save" value="Save" action="#{SlabMaster.saveSlabMaster}" 
                                                       reRender="slabMasterPanel,slabBatchList,errMsg51"/>
                                    <%--a4j:commandButton id="delete" value="Delete" action="#{SlabMaster.deleteSlabMaster}" 
                                                       reRender="purposeList,natureList,dexcriptionList,slabList,slabMasterGrid,save,delete,errMsg51,slabCriteriaAmt,slabCriteriaList,toAmountText,fromAmountText,dexcriptionList,optionList"/--%>
                                    <a4j:commandButton id="cancel" value="Reset" action="#{SlabMaster.refreshSlabMasterForm}" 
                                                       reRender="slabMasterPanel,errMsg51"/>
                                    <a4j:commandButton id="exit" value="Exit" action="#{SalaryStructure.btnExitAction}"/>
                                </h:panelGroup>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="popUpGridPanel" width="750" moveable="false" height="375" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                    <f:facet name="header">
                        <h:panelGrid style="width:100%;text-align:center;">
                            <h:outputText value="Tax Slab Details" style="text-align:center;"/>
                        </h:panelGrid>
                    </f:facet>
                    <h:form>
                        <h:panelGrid id="slabMasterGrid" width="100%" style="width:100%;height:300px;" columnClasses="vtop">
                            <rich:dataTable  id="slabList" var="dataItem" value="#{SlabMaster.slabMasterBatchList}"
                                             rowClasses="gridrow1, gridrow2" rows="8"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px"><h:outputText value="Purpose" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Description" /></rich:column>
                                        <rich:column width="100px"><h:outputText value="Nature"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Start Range"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="End Range"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Criteria"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Slab Amount"/></rich:column>
                                        <rich:column width="55px"><h:outputText value="Applied"/></rich:column>
                                        <rich:column width="35px"><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.purpose}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.nature}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.startRange}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.endRange}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteria}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteriaAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.appFlg}"/></rich:column>
                                <rich:column style="text-align:center">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" limitToList="true" action="#{SlabMaster.setRowValuesSlabMasterGrid}" oncomplete="#{rich:component('popUpGridPanel')}.hide()"
                                                     reRender="purposeList,natureList,dexcriptionList,slabList,slabMasterGrid,save,delete,errMsg51,slabCriteriaAmt,slabCriteriaList,toAmountText,fromAmountText,dexcriptionList" >
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SlabMaster.slabMastercurrentItem}"/>
                                    </a4j:commandLink>
                                    <rich:toolTip for="selectlink" value="Select this row."/>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="stopDataScroll" align="left" for="slabList" maxPages="5"/>
                        </h:panelGrid>
                        <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                        </h:panelGrid>
                    </h:form>
                </rich:modalPanel> 
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
        </body>
    </html>
</f:view>