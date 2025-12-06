<%@ attribute name="iconColor" required="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" type="java.lang.String" %>
<%@ attribute name="height" required="false" type="java.lang.String" %>
<%@ attribute name="className" required="false" type="java.lang.String" %>
<svg xmlns="http://www.w3.org/2000/svg" class="${className ne null ? className : ' '}" width="${width ne null ? width : '15.696'}" height="${height ne null ? height : '15.696'}" viewBox="0 0 15.696 15.696">
    <path fill=${iconColor} d="M15.848,8A7.848,7.848,0,1,0,23.7,15.848,7.849,7.849,0,0,0,15.848,8Zm0,3.481a1.329,1.329,0,1,1-1.329,1.329A1.329,1.329,0,0,1,15.848,11.481Zm1.772,8.038a.38.38,0,0,1-.38.38H14.456a.38.38,0,0,1-.38-.38v-.76a.38.38,0,0,1,.38-.38h.38V16.355h-.38a.38.38,0,0,1-.38-.38v-.76a.38.38,0,0,1,.38-.38h2.025a.38.38,0,0,1,.38.38V18.38h.38a.38.38,0,0,1,.38.38Z" transform="translate(-8 -8)"/>
</svg>