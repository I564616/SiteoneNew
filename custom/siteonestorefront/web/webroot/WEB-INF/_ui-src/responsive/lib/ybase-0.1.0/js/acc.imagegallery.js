/* Youtube analytics*/
var player;
var players=[];
var zeroWatched;
var oneFourthWatched;
var halfWatched;
var threeFourthWatched;
var listWatched;
/* Youtube analytics variables*/

ACC.imagegallery = {

	_autoload: [
		"bindImageGallery",
		"bindGalleryPopup",
		"openImageZoom",
			],
	resetWatchPercentage: function () {
		/*reset watch percentage when the video ends*/
		zeroWatched = false;
		oneFourthWatched = false;
		halfWatched = false;
		threeFourthWatched = false;
		listWatched = false;

		/*--reset watch percentage when the video ends--*/
	},
	openVideoIframe: function (ytUrl) {

		$('a.popup-big-image').trigger('zoom.destroy');
		$(".popup-enlarged-image").addClass("hidden");
		$(".popup-hover--message").addClass("hidden");
		$(".pdp-video-responsive").removeClass("hidden");
		$('a.popup-big-image').addClass("hidden");
		$("#cboxLoadedContent .pdp-video-responsive iframe").remove()
		$('<iframe id="popupIframe" class="popup-video-iframe" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" frameborder="0"  src=""></iframe>').attr("src", ytUrl).appendTo("#cboxLoadedContent .pdp-video-responsive");
		var videoIframeId = $("#cboxLoadedContent .pdp-video-responsive iframe").attr("id");
		ACC.imagegallery.resetWatchPercentage();
		ACC.imagegallery.onYouTubeIframeAPIReady(videoIframeId);

	},
	openImageZoom: function (zoomImage, largeImage) {
		$("#cboxLoadedContent .pdp-video-responsive iframe").attr("src", "");
		$('a.popup-big-image').css("display", "inline-block")
		$(".popup-enlarged-image").attr("src", largeImage).removeClass("hidden");
		$(".popup-hover--message").removeClass("hidden");
		$('a.popup-big-image').removeClass("hidden");
		$(".js-click-here-to-zoom").data("assistive-large-img-url", largeImage);
		$(".js-click-here-to-zoom").data("assistive-zoom-img-url", zoomImage);
		if($('body').hasClass("un-assistive")){
			$(".js-click-here-to-zoom").parent().removeClass('hidden');
		}
    else{
      $(".js-click-here-to-zoom").parent().addClass('hidden');
    }
		$.colorbox.resize();
		$('a.popup-big-image').zoom({
			url: zoomImage, 
			on: 'mouseover'

			});
	},
	showTitleInPopup:function(element){
		$(element).slice(1).remove();
		$(element).removeClass("hidden");
	},
	galleryPopup:function(largeImage, zoomImage, videoUrl, variantPopup, _curEle){
    var curContainer = $(".gallery-popup-container").html();
    if(variantPopup) {
        $('.gallery-popup-container-variant').find('.photo-thumbnails')
            .html($(_curEle).find('.photoThumbImage').html());
        curContainer = $(".gallery-popup-container-variant").html();
    } else {
        curContainer = $(".gallery-popup-container").html();
    }

    ACC.colorbox.open("", {
        html: curContainer,
        className: 'cb-image-popup',
        close: '<span class="close-menu"></span>',
        onOpen: function(){
            $('.zoom-image-overlay').remove();
        },

        onComplete: function(){
            $(document).ready(function () {
                $('#cboxTitle').hide();
                $('#cboxLoadedContent').attr("style", "margin-top:0px !important");
                ACC.imagegallery.showTitleInPopup("#cboxLoadedContent .popup-gallery-thumbnail");
                ACC.imagegallery.showTitleInPopup("#cboxLoadedContent .popup-video-thumbnail");

                
                if (videoUrl === "" || videoUrl === undefined || videoUrl === null) {
                    ACC.imagegallery.openImageZoom(zoomImage, largeImage);
                } else {
                    ACC.imagegallery.openVideoIframe(videoUrl + ACC.config.youTubeQueryParams);
                }

              
                setTimeout(function(){
                    var $thumbs = $("#cboxLoadedContent .popup-product-thumb");
                    $thumbs.removeClass("selected");

                    var $target = $();

                
                    if (largeImage) {
                        $target = $("#cboxLoadedContent .popup-product-thumb[data-large-image='" + largeImage + "']");
                    }

                    
                    if (!$target.length && zoomImage) {
                        $target = $("#cboxLoadedContent .popup-product-thumb[data-zoom-image='" + zoomImage + "']");
                    }

                    
                    if (!$target.length && videoUrl) {
                        $target = $("#cboxLoadedContent .video-thumb[data-youtube-url='" + videoUrl + "']");
                    }

                   
                    if (!$target.length) {
                        $target = $("#cboxLoadedContent .popup-thumbnails_0 .popup-product-thumb");
                    }

                    var idx = Number($target.data("index")) || 0;
                    $("#inputdataprev").val(idx);
                    $target.addClass("selected");

                    
                    if (!$target.hasClass("video-thumb")) {
                        var large = $target.data("large-image");
                        var zoom  = $target.data("zoom-image");
                        ACC.imagegallery.openImageZoom(zoom, large);
                    } else {
                        var yt = $target.data("youtube-url");
                        ACC.imagegallery.openVideoIframe(yt + ACC.config.youTubeQueryParams);
                    }

                    $.colorbox.resize();
                }, 0);

              
                $(document).on("click", ".js-click-here-to-zoom", function (e) {
                    e.preventDefault();
                    var assistiveZoomImg = $(".js-click-here-to-zoom").data("assistive-zoom-img-url");
                    var assistiveLargeImg = $(".js-click-here-to-zoom").data("assistive-large-img-url");
                    ACC.colorbox.open("", {
                        html: `<div class="row zoom-image-overlay">
                                 <div class="col-xs-12">
                                   <a href="#" class="cb-back">back</a>
                                 </div>
                                 <div class="col-xs-12">
                                   <img class="img-responsive text-align-center" src="${assistiveZoomImg}" alt=""/>
                                 </div>
                               </div>`,
                        width: '800px',
                        height: '500px',
                        onOpen: function(){ $('.gallery-popup-overlay').remove(); },
                        onComplete: function () {
                            setTimeout(function(){ $.colorbox.resize(); }, 1000);
                            $(document).on("click", "#cboxContent .cb-back", function () {
                                ACC.imagegallery.galleryPopup(assistiveLargeImg, assistiveZoomImg, "")
                            });
                        }
                    });
                });
            });
        },

        onClosed: function() {
            
            $(".pdp-video-responsive").addClass("hidden");
            $('a.popup-big-image').removeClass("hidden");
            $(".js-click-here-to-zoom").parent().addClass('hidden');

            
            $("#inputdataprev").val(0);
        }
    });
},
	bindImageGallery: function (){
		
		function currentCarouselImage(currentItem,itemsAmount){
			$(".pdp-current-item").text(currentItem+1);
			$(".pdp-total-item").text(itemsAmount);
			$(".owl-item.active").find(".launch-image-popup .lazyOwl").css("display","block")
			$(".owl-item").not(".active").find(".launch-image-popup .lazyOwl").css("display","block")
		}
		
		$(".js-gallery").each(function(){
			var $image = $(this).find(".js-gallery-image");
			var $carousel = $(this).find(".js-gallery-carousel")
			var imageTimeout;

			
			$image.owlCarousel({
				singleItem : true,
				pagination:true,
				navigation:true,
				lazyLoad:true,
				addClassActive:true,
				pdpImageCarousel: true,
				navigationText : ["<span class='glyphicon glyphicon-chevron-left'></span>", "<span class='glyphicon glyphicon-chevron-right'></span>"],
				afterAction : function(){
					ACC.imagegallery.syncPosition($image,$carousel,this.currentItem)
					$image.data("zoomEnable",true);
					currentCarouselImage(this.currentItem,this.itemsAmount)				
				},
				afterInit:function(e){
					currentCarouselImage(this.currentItem,this.itemsAmount)	
				},
				
				startDragging: function(e){
					
					$image.data("zoomEnable",false)
				},
				afterLazyLoad:function(e){

					var b = $image.data("owlCarousel") || {}
					if(!b.currentItem){
						b.currentItem = 0
					}

					var $e = $($image.find("img.lazyOwl")[b.currentItem]);
					if ($e.hasClass("disable-zoom") === false) {
						startZoom($e.parent())
					}
				}
			});


			$carousel.owlCarousel({
				navigation:true,
				navigationText : ["<span class='glyphicon glyphicon-chevron-left'></span>", "<span class='glyphicon glyphicon-chevron-right'></span>"],
				pagination:false,
				items:2,
				itemsDesktop : [5000,7], 
				itemsDesktopSmall : [1200,5], 
				itemsTablet: [768,4], 
				itemsMobile : [480,3],
				lazyLoad:true,
				afterAction : function(){

				},
			});


			$carousel.on("click","a.item",function(e){
				e.preventDefault();
				if($(this).hasClass("exceeded")===false){
				$image.trigger("owl.goTo",$(this).parent(".owl-item").data("owlItem"));
				}
				else {
					var firstZoomImage=$(this).data("first-zoom-image");
					var firstLargeImage=$(this).data("first-src");
					var firstVideoUrl=$(this).data("first-video-url");
					ACC.imagegallery.galleryPopup(firstLargeImage,firstZoomImage,firstVideoUrl);
				}
			})



			function startZoom(e){	
				
				if(($(window).width()) <= 600)
					{
						var device = (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
						if(device == true) {
							return false;
						}
					}
				$(e).zoom({
					url: $(e).find("img.lazyOwl").data("zoomImage"),
					touch: true,
					on: "grab",
					touchduration:300,

					onZoomIn:function(){
					
					},

					onZoomOut:function(){
						var owl = $image.data('owlCarousel');
						owl.dragging(true)
						$image.data("zoomEnable",true)
					},

					zoomEnableCallBack:function(){
						var bool = $image.data("zoomEnable")

						var owl = $image.data('owlCarousel');
						if(bool==false){
							owl.dragging(true)
						}
						else{

						 	owl.dragging(false)
						}
						return bool;
					}
				});
			}
		})
	},
	carouselGalleryPdp: function(ref,index,formateVal) {
		$('#inputdataprev').val(index);
	},
	caroselGalleryprevnext: function(ref,carouselClick) {
			target = $(ref);
			index = $('#inputdataprev').val();
			galleryCount = $('#galleryImageCount').val();
			indexdata = index;
			if(index > 0 && Number(index) < galleryCount){
				if(carouselClick == 'prev'){
					index = Number(index) - 1;
				}
				if(carouselClick == 'next'){
					index = Number(index) + 1;
				}
			}
			if(index == 0 &&  indexdata != 1){
                if(carouselClick == 'next'){
					index = Number(index)+ 1;}
                if(carouselClick == 'prev'){
					index = galleryCount-1;}
            }
    		if(index == galleryCount){index=0;}
			$('#inputdataprev').val(index);
			var imgThumbnail=$(".popup-thumbnails_"+index).find(".popup-product-thumb");
			$(".popup-product-thumb").removeClass("selected");
			imgThumbnail.addClass("selected");
			if(imgThumbnail.hasClass("video-thumb")=== false){
				$(".pdp-video-responsive").addClass("hidden");
				var largeImageUrl=imgThumbnail.data("large-image");
				var zoomImageUrl=imgThumbnail.data("zoom-image");
				$(".popup-enlarged-image").attr("src",largeImageUrl).removeClass("hidden");
				$(".popup-hover--message").removeClass("hidden");
				$('a.popup-big-image').addClass("hidden");
				$.colorbox.resize();
				ACC.imagegallery.openImageZoom(zoomImageUrl,largeImageUrl);	
			}
			else{	
				var youtubeURL=$(".popup-thumbnails_"+index).find(".video-thumb").data("youtube-url");
				ACC.imagegallery.openVideoIframe(youtubeURL+ACC.config.youTubeQueryParams);
				$.colorbox.resize();	
			}
	},

	bindGalleryPopup: function() {
		$(document).on("click", ".launch-image-popup", function(e) {
            if($(this).find(".lazyOwl").length > 0){
                var zoomImage=$(this).find(".lazyOwl").data("zoom-image");
                var largeImage=$(this).find(".lazyOwl").data("src");
                var videoUrl=$(this).find(".lazyOwl").data("video-url");
                ACC.imagegallery.galleryPopup(largeImage,zoomImage,videoUrl);   
            }
            else{               
                var zoomImage=$('.image-inner-wrapper.launch-image-popup').find(".lazyOwl").data("zoom-image");
                var largeImage=$('.image-inner-wrapper.launch-image-popup').find(".lazyOwl").data("src");
                var videoUrl=$('.image-inner-wrapper.launch-image-popup').find(".lazyOwl").data("video-url");
                ACC.imagegallery.galleryPopup(largeImage,zoomImage,videoUrl);   
            }  
			
		})
		$(document).on("click", ".launch-image-popup-variant", function(e) {
            if($(this).find(".lazyOwl").length > 0){
                var zoomImage=$(this).find(".lazyOwl").data("zoom-image");
                var largeImage=$(this).find(".lazyOwl").data("src");
                var videoUrl=$(this).find(".lazyOwl").data("video-url");
                ACC.imagegallery.galleryPopup(largeImage,zoomImage,videoUrl,true,this);   
            }
            else{               
                var zoomImage=$('.image-inner-wrapper.launch-image-popup-variant').find(".lazyOwl").data("zoom-image");
                var largeImage=$('.image-inner-wrapper.launch-image-popup-variant').find(".lazyOwl").data("src");
                var videoUrl=$('.image-inner-wrapper.launch-image-popup-variant').find(".lazyOwl").data("video-url");
                ACC.imagegallery.galleryPopup(largeImage,zoomImage,videoUrl,true,this);   
            }  
			
		})
		$(document).on("click", ".popup-thumbnails", function(e) {
				e.preventDefault();
				var imgThumbnail=$(this).find(".popup-product-thumb");
				$(".popup-product-thumb").removeClass("selected");
				imgThumbnail.addClass("selected");
				if(imgThumbnail.hasClass("video-thumb")=== false){
				$(".pdp-video-responsive").addClass("hidden");
				var largeImageUrl=imgThumbnail.data("large-image");
				var zoomImageUrl=imgThumbnail.data("zoom-image");
				$(".popup-enlarged-image").attr("src",largeImageUrl).removeClass("hidden");
				$(".popup-hover--message").removeClass("hidden");
				$('a.popup-big-image').addClass("hidden");
				$.colorbox.resize();
				ACC.imagegallery.openImageZoom(zoomImageUrl,largeImageUrl);
				
			}
			else{
				
				var youtubeURL=$(this).find(".video-thumb").data("youtube-url");
				ACC.imagegallery.openVideoIframe(youtubeURL+ACC.config.youTubeQueryParams);
				$.colorbox.resize();
				
			}
		})
	},
	syncPosition: function($image, $carousel, currentItem) {
		$carousel.trigger("owl.goTo", currentItem);
	},

	onYouTubeIframeAPIReady: function (videoIframeId) {
		if (videoIframeId) {
			player = new YT.Player(videoIframeId, {
				events: {
					'onReady': ACC.imagegallery.onPlayerReady,
					'onStateChange': ACC.imagegallery.onPlayerStateChange
				}
			});
			players.push({player:player,isStopped:false});
		}
	},

	
	youTubeData: function(name,id,player,trackData){
		digitalData.eventData = {
			videoName: name,
			videoID: id,
			videoPlayer: player
		}
		try {
			_satellite.track(trackData);
		} catch (e) { }
	},
	
	onPlayerStateChange: function (event) {

		var playerStatus = event.data;
		var playerName = "youtube"
		var playerData = event.target.getVideoData();
		switch (playerStatus) {
			case 0: {
				if (!event.target.getVideoData().list) {
					ACC.imagegallery.youTubeData(playerData.title, playerData.video_id, "Youtube", "videoComplete");
				}

				ACC.imagegallery.resetWatchPercentage();
				console.log("ended");
				break;
			}

				case 1: {

				setInterval(ACC.imagegallery.showPercentage, 50, event);
				if (Math.round(event.target.getCurrentTime()) > 1) {
					ACC.imagegallery.youTubeData(playerData.title, playerData.video_id, "Youtube", "videoResume");
					console.log("Resume");
				}

				break;
			}

			case 2: {

				ACC.imagegallery.youTubeData(playerData.title, playerData.video_id, "Youtube", "videoPause");
				console.log("Paused");
			    if($(event.target.g).hasClass("marketing-videos")){
					ACC.imagegallery.resetWatchPercentage();
				}
				break;
			}
		}

	},
	showPercentage: function (videoEvent) {
		var percent = 0;
		var currentTime = !videoEvent.target.getCurrentTime ? 0.0 : videoEvent.target.getCurrentTime();
		var totalDuration = !videoEvent.target.getDuration ? 0.0 : videoEvent.target.getDuration();
		var playerDetails = videoEvent.target.getVideoData();
		percent = Math.ceil((currentTime / totalDuration) * 100);

		switch (percent) {
			case 1: {

				if ($(videoEvent.target.h).hasClass("marketing-videos")) {

					var currentUrl = videoEvent.target.h.src;
					for (var i = 0; i < players.length; i++) {
						if (players[i].player.h.src != currentUrl && players[i].isStopped===true) {
							players[i].player.stopVideo();
							players[i].isStopped=false;

						}
						else if(players[i].player.h.src == currentUrl && players[i].isStopped===false){
							players[i].isStopped=true;
							ACC.imagegallery.resetWatchPercentage();
						}

					}

				}
				if (zeroWatched === false) {
					console.log("started");
					ACC.imagegallery.youTubeData(playerDetails.title, playerDetails.video_id, "Youtube", "videoStart");
					zeroWatched = true;
				}
				break;
			}
			case 25: {
				if(oneFourthWatched ===false){
				console.log("25");
				ACC.imagegallery.youTubeData(playerDetails.title,playerDetails.video_id,"Youtube","videoMilestone25");
				oneFourthWatched =true;
				}
				break;
			}
			case 50: {
				if(halfWatched===false){
				console.log("50");
				ACC.imagegallery.youTubeData(playerDetails.title,playerDetails.video_id,"Youtube","videoMilestone50");
				halfWatched=true
			}
				break;
			}
			case 75: {
				if(threeFourthWatched===false){
				console.log("75");
				ACC.imagegallery.youTubeData(playerDetails.title,playerDetails.video_id,"Youtube","videoMilestone75");
				threeFourthWatched=true;
			}
				break;
			}
			case 99: {
				if (listWatched === false) {
					if (videoEvent.target.getVideoData().list) {
						ACC.imagegallery.youTubeData(playerDetails.title, playerDetails.video_id, "Youtube", "videoComplete");
						listWatched = true;
					}
			}
				break;
			}
		}
	},
	videoAnalyticsFlag: true,
	videoAnalyticsAdd() {
		$(".marketing-videos").each(function (e) {
			let target = $(this);
			let targetParent = target.parent();
			targetParent.html();
			let videoWidth = target.attr("width");
			let videoHeight = target.attr("height");
			let videoAllow = target.attr("allow");
			let videoId = target.attr("id");
			let videoTitle = target.attr("title");
			let videoSrc = target.attr("src");
			targetParent.html('<iframe width="' + videoWidth + '" height="' + videoHeight + '" allow="' + videoAllow + '" allowfullscreen="" class="marketing-videos" title="' + videoTitle + '" frameborder="0" id="' + videoId + '" onload="ACC.imagegallery.videoAnalyticsCapture(this)" data-videonum="' + e + '" src="' + videoSrc + '" ></iframe>');
		});
	},
	videoAnalyticsCapture(element) {
		$(document).ready(function () {
			if ($(element).hasClass("marketing-videos")) {
				var elemId = $(element).attr("id");
				ACC.imagegallery.resetWatchPercentage();
				ACC.imagegallery.onYouTubeIframeAPIReady(elemId);
			}
		});
	},
	thumnailGalleryImagePDP: function (btn){
				totalGalleryImage = $('#totalGalleryImage').val();
				nextBtnValue = $('#nextBtnValue').val();
				startIndex = $('#startIndex').val();
				if(btn == 'down'){
					$('.down-arrow-icon').removeClass('disabled');
					if(nextBtnValue < Number(totalGalleryImage) + 1){
						$("#galleyImageItem_"+Number(nextBtnValue)).removeClass('hidden');
						$("#galleyImageItem_"+Number(startIndex)).addClass('hidden');
						$("#startIndex").val(Number(startIndex)+1);
						$("#nextBtnValue").val(Number(nextBtnValue)+1);	
						$('.up-arrow-icon').removeClass('disabled');
					}
					if($('#nextBtnValue').val() >= Number(totalGalleryImage) + 1){
						$('.down-arrow-icon').addClass('disabled');
					}
				}
				if(btn == 'up'){
					if(startIndex != 1){
						$('.down-arrow-icon').removeClass('disabled');
						$("#startIndex").val(Number(startIndex)-1);
						 $("#nextBtnValue").val(Number(nextBtnValue)-1);	
						nextBtnValue = $('#nextBtnValue').val();
						startIndex = $('#startIndex').val();
						$("#galleyImageItem_"+Number(nextBtnValue)).addClass('hidden');
						$("#galleyImageItem_"+Number(startIndex)).removeClass('hidden');
					}
					if(startIndex == 1){
						$('.up-arrow-icon').addClass('disabled');
					}
				}
			},
	mainImageGallery: function(index){
		$(".js-gallery").find(".js-gallery-image").trigger("owl.goTo",index-1);
	},
			
	
},

$(document).ready(function () {
	$(".overlay-text-visible").attr("style", "");

	var tag = document.createElement('script');
	tag.id = 'iframe-demo';
	tag.src = 'https://www.youtube.com/iframe_api';
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
})
function marketingVideoAnalytics(element) {
	if($(element).hasClass("marketing-videos") && ACC.imagegallery.videoAnalyticsFlag){
		ACC.imagegallery.videoAnalyticsFlag = false;
		ACC.imagegallery.videoAnalyticsAdd();
	}
}