$(document).ready(function () {
    var $url = 'Controller';
    var $titleEn;
    var $titleRu;
    var $releaseYear;
    var $descrEn;
    var $descrRu;
    var $movieId;
    var lang = $('html').attr("lang");
    var errorMsg;
    var saveMsg;

    if (lang === "ru_RU") {
        errorMsg="Ошибка в процессе выполнения операции";
        saveMsg="Сохранить";
    } else {
        errorMsg="Error during procedure";
        saveMsg="Save";
    }

    function hideMsg(){
        setTimeout(function(){$('#message').fadeOut()}, 3000);
    }
    $('#addMovieBtn').click(function () {
        var dataArray=$('#movieForm').serializeArray();
        $titleEn=$.trim(dataArray[0].value);
        $titleRu=$.trim(dataArray[1].value);
        $releaseYear=dataArray[2].value;
        $descrEn=dataArray[3].value;
        $descrRu=dataArray[4].value;

        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'add-movie', titleEn: $titleEn, titleRu: $titleRu,
                releaseYear: $releaseYear, descriptionEn: $descrEn, descriptionRu:$descrRu},
            dataType: 'json',
            success: function (data) {
                $('#addMovieBtn').css("display", "none");
                $("textarea").prop('disabled', true);
                $("input").prop('disabled', true);
                $('.thumbnail').remove();
                $movieId=data;
                $('.menu').css("display", "block");
            },
            error: function () {
                $('#message').html('<div class="alert alert-danger fade in">' +
                    '<button type="button" class="close close-alert" data-dismiss="alert"' +
                    ' aria-hidden="true">×</button>'+errorMsg +
                    '</div>');
                hideMsg();
            }
        });
    });
    $('#3rdStep').click(function () {
        $('.menu').css("display", "none");
        $('.img-form').css('display','block').append("<form method='post'" +
            "action='UploadServlet?command=upload-movie-poster&movieId=" + $movieId + "'" +
            "enctype='multipart/form-data' style='margin-top:30px;margin-left:40%'>" +
            "<input name='data' type='file'>" +
            "<input class='btn btn-success' type='submit' style='margin-top:40px;' value='"+saveMsg+"'>" +
            "</form>");
    });
    
    $('#addCountry').click(function () {
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'view-all-countries'},
            dataType: 'json',
            success: function (data) {
                var options;
                for(var i=0; i<data.length;i++){
                    options+="<option>"+data[i].name+"</option>";
                }
                $('#dropdown-add-country').children().children().children().last().html(options).selectpicker('refresh');
                $('.bootstrap-select').css("width", "500px");
            },
            error: function () {
                $('#message').html('<div class="alert alert-danger fade in">' +
                    '<button type="button" class="close close-alert" data-dismiss="alert"' +
                    ' aria-hidden="true">×</button>'+errorMsg +
                    '</div>');
                hideMsg();
            }
        });
    });
    $('#addGenre').click(function () {
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'view-all-genres'},
            dataType: 'json',
            success: function (data) {
                var options;
                for(var i=0; i<data.length;i++){
                    options+="<option>"+data[i].name+"</option>";
                }
                $('#dropdown-add-genre').children().children().children().last().html(options).selectpicker('refresh');
                $('.bootstrap-select').css("width", "500px");
            },
            error: function () {
                $('#message').html('<div class="alert alert-danger fade in">' +
                    '<button type="button" class="close close-alert" data-dismiss="alert"' +
                    ' aria-hidden="true">×</button>'+errorMsg +
                    '</div>');
                hideMsg();
            }
        });
    });
    $('#addActor').click(function () {
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command: 'view-all-actors-json'},
            dataType: 'json',
            success: function (data) {
                var options;
                for(var i=0; i<data.length;i++){
                    options+="<option>"+data[i].firstName+" "+data[i].lastName+"</option>";
                }
                $('#dropdown-add-actor').children().children().children().last().html(options).selectpicker('refresh');
                $('.bootstrap-select').css("width", "500px");
            },
            error: function () {
                $('#message').html('<div class="alert alert-danger fade in">' +
                    '<button type="button" class="close close-alert" data-dismiss="alert"' +
                    ' aria-hidden="true">×</button>'+errorMsg +
                    '</div>');
                hideMsg();
            }
        });
    });
    $('#addCountryBtn').click(function (){
        var countryNames = $('.selectpicker option:selected');
        for (var i=0; i<countryNames.length;i++){
            var name=countryNames[i].text;
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command:'add-country-for-movie',movieId: $movieId, countryName: name},
                success: function (result) {
                    if (result==="true"){
                    } 
                    if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>'+errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        }
        var text="";
        for (var j=0; j<countryNames.length;j++){
                text+=countryNames[j].text+" ";
        }
        $('#country-li').replaceWith('<p class="panel panel-text">'+text+'</p>');
        
    });
    $('#addGenreBtn').click(function (){
        var genreNames = $('.selectpicker option:selected');
        for (var i=0; i<genreNames.length;i++){
            var name=genreNames[i].text;
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command:'add-genre-for-movie',movieId: $movieId, genreName: name},
                success: function (result) {
                    if (result==="true"){
                    }
                    if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>'+errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        }
        var text="";
        for (var j=0; j<genreNames.length;j++){
                text+=genreNames[j].text+" ";
        }
        $('#genre-li').replaceWith('<p class="panel panel-text">'+text+'</p>');
    });
    $('#addActorBtn').click(function (){
        var actorNames = $('.selectpicker option:selected');
        for (var i=0; i<actorNames.length;i++){
            var name=actorNames[i].text;
            var firstName=$.trim(name.split(" ")[0]);
            var lastName=$.trim(name.split(" ")[1]);
            $.ajax({
                type: 'POST',
                url: $url,
                data: {command:'add-actor-for-movie',movieId: $movieId, firstName: firstName, lastName: lastName},
                success: function (result) {
                    if (result==="true"){
                    }
                    if (result === "false") {
                        $('#message').html('<div class="alert alert-danger fade in">' +
                            '<button type="button" class="close close-alert" data-dismiss="alert"' +
                            ' aria-hidden="true">×</button>'+errorMsg +
                            '</div>');
                        hideMsg();
                    }
                }
            });
        }
        var text="";
        for (var j=0; j<actorNames.length;j++){
            text+=actorNames[j].text+" ";
        }
        $('#actor-li').replaceWith('<p class="panel panel-text">'+text+'</p>');
    });

});
