<%@page pageEncoding="UTF-8" %>
<%@ include file="../tiles/include.jsp" %>
<html>
<head>
    <title>监控系统</title>
    <meta http-equiv="Content-Type" content="text/html;">

    <link href="<c:url value="/pages/style/default/style.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/pages/style/default/info.css"/>" type="text/css" rel=stylesheet>
    <html:javascript formName="monitorHostTemplateForm"/>
    <script type="text/javascript">
        function submitForm(){
            var editForm = document.forms[0];
            if(!validateMonitorHostTemplateForm(editForm)){
                return false;
            }
            var type = document.getElementById("type").value;
            if(type=="edit"){
                editForm.action = "updateHostTemplate.do?method=updateMonitorHostTemplate";
            } else if(type=="add"){
                editForm.action = "addHostTemplate.do?method=addMonitorHostTemplate";
            }
            editForm.submit();
        }
	   	 function showtuna_jmpinfo(tt,content){
		   	 if(content!=''){
			 var ttop = tt.offsetTop;    //TT控件的定位点高
			    var thei = tt.clientHeight;    //TT控件本身的高
			    var tleft = tt.offsetLeft;    //TT控件的定位点宽
			    while (tt = tt.offsetParent) {
			        ttop += tt.offsetTop;
			        tleft += tt.offsetLeft;
			    }
			    document.getElementById("tuna_jmpinfo").style.top = ttop + thei;
			    document.getElementById("tuna_jmpinfo").style.left = tleft;
			    document.getElementById("pubJumInfo_content").innerHTML =content;
			    document.getElementById("tuna_jmpinfo").style.visibility = "visible";	
		   	 }	
		 }
		 function showself(){
			    document.getElementById("tuna_jmpinfo").style.visibility = "visible";		
		 }
		 function notshowself(){
			    document.getElementById("tuna_jmpinfo").style.visibility = "hidden";		
		 }
		 function notshowtuna_jmpinfo(){
			    document.getElementById("tuna_jmpinfo").style.visibility = "hidden";		
		 }
    </script>
