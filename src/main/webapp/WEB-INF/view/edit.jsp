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
    </head>
    <script>
        function closeSave(){
            function refresh() {
                window.opener.location = "http://" + window.opener.location.host + window.opener.location.pathname + "?" + ${muscle.fkMuscleGroup.idMuscleGroup};
                window.opener.location.reload(true);
            }
            document.forms['muscleDetails'].submit();
            setTimeout(refresh(), 1000);
            window.close();
        }
        function closeCancel(){
            window.close();
        }
        
        function foo(win) {
            win.opener.location.reload();
        }
    </script>
    
    <body>
        
        <div id="newRecordDiv">
            <form class="pure-form" action="change" method="POST" name="muscleDetails"> 
                <input type="hidden" name="idMuscle" value="${muscle.idMuscle}">
                <label for="nameRu">Russian title:</label><br>
                <input class="pure-input-1-3" type="text" name="nameRu" value="${muscle.nameRu}" required>
                <br><br>
                Latin title:<br>
                <input class="pure-input-1-3" type="text" name="nameLatin" value="${muscle.nameLatin}" required>
                <br><br>
                Description:<br>
                <textarea cols="40" rows="5" name="description" required>${muscle.description}</textarea><br><br>
                Example activity:<br>
                <textarea cols="40" rows="5" name="example">${muscle.example}</textarea><br><br>
                <input type="button" class="pure-button" value="Save" onclick="closeSave();">
                &nbsp;&nbsp;
                <input type="button" class="button-small button-secondary pure-button" value="Cancel" onclick="closeCancel();">
            </form>
        </div>
    </body>
</html>
