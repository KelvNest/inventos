<%@include file="../jslt.jsp" %>
 
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="pw.entity.GestorLista" id="gl"/>
        <c:set var="actor" value="${gl.buscarActor(id)}"/>
       
        <fieldset class="dialogoModal">
            <legend>¿Eliminar Actor ${id}?</legend>
            <form method="post" action="ActorAdmin">
                <input type="hidden" name="id_actor" value="${id}">
                <input type="hidden" name="accion" value="Eliminar">
                <p> ¿Desea eliminar al actor ${actor.nombre}?,
                Pulse Si para continuar, o No para cancelar</p>                
                <input type="submit" name="x" value="Si"/> 
                <input type="button" value="No" onclick="cancelarActor()"/>
                <!--<input type="text" name="pais" class="label_better" data-new-placeholder="País" placeholder="País">-->
            </form>
        </fieldset>
    </c:when>
    <c:otherwise>
        <div>
            Error
        </div>
    </c:otherwise>
</c:choose>

<c:set var="actor" value="${gl.buscarActor(id)}"/>
<div></div>