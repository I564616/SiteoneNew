<%@ attribute name="iconColor" required="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>
<span class="flex-center">
<svg xmlns="http://www.w3.org/2000/svg" width="${width ne null?width:'34'}" height="${height ne null?height:'34'}" viewBox="0 0 34 34">
   <defs>
      <style>.nearby-g,.nearby-circle2{fill:none;}.nearby-g{stroke-width:3px;}.nearby-circle1{stroke:none;}</style>
   </defs>
   <g transform="translate(-851 -522)">
      <path class="nearby-path" fill=${iconColor} d="M5.349,76.863.231,71.633a.817.817,0,0,1,0-1.138l1.114-1.138a.776.776,0,0,1,1.114,0L5.906,72.88l7.385-7.546a.776.776,0,0,1,1.114,0l1.114,1.138a.817.817,0,0,1,0,1.138L6.463,76.863a.776.776,0,0,1-1.114,0Z" transform="translate(860 467.902)"></path>
      <g class="nearby-g" stroke=${iconColor} transform="translate(851 522)">
         <circle class="nearby-circle1" cx="17" cy="17" r="17"></circle>
         <circle class="nearby-circle2" cx="17" cy="17" r="15.5"></circle>
      </g>
   </g>
</svg>
</span>