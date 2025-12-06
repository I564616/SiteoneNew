/*
 *
 * TableSorter 2.0 - Client-side table sorting with ease!
 * Version 2.0.5b
 * @requires jQuery v1.2.3
 *
 * Copyright (c) 2007 Christian Bach
 * Examples and docs at: http://tablesorter.com
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */
(function($){$.extend({tablesorter:new
function(){var parsers=[],widgets=[];this.defaults={cssHeader:"header",cssAsc:"headerSortUp",cssDesc:"headerSortDown",cssChildRow:"expand-child",sortInitialOrder:"asc",sortMultiSortKey:"shiftKey",sortForce:null,sortAppend:null,sortLocaleCompare:true,textExtraction:"simple",parsers:{},widgets:[],widgetZebra:{css:["even","odd"]},headers:{},widthFixed:false,cancelSelection:true,sortList:[],headerList:[],dateFormat:"us",decimal:'/\.|\,/g',onRenderHeader:null,selectorHeaders:'thead th',debug:false};function benchmark(s,d){log(s+","+(new Date().getTime()-d.getTime())+"ms");}this.benchmark=benchmark;function log(s){if(typeof console!="undefined"&&typeof console.debug!="undefined"){console.log(s);}else{alert(s);}}function buildParserCache(table,$headers){if(table.config.debug){var parsersDebug="";}if(table.tBodies.length==0)return;var rows=table.tBodies[0].rows;if(rows[0]){var list=[],cells=rows[0].cells,l=cells.length;for(var i=0;i<l;i++){var p=false;if($.metadata&&($($headers[i]).metadata()&&$($headers[i]).metadata().sorter)){p=getParserById($($headers[i]).metadata().sorter);}else if((table.config.headers[i]&&table.config.headers[i].sorter)){p=getParserById(table.config.headers[i].sorter);}if(!p){p=detectParserForColumn(table,rows,-1,i);}if(table.config.debug){parsersDebug+="column:"+i+" parser:"+p.id+"\n";}list.push(p);}}if(table.config.debug){log(parsersDebug);}return list;};function detectParserForColumn(table,rows,rowIndex,cellIndex){var l=parsers.length,node=false,nodeValue=false,keepLooking=true;while(nodeValue==''&&keepLooking){rowIndex++;if(rows[rowIndex]){node=getNodeFromRowAndCellIndex(rows,rowIndex,cellIndex);nodeValue=trimAndGetNodeText(table.config,node);if(table.config.debug){log('Checking if value was empty on row:'+rowIndex);}}else{keepLooking=false;}}for(var i=1;i<l;i++){if(parsers[i].is(nodeValue,table,node)){return parsers[i];}}return parsers[0];}function getNodeFromRowAndCellIndex(rows,rowIndex,cellIndex){return rows[rowIndex].cells[cellIndex];}function trimAndGetNodeText(config,node){return $.trim(getElementText(config,node));}function getParserById(name){var l=parsers.length;for(var i=0;i<l;i++){if(parsers[i].id.toLowerCase()==name.toLowerCase()){return parsers[i];}}return false;}function buildCache(table){if(table.config.debug){var cacheTime=new Date();}var totalRows=(table.tBodies[0]&&table.tBodies[0].rows.length)||0,totalCells=(table.tBodies[0].rows[0]&&table.tBodies[0].rows[0].cells.length)||0,parsers=table.config.parsers,cache={row:[],normalized:[]};for(var i=0;i<totalRows;++i){var c=$(table.tBodies[0].rows[i]),cols=[];if(c.hasClass(table.config.cssChildRow)){cache.row[cache.row.length-1]=cache.row[cache.row.length-1].add(c);continue;}cache.row.push(c);for(var j=0;j<totalCells;++j){cols.push(parsers[j].format(getElementText(table.config,c[0].cells[j]),table,c[0].cells[j]));}cols.push(cache.normalized.length);cache.normalized.push(cols);cols=null;};if(table.config.debug){benchmark("Building cache for "+totalRows+" rows:",cacheTime);}return cache;};function getElementText(config,node){var text="";if(!node)return"";if(!config.supportsTextContent)config.supportsTextContent=node.textContent||false;if(config.textExtraction=="simple"){if(config.supportsTextContent){text=node.textContent;}else{if(node.childNodes[0]&&node.childNodes[0].hasChildNodes()){text=node.childNodes[0].innerHTML;}else{text=node.innerHTML;}}}else{if(typeof(config.textExtraction)=="function"){text=config.textExtraction(node);}else{text=$(node).text();}}return text;}function appendToTable(table,cache){if(table.config.debug){var appendTime=new Date()}var c=cache,r=c.row,n=c.normalized,totalRows=n.length,checkCell=(n[0].length-1),tableBody=$(table.tBodies[0]),rows=[];for(var i=0;i<totalRows;i++){var pos=n[i][checkCell];rows.push(r[pos]);if(!table.config.appender){var l=r[pos].length;for(var j=0;j<l;j++){tableBody[0].appendChild(r[pos][j]);}}}if(table.config.appender){table.config.appender(table,rows);}rows=null;if(table.config.debug){benchmark("Rebuilt table:",appendTime);}applyWidget(table);setTimeout(function(){$(table).trigger("sortEnd");},0);};function buildHeaders(table){if(table.config.debug){var time=new Date();}var meta=($.metadata)?true:false;var header_index=computeTableHeaderCellIndexes(table);$tableHeaders=$(table.config.selectorHeaders,table).each(function(index){this.column=header_index[this.parentNode.rowIndex+"-"+this.cellIndex];this.order=formatSortingOrder(table.config.sortInitialOrder);this.count=this.order;if(checkHeaderMetadata(this)||checkHeaderOptions(table,index))this.sortDisabled=true;if(checkHeaderOptionsSortingLocked(table,index))this.order=this.lockedOrder=checkHeaderOptionsSortingLocked(table,index);if(!this.sortDisabled){var $th=$(this).addClass(table.config.cssHeader);if(table.config.onRenderHeader)table.config.onRenderHeader.apply($th);}table.config.headerList[index]=this;});if(table.config.debug){benchmark("Built headers:",time);log($tableHeaders);}return $tableHeaders;};function computeTableHeaderCellIndexes(t){var matrix=[];var lookup={};var thead=t.getElementsByTagName('THEAD')[0];var trs=thead.getElementsByTagName('TR');for(var i=0;i<trs.length;i++){var cells=trs[i].cells;for(var j=0;j<cells.length;j++){var c=cells[j];var rowIndex=c.parentNode.rowIndex;var cellId=rowIndex+"-"+c.cellIndex;var rowSpan=c.rowSpan||1;var colSpan=c.colSpan||1
var firstAvailCol;if(typeof(matrix[rowIndex])=="undefined"){matrix[rowIndex]=[];}for(var k=0;k<matrix[rowIndex].length+1;k++){if(typeof(matrix[rowIndex][k])=="undefined"){firstAvailCol=k;break;}}lookup[cellId]=firstAvailCol;for(var k=rowIndex;k<rowIndex+rowSpan;k++){if(typeof(matrix[k])=="undefined"){matrix[k]=[];}var matrixrow=matrix[k];for(var l=firstAvailCol;l<firstAvailCol+colSpan;l++){matrixrow[l]="x";}}}}return lookup;}function checkCellColSpan(table,rows,row){var arr=[],r=table.tHead.rows,c=r[row].cells;for(var i=0;i<c.length;i++){var cell=c[i];if(cell.colSpan>1){arr=arr.concat(checkCellColSpan(table,headerArr,row++));}else{if(table.tHead.length==1||(cell.rowSpan>1||!r[row+1])){arr.push(cell);}}}return arr;};function checkHeaderMetadata(cell){if(($.metadata)&&($(cell).metadata().sorter===false)){return true;};return false;}function checkHeaderOptions(table,i){if((table.config.headers[i])&&(table.config.headers[i].sorter===false)){return true;};return false;}function checkHeaderOptionsSortingLocked(table,i){if((table.config.headers[i])&&(table.config.headers[i].lockedOrder))return table.config.headers[i].lockedOrder;return false;}function applyWidget(table){var c=table.config.widgets;var l=c.length;for(var i=0;i<l;i++){getWidgetById(c[i]).format(table);}}function getWidgetById(name){var l=widgets.length;for(var i=0;i<l;i++){if(widgets[i].id.toLowerCase()==name.toLowerCase()){return widgets[i];}}};function formatSortingOrder(v){if(typeof(v)!="Number"){return(v.toLowerCase()=="desc")?1:0;}else{return(v==1)?1:0;}}function isValueInArray(v,a){var l=a.length;for(var i=0;i<l;i++){if(a[i][0]==v){return true;}}return false;}function setHeadersCss(table,$headers,list,css){$headers.removeClass(css[0]).removeClass(css[1]);var h=[];$headers.each(function(offset){if(!this.sortDisabled){h[this.column]=$(this);}});var l=list.length;for(var i=0;i<l;i++){h[list[i][0]].addClass(css[list[i][1]]);}}function fixColumnWidth(table,$headers){var c=table.config;if(c.widthFixed){var colgroup=$('<colgroup>');$("tr:first td",table.tBodies[0]).each(function(){colgroup.append($('<col>').css('width',$(this).width()));});$(table).prepend(colgroup);};}function updateHeaderSortCount(table,sortList){var c=table.config,l=sortList.length;for(var i=0;i<l;i++){var s=sortList[i],o=c.headerList[s[0]];o.count=s[1];o.count++;}}function multisort(table,sortList,cache){if(table.config.debug){var sortTime=new Date();}var dynamicExp="var sortWrapper = function(a,b) {",l=sortList.length;for(var i=0;i<l;i++){var c=sortList[i][0];var order=sortList[i][1];var s=(table.config.parsers[c].type=="text")?((order==0)?makeSortFunction("text","asc",c):makeSortFunction("text","desc",c)):((order==0)?makeSortFunction("numeric","asc",c):makeSortFunction("numeric","desc",c));var e="e"+i;dynamicExp+="var "+e+" = "+s;dynamicExp+="if("+e+") { return "+e+"; } ";dynamicExp+="else { ";}var orgOrderCol=cache.normalized[0].length-1;dynamicExp+="return a["+orgOrderCol+"]-b["+orgOrderCol+"];";for(var i=0;i<l;i++){dynamicExp+="}; ";}dynamicExp+="return 0; ";dynamicExp+="}; ";if(table.config.debug){benchmark("Evaling expression:"+dynamicExp,new Date());}eval(dynamicExp);cache.normalized.sort(sortWrapper);if(table.config.debug){benchmark("Sorting on "+sortList.toString()+" and dir "+order+" time:",sortTime);}return cache;};function makeSortFunction(type,direction,index){var a="a["+index+"]",b="b["+index+"]";if(type=='text'&&direction=='asc'){return"("+a+" == "+b+" ? 0 : ("+a+" === null ? Number.POSITIVE_INFINITY : ("+b+" === null ? Number.NEGATIVE_INFINITY : ("+a+" < "+b+") ? -1 : 1 )));";}else if(type=='text'&&direction=='desc'){return"("+a+" == "+b+" ? 0 : ("+a+" === null ? Number.POSITIVE_INFINITY : ("+b+" === null ? Number.NEGATIVE_INFINITY : ("+b+" < "+a+") ? -1 : 1 )));";}else if(type=='numeric'&&direction=='asc'){return"("+a+" === null && "+b+" === null) ? 0 :("+a+" === null ? Number.POSITIVE_INFINITY : ("+b+" === null ? Number.NEGATIVE_INFINITY : "+a+" - "+b+"));";}else if(type=='numeric'&&direction=='desc'){return"("+a+" === null && "+b+" === null) ? 0 :("+a+" === null ? Number.POSITIVE_INFINITY : ("+b+" === null ? Number.NEGATIVE_INFINITY : "+b+" - "+a+"));";}};function makeSortText(i){return"((a["+i+"] < b["+i+"]) ? -1 : ((a["+i+"] > b["+i+"]) ? 1 : 0));";};function makeSortTextDesc(i){return"((b["+i+"] < a["+i+"]) ? -1 : ((b["+i+"] > a["+i+"]) ? 1 : 0));";};function makeSortNumeric(i){return"a["+i+"]-b["+i+"];";};function makeSortNumericDesc(i){return"b["+i+"]-a["+i+"];";};function sortText(a,b){if(table.config.sortLocaleCompare)return a.localeCompare(b);return((a<b)?-1:((a>b)?1:0));};function sortTextDesc(a,b){if(table.config.sortLocaleCompare)return b.localeCompare(a);return((b<a)?-1:((b>a)?1:0));};function sortNumeric(a,b){return a-b;};function sortNumericDesc(a,b){return b-a;};function getCachedSortType(parsers,i){return parsers[i].type;};this.construct=function(settings){return this.each(function(){if(!this.tHead||!this.tBodies)return;var $this,$document,$headers,cache,config,shiftDown=0,sortOrder;this.config={};config=$.extend(this.config,$.tablesorter.defaults,settings);$this=$(this);$.data(this,"tablesorter",config);$headers=buildHeaders(this);this.config.parsers=buildParserCache(this,$headers);cache=buildCache(this);var sortCSS=[config.cssDesc,config.cssAsc];fixColumnWidth(this);$headers.click(function(e){var totalRows=($this[0].tBodies[0]&&$this[0].tBodies[0].rows.length)||0;if(!this.sortDisabled&&totalRows>0){$this.trigger("sortStart");var $cell=$(this);var i=this.column;this.order=this.count++%2;if(this.lockedOrder)this.order=this.lockedOrder;if(!e[config.sortMultiSortKey]){config.sortList=[];if(config.sortForce!=null){var a=config.sortForce;for(var j=0;j<a.length;j++){if(a[j][0]!=i){config.sortList.push(a[j]);}}}config.sortList.push([i,this.order]);}else{if(isValueInArray(i,config.sortList)){for(var j=0;j<config.sortList.length;j++){var s=config.sortList[j],o=config.headerList[s[0]];if(s[0]==i){o.count=s[1];o.count++;s[1]=o.count%2;}}}else{config.sortList.push([i,this.order]);}};setTimeout(function(){setHeadersCss($this[0],$headers,config.sortList,sortCSS);appendToTable($this[0],multisort($this[0],config.sortList,cache));},1);return false;}}).mousedown(function(){if(config.cancelSelection){this.onselectstart=function(){return false};return false;}});$this.bind("update",function(){var me=this;setTimeout(function(){me.config.parsers=buildParserCache(me,$headers);cache=buildCache(me);},1);}).bind("updateCell",function(e,cell){var config=this.config;var pos=[(cell.parentNode.rowIndex-1),cell.cellIndex];cache.normalized[pos[0]][pos[1]]=config.parsers[pos[1]].format(getElementText(config,cell),cell);}).bind("sorton",function(e,list){$(this).trigger("sortStart");config.sortList=list;var sortList=config.sortList;updateHeaderSortCount(this,sortList);setHeadersCss(this,$headers,sortList,sortCSS);appendToTable(this,multisort(this,sortList,cache));}).bind("appendCache",function(){appendToTable(this,cache);}).bind("applyWidgetId",function(e,id){getWidgetById(id).format(this);}).bind("applyWidgets",function(){applyWidget(this);});if($.metadata&&($(this).metadata()&&$(this).metadata().sortlist)){config.sortList=$(this).metadata().sortlist;}if(config.sortList.length>0){$this.trigger("sorton",[config.sortList]);}applyWidget(this);});};this.addParser=function(parser){var l=parsers.length,a=true;for(var i=0;i<l;i++){if(parsers[i].id.toLowerCase()==parser.id.toLowerCase()){a=false;}}if(a){parsers.push(parser);};};this.addWidget=function(widget){widgets.push(widget);};this.formatFloat=function(s){var i=parseFloat(s);return(isNaN(i))?0:i;};this.formatInt=function(s){var i=parseInt(s);return(isNaN(i))?0:i;};this.isDigit=function(s,config){return/^[-+]?\d*$/.test($.trim(s.replace(/[,.']/g,'')));};this.clearTableBody=function(table){if (navigator.userAgent.match("MSIE")) {function empty(){while(this.firstChild)this.removeChild(this.firstChild);}empty.apply(table.tBodies[0]);}else{table.tBodies[0].innerHTML="";}};}});$.fn.extend({tablesorter:$.tablesorter.construct});var ts=$.tablesorter;ts.addParser({id:"text",is:function(s){return true;},format:function(s){return $.trim(s.toLocaleLowerCase());},type:"text"});ts.addParser({id:"digit",is:function(s,table){var c=table.config;return $.tablesorter.isDigit(s,c);},format:function(s){return $.tablesorter.formatFloat(s);},type:"numeric"});ts.addParser({id:"currency",is:function(s){return/^[£$€?.]/.test(s);},format:function(s){return $.tablesorter.formatFloat(s.replace(new RegExp(/[£$€]/g),""));},type:"numeric"});ts.addParser({id:"ipAddress",is:function(s){return/^\d{2,3}[\.]\d{2,3}[\.]\d{2,3}[\.]\d{2,3}$/.test(s);},format:function(s){var a=s.split("."),r="",l=a.length;for(var i=0;i<l;i++){var item=a[i];if(item.length==2){r+="0"+item;}else{r+=item;}}return $.tablesorter.formatFloat(r);},type:"numeric"});ts.addParser({id:"url",is:function(s){return/^(https?|ftp|file):\/\/$/.test(s);},format:function(s){return jQuery.trim(s.replace(new RegExp(/(https?|ftp|file):\/\//),''));},type:"text"});ts.addParser({id:"isoDate",is:function(s){return/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(s);},format:function(s){return $.tablesorter.formatFloat((s!="")?new Date(s.replace(new RegExp(/-/g),"/")).getTime():"0");},type:"numeric"});ts.addParser({id:"percent",is:function(s){return/\%$/.test($.trim(s));},format:function(s){return $.tablesorter.formatFloat(s.replace(new RegExp(/%/g),""));},type:"numeric"});ts.addParser({id:"usLongDate",is:function(s){return s.match(new RegExp(/^[A-Za-z]{3,10}\.? [0-9]{1,2}, ([0-9]{4}|'?[0-9]{2}) (([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(AM|PM)))$/));},format:function(s){return $.tablesorter.formatFloat(new Date(s).getTime());},type:"numeric"});ts.addParser({id:"shortDate",is:function(s){return/\d{1,2}[\/\-]\d{1,2}[\/\-]\d{2,4}/.test(s);},format:function(s,table){var c=table.config;s=s.replace(/\-/g,"/");if(c.dateFormat=="us"){s=s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/,"$3/$1/$2");}else if(c.dateFormat=="uk"){s=s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/,"$3/$2/$1");}else if(c.dateFormat=="dd/mm/yy"||c.dateFormat=="dd-mm-yy"){s=s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{2})/,"$1/$2/$3");}return $.tablesorter.formatFloat(new Date(s).getTime());},type:"numeric"});ts.addParser({id:"time",is:function(s){return/^(([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(am|pm)))$/.test(s);},format:function(s){return $.tablesorter.formatFloat(new Date("2000/01/01 "+s).getTime());},type:"numeric"});ts.addParser({id:"metadata",is:function(s){return false;},format:function(s,table,cell){var c=table.config,p=(!c.parserMetadataName)?'sortValue':c.parserMetadataName;return $(cell).metadata()[p];},type:"numeric"});ts.addWidget({id:"zebra",format:function(table){if(table.config.debug){var time=new Date();}var $tr,row=-1,odd;$("tr:visible",table.tBodies[0]).each(function(i){$tr=$(this);if(!$tr.hasClass(table.config.cssChildRow))row++;odd=(row%2==0);$tr.removeClass(table.config.widgetZebra.css[odd?0:1]).addClass(table.config.widgetZebra.css[odd?1:0])});if(table.config.debug){$.tablesorter.benchmark("Applying Zebra widget",time);}}});})(jQuery);
(function($) {
	$.extend({
		tablesorterPager: new function() {

			function updatePageDisplay(c) {
				$(c.cssPageDisplay,c.container).val((c.page+1) + c.seperator + c.totalPages);
			}

			function moveToPage(table) {
				var c = table.config;
				if(c.page < 0 || c.page > (c.totalPages-1)) {
					c.page = 0;
				}

				renderTable(table,c.rowsCopy);
			}

			function renderTable(table,rows) {

				var c = table.config;
				var l = rows.length;
				var s = (c.page * c.size);
				var e = (s + c.size);
				if(e > rows.length ) {
					e = rows.length;
				}


				var tableBody = $(table.tBodies[0]);

				// clear the table body

				$.tablesorter.clearTableBody(table);

				for(var i = s; i < e; i++) {

					var o = rows[i];
					var l = o.length;
					for(var j=0; j < l; j++) {

						tableBody[0].appendChild(o[j]);

					}
				}

				$(table).trigger("applyWidgets");

				if( c.page >= c.totalPages ) {
        			moveToLastPage(table);
				}

				updatePageDisplay(c);
			}

			this.appender = function(table,rows) {

				var c = table.config;

				c.rowsCopy = rows;
				c.totalRows = rows.length;
				c.totalPages = Math.ceil(c.totalRows / c.size);

				renderTable(table,rows);
			};

			this.defaults = {
				size: 5,
				offset: 0,
				page: 0,
				totalRows: 0,
				totalPages: 0,
				container: null,
				seperator: "/",
				appender: this.appender
			};

			this.construct = function(settings) {

				return this.each(function() {

                    config = $.extend(this.config, $.tablesorterPager.defaults, settings);

					var table = this, pager = config.container;

                    var curr = 0;
                    var numPages = Math.ceil($(table).find("tbody tr").length/config.size);
                    if (numPages > 1) {
                        while(numPages > curr){
                            $('<li><a href="#" class="page_link">'+(curr+1)+'</a></li>').appendTo(pager);
                            curr++;
                        }

                        $(this).trigger("appendCache");
                        $(pager).find('.page_link:first').addClass('active');
                        $(pager).find(".page_link").click(function() {
                            var clickedPage = $(this).html().valueOf()-1;
                            table.config.page = clickedPage;
                            moveToPage(table);
                            pager.find(".page_link").removeClass("active");
                            pager.find(".page_link").eq(clickedPage).addClass("active");
                            return false;
                        });
					}
				});
			};

		}
	});
	// extend plugin scope
	$.fn.extend({
        tablesorterPager: $.tablesorterPager.construct
	});

})(jQuery);
var ASM = ASM || {}; // make sure ASM is available
var sessionSec;
var counter;
var carts;

