$(document).ready(function () {
    var lang = $('html').attr("lang");
    var loginMsgLn;
    var loginRegexMsg;
    var passwordMsgLn;
    var emptyField;

    if (lang === "ru_RU") {
        loginMsgLn="Поле логин должно содержать больше 4 и меньше 25 символов";
        loginRegexMsg="Поле логин может содержать только a-z, A-Z, 0-9,_";
        passwordMsgLn="Поле пароль должно содержать больше 4 и меньше 25 символов";
        emptyField="Поле не может быть пустым";
    } else {
        loginMsgLn="The username must be more than 4 and less than 25 characters long";
        loginRegexMsg="The username can only consist of a-z, A-Z, 0-9,_";
        passwordMsgLn="The password must be more than 4 and less than 25 characters long";
        emptyField="The field must not be empty";
    }

    $('#authForm').bootstrapValidator({
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
            }
        }
    });
    $('#login-div>small').detach().prependTo('#login-p');
    $('#pass-div>small').detach().prependTo('#pass-p');
});
