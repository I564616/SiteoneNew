<%@ attribute name="iconColor" required="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>
<span class="flex-center">
<svg xmlns="http://www.w3.org/2000/svg" width="${width ne null?width:'34'}" height="${height ne null?height:'34'}" viewBox="0 0 34 34">
   <defs>
      <style>.cross-mark-circle-container,.cross-mark-circle2{fill:none;}.cross-mark-circle-container{stroke-width:3px;}.cross-mark-circle1{stroke:none;}</style>
   </defs>
   <g transform="translate(-1022 -1007)">
      <g class="cross-mark-circle-container" stroke=${iconColor} transform="translate(1022 1007)">
         <circle class="cross-mark-circle1" cx="17" cy="17" r="17"/>
         <circle class="cross-mark-circle2" cx="17" cy="17" r="15.5"/>
      </g>
      <path fill=${iconColor} d="M6.9,85l2.843-2.843a.894.894,0,0,0,0-1.264l-.632-.632a.894.894,0,0,0-1.264,0L5,83.1,2.157,80.262a.894.894,0,0,0-1.264,0l-.632.632a.894.894,0,0,0,0,1.264L3.1,85,.262,87.843a.894.894,0,0,0,0,1.264l.632.632a.894.894,0,0,0,1.264,0L5,86.9l2.843,2.843a.894.894,0,0,0,1.264,0l.632-.632a.894.894,0,0,0,0-1.264Z" transform="translate(1034 939)"/>
   </g>
</svg>
</span>