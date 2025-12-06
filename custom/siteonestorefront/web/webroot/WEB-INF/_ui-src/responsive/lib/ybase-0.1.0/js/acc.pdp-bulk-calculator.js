ACC.PDPBulkCalculator = {
    _autoload: [
        "bindRadioBtnInputHandler",
        "bindInputButtonHandler",
        "bindShapesClickHandler",
        "bindSubmitClickHandler"
    ],

    resetBulkCalculator: function() {
        // Reset radio button and show/hide elements
        $("#measurements").prop("checked", true);
        $("#sqftGroup").hide();
        $("#calculatorButtons, #calculatorImages, #measurementsGroup").show();
        // Reset all number inputs
        $("#show-bulk-calculator input[type='text']").val("");
        $("#show-bulk-calculator input[type='text']").attr("value", "");
        $(".qty-warning-message").addClass("hidden");
        // Reset text and add ellipsis class to specific elements
        ACC.PDPBulkCalculator.resetCalculatorDetails();
        // Reset product quantity to 1
        $("#show-bulk-calculator input[name='productPostPLPQty']").val(1);
        $("#calculatorAddToCart .js-pdp-add-to-cart").val(1);
    },
    
    // CLOSE-CALCULATOR-ICON
    closeCalculator: function() {
        $("#calculatorButtons #icons").removeClass("active").first().addClass("active");
        $("#calculatorButtons #1").first().trigger("click");
        $("#calculatorInputs input:visible").attr("value", "");
        $("#calculatorInputs input:visible").val("").siblings(".metricLabel").css("visibility", "hidden");
        $('#calculator-overlay').fadeOut(400);
        $('#show-bulk-calculator .footer').css({ right: "0" }).animate({ right: "-100%" }, 400);
        $('#show-bulk-calculator').css({ right: "0" }).animate({ right: "-100%" }, 400, function () {
            $('body').removeClass('no-scroll');
            ACC.PDPBulkCalculator.resetBulkCalculator();
        });
    },

    resetCalculatorDetails: function() {
        const elementsToReset = [
            "subTotalValue", "extraWasteValue", "totalCubicYards",
            "roundsToValue", "area", "weightValue",
            "finalCubicYards", "finalTotal"
        ];
        elementsToReset.forEach(id => {
            const $element = $(`#${id}`);
            $element.text("");
            // Add 'ellipsis' class only if the id is not 'finalCubicYards' or 'finalTotal'
            if (id !== "finalCubicYards" && id !== "finalTotal") {
                $element.addClass("ellipsis");
            }
        });
        $("#disclaimer").addClass("hidden");
        $("#calculatorAddToCart > button").attr("disabled", "disabled");
    },

    bindRadioBtnInputHandler: function() {
        $("#calculatorMenu input").on("change", function () {
            ACC.PDPBulkCalculator.resetCalculatorDetails();
            $("#calculatorInputs input:visible").attr("value", "");
            $("#calculatorInputs input:visible").val("").siblings(".metricLabel").css("visibility", "hidden");
            $("depthValue").attr("value", "");
            $("depthValue").val("");
            if (this.id === "sqft") {
                $("#calculatorButtons").hide();
                $("#calculatorImages").hide();
                $("#measurementsGroup").hide();
                $("#sqftGroup").show();
                $("#areaInput").show();
            } else {
                $("#sqftGroup").hide();
                $("#calculatorButtons").show();
                $("#calculatorImages").show();
                $("#measurementsGroup").show();
            }
        });
    },

    bindInputButtonHandler: function() {
        $(".buttons").on("click", "button", function () {
            $(this).siblings().removeClass("selected");
            $(this).addClass("selected");
            const input = $(this).parent().siblings("input");
            const currentInputValue = parseFloat(input.attr("value"));
            const targetContainer = input.attr('id') !== 'depthValue' ? input.closest('.input') : input.closest('.input-container');
            if ($(this).text().trim() === "in") {
                input.data("suffix", "inches");
                let calculatedValue = (parseFloat((currentInputValue * 12).toFixed(2)).toString().replace(/,/g,'') || '');
                if(!isNaN(calculatedValue) && calculatedValue.trim() !== "") {
                    const parts = calculatedValue.split('.');
                    const formatted = Number(parts[0]).toLocaleString();
                    input.val(parts.length > 1 ? `${formatted}.${parts[1]}` : formatted);
                }
                input.attr("value", parseFloat((currentInputValue * 12).toFixed(2)) || '');
                targetContainer.find(".metricLabel").text("inches");
                targetContainer.find(".metricLabel").css("left", (125 + parseInt((input.val().length * 8.2))) + "px");
            } else {
                if (input.closest("#sqftGroup").is(':visible')) {
                    input.data("suffix", "square feet");
                    targetContainer.find(".metricLabel").text("square feet");
                } else {
                    input.data("suffix", "feet");
                    targetContainer.find(".metricLabel").text("feet");
                }
                let calculatedValue = (parseFloat((currentInputValue / 12).toFixed(2)).toString().replace(/,/g,'') || '');
                if(!isNaN(calculatedValue) && calculatedValue.trim() !== "") {
                    const parts = calculatedValue.split('.');
                    const formatted = Number(parts[0]).toLocaleString();
                    input.val(parts.length > 1 ? `${formatted}.${parts[1]}` : formatted);
                }
                input.attr("value", parseFloat((currentInputValue / 12).toFixed(2)) || '');
                targetContainer.find(".metricLabel").css("left", (125 + parseInt((input.val().length * 8.2))) + "px");
            }
        });
    },

    bindShapesClickHandler: function() {
        $("#calculatorButtons > div").on("click", function (event) {
            const id = event.target.id;
            const $lengthInput = $("#lengthInput");
            const $widthInput = $("#widthInput");
            const $heightInput = $("#heightInput");
            const $sideInput = $("#sideInput");
            const $diameterInput = $("#diameterInput");
            const $sideA = $("#sideA");
            const $sideB = $("#sideB");
        
            const $image = $(`#calculatorImages svg#${id}`);
            const elementsToReset = [
                "subTotalValue", "extraWasteValue", "totalCubicYards",
                "roundsToValue", "area", "weightValue",
                "finalCubicYards", "finalTotal"
            ];
            elementsToReset.forEach(id => {
                const $element = $(`#${id}`);
                $element.text("");
                if (id !== "finalCubicYards" && id !== "finalTotal") {
                    $element.addClass("ellipsis");
                }
            });
            $("#disclaimer").addClass("hidden");
            $("#calculatorAddToCart > button").attr("disabled", "disabled");
            $("#depthValue").val("").siblings(".metricLabel").css("visibility", "hidden");
            $("#calculatorInputs input:visible").val("").siblings(".metricLabel").css("visibility", "hidden");
        
            if (id && $image.length && $(this).length) {
                $(".active").removeClass("active");
                $(this).addClass("active");
                $image.addClass("active");
                if (["1", "3", "6", "8"].includes(id)) {
                    $lengthInput.addClass("active");
                    $widthInput.addClass("active");
                } else if (id === "2") {
                    $diameterInput.addClass("active");
                } else if (id === "4") {
                    $sideA.addClass("active");
                    $sideB.addClass("active");
                    $heightInput.addClass("active");
                } else if (["5", "7"].includes(id)) {
                    $sideInput.addClass("active");
                }
            }
        });
    },
    formatTotal: function(totalVal, element) {
        const totalValStr = parseFloat(totalVal).toFixed(2);
        const totalDigits = totalValStr.replace('.', '').length;
        let formattedTotVal = parseFloat(totalValStr).toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
        let elementFlag = element === '#finalCubicYards' ? true : false;
        if(totalDigits <= 7) {
           $(element) .css('font-size', '36px').text(elementFlag ? formattedTotVal : ('$' + formattedTotVal));
        } else if(totalDigits > 7 & totalDigits <= 9) {
            $(element) .css('font-size', '32px').text(elementFlag ? formattedTotVal : ('$' + formattedTotVal));
        } else if(totalDigits > 9) {
            const scientificNotation = elementFlag ? parseFloat(totalVal).toExponential(2).replace('e+', ' x 10<sup>') + '</sup>' : ('$' + parseFloat(totalVal).toExponential(2).replace('e+', ' x 10<sup>') + '</sup>');
            $(element) .css('font-size', '36px').html(scientificNotation);
        }
    },
    formatSubTotals: function(subTotalVal) {
        const totalValStr = parseFloat(subTotalVal).toFixed(2);
        const totalDigits = totalValStr.replace('.', '').length;
        let formattedTotVal = parseFloat(totalValStr).toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
        if(totalDigits <= 9) {
            return formattedTotVal;
         } else if(totalDigits > 9) {
             const scientificNotation =  parseFloat(subTotalVal).toExponential(2).replace('e+', ' x 10<sup>') + '</sup>';
             return scientificNotation;
         }
    },
    bindSubmitClickHandler: function() {
        $("#calculatorAddToCart").on("click", "button", function(e) {
            $("#addToCartSection button").trigger("click");
        });
    }
};

