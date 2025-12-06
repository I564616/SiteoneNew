ACC.minicart = {

    _autoload: [
        "bindMiniCart"
    ],
    
    bindMiniCart: function(){

        $(document).on("click",".js-mini-cart-link", function(e){
            e.preventDefault();

            if($('#checkout-cart-link').length) {
                window.location.href = ACC.config.encodedContextPath + '/cart';
                return;
            }

            var url = $(this).data("miniCartUrl") + "?v=" + Date.now();
            var cartName = ($(this).find(".js-mini-cart-count").html() != 0) ? $(this).data("miniCartName"):$(this).data("miniCartEmptyName");
            loading.start();
            ACC.colorbox.open("",{
                href: url,
                maxWidth:"100%",
                width:"621px",
                overflow:"hidden",
                initialWidth :"380px",
                onComplete:function(){ 
                    $("#cboxContent").addClass("mini-cart-page");
                    $("#colorbox").addClass("mini-cart-colorbox");
                    loading.stop();
                }
            });
        });

        $(document).on("click",".js-mini-cart-close-button", function(e){
            $("#cboxContent").removeClass("mini-cart-page");
            $("#colorbox").removeClass("mini-cart-colorbox");
            e.preventDefault();
            ACC.colorbox.close();
            ACC.minicart.updateMiniCartDisplay();
        });
    },
    
    updateMiniCartDisplay: function(){
        var cartItems = $(".mini-cart-link").data("miniCartItemsText");
        var miniCartRefreshUrl = $(".mini-cart-link").data("miniCartRefreshUrl");
        $.ajax({
            url: miniCartRefreshUrl,
            cache: false,
            type: 'GET',
            success: function(jsonData){
            	$('#cartCountId').val(jsonData.miniCartCount);
                $(".mini-cart-link .js-mini-cart-count").html('<span class="nav-items-total">' + jsonData.miniCartCount + '</span>' ); }
           
        });
    }

};