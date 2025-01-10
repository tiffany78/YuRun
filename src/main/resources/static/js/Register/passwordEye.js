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

let eyeconfrim = document.getElementById("eyeiconConfrim");
let confrim = document.getElementById("confrimInput");

eyeconfrim.onclick = function(){
    if(confrim.type == "password"){
        confrim.type = "text";
        eyeconfrim.src = "/images/eye-open.png"
    }
    else{
        confrim.type = "password";
        eyeconfrim.src = "/images/eye-close.png"
    }
}