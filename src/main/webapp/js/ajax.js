$(document).ready(function () {
    var $url= 'Controller';

    $('.ban').click(function (){
        var $current = $(this);
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command:'change-ban-status',userId: $.trim($current.parent().parent().children().first().text())},
            success: function (data) {
                $current.text(data);
            }
        });
    });

    $('#btnHello').click(function () {
        var fullName=$('#fullName').val();
        $.ajax({
            type: 'POST',
            url: $url,
            data: {command:'change-ban-status',fullname: fullName},
            success: function (result) {
                $('#result1').html(result)
            }
        });
    });
    $.fn.editable.defaults.mode = 'inline';
    $('#edit').editable({
        ajaxOptions: {
            dataType: 'json'
        },
        type: 'text',
        url: $url+"?change-ban-status",
        pk: 1,
        title: 'Enter username',
        name:"edit"
    });
    $('.editable-submit').click(function () {
        $('.editable-open').editable('submit', {
            url: $url,
            params: function (params) {
                var data = {};
                data['value'] = params.value;
                data['pk'] = params.pk;
                data['originalValue'] = $(this).text();
                data['action'] = 'view-all-movies';
                return data;
            },
            success: function(params, config) {
                console.log(params);
            }
        });
    });
    $('#for-edit').click(function () {
        var text=$('#forEdit').text();
        $('#forEdit').replaceWith( "<form action=\"Controller?command=view-all-movies\" method=\"post\">" +
            " <input type=\"text\" value="+text+"><input type=\"submit\" class=\"btn btn-primary\" value=\"save\"></form> " );
    });
    var text1 = 'Two';
    $("select option").filter(function() {
        //may want to use $.trim in here
        return $(this).text() == text1;
    }).prop('selected', true);
});
