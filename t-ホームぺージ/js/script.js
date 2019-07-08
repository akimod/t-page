/*$(function() {
    $('#sub-panel').fadeIn();
});*/

/*$(function(){
    // 「#hide-text」要素に対するclickイベントを作成してください
    $('.sub-panel').hide();
    $('#navbarResponsive').mouseenter(function(){
      $('.sub-panel').slideDown(200);
    });
    $('#navbarResponsive').mouseleave(function(){
        $('.sub-panel').slideUp(200);
      });
  });
*/

$(function(){
  $('.sub-panel').hide();
  $( "#navbarResponsive").click(function() {
    if ( $( ".sub-panel:first" ).is( ":hidden" ) ) {
      $( ".sub-panel" ).slideDown();
    } else {
      $( ".sub-panel" ).slideUp();
    }
  });
});

$(function(){
  $('#home').fadeIn(2000);
});


$(function(){
  $('#back-to-top').hide();
  $(window).scroll(function(){
    $('#pos').text($(this).scrollTop());
    if($(this).scrollTop() > 200){
      $('#back-to-top').fadeIn();
    }else{
      $('#back-to-top').fadeOut();
    }
  });
  $('#back-to-top a').click(function() {
    $('html, body').animate({
        scrollTop:0
    }, 500);
    return false;
 });
});