function personifyForm() {
    if ($('#_asmLogin').length) {
        var loginUser = $("#asmLoginForm input[name='username']");
        var min = 1;
        if (loginUser.val().length >= min) {
            loginUser.parent().addClass('checked');
        }
    }

    $("#asmLoginForm input[name='username'], #asmLoginForm input[name='password']").keyup(function () {
        var min = 1;
        var parentNode = $(this.parentNode);

        if (this.value.length >= (min)) {
            parentNode.addClass('checked');
            checkSignInButton(parentNode);
        } else {
            parentNode.removeClass('checked');
            checkSignInButton(parentNode);
        }
    });

    $("input[name='customerName']").keyup(function (e) {
        $("input[name='customerId']").val("");
        validateNewAccount(this);
        $(this).removeData("hover");
        removeAsmHover();
        toggleBind(false);
        toggleStartSessionButton(this, false);

        if ($(this).val().length < 3) {
            toggleCreateAccount(false);
        }
    });

    $("#_asmPersonifyForm input[name='cartId']").keyup(function () {
        formValidate(this, 8, true, 8);
        if (isErrorDisplayed()) {
            $("input[name='cartId']").removeClass('ASM-input-error');
            if ($('.ASM_alert')) {
                $('.ASM_alert').remove();
            }
        }
    });

    $("#_asmPersonifyForm input[name='customerName']").keyup(function () {
        if (isErrorDisplayed()) {
            $("input[name='customerName']").removeClass('ASM-input-error');
            if ($('.ASM_alert')) {
                $('.ASM_alert').remove();
            }
            if ($(this).val() === "") {
                $("input[name='cartId']").removeClass('ASM-input-error');
                toggleStartSessionButton($("input[name='cartId']"), true);
                $("input[name='customerId']").val("");
            }
        }
        if ($(this).val() === "") {
            $("input[name='cartId']").val("");
            $("#asmAutoCompleteCartId").empty();
        }
    });

    $("#_asmPersonifyForm input[name='cartId']").blur(function () {
        var regEx = /^\s+$/;
        if (regEx.test($(this).val())) {
            $(this).val('');
            formValidate(this, 8, true, 8);
        }
    });

    $("#_asmBindForm input[name='cartId']").keyup(function (e) {
        checkCartIdFieldAndToggleBind(this);
    });

    $("#_asmBindForm input[name='cartId']").bind('paste', function (e) {
        var inputField = this;
        setTimeout(function () {
            checkCartIdFieldAndToggleBind(inputField);
        }, 100);
    });
}

function validateForm() {
    if ($("#sessionTimer").length && $('#asmLogoutForm').length) {
        startTimer();
    }

    if ($("#resetButton").length) {
        $("#resetButton").click(function () {
            resetSession();
        });
    }

    /* for <=IE9 */
    if (placeholderNotAvailable()) {
        $('[placeholder]').focus(function () {
            var input = $(this);
            if (input.val() === input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function () {
            var input = $(this);
            if (input.val() === '' || input.val() === input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur();
    }

    $('[placeholder]').blur(function () {
        var input = $(this);
        if ((input.val() === '') && (input.attr("name"))) {
            toggleBind(false);
        }
    });

    if ($('.ASM_alert_cart').length) {
        $("input[name='cartId']").addClass('ASM-input-error');
    }

    if ($('.ASM_alert_customer').length) {
        $("input[name='customerName']").addClass('ASM-input-error');
    }

    if ($('.ASM_alert_cred').length) {
        $("input[name='username']").addClass('ASM-input-error');
        $("input[name='password']").addClass('ASM-input-error');
    }

    if ($('.ASM_alert_create_new').length) {
        toggleCreateAccount(true);
    }
}

function addASMHandlers() {

    revertAutocompleteNormalize();
    removeAsmAlert(3000);
    addCloseBtnHandler();
    addASMFormHandler();
    addHideBtnHandler();
    addCustomerListBtnHandler();
    customerListModalHandler();
    addCustomer360Handler();
    addGenericCustomer360Handler();
    validateForm();
    personifyForm();

    $("#_asmPersonifyForm input[name='customerName'], input[name='customerId']").hover(function () {
            var item = ($(this).attr('data-hover')) ? jQuery.parseJSON($(this).attr('data-hover')) : $(this).data("hover");
            var disabled = ($(this).attr('data-hover')) ? "disabled" : "";

            if (!(item === null || item === undefined)) {
                $(this)
                    .after(
                        $('<div>')
                            .attr('id', 'asmHover')
                            .addClass(disabled)
                            .append(
                                $('<span>').addClass('name').text(item.name),
                                $('<span>').addClass('email').text(item.email),
                                $('<span>').addClass('date').text(item.date),
                                $('<span>').addClass('card').text(item.card)
                            )
                    );
            }
        }, function () {
            removeAsmHover();
        }
    );

    $("#_asmPersonifyForm input[name='cartId']").autocomplete({
        source: function (request, response) {
            response(carts);
        },
        appendTo: "#asmAutoCompleteCartId",
        autoFocus: true,
        minLength: 0,
        select: function (event, ui) {
            if (ui.item.value === "") {
                event.preventDefault();
            }
            toggleStartSessionButton(this, true);
        }
    });

    $("#_asmPersonifyForm input[name='cartId']").on('click, focus', function () {
        $("#_asmPersonifyForm input[name='cartId']").autocomplete('search', '');
    });

    if ($("input[name='customerName']").length > 0) {
        $("input[name='customerName']").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: ACC.config.encodedContextPath + "/assisted-service/autocomplete",
                    dataType: "json",
                    data: {
                        customerId: request.term
                    },
                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                email: item.email,
                                date: item.date,
                                card: item.card,
                                value: item.value,
                                carts: item.carts
                            };
                        }));
                    }
                });
            },
            minLength: 3,
            appendTo: "#asmAutoComplete",
            select: function (event, ui) {
                if (ui.item.value === null) {
                    event.preventDefault();
                    return;
                }
                // Stop the propagation of this event to prevent the event handler of keydown from processing the enter when an item was selected by that enter
                event.originalEvent.originalEvent.stopPropagation();
                toggleStartSessionButton(this, true);
                $(this).data('hover', {
                    name: ui.item.value,
                    email: ui.item.email,
                    card: ui.item.card,
                    date: ui.item.date
                });

                /* insert item.value in customerId hidden field */
                $("input[name='customerId']").val(ui.item.email);

                carts = ui.item.carts;

                function handleCartIdInput() {
                    if (carts !== null) {
                        if (carts.length === 1) {
                            $("input[name='cartId']").val(carts[0]);
                        } else {
                            $("input[name='cartId']").autocomplete('search', '');
                            $("input[name='cartId']").focus();
                        }
                    } else {
                        carts = [{label: "No Existing Carts", value: ""}];
                        $("input[name='cartId']").autocomplete('search', '');
                        $("input[name='cartId']").focus();
                    }
                }

                if ($("input[name='cartId']").attr("orig_value") === null) {
                    $("input[name='cartId']").val('');
                    handleCartIdInput();
                }
                checkCartIdFieldAndToggleBind($("input[name='cartId']")[0]);

            }
        }).data("ui-autocomplete")._renderItem = function (ul, item) {
            if (item.value === null) {
                toggleCreateAccount(true);
                return $("<li></li>").data("item.autocomplete", item).append($('<span class=noresult>').text(ASM_MESSAGES.customerIdNotFound))
                    .appendTo(ul);
            } else {
                toggleCreateAccount(false);
            }

            return $("<li></li>").data("item.autocomplete", item).append($('<span>').addClass('name').text(item.value),
                $('<span>').addClass('email').text(item.email),
                $('<span>').addClass('date').text(item.date),
                $('<span>').addClass('card').text(item.card)).appendTo(ul);
        };
    }

    if ($("#_asmBindForm").length) {
        var customerId = $("input[name='customerName']").attr('readonly');

        if (customerId === "readonly") {
            $(".ASM_icon-chain").removeClass('invisible').addClass('ASM_chain-bind');

            if ($("#_asmBindForm input[name='customerId']").val() !== undefined && $("#_asmBindForm input[name='customerId']").val() !== "") {
                $(".js-customer360").removeAttr('disabled');
            }
        }
    }
    if ($(".add_to_cart_form").length && $("#_asm input[name='cartId']").val() === "") {
        $(".add_to_cart_form").submit(function (event) {
            setTimeout(function () {
                var url = ACC.config.encodedContextPath + "/assisted-service/add-to-cart";
                $.post(url, function (data) {
                    $("#_asm").replaceWith(data);
                    addASMHandlers();
                });
            }, 400);
        });
    }
    enableAsmPanelButtons();
}

$(document).ready(function () {
    var ASM = ASM || {}; // make sure ACC is available
    addASMHandlers();

    $(document).on("click", ".js-select-store-label", function (e) {
        $("#colorbox .js-pickup-component").addClass("show-store");
        colorboxResize();
    });

    $(document).on("click", ".js-asm-store-finder-details-back", function (e) {
        $("#colorbox .js-pickup-component").removeClass("show-store");
    });
});

function addASMFormHandler() {
    if (($) && ($(".asmForm").length)) {
        $(".asmForm").each(function () {
            $(this).submit(function () {
                $(this).find('[placeholder]').each(function () {
                    var input = $(this);
                    if (input.val() === input.attr('placeholder')) {
                        input.val('');
                    }
                });
                $.ajax({
                    type: "POST",
                    url: $(this).attr('action'),
                    data: $(this).serialize(),
                    success: function (data) {
                        $("#_asm").replaceWith(data);
                        addASMHandlers();
                    }
                });
                return false;
            });
        });
    }
}

function addCloseBtnHandler() {
    $("#_asm .closeBtn").click(function () {
        $("#_asm").remove();
        var url = ACC.config.encodedContextPath + "/assisted-service/quit";
        $.post(url, function (data) {
            var params = new URLSearchParams(window.location.search);
            params.delete('asm');
            window.location.search = params;
        });
    });
}

function addHideBtnHandler() {
    $("#_asm .ASM_control_collapse").click(function () {
        $("#_asm").toggleClass("ASM-collapsed");
    });
}

function startTimer() {
    sessionSec = timer;
    clearInterval(counter);
    counter = setInterval(timerFunc, 1000);
}

function timerFunc() {
    if (sessionSec <= 0) {
        clearInterval(counter);
        finishASMagentSession();
        return;
    }
    sessionSec = sessionSec - 1;
    var min = Math.floor(sessionSec / 60);
    var sec = sessionSec % 60;
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    $("#sessionTimer .ASM_timer_count").html(min + ":" + sec);
}

function resetSession() {
    var request = $.ajax({
        url: ACC.config.encodedContextPath + "/assisted-service/resetSession",
        type: "POST"
    });
    request.done(function (msg) {
        sessionSec = timer + 1;
    });
    request.fail(function (jqXHR, textStatus) {
        $('#errors').empty();
        $('#errors').append("Request failed: " + textStatus);
    });
}

function finishASMagentSession() {
    $.ajax({
        url: ACC.config.encodedContextPath + "/assisted-service/logoutasm",
        type: "POST",
        success: function (data) {
            $("#_asm").replaceWith(data);
            addASMHandlers();
        }
    });
}

function isStartEmulateButtonPresent() {
    return $(".ASM-btn-start-session").length === 1;
}

function enableAsmPanelButtons() {
    $('div[id="_asm"] button').not(".js-customer360, .ASM-btn-start-session, .ASM-btn-create-account, .ASM-btn-login").removeAttr('disabled');
    if (isStartEmulateButtonPresent()) {
        if ($("#_asmPersonifyForm input[name='customerId']").val() !== "") {
            $("#_asmPersonifyForm input[name='customerId']").parent().addClass("checked");
        }
        formValidate($("#_asmPersonifyForm input[name='cartId']")[0], 8, true, 8);
    }
}

function placeholderNotAvailable() {
    var i = document.createElement('input');
    return !('placeholder' in i);
}

function removeAsmHover() {
    $('#asmHover').remove();
}

function toggleCreateAccount(activate) {
    var bindIcon = $(".ASM_icon-chain");
    var createButton = $("#_asmCreateAccountForm button.ASM-btn-create-account[type='submit']");
    if (activate) {
        createButton.removeClass('hidden');
        bindIcon.removeClass('invisible');
    } else {
        createButton.addClass('hidden');
        bindIcon.addClass('invisible');
    }
}

function toggleActivationState(button, activate) {
    if (activate) {
        button.removeAttr('disabled');
    } else {
        button.attr('disabled', '');
    }
}

function checkSignInButton(el) {
    var signInBtn = $("#asmLoginForm button[type='submit']");
    var checkSum = $(el).parent().find('.checked').length;
    if (checkSum > 1) {
        toggleActivationState(signInBtn, true);
    } else {
        toggleActivationState(signInBtn, false);
    }
}

function checkStartSessionButton(el) {
    toggleStartSessionButton(el, false);
    var checkSum = $(el.parentNode).siblings('.checked').length;
    if (checkSum > 0) {
        toggleActivationState($("button.ASM-btn-start-session"), true);
    }
}

function checkCartIdFieldAndToggleBind(cartIdField) {
    function checkCustomerDataTyped() {
        return !$(cartIdField).hasClass('placeholder')
            && ($("input[name='customerName']").val().length > 0)
            && ($("input[name='customerId']").val().length > 0);
    }

    function checkCartIdTyped() {
        return !isNaN(cartIdField.value)
            && (cartIdField.value.length === 8);
    }

    if (checkCustomerDataTyped() && checkCartIdTyped()) {
        $("#_asmBindForm button[type='submit']").removeClass('hidden');
        $(".ASM_icon-chain").removeClass('invisible');
        return;
    }
    $("#_asmBindForm button[type='submit']").addClass('hidden');
    $(".ASM_icon-chain").addClass('invisible');
}

function toggleBind(activate) {
    if ($("#_asmBindForm").length) {
        var bindIcon = $(".ASM_icon-chain");
        var bindButton = $("#_asmBindForm button.ASM-btn-bind-cart[type='submit']");
        if (activate) {
            bindButton.removeClass('hidden');
            bindIcon.removeClass('invisible');
        } else {
            bindButton.addClass('hidden');
            if ($('.ASM-btn-create-account').hasClass('hidden')) {
                bindIcon.addClass('invisible');
            }
        }
    }
}

function toggleStartSessionButton(el, activate) {
    var checkedItem = $(el).parent();
    var button = $("button.ASM-btn-start-session");
    if (activate) {
        button.removeAttr('disabled');
        checkedItem.addClass("checked");
    } else {
        button.attr('disabled', '');
        checkedItem.removeClass("checked");
    }
}

