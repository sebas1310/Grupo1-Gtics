$(document).ready(()=>{
    $('#input1, #input2').keyup(()=>{
        console.log('Entra a validar')
        validarContrasena();
    })

});
function validarContrasena(){
    const pass1 = $('#input1').val();
    const pass2 = $('#input2').val();
    if(pass1==pass2){
        $('#button_submit').removeClass('disabled')
    }else{
        $('#button_submit').removeClass('disabled')
        $('#button_submit').addClass('disabled')

    }if(pass1.trim() === "" || pass2.trim() === ""){
        var errorMessage = document.getElementById("error-message");
        errorMessage.style.display = "block";
        errorMessage.innerHTML = "No se permiten espacios en blanco.";
        return false;
    }

}