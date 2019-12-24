<%-- 
    Document   : certissue
    Created on : Aug 27, 2011, 11:16:38 AM
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
            <title>Ho Transaction</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){setMask();});
                function setMask(){jQuery(".calInstDate").mask("99/99/9999");}
                function validate(e) {e = e || window.event; var bad = /[^\sa-z\d\.=+-_,()*]/i, key = String.fromCharCode( e.keyCode || e.which ); if ( e.which !== 0 && e.charCode !== 0 && bad.test(key) ){e.returnValue = false;if ( e.preventDefault ) {e.preventDefault();}} } 
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="CertIssue" />
            <h:form id="CertIssue" prependId="false">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CertIssue.datetoday}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Ho Transaction/Share Certificate Issue"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{CertIssue.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" styleClass="row2">
                        <h:outputLabel id="errmsg" value="#{CertIssue.msg}"  styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="2" id="bodyPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="leftPanel" width="100%"  style="text-align:center;height:260px" >
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row1" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label1" styleClass="label" value="Account No."/>
                                <h:panelGroup>
                                    <a4j:region id="acnoRegion">
                                    <h:inputText id="txtAcNo" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" maxlength="12" value="#{CertIssue.oldAcno}"
                                                 style="width:100px;"  >
                                        <a4j:support event="onblur" action="#{CertIssue.onBlurAcno}" oncomplete="if(#{CertIssue.flag==true}){
                                                     #{rich:component('shareheadView')}.show();}
                                                     else if(#{CertIssue.dividendFlag==true}){
                                                     #{rich:component('shareDevidendView')}.show();
                                                     #{rich:element('textfoliono')}.focus();}else{
                                                     #{rich:element('textDate')}.focus();}setMask()" 
                                                     reRender="sharepanel1,errmsg,outtextaccountname,outtextdateofopening,stxtNewAcno,txtbalance,listType,txtAcNo,shareDevidendView,txtAmount"/>
                                    </h:inputText>
                                    </a4j:region>
                                    <h:outputText id="stxtNewAcno" styleClass="output" value="#{CertIssue.acno}"/>
                                </h:panelGroup>
                                <h:outputLabel value="Balance " styleClass="output"/>
                                <h:outputText id="txtbalance" value="#{CertIssue.balance}" styleClass="label" />
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid2" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >
                                <h:outputLabel value="Account Name" styleClass="output"/>
                                <h:outputText id="outtextaccountname" value="#{CertIssue.accountname}" styleClass="label"/>
                                <h:outputLabel value="Date of Opening" styleClass="output"/>
                                <h:outputText id="outtextdateofopening" value="#{CertIssue.accountopening}" styleClass="label"/>
                            </h:panelGrid>

                            <h:panelGrid id="panelGrid3" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1" >
                                <h:outputLabel value="Date" styleClass="output"/>
                                <h:panelGroup>
                                    <h:inputText id="textDate" styleClass="input calInstDate"  style="width:70px;"  maxlength="10" value="#{CertIssue.accountdate}"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel   value="DD/MM/YYYY" styleClass="label" style="color:purple"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblAdviceNo" value="Advice No./Seq No." styleClass="label"/>
                                <h:panelGroup id="pg1">
                                    <h:selectOneListbox id="listAdviceNo" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.advise}" >
                                        <f:selectItems value="#{CertIssue.adviselist}"/>
                                        <a4j:support ajaxSingle="true" event="onblur" action="#{CertIssue.onchangAdvice}" 
                                                     oncomplete="
                                                     if(#{CertIssue.adviceBoolean=='true'}){#{rich:element('txtSeqNo')}.focus();}
                                                     else{#{rich:element('listTransaction')}.focus();} setMask();" reRender="listBranch,txtSeqNo" />
                                    </h:selectOneListbox>
                                    <h:inputText id="txtSeqNo" styleClass="input" value="#{CertIssue.adviceNo}" size="3" disabled="#{!CertIssue.adviceBoolean}" >  
                                    </h:inputText>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid4" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left"styleClass="row2" >
                                <h:outputLabel id="lblTransaction" value="Transaction" styleClass="label"/>
                                <h:selectOneListbox id="listTransaction" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.strtrantype}">
                                    <f:selectItems value="#{CertIssue.trantype}"/>                                    
                                </h:selectOneListbox>
                                <h:outputLabel id="lblType" value="Type" styleClass="label"/>
                                <h:selectOneListbox id="listType" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.type}" disabled="#{CertIssue.shareCheck}">
                                    <f:selectItems value="#{CertIssue.transtype}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid5" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >
                                <h:outputLabel id="lblBy" value="By" styleClass="output"/>
                                <h:selectOneListbox id="listBy" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.tranby}" >
                                    <f:selectItems value="#{CertIssue.transby}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblInstNo" value="Inst No." styleClass="label"/>
                                <h:inputText id="txtInstNo" styleClass="input" value="#{CertIssue.instmentno}" style="width:70px"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid6" width="100%" columns="4" columnClasses="col2,col7,col2,col7"style="text-align:left" styleClass="row1" >
                                <h:outputLabel id="lblBranch" value="Origin Branch" styleClass="output"/>
                                <h:selectOneListbox id="listBranch" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.orignbranch}" disabled="#{!CertIssue.adviceBoolean}" >
                                    <f:selectItems value="#{CertIssue.branchlist}"/>
                                </h:selectOneListbox>

                                <h:outputLabel id="lblRelatedTo" value="Related To" styleClass="output"/>
                                <h:selectOneListbox id="listRelatedTo" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.strRelate}">
                                    <f:selectItems value="#{CertIssue.relatedto}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid22" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >
                                <h:outputLabel id="lblDetails" value="Details" styleClass="label"/>
                                <h:inputText id="txtDetails" styleClass="input" value="#{CertIssue.hodetails}" onkeypress="validate(event)">
                                </h:inputText>
                                <h:outputLabel id="lblAmount" value="Amount" styleClass="label"/>
                                <h:inputText id="txtAmount" styleClass="input" value="#{CertIssue.hoamount}" style="width:70px" disabled="#{CertIssue.disableAmt}"><f:convertNumber maxFractionDigits="2" minFractionDigits="2" />
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid899" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1" >
                                <h:outputLabel id="lblvaluedt" value="Value Date" styleClass="label"/>
                                <h:panelGroup>
                                    <h:inputText id="txtvaluedt" styleClass="input calInstDate"  style="width:70px;"  maxlength="10" value="#{CertIssue.valueDt}" >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                        <a4j:support event="onblur" focus="savemain" oncomplete="setMask()"/>
                                    </h:inputText>
                                    <h:outputLabel   value="DD/MM/YYYY" styleClass="label" style="color:purple"/>
                                </h:panelGroup>
                                <h:outputLabel  />
                                <h:outputLabel  />
                            </h:panelGrid>
                        </h:panelGrid>                        
                        <h:panelGrid id="Panelgridff" styleClass="row2" width="100%" style="height:268px" >
                            <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1"  columnClasses="vtop">
                                <a4j:region>
                                    <rich:dataTable  value="#{CertIssue.bacthtable}" var="dataItem1"
                                                     rowClasses="gridrow1, gridrow2" id="taskList1" rows="6" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column width="85px" ><h:outputText style="text-align:center" value="Account No." /></rich:column>
                                                <rich:column width="60px" ><h:outputText style="text-align:center" value="Date" /></rich:column>
                                                <rich:column width="75px" ><h:outputText style="text-align:center" value="Value Date" /></rich:column>
                                                <rich:column width="40px" ><h:outputText style="text-align:center" value="Type" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Credit" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Debit" /></rich:column>
                                                <rich:column width="200px" ><h:outputText style="text-align:center" value="Details" /></rich:column>
                                                <rich:column width="40px" ><h:outputText style="text-align:center" value="Delete" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column width="85px" ><h:outputText value="#{dataItem1.acno}" style="text-align:center" /></rich:column>
                                        <rich:column width="60px" ><h:outputText value="#{dataItem1.dt}" style="text-align:center" ><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText></rich:column>
                                        <rich:column width="75px" ><h:outputText value="#{dataItem1.valDt}" style="text-align:center" ><f:convertDateTime pattern="dd/MM/yyyy" /></h:outputText></rich:column>
                                        <rich:column width="40px" ><h:outputText value="#{dataItem1.ty}" style="text-align:center"  /></rich:column>
                                        <rich:column width="100px" >
                                            <h:outputText value="#{dataItem1.crAmtHo}" style="text-align:center" ><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /></h:outputText> 
                                        </rich:column>
                                        <rich:column width="100px" >
                                            <h:outputText value="#{dataItem1.drAmtHo}" style="text-align:center"><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /> </h:outputText>
                                        </rich:column>
                                        <rich:column width="200px" ><h:outputText value="#{dataItem1.viewDetails}" style="text-align:center"  /></rich:column>
                                        <rich:column width="40px" >
                                            <a4j:commandLink id="deletelink" action="#{CertIssue.deleteRow}" reRender="PanelGridMain,txtdr,txtcr,batchPanelRj" >
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem1}" target="#{CertIssue.currentData}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid id="pppp" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="group2">
                                    <a4j:region id="batchPanelRj">
                                    <a4j:commandButton id="savebatch" value="Save Batch" disabled="#{CertIssue.boolsavebatch}" action="#{CertIssue.saveBatch}" oncomplete="setMask()" reRender="PanelGridMain" focus="savemain" />
                                    <a4j:commandButton id="cancelbatch" value="Cancel Batch" action="#{CertIssue.cancelBatch}"  oncomplete="setMask()" reRender="taskList1,ulpg" />
                                   </a4j:region>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="ulpg" columns="4" style="width:100%;height:30px;text-align:center;">
                                <h:outputLabel value="Dr Amount" styleClass="output"/>
                                <h:outputText id="txtdr" value="#{CertIssue.drAmt}" styleClass="output">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                                <h:outputLabel value="Cr Amount" styleClass="output"/>
                                <h:outputText id="txtcr" value="#{CertIssue.crAmt}" styleClass="output">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="ppppllllll" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="group3">
                            <a4j:region id="btnPanelRj">
                            <a4j:commandButton id="savemain" value="Save"  action="#{CertIssue.saveBtn}" oncomplete="setMask()" reRender="PanelGridMain,shareDevidendView"/>
                            <a4j:commandButton id="cancelmain" value="Refresh" action="#{CertIssue.cancelBtn}" oncomplete="setMask()" reRender="PanelGridMain,shareDevidendView" />
                            <a4j:commandButton id="exitmain" value="Exit" action="case1" />
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="Press F2-GL Head, F4 - Account View "style="color:blue;"/>
                    </h:panelGrid>
                    <a4j:region id="keyRegion">
                        <a4j:jsFunction name="showGlHeadDetails" action="#{CertIssue.selectGlHeadOnPressF6}" oncomplete="#{rich:component('glheadView')}.show();" reRender="glHeadList,dataScroGLHead" />
                        <a4j:jsFunction name="showAcDeatil" oncomplete="if(#{CertIssue.acno==null} || #{CertIssue.acno==''}){#{rich:component('acView')}.hide();} else{#{rich:component('acView')}.show();}" reRender="acView,errmsg" action="#{CertIssue.acctViewAuthUnAuth}"/>
                    </a4j:region>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="acnoRegion"/>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="batchPanelRj"/>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPanelRj"/>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="folioRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </h:form>
            <rich:modalPanel id="glheadView" height="350" width="700" style="align:right" autosized="true">
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
                        <rich:dataTable  var="item" value="#{CertIssue.listForF6}"
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
                        <rich:datascroller id="dataScroGLHead" align="left" for="glHeadList" maxPages="20" />
                        <h:panelGrid id="glheadFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="glheadBtnPanel">
                                <a4j:commandButton id="glheadClose" value="Close" onclick="#{rich:component('glheadView')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>          
            <rich:modalPanel id="shareheadView" height="480" width="900" autosized="true" style="align:right">
                <a4j:form>
                    <f:facet name="header">
                        <h:outputText value="Share Certificate" />
                    </f:facet>
                    <h:panelGrid id="sharepanel1" width="100%" >
                        <h:panelGrid id="shareheadpanel" styleClass="header" width="100%" style="text-align-center">
                            <h:outputLabel id="lblTitle1" styleClass="headerLabel" value="Certificate Issue Form"/>
                        </h:panelGrid>
                        <h:panelGrid id="BodyPanel01" styleClass="error" columns="1" style="text-align:center"  width="100%">
                            <h:outputLabel id="errmsg1" value="#{CertIssue.shareMsg}"  styleClass="error"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel2" width="100%" styleClass="row2" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left" >
                            <h:outputLabel value="Folio No" styleClass="output"/>
                            <h:panelGroup layout="block">
                                <a4j:region id="folioRegion">
                                <h:inputText id="textfoliono" styleClass="input" style="width: 90px" value="#{CertIssue.folionoShow}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                                    <a4j:support ajaxSingle="true"  event="onblur" action="#{CertIssue.sharefoliodetails}" reRender="sharepanel13,liststatus,outFolioShow,outname,outtxtfathername,outtxtbranch,taskList,datascrollerTab,outshareno,errmsg1" focus="txtremarks">
                                    </a4j:support>
                                </h:inputText>
                                </a4j:region>
                                <h:outputText id="outFolioShow" value="#{CertIssue.foliono}" styleClass="label"/>
                            </h:panelGroup>
                            <h:outputLabel value="Name" styleClass="output"/>
                            <h:outputText id="outname" value="#{CertIssue.folioname}" styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel3" width="100%" styleClass="row1" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left" >
                            <h:outputLabel value="Father Name" styleClass="output"/>
                            <h:outputText id="outtxtfathername" styleClass="label" value="#{CertIssue.foliofathername}"/>
                            <h:outputLabel value="Branch" styleClass="output"/>
                            <h:outputText id="outtxtbranch" styleClass="label" value="#{CertIssue.city}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel13" width="100%" styleClass="row1" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left" >
                            <h:outputLabel value="Folio Balance" styleClass="output"/>
                            <h:outputText id="folioBal" styleClass="label" value="#{CertIssue.folioBalance}"/>
                            <h:outputLabel />
                            <h:outputText />
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel4" width="100%" styleClass="row2" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left">
                            <h:outputLabel value="Share Value / Available Share" styleClass="output"/>
                            <h:panelGroup layout="block">
                                <h:outputText id="outtxtsharers" value="#{CertIssue.sharevalue}" styleClass="label"><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /> </h:outputText>
                                <h:outputText styleClass="label" value=" Rs. / " />
                                <h:outputText id="outtxtnosharers" styleClass="label" value="#{CertIssue.noofshareAvailable}" />
                            </h:panelGroup>
                            <h:outputLabel value="Remarks" styleClass="output"/>
                            <h:inputText id="txtremarks" value="#{CertIssue.remark}" styleClass="label" >
                                <a4j:support ajaxSingle="true"  event="onblur" reRender="txtremarks" focus="txtamount" >
                                </a4j:support>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel5" width="100%" styleClass="row1" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left">
                            <h:outputLabel value="Certificate No" styleClass="output"/>
                            <h:inputText id="txtcertificateNo" styleClass="label" disabled="true" value="#{CertIssue.certificateno}" style="width:70px"/>
                            <a4j:support ajaxSingle="true" event="onblur"  />
                            <h:outputLabel value="No of share" styleClass="output" />
                            <h:inputText id="txtamount" styleClass="label" value="#{CertIssue.noshare}" style="width:70px">
                                <a4j:support ajaxSingle="true" event="onblur" reRender="txtamount" focus="txtIssueDate" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel7" width="100%" styleClass="row1" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left">
                            <h:outputLabel value="Issue Date" styleClass="output" />
                            <h:panelGroup>
                                <h:inputText id="txtIssueDate" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{CertIssue.issueDate}"  >
                                     <a4j:support ajaxSingle="true" event="onblur"  />
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFromProduct" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel value="Status" styleClass="output" />
                            <h:panelGroup>
                                <h:selectOneListbox id="liststatus" style="width:80px" styleClass="ddlist" size="1" value="#{CertIssue.status}" disabled="#{!CertIssue.sharePaid}">
                                    <f:selectItems value="#{CertIssue.statuslist}"/>
                                    <a4j:support ajaxSingle="true" event="onblur" action="#{CertIssue.enableShareNoToBePaid}" reRender="txtNoofshare"
                                                 oncomplete="if(#{CertIssue.status=='Inactive'}){ #{rich:element('updateBtn')}.focus(); }else { #{rich:element('txtNoofshare')}.focus(); }"/>
                                </h:selectOneListbox>
                                
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel8" width="100%" styleClass="row2" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left">
                            <h:outputLabel styleClass="output" value="No of Share to be Paid"/>
                            <h:inputText id="txtNoofshare" value="#{CertIssue.sharesToBePaid}" styleClass="label" style="width:50px" disabled="#{CertIssue.boolshareno}">
                                    <a4j:support ajaxSingle="true" event="onblur" focus="updateBtn"/>
                            </h:inputText>
                            <h:outputLabel value="Refund Date" />
                            <h:panelGroup>
                                <h:inputText id="txtRefundDate" styleClass="input calInstDate"  style="width:70px;" disabled="#{CertIssue.refundbool}" maxlength="10" value="#{CertIssue.refundDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFromProduct1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{CertIssue.datagridcert}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="3" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Cert No." /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="CertIssue Date" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="No of Share" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Amount" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="From No" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="To No" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Issue Date" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Issued By" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Status" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Remarks" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.certNo}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.certIssueDt}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.noofshares}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.amount}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.fromNo}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.toNo}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.issuedate}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.issueBy}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.status}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD" ajaxSingle="true" action="#{CertIssue.selectData}" oncomplete="#{rich:element('liststatus')}.focus();setMask()" 
                                                         reRender="outtxtsharers,listType,errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,txtNoofshare">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{CertIssue.currentdata}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab"  align="left" for="taskList" maxPages="5"/>
                            </a4j:region>
                        </h:panelGrid> 
                        <h:panelGrid id="BodyPanel4"  style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="group1" >
                                <a4j:commandButton id="updateBtn"  value="Update" ajaxSingle="true" action="#{CertIssue.btn_update}" reRender="errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,txtAmount,errmsg,listBranch,listAdviceNo,txtSeqNo" 
                                                   oncomplete="setMask();if(#{CertIssue.shareMsg == ''}){#{rich:component('shareheadView')}.hide();#{rich:element('listAdviceNo')}.focus();}" />
                                <a4j:commandButton id="refreshBtn" value="Refresh" ajaxSingle="true" action="#{CertIssue.btn_refresh}" reRender="taskList,outname,outtxtfathername,outtxtbranch,textfoliono,errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,outFolioShow" oncomplete="setMask()"/>
                                <a4j:commandButton id="exitBtn" value="Close" ajaxSingle="true" action="#{CertIssue.sharePopupClose}" onclick="#{rich:component('shareheadView')}.hide();"  reRender="taskList,outname,outtxtfathername,outtxtbranch,textfoliono,errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,listType"/>
                            </h:panelGroup>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction2" styleClass="output" value="F7-Sign View" style="color:blue;"/>
                    </h:panelGrid>
                    <a4j:region id="keyRegion">
                        <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('textfoliono')}.value!='') #{rich:component('sigView')}.show();" reRender="sigView"/>
                    </a4j:region>
                </a4j:form>
            </rich:modalPanel>
            
              <rich:modalPanel id="shareDevidendView" height="300" width="900" autosized="true" style="align:right">
                <a4j:form>
                    <f:facet name="header">
                        <h:outputText value="Share Certificate" />
                    </f:facet>
                    <h:panelGrid id="sharepanel1" width="100%" >
                        <h:panelGrid id="shareheadpanel" styleClass="header" width="100%" style="text-align-center">
                            <h:outputLabel id="lblTitle1" styleClass="headerLabel" value="Dividend Payment"/>
                        </h:panelGrid>
                        <h:panelGrid id="BodyPanel01" styleClass="error" columns="1" style="text-align:center"  width="100%">
                            <h:outputLabel id="errmsg1" value="#{CertIssue.shareMsg}"  styleClass="error"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel2" width="100%" styleClass="row2" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left" >
                            <h:outputLabel value="Folio No" styleClass="output"/>
                            <h:panelGroup layout="block">
                                <a4j:region id="folioRegion">
                                <h:inputText id="textfoliono" styleClass="input" style="width: 90px" value="#{CertIssue.folionoShow}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                                    <a4j:support ajaxSingle="true"  event="onblur" action="#{CertIssue.sharefolioInformation}" reRender="outtxtDividendBal,liststatus,outFolioShow,outname,outtxtfathername,outtxtbranch,taskList,datascrollerTab,outshareno,errmsg1" focus="txtremarks">
                                    </a4j:support>
                                </h:inputText>
                                </a4j:region>
                                <h:outputText id="outFolioShow" value="#{CertIssue.foliono}" style="color:green" styleClass="label"/>
                            </h:panelGroup>
                            <h:outputLabel value="Name" styleClass="output"/>
                            <h:outputText id="outname" value="#{CertIssue.folioname}" styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel3" width="100%" styleClass="row1" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left" >
                            <h:outputLabel value="Father Name" styleClass="output"/>
                            <h:outputText id="outtxtfathername" styleClass="label" value="#{CertIssue.foliofathername}"/>
                            <h:outputLabel value="Branch" styleClass="output"/>
                            <h:outputText id="outtxtbranch" styleClass="label" value="#{CertIssue.city}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sharepanel4" width="100%" styleClass="row2" columns="4" columnClasses="col3,col3,col3,col3" style="text-align:left">
                            <h:outputLabel value="Dividend Outstanding Balance" styleClass="output"/>
                            <h:panelGroup layout="block">
                                <h:outputText id="outtxtDividendBal" value="#{CertIssue.dividendBal}" styleClass="label"><f:convertNumber maxFractionDigits="2" minFractionDigits="2" /> </h:outputText>
                            </h:panelGroup>
                            <h:outputLabel value="Dividend Amount" styleClass="output"/>
                            <h:inputText id="txtremarks" value="#{CertIssue.dividendAmount}" styleClass="label" style = "width:80px">
                                <a4j:support ajaxSingle="true"  event="onblur" action="#{CertIssue.onbularDividend}"reRender="txtremarks,errmsg1" focus="txtamount" >
                                </a4j:support>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="BodyPanel4"  style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="group1" >
                                <a4j:commandButton id="updateBtn"  value="Update" ajaxSingle="true" action="#{CertIssue.dividendBnt}" reRender="errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,txtAmount,errmsg,listBranch,listAdviceNo,txtSeqNo" 
                                                   oncomplete="setMask();if(#{CertIssue.shareMsg == ''}){#{rich:component('shareDevidendView')}.hide();#{rich:element('listAdviceNo')}.focus();}" />
                                <a4j:commandButton id="refreshBtn" value="Refresh" ajaxSingle="true" action="#{CertIssue.btn_refresh}" reRender="outtxtDividendBal,outname,outtxtfathername,outtxtbranch,textfoliono,errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,outFolioShow" oncomplete="setMask()"/>
                                <a4j:commandButton id="exitBtn" value="Close" ajaxSingle="true" action="#{CertIssue.sharePopupClose}" onclick="#{rich:component('shareDevidendView')}.hide();"  reRender="taskList,outname,outtxtfathername,outtxtbranch,textfoliono,errmsg1,txtcertificateNo,txtamount,txtremarks,txtsharenofrom,txtsharenoto,txtIssueDate,txtRefundDate,liststatus,listType"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction2" styleClass="output" value="F7-Sign View" style="color:blue;"/>
                    </h:panelGrid>
                    <a4j:region id="keyRegion">
                        <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('textfoliono')}.value!='') #{rich:component('sigView')}.show();" reRender="sigView"/>
                    </a4j:region>
                </a4j:form>
            </rich:modalPanel>
            
            
            <rich:modalPanel id="sigView" height="380" width="700" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                        <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{CertIssue.createSignature}" value="#{CertIssue.folionoShow}"/>
                        </h:panelGroup>
                        <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="sigViewBtnPanel">
                                <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;"/>                                
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="acView" height="560" width="900" left="true">
                <f:facet name="header">
                    <h:outputText value="Account Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                        <rich:componentControl for="acView" attachTo="closelink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="acViewPanel" width="100%">
                        <h:panelGrid id="acViewRow1" columns="6" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">  
                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAccNo" style="output" value="#{CertIssue.acno}"/>
                            <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                            <h:outputText id="txtOpeningBal" style="output" value="#{CertIssue.openBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                            <h:outputText id="txtPresentBal" style="output" value="#{CertIssue.presentBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>

                        <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="txnItem" value="#{CertIssue.txnDetailList}"
                                             rowClasses="gridrow1, gridrow2" id="authTxnList" rows="8"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Account Details" /></rich:column>
                                        <rich:column breakBefore="true" style="width:"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                        <rich:column><h:outputText value="Deposite" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Authorized" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{txnItem.dt}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.details}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.instNo}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.drAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.crAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.enterBy}"/></rich:column>
                                <rich:column><h:outputText  value="#{txnItem.authBy}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                        </h:panelGrid>


                        <h:panelGrid id="pendingTxnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  var="unAuthItem" value="#{CertIssue.txnDetailUnAuthList}"
                                             rowClasses="gridrow1, gridrow2" id="pendingTxnList" rows="5"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Today's Pending Authorization" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                        <rich:column><h:outputText value="Deposite" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Authorized" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{unAuthItem.dt}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.details}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.instNo}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.drAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.crAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.enterBy}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.authBy}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="unauthDataScroll"align="left" for="pendingTxnList" maxPages="20" />
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="acViewBtnPanel">
                            <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('acView')}.hide(); return false;">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
              </rich:modalPanel>            
            <rich:hotKey id="F4Key"key="F4" handler="showAcDeatil();"/>
            <rich:hotKey key="F2" handler="showGlHeadDetails();"/>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
        </body>
    </html>
</f:view>
