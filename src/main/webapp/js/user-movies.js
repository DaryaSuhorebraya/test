$(document).ready(function () {
    $('#search').click(function () {
        $(".item").css('display','block');

        var text=$('#search-text').val();
        var searchResult=$(".title:contains('" + text+ "')").parent().parent().parent();
        //$('.item:not(searchResult)').css('display','none');
        $(".item").not(searchResult).css('display','none');
    });
});

