<%-- 
    Document   : actores
    Created on : 01/11/2014, 10:05:52 AM
    Author     : seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organizador de películas</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="jslt.jsp" %>
        <%@include file="estilo.jsp" %>
        <%@include file="labelbetter.jsp" %>
        <!--        <script>
                  $(document).ready( function() {
                    $(".label_better").label_better({
                      easing: "bounce"
                    });
                  });
                </script>-->
    </head>
    <body>        
        <div id="bgVentanaModal" onclick="cancelarActor()">
            <div id="msjajax"></div>
            <div id="dtactor"></div>
        </div>
        <header>
            <!--<img src="img/peliculaweb.png" alt="Pelicula Web"/>-->
            <%@include file="encabezado.jsp" %>        
        </header>
        <c:if test="${!empty requestScope.mensaje}">
            <article>${requestScope.mensaje}</article>
            </c:if>
        <div id="pagina">
            <h1>Actores</h1>
            <form>
                <label for="nombre">Nombre:<label/>
                    <input type="search" id="nombre" name="nombre" placeholder="Nombre del actor"/>
                    <a href="actores.jsp"/>Mostrar todos<a/>
            </form>
            <jsp:useBean class="pw.entity.GestorLista" id="gl"/>
            <c:choose>
                <c:when test="${empty param.nombre}">
                    <c:set var="actores" value="${gl.listarActores()}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="actores" value="${gl.listarActores(param.nombre)}"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${!empty actores}">
                    <table>
                        <tr>
                            <th> Id Actor</th>
                            <th> Nombre</th>
                            <th> Sexo</th>
                            <th> País</th>
                            <th> </th>
                        </tr>
                        <c:forEach var="actor" items="${actores}">
                            <tr>
                                <td><a href="#" onclick="editarActor('${actor.idActor}')">${actor.idActor}</a></td>
                                <td>${actor.nombre}</td>
                                <td>${actor.sexo}</td>
                                <td>${actor.pais}</td>
                                <td><a href="#" onclick="eliminarActor('${actor.idActor}')">X</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <article>
                        <h1>La busqueda no arrojo resultados</h1>
                        <p>No hay nada que mostrar :s</p>
                    </article>
                    
                   
                </c:otherwise>
            </c:choose>

        </div>
        <footer><%@include file="pie.jsp"%> </footer>
    </body>
</html>
