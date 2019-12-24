<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:facet name="header">
    <h:outputText value="GL Heads Register" />
</f:facet>
<f:facet name="controls">
    <h:panelGroup>
        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink1"/>
        <rich:componentControl for="glheadView" attachTo="glheadlink1" operation="hide" event="onclick"/>
    </h:panelGroup>
</f:facet>
<a4j:form>
    <h:panelGrid id="glheadPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
        <%-- Added by manish kumar --%>
        <h:panelGrid id="errormsg" style="height:25px;text-align:center" styleClass="error" width="100%">
                    <h:outputText id="errmsg" value="#{Transactions.errorMsg}"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel1" width="100%" styleClass="row2">
            <h:outputLabel style="margin-left:15px;" value="Search By" id="searchTypeId" styleClass="label"/>
            <h:selectOneListbox id="searchListId" value="#{Transactions.searchType}" styleClass="ddlist" size="1"  style="width:100px" >
                <f:selectItems value="#{Transactions.searchTypeList}" />
                <a4j:support  event="onblur" action="#{Transactions.setSearchLabelValue}"   reRender="searchTypeLabel,searchValueId,errormsg" focus="searchValueId"/>                     
            </h:selectOneListbox>
      
            <h:outputLabel value="#{Transactions.searchLabel}" id="searchTypeLabel" styleClass="label"/>
            <h:panelGroup>
                    <h:inputText id="searchValueId" styleClass="input"  value="#{Transactions.searchValue}" style="width:90px">
                        <a4j:support event="onkeyup" ajaxSingle="true" actionListener="#{Transactions.searchedItem}" reRender="glHeadList,errormsg" />
                    </h:inputText>
            </h:panelGroup>
        </h:panelGrid>
        <%-- --%>
        <rich:dataTable  var="item" value="#{Transactions.listForF6}"
                         rowClasses="gridrow1, gridrow2" id="glHeadList" rows="15"
                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                         width="100%">
            
            <f:facet name="header">
                
                <rich:columnGroup>
                    <rich:column colspan="2"></rich:column>
                    <rich:column breakBefore="true"><h:outputText value="GL Head" /></rich:column>
                    <rich:column><h:outputText value="Acno" /></rich:column>
                </rich:columnGroup>
            </f:facet>
            <rich:column><h:outputText value="#{item.accName}"/></rich:column>
            <rich:column><h:outputText value="#{item.glhead}"/></rich:column>
        </rich:dataTable>
        <rich:datascroller id="dataScroGLHead"align="left" for="glHeadList" maxPages="20" />
        <h:panelGrid id="glheadFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
            <h:panelGroup id="glheadBtnPanel">
                <a4j:commandButton id="glheadClose" value="Close" onclick="#{rich:component('glheadView')}.hide(); return false;"/>
            </h:panelGroup>
        </h:panelGrid>
    </h:panelGrid>
</a4j:form>