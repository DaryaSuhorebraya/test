$(document).ready(function () {
    var $url= 'Controller';
    var lang = $('html').attr("lang");
    var errorMsg;
    var deleteMsg;

    if (lang === "ru_RU") {
        errorMsg="Ошибка в процессе выполнения операции";
        deleteMsg="Удалить?";
    } if (lang === "en_EN") {
        errorMsg="Error during procedure";
        deleteMsg="Delete?";
    }

    function hideMsg(){
        setTimeout(function(){
            $('#message').empty();
        }, 3000);
    }

    $('.delete-movie').click(function (){
        if(confirm(deleteMsg)){
            var idMovie=$(this).siblings('#id').text();
            var current=$(this);
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command:'delete-movie',movieId: idMovie},
                success: function (result) {
                    if (result==="true"){
                        current.parent().parent().remove()
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
   
    $('#list').click(function(event){
        event.preventDefault();
        $('#products .item').addClass('list-group-item');
        $('.btn-info').addClass('pull-right');
    });
    $('#grid').click(function(event){
        event.preventDefault();
        $('#products .item').removeClass('list-group-item');
        $('#products .item').addClass('grid-group-item');
        $('.btn-info').removeClass('pull-right');
    });

    $(".thumbnail").dotdotdot({
        ellipsis	: '... '
    });

});