function formValidate(el, min, number, max) {
    if (!$(el).hasClass('placeholder')) {
        if ($(el).hasClass("ASM-input-error")) {
            toggleStartSessionButton(el, false);
            return false;
        }
        if ((number !== false) && isNaN(el.value)) {
            toggleStartSessionButton(el, false);
            return false;
        }
        if (el.value.length >= (min)) {
            toggleStartSessionButton(el, true);
            if (max !== undefined && el.value.length > (max)) {
                toggleStartSessionButton(el, false);
            }
        } else if (el.value.length === 0) {
            checkStartSessionButton(el);
        } else {
            toggleStartSessionButton(el, false);
            return false;
        }
        return true;
    }
    return false;
}

function validateEmail(mailAddress) {
    return ($('<input>').attr({
        type: 'email',
        required: 'required'
    }).val(mailAddress))[0].checkValidity() && (mailAddress.indexOf(".") > 0);
}

function validateName(name) {
    var split = name.trim().split(/\s+/);
    return !isBlank(split[0]) && !isBlank(split[1]);
}

function isBlank(str) {
    return (!str || 0 === str.length);
}

function validateNewAccount(el) {
    var createAccountButton = $("#_asmCreateAccountForm button.ASM-btn-create-account[type='submit']");
    var customerValues = el.value.split(', ');
    var IdInput = $("#_asmCreateAccountForm input[name='customerId']");
    var NameInput = $("#_asmCreateAccountForm input[name='customerName']");

    if (customerValues.length > 1) {
        var validName = validateName(customerValues[0]);
        var validMail = validateEmail(customerValues[1]);
        if (validName && validMail) {
            toggleActivationState(createAccountButton, true);
            /* fill hidden input fields */
            IdInput.val(customerValues[1].replace(/^\s\s*/, '').replace(/\s\s*$/, ''));
            NameInput.val(customerValues[0]);
        } else {
            /* no valid customer values */
            toggleActivationState(createAccountButton, false);
            return false;
        }
    } else {
        /* too less customer values */
        toggleActivationState(createAccountButton, false);
        return false;
    }
}

function revertAutocompleteNormalize() {
    /* After http://bugs.jqueryui.com/ticket/9762 there was a change when for empty value label is placed instead.
     * But we want to send empty values for NO_FOUND case */
    $.ui.autocomplete.prototype._normalize = function (a) {
        if (a.length && a[0].label && a[0].value) {
            return a;
        }
        return $.map(a, function (b) {
            if (typeof b === "string") {
                return {label: b, value: b};
            }
            return $.extend({label: b.label || b.value, value: b.value || b.label}, b);
        });
    };
}

function isErrorDisplayed() {
    return $('.ASM_alert').length;
}


function addCustomerListBtnHandler() {
    $(".js-customer-list-btn").removeClass('disabled');
    $(document).on("click", ".js-customer-list-btn", function (e) {
        e.preventDefault();
        populateCustomerListModal($(this).data('actionurl'), '.js-customer-list-modal-content', addCustomerListSelect);
    });
}

function openCustomer360Colorbox(colorboxTarget) {
    colorboxTarget.colorbox({
        inline: 'true',
        className: 'ASM_customer360-modal',
        width: "100%",
        maxWidth: "1200px",
        close: '<span class="ASM_icon ASM_icon-close"></span>',
        transition: 'none',
        scrolling: false,
        opacity: 0.7,
        top: 10,
        onOpen: function () {
            customer360Callback();
            $(window).on("resize", colorboxResize);
        },
        onClosed: function () {
            $(window).off("resize", colorboxResize);
        }
    });
}

function colorboxResize() {
    $.colorbox.resize();
}

function addCustomer360Handler() {
    openCustomer360Colorbox($(".js-customer360"));
}

function addGenericCustomer360Handler() {
    if ($("#enable360View").val()) {
        openCustomer360Colorbox($);
    }
}

function customer360Callback() {
    var loader = "<div class='loader'>Loading..</div>";
    $("#cboxLoadedContent").html(loader).show();
    $.ajax({
        url: ACC.config.encodedContextPath + "/assisted-service-aif/customer360",
        type: "GET",
        success: function (data) {
            $("#cboxLoadedContent").append(data);
            $.colorbox.resize();
        },
        error: function (xht, textStatus, ex) {
            console.debug("Failed to load Customer 360. %s", ex);
            document.location.reload();
        }
    });
}

function loadCustomer360Fragment(params) {
    return $.ajax({
        url: ACC.config.encodedContextPath + "/assisted-service-aif/customer360Fragment",
        timeout: params.timeout,
        type: params.method,
        data: params,
        success: function (data) {
            $("#" + params.fragmentId).html(data);
            $.colorbox.resize();

        },
        error: function (xht, textStatus, ex) {
            if (textStatus === 'timeout') {
                $("#" + params.fragmentId).html("Widget timeout!");
                //do something. Try again perhaps?
            } else {
                console.debug("Failed to get widget data! %s", ex);
                $("#" + params.fragmentId).html("Failed to get widget data!");
            }
        }
    });
}

function asmAifSectionClickHandler() {
    $(document).on("click", ".asm__customer360__menu li", function (e) {
        e.preventDefault();
        if (!$(this).hasClass('nav-tabs-mobile-caret')) {
            aifSelectSection($(this).index());
        }
    });
}

function aifSelectLastSection() {
    var index = 0;
    if (sessionStorage.getItem("lastSection")) {
        var lastSection = JSON.parse(sessionStorage.getItem("lastSection"));
        if (getCurrentEmulatedCustomerId() === lastSection.userId) {
            index = lastSection.sectionId;
        }
    }
    $($(".asm__customer360__menu li[role='presentation']")[index]).addClass("active");
    aifSelectSection(index);
}

function aifSelectSection(index) {
    $("#sectionPlaceholder").hide();
    $("#longLoadExample").show();
    var sectionId = $(".asm__customer360__menu li").get(index).getAttribute("value");
    sessionStorage.setItem("lastSection", JSON.stringify({userId: getCurrentEmulatedCustomerId(), sectionId: index}));
    $.ajax({
        url: ACC.config.encodedContextPath + "/assisted-service-aif/customer360section?sectionId=" + sectionId,
        type: "GET",
        success: function (data) {
            $("#sectionPlaceholder").html(data);
            $("#longLoadExample").hide();
            $("#sectionPlaceholder").show();
            $.colorbox.resize();
        }
    });
    resetSession();
}

function getCurrentEmulatedCustomerId() {
    if ($("#_asmBindForm input[name='customerId']").length) {
        return $("#_asmBindForm input[name='customerId']").val();
    }
    return "anonymous";
}

function getCustomerListSearchUrl() {
    var targetUrl = $(".js-customer-list-sorting").data('sort-url');
    targetUrl += $(".ASM_customer-list-modal .sort-refine-bar .form-control").val();
    var query = $("#ASM_customer-list-queryInput").val();
    var uriEncodedquery = encodeURIComponent(query);
    targetUrl += '&query=' + uriEncodedquery;
    return targetUrl;
}

function customerListModalHandler() {
    $(document).on("click", ".ASM_customer-list-modal .pagination a", function (e) {
        e.preventDefault();
        populateCustomerListModal($(this).attr('href'), ".asm-account-section", replaceCustomerListTable);
    });

    $(document).on("click", "#ASM_customer-list-sortOptions .sortOption", function (e) {
        e.preventDefault();
        var selectedOption = $(this).data('value');
        var previouslySelectedOption = $(".ASM_customer-list-modal .sort-refine-bar .form-control").val();
        if (selectedOption !== previouslySelectedOption) {
            $(".ASM_customer-list-modal .sort-refine-bar .form-control").val(selectedOption);
            var targetUrl = getCustomerListSearchUrl();
            populateCustomerListModal(targetUrl, ".asm-account-section", replaceCustomerListTable);
        }
    });

    $(document).on("keypress", "#ASM_customer-list-queryInput", function (event) {
        if (event.keyCode === 13) {
            $("#ASM_customer-list-searchButton").click();
            return false;
        } else {
            return true;
        }
    });

    $(document).on("click", "#ASM_customer-list-searchButton", function (e) {
        e.preventDefault();
        var targetUrl = getCustomerListSearchUrl();
        populateCustomerListModal(targetUrl, ".asm-account-section", replaceCustomerListTable);
    });

    $(document).on("change", ".ASM_customer-list-modal .sort-refine-bar .form-control", function (e) {
        e.preventDefault();

        var targetUrl = getCustomerListSearchUrl();
        populateCustomerListModal(targetUrl, ".asm-account-section", replaceCustomerListTable);
    });

    $(document).on("change", ".js-customer-list-select", function (e) {
        e.preventDefault();
        var targetUrl = $(this).data('search-url');
        targetUrl += $(this).val();
        var request = populateCustomerListModal(targetUrl, ".asm-account-section", replaceCustomerListTable);
        request.done(function () {
            $.colorbox.resize();
        });
    });
}

function addCustomerListSelect(componentToUpdate, data) {
    var selector = $(data).find('.js-customer-list-select');
    $(componentToUpdate).html(data);
    var searchUrl = $(data).find('.js-customer-list-select').data('search-url');
    if (selector[0].options.length > 0) {
        searchUrl += selector[0].options[0].value;
    }

    var request = populateCustomerListModal(searchUrl, componentToUpdate, appendCustomerListTable);
    request.done(function () {
        ACC.colorbox.open("", {
            href: ".js-customer-list-modal-content",
            inline: true,
            className: 'ASM_customer-list-modal',
            width: "100%",
            maxWidth: "1200px",
            close: '<span class="ASM_icon ASM_icon-close"></span>',
            transition: 'none',
            scrolling: false,
            opacity: 0.7,
            top: 10,
            onOpen: function () {
                $(window).on("resize", colorboxResize);
            },
            onClosed: function () {
                $(window).off("resize", colorboxResize);
            }
        });
    });
}

function appendCustomerListTable(componentToUpdate, data) {
    $(componentToUpdate).append(data);
}

function replaceCustomerListTable(componentToUpdate, data) {
    $(componentToUpdate).html(data);
}

function populateCustomerListModal(targetUrl, componentToUpdate, callFunction) {
    var method = "GET";
    return $.ajax({
        url: targetUrl,
        type: method,
        success: function (data) {
            callFunction(componentToUpdate, data);
        },
        error: function (xht, textStatus, ex) {
            console.debug("Failed to get customer list. %s", ex);
            document.location.reload();
        }
    });
}

function getAifTablePageSize() {
    var pagesNumber = 5; // number
    if ($(window).width() < 668) {
        pagesNumber = 10;
    }
    return pagesNumber;
}

function copyToClipBoard(text) {
    $("#asmCopyHoldtext").val(text);
    $("#asmCopyHoldtext").show();
    $("#asmCopyHoldtext").select();
    try {
        return document.execCommand("copy");
    } catch (ex) {
        console.debug("Copy to clipboard failed.", ex);
        return false;
    } finally {
        $("#asmCopyHoldtext").hide();
    }
}

function addRatesTableSorterParser() {
    $.tablesorter.addParser({
        // set a unique id
        id: 'rates',
        is: function (s) {
            // return false so this parser is not auto detected
            return false;
        },
        format: function (s, table, cell) {
            return Math.floor($(cell).attr('data-text') * 10);
        },
        type: 'numeric'
    });
}

function removeAsmAlert(delay) {
    setTimeout(function () {
        $(".ASM_alert").fadeOut("slow");
    }, delay);
}

ACC.assistedservicestorefront = {

    buildArrayValues: function (variableArray, value) {
        variableArray.push(value);
        return variableArray;
    }

};
// collapsible
$(function () {
    if ($(".js-ASM-collapseBtn").length > 0) {
        var onDragging = false;

        var readASMcollapseCookie = function () {
            var nameEQ = "ASMcollapseBtn=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) === ' ') {
                    c = c.substring(1, c.length);
                }
                if (c.indexOf(nameEQ) === 0) {
                    return jQuery.parseJSON(decodeURIComponent(c.substring(nameEQ.length, c.length)));
                }
            }
            return null;
        };

        //get saved values from cookie
        var startPosition = readASMcollapseCookie();
        if (startPosition) {
            //set position of collapseBtn
            $(".js-ASM-collapseBtn").css("left", startPosition.position + "%");
            if (startPosition.state) {
                $("#_asm").addClass("asm-collapsed");
            }
        }

        $("#_asm").show();

        var saveCollapseBtn = function () {
            var pos = $(".js-ASM-collapseBtn").offset().left;
            var parentWidth = $(".js-ASM-collapseBtn-wrapper").width();
            var obj = {
                position: pos / (parentWidth / 100),
                state: $("#_asm").hasClass("asm-collapsed")
            };
            document.cookie = "ASMcollapseBtn=" + encodeURIComponent(JSON.stringify(obj)) + "; path=/";
        };

        //init drag of the button
        $(".js-ASM-collapseBtn").draggable({
            cancel: '.no-drag',
            axis: "x",
            containment: ".collapseBtn-wrapper",
            delay: 300,
            distance: 10,
            opacity: 0.8,
            start: function () {
                onDragging = true;
                $(".js-ASM-collapseBtn-wrapper").addClass("active");

            },
            stop: function () {
                onDragging = false;
                $(".js-ASM-collapseBtn-wrapper").removeClass("active");
                //save values to cookie
                saveCollapseBtn();
            }
        });

        //bind event for toogle the asm panel
        $(document).on("mouseup", ".js-ASM-collapseBtn", function () {
            if (!onDragging) {
                if ($("#_asm").hasClass("asm-collapsed")) {
                    $("#_asm").removeClass("asm-collapsed");
                } else {
                    $("#_asm").addClass("asm-collapsed");
                }
                //save values to cookie
                saveCollapseBtn();
            }
        });

    }
});

// dropdown
$(function () {
    $(document).on("click", ".js-dropdown", function (e) {
        e.preventDefault();
        var $e = $(this).parent();
        if ($e.hasClass("open")) {
            $e.removeClass("open");
        } else {
            $e.addClass("open");
        }
    });
    $(document).on("click", ".js-customer-360-tab", function (e) {
        e.preventDefault();
        $(this).parent().addClass("active").siblings().removeClass("active");
    });
});

/*
 * Copyright (c) 2024 SAP SE or an SAP affiliate company. All rights reserved.
 */

