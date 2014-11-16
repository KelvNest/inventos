<%-- 
    Document   : mensaje.jsp
    Created on : 08/11/2014, 10:33:52 AM
    Author     : seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Mensaje</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="jslt.jsp" %>
        <%@include file="estilo.jsp" %>
<!--        <script>
	  $(document).ready( function() {
	    $(".label_better").label_better({
	      easing: "bounce"
	    });
	  });
	</script>-->
    </head>
    <body>
        <header>
            <!--<img src="img/peliculaweb.png" alt="Pelicula Web"/>-->
            <%@include file="encabezado.jsp" %>        
        </header>
        <div id="pagina">
            <h1>Mensaje</h1>
            <p>${requestScope.mensaje}</p>
        </div>
            <footer><%@include file="pie.jsp"%> </footer>
    </body>
</html>
