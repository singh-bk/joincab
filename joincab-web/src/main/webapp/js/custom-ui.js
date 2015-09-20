$(document).ready(function(){
	 // unblock when ajax activity stops 
	 $(document).ajaxStop($.unblockUI); 
	
	/*for populating dropdown*/
  $('#homeLink').click(function(){
   var vehicle = $.jStorage.deleteKey('vehicleType');
  var hopTime = $.jStorage.deleteKey('time');
  var vehId = $.jStorage.deleteKey('vehicleTypeId');
  var hopTimeId = $.jStorage.deleteKey('timeId');
  });

  var vehicle = $.jStorage.get('vehicleType');
  var hopTime = $.jStorage.get('time');
  var vehId = $.jStorage.get('vehicleTypeId');
  var hopTimeId = $.jStorage.get('timeId');	  
	
  if(vehicle != null){
    $('#dd3 span').text(vehicle);
    $('#dd3 span').attr('id',vehId);
  }
  if(hopTime != null){
    $('#dd2 span').text(hopTime);
    $('#dd2 span').attr('id',hopTimeId);
  }
  
  
	/*for populating dropdown end*/

  
	 $('.refSearch').click(function(){
	        
	        $('.ref-search-tab').addClass('dis-none');
	        $('.ref-search-box').removeClass('dis-none');
	    
	    });
	    
	    $('.subhdr-close-btn').click(function(){
	    
	        $('.ref-search-tab').removeClass('dis-none');
          $('.ref-search-box').addClass('dis-none');
	    
	    });
    
    
    
    
    //home links
    
    $(".home-navigation ul li").on("click", "a", function (event) {
       
        $(".home-navigation ul li a.active").removeClass("active");
        $(this).addClass("active");
    });
    /*user profile links*/
    $('.ulft-container ul li a').click(function(event) {
      $('.ulft-container ul li a.active').removeClass('active');
      $(this).addClass('active');
    });
    
     $( window ).scroll(function() {
		var position = window.location.href.split('#')[1];
        $("#hdTop li").removeClass('active');
        $("#"+position).addClass('active');
	});
    
    
    $( "#edit" ).on('click', function() {
    $( ".editable" ).replaceWith( function() {
        return "<input class=\"editable\" type=\"text\" value=\"" + $( this ).html() + "\" />";
    });
    $("#edit").addClass('dis-none');
    $("#update").removeClass('dis-none');
    });
    $( "#update" ).on('click', function() {
    $( "input[type=text]" ).replaceWith( function() {
        return "<span class=\"editable\">" + $( this ).val() + "</span>";
    });
    $("#edit").removeClass('dis-none');
    $("#update").addClass('dis-none');
});
    

    $('.content-hme').css('height',($(window).height()));
    
    
	//tabs starts here 

	$('.tab-1').hide();
	$('.tab-cab ul li:first').addClass("active").show();
	$('.tab-1:first').show();

	//on click event


	$('.tab-cab ul li').click(function(){

		$('.tab-cab ul li').removeClass("active");
		$(this).addClass("active");
		$('.tab-1').hide();
		var tabwidth = $('.tab-1').width();
		var activeTab = $(this).attr("href");
		$(activeTab).show();
		 $('html, body').stop();
            return false;
	});

function DropDown(el) {
        this.dd = el;
        this.placeholder = this.dd.children('span');
        this.opts = this.dd.find('ul.dropdown > li');
        this.val = '';
        this.index = -1;
        this.initEvents();
      }
      DropDown.prototype = {
        initEvents : function() {
          var obj = this;

          obj.dd.on('click', function(event){
            $(this).toggleClass('active');
            return false;
          });

           $(document).bind('click', function(e) {
                var $clicked = $(e.target);
                if (! $clicked.parents().hasClass("wrapper-dropdown-3"))
                    $(".wrapper-dropdown-3 active").removeClass('active');
                	$(".wrapper-dropdown-3").removeClass('active');
            });

          obj.opts.on('click',function(){
            var opt = $(this);
            obj.val = opt.text();
            obj.index = opt.index();
            obj.placeholder.text(obj.val);
            obj.placeholder.attr("id",opt.attr('id'));
          });
        },
        getValue : function() {
          return this.val;
        },
        getIndex : function() {
          return this.index;
        }
      }
      $(function() {

        var dd ;
        $('.wrapper-dropdown-3').each(function() {
             dd = new DropDown( $('#'+this.id) );
            $('.wrapper-dropdown-3').removeClass('active');
        });
      });





	//slider code
			
	  
		$('#tst-container').cycle({ 
   			 fx:     'slideY', 
    		speed:  'slow', 
    		timeout: '3000', 
    		next:   '#next2', 
    		prev:   '#prev2' 
		});
		
		

});

var showFare = function(distance,vehicleType){
    var obj = jQuery.parseJSON( '{ "0": "14" ,"1":"15","2":"17","3":"20"}' );
    var rate = obj[vehicleType];
    var fare = Math.round(Math.round((rate*distance)/1000)*100/100);
     distance = Math.round(distance/1000);
    if(fare < 595){
      fare = 595
    }
    else{
      fare = ((Math.round(fare/10))*10);
    }
    $('.rate').text(fare);
    $('.distance').text(distance);
  };