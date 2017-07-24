$(document).ready(function () {
    var $url = 'Controller';
    var lang = $('html').attr("lang");
    var errorMsg;
    var deleteMsg;

    if (lang === "ru_RU") {
        errorMsg="Ошибка в процессе выполнения операции";
        deleteMsg="Удалить?";
    } if (lang === "en_EN") {
        deleteMsg="Delete?";
        errorMsg="Error during procedure";
    }
    $('.input-stars').rating("refresh", {displayOnly: true, stars: 10, disabled: false});

    function hideMsg(){
        setTimeout(function(){
            $('#message').empty();
        }, 3000);
    }

    $('.delete-review').click(function () {
        if (confirm(deleteMsg)) {
            var current = $(this);
            var idMovie = current.siblings('.id-movie').text();
            var idUser= current.siblings('.id-user').text();
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'delete-review', userId:idUser, movieId:idMovie},
                success: function (result) {
                    if (result === "true") {
                        current.parent().parent().next().remove();
                        current.parent().parent().remove();
                    } if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>'+errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        }
    });
});
