$(document).ready(function () {
    $(document).on("scroll", onScroll);
    
    //smoothscroll
    
     var jump=function(e)
    {
       if (e){
           e.preventDefault();
           var target = $(this).attr("href");
       }else{
           var target = location.hash;
       }
       if (target!='#') 
       $('html,body').animate(
       {
           scrollTop: $(target).offset().top
       },1000,function()
       {
           location.hash = target;
       });

    }

    
    
        $('a[href^=#]').bind("click", jump);

        if (location.hash){
            setTimeout(function(){
                $('html, body').scrollTop(0).show()
                jump()
            }, 0);
        }else{
          $('html, body').show()
        }

});

function onScroll(event){
    var scrollPos = $(document).scrollTop();
    $('.home-navigation ul li a').each(function () {
        var currLink = $(this);
        var refElement = $(currLink.attr("href"));
        if (refElement.position().top <= scrollPos && refElement.position().top + refElement.height() > scrollPos) {
            $('.home-navigation ul li a').removeClass("active");
            currLink.addClass("active");
        }
        else{
            currLink.removeClass("active");
        }
    });
};