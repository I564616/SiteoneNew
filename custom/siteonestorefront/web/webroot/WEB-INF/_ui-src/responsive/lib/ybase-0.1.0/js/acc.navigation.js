var oDoc = document;

ACC.navigation = {

    _autoload: [
        "offcanvasNavigation",
        "myAccountNavigation",
        "orderToolsNavigation",
        "footerNav",
        "hoverIntentMainMenu"
    ],

    offcanvasNavigation: function(){

        //flyout - extend height of L1 to match L2 height
        $('.L1Categories').hover(function(){
            $('.showCategories').css('height', $(this).find('.L2Categories').height());
        }, function(){
            $('.showCategories').css('height', 'auto');
        });

        enquire.register("screen and (max-width:"+screenSmMax+")", {

            match : function() {
                $(".js-enquire-offcanvas-navigation .js-enquire-has-sub .js_nav__link--drill__down").off();
                $(".js-enquire-offcanvas-navigation .js-enquire-has-sub .js_nav__link--drill__down").bind("click",function(e){
                    e.preventDefault();
                    $(".js-userAccount-Links").hide();
                    $(".js-enquire-offcanvas-navigation ul.js-offcanvas-links").addClass("active");
                    $(".js-enquire-offcanvas-navigation .js-enquire-has-sub").removeClass("active");
                    $(this).closest('.js-enquire-has-sub').addClass('active');
                    
                    
                });
                
                $('.category_item').on('click', function(e){ 
                    var fclick =  true;
                    if(fclick){
                        fclick =  false;
                            e.preventDefault();
                            $(".js-userAccount-Links").hide();
                            $(".js-enquire-offcanvas-navigation ul.js-offcanvas-links").addClass("active");
                            $(".js-enquire-offcanvas-navigation .js-enquire-has-sub").removeClass("active");
                            $(".js-enquire-offcanvas-navigation .js-enquire-has-sub .js_nav__link--drill__down").closest('.js-enquire-has-sub').addClass('active');
                            $('.partner-cat').addClass('active');                           
                          
                    }
                    var getIndex = $(this).index();                  
                    $('.level1-new').find('.l1-img').eq(getIndex).find('.sub-category-new').click();
                });
                
                $('.level1-cat > li .js-L2-nav').on('click', function(e){
                    //$(this).addClass('active');
                    $(this).closest('.level1-cat').addClass('active');
                    $(this).parent().find('.level2-cat').addClass('active');
                });
                $('.level2-cat .js-L2-item').on('click', function(e){
                    if($(this).hasClass("shopall_txt") != true){
                        e.preventDefault();
                        var level2child=$(this).children()[1].value;
                        if(level2child > 0)
                        {
                            $(this).closest('.level2-cat').addClass('selected');
                            $(this).find('.level3-cat').addClass('active');
                        }
                        else{
                    	    window.location.href=$(this).children()[0].href;
                        }
                    }
                });
                $('.js-back-L1').off().on('click', function(e){
                    $(".js-enquire-offcanvas-navigation ul.js-offcanvas-links").removeClass('active');
                    $(this).closest('.js-enquire-has-sub').removeClass('active');
                    $(this).closest('.selected').removeClass('selected');
                    $('.partner-cat').removeClass('active');
                });
                $('.js-back-L2').off().on('click', function(e){
                    $(this).closest('.active').removeClass('active');
                    $(this).closest('.active').removeClass('active');
                    //$('.partner-cat').removeClass('active');
                });
                $('.js-back-L3').off().on('click', function(e){
                    e.stopPropagation();
                    $(this).closest('.active').removeClass('active');
                    $(this).closest('.selected').removeClass('selected');
                });
                $('.level3-cat a').off().on('click', function(e) {
                    e.stopPropagation();
                });
                
                $(document).on("click",".js-enquire-offcanvas-navigation .js-enquire-sub-close",function(e){
                    e.preventDefault();
                    $(this).parent().parent(".js-enquire-has-sub").removeClass("active");
                    //$(".js-userAccount-Links").show();
                    //$(".js-enquire-offcanvas-navigation ul.js-offcanvas-links").removeClass("active");
                    //$(".js-enquire-offcanvas-navigation .js-enquire-has-sub").removeClass("active");
                });
            },

            unmatch : function() {

                
            }


        });

    },

    myAccountNavigation: function(){
        //Add the order form img in the navigation
        $('.nav-form').html($('<span class="glyphicon glyphicon-list-alt"></span>'));


        var aAcctData = [];
        var sSignBtn = "";

        //my account items
        var oMyAccountData = $(".accNavComponent");

        //the my Account hook for the desktop
        var oMMainNavDesktop = $(".js-secondaryNavAccount > ul");

        //offcanvas menu for tablet/mobile
        var oMainNav = $(".navigation--bottom > ul.nav__links.nav__links--products");

        if(oMyAccountData){
            var aLinks = oMyAccountData.find("a");
            for(var i = 0; i < aLinks.length; i++){
                aAcctData.push({link: aLinks[i].href, text: aLinks[i].title});
            }
        }

        var navClose = '';
        navClose += '<div class="close-nav">';
        navClose += '<button type="button" class="js-toggle-sm-navigation btn bold">X</button>';
        navClose += '</div>';
        
       

        //create Sign In/Sign Out Button
        if($(".liOffcanvas a") && $(".liOffcanvas a").length > 0){
            sSignBtn += '<li class=\"auto liUserSign\" ><a class=\"userSign\" href=\"' + $(".liOffcanvas a")[0].href + '\">' + $(".liOffcanvas a")[0].innerHTML + '</a></li>';
        }

        //create Welcome User + expand/collapse and close button
        //This is for mobile navigation. Adding html and classes.
        var oUserInfo = $(".nav__right ul li.logged_in");
        //Check to see if user is logged in
        if(oUserInfo && oUserInfo.length === 1)
        {
            var sUserBtn = '';
            sUserBtn += '<li class=\"auto \">';
            sUserBtn += '<div class=\"userGroup\">';
            sUserBtn += '<span class="glyphicon glyphicon-user myAcctUserIcon"></span>';
            sUserBtn += '<div class=\"userName\">' + oUserInfo[0].innerHTML + '</div>';
            if(aAcctData.length > 0){
                sUserBtn += '<a class=\"collapsed js-nav-collapse\" id="signedInUserOptionsToggle" data-toggle=\"collapse\"  data-target=\".offcanvasGroup1\">';
                sUserBtn += '<span class="glyphicon glyphicon-chevron-up myAcctExp"></span>';
                sUserBtn += '</a>';
            }
            sUserBtn += '</div>';
            sUserBtn += navClose;

            $('.js-sticky-user-group').html(sUserBtn);


            $('.js-userAccount-Links').append(sSignBtn);
            $('.js-userAccount-Links').append($('<li class="auto"><div class="myAccountLinksContainer js-myAccountLinksContainer"></div></li>'));


            //FOR DESKTOP
            var myAccountHook = $('<a class=\"myAccountLinksHeader collapsed js-myAccount-toggle\" data-toggle=\"collapse\" data-parent=".nav__right" href=\"#accNavComponentDesktopOne\">' + oMyAccountData.data("title") + '</a>');
            myAccountHook.insertBefore(oMyAccountData);

            //FOR MOBILE
            //create a My Account Top link for desktop - in case more components come then more parameters need to be passed from the backend
            var myAccountHook = [];
            myAccountHook.push('<div class="sub-nav">');
            myAccountHook.push('<a id="signedInUserAccountToggle" class=\"myAccountLinksHeader collapsed js-myAccount-toggle\" data-toggle=\"collapse\" data-target=".offcanvasGroup2">');
            myAccountHook.push(oMyAccountData.data("title"));
            myAccountHook.push('<span class="glyphicon glyphicon-chevron-down myAcctExp"></span>');
            myAccountHook.push('</a>');
            myAccountHook.push('</div>');

            $('.js-myAccountLinksContainer').append(myAccountHook.join(''));

            //add UL element for nested collapsing list
            $('.js-myAccountLinksContainer').append($('<ul data-trigger="#signedInUserAccountToggle" class="offcanvasGroup2 offcanvasNoBorder collapse js-nav-collapse-body subNavList js-myAccount-root sub-nav"></ul>'));


            //offcanvas items
            //TODO Follow up here to see the output of the account data in the offcanvas menu
            for(var i = aAcctData.length - 1; i >= 0; i--){
                var oLink = oDoc.createElement("a");
                oLink.title = aAcctData[i].text;
                oLink.href = aAcctData[i].link;
                oLink.innerHTML = aAcctData[i].text;

                var oListItem = oDoc.createElement("li");
                oListItem.appendChild(oLink);
                oListItem = $(oListItem);
                oListItem.addClass("auto ");
                $('.js-myAccount-root').append(oListItem);
            }

        } else {
            //var navButtons = (sSignBtn.substring(0, sSignBtn.length - 5) + navClose) + '</li>';
            var navButtons = navClose + '</li>';
            $('.js-sticky-user-group').html(navButtons);

        }
        //guest user
        if(!$('#welcomeOverlay').length ) {
            $('.liOffcanvas').find('a').css('width', '100%');
            $('.js-offcanvas-links')
                .append("<li class='hide-li'>" + $('.liOffcanvas').html() + "</li>");
        }
        $('.js-offcanvas-links')
            .append("<li class='hide-li'>" + $('#js-mobile-flyoutlink').html() + "</li>");
        $('.js-mobile-links').each(function() {
            $('.js-offcanvas-links').append("<li class='hide-li'>" + $(this).html() + "</li>");
        });
        ACC.global.countrySelectEvents();

        //desktop
        for(var i = 0; i < aAcctData.length; i++){
            var oLink = oDoc.createElement("a");
            oLink.title = aAcctData[i].text;
            oLink.href = aAcctData[i].link;
            oLink.innerHTML = aAcctData[i].text;

            var oListItem = oDoc.createElement("li");
            oListItem.appendChild(oLink);
            oListItem = $(oListItem);
            oListItem.addClass("auto col-md-4");
            oMMainNavDesktop.get(0).appendChild(oListItem.get(0));
        }

        //hide and show contnet areas for desktop
        $('.js-secondaryNavAccount').on('shown.bs.collapse', function () {

            if($('.js-secondaryNavCompany').hasClass('in')){
                $('.js-myCompany-toggle').trigger('click');
            }

        });

        $('.js-secondaryNavCompany').on('shown.bs.collapse', function () {

            if($('.js-secondaryNavAccount').hasClass('in')){
                $('.js-myAccount-toggle').trigger('click');
            }

        });

        //change icons for up and down


        $('.js-nav-collapse-body').on('hidden.bs.collapse', function(e){

            var target = $(e.target);
            var targetSpan = target.attr('data-trigger') + ' > span';
            if(target.hasClass('in')) {
                $(targetSpan).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
            }
            else {
                $(targetSpan).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
            }

        });

        $('.js-nav-collapse-body').on('show.bs.collapse', function(e){
            var target = $(e.target)
            var targetSpan = target.attr('data-trigger') + ' > span';
            if(target.hasClass('in')) {
                $(targetSpan).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');

            }
            else {
                $(targetSpan).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
            }

        });

        //$('.offcanvasGroup1').collapse();
        //Blank space issue fix
        $(document).on("click", ".mobile__nav__row--btn-menu", function (e) {
            document.body.classList.add('max-height-mobile-menu');
        });

    },

    orderToolsNavigation: function(){
        $('.js-nav-order-tools').on('click', function(e){
            $(this).toggleClass('js-nav-order-tools--active');
        });
    },
    footerNav: function() {
        $('.js-footer-dropdown').on('click', function(e) {
            e.stopPropagation();

            var $icon = $(this).find('.glyphicon');

            //apply only for mobile
            if(!$icon.is(':visible')){
                return;
            }

            if($icon.hasClass('glyphicon-triangle-right')) {
                $(this).closest('.footer__nav--container').find('.footer__nav--links').slideDown();
                $icon.removeClass('glyphicon-triangle-right').addClass('glyphicon-triangle-bottom');
            } else {
                $(this).closest('.footer__nav--container').find('.footer__nav--links').slideUp();
                $icon.removeClass('glyphicon-triangle-bottom').addClass('glyphicon-triangle-right');
            }
        });
    },

    hoverIntentMainMenu: function() {
        $(window).on('load resize', function () { 
            var viewportWidth = $(window).width();
            var $menuSubRight = "",
                $menuSubLeft = "";
            if(viewportWidth >= 1000 && viewportWidth < 1200){
                $menuSubRight = $(".js-navigation--middle .L2Categories:not(.SH16,.SH17,.SH11,.SH15)");
                $menuSubLeft = $(".js-navigation--middle .L2Categories.SH16, .js-navigation--middle .L2Categories.SH17, .js-navigation--middle .L2Categories.SH11 ,.js-navigation--middle .L2Categories.SH15");
                $menuSubRight.menuAim("destroy");
                $menuSubLeft.menuAim("destroy");

            } else if(viewportWidth >= 1200) {
                $menuSubRight = $(".js-navigation--middle .L2Categories:not(.SH16,.SH17,.SH15)");
                $menuSubLeft = $(".js-navigation--middle .L2Categories.SH16, .js-navigation--middle .L2Categories.SH17 ,.js-navigation--middle .L2Categories.SH15");
                $menuSubRight.menuAim("destroy");
                $menuSubLeft.menuAim("destroy");
            }
			
			if(viewportWidth >= 1000){
				$menuSubRight.menuAim({
                submenuDirection: "right",
                activate: activateSubmenu,
                deactivate: deactivateSubmenu
            	});
            
           	    $menuSubLeft.menuAim({
                submenuDirection: "left",
                activate: activateSubmenu,
                deactivate: deactivateSubmenu
                });
			}
            
        });
        
        function activateSubmenu(row) {
            var $row = $(row),
                $submenu = $row.find('.L3categoryli');
        
            $row.find('a.L2CatLinks').addClass('hover');
            $submenu.css({display: 'block'});
        }
        
        function deactivateSubmenu(row) {
            var $row = $(row),
                $submenu = $row.find('.L3categoryli');
        
            $row.find('a.L2CatLinks').removeClass('hover');
            $submenu.css('display', 'none');
        }

    }
};
