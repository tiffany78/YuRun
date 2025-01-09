let eyeicon = document.getElementById("eyeiconPass");
let password = document.getElementById("passwordInput");

eyeicon.onclick = function(){
    if(password.type == "password"){
        password.type = "text";
        eyeicon.src = "/images/eye-open.png"
    }
    else{
        password.type = "password";
        eyeicon.src = "/images/eye-close.png"
    }
}