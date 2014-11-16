<%-- 
    Document   : ActorAdmin
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
    </head>
    <body>
        <header>
            <!--<img src="img/peliculaweb.png" alt="Pelicula Web"/>-->
            <%@include file="encabezado.jsp" %>        
        </header>
        <div id="pagina">
            <h1>Pagina principal</h1>
            <p>Este es el primer parrafo de todos O.O un super parrafo XD</p>
            <c:if test="${!empty requestScope.mensaje}">
                <article>${requestScope.mensaje}</article>
            </c:if>
            <fieldset>
                <legend>Registrar actor</legend>
                <form method="get" action="ActorAdmin">
                    <input type="text" name="id_actor" class="label_better" data-new-placeholder="Id Actor" placeholder="Id Actor">
                    <input type="text" name="nombre" class="label_better" data-new-placeholder="Nombre y apellido" placeholder="Nombre y apellido">
                    <input type="text" name="pais" class="label_better" data-new-placeholder="País" placeholder="País">
                    <select name="sexo">
                        <option value="">Seleccione el sexo</option>
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>                        
                    </select>
                    <label>Fecha de nacimiento</label>
                    <select name="dia">
                        <option value="">Día</option>
                        <c:forEach begin="1" end="31" var="dia">
                            <option value="${dia}">${dia}</option>
                        </c:forEach>                      
                    </select>
                    <select name="mes">
                        <option value="">Mes</option>
                        <c:forEach begin="0" end="11" var="mes">
                            <option value="${mes}">${mes+1}</option>
                        </c:forEach>                      
                    </select>
                    <select name="anio">
                        <option value="">Año</option>
                        <c:forEach begin="1950" end="2014" var="anio">
                            <c:set var="i" value="${2014-anio}"/>
                            <option value="${1950+i}">${1950+i}</option>
                        </c:forEach>                      
                    </select>
                    <input type="submit" name="accion" value="Registrar"/> 
                    <!--<input type="text" name="pais" class="label_better" data-new-placeholder="País" placeholder="País">-->
                </form>
            </fieldset>
        </div>
        <footer><%@include file="pie.jsp"%> </footer>
    </body>
</html>
