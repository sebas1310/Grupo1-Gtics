$(document).ready(()=>{
    $('#input1, #input2').keyup(()=>{
        console.log('Entra a validar')
        validarContrasena();
    })

});
function validarContrasena(){
    const pass1 = $('#input1').val();
    const pass2 = $('#input2').val();
    const nombre = $('#nombres').val();
    const apellido = $('#apellidos').val();
    const dni = $('#dni').val();
    const celular = $('#celular').val();
    const direccion = $('#direccion').val();

    if(pass1==pass2){
        $('#button_submit').removeClass('disabled')
    }else{
        $('#button_submit').removeClass('disabled')
        $('#button_submit').addClass('disabled')

    }if(pass1.trim() === "" || pass2.trim() === "" || nombre.trim() === "" || apellido.trim() === "" || dni.trim() === "" || celular.trim() === "" || direccion.trim() === ""){
        var errorMessage = document.getElementById("error-message");
        errorMessage.style.display = "block";
        errorMessage.innerHTML = "No se permiten espacios en blanco.";
        return false;
    }

}