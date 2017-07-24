$(document).ready(function () {
    var idUser;
    var $url = 'Controller';
    var $value;
    var banString;
    var index;
    var lang = $('html').attr("lang");
    var errorMsg;
    var deleteMsg;

    if (lang === "ru_RU") {
        errorMsg = "Ошибка в процессе выполнения операции";
        deleteMsg = "Удалить?";
    }
    if (lang === "en_EN") {
        errorMsg = "Error during procedure";
        deleteMsg = "Delete?";
    }
    function hideMsg() {
        setTimeout(function () {
            $('#message').empty();
        }, 3000);
    }

    $('tr').click(function () {
        banString = $(this).children()[8];
        $value = $.trim(banString.childNodes[0].textContent);
        idUser = $.trim($(this).children()[0].childNodes[0].textContent);
        index = $(this).index();
        var firstName = $.trim($(this).children()[1].childNodes[0].textContent);
        var lastName = $.trim($(this).children()[2].childNodes[0].textContent);
        if ($value === "Yes") {
            $('#for-ban').html('Unban').css("visibility", "visible");
        }
        if ($value === "Да") {
            $('#for-ban').html('Отменить бан').css("visibility", "visible");
        }
        if ($value === "Нет") {
            $('#for-ban').html('Поставить бан').css("visibility", "visible");
        }
        if ($value === "No") {
            $('#for-ban').html('Ban').css("visibility", "visible");
        }
        $('#edit').attr("href", "Controller?command=view-user&userId=" + idUser).css("visibility", "visible");
        $('#delete').css("visibility", "visible");
        $('#select-user').text(firstName + " " + lastName);
    });

    $('#for-ban').click(function () {
        var $current = $(this),
            status = $.trim($current[0].childNodes[0].textContent);
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'change-ban-status', userId: idUser, status: status},
            success: function (result) {
                if (result === "true") {
                    if (status === "Unban") {
                        $current.html('Ban');
                        $(banString).html('No');
                    }
                    if (status === "Отменить бан") {
                        $current.html('Поставить бан');
                        $(banString).html('Нет');
                    }
                    if (status === "Поставить бан") {
                        $current.html('Отменить бан');
                        $(banString).html('Да');
                    }
                    if (status === "Ban") {
                        $current.html('Unban');
                        $(banString).html('Yes');
                    }
                } if (result === "false") {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>' + errorMsg +
                        '</div>');
                    hideMsg();
                }
            }
        });
    });
    $('#delete').click(function () {
        if (confirm(deleteMsg)) {
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'delete-user', userId: idUser},
                success: function (result) {
                    if (result === "true") {
                        $('#delete').css("visibility", "hidden");
                        $('#edit').css("visibility", "hidden");
                        $('#for-ban').css("visibility", "hidden");
                        $("tr").eq(index).remove();
                    } if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        }
    });

});