ASM.storefinder = {
	storeData: "",
	storeId: "",
	originalStore: "",
	coords: {},
	storeSearchData: {},
	originAddress: "",

	autoLoad: function (data) {
		originAddress = data;
		ASM.storefinder.init();
	},

	createListItemHtml: function (data, id) {

		let $rdioEl = $("<input>")
			.addClass("js-asm-store-finder-input")
			.attr("type", "radio")
			.attr("name", "storeNamePost")
			.attr("id", "asm-store-filder-entry-" + id)
			.attr("data-id", id)
			.val(data.displayName)
		let $spanInfo = $("<span>")
			.addClass("entry__info")
			.append($("<span>").addClass("entry__name").text(data.displayName))
			.append(
			$("<span>")
			.addClass("entry__address")
			.text(data.line1 + " " + data.line2)
			)
			.append($("<span>").addClass("entry__city").text(data.town));
		let $spanDistance = $("<span>")
			.addClass("entry__distance")
			.append($("<span>").text(data.formattedDistance));
		let $label = $("<label>")
			.addClass("js-select-store-label")
			.attr("for", "asm-store-filder-entry-" + id)
			.append($spanInfo)
			.append($spanDistance);

		return $("<li>").addClass("asm__list__entry").append($rdioEl).append($label);
	},

	refreshNavigation: function () {
		var storeData = ASM.storefinder.storeData;
		let $storeList = $(".js-asm-store-finder-navigation-list");
		$storeList.empty();

		if (storeData) {
			for (i = 0; i < storeData["data"].length; i++) {
				$storeList.append(ASM.storefinder.createListItemHtml(storeData["data"][i], i));
			}


			// select the first store
			var firstInput = $(".js-asm-store-finder-input")[0];
			$(firstInput).click();
		}

		var page = ASM.storefinder.storeSearchData.page;
		var to = ((page * 10 + 10) > storeData.total) ? storeData.total : page * 10 + 10;

		$(".js-asm-store-finder-pager-item-from").text(page * 10 + 1);
		$(".js-asm-store-finder-pager-item-to").text(to);
		$(".js-asm-store-finder-pager-item-all").text(storeData.total);
		$(".js-asm-store-finder").removeClass("show-store");

		if (!storeData || !storeData.total || storeData.total === 0) {
			$(".store__finder--panel").hide();
			$(".js-asm-store-finder-pager-page-info").hide();
			$(".asm_store__finder--pagination-footer").hide();
		}
	},


	bindPagination: function () {
		$(document).on("click", ".js-asm-store-finder-pager-prev", function (e) {
			e.preventDefault();
			var page = ASM.storefinder.storeSearchData.page;
			ASM.storefinder.getStoreData(page - 1);
			checkStatus(page - 1);
		});

		$(document).on("click", ".js-asm-store-finder-pager-next", function (e) {
			e.preventDefault();
			var page = ASM.storefinder.storeSearchData.page;
			ASM.storefinder.getStoreData(page + 1);
			checkStatus(page + 1);
		});

		function checkStatus(page) {
			if (page === 0) {
				$(".js-asm-store-finder-pager-prev").attr("disabled", "disabled");
			} else {
				$(".js-asm-store-finder-pager-prev").removeAttr("disabled");
			}

			if (page === Math.floor(ASM.storefinder.storeData.total / 10)) {
				$(".js-asm-store-finder-pager-next").attr("disabled", "disabled");
			} else {
				$(".js-asm-store-finder-pager-next").removeAttr("disabled");
			}
		}

	},

	bindStoreChange: function () {
		$(document).on("change", ".js-asm-store-finder-input", function (e) {
			e.preventDefault();



			storeData = ASM.storefinder.storeData["data"];

			var storeId = $(this).data("id");

			var $ele = $(".js-asm-store-finder-details");



			$.each(storeData[storeId], function (key, value) {
				if (key === "image") {
					$ele.find(".js-asm-store-image").empty();
					if (value !== "") {
						$ele.find(".js-asm-store-image").append($("<img>").attr("src", value).attr("alt", ""));
					}
				} else if (key === "productcode") {
					$ele.find(".js-asm-store-productcode").val(value);
				}
				else if (key === "openings") {
					var $oele = $ele.find(".js-asm-store-" + key);
					$oele.empty();
					if (value !== "") {
						$.each(value, function (key2, value2) {
							$oele.append($("<dt>").text(key2));
							$oele.append($("<dd>").text(value2));
						});
					}
				}
				else if (key === "features") {
					let $features = $ele.find(".js-asm-store-" + key);
					$features.empty();
					$.each(value,function(key2,value2){
						$features.append($("<li>").text(value2));
					});
				}
				else {
					if (value !== "") {
						$ele.find(".js-asm-store-" + key).text(value);
					} else {
						$ele.find(".js-asm-store-" + key).text('');
					}
				}

			});

			ASM.storefinder.storeId = storeData[storeId];
			ASM.storefinder.initGoogleMap();

		});
	},



	initGoogleMap: function () {

		if ($(".js-asm-store-finder-map").length > 0) {
			ACC.global.addGoogleMapsApi("ASM.storefinder.loadGoogleMap");
		}
	},

	loadGoogleMap: function () {

		storeInformation = ASM.storefinder.storeId;

		if ($(".js-asm-store-finder-map").length > 0) {
			$(".js-asm-store-finder-map").attr("id", "asm-store-finder-map");
			var centerPoint = new google.maps.LatLng(storeInformation["latitude"], storeInformation["longitude"]);

			var mapOptions = {
				zoom: 16,
				zoomControl: true,
				panControl: true,
				streetViewControl: false,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				center: centerPoint
			};

			var map = new google.maps.Map(document.getElementById("asm-store-finder-map"), mapOptions);

			// Prevent the store info has been stored, when the first page is loaded, which is needed only for the driving instructions.
			if (ASM.storefinder.originalStore === "") {
				ASM.storefinder.originalStore = data["data"][0];
			}

			//Driving Options
			if (ACC.config.googleApiKey !== "" && ASM.storefinder.originalStore["latitude"] !== storeInformation["latitude"]) {
				var directionsDisplay = new google.maps.DirectionsRenderer();
				var directionsService = new google.maps.DirectionsService();
				var originPoint = new google.maps.LatLng(ASM.storefinder.originalStore["latitude"], ASM.storefinder.originalStore["longitude"]);
				directionsDisplay.setMap(map);
				var request = {
					origin: originPoint,
					destination: centerPoint,
					travelMode: 'DRIVING'
				};

				directionsService.route(request, function (response, status) {
					if (status === 'OK') {
						directionsDisplay.setDirections(response);
					}
				});
			}
			// Driving Options

			var marker = new google.maps.Marker({
				position: new google.maps.LatLng(storeInformation["latitude"], storeInformation["longitude"]),
				map: map,
				title: storeInformation["name"],
				icon: "https://maps.google.com/mapfiles/marker" + 'A' + ".png"
			});

			var infowindow = new google.maps.InfoWindow({
				content: ACC.common.encodeHtml(storeInformation["name"]),
				disableAutoPan: true
			});

			google.maps.event.addListener(marker, 'click', function () {
				var mapWindow = infowindow.open(map, marker, 'noopener,noreferrer');
				mapWindow.opener = null;
			});

			var markerPosition = storeInformation["latitude"] + "," + storeInformation["longitude"];
			map.addListener('click', function (e) {
				if (ACC.config.googleApiKey !== "") {
					window.open("https://www.google.de/maps/dir/" + encodeURIComponent(originAddress) + "/" + encodeURIComponent(markerPosition), '_blank', 'noopener,noreferrer');
				}

				else {
					window.open("https://www.google.de/maps/dir/" + encodeURIComponent(markerPosition), '_blank', 'noopener,noreferrer');
				}
			});
		}

	},

	getStoreData: function (page) {
		ASM.storefinder.storeSearchData.page = page;
		url = $(".js-asm-store-finder").data("url");
		$.ajax({
			url: url,
			data: ASM.storefinder.storeSearchData,
			type: "get",
			success: function (response) {
				var storeData;
				try {
					storeData = $.parseJSON(response);
				} catch (e) {
					storeData = { total: 0, data: [] };
				}
				ASM.storefinder.storeData = storeData;
				ASM.storefinder.refreshNavigation();
				if (ASM.storefinder.storeData.total < 10) {
					$(".js-asm-store-finder-pager-next").attr("disabled", "disabled");
				}
			}
		});
	},

	getInitStoreData: function (q, latitude, longitude) {
		$(".alert").remove();
		data = {
			"q": "",
			"page": 0
		};
		if (q != null) {
			data.q = q;
		}

		if (latitude != null) {
			data.latitude = latitude;
		}

		if (longitude != null) {
			data.longitude = longitude;
		}

		ASM.storefinder.storeSearchData = data;
		ASM.storefinder.getStoreData(data.page);
		$(".js-asm-store-finder").show();
		$(".js-asm-store-finder-pager-prev").attr("disabled", "disabled");
		$(".js-asm-store-finder-pager-next").removeAttr("disabled");
	},

	init: function () {
		$("#findStoresNearMe").attr("disabled", "disabled");
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(
				function (position) {
					ASM.storefinder.coords = position.coords;
					$('#findStoresNearMe').removeAttr("disabled");
				},
				function (error) {
					console.debug("An error occurred... The error code and message are: " + error.code + "/" + error.message);
				}
			);
		}
	}
};

$(document).ready(
	function () {
		ASM.storefinder.bindStoreChange();
		ASM.storefinder.bindPagination();
	}
)

ACC.punchout = {

    _autoload: [
        "blockInspectLogoLink",
        "punchoutNavigation"
    ],


    blockInspectLogoLink: function(){
        $(".inspect-logo a").on("click touchend", function(e){
            e.preventDefault();
        });
    },

    punchoutNavigation: function(){
        if($('.punchout-header').length > 0){
            $('.js-userAccount-Links').remove();
        }
    }
};
ACC.myCompanyNavigation = {

    _autoload: [
        "myCompanyNavigation"
    ],


    myCompanyNavigation: function(){


        var aCompData = [];
        var oMyCompanyData = $('.companyNavComponent');


        //the my company hook for the desktop

        var oMMainNavDesktopCom = $(".js-secondaryNavCompany .js-nav__links");

        if(oMyCompanyData){
            var cLinks = oMyCompanyData.find("a");
            for(var i = 0; i < cLinks.length; i++){
                aCompData.push({link: cLinks[i].href, text: cLinks[i].title});
            }
        }

        //create Welcome User + expand/collapse and close button
        //This is for mobile navigation. Adding html and classes.
        var oUserInfo = $(".js-logged_in");
        //Check to see if user is logged in
        if(oUserInfo && oUserInfo.length === 1 && oMyCompanyData.length > 0)
        {


            //FOR DESKTOP

            //FOR MOBILE
            //create a My Company Top link for desktop - in case more components come then more parameters need to be passed from the backend

            //add my company icon
            //<span class="glyphicon glyphicon-list-alt"></span>
            var myCompanyHook = $('<a class=\"myCompanyLinksHeader collapsed js-myCompany-toggle\"  data-toggle=\"collapse\" data-parent=".nav__right" href=\"#accNavComponentDesktopTwo\">' + oMyCompanyData.data("title") + '</a>' );
            myCompanyHook.insertBefore(oMyCompanyData);


            $('.js-userAccount-Links').append($('<li class="auto"><div class="myCompanyLinksContainer js-myCompanyLinksContainer"></div></li>'));
            var myCompanyHook = [];
            myCompanyHook.push('<div class="sub-nav">')
            myCompanyHook.push('<a id="signedInCompanyToggle" class="myCompanyLinksHeader collapsed js-myCompany-toggle"  data-toggle="collapse" data-target=".offcanvasGroup3">');
            myCompanyHook.push(oMyCompanyData.data("title"));
            myCompanyHook.push('<span class="glyphicon glyphicon-chevron-down myCompanyExp"></span>');
            myCompanyHook.push('</a>');
            myCompanyHook.push('</div>');
            $('.js-myCompanyLinksContainer').append(myCompanyHook.join(''));

            //add UL element for nested collapsing list
            $('.js-myCompanyLinksContainer').append($('<ul data-trigger="#signedInCompanyToggle" class="js-myCompany-root offcanvasGroup3 offcanvasNoBorder subNavList js-nav-collapse-body  collapse sub-nav "></ul>'));

            //My Company links for desktop

            for(var i = aCompData.length - 1; i >= 0; i--){
                var oLink = oDoc.createElement("a");
                oLink.title = aCompData[i].text;
                oLink.href = aCompData[i].link;
                oLink.innerHTML = aCompData[i].text;

                var oListItem = oDoc.createElement("li");
                oListItem.appendChild(oLink);
                oListItem = $(oListItem);
                oListItem.addClass("auto ");
                $('.js-myCompany-root').append(oListItem);
            }

        }
        //desktop

        for(var i = 0; i < aCompData.length; i++){
            var oLink = oDoc.createElement("a");
            oLink.title = aCompData[i].text;
            oLink.href = aCompData[i].link;
            oLink.innerHTML = aCompData[i].text;

            var oListItem = oDoc.createElement("li");
            oListItem.appendChild(oLink);
            oListItem = $(oListItem);
            oListItem.addClass("auto col-md-4");
            oMMainNavDesktopCom.get(0).appendChild(oListItem.get(0));
        }

        //hide and show contnet areas for desktop
        $('.js-secondaryNavAccount').on('shown.bs.collapse', function () {

            if($('.js-secondaryNavCompany').hasClass('in')){
                $('.js-myCompany-toggle').click();
            }

        });

        $('.js-secondaryNavCompany').on('shown.bs.collapse', function () {

            if($('.accNavComponentAccount').hasClass('in')){
                $('.js-myAccount-toggle').click();
            }

        });

    }

};
ACC.commerceorg = {

    _autoload: [
        "bindToSelectBudget",
        "bindToDeselectBudget",
        "bindToSelectLink",
        "bindToDeselectLink",
        "bindToActionConfirmationModalWindow",
        "disablePermissionConfirmation",
        "bindToSelectUser",
        "bindToDeselectUser",
	    "bindToUnitAddUserButton",
	    "disablePermissionConfirmation",
        "bindPermissionTypeSelectionForAddNew",
        "bindToRemoveUserFromUnit",
        "budgetFormInit"
    ],

    bindToSelectBudget: function()
    {
        $(document).on('click','.js-selectBudget',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.selectionCallback);
            return false;
        });

    },

    bindToDeselectBudget: function()
    {
        $(document).on('click','.js-deselectBudget',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.deselectionCallback);
            return false;
        });
    },

    selectionCallback: function(budget)
    {
        $('#card-' + budget.normalizedCode).addClass("selected");
        $('#span-' + budget.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(budget));
    },

    deselectionCallback: function(budget)
    {
        $('#card-' + budget.normalizedCode).removeClass("selected");
        $('#span-' + budget.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(budget));
    },

    disablePermissionConfirmation: function(data)
    {
        $(document).on("click",".js-disable-permission-confirmation",function(e){
            e.preventDefault();

            ACC.colorbox.open("",{
                inline:true,
                href: "#disablePermission",
                width:"620px",
                onComplete: function(){
                    $(this).colorbox.resize();
                }
            });
        });

        $(document).on("click",'#disablePermission #cancelDisablePermission', function (e) {
            e.preventDefault();
            $.colorbox.close();
        });
    },

    bindPermissionTypeSelectionForAddNew: function ()
    {
        $('#selectNewPermissionType').on("change", function (e)
        {
            $.ajax({
                url: ACC.config.encodedContextPath + '/my-company/organization-management/manage-permissions/getNewPermissionForm',
                async: true,
                data: {'permissionType':$(this).val()},
                dataType: "html",
                beforeSend: function ()
                {
                    $("#addNewPermissionForm").html(ACC.address.spinner);
                }
            }).done(function (data)
            {
                $("#addNewPermissionForm").html($(data).html());
                ACC.commerceorg.bindPermissionTypeSelectionForAddNew();
            });
        })
    },

    bindToSelectUser: function()
    {
        $(document).on('click','.js-selectUser',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.userSelectionCallback);
            return false;
        });
    },

    bindToDeselectUser: function()
    {
        $(document).on('click','.js-deselectUser',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.userSelectionCallback);
            return false;
        });
    },

    bindToRemoveUserFromUnit: function()
    {
        $(document).on('click','.js-remove-user-from-unit',function(){
        	var removeUserFromUnit = $(this).parents('.card');
        	var counterElem = $(this).parents('.account-cards').prev('.account-list-header').find('.counter');

        	$.postJSON(this.getAttribute('url'), {}, function(){
        		removeUserFromUnit.remove();
                counterElem.text(counterElem.text() - 1);
        	});

        	return false;
        });
    },

    userSelectionCallback: function(user)
    {
        var userNormalizedId = typeof user.normalizedUid != 'undefined' ? user.normalizedUid : user.normalizedCode;

        $('#selection-' + userNormalizedId).html($('#enableDisableLinksTemplate').tmpl(user));
        $('#roles-' + userNormalizedId).html($('#userRolesTemplate').tmpl(user));
        if (user.selected)
        {
            $('#row-' + userNormalizedId).addClass("selected");
        }
        else
        {
            $('#row-' + userNormalizedId).removeClass("selected");
        }
    },

    bindToSelectLink: function()
    {
        $(document).on('click','.js-selectLink',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.selectionCallbackLink);
            return false;
        });
    },

    bindToDeselectLink: function()
    {
        $(document).on('click','.js-deselectLink',function(){
            var url = $(this).attr('url');
            $.postJSON(url,{}, ACC.commerceorg.deselectionCallbackLink);
            return false;
        });
    },

    selectionCallbackLink: function(permission)
    {
        $('#row-' + permission.normalizedCode).addClass("selected");
        $('#span-' + permission.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(permission));
    },

    deselectionCallbackLink: function(permission)
    {
        $('#row-' + permission.normalizedCode).removeClass("selected");
        $('#span-' + permission.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(permission));
    },


    bindToActionConfirmationModalWindow: function()
    {
        $('.js-action-confirmation-modal a').click(function(e){
            e.preventDefault();

            var title = $(this).data('action-confirmation-modal-title');
            var id = $(this).data('action-confirmation-modal-id');
            var modalWindow = $('#js-action-confirmation-modal-content-' + id);

            if (modalWindow.data('useSourceElementUrl') === true) {
                var url = $(this).prop('href');

                modalWindow.find('.url-holder').each(function(index, element) {
                    var target = $(element);

                    if (target.is("form")) {
                        target.prop('action', url);
                    } else {
                        target.prop('href', url);
                    }
                });
            }

            ACC.colorbox.open(title,{
                inline:true,
                href:modalWindow,
                width:"480px",
                onComplete: function(){
                    ACC.colorbox.resize();
                }
            });
        });

        $('.js-action-confirmation-modal-cancel').click(function(e){
            e.preventDefault();
            ACC.colorbox.close();
        });
    },

    bindToUnitAddUserButton: function()
    {
        $('.js-add-user-action').click(function(e) {
            $(this).parent('.add-user-action-menu').toggleClass('open');
            return false;
        });
    },
    
    budgetFormInit: function() 
    {
        var dateFormatForDatePicker = $('#editB2bBudgetform').data("dateFormatForDatePicker");

        $("#budgetStartDate").datepicker({dateFormat: dateFormatForDatePicker});
        $(document).on("click", '#editB2bBudgetform .js-open-datepicker-budgetStartDate', function () {
            $("#budgetStartDate").datepicker('show');
        });

        $("#budgetEndDate").datepicker({dateFormat: dateFormatForDatePicker});
        $(document).on("click", '#editB2bBudgetform .js-open-datepicker-budgetEndDate', function () {
            $("#budgetEndDate").datepicker('show');
        });

        $('#editB2bBudgetform').validate({
            rules: {
                startDate: {
                    required: true
                },
                endDate: {
                    required: true
                }
            }

        })
    }
};

