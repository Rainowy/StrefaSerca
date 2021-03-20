$(document).ready(function () {
    $('#question_submit_button').click(function () {
        ValidateQuestionForm();
        //toggle alert
        if ($('#bsalert').hasClass('in')) {
            toggleAlert()
        }
    });
});

/** Ask me a question code */
//jQuery validation method
function ValidateQuestionForm() {
    $('#ask_question_form').validate({
        errorClass: "my-error-class",
        // validClass: "my-valid-class",
        rules: {
            name: {
                required: true
            },
            email: {
                required: true
            },
            textarea: {
                required: true,
                minlength: 10
            }
        },
        messages: {
            name: "Wpisz swoje imię",
            email: "Wpisz prawidłowy email",
            textarea: "Wpisz przynajmniej 10 znaków"
        }
    })
    if ($('#ask_question_form').valid()) {
        $('#ask_question').modal('show');
    }
}

$('#send_question').click(function (e) {
    let form = $('#ask_question_form');
    e.preventDefault();
    grecaptcha.ready(function () {
        grecaptcha.execute('6LfqCYYaAAAAAIkmr1LIjM_SfBAh-P_UvElWNqf2', {action: 'submit'}).then(function (token) {
            console.log(token)
            // Add your logic to submit to your backend server here.
            var name = $("#name").val();
            var email = $("#email").val();
            var phone = $("#phone").val();
            var textarea = $("#textarea").val();
            console.log(token)
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
                data: {
                    name: name,
                    email: email,
                    phone: phone,
                    textarea: textarea,
                     token: token
                },
                dataType: "json",
                success: function (response) {
                    console.log("OTO RESPONSE " + response)
                    if (response) {
                        alertMessage(response)
                    } else {
                        alertMessage(response)
                    }
                },
                error: function (data) {
                    console.log(data + "NIE WYSŁANO")
                },
                // beforeSend: function(){
                //     $("#statutConnexion").html("Traitement de votre requête d'authentification en cours...");
                // },
                // success: function(response){
                //     $("#statutConnexion").html(response.Message);
                //     if(response.Victoire){
                //         $("#formulaireConnexion").slideUp();
                //         window.location.replace("/compte");
                //     }
                //     else{
                //         grecaptcha.reset();
                //     }
                // },
                // error: function(){
                //     $("#statutConnexion").html("La communication avec le système d'authentification n'a pas pu être établie. Veuillez réessayer.");
                //     grecaptcha.reset();
                // }
            });


            // $.ajax({
            //     type: form.attr('method'), // method attribute of form
            //     url: form.attr('action'),  // action attribute of form
            //     data: form.serialize(),
            //
            //     success: function (response) {
            //
            //         if (response) {
            //             alertMessage(response)
            //         } else {
            //             alertMessage(response)
            //         }
            //
            //
            //     },
            //     error: function (data) {
            //         console.log(data + "NIE WYSŁANO")
            //     },
            // });
        });
    });

    // $.ajax({
    //     type: form.attr('method'), // method attribute of form
    //     url: form.attr('action'),  // action attribute of form
    //     data: form.serialize(),
    //     success: function (response) {
    //
    //         if (response) {
    //             alertMessage(response)
    //         } else {
    //             alertMessage(response)
    //         }
    //
    //
    //     },
    //     error: function (data) {
    //         console.log(data + "NIE WYSŁANO")
    //     },
    // });
});
// $('#send_question').click(function (e) {
//     let form = $('#ask_question_form');
//     e.preventDefault();
//
//     // var onloadCallback = function(){
//         // grecaptcha.render("emplacementRecaptcha",{
//         //     "sitekey": "6LfqCYYaAAAAAIkmr1LIjM_SfBAh-P_UvElWNqf2",
//         //     "badge": "inline",
//         //     "type": "image",
//         //     "size": "invisible",
//         //     "callback": onSubmit
//         // });
//
//     // };
//     // var clientId = grecaptcha.render('inline-badge', {
//     //     'sitekey': '6Ldqyn4UAAAAAN37vF4e1vsebmNYIA9UVXZ_RfSp',
//     //     'badge': 'inline',
//     //     'size': 'invisible'
//     // });
//     // grecaptcha.ready(function() {
//     //     grecaptcha.execute(clientId, {
//     //         action: 'action_name'
//     //     })
//     //         .then(function(token) {
//     //             // Verify the token on the server.
//     //         });
//     // });
//     grecaptcha.ready(function() {
//         grecaptcha.execute('6Ldqyn4UAAAAAN37vF4e1vsebmNYIA9UVXZ_RfSp', {action: 'submit'}).then(function(token) {
//             // Add your logic to submit to your backend server here.
//             console.log(token)
//             // var onSubmit = function(token){
//             var name = $("#name").val();
//             var email = $("#email").val();
//             var phone = $("#phone").val();
//             var textarea = $("#textarea").val();
//             console.log(token)
//             $.ajax({
//                 type: form.attr('method'),
//                 url: form.attr('action'),
//                 data:{
//                     name: name,
//                     email: email,
//                     phone: phone,
//                     textarea: textarea,
//                     // token: token
//                 },
//                 dataType: "json",
//                 success: function (response) {
//                     if (response) {
//                         alertMessage(response)
//                     } else {
//                         alertMessage(response)
//                     }
//                 },
//                 error: function (data) {
//                     console.log(data + "NIE WYSŁANO")
//                 },
//                 // beforeSend: function(){
//                 //     $("#statutConnexion").html("Traitement de votre requête d'authentification en cours...");
//                 // },
//                 // success: function(response){
//                 //     $("#statutConnexion").html(response.Message);
//                 //     if(response.Victoire){
//                 //         $("#formulaireConnexion").slideUp();
//                 //         window.location.replace("/compte");
//                 //     }
//                 //     else{
//                 //         grecaptcha.reset();
//                 //     }
//                 // },
//                 // error: function(){
//                 //     $("#statutConnexion").html("La communication avec le système d'authentification n'a pas pu être établie. Veuillez réessayer.");
//                 //     grecaptcha.reset();
//                 // }
//             });
//
//
//
//
//             // $.ajax({
//             //     type: form.attr('method'), // method attribute of form
//             //     url: form.attr('action'),  // action attribute of form
//             //     data: form.serialize(),
//             //     success: function (response) {
//             //         if (response) {
//             //             alertMessage(response)
//             //         } else {
//             //             alertMessage(response)
//             //         }
//             //     },
//             //     error: function (data) {
//             //         console.log(data + "NIE WYSŁANO")
//             //     },
//             // });
//         });
//     });
//
//
//     // // var onSubmit = function(token){
//     //     var name = $("#name").val();
//     //     var email = $("#email").val();
//     //     var phone = $("#phone").val();
//     //     var textarea = $("#textarea").val();
//     //     console.log(token)
//     //     $.ajax({
//     //         type: form.attr('method'),
//     //         url: form.attr('action'),
//     //         data:{
//     //             name: name,
//     //             email: email,
//     //             phone: phone,
//     //             textarea: textarea,
//     //             // token: token
//     //         },
//     //         dataType: "json",
//     //         success: function (response) {
//     //             if (response) {
//     //                 alertMessage(response)
//     //             } else {
//     //                 alertMessage(response)
//     //             }
//     //         },
//     //         error: function (data) {
//     //             console.log(data + "NIE WYSŁANO")
//     //         },
//     //         // beforeSend: function(){
//     //         //     $("#statutConnexion").html("Traitement de votre requête d'authentification en cours...");
//     //         // },
//     //         // success: function(response){
//     //         //     $("#statutConnexion").html(response.Message);
//     //         //     if(response.Victoire){
//     //         //         $("#formulaireConnexion").slideUp();
//     //         //         window.location.replace("/compte");
//     //         //     }
//     //         //     else{
//     //         //         grecaptcha.reset();
//     //         //     }
//     //         // },
//     //         // error: function(){
//     //         //     $("#statutConnexion").html("La communication avec le système d'authentification n'a pas pu être établie. Veuillez réessayer.");
//     //         //     grecaptcha.reset();
//     //         // }
//     //     });
//     //
//     //
//     //
//     //
//     // // $.ajax({
//     // //     type: form.attr('method'), // method attribute of form
//     // //     url: form.attr('action'),  // action attribute of form
//     // //     data: form.serialize(),
//     // //     success: function (response) {
//     // //         if (response) {
//     // //             alertMessage(response)
//     // //         } else {
//     // //             alertMessage(response)
//     // //         }
//     // //     },
//     // //     error: function (data) {
//     // //         console.log(data + "NIE WYSŁANO")
//     // //     },
//     // // });
// });

function toggleAlert() {
    $(".alert").toggleClass('in out');
    return false; // Keep close.bs.alert event from removing from DOM
}

function alertMessage(response) {
    let alert = $(".alert");
    alert.toggleClass('in out')
    if (response) {
        alert.addClass("alert-success");
        $(".text-alert").html("Wiadomość została wysłana")
        return false;
    } else {
        alert.addClass("alert-danger");
        $(".text-alert").html('Wiadomość nie została wysłana, <br> spróbuj ponownie później')
        return false;
    }
}

/** */




