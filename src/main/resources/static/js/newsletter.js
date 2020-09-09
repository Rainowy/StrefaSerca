$(document).ready(function () {
    //Turn off form submitting with enter
    $('#request_newsletter').bind('keydown', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
        }
    });

    $('#newsletter_submit_button').click(function (e) {
        ValidateNewsletterForm(e);
    });
});

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
        let form = $('#request_newsletter');
        e.preventDefault();
        $.ajax({
            type: form.attr('method'), // method attribulette of form
            url: form.attr('action'),  // action attribute of form
            data: form.serialize()
        });
    }
}
/** */

