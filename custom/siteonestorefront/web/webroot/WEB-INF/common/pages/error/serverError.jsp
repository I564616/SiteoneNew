<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <title>System Error Occurred</title>
        <link rel="stylesheet" type="text/css" media="all" href="/wro/theme.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style type="text/css">
            .container-lg {
                padding-left: 20px;
                padding-right: 20px;
            }
        </style>

        <script type="text/javascript">
            if(window.location.host.indexOf('siteone.com') == 0 
                || window.location.host.indexOf('new.siteone.com') == 0
                || window.location.host.indexOf('www.siteone.com') == 0
                || window.location.href == "https://siteone.com/") {
            	document.write('<script src="//assets.adobedtm.com/launch-EN5dfbd141e22f428c9d7b74e9899499cb.min.js" async><\/script>'); 
            } else {
            	document.write('<script src="//assets.adobedtm.com/launch-EN137fa872e4554425b4c285c1a6443e53-development.min.js" async><\/script>'); 
            }
        </script>

        <script type="text/javascript">
            _AAData = {"page":{"pageName":"System Error Occurred","template":"Error Page Template"},"language":{"LanguageSelection":"english"},"pathingChannel": "Error: System Error","pathingPageName": "Error: System Error Occurred","channel":"404 Page Not Found","systemErrors":"page is not found"};
        </script>
    </head>

    <body>

    <header class="container-lg" style="padding-top: 20px">
		<a href="<c:url value="/"/>"><img src="/_ui/responsive/theme-lambda/images/SiteoneLogo.jpg"/></a>
	</header>
    <div class="container-lg">

        <h1 class="headline">This page isn't working. Neither is Steve<h1>    
    </div>
    
    <div class="text-center">
        <img src="/_ui/responsive/common/images/SiteOne-Steve.jpg" width="100%" style="max-width: 1680px;" />    
    </div>
    

    <div class="container-lg" style="padding-top: 20px;">
        <p>We're sorry, but the page you're looking for is either missing, broken or no longer exists.<br>Please try re-typing the address, or use the menu above to search our site.</p>

        <div class="content">
            <h5>
            <b>Search tips:</b>
            </h5>
            <ul>
                <li>Use different words</li>
                <li>Check your spelling</li>
                <li>Start with something less specific-<br>then narrow your results</li>
            </ul>
        </div>

        <div class="col-xs-12 col-md-12">
            <h5><b>Search again:</b></h5>
                <div class="row">
                <div class="col-md-12 col-xs-12">
                    <form method="get" action="<c:url value="/search/"/>" id="searchBox">
                        <input type="text" class="form-control search-empty-input" name="text" value="">
                        <span class="col-md-2"> <button class="btn btn-block btn-primary">Search</button></span>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-md-12">
            <h5><b>Let us help:</b></h5>
            <ul>
                <li>Call <a href="tel:(800)748-3663">1-800-SITEONE (1-800-748-3663)</a></li>
                <li>Email us at&nbsp;&nbsp;<a href="mailto:customersupport@siteone.com ">customersupport@siteone.com </a></li>
            </ul>
        </div>
    </div>
     <script type="text/javascript">
        window.addEventListener('load', function () {
   			_satellite.track('pageload');
      	});
    </script>
    </body>
</html>
