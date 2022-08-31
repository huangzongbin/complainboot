<#--
 @author 殷帅
 @version 1.0
 @Description: 验证模板
 @date 2018-05-15 15:50:00
 -->
<#macro validate validate><#if (validate)?contains("required")> required</#if><#if validate?contains("isUserName")> isUserName="true"</#if><#if validate?contains("isMobile")> isMobile="true"</#if><#if validate?contains("isTel")> isTel="true"</#if><#if validate?contains("isPhone")> isPhone="true"</#if><#if validate?contains("isStringAndNum")> isStringAndNum="true"</#if><#if validate?contains("chcharacter")> chcharacter="true"</#if><#if validate?contains("isZipCode")> isZipCode="true"</#if><#if validate?contains("rangelength")> rangelength="1,20"</#if></#macro>
