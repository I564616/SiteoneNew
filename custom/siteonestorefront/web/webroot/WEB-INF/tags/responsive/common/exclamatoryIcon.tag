<%@ attribute name="iconColor" required="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>
<span class="flex-center">
<svg xmlns="http://www.w3.org/2000/svg" width="${width ne null?width:'34'}" height="${height ne null?height:'34'}" viewBox="0 0 34 34">
   <defs>
      <style>.available-path,.available-circle2{fill:none;}.available-path{stroke-width:3px;}.available-circle1{stroke:none;}</style>
   </defs>
   <g transform="translate(-596 -851)">
      <g class="available-path" stroke=${iconColor} transform="translate(596 851)">
         <circle class="available-circle1" cx="17" cy="17" r="17"/>
         <circle class="available-circle2" cx="17" cy="17" r="15.5"/>
      </g>
      <path class="available-g" fill=${iconColor} d="M20.688,12.656a2.344,2.344,0,1,1-2.344-2.344A2.346,2.346,0,0,1,20.688,12.656ZM16.271.738l.4,7.969a.7.7,0,0,0,.7.668h1.944a.7.7,0,0,0,.7-.668l.4-7.969a.7.7,0,0,0-.7-.738h-2.74A.7.7,0,0,0,16.271.738Z" transform="translate(595 861)"/>
   </g>
</svg>
</span>