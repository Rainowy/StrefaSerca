$(document).ready(function () {
    //Turn off form submitting with enter
    $('#request_newsletter').bind('keydown', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
        }
    });
    $('#question_submit_button').click(function () {
        ValidateQuestionForm();
        //toggle alert
        if ($('#bsalert').hasClass('in')) {
            toggleAlert()
        }
    });
    $('#newsletter_submit_button').click(function (e) {
        ValidateNewsletterForm(e);
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
    var form = $('#ask_question_form');
    e.preventDefault();
    $.ajax({
        type: form.attr('method'), // method attribute of form
        url: form.attr('action'),  // action attribute of form
        data: form.serialize(),
        success: function () {
            toggleAlert();
            $(form)[0].reset();
        },
        error: function (data) {
            console.log(data + "NIE WYSŁANO")
            // successmessage = 'Error';
            // $("label#successmessage").text(successmessage);
        },
    });
});

function toggleAlert() {
    $(".alert").toggleClass('in out');
    return false; // Keep close.bs.alert event from removing from DOM
}

/** */

/** Newsletter code */
function ValidateNewsletterForm(e) {
    $('#request_newsletter').validate({
        errorClass: "my-error-class",
        // validClass: "my-valid-class",
        rules: {
            newsletter: {
                required: true
            },
        },
        messages: {
            newsletter: "Wpisz prawidłowy adres email",
        },
        errorElement: 'div',
        errorLabelContainer: '.errorTxt'
    })
    if ($('#request_newsletter').valid()) {
        $('#mail_confirm').modal('show');
        sendNewsletter(e)
    }

    function sendNewsletter(e) {
        var form = $('#request_newsletter');
        e.preventDefault();
        $.ajax({
            type: form.attr('method'), // method attribute of form
            url: form.attr('action'),  // action attribute of form
            data: form.serialize()
        });
    }
}

/** */


