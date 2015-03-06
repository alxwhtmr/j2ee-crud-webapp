<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        
        <title>prj</title>
    </head>
    <body>
        <div id="main">

            <div id="indexLeftColumn">
                <div id="usage">
                    <p>This is a human body muscles database</p>
                    <ul>
                        <strong>Functions:</strong>
                        <li>You can choose muscle group: the detailed information about related muscles will be shown</li>
                        <li>You can add a new muscle and its details to any presented group</li>
                        <li>You can search specific muscle in a search form</li>
                    </ul>
                </div>
            </div>
            
            <div id="indexRightColumn">
                <c:forEach var="muscleGroup" items="${muscleGroups}">
                    <div class="groupBox">
                        <a href="showItems?${muscleGroup.idMuscleGroup}">
                            <span class="groupLabelText">${muscleGroup.nameEn}</span>
                            <img class="bparts" src="${initParam.imagePath}${muscleGroup.nameEn}.png"
                                 alt="${muscleGroup.nameEn}">
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
