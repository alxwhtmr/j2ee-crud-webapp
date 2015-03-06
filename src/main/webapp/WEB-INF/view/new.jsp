<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/buttons-min.css">
        <link rel="stylesheet" type="text/css" href="css/forms-min.css">
        <title>New record</title>
        
        <script>
            function closeSave(){
                var select = document.getElementById("ddl");
                var group = select.options[select.selectedIndex].getAttribute("groupId");
                console.log("new submit");
                function refresh() {
                    window.opener.location = "http://" + window.opener.location.host + window.opener.location.pathname + "?" + group + "#";
                    window.opener.location.reload(true);
                }
                
                document.forms['muscleDetails'].submit();
                setTimeout(refresh(), 3000);

                window.close();
            }
            function closeCancel(){
                window.close();
            }

            function foo(win) {
                win.opener.location.reload();
            }
        </script>
    </head>
    
    
    <body id="newRecordBody">
        <div id="newRecordDiv">
            <form class="pure-form" action="save" method="POST" name="muscleDetails">            
                Group:&nbsp;
                <select id="ddl" name="muscleGroup">
                    <c:forEach var="muscleGroup" items="${muscleGroups}">
                        <option groupId="${muscleGroup.idMuscleGroup}">                          
                            ${muscleGroup.nameEn}
                        </option>
                    </c:forEach>
                </select>
                <br><br>
                <label for="nameRu">Russian title:</label><br>
                <input class="pure-input-1-2" type="text" name="nameRu" value="testRu" required>
                <br><br>
                Latin title:<br>
                <input class="pure-input-1-2" type="text" name="nameLatin" value="testEn" required>
                <br><br>
                Description:<br>
                <textarea cols="40" rows="5" name="description" required>description</textarea><br><br>
                Example activity:<br>
                <textarea cols="40" rows="5" name="example">example</textarea><br><br>
                <input type="button" class="pure-button" value="Create" onclick="closeSave();">
                &nbsp;&nbsp;
                <input type="button" class="button-small button-secondary pure-button" value="Cancel" onclick="closeCancel();">
            </form>
        </div>
    </body>
</html>
