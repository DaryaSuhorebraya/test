$(document).ready(function () {
    var $url = 'Controller';
    var movieId = $('#movie-id').text();
    var userId = $('#user-id').text();
    var ratingCount = 0;
    var mark;
    var highRatingElement;
    var lang = $('html').attr("lang");
    var yetReviewedMsg;
    var needAuthMsg;
    var rateFirstMsg;
    var errorMsg;
    var bannedUser;
    var deleteMsg;
    var yourRatingMsg;
    var rate;

    if (lang === "ru_RU") {
        yetReviewedMsg = "Вы уже оставили отзыв на этот фильм";
        needAuthMsg = "Для этого действия необходимо авторизоваться";
        rateFirstMsg = "Необходимо сначало оценить фильм";
        errorMsg = "Ошибка в процессе выполнения операции";
        bannedUser = "У вас нет прав на совершение данной операции";
        deleteMsg = "Удалить?";
        yourRatingMsg="Твой рейтинг:";
        rate="Оценить";
    }
    if (lang === "en_EN") {
        yetReviewedMsg = "You have reviewed this movie yet";
        needAuthMsg = "You have to log in for this action";
        rateFirstMsg = "You should rate the movie first";
        errorMsg = "Error during procedure";
        bannedUser = "You have no rights to do this procedure";
        deleteMsg = "Delete?";
        yourRatingMsg="Your rating:";
        rate ="Rate";
    }
    $('.user-stars').rating("refresh", {displayOnly: true, stars: 10, disabled: false});

    var collection = $('.review');
    (collection).each(function () {
        var current = $(this);
        if (document.getElementById('user-login').childNodes[0].data ===
            $(current).find('.user-login').text().trim()) {
            current.css('background-color', 'rgb(221, 229, 225)')
                .css('box-shadow', '0 0 30px rgba(0,0,4,0.2)');
            current.children().prepend("<span class='btn-label delete-review'>" +
                "<i class='glyphicon glyphicon-remove'></i>" +
                "</span>");
        }
    });
    function hideMsg() {
        setTimeout(function () {
            $('#message').empty();
        }, 3000);
    }

    $(".add-review-block").on("click", ".leave-review", function () {
        var current = $(this);
        if (document.getElementById('user-login') !== null) {

            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'check-review-opportunity', movieId: movieId, userId: userId},
                success: function (result) {
                    if (result === "true") {
                        $('#message').html('<div class="alert alert-info fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + yetReviewedMsg +
                            '</div>');
                        hideMsg();
                    }
                    if (result == 'isBanned') {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + bannedUser +
                            '</div>');
                        hideMsg();
                    }
                    if (result === "false") {
                        $('.text-right').css('display', 'none');
                        $('.add-review').css('display', 'block');

                        $.ajax({
                            type: 'POST',
                            url: $url,
                            data: {command: 'check-rate-opportunity', movieId: movieId, userId: userId},
                            success: function (result) {
                                if (result === "true") {
                                    $.ajax({
                                        type: 'POST',
                                        url: $url,
                                        data: {
                                            command: 'view-rating-on-movie-by-user-id',
                                            movieId: movieId,
                                            userId: userId
                                        },
                                        success: function (data) {
                                            mark = data;
                                            $('.add-star-rating').replaceWith("<div class='review-block-rate'>" +
                                                "<input class='inserted-rating rating rating-loading'  data-size='xs' value='" + data + "' data-min='0'" +
                                                " data-max='10' data-step='1'>" +
                                                "</div>");
                                            $('.inserted-rating').rating("refresh", {
                                                displayOnly: true,
                                                stars: 10,
                                                disabled: false
                                            });
                                            ratingCount = 1;
                                        }
                                    });

                                }
                                if (result == 'isBanned') {
                                    $('#message').html('<div class="alert alert-danger fade in">' +
                                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                                        ' aria-hidden="true">×</button>' + bannedUser +
                                        '</div>');
                                    hideMsg();
                                }
                                if (result === "false") {
                                    $('.add-star-rating').css('display', 'block');
                                    $('.add-rating').rating("refresh", {stars: 10});
                                    $('.caption').remove();
                                    $('.clear-rating').remove();
                                    $('.rating-container').css('display', 'inline-block');
                                }
                            }
                        });
                    }
                }
            });
        } else {
            $('#message').html('<div class="alert alert-info fade in">' +
                '<button type="button" class="close close-alert" data-dismiss="alert"' +
                ' aria-hidden="true">×</button>' + needAuthMsg +
                '</div>');
            hideMsg();
        }
    });

    $(".add-review-block").click(function (event) {
        event.stopPropagation();
    });
    $('html').click(function () {
        $('.text-right').css('display', 'block');
        $('.add-review').css('display', 'none');
    });

    $('.btn-rate').click(function () {
        var current = $(this);
        mark = current.parent().parent().parent().find(".add-rating").val();
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'rate-movie', movieId: movieId, mark: mark},
            success: function (result) {
                if (result === "true") {
                    ratingCount = 1;
                    current.css('display', 'none');
                    $('.add-rating').rating("refresh", {displayOnly: true, stars: 10, disabled: false, value: mark})
                        .css('font-size', ' 1.2em');

                }
                if (result == 'isBanned') {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>' + bannedUser +
                        '</div>');
                    hideMsg();
                }
                if (result === "false") {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>' + errorMsg +
                        '</div>');
                    hideMsg();
                }
            }
        });
    });

    $('#save-review').click(function () {
        if (ratingCount === 1) {
            var dataArray = $('#form-add-review').serializeArray();
            var title = dataArray[0].value;
            var review = dataArray[1].value;
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'review-movie', movieId: movieId, title: title, review: review},
                success: function (result) {
                    if (result === "true") {
                        ratingCount = 0;
                        $('.add-review').css('display', 'none');
                        $('.text-right').css('display', 'block');

                        var login = document.getElementById('user-login').textContent;
                        $("<hr><div class='row review'>" +
                            "<div class='col-md-12'>" +
                            "<span class='btn-label delete-review'>" +
                            "<i class='glyphicon glyphicon-remove'></i>" +
                            "</span>" +
                            "<div class='review-block-rate'>" +
                            "<input class='user-stars rating rating-loading'  data-size='xs'" +
                            " value='" + mark + "' data-min='0' data-max='10' data-step='1'>" +
                            "</div>" +
                            "<span class='user-login'>" + login + "</span>" +
                            "<h4 class='review-title'>" + title + "</h4>" +
                            "<span class='pull-right'>" + new Date().toJSON().split('T')[0] + "</span>" +
                            "<p class='review-text'>" + review + "</p>" +
                            "</div>" +
                            "</div>").insertAfter(".add-review-block")
                            .css('background-color', 'rgb(221, 229, 225)')
                            .css('box-shadow', '0 0 30px rgba(0,0,4,0.2)');
                        $('.user-stars').rating("refresh", {displayOnly: true, stars: 10, disabled: false});

                    }
                    if (result == 'isBanned') {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + bannedUser +
                            '</div>');
                        hideMsg();
                    }
                    if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        } else {
            $('#message').html('<div class="alert alert-info fade in">' +
                '<button type="button" class="close close-alert" data-dismiss="alert"' +
                ' aria-hidden="true">×</button>' + rateFirstMsg +
                '</div>');
            hideMsg();
        }
    });

    $(".well").on("click", ".review .delete-review", function () {
        if (confirm(deleteMsg)) {
            var current = $(this);

            var reviewBlock = current.parent().parent();
            var userLogin = $(reviewBlock).find('.user-login').text().trim();
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'get-user-by-login', login: userLogin},
                success: function (result) {
                    if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + errorMsg +
                            '</div>');
                        hideMsg();
                    } else {
                        userId = result;
                        $.ajax({
                            type: 'POST',
                            url: $url,
                            data: {command: 'delete-review', movieId: movieId, userId: userId},
                            success: function (result) {
                                if (result === "true") {
                                    reviewBlock.next().remove();
                                    reviewBlock.remove();
                                    current.remove();
                                }
                                if (result == 'isBanned') {
                                    $('#message').html('<div class="alert alert-danger fade in">' +
                                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                                        ' aria-hidden="true">×</button>' + bannedUser +
                                        '</div>');
                                    hideMsg();
                                }
                                if (result === "false") {
                                    $('#message').html('<div class="alert alert-danger fade in">' +
                                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                                        ' aria-hidden="true">×</button>' + errorMsg +
                                        '</div>');
                                    hideMsg();
                                }
                            }
                        });
                    }
                }
            });


        }
    });
    $(".caption-full").on("click", ".ratings .delete-rating", function () {
        if (confirm(deleteMsg)) {
            var current = $(this);
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command: 'delete-rating', movieId: movieId, userId: userId},
                success: function (result) {
                    if (result === "true") {
                        $('.h4-user-rating:first').replaceWith("<a class='btn btn-success rate'>" +
                            ""+rate+" </a>");
                        $('.h4-user-rating').remove();
                        current.remove();

                    }
                    if (result == 'isBanned') {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>' + bannedUser +
                            '</div>');
                        hideMsg();
                    }
                    if (result === "false") {
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
    $(".caption-full").on("click", ".ratings .rate", function () {
        if (document.getElementById('user-login') !== null) {
            highRatingElement=$('.ratings');
            $('.ratings').replaceWith("<div class='high-star-rating'>" +
                "<input data-size='xs' data-min='0' data-max='10'" +
                "data-step='1' class='rating-loading high-input-rating'>" +
                "<button class='btn btn-success' id='save-rate'>"+rate+"</button>"
                + "</div>");
            $('.caption-full .high-star-rating').css('display', 'block').css('margin-bottom', '3px');
            $('.caption-full .high-input-rating').rating("refresh", {stars: 10});
            $('.caption-full #save-rate').css('margin-left', '20px');
            $('.caption').remove();
            $('.clear-rating').remove();
            $('.rating-container').css('display', 'inline-block');
        } else {
            $('#message').html('<div class="alert alert-info fade in">' +
                '<button type="button" class="close close-alert" data-dismiss="alert"' +
                ' aria-hidden="true">×</button>' + needAuthMsg +
                '</div>');
            hideMsg();
        }
    });
    $(".caption-full").on("click", ".high-star-rating #save-rate", function () {
        var current = $(this);
        mark = current.parent().parent().parent().find(".high-input-rating").val();
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'rate-movie', movieId: movieId, mark: mark},
            success: function (result) {
                if (result === "true") {
                    current.css('display', 'none');
                    $('.high-star-rating').replaceWith(highRatingElement);
                    $('.rate').replaceWith("<h4 class='h4-user-rating'>("+
                        ""+yourRatingMsg+" "+mark+"</h4>"+
                        "<span class='btn-label delete-rating'>"+
                        "<i class='glyphicon glyphicon-remove'></i>"+
                        "</span>"+
                        "<h4 class='h4-user-rating'>)</h4>");
                }
                if (result == 'isBanned') {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>' + bannedUser +
                        '</div>');
                    hideMsg();
                }
                if (result === "false") {
                    $('#message').html('<div class="alert alert-danger fade in">' +
                        '<button type="button" class="close close-alert" data-dismiss="alert"' +
                        ' aria-hidden="true">×</button>' + errorMsg +
                        '</div>');
                    hideMsg();
                }
            }
        });
    });
});