</head>
<body>
<html:form action="hostTemplate.do?method=addMonitorHostTemplate" method="post">
<html:hidden property="monitorHostTemplate.templateId" />
<html:hidden property="type" />
    <div style="width:100%;margin:8px 8px 0 10">
        <%--<html:errors  property="addMonitorHostTemplate"/>--%>
        <html:errors/>
        <table width=100% border="1px" cellpadding="1px" cellspacing=0 bordercolor="#FFFFFF">
            <tbody>
            <tr class="toptitle">
                <td colspan="3" style="text-align:left"><p style="font-weight:bold;"><logic:equal name="monitorHostTemplateForm" property="type" value="edit">修改</logic:equal><logic:equal name="monitorHostTemplateForm" property="type" value="add">增加</logic:equal>主机模板</p></td>
            </tr>
            <tr>
                <td class="td_title">模板名称：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.templateName"  maxlength="100"/>
                    <font color="#ff0000">*</font>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">检查命令：</td>
                <td class="td_value">
                    <html:select property="monitorHostTemplate.checkCommand.commandId" >
                        <html:option value="">-----------请选择-----------</html:option>
                        <html:options collection="hostCommands" property="commandId" labelProperty="commandName"/>
                    </html:select>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">检查命令参数：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.checkCommandArgs" styleClass="input_box" maxlength="100" />
                </td>
                <td class="td_value1">
                &nbsp;<span class="searchresult_fltlist_bgblue" onmousemove="javascript:showtuna_jmpinfo(this,'(wrta,pl%!crta,pl%) example (3000,50%!3000,80%)<br/>阈值的格式是 <rta>,<pl>%  其中<rta>是指平均响应时间(roud trip average)单位：毫秒(ms)，超过指定阈值会触发WARNNING或CRITICAL状态, <pl>是指丢包率，超过指定丢包率值同样触发WARNNING或CRITICAL状态.格式中的wrta,pl是触发WARNNING状态的平均响应时间和丢包率，crta,pl是触发CTITICAL状态的平均响应时间和丢包率');" onmouseout="javascript:notshowtuna_jmpinfo();">帮助</span>
                </td>
            </tr>
            <tr>
                <td class="td_title">检查时间域：</td>
                <td class="td_value">
                    <html:select property="monitorHostTemplate.checkTimeperiod.timeperiodId" >
                        <html:option value="">-----------请选择-----------</html:option>
                        <html:options collection="monitorTimePeriods" property="timeperiodId" labelProperty="alias"/>
                    </html:select>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">检查时间间隔：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.checkInterval" styleClass="input_box" maxlength="50"/>分钟
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">重试时间间隔：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.retryInterval" styleClass="input_box" maxlength="50"/>分钟 
                </td>
                <td class="td_value1">
                (说明：系统首次发现主机down后，为确认主机是否真实down机所进行重复检查的时间间隔.)
                </td>
            </tr>
            <tr>
                <td class="td_title">最大重试次数：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.maxCheckAttempts" styleClass="input_box" maxlength="50"/>次 
                </td>
                <td class="td_value1">
                (说明：系统首次发现主机down后，为确认主机是否真实down机所进行重复检查的次数.)
                </td>
            </tr>
            <tr>
                <td class="td_title">是否允许主动检查：</td>
                <td class="td_value">
                    <html:checkbox property="monitorHostTemplate.activeChecksEnabled" value="1"/>
                </td>
                <td class="td_value1">
                &nbsp;<span class="searchresult_fltlist_bgblue" onmousemove="javascript:showtuna_jmpinfo(this,'主动检查：由监控系统自主进行的主机在线状态检查');" onmouseout="javascript:notshowtuna_jmpinfo();">说明</span>
                </td>
            </tr>
            <tr>
                <td class="td_title">通知时间域：</td>
                <td class="td_value">
                    <html:select property="monitorHostTemplate.notificationTimeperiod.timeperiodId" >
                        <html:option value="">-----------请选择-----------</html:option>
                        <html:options collection="monitorTimePeriods" property="timeperiodId" labelProperty="alias"/>
                    </html:select>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">通知时间间隔：</td>
                <td class="td_value">
                    <html:text property="monitorHostTemplate.notificationInterval" styleClass="input_box" maxlength="50"/>分钟
                </td>
                <td class="td_value1">
                &nbsp;<span class="searchresult_fltlist_bgblue" onmousemove="javascript:showtuna_jmpinfo(this,'主机持续down状态,监控系统重复通知的时间间隔.')" onmouseout="javascript:notshowtuna_jmpinfo();">说明</span>
                </td>
            </tr>
            <tr>
                <td class="td_title">down机是否通知：</td>
                <td class="td_value">
                    <html:checkbox property="monitorHostTemplate.notifyOnDown" value="1"/>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">主机recovery是否通知：</td>
                <td class="td_value">
                    <html:checkbox property="monitorHostTemplate.notifyOnRecovery" value="1"/>
                </td>
                <td class="td_value1">
                &nbsp;<span class="searchresult_fltlist_bgblue" onmousemove="javascript:showtuna_jmpinfo(this,'recovery:主机从down机状态恢复.')" onmouseout="javascript:notshowtuna_jmpinfo();">说明</span>
                </td>
            </tr>
            <tr>
                <td class="td_title">联系人组：</td>
                <td class="td_value">
                    <html:select property="monitorHostTemplate.contactGroups.contactgroupId" >
                        <html:option value="">-----------请选择-----------</html:option>
                        <html:options collection="monitorContactgroups" property="contactgroupId" labelProperty="contactgroupName"/>
                    </html:select>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td class="td_title">主机组：</td>
                <td class="td_value">
                    <html:select property="monitorHostTemplate.hostgroups.hostgroupId" >
                        <html:option value="">-----------请选择-----------</html:option>
                        <html:options collection="monitorHostgroups" property="hostgroupId" labelProperty="groupName"/>
                    </html:select>
                </td>
                <td class="td_value1">
                </td>
            </tr>
            <tr>
                <td colspan="3"  align="center">
                    &nbsp;&nbsp;
                    <input type="button" class="button_nor" onclick="javascript:submitForm();" value=" 保 存 " />
                    <input type="reset" class="button_nor"  value=" 重 置 " />
                    <input  type="button" class="button_nor" value=" 返 回 " onclick="history.back();">
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</html:form>

<div style="visible; visibility: hidden; position: absolute; z-index: 120; display: block; width: 300px; height: 133px;" id="tuna_jmpinfo" onmouseover="showself()" onmouseout="notshowself()">
	<table width="300px" cellspacing="0" cellpadding="0" class="pubJmpInfo_jmpInfo01" id="main">
        <tbody><tr>
            <td class="top_l"></td><td class="top_m"></td><td class="top_r"></td>
        </tr>
        <tr>
            <td class="middle_l"></td>
            <td class="middle_m">
                 <div class="middle_content">
                    <h1 style="background: transparent none repeat scroll 0% 0%; -moz-background-clip: border; -moz-background-origin: padding; -moz-background-inline-policy: continuous;">说明信息</h1>                
                    <div class="pubJmpInfo_horizontal"></div>
                    <div id="pubJumInfo_content"></div>
                 </div>
            </td>
            <td class="middle_r"></td>
        </tr>
        <tr>
            <td class="bottom_l"></td><td class="bottom_m"></td><td class="bottom_r"></td>
        </tr>
    </tbody></table>
</div>
</body>

</html>
