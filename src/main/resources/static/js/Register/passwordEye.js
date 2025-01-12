//untuk see password di register
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

//untuk see confrim password di register
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

//indikator kuatnya password
var pass = document.getElementById("passwordInput");
var msg = document.getElementById("pesan");
var str = document.getElementById("strenght");

pass.addEventListener('input', () => {
    if(pass.value.length > 0){
        msg.style.display = "block";
        pass.style.borderColor = "none";
    }
    else{
        msg.style.display = "none";
        pass.style.borderColor = "none";
    }
    if(pass.value.length < 4){
        str.innerHTML = "weak";
        pass.style.borderColor = "#ff5925";
        msg.style.color = "#ff5925";
    }
    else if (pass.value.length >= 4 && pass.value.length < 8){
        str.innerHTML = "medium";
        pass.style.borderColor = "#FFA500";
        msg.style.color = "#FFA500";
    }
    else if(pass.value.length >= 8){
        str.innerHTML = "strong";
        pass.style.borderColor = "#26d730";
        msg.style.color = "#26d730";

    }

})
