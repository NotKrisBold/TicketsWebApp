var mod = (function(){

    var register_form = document.querySelector("#register_form");
    var firstName = document.querySelector("#firstname");
    var lastName = document.querySelector("#lastname");
    var userName = document.querySelector("#username");
    var password = document.querySelector("#password");
    var password_confirm = document.querySelector("#password_confirm");

    var keys = [
        {
            name: "firstname",
            field: firstName,
            verified: false,
            verifierFunc: function(firstname){
                return (/^[a-zA-Z]+$/g).test(firstname);
            }
        },
        {
            name: "lastname",
            field: lastName,
            verified: false,
            verifierFunc: function(lastname){
                return (/^[a-zA-Z]+$/g).test(lastname);
            }
        },
        {
            name: "username",
            field: userName,
            verified: false,
            verifierFunc: function(username){
                return (/^[a-zA-Z0-9_]+$/g).test(username);
            }
        },
        {
            name: "password",
            field: password,
            verified: false,
            verifierFunc: function(password){
                return (/^[a-zA-Z0-9_]{8,15}$/g).test(password);
            }
        },
        {
            name: "password_confirm",
            field: password_confirm,
            verified: false,
            verifierFunc: function(password_confirm){
                return (/^[a-zA-Z0-9_]{8,15}$/g).test(password_confirm);
            }
        }
    ];

    for(var i = 0, l=keys.length; i<l; i+=1){
        (function() {
            var current = keys[i];
            current.field.addEventListener("input", eventHandler.bind(this, current));
        })();
    }

    function eventHandler(currentElement, evt) {
        if (evt.target.value.length == 0) {
            currentElement.field.classList.remove('is-invalid');
            currentElement.field.classList.remove('is-valid');
            currentElement.verified = false;
            checkAllVerified();
            return;
        }

        if (currentElement.verifierFunc(evt.target.value)) {
            if (currentElement.field.classList.contains('is-invalid')) {
                currentElement.field.classList.remove('is-invalid')
            }
            currentElement.field.classList.add('is-valid');
            currentElement.verified = true;

        } else {
            if (currentElement.field.classList.contains('is-valid')) {
                currentElement.field.classList.remove('is-valid')
            }
            currentElement.field.classList.add('is-invalid');
            currentElement.verified = false;
        }
        (checkAllVerified() && checkPasswordMatch()) ? createButton() : removeButton();
    }

    function checkAllVerified(){
        var filtered = keys.filter(function(elem){
            return elem.verified == true;
        });

        if(filtered.length==keys.length) {
            return true;
        }
        return false;
    }

    function checkPasswordMatch(){
        return password.value === password_confirm.value;
    }

    function createButton() {
        var container = document.querySelector("#register_submit_container")
        var reg = document.querySelector("#register_submit");
        //check if already present
        if (reg) {
            return;
        }

        var button = document.createElement("input");
        button.id = "register_submit";
        button.type = "submit";
        button.value = "Registrati";
        button.classList.add("btn", "btn-primary");
        container.appendChild(button);
    }

    function removeButton(){
        var container = document.querySelector("#register_submit_container")
        var reg = document.querySelector("#register_submit");
        if(reg){
            container.removeChild(reg);
        }
    }

})();