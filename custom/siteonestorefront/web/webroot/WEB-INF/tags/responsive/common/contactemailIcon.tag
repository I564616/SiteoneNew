<%@ attribute name="iconColor" required="true" type="java.lang.String" %>
 
<%@ attribute name="width" required="true" type="java.lang.String" %>
<%@ attribute name="height" required="true" type="java.lang.String" %>

<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}" viewBox="0 0 30 30">
   <defs>
      <style>.mail-icon{fill:${iconColor};}</style>
   </defs>
   <path class="mail-icon" d="M29.411,11.317l-4.1-1.639V.938A.938.938,0,0,0,24.375,0H5.625a.938.938,0,0,0-.937.938v8.74l-4.1,1.639A.938.938,0,0,0,0,12.188H0V29.063A.938.938,0,0,0,.938,30H29.063A.938.938,0,0,0,30,29.063V12.188h0A.938.938,0,0,0,29.411,11.317Zm-2.245,1.122-1.853,1.151V11.7ZM23.438,1.875V14.753L15,19.99,6.563,14.753V1.875ZM4.688,13.589,2.834,12.439,4.688,11.7ZM1.875,28.125V14.051l12.631,7.84a.937.937,0,0,0,.989,0l12.631-7.839V28.125Z"/>
   <path class="mail-icon" d="M54.063,14.375h3.75a.938.938,0,0,0,0-1.875h-3.75a.938.938,0,0,0,0,1.875Z" transform="translate(-37.188 -8.75)"/>
   <path class="mail-icon" d="M29.063,26.875h11.25a.938.938,0,0,0,0-1.875H29.063a.938.938,0,0,0,0,1.875Z" transform="translate(-19.688 -17.5)"/>
   <path class="mail-icon" d="M29.063,39.375h11.25a.938.938,0,0,0,0-1.875H29.063a.938.938,0,0,0,0,1.875Z" transform="translate(-19.688 -26.25)"/>
</svg>