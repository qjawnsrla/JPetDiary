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
<%@ include file="../diary/IncludeTopforDiary.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%--<div id="BackLink"><stripes:link--%>
<%--        beanclass="org.mybatis.jpetstore.web.actions.CatalogActionBean"--%>
<%--        event="viewProduct">--%>
<%--    <stripes:param name="productId" value="${actionBean.product.productId}" />--%>
<%--    Return to ${actionBean.product.productId}--%>
<%--</stripes:link></div>--%>
<html>
<style>
    #Catalog{
        background-color: dimgray;
    }
    div {
        display: block;
    }
    .order{
        text-align: right;
    }
    .diary-wrap {
        border-radius: 10px;
        display: flex;
        flex-wrap: wrap;
    }
    .card-wrap {
        max-width: 25%;
        border-radius: 10px;
        margin: 4.16%;
        display: flex;
        flex: 1 1 20%;
        flex-direction: column;
        background: #1E1E1E;
    }
    .thumbnail {
        display: block;
        width: 100%;
        height: 100%;
        text-decoration: none;
    }
    .detail {
        padding: 1rem;
        display: flex;
        flex: 1 1 20%;
        flex-direction: column;
    }
    .detail-box {
        display: block;
        text-decoration: none;
    }

    .detail-box h4 {
        font-size: 1rem;
        margin: 0px 0px 0.25rem;
        line-height: 1.5;
        word-break: break-word;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        color: #ECECEC;
    }
    .sub-info {
        font-size: 0.75rem;
        line-height: 1.5;
        color: #ECECEC;
    }
    .sub-sep {
        margin-left: 0.5rem;
        margin-right: 0.5rem;
        color: #ECECEC;
    }
    .writer-info {
        padding: 0.625rem 1rem;
        border-top: 1px solid #ECECEC;
        display: flex;
        font-size: 0.75rem;
        line-height: 1.5;
        justify-content: space-between;
    }
    .order{
        font-size: 18px;
        color: black;
    }

</style>

<body>
<div id="Catalog">
    <br> <br>
    <div class="order">
        &nbsp;<br>
        <span>정렬 기준 :
        <c:forTokens var="order" items="likes;likes,comments;comments,latest;no" delims=",">
            <c:choose>
                <c:when test="${actionBean.orderLikesOrComments==order.split(';')[1]}">
                    ${order.split(';')[0]}
                </c:when>
                <c:otherwise>
                    <stripes:link
                            beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                            event="viewDiaryBoard">
                        ${order.split(';')[0]}
                        <stripes:param name="orderLikesOrComments" value="${order.split(';')[1]}" />
                    </stripes:link >
                </c:otherwise>
            </c:choose>
        </c:forTokens>
        </span>
    </div>

    <div id="DiaryCategory"><br><br>
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'ALL' || actionBean.orderCategory == null}">
                <span style="color:black;">ALL</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="ALL" />
                    <stripes:param name="page" value="1"/>
                    <stripes:param name="reset" value="1" />
                    <span>ALL</span>
                </stripes:link>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'FISH'}">
                <span style="color:black;">FISH</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="FISH" />
                    <stripes:param name="page" value="1" />
                    <span>FISH</span>
                </stripes:link>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'DOGS'}">
                <span style="color:black;">DOGS</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="DOGS" />
                    <stripes:param name="page" value="1" />
                    <span>DOGS</span>
                </stripes:link>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'CATS'}">
                <span style="color:black;">CATS</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="CATS" />
                    <stripes:param name="page" value="1" />
                    <span>CATS</span>
                </stripes:link>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'REPTILES'}">
                <span style="color:black;">REPTILES</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="REPTILES" />
                    <stripes:param name="page" value="1" />
                    <span>REPTILES</span>
                </stripes:link>
            </c:otherwise>
        </c:choose> |
        <c:choose>
            <c:when test="${actionBean.orderCategory == 'BIRDS'}">
                <span style="color:black;">BIRDS</span>
            </c:when>
            <c:otherwise>
                <stripes:link
                        beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                        event="viewDiaryBoard">
                    <stripes:param name="orderCategory" value="BIRDS" />
                    <stripes:param name="page" value="1" />
                    <span>BIRDS</span>
                </stripes:link>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="diary-wrap">
        <c:forEach var="diary" items="${actionBean.diaryList}">
            <div class="card-wrap">
                <stripes:link class="thumbnail" beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean" event="getDiaryContent">
                    <stripes:param name="no" value="${diary.no}" />
                    <stripes:param name="page2" value="1" />
                    <img src="https://share.shbox.kr/jpetstore_war/static/${diary.imgurl}" style="width:100%; height: 100%; border-radius: 10px 10px 0 0;">
                </stripes:link>
                <div class="detail">
                    <stripes:link class="detail-box" beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean" event="getDiaryContent">
                        <stripes:param name="no" value="${diary.no}" />
                        <stripes:param name="page2" value="1" />
                        <h4>${diary.title}</h4>
                    </stripes:link>
                </div>
                <div class="sub-info">
                    <span>${diary.categoryid}</span>
                    <span class="sub-sep">.</span>
                    <span>${diary.date}</span>
                    <span class="sub-sep">.</span>
                    <span>${diary.comments} 개의 댓글</span>
                </div>
                <div class="writer-info">
                    <div style="display: flex; align-items: center; color: #ECECEC;">
                        <span>
                        by ${diary.userid}
                    </span>
                    </div>
                    <div style="display: flex; align-items: center; color:#ECECEC;">
                        ${diary.likes}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="search">
        <div>
            <stripes:form beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean">
                <stripes:text size="30%" name="keyword"/>
                <stripes:param name="page" value="1"/>
                <stripes:submit name="viewDiaryBoard" value="Search" />
            </stripes:form>
        </div>
    </div>
    <br>
    <div class="page">
        <c:if test="${actionBean.prev}">
            <stripes:link
                    beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                    event="viewDiaryBoard">
                ◁
                <stripes:param name="page" value="${actionBean.page - 1}" />
            </stripes:link>
        </c:if>
        <c:forEach begin="${actionBean.beginPage}" end="${actionBean.endPage}" step="1" var="index">
            <c:choose>
                <c:when test="${actionBean.page==index}">
                    ${index}
                </c:when>
                <c:otherwise>
                    <stripes:link
                            beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                            event="viewDiaryBoard">
                        ${index}
                        <stripes:param name="page" value="${index}" />
                    </stripes:link>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${actionBean.next}">
            <stripes:link
                    beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                    event="viewDiaryBoard">
                ▷
                <stripes:param name="page" value="${actionBean.page + 1}" />
            </stripes:link>
        </c:if>
    </div>
    <div style="margin-top: 20px; padding-top: 20px">
        <stripes:link class="Button"
                      beanclass="org.mybatis.jpetstore.web.actions.DiaryActionBean"
                      event="getNewDiaryForm" >
            Write Diary
        </stripes:link>
    </div>
</div>
</body>
</html>

<%@ include file="../common/IncludeBottom.jsp"%>



