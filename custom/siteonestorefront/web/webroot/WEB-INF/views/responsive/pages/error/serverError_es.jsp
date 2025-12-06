<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Error del sistema ocurrido</title>
        <link rel="stylesheet" type="text/css" media="all" href="/_ui/responsive/theme-lambda/css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style type="text/css">
            .error-container{
                padding-top: 100px;
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
     <div class="col-md-12 text-center errorbg error-container">
			<img class="errorimg" src="/_ui/responsive/theme-lambda/images/siteone_logo.png"><br><br>
			<div class="errormsg">Algo salió mal</div><br>
			<div>Lo sentimos, no se encontró la página que</div>
			<div class="errortxt">buscas. Favor de regresar a la página de inicio.</div>
			<a class="error-link" href="/en/">Página de inicio SiteOne</a>
	 </div>
     <script type="text/javascript">
        window.addEventListener('load', function () {
   			_satellite.track('pageload');
      	});
    </script>
    </body>
</html>