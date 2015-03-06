<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script>
            function newPopupShowItems(url) {
                popupWindow = window.open(
                    url,'popUpWindow',
                    'height=630,width=630,left=100,top=50,resizable=no,scrollbars=no,toolbar=no,menubar=no,location=no,directories=no,status=yes');
            }
            
            function confirmDelete(id, group) {
                function refresh() {
                    window.location = "http://" + window.location.host + window.location.pathname + "?" + group;
                    window.opener.location.reload(true);
                }
                var r = confirm("Are you sure?");
                if (r == true) {
                    jsp = "";
                    console.log("Deleting " + id);
                    xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function() {
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                            console.log("response=" + xmlhttp.responseText);
                            jsp = xmlhttp.responseText;
                        }
                        setTimeout(refresh, 500);
                    }
                    xmlhttp.open("POST","delete?id=" + id + "&group=" + group, true);
                    xmlhttp.send();
                }
            }
        </script>
        
            <div id="showItemsLeftColumn">
                <c:forEach var="muscleGroup" items="${muscleGroups}">
                    <c:choose>
                        <c:when test="${muscleGroup.idMuscleGroup == pageContext.request.queryString}">
                            <div class="showItemsGroupBox" id="selectedGroup">
                                <a href="showItems?${muscleGroup.idMuscleGroup}">
                                    <span class="groupText">${muscleGroup.nameEn}</span>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="showItemsGroupBox">
                                <a href="showItems?${muscleGroup.idMuscleGroup}">
                                     <span class="groupText">${muscleGroup.nameEn}</span>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div id="showItemsRightColumn">
                <c:if test="${muscles == null}">
                    <div id="nothing">
                        Nothing found
                    </div>
                </c:if>
                <c:forEach var="muscle" items="${muscles}" varStatus="iter">
                    <div class="itemDetails">
                        <div class="itemNameBox">
                            ${muscle.nameRu}<br>
                            [лат. ${muscle.nameLatin}]
                        </div>
                        <div class="itemDescriptionBox">
                            <p>${muscle.description}</p>
                        </div>
                        <div class="itemFunctionBox">
                            <p>${muscle.example}</p>
                        </div>
                        <div class="itemEditDelBox">
                            <div class="itemEdit">
                                <a href="JavaScript:newPopupShowItems('edit?${muscle.idMuscle}')">
                                    <img class="icons" src="${initParam.imagePath}edit.png" alt="[edit]">
                                </a>
                            </div>
                            <hr id="editDelSeparator">
                            <div class="itemDel">
                                <a href="#">
                                    <img class="icons" src="${initParam.imagePath}trash.png" alt="[del]" onclick="confirmDelete('${muscle.idMuscle}', '${muscle.fkMuscleGroup.idMuscleGroup}');">
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