$(document).ready(function () {
    // Show the "Bulk Calculator" button only if the product code is eligible
    if($(".linktracking-breadcrumb").length){
        (() => {
            let currentCategoryNumber = $(".linktracking-breadcrumb").last()[0].href.split("/").pop();
            const shouldShowBulkCalcButton = [
                //"sh1428113",
                "sh1226",
                "sh1227100",
                "sh1227101",
                "sh1520",
                "sh1521",
                "sh1517110",
                "sh1517117",
                "sh1517115",
                "sh1517123"
            ].indexOf(currentCategoryNumber) !== -1;
            if (shouldShowBulkCalcButton && $("#productSelect").length && !$("#productSelect button").hasClass("js-login-to-buy")
                && $(".bulkCalculatorType").length && $(".bulkCalculatorType").val() !== "" && $(".sellableUomsFlag").val() == "false") {
                if ($(".intervalQtyInfo").is(':visible') || $(".minQtyInfo").is(':visible')) {
                    $(".showBulkCalculatorEntrypoint").addClass("hidden");
                } else {
                    $(".showBulkCalculatorEntrypoint").removeClass("hidden");
                }
            } else {
                $(".showBulkCalculatorEntrypoint").addClass("hidden");
            }
        })();
    }

    $("#showBulkCalculator, #showBulkCalculatorMob").on("click", function (e) {
        $("#show-bulk-calculator input[name='productPostPLPQty']").attr("type", "hidden");
        $('#calculator-overlay').fadeIn(400); // Fade in the dark background
        $('#show-bulk-calculator').css({ right: "-100%" }).animate({ right: "0" }, 400, function () {
            $('#calculatorButtons #icons:first').addClass('active');
        });
        $('#show-bulk-calculator .footer').css({ right: "-100%" }).animate({ right: "0" }, 400);

        $("#lengthSuffix").text("feet");
        $("#pdpBulkAddWasteCost input").prop("checked", true);
        $("#signinId-hidecsp + #show-bulk-calculator").remove();
        $("#sqftGroup").hide();

        // Set unit of measure
        var uomValue = $(".product-uomMeasure").val() || '';
        var unitOfMeasure = uomValue.toLowerCase().includes('ton') ? 'tons' : 'cubic yards';

        // Update all .lineitem labels except the last two
        $(".lineitem:not(:last-child):not(:nth-last-child(2)) label").text(unitOfMeasure);

        // Update the specified element with all caps text
        $("#finalCubicYardsLabel").text(unitOfMeasure);

        function updateButtonState(productPrice) {
            let $button = $("#show-bulk-calculator .footer button");
            if (productPrice < 1) {
                $button.prop('disabled', true);
            } else {
                $button.prop('disabled', false);
            }
        }

        function extractProductPrice() {
            var extractPrice = function () {
                var price = '';
                let isAnonymous = $(".loggedInFlag").val();
                if (isAnonymous == "true") {
                    if ($('.trackRetailPrice').length && $('.trackRetailPrice').val() !== '') {
                        price = $('.trackRetailPrice').val();
                    }
                } else {
                    if ($('.trackCSP').length && $('.trackCSP').val() !== '') {
                        price = $('.trackCSP').val();
                    }
                }
                /* if ($('.quoteUom-CustomerPrice').length) {
                    price = $('.quoteUom-CustomerPrice').val();
                } else if ($('.quoteUom-Price').length) {
                    price = $('.quoteUom-Price').val();
                } else if ($('.uom-dropdown-option .custom-dropdown-label').length) {
                    price = $('.uom-dropdown-option .custom-dropdown-label').first().text().split('/')[0].trim();
                } else if ($('.multipleUomItem .multipleUomItemYourPrice').length) {
                    price = $('.multipleUomItem .multipleUomItemYourPrice').first().text().trim();
                } else if ($('.price').length) {
                    price = $('.price').first().text().trim();
                } else if ($(".yourPriceValue .bulk-price").length) {
                    price = $(".yourPriceValue .bulk-price").last().text().trim();
                } */
                return price;
            };

            var cleanPrice = function (priceString) {
                var cleaned = priceString.replace(/[^\d.]/g, '');
                return parseFloat(cleaned) || 0;
            };

            var extractedPrice = extractPrice();
            return cleanPrice(extractedPrice);
        }

        let productPrice = extractProductPrice();
        if (productPrice < 1) {
            updateButtonState(productPrice);
        }

        /* let loggedIn = $('.loginstatus').val() == '';
        let $button = $("#show-bulk-calculator #addToCartSection button");
        if (loggedIn) {
            $button.prop('disabled', true);
        } else {
            $button.prop('disabled', false);
        } */

        $("#calculatorAddToCart").html($("#productSelect").html());
        $("#calculatorAddToCart > button").attr("class", "btn btn-primary btn-block bulkAddtoCart");
        $("#calculatorAddToCart > button").attr("disabled", "disabled");
    });

    // tracking logic
   let clickCount = 0;
    $(document).on('click','#showBulkCalculator',function(){
     clickCount++
    digitalData.eventData = {
           linktype: "",
           linkName: "Bulk Materials Calculator",
           onClickPageName:$(".siteonepagename").val(),
           clickCount:clickCount
           }
           try {
               _satellite.track("linkClicks");
                if($('#materialsCalculators').is(":visible")){
                _AAData={ 
                        "page":{
                        "pageName" : 'content: bulk materials calculator'
                        },
                        "pathingChannel": "content",
                        "pathingPageName": 'content: bulk materials calculator',
                        eventType: ""

                        }
                        try {
                        _satellite.track('pageload');
                            } catch(e) { }
                
               }
               } catch (e) { }   

              
   });
     
(function () {
    // Recalculate totals with input change and debounce
    let debounceTimeout;

    $(".input input").on("input", function () {
        clearTimeout(debounceTimeout);

        const isAnyInputEmpty = $("#calculatorInputs input:visible").toArray().some(input => !input.value.trim());
        if (isAnyInputEmpty) {
            ACC.PDPBulkCalculator.resetCalculatorDetails();
        }

        const $this = $(this);
        const inputValue = $this.val();
        const metricLabel = $this.siblings(".metricLabel");
        const isNotEmpty = inputValue.length > 0;

        // Update metric type (inches/feet)
        const suffix = $this.data("suffix") || "";
        metricLabel.text(suffix);

        // Toggle metric label visibility
        metricLabel.css("visibility", isNotEmpty ? "visible" : "hidden");

        // Adjust metric label position based on input length
        metricLabel.css("left", `${125 + (inputValue.length * 8.2)}px`);

        // Validate inputs and run recalculation with debounce
        const areAllInputsFilled = $("#calculatorInputs input:visible").toArray().every(input => input.value.trim());
        if (!areAllInputsFilled) return;

        debounceTimeout = setTimeout(recalculate, 200); // Run recalculation after debouncing
    });
})();

function recalculate() {
    function getValueOrDefault(selector) {
        const value = $(selector).attr("value");
        const suffix = $(selector).data("suffix");
        return (value ? (suffix === "feet" || suffix === "square feet" ? parseFloat(value) : parseFloat(value) / 12) : 0);
    }

    const length = getValueOrDefault("#lengthValue");
    const width = getValueOrDefault("#widthValue");
    const depth = getValueOrDefault("#depthValue");
    const area = getValueOrDefault("#areaValue");
    const diameter = getValueOrDefault("#diameterValue");
    const side = getValueOrDefault("#sideValue");
    const sideA = getValueOrDefault("#sideAValue");
    const sideB = getValueOrDefault("#sideBValue");
    const height = getValueOrDefault("#heightValue");

    const fetchActiveShape = () => {
        const activeShape = parseInt($("#calculatorButtons .active div")[0].id) - 1;
        const shapes = [
            "rectangle",
            "circle",
            "rightTrangle",
            "trapezoid",
            "square",
            "oval",
            "equilTriangle",
            "parrallelogram",
        ];
        return shapes[activeShape];
    };

    const calculateRecVolume = (area, depth) => {
        return parseFloat(((area * depth) / 27).toFixed(2));
    };

    const calculateWaste = (volume) => {
        const shouldCalculateWaste = $("#pdpBulkAddWasteCost input").is(":checked");
        if (shouldCalculateWaste) {
            const waste = (volume * 0.10).toFixed(2);
            return parseFloat(waste);
        }
        return 0;
    };

    const getTotalCubicYards = (volume, waste) => {
        const total = (volume + waste).toFixed(2)
        return parseFloat(total);
    };

    const roundUpToWhole = (materialEstimate) => {
        return Math.ceil(materialEstimate);
    };

    const calculateWeight = (weight) => {
        const averageWeightFactors = {
            //sh1428113:410,
            sh1226: 410, // barkandmulch
            sh1211113: 2100, // topsoil
            sh1517110: 2700, // stonewalls
            sh1227100:2100,
            sh1227101:2100,
            sh1520:2650,
            sh1521:2800,
            sh1517121: 2800, //decrock
            sh1517117: 2700, // stonewalls
            sh1517115: 3564, // flagstone
            sh1517111: 3000, // sand & crusher run
            sh1517123: 3000 // river rock
        };
        const categoryCode = $(".linktracking-breadcrumb").last()[0].href.split("/").pop();
        const averageWeightFactor = averageWeightFactors[categoryCode];
        const pounds = weight * averageWeightFactor;
        let calculatedWeight = parseFloat((pounds / 2000).toFixed(2));
        return calculatedWeight;
    };

    const getCircleArea = () => {
        const step1 = diameter / 2;
        const step2 = step1 * step1;
        const step3 = 3.1416 * step2;
        return step3.toFixed(2);
    };

    const getTrapezoidArea = () => {
        const step1 = sideA + sideB;
        const step2 = step1 / 2;
        const step3 = height * step2;
        return step3.toFixed(2);
    };

    const getOvalArea = () => {
        const step1 = length / 2;
        const step2 = width / 2;
        const step3 = 3.1416 * step1 * step2;
        return step3.toFixed(2);
    };

    const getEqlTrnArea = () => {
        const step1 = side * side;
        const step2 = step1 * 0.433;
        return step2.toFixed(2);
    };

    function extractProductPrice() {
        var extractPrice = function () {
            var price = '';
            let isAnonymous = $(".loggedInFlag").val();
            if (isAnonymous == "true") {
                if ($('.trackRetailPrice').length && $('.trackRetailPrice').val() !== '') {
                    price = $('.trackRetailPrice').val();
                }
            } else {
                if ($('.trackCSP').length && $('.trackCSP').val() !== '') {
                    price = $('.trackCSP').val();
                }
            }
            /* if ($('.quoteUom-CustomerPrice').length) {
                price = $('.quoteUom-CustomerPrice').val();
            } else if ($('.quoteUom-Price').length) {
                price = $('.quoteUom-Price').val();
            } else if ($('.uom-dropdown-option .custom-dropdown-label').length) {
                price = $('.uom-dropdown-option .custom-dropdown-label').first().text().split('/')[0].trim();
            } else if ($('.multipleUomItem .multipleUomItemYourPrice').length) {
                price = $('.multipleUomItem .multipleUomItemYourPrice').first().text().trim();
            } else if ($('.price').length) {
                price = $('.price').first().text().trim();
            } else if ($(".yourPriceValue .bulk-price").length) {
                price = $(".yourPriceValue .bulk-price").last().text().trim();
            } */
            return price;
        };

        var cleanPrice = function (priceString) {
            var cleaned = priceString.replace(/[^\d.]/g, '');
            return parseFloat(cleaned) || 0;
        };

        var extractedPrice = extractPrice();
        return cleanPrice(extractedPrice);
    }

    function autoCalculate(volume = 0, waste = 0, totalCubicYards = 1, totalCubicYardsRounded = 1, area = 0, tons = 0) {
        // Helper function to format numbers
        const formatNumber = (num) => {
            // Convert to number and limit decimal places
            let formatted = parseFloat(num);
            // Check if it's a valid number
            if (isNaN(formatted)) return 0;
            // If number is too large, cap it
            //if (formatted > 99999) formatted = 99999;
            // If number is too small, floor at 0
            //if (formatted < 0) formatted = 0;
            // Format to max 2 decimal places
            return formatted.toFixed(2);
        };

        let productPrice = (extractProductPrice());
        const visibleInputs = $("#calculatorInputs input:visible");

        // Validate visible inputs
        if (visibleInputs.length === 0 || visibleInputs.toArray().some(input => isNaN(parseFloat(input.value.trim())))) {
            ["#subTotalValue", "#extraWasteValue", "#totalCubicYards", "#roundsToValue", "#area", "#weightValue"]
                .forEach(selector => $(selector).addClass("ellipsis").text(""));
            $("#finalCubicYards").text("");
            $("#finalTotal").text("");
            $(".pdp-bulk-sidebar input[name='pdpAddtoCartInput']").val("");
            return;
        }

        if ($("#calculatorDisplay .ellipsis").length > 0) {
            $("#calculatorDisplay .ellipsis").removeClass("ellipsis");
            $("#disclaimer").removeClass("hidden");
            if(!$("#productSelect button").is(":disabled")) {
                $("#calculatorAddToCart > button").removeAttr("disabled");
            }
        }

        const calcWaste = $("#pdpBulkAddWasteCost input").is(":checked");
        var uomValue = $(".product-uomMeasure").val() || '';

        const weightST = tons;
        const weightWaste = tons * .10;
        const weightTotal =  tons + (tons * .10);
        const weightRoundsTo =  Math.ceil(tons + (tons * .10));
        const weightLastValue =  tons;
        const valueToUse = uomValue.toLowerCase().includes('ton');
        const volumeInCubicYards = volume;
        // Format all numeric values
        volume = formatNumber(valueToUse ? weightST : formatNumber(volume));
        waste = formatNumber(valueToUse ? weightWaste : formatNumber(waste));
        totalCubicYards = formatNumber(valueToUse ? weightTotal : formatNumber(totalCubicYards));
        totalCubicYardsRounded = formatNumber(valueToUse ? (calcWaste ? weightRoundsTo : Math.ceil(tons)) : totalCubicYardsRounded);
        area = formatNumber(area);
        tons = formatNumber(tons);
        
        // Update DOM values with formatted numbers
        $("#subTotalValue").html(ACC.PDPBulkCalculator.formatSubTotals(volume));
        $("#extraWasteValue").html(ACC.PDPBulkCalculator.formatSubTotals(calcWaste ? waste : "0.00"));
        $("#totalCubicYards").html(ACC.PDPBulkCalculator.formatSubTotals(calcWaste ? totalCubicYards : (totalCubicYards - waste)));
        $("#roundsToValue").html(ACC.PDPBulkCalculator.formatSubTotals(totalCubicYardsRounded));
        $("#area").html(ACC.PDPBulkCalculator.formatSubTotals(area));

        $('.line-item-label').last().text(valueToUse ? "Volume" : "Weight");
        $("#weightValue").html(ACC.PDPBulkCalculator.formatSubTotals(valueToUse ? (calcWaste ? (volumeInCubicYards + (volumeInCubicYards * .10)) : volumeInCubicYards) : (calcWaste ? weightTotal : tons)));
        $('#weightValue + label').text(valueToUse ? "cubic yards" : "tons");

        ACC.PDPBulkCalculator.formatTotal(totalCubicYardsRounded, '#finalCubicYards');
        // Calculate final total with formatted numbers
        const finalTotal = (productPrice * totalCubicYardsRounded);
        ACC.PDPBulkCalculator.formatTotal(finalTotal, '#finalTotal');
        $("#customerSpecificPrice").text(finalTotal);

        let finalQuantityInput = parseInt(totalCubicYardsRounded) > 99999 ? 99999 : (parseInt(totalCubicYardsRounded) < 1 ? 1 : parseInt(totalCubicYardsRounded));
        if (parseInt(totalCubicYardsRounded) > 99999) {
            $(".qty-warning-message").removeClass("hidden");
            $("#calculatorAddToCart > button").attr("disabled", "disabled");
        } else {
            if (parseInt(totalCubicYardsRounded) > 0) {
                if(!$("#productSelect button").is(":disabled")) {
                    $("#calculatorAddToCart > button").removeAttr("disabled");
                }
            } else {
                $("#calculatorAddToCart > button").attr("disabled", "disabled");
            }
            $(".qty-warning-message").addClass("hidden");
        }
        $('input#qty.qty.js-qty-selector-input').val(finalQuantityInput).trigger('change');
        $("input[name='pdpAddtoCartInput']").val(finalQuantityInput).trigger('change');
        $("#cboxLoadedContent .customerSpecificPrice .atc-price-analytics").text(finalTotal);
    }

    if ($("#calculatorInputs input:visible")[0].id === 'areaValue') {
        const areaForSqFt = area;
        const volumeForSqFt = calculateRecVolume(areaForSqFt, depth);
        const wasteForSqFt = calculateWaste(volumeForSqFt);
        const totalCubicYardsForSqFt = getTotalCubicYards(volumeForSqFt, wasteForSqFt);
        const totalCubicYardsRoundedForSqFt = roundUpToWhole(totalCubicYardsForSqFt);
        const tonsForSqFt = calculateWeight(volumeForSqFt);
        autoCalculate(volumeForSqFt, wasteForSqFt, totalCubicYardsForSqFt, totalCubicYardsRoundedForSqFt, areaForSqFt, tonsForSqFt);
        return;
    } else {
        switch (fetchActiveShape()) {
            case "rectangle":
                const areaRec = length * width;
                const volumeForRec = calculateRecVolume(areaRec, depth);
                const wasteExtraRec = calculateWaste(volumeForRec);
                const totalCubicYardsRec = getTotalCubicYards(volumeForRec, wasteExtraRec);
                const totalCubicYardsRoundedRec = roundUpToWhole(totalCubicYardsRec);
                const tonsRec = calculateWeight(volumeForRec);
                autoCalculate(volumeForRec, wasteExtraRec, totalCubicYardsRec, totalCubicYardsRoundedRec, areaRec, tonsRec);
                break;
            case "circle":
                const areaCircle = getCircleArea();
                const volumeForCir = calculateRecVolume(areaCircle, depth);
                const wasteExtraCir = calculateWaste(volumeForCir);
                const totalCubicYardsCir = getTotalCubicYards(volumeForCir, wasteExtraCir);
                const totalCubicYardsRoundedCir = roundUpToWhole(totalCubicYardsCir);
                const tonsCircle = calculateWeight(volumeForCir);
                autoCalculate(volumeForCir, wasteExtraCir, totalCubicYardsCir, totalCubicYardsRoundedCir, areaCircle, tonsCircle)
                break;
            case "rightTrangle":
                const areaRtTriangle = (length * width) / 2;
                const volumeForRtTriangle = calculateRecVolume(areaRtTriangle, depth);
                const wasteExtraRtTriangle = calculateWaste(volumeForRtTriangle);
                const totalCubicYardsRtTriangle = getTotalCubicYards(volumeForRtTriangle, wasteExtraRtTriangle);
                const totalCubicYardsRoundedRtTriangle = roundUpToWhole(totalCubicYardsRtTriangle);
                const tonsRtTriangle = calculateWeight(volumeForRtTriangle);
                autoCalculate(volumeForRtTriangle, wasteExtraRtTriangle, totalCubicYardsRtTriangle, totalCubicYardsRoundedRtTriangle, areaRtTriangle, tonsRtTriangle)
                break;
            case "trapezoid":
                const areaTrpzd = getTrapezoidArea();
                const volumeForTrpzd = calculateRecVolume(areaTrpzd, depth);
                const wasteExtraTrpzd = calculateWaste(volumeForTrpzd);
                const totalCubicYardsTrpzd = getTotalCubicYards(volumeForTrpzd, wasteExtraTrpzd);
                const totalCubicYardsRoundedTrpzd = roundUpToWhole(totalCubicYardsTrpzd);
                const tonsTrpzd = calculateWeight(volumeForTrpzd);
                autoCalculate(volumeForTrpzd, wasteExtraTrpzd, totalCubicYardsTrpzd, totalCubicYardsRoundedTrpzd, areaTrpzd, tonsTrpzd);
                break;
            case "square":
                const areaSquare = side * side;
                const volumeForSquare = calculateRecVolume(areaSquare, depth);
                const wasteExtraSquare = calculateWaste(volumeForSquare);
                const totalCubicYardsSquare = getTotalCubicYards(volumeForSquare, wasteExtraSquare);
                const totalCubicYardsRoundedSquare = roundUpToWhole(totalCubicYardsSquare);
                const tonsSquare = calculateWeight(volumeForSquare)
                autoCalculate(volumeForSquare, wasteExtraSquare, totalCubicYardsSquare, totalCubicYardsRoundedSquare, areaSquare, tonsSquare);
                break;
            case "oval":
                const areaOval = getOvalArea();
                const volumeForOval = calculateRecVolume(areaOval, depth);
                const wasteExtraOval = calculateWaste(volumeForOval);
                const totalCubicYardsOval = getTotalCubicYards(volumeForOval, wasteExtraOval)
                const totalCubicYardsRoundedOval = roundUpToWhole(totalCubicYardsOval);
                const tonsOval = calculateWeight(volumeForOval)
                autoCalculate(volumeForOval, wasteExtraOval, totalCubicYardsOval, totalCubicYardsRoundedOval, areaOval, tonsOval)
                break;
            case "equilTriangle":
                const areaEqlTrn = getEqlTrnArea();
                const volumeForEqlTrn = calculateRecVolume(areaEqlTrn, depth);
                const wasteExtraEqlTrn = calculateWaste(volumeForEqlTrn);
                const totalCubicYardsEqlTrn = getTotalCubicYards(volumeForEqlTrn, wasteExtraEqlTrn);
                const totalCubicYardsRoundedEqlTrn = roundUpToWhole(totalCubicYardsEqlTrn);
                const tonsEqlTrn = calculateWeight(volumeForEqlTrn);
                autoCalculate(volumeForEqlTrn, wasteExtraEqlTrn, totalCubicYardsEqlTrn, totalCubicYardsRoundedEqlTrn, areaEqlTrn, tonsEqlTrn);
                break;
            case "parrallelogram":
                const areaPllgrm = length * width;
                const volumeForPllgrm = calculateRecVolume(areaPllgrm, depth);
                const wasteExtraPllgrm = calculateWaste(volumeForPllgrm);
                const totalCubicYardsPllgrm = getTotalCubicYards(volumeForPllgrm, wasteExtraPllgrm);
                const totalCubicYardsRoundedPllgrm = roundUpToWhole(totalCubicYardsPllgrm);
                const tonsPllgrm = calculateWeight(volumeForPllgrm);
                autoCalculate(volumeForPllgrm, wasteExtraPllgrm, totalCubicYardsPllgrm, totalCubicYardsRoundedPllgrm, areaPllgrm, tonsPllgrm);
                break;
            default:
                break;
        }
    }
}


// close calculator icon
$("#closeBulkCalculator").click(ACC.PDPBulkCalculator.closeCalculator);

// calculator background
$('#calculator-overlay').click(function (e) {
    if (!$(e.target).closest('#show-bulk-calculator').length) {
        ACC.PDPBulkCalculator.closeCalculator();
    }
});

// add to cart click
$('#showAddtoCart').click(function (e) {
    ACC.PDPBulkCalculator.closeCalculator();
});

// waste feature
$("#pdpBulkAddWasteCost input").on("change", function () {
    recalculate();
});

// tooltip
$("#pdpBulkTooltip").on("mouseenter", (event) => {
    event.stopPropagation();
    $('#tooltip').css({ display: "block" });
}).on("mouseleave", (event) => {
    event.stopPropagation();
    $('#tooltip').css({ display: "none" }); // Hide the tooltip when mouse leaves
});
});