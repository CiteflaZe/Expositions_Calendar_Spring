$(function() {
    $("form[name='login']").validate({
        rules: {

            email: {
                required: true,
                email: true
            },
            password: {
                required: true

            }
        },
        messages: {
            email: "Please enter a valid email address",

            password: {
                required: "Please enter password",

            }

        },
        submitHandler: function(form) {
            form.submit();
        }
    });
});



$(function() {

    $("form[name='registration']").validate({
        rules: {
            name: "required",
            surname: "required",
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 4
            },
            passwordConfirmation: {
                required: true,
            }
        },

        messages: {
            name: "Please enter your name",
            surname: "Please enter your surname",
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 4 characters long"
            },
            email: "Please enter a valid email address",
            passwordConfirmation: "Please provide a password confirmation"
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
});
