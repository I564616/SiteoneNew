<%@ attribute name="width" required="true" type="java.lang.String" %>
<%@ attribute name="height" required="true" type="java.lang.String" %>
<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}" viewBox="0 0 55 55">
    <defs>
        <style>
            .big-cross-circle,
            .big-cross-circle2 {
                fill: none;
            }

            .big-cross-circle {
                stroke: #5a5b5d;
                stroke-width: 3px;
            }

            .big-cross-icon {
                fill: #5a5b5d;
            }

            .big-cross-circle1 {
                stroke: none;
            }
        </style>
    </defs>
    <g transform="translate(-92 -475)">
        <g class="big-cross-circle" transform="translate(92 475)">
            <circle class="big-cross-circle1" cx="27.5" cy="27.5" r="27.5" />
            <circle class="big-cross-circle2" cx="27.5" cy="27.5" r="26" />
        </g>
        <path class="big-cross-icon"
            d="M9.654,87l3.98-3.98a1.251,1.251,0,0,0,0-1.769l-.885-.885a1.251,1.251,0,0,0-1.769,0L7,84.346l-3.98-3.98a1.251,1.251,0,0,0-1.769,0l-.885.885a1.251,1.251,0,0,0,0,1.769L4.346,87,.366,90.98a1.251,1.251,0,0,0,0,1.769l.885.885a1.251,1.251,0,0,0,1.769,0L7,89.654l3.98,3.98a1.251,1.251,0,0,0,1.769,0l.885-.885a1.251,1.251,0,0,0,0-1.769Z"
            transform="translate(113 416)" />
    </g>
</svg>