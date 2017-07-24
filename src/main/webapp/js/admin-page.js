$(document).ready(function () {
    var $url = 'Controller';
    var genreId;
    var name;
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
    
    $.ajax({
        type: 'POST',
        url: $url,
        data: {command: 'get-user-statistics'},
        dataType: 'json',
        success: function (data) {
            new Morris.Line({
                element: 'user-chart',
                data: data,
                xkey: 'label',
                ykeys: ['value'],
                labels: ['Count']
            });

        },
        error: function (textStatus) {
            alert('err');
        }
    });
    $.ajax({
        type: 'POST',
        url: $url,
        data: {command: 'get-review-statistics'},
        dataType: 'json',
        success: function (data) {
            new Morris.Bar({
                element: 'review-chart',
                data: data,
                xkey: 'label',
                ykeys: ['value'],
                labels: ['Count']
            });

        },
        error: function (textStatus) {
            alert('err');
        }
    });
    $.ajax({
        type: 'POST',
        url: $url,
        data: {command: 'get-genre-statistics'},
        dataType: 'json',
        success: function (data) {
            new Morris.Donut({
                element: 'genre-chart',
                data: data
            });
        },
        error: function (textStatus) {
            alert('err');
        }
    });
    //$(".col-sm-6").on("click", ".list-group .list-group-item .delete-genre", function () {
    $(".list-group-item").on("click", ".delete-genre", function () {
        if (confirm(deleteMsg)) {
            var current = $(this);
            var idGenre = current.siblings('.id-genre').text();
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'delete-genre', genreId: idGenre},
                success: function (result) {
                    if (result === "true") {
                        current.parent().remove();
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
    })
        .on("click", ".edit-genre", function () {
        var current = $(this);
        name = $.trim(current.parent()[0].childNodes[0].nodeValue);
        genreId = current.siblings('.id-genre').text();
        current.parent()[0].innerHTML = "<form id='form-genre-edit'>" +
            "<input style='width: 100px' type='text' name='name' value=" + name + ">" +
            "</form>" +
            "<span class='btn-label btn-primary save-edit-genre' >" +
            "<i class='glyphicon glyphicon-ok'></i>";
    })
        .on("click", ".save-edit-genre", function () {
        var current = $(this);
        var form = $('#form-genre-edit');
        name = $.trim(form.find('input[name="name"]').val());
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'edit-genre', name: name, genreId: genreId},
            success: function (result) {
                if (result === "true") {
                    current.css('display', 'none');
                    form.css('display', 'none');
                    current.parent()[0].innerHTML= name +
                        "<p class='id-genre'>" + genreId + "</p>" +
                        "<span class='btn-label btn-danger delete-genre'>" +
                        "<i class='glyphicon glyphicon-remove'></i>" +
                        "</span>"+
                        "<span class='btn-label btn-danger edit-genre'>" +
                        "<i class='glyphicon glyphicon-edit'></i>" +
                        "</span>" ;
                } if (result === "false") {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>'+errorMsg +
                        '</div>');
                    hideMsg();
                }
            }
        });
    });

    $(".list-group-item").click(function (event) {
        event.stopPropagation();
    });
    $("#add-content").click(function (event) {
        event.stopPropagation();
    });

    $('#add-genre').click(function () {
        var current = $(this);
        current.css('display', 'none');
        $('#add-genre-form').css('display', 'inline-block');
        $('#save-add-genre').css('display', 'block');
    });

    $('#save-add-genre').click(function () {
        var current = $(this);
        var form = $('#add-genre-form');
        var nameRu = $.trim(form.find('input[name="nameRu"]').val());
        var nameEn = $.trim(form.find('input[name="nameEn"]').val());
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'add-genre', nameRu: nameRu, nameEn: nameEn},
            success: function (result) {
                current.css('display', 'none');
                form.css('display', 'none');
                $('#add-genre').css('display', 'block');
                $('.list-inline').append(
                    "<li class='list-group-item'>" + nameEn +
                    "<p class='id-genre'>" + result + "</p>" +
                    "<span class='btn-label btn-danger delete-genre'>" +
                    "<i class='glyphicon glyphicon-remove'></i>" +
                    "</span>" +
                    "<span class='btn-label btn-danger edit-genre'>" +
                    "<i class='glyphicon glyphicon-edit'></i>" +
                    "</span>" +
                    "</li>");
            }
        });
    });
    $('html').click(function () {
        $('#add-genre-form').css('display', 'none');
        $('#add-genre').css('display', 'block');
        $('#save-add-genre').css('display', 'none');

        $('#form-genre-edit').css('display', 'none');
        $('.save-edit-genre').css('display', 'none').parent()[0].innerHTML= name +
            "<p class='id-genre'>" + genreId + "</p>" +
            "<span class='btn-label btn-danger delete-genre'>" +
            "<i class='glyphicon glyphicon-remove'></i>" +
            "</span>"+
            "<span class='btn-label btn-danger edit-genre'>" +
            "<i class='glyphicon glyphicon-edit'></i>" +
            "</span>" ;

    });

});