(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

$.extend( $.fn, {

	validate: function( options ) {

		// If nothing is selected, return nothing; can't chain anyway
		if ( !this.length ) {
			if ( options && options.debug && window.console ) {
				console.warn( "Nothing selected, can't validate, returning nothing." );
			}
			return;
		}

		// Check if a validator for this form was already created
		var validator = $.data( this[ 0 ], "validator" );
		if ( validator ) {
			return validator;
		}

		// Add novalidate tag if HTML5.
		this.attr( "novalidate", "novalidate" );

		validator = new $.validator( options, this[ 0 ] );
		$.data( this[ 0 ], "validator", validator );

		if ( validator.settings.onsubmit ) {

			this.on( "click.validate", ":submit", function( event ) {

				// Track the used submit button to properly handle scripted
				// submits later.
				validator.submitButton = event.currentTarget;

				// Allow suppressing validation by adding a cancel class to the submit button
				if ( $( this ).hasClass( "cancel" ) ) {
					validator.cancelSubmit = true;
				}

				// Allow suppressing validation by adding the html5 formnovalidate attribute to the submit button
				if ( $( this ).attr( "formnovalidate" ) !== undefined ) {
					validator.cancelSubmit = true;
				}
			} );

			// Validate the form on submit
			this.on( "submit.validate", function( event ) {
				if ( validator.settings.debug ) {

					// Prevent form submit to be able to see console output
					event.preventDefault();
				}

				function handle() {
					var hidden, result;

					// Insert a hidden input as a replacement for the missing submit button
					// The hidden input is inserted in two cases:
					//   - A user defined a `submitHandler`
					//   - There was a pending request due to `remote` method and `stopRequest()`
					//     was called to submit the form in case it's valid
					if ( validator.submitButton && ( validator.settings.submitHandler || validator.formSubmitted ) ) {
						hidden = $( "<input type='hidden'/>" )
							.attr( "name", validator.submitButton.name )
							.val( $( validator.submitButton ).val() )
							.appendTo( validator.currentForm );
					}

					if ( validator.settings.submitHandler && !validator.settings.debug ) {
						result = validator.settings.submitHandler.call( validator, validator.currentForm, event );
						if ( hidden ) {

							// And clean up afterwards; thanks to no-block-scope, hidden can be referenced
							hidden.remove();
						}
						if ( result !== undefined ) {
							return result;
						}
						return false;
					}
					return true;
				}

				// Prevent submit for invalid forms or custom submit handlers
				if ( validator.cancelSubmit ) {
					validator.cancelSubmit = false;
					return handle();
				}
				if ( validator.form() ) {
					if ( validator.pendingRequest ) {
						validator.formSubmitted = true;
						return false;
					}
					return handle();
				} else {
					validator.focusInvalid();
					return false;
				}
			} );
		}

		return validator;
	},

	valid: function() {
		var valid, validator, errorList;

		if ( $( this[ 0 ] ).is( "form" ) ) {
			valid = this.validate().form();
		} else {
			errorList = [];
			valid = true;
			validator = $( this[ 0 ].form ).validate();
			this.each( function() {
				valid = validator.element( this ) && valid;
				if ( !valid ) {
					errorList = errorList.concat( validator.errorList );
				}
			} );
			validator.errorList = errorList;
		}
		return valid;
	},

	rules: function( command, argument ) {
		var element = this[ 0 ],
			isContentEditable = typeof this.attr( "contenteditable" ) !== "undefined" && this.attr( "contenteditable" ) !== "false",
			settings, staticRules, existingRules, data, param, filtered;

		// If nothing is selected, return empty object; can't chain anyway
		if ( element == null ) {
			return;
		}

		if ( !element.form && isContentEditable ) {
			element.form = this.closest( "form" )[ 0 ];
			element.name = this.attr( "name" );
		}

		if ( element.form == null ) {
			return;
		}

		if ( command ) {
			settings = $.data( element.form, "validator" ).settings;
			staticRules = settings.rules;
			existingRules = $.validator.staticRules( element );
			switch ( command ) {
			case "add":
				$.extend( existingRules, $.validator.normalizeRule( argument ) );

				// Remove messages from rules, but allow them to be set separately
				delete existingRules.messages;
				staticRules[ element.name ] = existingRules;
				if ( argument.messages ) {
					settings.messages[ element.name ] = $.extend( settings.messages[ element.name ], argument.messages );
				}
				break;
			case "remove":
				if ( !argument ) {
					delete staticRules[ element.name ];
					return existingRules;
				}
				filtered = {};
				$.each( argument.split( /\s/ ), function( index, method ) {
					filtered[ method ] = existingRules[ method ];
					delete existingRules[ method ];
				} );
				return filtered;
			}
		}

		data = $.validator.normalizeRules(
		$.extend(
			{},
			$.validator.classRules( element ),
			$.validator.attributeRules( element ),
			$.validator.dataRules( element ),
			$.validator.staticRules( element )
		), element );

		// Make sure required is at front
		if ( data.required ) {
			param = data.required;
			delete data.required;
			data = $.extend( { required: param }, data );
		}

		// Make sure remote is at back
		if ( data.remote ) {
			param = data.remote;
			delete data.remote;
			data = $.extend( data, { remote: param } );
		}

		return data;
	}
} );

// JQuery trim is deprecated, provide a trim method based on String.prototype.trim
var trim = function( str ) {

	return str.replace( /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, "" );
};

// Custom selectors
$.extend( $.expr.pseudos || $.expr[ ":" ], {		

	blank: function( a ) {
		return !trim( "" + $( a ).val() );
	},

	filled: function( a ) {
		var val = $( a ).val();
		return val !== null && !!trim( "" + val );
	},

	unchecked: function( a ) {
		return !$( a ).prop( "checked" );
	}
} );

// Constructor for validator
$.validator = function( options, form ) {
	this.settings = $.extend( true, {}, $.validator.defaults, options );
	this.currentForm = form;
	this.init();
};

$.validator.format = function( source, params ) {
	if ( arguments.length === 1 ) {
		return function() {
			var args = $.makeArray( arguments );
			args.unshift( source );
			return $.validator.format.apply( this, args );
		};
	}
	if ( params === undefined ) {
		return source;
	}
	if ( arguments.length > 2 && params.constructor !== Array  ) {
		params = $.makeArray( arguments ).slice( 1 );
	}
	if ( params.constructor !== Array ) {
		params = [ params ];
	}
	$.each( params, function( i, n ) {
		source = source.replace( new RegExp( "\\{" + i + "\\}", "g" ), function() {
			return n;
		} );
	} );
	return source;
};

$.extend( $.validator, {

	defaults: {
		messages: {},
		groups: {},
		rules: {},
		errorClass: "error",
		pendingClass: "pending",
		validClass: "valid",
		errorElement: "label",
		focusCleanup: false,
		focusInvalid: true,
		errorContainer: $( [] ),
		errorLabelContainer: $( [] ),
		onsubmit: true,
		ignore: ":hidden",
		ignoreTitle: false,
		customElements: [],
		onfocusin: function( element ) {
			this.lastActive = element;

			// Hide error label and remove error class on focus if enabled
			if ( this.settings.focusCleanup ) {
				if ( this.settings.unhighlight ) {
					this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
				}
				this.hideThese( this.errorsFor( element ) );
			}
		},
		onfocusout: function( element ) {
			if ( !this.checkable( element ) && ( element.name in this.submitted || !this.optional( element ) ) ) {
				this.element( element );
			}
		},
		onkeyup: function( element, event ) {

			// Avoid revalidate the field when pressing one of the following keys
			// Shift       => 16
			// Ctrl        => 17
			// Alt         => 18
			// Caps lock   => 20
			// End         => 35
			// Home        => 36
			// Left arrow  => 37
			// Up arrow    => 38
			// Right arrow => 39
			// Down arrow  => 40
			// Insert      => 45
			// Num lock    => 144
			// AltGr key   => 225
			var excludedKeys = [
				16, 17, 18, 20, 35, 36, 37,
				38, 39, 40, 45, 144, 225
			];

			if ( event.which === 9 && this.elementValue( element ) === "" || $.inArray( event.keyCode, excludedKeys ) !== -1 ) {
				return;
			} else if ( element.name in this.submitted || element.name in this.invalid ) {
				this.element( element );
			}
		},
		onclick: function( element ) {

			// Click on selects, radiobuttons and checkboxes
			if ( element.name in this.submitted ) {
				this.element( element );

			// Or option elements, check parent select in that case
			} else if ( element.parentNode.name in this.submitted ) {
				this.element( element.parentNode );
			}
		},
		highlight: function( element, errorClass, validClass ) {
			if ( element.type === "radio" ) {
				this.findByName( element.name ).addClass( errorClass ).removeClass( validClass );
			} else {
				$( element ).addClass( errorClass ).removeClass( validClass );
			}
		},
		unhighlight: function( element, errorClass, validClass ) {
			if ( element.type === "radio" ) {
				this.findByName( element.name ).removeClass( errorClass ).addClass( validClass );
			} else {
				$( element ).removeClass( errorClass ).addClass( validClass );
			}
		}
	},

	setDefaults: function( settings ) {
		$.extend( $.validator.defaults, settings );
	},

	messages: {
		required: "This field is required.",
		remote: "Please fix this field.",
		email: "Please enter a valid email address.",
		url: "Please enter a valid URL.",
		date: "Please enter a valid date.",
		dateISO: "Please enter a valid date (ISO).",
		number: "Please enter a valid number.",
		digits: "Please enter only digits.",
		equalTo: "Please enter the same value again.",
		maxlength: $.validator.format( "Please enter no more than {0} characters." ),
		minlength: $.validator.format( "Please enter at least {0} characters." ),
		rangelength: $.validator.format( "Please enter a value between {0} and {1} characters long." ),
		range: $.validator.format( "Please enter a value between {0} and {1}." ),
		max: $.validator.format( "Please enter a value less than or equal to {0}." ),
		min: $.validator.format( "Please enter a value greater than or equal to {0}." ),
		step: $.validator.format( "Please enter a multiple of {0}." )
	},

	autoCreateRanges: false,

	prototype: {

		init: function() {
			this.labelContainer = $( this.settings.errorLabelContainer );
			this.errorContext = this.labelContainer.length && this.labelContainer || $( this.currentForm );
			this.containers = $( this.settings.errorContainer ).add( this.settings.errorLabelContainer );
			this.submitted = {};
			this.valueCache = {};
			this.pendingRequest = 0;
			this.pending = {};
			this.invalid = {};
			this.reset();

			var currentForm = this.currentForm,
				groups = ( this.groups = {} ),
				rules;
			$.each( this.settings.groups, function( key, value ) {
				if ( typeof value === "string" ) {
					value = value.split( /\s/ );
				}
				$.each( value, function( index, name ) {
					groups[ name ] = key;
				} );
			} );
			rules = this.settings.rules;
			$.each( rules, function( key, value ) {
				rules[ key ] = $.validator.normalizeRule( value );
			} );

			function delegate( event ) {
				var isContentEditable = typeof $( this ).attr( "contenteditable" ) !== "undefined" && $( this ).attr( "contenteditable" ) !== "false";

				// Set form expando on contenteditable
				if ( !this.form && isContentEditable ) {
					this.form = $( this ).closest( "form" )[ 0 ];
					this.name = $( this ).attr( "name" );
				}

				// Ignore the element if it belongs to another form. This will happen mainly
				// when setting the `form` attribute of an input to the id of another form.
				if ( currentForm !== this.form ) {
					return;
				}

				var validator = $.data( this.form, "validator" ),
					eventType = "on" + event.type.replace( /^validate/, "" ),
					settings = validator.settings;
				if ( settings[ eventType ] && !$( this ).is( settings.ignore ) ) {
					settings[ eventType ].call( validator, this, event );
				}
			}
			var focusListeners = [ ":text", "[type='password']", "[type='file']", "select", "textarea", "[type='number']", "[type='search']",
								"[type='tel']", "[type='url']", "[type='email']", "[type='datetime']", "[type='date']", "[type='month']",
								"[type='week']", "[type='time']", "[type='datetime-local']", "[type='range']", "[type='color']",
								"[type='radio']", "[type='checkbox']", "[contenteditable]", "[type='button']" ];
			var clickListeners = [ "select", "option", "[type='radio']", "[type='checkbox']" ];
			$( this.currentForm )
				.on( "focusin.validate focusout.validate keyup.validate", focusListeners.concat( this.settings.customElements ).join( ", " ), delegate )

				// Support: Chrome, oldIE
				// "select" is provided as event.target when clicking a option
				.on( "click.validate", clickListeners.concat( this.settings.customElements ).join( ", " ), delegate );

			if ( this.settings.invalidHandler ) {
				$( this.currentForm ).on( "invalid-form.validate", this.settings.invalidHandler );
			}
		},

		form: function() {
			this.checkForm();
			$.extend( this.submitted, this.errorMap );
			this.invalid = $.extend( {}, this.errorMap );
			if ( !this.valid() ) {
				$( this.currentForm ).triggerHandler( "invalid-form", [ this ] );
			}
			this.showErrors();
			return this.valid();
		},

		checkForm: function() {
			this.prepareForm();
			for ( var i = 0, elements = ( this.currentElements = this.elements() ); elements[ i ]; i++ ) {
				this.check( elements[ i ] );
			}
			return this.valid();
		},

		element: function( element ) {
			var cleanElement = this.clean( element ),
				checkElement = this.validationTargetFor( cleanElement ),
				v = this,
				result = true,
				rs, group;

			if ( checkElement === undefined ) {
				delete this.invalid[ cleanElement.name ];
			} else {
				this.prepareElement( checkElement );
				this.currentElements = $( checkElement );

				// If this element is grouped, then validate all group elements already
				// containing a value
				group = this.groups[ checkElement.name ];
				if ( group ) {
					$.each( this.groups, function( name, testgroup ) {
						if ( testgroup === group && name !== checkElement.name ) {
							cleanElement = v.validationTargetFor( v.clean( v.findByName( name ) ) );
							if ( cleanElement && cleanElement.name in v.invalid ) {
								v.currentElements.push( cleanElement );
								result = v.check( cleanElement ) && result;
							}
						}
					} );
				}

				rs = this.check( checkElement ) !== false;
				result = result && rs;
				if ( rs ) {
					this.invalid[ checkElement.name ] = false;
				} else {
					this.invalid[ checkElement.name ] = true;
				}

				if ( !this.numberOfInvalids() ) {

					// Hide error containers on last error
					this.toHide = this.toHide.add( this.containers );
				}
				this.showErrors();

				// Add aria-invalid status for screen readers
				$( element ).attr( "aria-invalid", !rs );
			}

			return result;
		},

		showErrors: function( errors ) {
			if ( errors ) {
				var validator = this;

				// Add items to error list and map
				$.extend( this.errorMap, errors );
				this.errorList = $.map( this.errorMap, function( message, name ) {
					return {
						message: message,
						element: validator.findByName( name )[ 0 ]
					};
				} );

				// Remove items from success list
				this.successList = $.grep( this.successList, function( element ) {
					return !( element.name in errors );
				} );
			}
			if ( this.settings.showErrors ) {
				this.settings.showErrors.call( this, this.errorMap, this.errorList );
			} else {
				this.defaultShowErrors();
			}
		},

		resetForm: function() {
			if ( $.fn.resetForm ) {
				$( this.currentForm ).resetForm();
			}
			this.invalid = {};
			this.submitted = {};
			this.prepareForm();
			this.hideErrors();
			var elements = this.elements()
				.removeData( "previousValue" )
				.removeAttr( "aria-invalid" );

			this.resetElements( elements );
		},

		resetElements: function( elements ) {
			var i;

			if ( this.settings.unhighlight ) {
				for ( i = 0; elements[ i ]; i++ ) {
					this.settings.unhighlight.call( this, elements[ i ],
						this.settings.errorClass, "" );
					this.findByName( elements[ i ].name ).removeClass( this.settings.validClass );
				}
			} else {
				elements
					.removeClass( this.settings.errorClass )
					.removeClass( this.settings.validClass );
			}
		},

		numberOfInvalids: function() {
			return this.objectLength( this.invalid );
		},

		objectLength: function( obj ) {
			/* jshint unused: false */
			var count = 0,
				i;
			for ( i in obj ) {

				// This check allows counting elements with empty error
				// message as invalid elements
				if ( obj[ i ] !== undefined && obj[ i ] !== null && obj[ i ] !== false ) {
					count++;
				}
			}
			return count;
		},

		hideErrors: function() {
			this.hideThese( this.toHide );
		},

		hideThese: function( errors ) {
			errors.not( this.containers ).text( "" );
			this.addWrapper( errors ).hide();
		},

		valid: function() {
			return this.size() === 0;
		},

		size: function() {
			return this.errorList.length;
		},

		focusInvalid: function() {
			if ( this.settings.focusInvalid ) {
				try {
					$( this.findLastActive() || this.errorList.length && this.errorList[ 0 ].element || [] )
					.filter( ":visible" )
					.trigger( "focus" )

					// Manually trigger focusin event; without it, focusin handler isn't called, findLastActive won't have anything to find
					.trigger( "focusin" );
				} catch ( e ) {

					// Ignore IE throwing errors when focusing hidden elements
				}
			}
		},

		findLastActive: function() {
			var lastActive = this.lastActive;
			return lastActive && $.grep( this.errorList, function( n ) {
				return n.element.name === lastActive.name;
			} ).length === 1 && lastActive;
		},

		elements: function() {
			var validator = this,
				rulesCache = {},
				selectors = [ "input", "select", "textarea", "[contenteditable]" ];

			// Select all valid inputs inside the form (no submit or reset buttons)
			return $( this.currentForm )
			.find( selectors.concat( this.settings.customElements ).join( ", " ) )
			.not( ":submit, :reset, :image, :disabled" )
			.not( this.settings.ignore )
			.filter( function() {
				var name = this.name || $( this ).attr( "name" ); // For contenteditable
				var isContentEditable = typeof $( this ).attr( "contenteditable" ) !== "undefined" && $( this ).attr( "contenteditable" ) !== "false";

				if ( !name && validator.settings.debug && window.console ) {
					console.error( "%o has no name assigned", this );
				}

				// Set form expando on contenteditable
				if ( isContentEditable ) {
					this.form = $( this ).closest( "form" )[ 0 ];
					this.name = name;
				}

				// Ignore elements that belong to other/nested forms
				if ( this.form !== validator.currentForm ) {
					return false;
				}

				// Select only the first element for each name, and only those with rules specified
				if ( name in rulesCache || !validator.objectLength( $( this ).rules() ) ) {
					return false;
				}

				rulesCache[ name ] = true;
				return true;
			} );
		},

		clean: function( selector ) {
			return $( selector )[ 0 ];
		},

		errors: function() {
			var errorClass = this.settings.errorClass.split( " " ).join( "." );
			return $( this.settings.errorElement + "." + errorClass, this.errorContext );
		},

		resetInternals: function() {
			this.successList = [];
			this.errorList = [];
			this.errorMap = {};
			this.toShow = $( [] );
			this.toHide = $( [] );
		},

		reset: function() {
			this.resetInternals();
			this.currentElements = $( [] );
		},

		prepareForm: function() {
			this.reset();
			this.toHide = this.errors().add( this.containers );
		},

		prepareElement: function( element ) {
			this.reset();
			this.toHide = this.errorsFor( element );
		},

		elementValue: function( element ) {
			var $element = $( element ),
				type = element.type,
				isContentEditable = typeof $element.attr( "contenteditable" ) !== "undefined" && $element.attr( "contenteditable" ) !== "false",
				val, idx;

			if ( type === "radio" || type === "checkbox" ) {
				return this.findByName( element.name ).filter( ":checked" ).val();
			} else if ( type === "number" && typeof element.validity !== "undefined" ) {
				return element.validity.badInput ? "NaN" : $element.val();
			}

			if ( isContentEditable ) {
				val = $element.text();
			} else {
				val = $element.val();
			}

			if ( type === "file" ) {

				// Modern browser (chrome & safari)
				if ( val.substr( 0, 12 ) === "C:\\fakepath\\" ) {
					return val.substr( 12 );
				}

				// Legacy browsers
				// Unix-based path
				idx = val.lastIndexOf( "/" );
				if ( idx >= 0 ) {
					return val.substr( idx + 1 );
				}

				// Windows-based path
				idx = val.lastIndexOf( "\\" );
				if ( idx >= 0 ) {
					return val.substr( idx + 1 );
				}

				// Just the file name
				return val;
			}

			if ( typeof val === "string" ) {
				return val.replace( /\r/g, "" );
			}
			return val;
		},

		check: function( element ) {
			element = this.validationTargetFor( this.clean( element ) );

			var rules = $( element ).rules(),
				rulesCount = $.map( rules, function( n, i ) {
					return i;
				} ).length,
				dependencyMismatch = false,
				val = this.elementValue( element ),
				result, method, rule, normalizer;

			// Abort any pending Ajax request from a previous call to this method.
			this.abortRequest( element );

			// Prioritize the local normalizer defined for this element over the global one
			// if the former exists, otherwise user the global one in case it exists.
			if ( typeof rules.normalizer === "function" ) {
				normalizer = rules.normalizer;
			} else if (	typeof this.settings.normalizer === "function" ) {
				normalizer = this.settings.normalizer;
			}

			// If normalizer is defined, then call it to retreive the changed value instead
			// of using the real one.
			// Note that `this` in the normalizer is `element`.
			if ( normalizer ) {
				val = normalizer.call( element, val );

				// Delete the normalizer from rules to avoid treating it as a pre-defined method.
				delete rules.normalizer;
			}

			for ( method in rules ) {
				rule = { method: method, parameters: rules[ method ] };
				try {
					result = $.validator.methods[ method ].call( this, val, element, rule.parameters );

					// If a method indicates that the field is optional and therefore valid,
					// don't mark it as valid when there are no other rules
					if ( result === "dependency-mismatch" && rulesCount === 1 ) {
						dependencyMismatch = true;
						continue;
					}
					dependencyMismatch = false;

					if ( result === "pending" ) {
						this.toHide = this.toHide.not( this.errorsFor( element ) );
						return;
					}

					if ( !result ) {
						this.formatAndAdd( element, rule );
						return false;
					}
				} catch ( e ) {
					if ( this.settings.debug && window.console ) {
						console.log( "Exception occurred when checking element " + element.id + ", check the '" + rule.method + "' method.", e );
					}
					if ( e instanceof TypeError ) {
						e.message += ".  Exception occurred when checking element " + element.id + ", check the '" + rule.method + "' method.";
					}

					throw e;
				}
			}
			if ( dependencyMismatch ) {
				return;
			}
			if ( this.objectLength( rules ) ) {
				this.successList.push( element );
			}
			return true;
		},

		// Return the custom message for the given element and validation method
		// specified in the element's HTML5 data attribute
		// return the generic message if present and no method specific message is present
		customDataMessage: function( element, method ) {
			return $( element ).data( "msg" + method.charAt( 0 ).toUpperCase() +
				method.substring( 1 ).toLowerCase() ) || $( element ).data( "msg" );
		},

		// Return the custom message for the given element name and validation method
		customMessage: function( name, method ) {
			var m = this.settings.messages[ name ];
			return m && ( m.constructor === String ? m : m[ method ] );
		},

		// Return the first defined argument, allowing empty strings
		findDefined: function() {
			for ( var i = 0; i < arguments.length; i++ ) {
				if ( arguments[ i ] !== undefined ) {
					return arguments[ i ];
				}
			}
			return undefined;
		},

		// The second parameter 'rule' used to be a string, and extended to an object literal
		// of the following form:
		// rule = {
		//     method: "method name",
		//     parameters: "the given method parameters"
		// }
		//
		// The old behavior still supported, kept to maintain backward compatibility with
		// old code, and will be removed in the next major release.
		defaultMessage: function( element, rule ) {
			if ( typeof rule === "string" ) {
				rule = { method: rule };
			}

			var message = this.findDefined(
					this.customMessage( element.name, rule.method ),
					this.customDataMessage( element, rule.method ),

					// 'title' is never undefined, so handle empty string as undefined
					!this.settings.ignoreTitle && element.title || undefined,
					$.validator.messages[ rule.method ],
					"<strong>Warning: No message defined for " + element.name + "</strong>"
				),
				theregex = /\$?\{(\d+)\}/g;
			if ( typeof message === "function" ) {
				message = message.call( this, rule.parameters, element );
			} else if ( theregex.test( message ) ) {
				message = $.validator.format( message.replace( theregex, "{$1}" ), rule.parameters );
			}

			return message;
		},

		formatAndAdd: function( element, rule ) {
			var message = this.defaultMessage( element, rule );

			this.errorList.push( {
				message: message,
				element: element,
				method: rule.method
			} );

			this.errorMap[ element.name ] = message;
			this.submitted[ element.name ] = message;
		},

		addWrapper: function( toToggle ) {
			if ( this.settings.wrapper ) {
				toToggle = toToggle.add( toToggle.parent( this.settings.wrapper ) );
			}
			return toToggle;
		},

		defaultShowErrors: function() {
			var i, elements, error;
			for ( i = 0; this.errorList[ i ]; i++ ) {
				error = this.errorList[ i ];
				if ( this.settings.highlight ) {
					this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );
				}
				this.showLabel( error.element, error.message );
			}
			if ( this.errorList.length ) {
				this.toShow = this.toShow.add( this.containers );
			}
			if ( this.settings.success ) {
				for ( i = 0; this.successList[ i ]; i++ ) {
					this.showLabel( this.successList[ i ] );
				}
			}
			if ( this.settings.unhighlight ) {
				for ( i = 0, elements = this.validElements(); elements[ i ]; i++ ) {
					this.settings.unhighlight.call( this, elements[ i ], this.settings.errorClass, this.settings.validClass );
				}
			}
			this.toHide = this.toHide.not( this.toShow );
			this.hideErrors();
			this.addWrapper( this.toShow ).show();
		},

		validElements: function() {
			return this.currentElements.not( this.invalidElements() );
		},

		invalidElements: function() {
			return $( this.errorList ).map( function() {
				return this.element;
			} );
		},

		showLabel: function( element, message ) {
			var place, group, errorID, v,
				error = this.errorsFor( element ),
				elementID = this.idOrName( element ),
				describedBy = $( element ).attr( "aria-describedby" );

			if ( error.length ) {

				// Refresh error/success class
				error.removeClass( this.settings.validClass ).addClass( this.settings.errorClass );

				// Replace message on existing label
				if ( this.settings && this.settings.escapeHtml ) {
					error.text( message || "" );
				} else {
					error.html( message || "" );
				}
			} else {

				// Create error element
				error = $( "<" + this.settings.errorElement + ">" )
					.attr( "id", elementID + "-error" )
					.addClass( this.settings.errorClass );

				if ( this.settings && this.settings.escapeHtml ) {
					error.text( message || "" );
				} else {
					error.html( message || "" );
				}

				// Maintain reference to the element to be placed into the DOM
				place = error;
				if ( this.settings.wrapper ) {

					// Make sure the element is visible, even in IE
					// actually showing the wrapped element is handled elsewhere
					place = error.hide().show().wrap( "<" + this.settings.wrapper + "/>" ).parent();
				}
				if ( this.labelContainer.length ) {
					this.labelContainer.append( place );
				} else if ( this.settings.errorPlacement ) {
					this.settings.errorPlacement.call( this, place, $( element ) );
				} else {
					place.insertAfter( element );
				}

				// Link error back to the element
				if ( error.is( "label" ) ) {

					// If the error is a label, then associate using 'for'
					error.attr( "for", elementID );

					// If the element is not a child of an associated label, then it's necessary
					// to explicitly apply aria-describedby
				} else if ( error.parents( "label[for='" + this.escapeCssMeta( elementID ) + "']" ).length === 0 ) {
					errorID = error.attr( "id" );

					// Respect existing non-error aria-describedby
					if ( !describedBy ) {
						describedBy = errorID;
					} else if ( !describedBy.match( new RegExp( "\\b" + this.escapeCssMeta( errorID ) + "\\b" ) ) ) {

						// Add to end of list if not already present
						describedBy += " " + errorID;
					}
					$( element ).attr( "aria-describedby", describedBy );

					// If this element is grouped, then assign to all elements in the same group
					group = this.groups[ element.name ];
					if ( group ) {
						v = this;
						$.each( v.groups, function( name, testgroup ) {
							if ( testgroup === group ) {
								$( "[name='" + v.escapeCssMeta( name ) + "']", v.currentForm )
									.attr( "aria-describedby", error.attr( "id" ) );
							}
						} );
					}
				}
			}
			if ( !message && this.settings.success ) {
				error.text( "" );
				if ( typeof this.settings.success === "string" ) {
					error.addClass( this.settings.success );
				} else {
					this.settings.success( error, element );
				}
			}
			this.toShow = this.toShow.add( error );
		},

		errorsFor: function( element ) {
			var name = this.escapeCssMeta( this.idOrName( element ) ),
				describer = $( element ).attr( "aria-describedby" ),
				selector = "label[for='" + name + "'], label[for='" + name + "'] *";

			// 'aria-describedby' should directly reference the error element
			if ( describer ) {
				selector = selector + ", #" + this.escapeCssMeta( describer )
					.replace( /\s+/g, ", #" );
			}

			return this
				.errors()
				.filter( selector );
		},

		// meta-characters that should be escaped in order to be used with JQuery
		// as a literal part of a name/id or any selector.
		escapeCssMeta: function( string ) {
			if ( string === undefined ) {
				return "";
			}

			return string.replace( /([\\!"#$%&'()*+,./:;<=>?@\[\]^`{|}~])/g, "\\$1" );
		},

		idOrName: function( element ) {
			return this.groups[ element.name ] || ( this.checkable( element ) ? element.name : element.id || element.name );
		},

		validationTargetFor: function( element ) {

			// If radio/checkbox, validate first element in group instead
			if ( this.checkable( element ) ) {
				element = this.findByName( element.name );
			}

			// Always apply ignore filter
			return $( element ).not( this.settings.ignore )[ 0 ];
		},

		checkable: function( element ) {
			return ( /radio|checkbox/i ).test( element.type );
		},

		findByName: function( name ) {
			return $( this.currentForm ).find( "[name='" + this.escapeCssMeta( name ) + "']" );
		},

		getLength: function( value, element ) {
			switch ( element.nodeName.toLowerCase() ) {
			case "select":
				return $( "option:selected", element ).length;
			case "input":
				if ( this.checkable( element ) ) {
					return this.findByName( element.name ).filter( ":checked" ).length;
				}
			}
			return value.length;
		},

		depend: function( param, element ) {
			return this.dependTypes[ typeof param ] ? this.dependTypes[ typeof param ]( param, element ) : true;
		},

		dependTypes: {
			"boolean": function( param ) {
				return param;
			},
			"string": function( param, element ) {
				return !!$( param, element.form ).length;
			},
			"function": function( param, element ) {
				return param( element );
			}
		},

		optional: function( element ) {
			var val = this.elementValue( element );
			return !$.validator.methods.required.call( this, val, element ) && "dependency-mismatch";
		},

		elementAjaxPort: function( element ) {
			return "validate" + element.name;
		},

		startRequest: function( element ) {
			if ( !this.pending[ element.name ] ) {
				this.pendingRequest++;
				$( element ).addClass( this.settings.pendingClass );
				this.pending[ element.name ] = true;
			}
		},

		stopRequest: function( element, valid ) {
			this.pendingRequest--;

			// Sometimes synchronization fails, make sure pendingRequest is never < 0
			if ( this.pendingRequest < 0 ) {
				this.pendingRequest = 0;
			}
			delete this.pending[ element.name ];
			$( element ).removeClass( this.settings.pendingClass );
			if ( valid && this.pendingRequest === 0 && this.formSubmitted && this.form() && this.pendingRequest === 0 ) {
				$( this.currentForm ).trigger( "submit" );

				// Remove the hidden input that was used as a replacement for the
				// missing submit button. The hidden input is added by `handle()`
				// to ensure that the value of the used submit button is passed on
				// for scripted submits triggered by this method
				if ( this.submitButton ) {
					$( "input:hidden[name='" + this.submitButton.name + "']", this.currentForm ).remove();
				}

				this.formSubmitted = false;
			} else if ( !valid && this.pendingRequest === 0 && this.formSubmitted ) {
				$( this.currentForm ).triggerHandler( "invalid-form", [ this ] );
				this.formSubmitted = false;
			}
		},

		abortRequest: function( element ) {
			var port;

			if ( this.pending[ element.name ] ) {
				port = this.elementAjaxPort( element );
				$.ajaxAbort( port );

				this.pendingRequest--;

				// Sometimes synchronization fails, make sure pendingRequest is never < 0
				if ( this.pendingRequest < 0 ) {
					this.pendingRequest = 0;
				}

				delete this.pending[ element.name ];
				$( element ).removeClass( this.settings.pendingClass );
			}
		},

		previousValue: function( element, method ) {
			method = typeof method === "string" && method || "remote";

			return $.data( element, "previousValue" ) || $.data( element, "previousValue", {
				old: null,
				valid: true,
				message: this.defaultMessage( element, { method: method } )
			} );
		},

		// Cleans up all forms and elements, removes validator-specific events
		destroy: function() {
			this.resetForm();

			$( this.currentForm )
				.off( ".validate" )
				.removeData( "validator" )
				.find( ".validate-equalTo-blur" )
					.off( ".validate-equalTo" )
					.removeClass( "validate-equalTo-blur" )
				.find( ".validate-lessThan-blur" )
					.off( ".validate-lessThan" )
					.removeClass( "validate-lessThan-blur" )
				.find( ".validate-lessThanEqual-blur" )
					.off( ".validate-lessThanEqual" )
					.removeClass( "validate-lessThanEqual-blur" )
				.find( ".validate-greaterThanEqual-blur" )
					.off( ".validate-greaterThanEqual" )
					.removeClass( "validate-greaterThanEqual-blur" )
				.find( ".validate-greaterThan-blur" )
					.off( ".validate-greaterThan" )
					.removeClass( "validate-greaterThan-blur" );
		}

	},

	classRuleSettings: {
		required: { required: true },
		email: { email: true },
		url: { url: true },
		date: { date: true },
		dateISO: { dateISO: true },
		number: { number: true },
		digits: { digits: true },
		creditcard: { creditcard: true }
	},

	addClassRules: function( className, rules ) {
		if ( className.constructor === String ) {
			this.classRuleSettings[ className ] = rules;
		} else {
			$.extend( this.classRuleSettings, className );
		}
	},

	classRules: function( element ) {
		var rules = {},
			classes = $( element ).attr( "class" );

		if ( classes ) {
			$.each( classes.split( " " ), function() {
				if ( this in $.validator.classRuleSettings ) {
					$.extend( rules, $.validator.classRuleSettings[ this ] );
				}
			} );
		}
		return rules;
	},

	normalizeAttributeRule: function( rules, type, method, value ) {

		// Convert the value to a number for number inputs, and for text for backwards compability
		// allows type="date" and others to be compared as strings
		if ( /min|max|step/.test( method ) && ( type === null || /number|range|text/.test( type ) ) ) {
			value = Number( value );

			// Support Opera Mini, which returns NaN for undefined minlength
			if ( isNaN( value ) ) {
				value = undefined;
			}
		}

		if ( value || value === 0 ) {
			rules[ method ] = value;
		} else if ( type === method && type !== "range" ) {

			// Exception: the jquery validate 'range' method
			// does not test for the html5 'range' type
			rules[ type === "date" ? "dateISO" : method ] = true;
		}
	},

	attributeRules: function( element ) {
		var rules = {},
			$element = $( element ),
			type = element.getAttribute( "type" ),
			method, value;

		for ( method in $.validator.methods ) {

			// Support for <input required> in both html5 and older browsers
			if ( method === "required" ) {
				value = element.getAttribute( method );

				// Some browsers return an empty string for the required attribute
				// and non-HTML5 browsers might have required="" markup
				if ( value === "" ) {
					value = true;
				}

				// Force non-HTML5 browsers to return bool
				value = !!value;
			} else {
				value = $element.attr( method );
			}

			this.normalizeAttributeRule( rules, type, method, value );
		}

		// 'maxlength' may be returned as -1, 2147483647 ( IE ) and 524288 ( safari ) for text inputs
		if ( rules.maxlength && /-1|2147483647|524288/.test( rules.maxlength ) ) {
			delete rules.maxlength;
		}

		return rules;
	},

	dataRules: function( element ) {
		var rules = {},
			$element = $( element ),
			type = element.getAttribute( "type" ),
			method, value;

		for ( method in $.validator.methods ) {
			value = $element.data( "rule" + method.charAt( 0 ).toUpperCase() + method.substring( 1 ).toLowerCase() );

			// Cast empty attributes like `data-rule-required` to `true`
			if ( value === "" ) {
				value = true;
			}

			this.normalizeAttributeRule( rules, type, method, value );
		}
		return rules;
	},

	staticRules: function( element ) {
		var rules = {},
			validator = $.data( element.form, "validator" );

		if ( validator.settings.rules ) {
			rules = $.validator.normalizeRule( validator.settings.rules[ element.name ] ) || {};
		}
		return rules;
	},

	normalizeRules: function( rules, element ) {

		// Handle dependency check
		$.each( rules, function( prop, val ) {

			// Ignore rule when param is explicitly false, eg. required:false
			if ( val === false ) {
				delete rules[ prop ];
				return;
			}
			if ( val.param || val.depends ) {
				var keepRule = true;
				switch ( typeof val.depends ) {
				case "string":
					keepRule = !!$( val.depends, element.form ).length;
					break;
				case "function":
					keepRule = val.depends.call( element, element );
					break;
				}
				if ( keepRule ) {
					rules[ prop ] = val.param !== undefined ? val.param : true;
				} else {
					$.data( element.form, "validator" ).resetElements( $( element ) );
					delete rules[ prop ];
				}
			}
		} );

		// Evaluate parameters
		$.each( rules, function( rule, parameter ) {
			rules[ rule ] = typeof parameter === "function" && rule !== "normalizer" ? parameter( element ) : parameter;
		} );

		// Clean number parameters
		$.each( [ "minlength", "maxlength" ], function() {
			if ( rules[ this ] ) {
				rules[ this ] = Number( rules[ this ] );
			}
		} );
		$.each( [ "rangelength", "range" ], function() {
			var parts;
			if ( rules[ this ] ) {
				if ( Array.isArray( rules[ this ] ) ) {
					rules[ this ] = [ Number( rules[ this ][ 0 ] ), Number( rules[ this ][ 1 ] ) ];
				} else if ( typeof rules[ this ] === "string" ) {
					parts = rules[ this ].replace( /[\[\]]/g, "" ).split( /[\s,]+/ );
					rules[ this ] = [ Number( parts[ 0 ] ), Number( parts[ 1 ] ) ];
				}
			}
		} );

		if ( $.validator.autoCreateRanges ) {

			// Auto-create ranges
			if ( rules.min != null && rules.max != null ) {
				rules.range = [ rules.min, rules.max ];
				delete rules.min;
				delete rules.max;
			}
			if ( rules.minlength != null && rules.maxlength != null ) {
				rules.rangelength = [ rules.minlength, rules.maxlength ];
				delete rules.minlength;
				delete rules.maxlength;
			}
		}

		return rules;
	},

	// Converts a simple string to a {string: true} rule, e.g., "required" to {required:true}
	normalizeRule: function( data ) {
		if ( typeof data === "string" ) {
			var transformed = {};
			$.each( data.split( /\s/ ), function() {
				transformed[ this ] = true;
			} );
			data = transformed;
		}
		return data;
	},

	addMethod: function( name, method, message ) {
		$.validator.methods[ name ] = method;
		$.validator.messages[ name ] = message !== undefined ? message : $.validator.messages[ name ];
		if ( method.length < 3 ) {
			$.validator.addClassRules( name, $.validator.normalizeRule( name ) );
		}
	},

	methods: {

		required: function( value, element, param ) {

			// Check if dependency is met
			if ( !this.depend( param, element ) ) {
				return "dependency-mismatch";
			}
			if ( element.nodeName.toLowerCase() === "select" ) {

				// Could be an array for select-multiple or a string, both are fine this way
				var val = $( element ).val();
				return val && val.length > 0;
			}
			if ( this.checkable( element ) ) {
				return this.getLength( value, element ) > 0;
			}
			return value !== undefined && value !== null && value.length > 0;
		},

		email: function( value, element ) {

			// If you have a problem with this implementation, report a bug against the above spec
			// Or use custom methods to implement your own email validation
			return this.optional( element ) || /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test( value );
		},

		url: function( value, element ) {

			// modified to allow protocol-relative URLs
			return this.optional( element ) || /^(?:(?:(?:https?|ftp):)?\/\/)(?:(?:[^\]\[?\/<~#`!@$^&*()+=}|:";',>{ ]|%[0-9A-Fa-f]{2})+(?::(?:[^\]\[?\/<~#`!@$^&*()+=}|:";',>{ ]|%[0-9A-Fa-f]{2})*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z0-9\u00a1-\uffff][a-z0-9\u00a1-\uffff_-]{0,62})?[a-z0-9\u00a1-\uffff]\.)+(?:[a-z\u00a1-\uffff]{2,}\.?))(?::\d{2,5})?(?:[/?#]\S*)?$/i.test( value );
		},

		date: ( function() {
			var called = false;

			return function( value, element ) {
				if ( !called ) {
					called = true;
					if ( this.settings.debug && window.console ) {
						console.warn(
							"The `date` method is deprecated and will be removed in version '2.0.0'.\n" +
							"Please don't use it, since it relies on the Date constructor, which\n" +
							"behaves very differently across browsers and locales. Use `dateISO`\n" +
							"instead or one of the locale specific methods in `localizations/`\n" +
							"and `additional-methods.js`."
						);
					}
				}

				return this.optional( element ) || !/Invalid|NaN/.test( new Date( value ).toString() );
			};
		}() ),

		dateISO: function( value, element ) {
			return this.optional( element ) || /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test( value );
		},

		number: function( value, element ) {
			return this.optional( element ) || /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:-?\.\d+)?$/.test( value );
		},

		digits: function( value, element ) {
			return this.optional( element ) || /^\d+$/.test( value );
		},

		minlength: function( value, element, param ) {
			var length = Array.isArray( value ) ? value.length : this.getLength( value, element );
			return this.optional( element ) || length >= param;
		},

		maxlength: function( value, element, param ) {
			var length = Array.isArray( value ) ? value.length : this.getLength( value, element );
			return this.optional( element ) || length <= param;
		},

		rangelength: function( value, element, param ) {
			var length = Array.isArray( value ) ? value.length : this.getLength( value, element );
			return this.optional( element ) || ( length >= param[ 0 ] && length <= param[ 1 ] );
		},

		min: function( value, element, param ) {
			return this.optional( element ) || value >= param;
		},

		max: function( value, element, param ) {
			return this.optional( element ) || value <= param;
		},

		range: function( value, element, param ) {
			return this.optional( element ) || ( value >= param[ 0 ] && value <= param[ 1 ] );
		},

		step: function( value, element, param ) {
			var type = $( element ).attr( "type" ),
				errorMessage = "Step attribute on input type " + type + " is not supported.",
				supportedTypes = [ "text", "number", "range" ],
				re = new RegExp( "\\b" + type + "\\b" ),
				notSupported = type && !re.test( supportedTypes.join() ),
				decimalPlaces = function( num ) {
					var match = ( "" + num ).match( /(?:\.(\d+))?$/ );
					if ( !match ) {
						return 0;
					}

					// Number of digits right of decimal point.
					return match[ 1 ] ? match[ 1 ].length : 0;
				},
				toInt = function( num ) {
					return Math.round( num * Math.pow( 10, decimals ) );
				},
				valid = true,
				decimals;

			// Works only for text, number and range input types
			// TODO find a way to support input types date, datetime, datetime-local, month, time and week
			if ( notSupported ) {
				throw new Error( errorMessage );
			}

			decimals = decimalPlaces( param );

			// Value can't have too many decimals
			if ( decimalPlaces( value ) > decimals || toInt( value ) % toInt( param ) !== 0 ) {
				valid = false;
			}

			return this.optional( element ) || valid;
		},

		equalTo: function( value, element, param ) {

			// Bind to the blur event of the target in order to revalidate whenever the target field is updated
			var target = $( param );
			if ( this.settings.onfocusout && target.not( ".validate-equalTo-blur" ).length ) {
				target.addClass( "validate-equalTo-blur" ).on( "blur.validate-equalTo", function() {
					$( element ).valid();
				} );
			}
			return value === target.val();
		},

		remote: function( value, element, param, method ) {
			if ( this.optional( element ) ) {
				return "dependency-mismatch";
			}

			method = typeof method === "string" && method || "remote";

			var previous = this.previousValue( element, method ),
				validator, data, optionDataString;

			if ( !this.settings.messages[ element.name ] ) {
				this.settings.messages[ element.name ] = {};
			}
			previous.originalMessage = previous.originalMessage || this.settings.messages[ element.name ][ method ];
			this.settings.messages[ element.name ][ method ] = previous.message;

			param = typeof param === "string" && { url: param } || param;
			optionDataString = $.param( $.extend( { data: value }, param.data ) );
			if ( previous.valid !== null && previous.old === optionDataString ) {
				return previous.valid;
			}

			previous.old = optionDataString;
			previous.valid = null;
			validator = this;
			this.startRequest( element );
			data = {};
			data[ element.name ] = value;
			$.ajax( $.extend( true, {
				mode: "abort",
				port: this.elementAjaxPort( element ),
				dataType: "json",
				data: data,
				context: validator.currentForm,
				success: function( response ) {
					var valid = response === true || response === "true",
						errors, message, submitted;

					validator.settings.messages[ element.name ][ method ] = previous.originalMessage;
					if ( valid ) {
						submitted = validator.formSubmitted;
						validator.toHide = validator.errorsFor( element );
						validator.formSubmitted = submitted;
						validator.successList.push( element );
						validator.invalid[ element.name ] = false;
						validator.showErrors();
					} else {
						errors = {};
						message = response || validator.defaultMessage( element, { method: method, parameters: value } );
						errors[ element.name ] = previous.message = message;
						validator.invalid[ element.name ] = true;
						validator.showErrors( errors );
					}
					previous.valid = valid;
					validator.stopRequest( element, valid );
				}
			}, param ) );
			return "pending";
		}
	}

} );

// Ajax mode: abort
// usage: $.ajax({ mode: "abort"[, port: "uniqueport"]});
//        $.ajaxAbort( port );
// if mode:"abort" is used, the previous request on that port (port can be undefined) is aborted via XMLHttpRequest.abort()

var pendingRequests = {},
	ajax;

// Use a prefilter if available (1.5+)
if ( $.ajaxPrefilter ) {
	$.ajaxPrefilter( function( settings, _, xhr ) {
		var port = settings.port;
		if ( settings.mode === "abort" ) {
			$.ajaxAbort( port );
			pendingRequests[ port ] = xhr;
		}
	} );
} else {

	// Proxy ajax
	ajax = $.ajax;
	$.ajax = function( settings ) {
		var mode = ( "mode" in settings ? settings : $.ajaxSettings ).mode,
			port = ( "port" in settings ? settings : $.ajaxSettings ).port;
		if ( mode === "abort" ) {
			$.ajaxAbort( port );
			pendingRequests[ port ] = ajax.apply( this, arguments );
			return pendingRequests[ port ];
		}
		return ajax.apply( this, arguments );
	};
}

// Abort the previous request without sending a new one
$.ajaxAbort = function( port ) {
	if ( pendingRequests[ port ] ) {
		pendingRequests[ port ].abort();
		delete pendingRequests[ port ];
	}
};
return $;
}));
ACC.checkoutsummary = {

	_autoload: [
		"bindAllButtons",
	    "bindScheduleReplenishment",
		"replenishmentInit"
	],

	bindAllButtons: function()
	{
		ACC.checkoutsummary.toggleActionButtons('.place-order-form');
		
	},
	
	toggleActionButtons: function(selector) {
		
		var cssClass = $(selector);
		var checkoutBtns = cssClass.find('.checkoutSummaryButton');
		var checkBox = cssClass.find('input[name=termsCheck]')	
		
		if(checkBox.is(':checked')) {
			checkoutBtns.prop('disabled', false);
		}
		
		checkBox.on('click', function() {
			var checked = $(this).prop('checked');

			if(checked) {
				checkoutBtns.prop('disabled', false);
			} else {
				checkoutBtns.prop('disabled', true);
			}
		});
	},

    validateDate: function(date,dateFormat) {
    	var validDate = true;
		try{
			$.datepicker.parseDate(dateFormat, date);
		} catch(err){
			//Bad date detected 
			validDate = false;
		}	
		return validDate;
    },

	toggleReplenishmentScheduleDateError: function(showError) {
		if (showError) {
			var datePickerElem = $('#replenishmentSchedule .datepicker');
			if (!datePickerElem.hasClass('has-error')) {
				datePickerElem.addClass('has-error');
			}
			$('#errorReplenishmentStartDate').show();
		} else {
			$('#replenishmentSchedule .datepicker').removeClass('has-error');
			$('#errorReplenishmentStartDate').hide();
		}
	},

	bindScheduleReplenishment: function(data)
	{
		var form = $('#placeOrderForm1');
		var placeReplenishment = false;
		
		$(document).on("click",".scheduleReplenishmentButton",function(e){
			e.preventDefault();
			
			var termChecked = $(this).closest("form").find('input[name=termsCheck]').is(':checked');	
			form.find('input[name=termsCheck]').prop('checked', termChecked);
			
			var title = $('.scheduleReplenishmentButton').first().text().trim();
			
			ACC.colorbox.open(title,{
				href: "#replenishmentSchedule",
				inline: true,
				width:"620px",
				onComplete: function(){
					ACC.checkoutsummary.toggleReplenishmentScheduleDateError(false);
					$(this).colorbox.resize();
					placeReplenishment = false;
				},
				onClosed: function() {
					
					if (placeReplenishment) {
						form.submit();
					}
					
					$(".replenishmentOrderClass").val(false);
				}
			});
			
			$("input:radio[name=replenishmentRecurrence]").click(function() {
				if ($("#replenishmentStartDate").val() != '') {
					$('#replenishmentSchedule .js-replenishment-actions').show();
				}
				switch(this.value)
				{
					case "DAILY":

						$('.scheduleformD').show();
						$('.scheduleformW').hide();
						$('.scheduleformM').hide();

						break;
					case "WEEKLY":
						$('.scheduleformD').hide();
						$('.scheduleformW').show();
						$('.scheduleformM').hide();
						break;
					default:
						$('.scheduleformD').hide();
						$('.scheduleformW').hide();
						$('.scheduleformM').show();
				};


				$.colorbox.resize();
			});
			
		});
		
		$(document).on("click",'#replenishmentSchedule #cancelReplenishmentOrder', function (e) {
			e.preventDefault();
			$(".replenishmentOrderClass").val(false);
			$.colorbox.close();
		});
		
		$(document).on("click",'#replenishmentSchedule #placeReplenishmentOrder', function (e){
			e.preventDefault();

			var localeDateFormat = $('#replenishmentSchedule').data('dateForDatePicker');
			var dateEntered = $("#replenishmentStartDate").val();

			if(ACC.checkoutsummary.validateDate(dateEntered, localeDateFormat)){
				$(".replenishmentOrderClass").val(true);
				placeReplenishment = true;
				$.colorbox.close();	
			} else{
				ACC.checkoutsummary.toggleReplenishmentScheduleDateError(true);
				$.colorbox.resize();
			}

		});

		$(document).on("change",'#replenishmentStartDate', function (e) {
			ACC.checkoutsummary.toggleReplenishmentScheduleDateError(false);
			$.colorbox.resize();
		});


		$(document).on("click",'#replenishmentSchedule .js-open-datepicker', function (){
			$('#replenishmentSchedule .hasDatepicker').datepicker('show');
		});

	},

	replenishmentInit: function ()
	{
		var placeOrderFormReplenishmentOrder = $('#replenishmentSchedule').data("placeOrderFormReplenishmentOrder");
		var placeOrderFormReplenishmentRecurrence = $('#replenishmentSchedule').data("placeOrderFormReplenishmentRecurrence");
		var dateForDatePicker = $('#replenishmentSchedule').data("dateForDatePicker");
		var placeOrderFormNDays = $('#replenishmentSchedule').data("placeOrderFormNDays");
		var placeOrderFormNthDayOfMonth = $('#replenishmentSchedule').data("placeOrderFormNthDayOfMonth");
		
		if (placeOrderFormReplenishmentOrder === undefined) {
			return;
		}
		
		// replenishment schedule data not set to cart yet
		if (!placeOrderFormReplenishmentOrder) {
		
			$('#replenishmentSchedule .js-replenishment-actions').hide();
		
			// default value for daily
			$("input:radio[name='replenishmentRecurrence'][value=DAILY]").prop('checked', false);
			$('.scheduleformD').hide();
			$("#nDays option[value=" + placeOrderFormNDays + "]").attr('selected', 'selected');

			// default value for weekly
			$("input:radio[name='replenishmentRecurrence'][value=WEEKLY]").prop('checked', false);
			$('.scheduleformW').hide();
		
			// default value for monthly
			$("input:radio[name='replenishmentRecurrence'][value=MONTHLY]").prop('checked', false);
			$('.scheduleformM').hide();
		
			if (placeOrderFormNthDayOfMonth != '')		
				$("#nthDayOfMonth option[value=" + placeOrderFormNthDayOfMonth + "]").attr('selected', 'selected');

			$("#replenishmentStartDate").val("");
		}
		else {
			switch(placeOrderFormReplenishmentRecurrence)
			{
				case "DAILY":
					$('.scheduleformD').show();
					break;
				case "WEEKLY":
					$('.scheduleformW').show();
					break;
				default:
					$('.scheduleformM').show();
			};

		}

		$(".js-replenishment-datepicker").datepicker({
			dateFormat: dateForDatePicker,
		    onClose: function() {
		    	if ($(this).val() == '' ) {
		    		$('#replenishmentSchedule .js-replenishment-actions').hide();
		    	}
		    	else {
		    		if ($("input:radio[name=replenishmentRecurrence]").is(':checked')) {
			        	$('#replenishmentSchedule .js-replenishment-actions').show();
                        $.colorbox.resize();
			        }
		    	}
		    		
		    }
		});	
		
	}

};

ACC.paymentType = {
		
		_autoload: [
		    		"bindPaymentTypeSelect",
		    		"showHideCostCenterSelect"
		    	],

	bindPaymentTypeSelect: function ()
	{
		$("input:radio[name='paymentType']").change(function()
		{
			ACC.paymentType.showHideCostCenterSelect();
		});
	},

	showHideCostCenterSelect: function() {
		var paymentTypeSelected =  $("input:radio[name='paymentType']:checked").val();
		if(paymentTypeSelected == "ACCOUNT") {
			$("#costCenter").show();
		} else {
			$("#costCenter").hide();
		}
	}
}
	
	

ACC.replenishment = {
	_autoload: [
		"bindToCancelReplenishmentOrderActionButton",
		"bindToCancelReplenishmentOrderCancelButton",
	],

	bindToCancelReplenishmentOrderActionButton: function ()
	{
		$(document).on("click", '.js-replenishment-cancel-button', ACC.replenishment.handleCancelReplenishmentOrderButtonClick);
	},
	bindToCancelReplenishmentOrderCancelButton: function ()
	{
		$(document).on("click", '.js-replenishment-order-cancel-modal-cancel-button', ACC.replenishment.handleCancelReplenishmentOrderCancelButtonClick);
	},

	handleCancelReplenishmentOrderButtonClick: function() {
		var replenishmentNumber = $(this).data('job-code');
		var popupTitle = $(this).data('popup-title');

		ACC.colorbox.open(popupTitle,{
			inline: true,
			className: "replenishment-order-cancel-modal",
			href: "#popup_confirm_replenishment_order_cancellation_" + replenishmentNumber,
			width: '435px',


			onComplete: function ()
			{
				$(this).colorbox.resize();
			}

		});

	},

	handleCancelReplenishmentOrderCancelButtonClick: function() {
		ACC.colorbox.close();
	}
}
var ACC = ACC || {}; // make sure ACC is available

if ($("#orderFormContainer").length > 0) {
    ACC.orderform = {
        _autoload: [
            "bindAll"
        ],

        $selectedProductIdsContainer: $("#js-selected-product-ids"),
        $productIdsInput: $("#js-product-ids"),
        $enableProductIdsCheckBox: $("#js-enable-product-ids"),
        $productIdTagBoxTemplate: $("#product-id-tag-box-template"),
        $removeProductIdButton: $(".js-remove-product-id"),
        $advSearchButton: $(".adv_search_button"),
        $orderFormToggle: $(".js-show-order-form-grid-wrapper"),
        $searchCurrentLabel: $(".searchInput label"),
        $createOrderFormButton: $(".js-create-order-form-button"),
        $createOrderFormCheckbox: $(".js-checkbox-sku-id, .js-checkbox-base-product"),
        $baseProductCheckBox: $(".js-checkbox-base-product"),
        $skuIDCheckbox: $(".js-checkbox-sku-id"),
        $skuQuantityOrderInput: $(".sku-quantity"),
        $addToCartBtn: $('#js-add-to-cart-order-form-btn-top, #js-add-to-cart-order-form-btn-bottom'),
        $orderForm:$("#isCreateOrderForm"),
        $userQtyInputSelection: $("input[data-product-selection]"),
        $totalPrice : $('.js-total-price'),
        $totalItemsCount : $('.js-total-items-count > span'),
        $totalPriceValue: $('.js-total-price-value'),
        $advancedSearchForm: $('#advancedSearchForm'),

        /*
         * Register all the event handlers.
         */
        bindAll: function () {
            ACC.orderform.bindToToggleProductIds(ACC.orderform.$enableProductIdsCheckBox);
            ACC.orderform.bindToRemoveProductId(ACC.orderform.$removeProductIdButton);
            ACC.orderform.bindToAdvSearchButton(ACC.orderform.$advSearchButton);
            ACC.orderform.bindOrderFormToggle(ACC.orderform.$orderFormToggle);
            ACC.orderform.bindToCreateOrderFormButton(ACC.orderform.$createOrderFormButton);
            ACC.orderform.bindToBaseProductCheckBox(ACC.orderform.$baseProductCheckBox);
            ACC.orderform.bindToSkuIDCheckBox(ACC.orderform.$skuIDCheckbox);
            ACC.orderform.bindAddToCartClick(ACC.orderform.$addToCartBtn);

            if (ACC.orderform.$enableProductIdsCheckBox.prop("checked")) {
                ACC.orderform.replaceSearchLabel(true);
                ACC.orderform.appendProductIdsForSearch();
            }

            if ($("#search-create-order-form").prop("checked")) {
                ACC.orderform.makeUserSelectionAfterPagination();
                ACC.orderform.setStateOrderFormButton();
            } else {
                ACC.orderform.fillQuantityValueForPagination(ACC.orderform.$userQtyInputSelection);
            }

            ACC.orderform.$advancedSearchForm.find("input[name='CSRFToken']").remove();
        },

        bindOrderFormToggle: function(orderFormToggle) {
            orderFormToggle.click(function (event) {
                var $this = $(this);
                $(this).toggleClass('open');
                var orderFormGridWrapper = $(this).parents('.js-item-list-item').next('.js-order-form-grid-wrapper');
                orderFormGridWrapper.slideToggle("slow", function() {
                    if(!$this.data('calc')) {
                        var scrollingContent = orderFormGridWrapper.children('#cartOrderGridForm').addClass('visible');
                        ACC.productorderform.coreTableScrollActions(scrollingContent);
                        $this.data('calc',true);
                    }
                });
            });
        },

        bindToAdvSearchButton: function (advSearchButton) {
            advSearchButton.click(function (event) {
                if (ACC.orderform.$enableProductIdsCheckBox.prop("checked")) {
                    //Append the new product id's to search in sku only mode!
                    if (ACC.orderform.$productIdsInput.val() != "") {
                        ACC.orderform.appendProductIdsForSearch();
                    }
                    ACC.orderform.$productIdsInput.val(ACC.orderform.getProductsIdsFromSessionStorage());
                }
                ACC.orderform.stripOutInvalidChars();
                if (ACC.orderform.$orderForm.val()) {
                    sessionStorage.clear();
                } else {
                    ACC.orderform.cleanupQtyInputFromSessionStorage();
                }
            });
        },

        bindToCreateOrderFormButton: function (createOrderFormButton) {
            createOrderFormButton.click(function (event) {
                ACC.orderform.stripOutInvalidChars();
                ACC.orderform.$productIdsInput.val(ACC.orderform.getProductsIdsFromSessionStorage());
                ACC.orderform.$orderForm.val(true);
                sessionStorage.removeItem("checkedProducts");
                ACC.orderform.$selectedProductIdsContainer.empty();
                ACC.orderform.$enableProductIdsCheckBox.attr("checked", false);
                ACC.orderform.$advSearchButton.click();
            });
        },

        bindToBaseProductCheckBox: function (baseProductCheckBox) {
            baseProductCheckBox.click(function (event) {
                var baseProduct = this;
                $(ACC.orderform.$skuIDCheckbox).each(function () {
                    if ($(this).attr("base-product-code") && $(this).attr("base-product-code") == baseProduct.value) {
                        $(this).prop("checked", baseProduct.checked);
                        ACC.orderform.addProductIdsToSessionStorage(this);
                    }
                })
            });
        },

        bindToSkuIDCheckBox: function (skuIDCheckbox) {
            skuIDCheckbox.click(function (event) {
                ACC.orderform.addProductIdsToSessionStorage(this);
            });
        },


        addToSkuQtyInput: function (_this) {
            // if there are no items to add, disable addToCartBtn, otherwise, enable it
            if (ACC.orderform.$totalItemsCount.length != 0 && ACC.orderform.$totalItemsCount.text() == 0) {
                ACC.orderform.$addToCartBtn.attr('disabled', 'disabled');
            } else {
                ACC.orderform.$addToCartBtn.removeAttr('disabled');
            }
            var qtyInputs = ACC.orderform.fetchOrPopulateSessionStorageObj("qtyInputs");
            var qtyInputJson = JSON.parse($(_this).attr("data-product-selection"));
            var filteredObjects = ACC.orderform.filterMatchingQtyInputs(qtyInputs, qtyInputJson);
            for (var i = 0; i < filteredObjects.length; i++) {
                qtyInputs.splice(qtyInputs.indexOf(filteredObjects[i]), 1);
            }
            qtyInputJson['qty'] = _this.value;
            qtyInputs.push(qtyInputJson);
            sessionStorage.setItem("qtyInputs", JSON.stringify(qtyInputs));

            ACC.orderform.setTotalItemPrice($(_this), _this.value, $(_this).siblings('.price').data('variant-price'));
        },

        setStateOrderFormButton: function () {
            ACC.orderform.$createOrderFormButton.attr('disabled', 'disabled');
            var checkedProducts = ACC.orderform.getProductsIdsFromSessionStorage();

            if(checkedProducts.length > 0){
                ACC.orderform.$createOrderFormButton.removeAttr("disabled");
            }
            else {
                ACC.orderform.$createOrderFormButton.attr('disabled', 'disabled');
            }
        },

        addProductIdsToSessionStorage: function (checkbox) {
            var clickedProductIds = JSON.parse(sessionStorage.getItem("checkedProducts"));
            if (clickedProductIds == null || clickedProductIds == undefined) {
                sessionStorage.setItem("checkedProducts", JSON.stringify([]))
                clickedProductIds = new Array();
            }

            var ifProductExist = clickedProductIds.indexOf(checkbox.value);

            if (checkbox.checked) {
                if (ifProductExist == -1)
                    clickedProductIds.push(checkbox.value);
            } else {
                if (ifProductExist != -1)
                    clickedProductIds.splice(ifProductExist, 1);
            }
            sessionStorage.setItem("checkedProducts", JSON.stringify(clickedProductIds));
            console.log(sessionStorage.getItem("checkedProducts"));
            ACC.orderform.setStateOrderFormButton();
        },

        fetchOrPopulateSessionStorageObj: function (item) {
            var storageObj = JSON.parse(sessionStorage.getItem(item));
            if (storageObj == null || storageObj == undefined) {
                sessionStorage.setItem(item, JSON.stringify([]))
                storageObj = new Array();
            }
            return storageObj
        },

        makeUserSelectionAfterPagination: function () {
            var clickedProductIds = JSON.parse(sessionStorage.getItem("checkedProducts"));
            if (clickedProductIds != null) {
                $(ACC.orderform.$skuIDCheckbox).each(function () {
                    if (clickedProductIds.indexOf(this.value) != -1)
                        $(this).prop("checked", true);
                })
            }
        },

        fillQuantityValueForPagination: function (userQtyInputSelection) {
            var qtyInputs = JSON.parse(sessionStorage.getItem("qtyInputs"));
            var prevParentId, prevVariantLoop, parentTable = {};
            if (qtyInputs != null && qtyInputs.length > 0) {
                userQtyInputSelection.each(function (obj) {
                    var filteredObjects = ACC.orderform.filterMatchingQtyInputs(qtyInputs, JSON.parse($(this).attr('data-product-selection')));
                    if (filteredObjects != null && filteredObjects.length > 0) {
                        var qty = filteredObjects[0].qty;
                        var resetSummary = false;
                        $(this).attr('value', qty);
                        if (this.hasAttribute('data-variant-id')) {

                            if(prevVariantLoop === $(this).parents('table').data('variant-loop')){
                                resetSummary = true;
                            }

                            ACC.productorderform.calculateVariantTotal($(this), qty);
                            if(prevParentId === $(this).data('parent-id') && prevVariantLoop === $(this).parents('table').data('variant-loop')){
                                ACC.productorderform.updateSelectedVariantGridTotal(this,0,false, resetSummary);
                            }
                            else {
                                ACC.productorderform.updateSelectedVariantGridTotal(this,0,true, resetSummary);
                            }
                            prevParentId = $(this).data('parent-id');
                            parentTable = $(this).parents('table');
                            prevVariantLoop = parentTable.data('variant-loop');
                        }
                    }
                });
                ACC.orderform.$addToCartBtn.removeAttr("disabled");
            }
            ACC.orderform.resetPriceCounters();
        },

        getProductsIdsFromSessionStorage: function () {
            var clickedProductIds = ACC.orderform.fetchOrPopulateSessionStorageObj("checkedProducts");
            if (clickedProductIds != null && clickedProductIds != "") {
                return clickedProductIds.join(",");
            }
            return [];
        },

        removeProductIdFromSessionStorage: function (productId) {
            var clickedProductIds = JSON.parse(sessionStorage.getItem("checkedProducts"));
            var ifProductExist = clickedProductIds.indexOf(productId);
            if (ifProductExist != -1) {
                clickedProductIds.splice(ifProductExist, 1);
            }
            sessionStorage.setItem("checkedProducts", JSON.stringify(clickedProductIds));
        },

        // Add product ids search enable checkbox handler
        bindToToggleProductIds: function (addProductIdsCheckBox) {
            addProductIdsCheckBox.on("change", function (event) {
                var checked = $(this).prop("checked");
                ACC.orderform.replaceSearchLabel(checked);
                // move the added product id tags back into the input
                var joinedProductIds = $.map(
                    ACC.orderform.$selectedProductIdsContainer.find(".product-id-tag-box .product-id"),
                    function (index) {
                        return $(index).text()
                    }
                ).join(", ");
                if (joinedProductIds !== "") {
                    ACC.orderform.$productIdsInput.val(joinedProductIds);
                }
                // clear product id tags
                ACC.orderform.$selectedProductIdsContainer.empty();
            });
        },

        // Add product id event handler
        appendProductIdsForSearch: function () {
            ACC.orderform.stripOutInvalidChars();
            var productIds = ACC.orderform.$productIdsInput.val().split(",");
            //  clean product ids
            productIds = $.map(productIds, function (productId, index) {
                return productId.trim();
            });
            productIds = $.unique(productIds);
            var presentProductIds = ACC.orderform.fetchOrPopulateSessionStorageObj("checkedProducts");

            // create the tags by using a template
            $.each(productIds, function (index, productId) {
                var $existingProductId = ACC.orderform.$selectedProductIdsContainer.find("#product-id-" + productId);
                if (productId !== '' && $existingProductId.length == 0) {
                    // Render the product id tag boxes using the template
                    ACC.orderform.$productIdTagBoxTemplate
                        .tmpl({productId: productId, index: index})
                        .appendTo(ACC.orderform.$selectedProductIdsContainer);
                }
                if (presentProductIds.indexOf(productId) == -1) {
                    presentProductIds.push(productId);
                }
            });
            sessionStorage.setItem("checkedProducts", JSON.stringify(presentProductIds));
            // clear the input field
            ACC.orderform.$productIdsInput.val('');
            ACC.orderform.$productIdsInput.focus();
        },


        bindAddToCartClick: function (addToCartBtn) {
            addToCartBtn.click(function () {
                $.ajax({
                    url: ACC.productorderform.$addToCartOrderForm.attr("action"),
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: ACC.orderform.getJSONDataForAddToCart(),
                    async: false,
                    success: function (response) {
                        $(window).off('beforeunload', ACC.productorderform.beforeUnloadHandler);
                        ACC.product.displayAddToCartPopup(response);
                        ACC.orderform.cleanUserQtySelection(ACC.orderform.$userQtyInputSelection);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        // log the error to the console
                        console.log("The following error occured: " + textStatus, errorThrown);
                    }
                });
            });
        },

        getJSONDataForAddToCart: function () {
            var qtyInputs = ACC.orderform.fetchOrPopulateSessionStorageObj("qtyInputs");
            var skusAsJSON = [];
            for (var i = 0; i < qtyInputs.length; i++) {
                if (parseInt(qtyInputs[i].qty) > 0) {
                    skusAsJSON.push({"product": {"code": qtyInputs[i].product}, "quantity": qtyInputs[i].qty});
                }
            }
            return JSON.stringify({"cartEntries": skusAsJSON});
        },


        bindToRemoveProductId: function (removeProductIdButton) {
            ACC.orderform.$selectedProductIdsContainer.on("click", removeProductIdButton, function (event) {
                event.preventDefault();
                var valueToRemove = $(event.target).parents('.js-remove-product-id');
                ACC.orderform.removeProductIdFromSessionStorage($(valueToRemove).children('.product-id').text());
                $(valueToRemove).remove();

                ACC.orderform.$selectedProductIdsContainer.empty();
                ACC.orderform.$enableProductIdsCheckBox.attr("checked", true);
                ACC.orderform.$productIdsInput.val(ACC.orderform.getProductsIdsFromSessionStorage());
                ACC.orderform.$advSearchButton.click();
            });
        },

        cleanUserQtySelection: function (userQtyInputSelection) {
            var qtyInputs = ACC.orderform.fetchOrPopulateSessionStorageObj("qtyInputs");
            if (qtyInputs != null && qtyInputs.length > 0) {
                userQtyInputSelection.each(function () {
                    $(this).prop('value', 0);
                    ACC.orderform.$addToCartBtn.attr('disabled', 'disabled');
                    ACC.orderform.setTotalItemPrice($(this), 0, 0);

                })
            }
            ACC.productorderform.cleanValues();
            ACC.productorderform.resetSelectedVariant();
            ACC.orderform.$totalPrice.html(ACC.productorderform.formatTotalsCurrency('0.00'));
            ACC.orderform.$totalItemsCount.text('0');
            ACC.orderform.$totalPriceValue.val(0);
            ACC.orderform.cleanupQtyInputFromSessionStorage();
        },

        replaceSearchLabel: function (productIdsChecked) {
            var currentLabel = ACC.orderform.$searchCurrentLabel.html();
            var searchByIdsLabel = $("#searchByIdsLabel").val();
            var searchByKeywordLabel = $("#searchByKeywordLabel").val();

            if (productIdsChecked) {
                currentLabel = currentLabel.replace(searchByKeywordLabel, searchByIdsLabel);
            } else {
                currentLabel = currentLabel.replace(searchByIdsLabel, searchByKeywordLabel);
            }
            ACC.orderform.$searchCurrentLabel.html(currentLabel);
        },

        stripOutInvalidChars: function () {
            ACC.orderform.$productIdsInput.val(ACC.orderform.$productIdsInput.val().replace(/[^a-z0-9 ,.\-_]/ig, ''));
        },

        cleanupQtyInputFromSessionStorage: function () {
            sessionStorage.removeItem("qtyInputs");
            sessionStorage.removeItem("totalPrice");
            sessionStorage.removeItem("totalPriceVal");
            sessionStorage.removeItem("totalItems");
        },

        resetPriceCounters: function () {
            ACC.orderform.$totalItemsCount.text(sessionStorage.totalItems);
            ACC.orderform.$totalPrice.html(sessionStorage.totalPrice || ACC.productorderform.formatTotalsCurrency('0.00'));
            ACC.orderform.$totalPriceValue.val(sessionStorage.totalPriceVal || 0);

            //sub-total
            ACC.orderform.fillTotalItemPrice();
        },

        setTotalItemPrice: function (item, quantity, price) {
            if(!item.data('variant-id')) {
                var htmlContent = (quantity > 0) ? ACC.productorderform.formatTotalsCurrency(price * quantity) : '';
                item.parent().next('.item-total').html(htmlContent);
            }
        },

        fillTotalItemPrice: function () {
            if (sessionStorage.qtyInputs !== undefined){
                var qtyInputsJson = JSON.parse(sessionStorage.qtyInputs);
                var $product = {};

                for (var i = 0; i < qtyInputsJson.length; i++) {
                    if (qtyInputsJson[i].qty > 0) {
                        $product = $('.' + qtyInputsJson[i].product);
                        var totalPrice = ACC.productorderform.formatTotalsCurrency($product.siblings('.price').data('variant-price') *  qtyInputsJson[i].qty);
                        $product.parent().siblings('.item-total').html(totalPrice);
                    }
                }
            }
        },

        filterMatchingQtyInputs: function (qtyInputs, dataValueObj) {
            var filteredObjects = $.grep(qtyInputs, function (obj) {
                return dataValueObj.product == obj.product;
            });
            return filteredObjects;
        }
    };
}


ACC.approval = {
    _autoload: [
        "bindToModalActionButton",
        "bindToOrderApprovalDecisionButtons",
        "bindToModalCancelButton"
    ],

    bindToModalActionButton : function() {
        $(document).on('click', '.approverDecisionButton', function() {
            var form = ACC.approval.currentForm;
            let decision = $(this).data('decision');
            form.find('#approverSelectedDecision').attr("value", decision);
            ACC.approval.approveOrder(decision, 'click');
        });
    },

    bindToModalCancelButton : function() {
        $(document).on('click', '.cancelOrderApprovalCommentButton',
            function(e) {
                e.preventDefault();
                ACC.colorbox.close();
            }
        );
    },

    bindToOrderApprovalDecisionButtons : function() {
        ACC.approval.bindToOrderApprovalDecisionButton('.approverDecisionApprovalButton', '.orderApprovalApproveCommentModal');
        ACC.approval.bindToOrderApprovalDecisionButton('.approverDecisionRejectButton', '.orderApprovalRejectCommentModal');
    },

    bindToOrderApprovalDecisionButton : function(decisionButtonSelector, popupModalSelector) {
        $(document).on('click', decisionButtonSelector, function(e) {
            e.preventDefault();
            var form = $(this).closest("form");
            var title = $(this).text().trim();
            var quoteCommentModal = form.find(popupModalSelector);
            ACC.approval.currentForm = form;

            form.find('textarea').val('');
            ACC.approval.isDecisionTaken = false;

            ACC.colorbox.open(title, {
                href : quoteCommentModal,
                inline : true,
                width : "620px",
                onClosed: function() {
                    if (ACC.approval.isDecisionMade) {
                        form.submit();
                    }
                },
                onLoad: function() {
                	var comments = ACC.approval.currentForm.find('#comments');
                	comments.removeClass("border-red");
                	$('.approverDecisionRejectErrorMsg').addClass("hidden");
        			$('.approverDecisionButton').removeClass("disabled");
        			var decisionBtn = ACC.approval.currentForm.find('.approverDecisionButton');
        			if($(decisionBtn).data('decision')=="REJECT"){
        				$('.approverDecisionButton').addClass("disabled");
        			}
                }
            });
        });
    },
    approveOrder: function(type, event){
    	let target =$("#colorbox .orderApprovalRejectCommentModal #comments");
    	let targetVal = target.val();
    	if(type == "REJECT"){
    		if(targetVal.length < 1){
    			target.addClass("border-red");
				$('.approverDecisionRejectErrorMsg').removeClass("hidden");
				$('.approverDecisionButton').addClass("disabled");
    		} else {
    			target.removeClass("border-red");
    			$('.approverDecisionRejectErrorMsg').addClass("hidden");
    			$('.approverDecisionButton').removeClass("disabled");
    			if(event == "click"){
    	            ACC.approval.isDecisionMade = true;
    	            ACC.colorbox.close();
    	    	}
    		}
    	} else if(event == "click"){
            ACC.approval.isDecisionMade = true;
            ACC.colorbox.close();
    	}
    }

};
/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company. All rights reserved.
 */

