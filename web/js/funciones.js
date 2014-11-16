function editarActor(id){
    ajaxActor(id, "ajax/editarActor.jsp");
}

function eliminarActor(id){
 ajaxActor(id, "ajax/eliminarActor.jsp");
}

function ajaxActor(id, url){
    var msjespera = "..::Eliminar al actor::..";
    if(id!==''){
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url:url,
            type: "POST",
            data:{id:id},
            beforeSend: function () {
                $("#msjajax").html(msjespera);
                $("#msjajax").fadeIn();
            },
            complete: function () {
                $("#msjajax").fadeOut();
            },
            success: function (data) {
                $('#dtactor').html(data);
            }
        });
    }
}

function cancelarActor(){
    $("#bgVentanaModal").fadeOut();
    $("#dtactor").html("");
}