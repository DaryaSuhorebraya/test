$(document).ready(function () {
    var lang = $('html').attr("lang");
    var loginMsgLn;
    var loginRegexMsg;
    var lastNameMsgLn;
    var nameRegexMsg;
    var firstNameLn;
    var emailMsg;
    var passwordMsgLn;
    var notEqualsPasswordMsg;
    var emptyField;

    if (lang === "ru_RU") {
        loginMsgLn="Поле логин должно содержать больше 4 и меньше 25 символов";
        loginRegexMsg="Поле логин может содержать только a-z, A-Z, 0-9,_";
        lastNameMsgLn="Поле фамилия должно содержать меньше 65 символов";
        nameRegexMsg="Поле может содержать а-я, А-Я, ё, -, a-z,A-Z";
        firstNameLn="Поле имя должно содержать меньше 45 символов";
        emailMsg="Поле не является email-адресом";
        passwordMsgLn="Поле пароль должно содержать больше 4 и меньше 25 символов";
        notEqualsPasswordMsg="Поля пароль и подтверждение пароля не совпадают";
        emptyField="Поле не может быть пустым";
    } else {
        loginMsgLn="The username must be more than 4 and less than 25 characters long";
        loginRegexMsg="The username can only consist of a-z, A-Z, 0-9,_";
        lastNameMsgLn="The last name must be less than 65 characters long";
        nameRegexMsg="The field can only consist of а-я, А-Я, ё, -, a-z,A-Z";
        firstNameLn="The first name must be less than 45 characters long";
        emailMsg="The input is not a valid email address";
        passwordMsgLn="The password must be more than 4 and less than 25 characters long";
        notEqualsPasswordMsg="The password and its confirm are not the same";
        emptyField="The field must not be empty";
    }


    $('#registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            login: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    stringLength: {
                        min: 4,
                        max: 25,
                        message: loginMsgLn
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: loginRegexMsg
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    stringLength: {
                        max: 65,
                        message: lastNameMsgLn
                    },
                    regexp: {
                        regexp: /^[a-zA-Zа-яА-Яё-]+$/,
                        message: nameRegexMsg
                    }
                }
            },
            firstName: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    stringLength: {
                        max: 45,
                        message: firstNameLn
                    },
                    regexp: {
                        regexp: /^[a-zA-Zа-яА-Яё-]+$/,
                        message: nameRegexMsg
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9.,_%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$/,
                        message: emailMsg
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    stringLength: {
                        min: 4,
                        max: 25,
                        message: passwordMsgLn
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: emptyField
                    },
                    identical: {
                        field: 'password',
                        message: notEqualsPasswordMsg
                    }
                }
            }
        }
    });

    $('small[data-bv-validator="emailAddress"]').remove();

    $( "input:reset" ).click(function () {
        $('.help-block').css('display','none');
        $('.form-group').removeClass("has-error has-success");
        $('.col-xs-9 i').css('display','none');
    });
});
