<%@include file="../jslt.jsp" %>
 
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="pw.entity.GestorLista" id="gl"/>
        <c:set var="actor" value="${gl.buscarActor(id)}"/>
       
        <fieldset class="dialogoModal">
            <legend>Editar Actor ${id}</legend>
            <form method="get" action="ActorAdmin">
                <input type="hidden" name="id_actor" value="${id}">
                <input type="text" name="nombre" class="label_better" 
                       data-new-placeholder="Nombre y apellido" 
                       placeholder="Nombre y apellido" value="${actor.nombre}">
                <input type="text" name="pais" class="label_better" 
                       data-new-placeholder="País" placeholder="País" 
                       value="${actor.pais}">
                <select name="sexo">
                    <option value="">Seleccione el sexo</option>
                    <option value="M" <c:if test="${actor.sexo=='M'}"> selected</c:if>>Masculino</option>
                    <option value="F" <c:if test="${actor.sexo=='F'}"> selected</c:if>>Femenino</option>                        
                </select>
                    
                <label>Fecha de nacimiento</label>
<!--                <select name="dia">
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
                </select>-->
                <input type="date" name="fechaNacimiento" value="${actor.fechaNacimiento}"/>
                <input type="submit" name="accion" value="Editar"/> 
                <input type="button" value="Cancelar" onclick="cancelarActor()"/>
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