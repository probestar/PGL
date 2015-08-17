<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
<!--
    function langSelecter_onChanged() {
        document.langForm.submit();
    }
//-->
</script>

<div id="langselector" style="display: block;float: right;margin-right: 8px;/*background: url(/lang/css/img/miandian_bg.png) repeat;
border-radius: 3px; color:#fff; z-index:99;
border: 1px solid #3a87d8; position: absolute;top:30%*/">
<s:set name="SESSION_LOCALE" value="#session['WW_TRANS_I18N_LOCALE']"/>
<s:bean id="locales" name="org.pgl.util.Locales"/>
<form  action="" method="post" name="langForm" style="font-size: 14px;">
    Language: <s:select label="Language" 
        list="#locales.locales" listKey="value"    listValue="key"
        value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"
        name="request_locale" id="langSelecter" 
        onchange="langSelecter_onChanged()" theme="simple"/>
</form>
</div>