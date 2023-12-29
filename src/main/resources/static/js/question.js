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
        grecaptcha.execute(captchaKey, {action: 'submit'}).then(function (token) {
            const name = $("#name").val();
            const email = $("#email").val();
            const phone = $("#phone").val();
            const textarea = $("#textarea").val();
            $.ajax({
                type: form.attr('method'),
                url: form.attr('action'),
                dataType: "json",
                data: {
                    name: name,
                    email: email,
                    phone: phone,
                    textarea: textarea,
                    token: token
                },
                success: function (response) {
                    alertMessage(response)
                },
                error: function (data) {
                    alert("Jeśteś botem lub wystąpił błąd serwera");
                },
            });
        });
    });
});

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




