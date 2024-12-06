<%--

       Copyright 2010-2022 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          https://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

--%>
<%@ taglib prefix="stripes"
		   uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<link rel="StyleSheet" href="../css/jpetstore.css" type="text/css"
		  media="screen"/>

	<meta name="generator"
		  content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org"/>
	<title>JPetStore Demo</title>
	<meta content="text/html; charset=windows-1252"
		  http-equiv="Content-Type"/>
	<meta http-equiv="Cache-Control" content="max-age=0"/>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT"/>
	<meta http-equiv="Pragma" content="no-cache"/>
</head>

<body>

<div id="Header">

	<div id="Logo">
		<div id="LogoContent">
			<stripes:link
					beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean">
				<img src="../images/logo-topbar.gif"/>
			</stripes:link></div>
	</div>

	<div id="Menu">
		<div id="MenuContent" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
			<stripes:link
					beanclass="org.mybatis.jpetstore.web.actions.CartActionBean"
					event="viewCart">
				<img align="middle" name="img_cart" src="../images/cart.gif"/>
			</stripes:link>

			<img align="middle" src="../images/separator.gif"/>

			<c:if test="${sessionScope.accountBean == null}">
				<stripes:link
						beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
						event="signonForm">
					Sign In
				</stripes:link>
			</c:if>

			<c:if test="${sessionScope.accountBean != null}">
				<c:if test="${!sessionScope.accountBean.authenticated}">
					<stripes:link
							beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
							event="signonForm">
						Sign In
					</stripes:link>
				</c:if>
			</c:if>

			<c:if test="${sessionScope.accountBean != null}">
				<c:if test="${sessionScope.accountBean.authenticated}">
					<stripes:link
							beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
							event="signoff">
						Sign Out
					</stripes:link>

					<img align="middle" src="../images/separator.gif"/>
					<stripes:link
							beanclass="org.mybatis.jpetstore.web.actions.AccountActionBean"
							event="editAccountForm">
						My Account
					</stripes:link>
					<c:if test="${sessionScope.accountBean.account.role eq 1}">
						<img align="middle" src="../images/separator.gif" />
						<stripes:link
								beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean"
								event="viewAllProduct">
							Admin Dashboard
						</stripes:link>
					</c:if>
				</c:if>
			</c:if>

			<img align="middle" src="../images/separator.gif"/>
			<stripes:link
					beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
					event="viewDiaryBoard">
				<stripes:param name="page" value="1"/>
				<stripes:param name="reset" value="1" />
				Pet Diary
			</stripes:link>
			<img align="middle" src="../images/separator.gif"/>

			<c:if test="${sessionScope.accountBean != null}">
				<c:if test="${sessionScope.accountBean.authenticated}">
					<stripes:link
							beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
							event="viewMyDiary">
						<stripes:param name="page2" value="1" />
						My Diary
						<img align="middle" src="../images/separator.gif"/>
					</stripes:link>
				</c:if>
			</c:if>
			<a href="../help.html">?</a></div>
	</div>

	<div id="Search">
		<div id="SearchContent"><stripes:form
				beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean">
			<stripes:text name="keyword" size="14"/>
			<stripes:submit name="searchProducts" value="Search"/>
		</stripes:form></div>
	</div>


</div>

<div id="Content">
<stripes:messages/>