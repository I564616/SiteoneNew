ACC.calculator = {
    _autoload: [
		"removeView",
        "updateView"
	],

    removeView: function() {
       if( $('.materialsCalculators').is(":Visible") != true)
       {
            $('.materialsCalculators').remove();
       }
       if( $('.mobile-materialCal-wrapper').is(":Visible") != true)
       {
            $('.mobile-materialCal-wrapper').remove();
       }
    },
    flagStoneCalculator: function() {  
    if($(".flagStoneCalculate").hasClass("disabled") == false){
        var LengthMultiplier = 1;
        var WidthMultiplier = 1;
        var ThicknessValue = 1;
        if( $('.materialsCalculators').is(":Visible") == true)
        {    
            if ($('#lengthUnitDropdown').val() == '1'){
                LengthMultiplier = 12;
            }
            if ($('#widthUnitDropdown').val() == '1'){
                 WidthMultiplier = 12;
            }
            ThicknessValue = $('#select-option').val();
        } 
        else{
            if ($('.lengthUnitDropdown').val() == 'feet'){
                LengthMultiplier = 12;
            }
            if ($('.widthUnitDropdown').val() == 'feet'){
                 WidthMultiplier = 12;
            }
            ThicknessValue = $('.depthUnitDropdown').val();
        }
        
        const length = $('.length').val() * LengthMultiplier;
        const width = $('.width').val() * WidthMultiplier;
        const thickness = ThicknessValue;
     
        //  Calculate square feet
        const sqInches = length * width;
        var divisorMin;
        var  divisorMax;
    
        if (thickness == 1){
            divisorMin = 120;
            divisorMax = 120;
        }
        else{
            divisorMin = 70;
            divisorMax = 70;
        }
        const sqFeet = sqInches * (1/144);
        const flagstoneRange = [(sqFeet/divisorMax).toFixed(1), (sqFeet/divisorMin).toFixed(1)];
        const cuInches = length * width * thickness;
        const squareFeet = document.getElementsByClassName('squareFeet')[0];
        const estimatedTons = document.getElementsByClassName('estimatedTons')[0];
        digitalData.eventData = {
        	"linkName": "Calculate",
        	"linktype": "",
        	"onClickPageName": "content: project calculators: Flag Stone Calculator Page"
        }
        try {
        	_satellite.track('linkClicks');
        } catch (e) { }
        _AAData.projectCalculator = {};
        if(flagstoneRange[0]===flagstoneRange[1]){
            squareFeet.innerText = sqFeet.toFixed(1);
            estimatedTons.innerText = flagstoneRange[0];
            _AAData.projectCalculator.calculatorMeasurements = 'sq.ft: ' + sqFeet.toFixed(1) + ' | tons: ' + flagstoneRange[0];
        }
        else{
            squareFeet.innerText = sqFeet.toFixed(1);
            estimatedTons.innerText = flagstoneRange[0] + " - " + flagstoneRange[1];
            _AAData.projectCalculator.calculatorMeasurements = 'sq.ft: ' + sqFeet.toFixed(1) + ' | tons: ' + flagstoneRange[0] + ' - ' + flagstoneRange[1];
        }
    }
    },
     CalculateFlagstone: function () {
  

  const pageName = $(".pagename").val() || "";

  const thickness = parseFloat($('#thickness').val()) || 1;
  const length = ACC.calculator.cleanFormattedNumber($('#length').val());
  const width = ACC.calculator.cleanFormattedNumber($('#width').val());

    const lengthUnit = $('#length')
      .closest('#projectCalcInput')
      .find('.toggle-unit.active')
      .data('unit')
      
    
    const widthUnit = $('#width')
      .closest('#projectCalcInput')
      .find('.toggle-unit.active')
      .data('unit')
      
      
    const lengthInFeet = lengthUnit === 'inches' ? length /12 : length
    const widthInFeet = widthUnit === 'inches' ? width /12 : width

    const areaSqFt = lengthInFeet * widthInFeet
    const areaSqIn = areaSqFt * 144
    const areaSqYd = areaSqFt / 9

    const areaUnit = $('.areaDropdownBtn').val() || 'sqft'
    const volumeUnit = $('.volumeDropdownBtn').val() || 'cbft'
    const weightUnit = $('.weightDropdownBtn').val() || 'pnds'  

    const format = ACC.calculator.formatNumber
    let thicknessFt,volumeCubicFeet,volumeCuFt,volumeCuYd,volumeCuIn,tons,lbs;
    if (pageName === 'Stone Walls Calculator Page') {
        thicknessFt=(thickness==3)?(8/12):parseFloat(thickness)
     volumeCubicFeet = areaSqFt * thicknessFt;
     volumeCuFt=parseFloat(volumeCubicFeet.toFixed(2))
      volumeCuYd = volumeCuFt / 27;
      volumeCuIn = volumeCuFt * 1728;

     lbs = volumeCuYd * 2700;  
     tons = lbs / 2000;
   }else{
    volumeCuFt = areaSqFt * (thickness / 12);
    volumeCuYd = volumeCuFt / 27;
    volumeCuIn = areaSqIn * thickness;

	lbs = volumeCuYd * 3564;  
	tons = lbs / 2000;
   
   }

    const areaMap = {
      sqft: format(areaSqFt),
      sqin: format(areaSqIn),
      sqyd: format(areaSqYd)
    }
    const volumeMap = {
      cbft: format(volumeCuFt),
      cbyd: format(volumeCuYd),
      cbin:format(volumeCuIn)
    }
    const weightMap = {
      pnds: format(lbs),
      tns: format(tons)
    }

    $('#areaDropdown #value').css('font-size', '40px').html(areaMap[areaUnit] || '0')
    $('#volumeDropdown #value').css('font-size', '40px').html(volumeMap[volumeUnit] || '0')
    $('#weightDropdown #value').css('font-size', '40px').html(weightMap[weightUnit] || '0')
  },
  
 cleanFormattedNumber : function (num) {
  if (!num) return 0;

  if (typeof num === "string") {
    num = num.replace(/,/g, "").trim();
  }

  const parsed = parseFloat(num);
  return isNaN(parsed) ? 0 : parsed;
},

 formatNumber : function(num) {
  const parsed = parseFloat(num);

  if (parsed === 0) return '0';

 
  if (parsed >= 0.001 && parsed <= 9999999) {
    return parsed % 1 ===0 ? parsed.toLocaleString()
    : parsed.toLocaleString(undefined, {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
  }

 
  return parsed.toExponential(2)
    .replace('e+', ' x 10<sup>')
    .replace('e-', ' x 10<sup>-') + '</sup>';
},
    stoneWallsCalculator: function () {
        if ($(".stoneWallCalculate").hasClass("disabled") == false) {
            var thickness = $('#select-option').val();
            var height = $('#height').val();
            var heightUnit = $('#heightUnit').val();
            var width = $('#width').val();
            var widthUnit = $('#widthUnit').val();

            var heightInFeet = ACC.calculator.convertToFeet(height, heightUnit);
            var widthInFeet = ACC.calculator.convertToFeet(width, widthUnit);

            var area = heightInFeet * widthInFeet;
            var weightMapping = {
                1: 160,
                2: 175,
                3: 135
            };
            var stoneWeightPerCubicFoot = weightMapping[thickness] || 0;
            var totalWeightTons = (area * stoneWeightPerCubicFoot) / 2000;
            if($('#materialsCalculators').length) {
                ACC.calculator.adjustFontSizeAndText(area.toFixed(2), 'squareFeet');
                ACC.calculator.adjustFontSizeAndText(totalWeightTons.toFixed(2), 'weightInTons');
            } else {
                document.getElementsByClassName('squareFeet')[0].innerText = area.toFixed(2);
                document.getElementsByClassName('weightInTons')[0].innerText = totalWeightTons.toFixed(2);
            }
            digitalData.eventData = {
                "linkName": "Calculate",
                "linktype": "",
                "onClickPageName": "content: project calculators: Stone Walls Calculator Page"
              }
              try {
                _satellite.track('linkClicks');
              } catch (e) { }
            _AAData.projectCalculator = {};
            _AAData.projectCalculator.calculatorMeasurements = 'sq.ft: ' + area.toFixed(2) + ' | tons: ' + totalWeightTons.toFixed(2);
        }
    },
    convertToFeet: function(value, unit) {
        switch(unit) {
            case 'inches':
                return value / 12;
            case 'feet':
                return value;
            case 'yards':
                return value * 3;
            default:
                return 0;
        }
    },
    roadbaseFillCalculator: function () {
        if ($(".roadBasefillCalculate").hasClass("disabled") == false) {
            var LengthMultiplier = 1;
            var WidthMultiplier = 1;
            var DepthMultiplier = 1;
            if ($('.materialsCalculators').is(":Visible") == true) {
                if ($('#lengthUnitDropdown').val() == '1') {
                    LengthMultiplier = 12;
                }
                if ($('#widthUnitDropdown').val() == '1') {
                    WidthMultiplier = 12;
                }
                if ($('#depthUnitDropdown').val() == '1') {
                    DepthMultiplier = 12;
                }
            } else {
                if ($('.lengthUnitDropdown').val() == 'feet') {
                    LengthMultiplier = 12;
                }
                if ($('.widthUnitDropdown').val() == 'feet') {
                    WidthMultiplier = 12;
                }
                if ($('.depthUnitDropdown').val() == 'feet') {
                    DepthMultiplier = 12;
                }
            }
            const length = $('.length').val() * LengthMultiplier;
            const width = $('.width').val() * WidthMultiplier;
            const thickness = $('.depth').val() * DepthMultiplier;

            const cuInches = length * width * thickness;
            const cuFeet = (length / 12) * (width / 12) * (thickness / 12);
            const sqFeet = (length / 12) * (width / 12);
            const cuYards = cuFeet * (1 / 27);
            const lbsOfFill = cuYards * 2565.415;
            const lbsOfFillMax = lbsOfFill * 1.4;
            const tonsOfFill = (lbsOfFill / 2000).toFixed(1);
            const tonsOfFillMax = (tonsOfFill * 1.06).toFixed(1);
            const estimatedTons = (tonsOfFill === tonsOfFillMax) ? tonsOfFill : tonsOfFill + ' - ' + tonsOfFillMax;
            if($('#materialsCalculators').length) {
                ACC.calculator.adjustFontSizeAndText(sqFeet.toFixed(2), 'squareFeet');
                ACC.calculator.adjustFontSizeAndText(estimatedTons, 'estimatedTons');
            } else {
                document.getElementsByClassName('squareFeet')[0].innerText = sqFeet.toFixed(2);
                document.getElementsByClassName('estimatedTons')[0].innerText = estimatedTons;
            }
            digitalData.eventData = {
                "linkName": "Calculate",
                "linktype": "",
                "onClickPageName": "content: project calculators: Road Basefill Calculator Page"
              }
              try {
                _satellite.track('linkClicks');
              } catch (e) { }
            _AAData.projectCalculator = {};
            _AAData.projectCalculator.calculatorMeasurements = 'sq.ft: ' + sqFeet.toFixed(2) + ' | tons: ' + estimatedTons;
        }
    },
    decorativeRockCalculator: function () {
        if ($(".decorativeRockCalculate").hasClass("disabled") == false) {
            var LengthMultiplier = 1;
            var WidthMultiplier = 1;
            var DepthMultiplier = 1;
            if ($('.materialsCalculators').is(":Visible") == true) {
                if ($('#lengthUnitDropdown').val() == '1') {
                    LengthMultiplier = 12;
                }
                if ($('#widthUnitDropdown').val() == '1') {
                    WidthMultiplier = 12;
                }
                if ($('#depthUnitDropdown').val() == '1') {
                    DepthMultiplier = 12;
                }
            } else {
                if ($('.lengthUnitDropdown').val() == 'feet') {
                    LengthMultiplier = 12;
                }
                if ($('.widthUnitDropdown').val() == 'feet') {
                    WidthMultiplier = 12;
                }
                if ($('.depthUnitDropdown').val() == 'feet') {
                    DepthMultiplier = 12;
                }
            }

            const length = $('.length').val() * LengthMultiplier;
            const width = $('.width').val() * WidthMultiplier;
            const thickness = $('.depth').val() * DepthMultiplier;

            const cuFeet = (length / 12) * (width / 12) * (thickness / 12);
            const sqFeet = (length / 12) * (width / 12);
            const tonnage = (cuFeet / 21.6).toFixed(1);
            const estimatedTons = (tonnage * 1.15).toFixed(1) + ' - ' + (tonnage * 1.25).toFixed(1);
            if($('#materialsCalculators').length) {
                ACC.calculator.adjustFontSizeAndText(sqFeet.toFixed(2), 'squareFeet');
                ACC.calculator.adjustFontSizeAndText(estimatedTons, 'estimatedTons');
            } else {
                document.getElementsByClassName('squareFeet')[0].innerText = sqFeet.toFixed(2);
                document.getElementsByClassName('estimatedTons')[0].innerText = estimatedTons;
            }
            digitalData.eventData = {
                "linkName": "Calculate",
                "linktype": "",
                "onClickPageName": "content: project calculators: Decorative Rock Calculator Page"
            }
            try {
            	_satellite.track('linkClicks');
          	} catch (e) { }
            _AAData.projectCalculator = {};
            _AAData.projectCalculator.calculatorMeasurements = 'sq.ft: ' + sqFeet.toFixed(2) + ' | tons: ' + estimatedTons;
        }
    },
    topSoilCalculator: function() {  
        if($(".topSoilCalculate").hasClass("disabled") == false){
            var LengthMultiplier = 1;
            var WidthMultiplier = 1;
            var DepthMultiplier = 1;
            if( $('.materialsCalculators').is(":Visible") == true)
            { 
            if ($('#lengthUnitDropdown').val() == '1'){
                LengthMultiplier = 12;
            }
            if ($('#widthUnitDropdown').val() == '1'){
                 WidthMultiplier = 12;
            }
            if ($('#depthUnitDropdown').val() == '1'){
                DepthMultiplier = 12;
            }
        }else{
            if ($('.lengthUnitDropdown').val() == 'feet'){
                LengthMultiplier = 12;
            }
            if ($('.widthUnitDropdown').val() == 'feet'){
                 WidthMultiplier = 12;
            }
            if ($('.depthUnitDropdown').val() == 'feet'){
                DepthMultiplier = 12;
            }
        }
            
        const length = $('.length').val() * LengthMultiplier;
        const width = $('.width').val() * WidthMultiplier;
        const thickness = $('.depth').val() * DepthMultiplier;
        //  Calculate square feet
        const cuInches = length * width * thickness;
        const cuFeet = (length/12) * (width/12) * (thickness/12);
        const cuYards =  (cuFeet * (1/27)).toFixed(1);

        const squareFeet = document.getElementsByClassName('squareFeet')[0];
        const estimatedTons = document.getElementsByClassName('estimatedTons')[0];

        var topsoilMultiplier = $('#select-option').val();
        if( $('#mob-flagstone-option').is(":Visible") == true){
           topsoilMultiplier = $('#mob-flagstone-option').val();
        }
		_AAData.projectCalculator = {};
        if(topsoilMultiplier > -1){
            const materialWeight = cuYards * topsoilMultiplier;
            if(materialWeight > 2000){
                const tonnage = (materialWeight/2000).toFixed(1);
                squareFeet.innerText = cuYards;
                estimatedTons.innerText = tonnage;
                _AAData.projectCalculator.calculatorMeasurements = 'cu.ft: ' + cuYards + ' | tons: ' + tonnage;
            }
            else{
                squareFeet.innerText = cuYards;
                estimatedTons.innerText = materialWeight;
                _AAData.projectCalculator.calculatorMeasurements = 'cu.ft: ' + cuYards + ' | tons: ' + materialWeight;
            }
        }
        else{
            squareFeet.innerText = cuYards;
            estimatedTons.innerText = 0.0;
            _AAData.projectCalculator.calculatorMeasurements = 'cu.ft: ' + cuYards + ' | tons: 0.0';
        }
        digitalData.eventData = {
                "linkName": "Calculate",
                "linktype": "",
                "onClickPageName": "content: project calculators: Top Soil Calculator Page"
      	}
      	try {
        	_satellite.track('linkClicks');
      	} catch (e) { }
        }
        },
        barkMulchBedCalculator: function () {
            if ($(".barkMulchBedCalculate").hasClass("disabled") == false) {
                var LengthMultiplier = 1;
                var WidthMultiplier = 1;
                var DepthMultiplier = 1;
                if ($('.materialsCalculators').is(":Visible") == true) {
                    if ($('#lengthUnitDropdown').val() == '1') {
                        LengthMultiplier = 12;
                    }
                    if ($('#widthUnitDropdown').val() == '1') {
                        WidthMultiplier = 12;
                    }
                    if ($('#depthUnitDropdown').val() == '1') {
                        DepthMultiplier = 12;
                    }
                } else {
                    if ($('.lengthUnitDropdown').val() == 'feet') {
                        LengthMultiplier = 12;
                    }
                    if ($('.widthUnitDropdown').val() == 'feet') {
                        WidthMultiplier = 12;
                    }
                    if ($('.depthUnitDropdown').val() == 'feet') {
                        DepthMultiplier = 12;
                    }
                }
                const length = $('.length').val() * LengthMultiplier;
                const width = $('.width').val() * WidthMultiplier;
                const thickness = $('.depth').val() * DepthMultiplier;
    
                const cuInches = length * width * thickness;
                const cuFeet = (length / 12) * (width / 12) * (thickness / 12);
                const cuYards = ((cuFeet * (1 / 27)) * 1.1).toFixed(1);
                document.getElementsByClassName('cubicYards')[0].innerText = cuYards;
                var mulchMultiplier = $('#select-option').val();
                if ($('#mob-barkmulch-option').is(":Visible") == true) {
                    mulchMultiplier = $('#mob-barkmulch-option').val();
                }
                _AAData.projectCalculator = {};
                if(mulchMultiplier > -1) {
                    const materialWeight = cuYards * mulchMultiplier;
                    const estimatedTons = document.getElementsByClassName('estimatedTons')[0];
                    if (materialWeight > 2000) {
                        const tonnage = (materialWeight / 2000).toFixed(1);
                        estimatedTons.innerText = tonnage;
                        _AAData.projectCalculator.calculatorMeasurements = 'cu.yd: ' + cuYards + ' | tons: ' + tonnage;
                    } else {
                        estimatedTons.innerText = materialWeight.toFixed(1);
                        _AAData.projectCalculator.calculatorMeasurements = 'cu.yd: ' + cuYards + ' | tons: ' + materialWeight.toFixed(1);
                    }
                }
                digitalData.eventData = {
	                "linkName": "Calculate",
	                "linktype": "",
	                "onClickPageName": "content: project calculators: Bark Mulch Calculator Page"
	            }
	            try {
	            	_satellite.track('linkClicks');
	            } catch (e) { }
            }
        },
    validateAndCalculate: function(input) {
        const $input = $(input);
        let value = $input.val();
        value = value.replace(/\s+[^\s]+$/, '');
        const regex = /^\d{0,4}(\.\d{0,2})?$/;
        if(!regex.test(value)) {
            value = value.substring(0, value.length - 1);
        }
        $input.val(value);
    },
    restrictInput: function(event) {
        const allowedKeys = ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete', 'Enter', '.'];
        if(allowedKeys.includes(event.key) || /[0-9]/.test(event.key)) {
            return true;
        }
        event.preventDefault();
        return false;
    },
    handleSuffix: function(input, add) {
        const $input = $(input);
        const suffix = $input.data('suffix');
        let value = $input.val().trim();
        if(add) {
            if(value && !value.endsWith(' ' + suffix)) {
                $input.val(value + ' ' + suffix);
            }
        } else {
            if(value.endsWith(' ' + suffix)) {
                $input.val(value.slice(0, -suffix.length - 1).trim());
            }
        }
    },
    calculateVoltageAtLastFixture: function(resistance, runLength, totalVA, voltageTap) {
        const loadCurrent = totalVA / voltageTap;
        const voltageDrop = (2 * loadCurrent * resistance * runLength / 1000);
        return voltageTap - voltageDrop;
    },
    barChart: null,
    selectedAWG: "6 AWG",
    isDesktopView: true,
    voltageDropCalculator: function () {
        var resistanceValue = $("#guage").val();
        var selGuageValue = $("#guage").text() || "6 AWG";
        if($(".mobile-materialCal-wrapper").is(":visible")) {
            selGuageValue = $("#guage option:selected").text();
            ACC.calculator.isDesktopView = false;
        }
        var voltageTap = $("#voltageTap").val();
        var lengthValue = $("#lengthValue").val().replace(/\s+[^\s]+$/, '');
        var totalVA = $("#totalVA").val().replace(/\s+[^\s]+$/, '');
        if (!resistanceValue || !voltageTap || (lengthValue.trim() == "" || parseFloat(lengthValue.trim()) == 0) || totalVA.trim() == "" || parseFloat(totalVA.trim()) == 0) {
            document.getElementsByClassName('voltageDropVal')[0].innerText = "0.00v";
            document.getElementsByClassName('voltageDropPctVal')[0].innerText = "0.00%";
            document.getElementsByClassName('voltageAtLastFixtureVal')[0].innerText = "0.00v";
            $(".status-section").children().addClass("hidden");
            $('.chart-container').addClass('hidden');
            return;
        }
        var loadCurrent = totalVA / voltageTap;
        var voltageDrop = (2 * loadCurrent * resistanceValue * lengthValue / 1000);
        var voltageDropPercentage = (voltageDrop / voltageTap) * 100;
        var voltageAtLastFixture = voltageTap - voltageDrop;
        document.getElementsByClassName('voltageDropVal')[0].innerText = voltageDrop.toFixed(2) + "v";
        document.getElementsByClassName('voltageDropPctVal')[0].innerText = voltageDropPercentage.toFixed(2) + "%";
        document.getElementsByClassName('voltageAtLastFixtureVal')[0].innerText = voltageAtLastFixture.toFixed(2) + "v";
        $(".low-voltage-msg").addClass("hidden");
        if (voltageAtLastFixture.toFixed(2) >= 10.5) {
            $(".good").removeClass("hidden").siblings().addClass("hidden");
        } else if (voltageAtLastFixture.toFixed(2) >= 10 && voltageAtLastFixture.toFixed(2) < 10.5) {
            $(".ok").removeClass("hidden").siblings().addClass("hidden");
        } else {
            $(".low").removeClass("hidden").siblings().addClass("hidden");
            $(".low-voltage-msg").removeClass("hidden");
        }
        ACC.calculator.renderBarGraph(selGuageValue.trim());
    },
    getAWGRange: function(selectedAWG) {
        const awgList = ["6 AWG","8 AWG","10 AWG","12 AWG","14 AWG","16 AWG","18 AWG","20 AWG"];
        const index = awgList.indexOf(selectedAWG);
        if(index === -1) return [];
        let start = Math.max(0, index - 2);
        let end = Math.min(awgList.length, start + 5);
        if(end - start < 5) {
            start = Math.max(0, end - 5);
        }
        return awgList.slice(start, end);
    },
    initializeBarChart: function() {
        const chartData = {
            labels: [],
            datasets: [{
                data: [],
                backgroundColor: [],
                borderColor: [],
                hoverBackgroundColor: [],
                borderWidth: 1,
                borderRadius: 5,
                borderSkipped: false,
                barThickness: 31
            }]
        };
        const options = {
            indexAxis: 'y',
            scales: {
                x: {
                    min: (ACC.calculator.isDesktopView) ? 8.5 : 8,
                    max: 11.5,
                    grid: {
                        color: function(context) {
                            if(context.tick.value === 8) {
                                return 'transparent';
                            }
                            return '#999999';
                        },
                        borderDash: [5, 5],
                        drawBorder: false,
                        drawOnChartArea: true,
                        drawTicks: false,
                        z: 1
                    },
                    ticks: {
                        stepSize: 0.5,
                        color: '#414244',
                        padding: 15,
                        callback: function(value) {
                            if (value < 8.5) return "";
                            return (value % 1 === 0) ? value + 'V' : value.toFixed(1) + 'V';
                        },
                        font : {
                            family: 'Arial',
                            size: ACC.calculator.isDesktopView ? 14 : 12,
                            weight: 400
                        }
                    },
                    offset: true
                },
                y: {
                    grid: {
                        drawBorder: false,
                        drawOnChartArea: false,
                        drawTicks: false,
                        borderDash: [5, 5]
                    },
                    ticks: {
                        font: {
                            family: 'Arial',
                            size: ACC.calculator.isDesktopView ? 16 : 14,
                            weight: function(context) {
                                if(ACC.calculator.isDesktopView) {
                                    return context.tick.label == ACC.calculator.selectedAWG ? 700 : 400;
                                } else {
                                    return context.tick.label == (ACC.calculator.selectedAWG).split(" ")[0] ? 'bold' : 400;
                                }  
                            }
                        }
                    }
                }
            },
            plugins: {
                tooltip: { enabled: false },
                legend: { display: false },
                datalabels: {
                    color: '#FFF',
                    anchor: 'end',
                    align: 'start',
                    formatter: (value) => (value.z < 0 ? '<0' : value.z) + 'V',
                    font: {
                        family: 'Arial',
                        size: 14,
                        weight: 400
                    }
                    /* ,offset: function(context) {
                        const value = context.dataset.data[context.dataIndex];
                        return !ACC.calculator.isDesktopView && value.z < 8.5 ? -1 : 2;
                    } */
                }
            },
            animation: {duration: 0},
            responsive: true,
            maintainAspectRatio: false/* ,
            elements: {
                bar: {
                    borderRadius: 5,
                    borderSkipped: false
                }
            } */
        };
        Chart.register(ChartDataLabels);
        const ctx = document.getElementById('wireGuageComparisonChart').getContext('2d');
        ACC.calculator.barChart = new Chart(ctx, {
            type: 'bar',
            data: chartData,
            options: options,
            plugins: [{
                beforeDraw: function (chart) {
                    const ctx = chart.ctx;
                    const xAxis = chart.scales.x;
                    const yAxis = chart.scales.y;
                    ctx.save();
                    ctx.fillStyle = '#999999';
                    xAxis.ticks.forEach((value, index) => {
                        if (value.value >= 8.5) {
                            var x = xAxis.getPixelForTick(index);
                            var y = xAxis.bottom - 35;
                            ctx.beginPath();
                            ctx.moveTo(x - 3, y);
                            ctx.lineTo(x + 3, y);
                            ctx.lineTo(x, y - 7);
                            ctx.closePath();
                            ctx.fill();
                        }
                    });
                    ctx.restore();

                    chart.data.datasets[0].data.forEach((value, index) => {
                        const y = yAxis.getPixelForValue(index);
                        const barWidth = xAxis.width;
                        const barHeight = chart.getDatasetMeta(0).data[index].height;
                        const barX = xAxis.left;
                        const barY = y - barHeight / 2;
                        ctx.fillStyle = '#F1F2F2';
                        ctx.fillRect(barX, barY, barWidth, barHeight);
                    });
                }
            }]
        });
    },
    updateBarChart: function (newLabels, newData, newColors) {
        ACC.calculator.barChart.data.labels = newLabels;
        ACC.calculator.barChart.data.datasets[0].data = newData.map((value, index) => {
            return {
                x: (value < 0) ? (ACC.calculator.isDesktopView ? 8.4 : 8.3)
                    : ((value > 11.5) ? 11.75 : ((value >= 0 && value <= 8.5) ? 8.5 : value)),
                y: index,
                z: value
            }
        });
        ACC.calculator.barChart.data.datasets[0].backgroundColor = newColors;
        ACC.calculator.barChart.data.datasets[0].borderColor = newColors;
        ACC.calculator.barChart.data.datasets[0].hoverBackgroundColor = newColors;
        ACC.calculator.barChart.update();
    },
    renderBarGraph: function(selectedAWG) {
        $('.chart-container').removeClass('hidden');
        var voltageTap = $("#voltageTap").val() || "";
        var lengthValue = $("#lengthValue").val().replace(/\s+[^\s]+$/, '') || "";
        var totalVA = $("#totalVA").val().replace(/\s+[^\s]+$/, '') || "";
        const awgResistanceObj = {
            "6 AWG": 0.3951,
            "8 AWG": 0.6282,
            "10 AWG": 0.9989,
            "12 AWG": 1.588,
            "14 AWG": 2.525,
            "16 AWG": 4.016,
            "18 AWG": 6.385,
            "20 AWG": 10.15
        }
        const awgRange = ACC.calculator.getAWGRange(selectedAWG);
        const awgData = {};
        for(const awg of awgRange) {
            const resistance = awgResistanceObj[awg];
            awgData[awg] = ACC.calculator.calculateVoltageAtLastFixture(resistance, lengthValue, totalVA, voltageTap).toFixed(2);
        }
        const awgLabels = ACC.calculator.isDesktopView ? Object.keys(awgData) : Object.keys(awgData).map(item => item.split(" ")[0]);
        const voltageData = Object.values(awgData);
        const barColors = voltageData.map(voltage => {
            if(voltage >= 10.5) {
                return '#77A12E';
            } else if(voltage >=10 && voltage < 10.5) {
                return '#EF8700';
            } else if(voltage >= 8.5 && voltage < 10){
                return '#E40101';
            } else if(voltage < 8.5 && voltage >= 0) {
                return '#5A5B5D';
            } else {
                return '#999999';
            }
        });
        ACC.calculator.selectedAWG = selectedAWG;
        ACC.calculator.updateBarChart(awgLabels, voltageData, barColors);
    },
    formatNumberInput: function(input) {
        const originalValue = input.value;
        const selectionStart = input.selectionStart;
        let value = originalValue.replace(/,/g, '');
        const hasDecimal = value.includes('.');
        let [integerPart, decimalPart] = value.split('.');
        if(integerPart.length > 9) {
            integerPart = integerPart.substring(0, 9);
        }
        if(integerPart.length + (decimalPart ? decimalPart.length : 0) > 9) {
            if(decimalPart) {
                decimalPart = decimalPart.substring(0, 9 - integerPart.length);
            }
        }
        if(parseFloat(integerPart + '.' + (decimalPart || '0')) < 9999999.99) {
            if(decimalPart && decimalPart.length > 2) {
                decimalPart = decimalPart.substring(0, 2);
            }
        }
        let formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        let formattedValue = hasDecimal ? formattedInteger + '.' + (decimalPart || '') : formattedInteger;
        if(formattedValue.length > 11){
        formattedValue = formattedValue.toString().replace(/\.$/, '');
        }
        input.value = formattedValue;
        const commasBeforeCursor = (originalValue.slice(0, selectionStart).match(/,/g) || []).length;
        const commasAfterFormat = (formattedValue.slice(0, selectionStart).match(/,/g) || []).length;
        const cursorPosition = selectionStart + (commasAfterFormat - commasBeforeCursor);
        input.setSelectionRange(cursorPosition, cursorPosition);
        const valueWithoutCommas = integerPart + (decimalPart ? '.' + decimalPart : '');
        $(input).attr('value', valueWithoutCommas);
    },
    updateWrapSection: function(selectedWrap) {
        ACC.calculator.updateFields(selectedWrap);
        ACC.calculator.checkAndCalculateLength();
		ACC.calculator.updateImageForTypeOfWrap(selectedWrap);
        $("#inputFields").find('input').val('');
        $("#inputFields").find('input').attr('value', '');
        $(".totalLength").text('0.00 ft');
    },
    updateFields: function(type) {
        $(".wrap-fields").addClass('hidden');
        $("." + type).removeClass('hidden');
    },
    dropdownToggleConvertion: function(unit) {
        let height = ACC.calculator.convertToFeet(parseFloat($('.inputField .height').attr('value')) || 0, $(".inputField .height").next('#projectCalcBtnGroup').find('.active').data('unit'));
        let width = ACC.calculator.convertToFeet(parseFloat($('.inputField .width').attr('value')) || 0, $(".inputField .width").next('#projectCalcBtnGroup').find('.active').data('unit'));
        let depth = ACC.calculator.convertToFeet(parseFloat($('.inputField .depth').attr('value')) || 0, $(".inputField .depth").next('#projectCalcBtnGroup').find('.active').data('unit'));
         let mobileFlag = $('.mobilebarkMulchCalc').hasClass('mob');
        let squareFeet = height * width;
		let cubicFeet = squareFeet * depth;
	    let pageName = $(".pagename").val();
        let Pounds;
        if (pageName == "Bark Mulch Calculator Page") {
            Pounds = (height / 3) * (width / 3)* (depth / 3) * 410;
        }
        if (pageName == "Top Soil Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2100;
            
        }
        if (pageName == "Road Basefill Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2650;
        }
        if (pageName == "Decorative Rock Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2800;
        }

        if(unit == ''){
            dropdown1 = $('.areaDropdownButton').val();
            dropdown2 = $('.volumeDropdownButton').val(); 
            dropdown3 = $('.weightDropdownButton').val();
            val1 = ACC.calculator.conversinofcal(height,width,depth,dropdown1);
            val2 = ACC.calculator.conversinofcal(height,width,depth,dropdown2);
            val3 = ACC.calculator.conversinofcal(height,width,depth,dropdown3);
            $('#areaDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(val1));
            $('#volumeDropdownComponent #value') .css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(val2));
            $('#weightDropdownComponent #value') .css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(val3));

        }
        else{
            switch(unit) {
                case 'squarefeet':
                    const squarefeet =height * width;
                    $('#areaDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(squarefeet));
                    break;
                case 'squareinches':
                    const squareinches =( height * width) * 144;
                    $('#areaDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(squareinches));
                    break;
                case 'squareyards':
                    const squareyards =( height * width) / 9;
                    $('#areaDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(squareyards));
                    break;
                case 'cubicfeet':
                    const cubicfeet = height * width * depth;
                    $('#volumeDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(cubicfeet));
                    break;
                case 'cubicyards':
                    const cubicyards =(height * width * depth) / 27;
                    $('#volumeDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(cubicyards));
                    break;
                case 'pounds':
                    $('#weightDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(Pounds));
                    break;
                case 'tons':
                    const tonVal = Pounds/2000;
                    $('#weightDropdownComponent #value').css('font-size',mobileFlag ? '25pt': '40px').html(ACC.calculator.formatTotalLengthCal(tonVal));
                    break;
            }
        }
    },
    conversinofcal: function(height,width,depth,unit){
        let pageName = $(".pagename").val();
        let Pounds;
        if (pageName == "Bark Mulch Calculator Page") {
            Pounds = (height / 3) * (width / 3)* (depth / 3) * 410;
        }
        if (pageName == "Top Soil Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2100;
        }
        if (pageName == "Road Basefill Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2650;
        }
        if (pageName == "Decorative Rock Calculator Page") {
            Pounds = (height / 3) * (width / 3) * (depth / 3) * 2800;
        }
        switch(unit) {
            case 'squarefeet':
                return height * width;
            case 'squareinches':
                return (height * width) * 144;
            case 'squareyards':
                return ( height * width) / 9;
            case 'cubicfeet':
                return height * width * depth;
            case 'cubicyards':
                return (height * width * depth) / 27;
            case 'pounds':
                return Pounds;
            case 'tons':
                return Pounds/2000;
        }
    },
     formatTotalLengthCal: function(num) {
        const hasDecimal = num % 1 !== 0;
        let lengthStr;
        let totalDigits;
        let formattedLength;
        if(hasDecimal){
            lengthStr =Math.abs(num).toFixed(2);
            totalDigits = lengthStr.replace('.', '').length;
            formattedLength = parseFloat(lengthStr).toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
        }
        else{
            totalDigits =  Math.abs(num).toString().length;
            formattedLength = parseFloat(num).toLocaleString();
            
        }
            if(totalDigits <= 7) {
            return formattedLength;
            } else if(totalDigits > 7 & totalDigits <= 9) {
                return formattedLength;
            } else if(totalDigits > 9) {
                const scientificNotation = num.toExponential(2).replace('e+', ' x 10<sup>') + '</sup>';
            return scientificNotation;
            }
    },
    checkAndCalculateLength: function() {
        let allFieldsFilled = true;
        const $activeFields = $(".wrap-fields:not(.hidden) input");
        $activeFields.each(function() {
            if($(this).val() === "" || parseFloat($(this).val().trim()) == 0) {
                allFieldsFilled = false;
            }
        });
        if(allFieldsFilled) {
            ACC.calculator.calculateLength();
        } else {
            let mobileFlag = $('.totalLength').hasClass('mob');
            $('.totalLength').css('font-size', mobileFlag ? '40px' : '32pt').text('0.00 ft');
        }
    },
    calculateLength: function() {
        let totalLength = 0;
        const wrapType = $("#typeOfWrap").val();
        if(wrapType === 'tree') {
            const height = ACC.calculator.convertToFeet(parseFloat($('.tree .height').attr('value')) || 0, $('.tree .height').closest('.hl-input-section').find('.active').data('unit'));
            const width = ACC.calculator.convertToFeet(parseFloat($('.tree .width').attr('value')) || 0, $('.tree .width').closest('.hl-input-section').find('.active').data('unit'));
            totalLength = height * width * 4.23;
        } else if(wrapType === 'canopy') {
            const height = ACC.calculator.convertToFeet(parseFloat($('.canopy .height').attr('value')) || 0, $('.canopy .height').closest('.hl-input-section').find('.active').data('unit'));
            const width = ACC.calculator.convertToFeet(parseFloat($('.canopy .width').attr('value')) || 0, $('.canopy .width').closest('.hl-input-section').find('.active').data('unit'));
            totalLength = height * width * 3.14;
        } else if(wrapType === 'trunk') {
            const height = ACC.calculator.convertToFeet(parseFloat($('.trunk .height').attr('value')) || 0, $('.trunk .height').closest('.hl-input-section').find('.active').data('unit'));
            const circumference = ACC.calculator.convertToFeet(parseFloat($('.trunk .circumference').attr('value')) || 0, $('.trunk .circumference').closest('.hl-input-section').find('.active').data('unit'));
            const spacing = ACC.calculator.convertToFeet(parseFloat($('.trunk .spacing').attr('value')) || 0, $('.trunk .spacing').closest('.hl-input-section').find('.active').data('unit'));
            totalLength = (height * circumference) / spacing;
        } else if(wrapType === 'branch') {
            const branches = parseFloat($('.branch .branches').attr('value')) || 0;
            const branchLength = ACC.calculator.convertToFeet(parseFloat($('.branch .length').attr('value')) || 0, $('.branch .length').closest('.hl-input-section').find('.active').data('unit'));
            const branchDiameter = ACC.calculator.convertToFeet(parseFloat($('.branch .diameter').attr('value')) || 0, $('.branch .diameter').closest('.hl-input-section').find('.active').data('unit'));
            const spacing = ACC.calculator.convertToFeet(parseFloat($('.branch .spacing').attr('value')) || 0, $('.branch .spacing').closest('.hl-input-section').find('.active').data('unit'));
            totalLength = (branches * branchLength * branchDiameter * 3.14) / spacing;
        }
        ACC.calculator.formatTotalLength(totalLength);
    },
    updateImageForTypeOfWrap: function(type) {
        const imageFileObj = {
            'tree': 'tree-christmas.png',
            'canopy': 'tree-canopy.png',
            'trunk': 'tree-trunk.png',
            'branch': 'tree-branch.png'
        };
        $('.typeOfWrapImage').attr('src', '/_ui/responsive/theme-lambda/images/' + imageFileObj[type]);
        $('.typeOfWrapImage').removeClass().addClass('typeOfWrapImage ' + type);
    },
    formatTotalLength: function(length) {
        const lengthStr = length.toFixed(2);
        const totalDigits = lengthStr.replace('.', '').length;
        let formattedLength = parseFloat(lengthStr).toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
        let mobileFlag = $('.totalLength').hasClass('mob');
        if(totalDigits <= 7) {
           $('.totalLength') .css('font-size', mobileFlag ? '40px': '32pt').text(formattedLength + ' ft');
        } else if(totalDigits > 7 & totalDigits <= 9) {
            $('.totalLength') .css('font-size', mobileFlag ? '35px' : '26pt').text(formattedLength + ' ft');
        } else if(totalDigits > 9) {
            const scientificNotation = length.toExponential(2).replace('e+', ' x 10<sup>') + '</sup> ft';
            $('.totalLength') .css('font-size', mobileFlag ? '35px' : '32pt').html(scientificNotation);
        }
    },
    adjustFontSizeAndText: function (text, className) {
        var element = $('.' + className).eq(0);
        var numDigits = text.replace(/[.-]/g, "").length;
        var fontSize;
        if(numDigits > 9) {
            fontSize = '30px';
        } else if (numDigits >= 8) {
            fontSize = '40px';
        } else if (numDigits >= 6) {
            fontSize = '50px';
        } else {
            fontSize = '64px';
        }
        element.css('font-size', fontSize);
        element.text(text);
    },
    currentMode: "dimensions",
    currentLayout: "square",
    savedValues: {
        length: '',
        width: '',
        sqft: '',
        spacing: ''
    },
    updateView: function () {
        if ($(".page-plantSpacingPage").length && $(".page-plantSpacingPage").is(":visible")) {
            const isRow = ACC.calculator.currentLayout === "row";
            if (isRow) {
                $(".modeButtons").addClass("hidden");
            } else {
                $(".modeButtons").removeClass("hidden");
            }
            $("#field-length, #field-width, #field-sqft, #field-spacing").addClass("hidden");
            if (isRow) {
                $("#field-length, #field-spacing").removeClass("hidden");
                $(".length").val('');
                $(".width").val('');
                $(".spacing").val('');
                $(".sqft").val('');
                $('.plantCount').text('');
                $('.plants').text('');
                $(".start-position").text('');
            } else {
                if (ACC.calculator.currentMode === "dimensions") {
                    $("#field-length, #field-width, #field-spacing").removeClass("hidden");
                    $(".length").val(ACC.calculator.savedValues.length);
                    $(".width").val(ACC.calculator.savedValues.width);
                    $(".spacing").val(ACC.calculator.savedValues.spacing);
                } else {
                    $("#field-sqft, #field-spacing").removeClass("hidden");
                    $(".sqft").val(ACC.calculator.savedValues.sqft);
                    $(".spacing").val(ACC.calculator.savedValues.spacing);
                }
            }
            const layoutLabel = {
                "square": "Square<br>Spacing",
                "triangular": "Triangular<br>Spacing",
                "row": "Row / Hedge"
            };
            if ($(".layout-heading").hasClass("mob") && ACC.calculator.currentLayout === 'row') {
                $(".layout-heading").html("Row");
            } else {
                $(".layout-heading").html(layoutLabel[ACC.calculator.currentLayout]);
            }
            $('.typeOfLayout').attr('src', '/_ui/responsive/theme-lambda/images/layout_' + ACC.calculator.currentLayout + '.png');
            ACC.calculator.checkAndCalculatePlants();
        }
    },
    convertToInches: function(value, unit) {
        switch(unit) {
            case 'inches':
                return value;
            case 'feet':
                return value * 12;
            case 'area':
                return value * 144;
            default:
                return 0;
        }
    },
    checkAndCalculatePlants: function () {
        let allFieldsFilled = true;
        const $activeFields = $(".field:not(.hidden) input");
        $activeFields.each(function () {
            if ($(this).val() === "" || parseFloat($(this).val().trim()) == 0) {
                allFieldsFilled = false;
            }
        });
        if (allFieldsFilled) {
            ACC.calculator.calculatePlants();
        } else {
            $('.plantCount').text('');
            $('.plants').text('');
            $(".start-position").text('');
        }
    },
    calculatePlants: function () {
        let nTotal = 0;
        let rowStartPoint = 0;
        const spacing = ACC.calculator.convertToInches(parseFloat($(".spacing").attr('value')) || 0, $('.spacing').closest('.hl-input-section').find('.active').data('unit'));
        if (ACC.calculator.currentLayout === "row") {
            const length = ACC.calculator.convertToInches(parseFloat($(".length").attr('value')) || 0, $('.length').closest('.hl-input-section').find('.active').data('unit'));
            nTotal = Math.floor(length / spacing) + 1;
            const extraLengthSpace = length - (nTotal - 1) * spacing;
            const offset = extraLengthSpace / 2;
            rowStartPoint = parseFloat(offset) + Number(spacing / 2);
        } else if (ACC.calculator.currentMode === "dimensions") {
            const length = ACC.calculator.convertToInches(parseFloat($(".length").attr('value')) || 0, $('.length').closest('.hl-input-section').find('.active').data('unit'));
            const width = ACC.calculator.convertToInches(parseFloat($(".width").attr('value')) || 0, $('.width').closest('.hl-input-section').find('.active').data('unit'));
            if (ACC.calculator.currentLayout === "square") {
                const nL = Math.floor(length / spacing) + 1;
                const nW = Math.floor(width / spacing) + 1;
                nTotal = nL * nW;
                const extraLengthSpace = length - (nL - 1) * spacing;
                const extraWidthSpace = width - (nW - 1) * spacing;
                const offset = Math.min(extraLengthSpace, extraWidthSpace) / 2;
                rowStartPoint = parseFloat(offset) + Number(spacing / 2);
            } else if (ACC.calculator.currentLayout === "triangular") {
                const sV = (spacing * Math.sqrt(3)/2);
                const nL = Math.floor(length / spacing) + 1;
                const nW = Math.floor(width / sV) + 1;
                nTotal = Math.ceil(nL * (nW / 2) + (nL - 1) * (nW / 2));
                const extraLengthSpace = (length - (nL - 1) * spacing);
                const extraWidthSpace = (width - (nW - 1) * sV);
                const offset =  Math.min(extraLengthSpace, extraWidthSpace) / 2;
                rowStartPoint = parseFloat(offset) + Number(spacing / 2);
            }
        } else {
            const sqft = ACC.calculator.convertToInches(parseFloat($(".sqft").attr('value')) || 0, "area");
            if (ACC.calculator.currentLayout === "square") {
                const sL = Math.sqrt(sqft);
                const nS = Math.floor(sL/ spacing) + 1;
                nTotal = nS * nS;
                const extraSpace = sL - (nS - 1) * spacing;
                const offset = extraSpace / 2;
                rowStartPoint = parseFloat(offset) + Number(spacing / 2);
            } else if (ACC.calculator.currentLayout === "triangular") {
                const sL = Math.sqrt(sqft);
                const nRow = Math.floor(sL / spacing) + 1;
                const nRows = Math.floor(sL / (spacing * Math.sqrt(3)/2)) + 1;
                nTotal = nRow * nRows;
                const extraSpace = sL - (nRow - 1) * spacing;
                const offset = extraSpace / 2;
                rowStartPoint = parseFloat(offset) + Number(spacing / 2);
            }
        }
        ACC.calculator.formatPlantCount(nTotal);
        $(".plants").text(nTotal > 1 ? "plants" : "plant");
        if($(".start-position").hasClass("mob")) {
            $(".start-position").text(`Start ${rowStartPoint.toFixed(2)}" from the edge.`);
        } else {
            $(".start-position").text(`Start the first row ${rowStartPoint.toFixed(2)}" from the edge.`);
        }
    },
    formatPlantCount: function(count) {
        const plantCountStr = count.toFixed(2);
        const totalDigits = plantCountStr.replace('.', '').length;
        let formattedPlantCount = parseFloat(plantCountStr).toLocaleString();
        let mobileFlag = $('.plantCount').hasClass('mob');
        if(totalDigits <= 7) {
           $('.plantCount').css('font-size', mobileFlag ? '40px': '40pt').text(formattedPlantCount);
        } else if(totalDigits > 7 & totalDigits <= 9) {
            $('.plantCount').css('font-size', mobileFlag ? '35px' : '32pt').text(formattedPlantCount);
        } else if(totalDigits > 9) {
            const scientificNotation = count.toExponential(2).replace('e+', ' x 10<sup>') + '</sup>';
            $('.plantCount').css('font-size', mobileFlag ? '35px' : '32pt').html(scientificNotation);
        }
    },
    handleModeButtonClick: function (button) {
        if (!$(button).hasClass("active")) {
            $(".modeButtons button").removeClass("active");
            $(button).addClass("active");
            ACC.calculator.currentMode = $(button).data("mode");
            if (ACC.calculator.currentLayout !== "row") {
                ACC.calculator.currentLayout = "square";
                $(".layoutButtons button").removeClass("active");
                $('.layoutButtons button[data-layout="square"]').addClass("active");
            }
            ACC.calculator.savedValues.length = '';
            ACC.calculator.savedValues.width = '';
            ACC.calculator.savedValues.sqft = '';
            ACC.calculator.savedValues.spacing = '';
            $(".length").val(ACC.calculator.savedValues.length);
            $(".width").val(ACC.calculator.savedValues.width);
            $(".sqft").val(ACC.calculator.savedValues.sqft);
            $(".spacing").val(ACC.calculator.savedValues.spacing);
            ACC.calculator.updateView();
        }
    },
    handleLayoutButtonClick: function (button) {
        if (!$(button).hasClass("active")) {
            const selectedLayout = $(button).data("layout");
            if (ACC.calculator.currentLayout === "row") {
                ACC.calculator.savedValues.length = '';
                ACC.calculator.savedValues.width = '';
                ACC.calculator.savedValues.sqft = '';
                ACC.calculator.savedValues.spacing = '';
                $(".length").val(ACC.calculator.savedValues.length);
                $(".width").val(ACC.calculator.savedValues.width);
                $(".sqft").val(ACC.calculator.savedValues.sqft);
                $(".spacing").val(ACC.calculator.savedValues.spacing);
            } else {
                if (selectedLayout !== "row") {
                    ACC.calculator.savedValues.length = $(".length").val();
                    ACC.calculator.savedValues.width = $(".width").val();
                    ACC.calculator.savedValues.sqft = $(".sqft").val();
                    ACC.calculator.savedValues.spacing = $(".spacing").val();
                }
            }
            $(".layoutButtons button").removeClass("active");
            $(button).addClass("active");
            ACC.calculator.currentLayout = selectedLayout;
            ACC.calculator.updateView();
        }
    },
    flagStoneMandatoryCheck: function() {
        if( $('.materialsCalculators').is(":Visible") == true)
        { 
        if($('.length').val() != '' && $('.width').val() != '' && $('#select-option').val() != ''){
            $(".flagStoneCalculate").removeClass("disabled");
        }
        else{
            $(".flagStoneCalculate").addClass("disabled");
        }
    }
    else{
        if($('.length').val() != '' && $('.width').val() != '' && $('.depthUnitDropdown').val() != null){
            $(".flagStoneCalculate").removeClass("disabled");
        }
        else{
            $(".flagStoneCalculate").addClass("disabled");
        }
    }
    },
    topSoilMandatoryCheck: function() {
        if($('.length').val() != '' && $('.width').val() != '' && $('.depth').val() != ''){
            $(".topSoilCalculate").removeClass("disabled");
        }
        else{
            $(".topSoilCalculate").addClass("disabled");
        }
    },
    roadBaseFillMandatoryCheck: function() {
        if($('.length').val() != '' && $('.width').val() != '' && $('.depth').val() != ''){
            $(".roadBasefillCalculate").removeClass("disabled");
        }
        else{
            $(".roadBasefillCalculate").addClass("disabled");
        }
    },
    decorativeRockMandatoryCheck: function() {
        if($('.length').val() != '' && $('.width').val() != '' && $('.depth').val() != ''){
            $(".decorativeRockCalculate").removeClass("disabled");
        }
        else{
            $(".decorativeRockCalculate").addClass("disabled");
        }
    },
    stoneWallsMandatoryCheck: function () {
        if ($('#height').val() != '' && $('#width').val() != '') {
            $(".stoneWallCalculate").removeClass("disabled");
        } else {
            $(".stoneWallCalculate").addClass("disabled");
        }
    },
    barkMulchMandatoryCheck: function() {
        if ($('.length').val() != '' && $('.width').val() != '' && $('.depth').val() != '') {
            $(".barkMulchBedCalculate").removeClass("disabled");
        } else {
            $(".barkMulchBedCalculate").addClass("disabled");
        }
    }
};

$(".mob-matcal-options").change(function() {
    var option = $(this).find('option:selected');
    window.location.href = option.data("url");
});
function dropdownToggle() {
    
    // select the main dropdown button element
    var dropdown = $(this).parent().parent().prev();
    // change the CONTENT of the button based on the content of selected option
    dropdown.html($(this).html() + '&nbsp;</i><span class="glyphicon glyphicon-chevron-down"></span>');
    
}
$(document).ready(function(){
    $('.dropdown-menu-cal a, .dropdown-menu-voltagedrop a, .dropdown-menu-hl a,.dropdown-menu-fs a').on('click', dropdownToggle);
   
      $("#tableMenu a").on('click', function(e) {
        e.preventDefault();
        $('#tableMenu li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var valueflag = $(this).data('value');
        $('#select-option').val(valueflag);
        ACC.calculator.flagStoneMandatoryCheck();
        ACC.calculator.topSoilMandatoryCheck();
        ACC.calculator.barkMulchMandatoryCheck();
      });
    
      $("#tableMenuoptionlength a").on('click', function(e) {
        e.preventDefault();
        $('#tableMenuoptionlength li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var valuelengthflag = $(this).data('value');
        $('.lengthUnitDropdown').val(valuelengthflag);
      });
      $("#tableMenuoptionwidth a").on('click', function(e) {
        e.preventDefault();
        $('#tableMenuoptionwidth li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var valuewidthflag = $(this).data('value');
        $('.widthUnitDropdown').val(valuewidthflag);
      });
      $("#tableMenuoptiondepth a").on('click', function(e) {
        e.preventDefault();
        $('#tableMenuoptiondepth li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var valuedepthflag = $(this).data('value');
        $('.depthUnitDropdown').val(valuedepthflag);
      });
      $("#tableMenuoptionheight a").on('click', function(e) {
        e.preventDefault();
        $('#tableMenuoptionheight li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var valueheightflag = $(this).data('value');
        $('.heightUnitDropdown').val(valueheightflag);
      });
        $(".toggle-unit").on("click", function () {
  const $btn = $(this);
  const $group = $btn.closest("#projectCalcBtnGroup");

  $group.find(".toggle-unit").removeClass("active");
  $btn.addClass("active");

  if ($("#flagStoneCalc").is(":visible")) {
    ACC.calculator.CalculateFlagstone();
  }
    });
      $("#guageListDropdown a").on('click', function(e) {
        e.preventDefault();
        $('#guageListDropdown li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var selectedValue = $(this).data('value');
        $('#guage').val(selectedValue);
        ACC.calculator.voltageDropCalculator();
      });
      $("#voltageTapListDropdown a").on('click', function(e) {
        e.preventDefault();
        $('#voltageTapListDropdown li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var selectedValue = $(this).data('value');
        $('#voltageTap').val(selectedValue);
        ACC.calculator.voltageDropCalculator();
      });
    if ($(".page-voltageDropCalculatorPage").length && $(".page-voltageDropCalculatorPage").is(":visible")) {
        ACC.calculator.voltageDropCalculator();
        ACC.calculator.initializeBarChart();
    }
    $("#typeOfWrapDropdown a").on('click', function(e) {
        e.preventDefault();
        $('#typeOfWrapDropdown li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        var selectedValue = $(this).data('type');
        $("#typeOfWrap").val(selectedValue);
        ACC.calculator.updateWrapSection(selectedValue);
      });
      $('#inputFields').on('click', '.toggle-unit', function() {
        const $button = $(this);
        const $inputGroup = $button.closest('.hl-input-section');
        $inputGroup.find('.toggle-unit').removeClass('active');
        $button.addClass('active');
        if(!$button.hasClass('mob')) {
            const unit = $button.data('unit');
            const $input =$inputGroup.find('input');
            const fieldName = $input.attr('class').split(' ')[2];
            $input.attr('placeholder', `Enter ${fieldName} in ${unit}`);
        }
        if($(".page-holidayLightingCalculatorPage").length && $(".page-holidayLightingCalculatorPage").is(":visible")) {
            ACC.calculator.checkAndCalculateLength();
        } else if($(".page-plantSpacingPage ").length && $(".page-plantSpacingPage").is(":visible")) {
            const unit = $button.data('unit');
            const $input =$inputGroup.find('input');
            const fieldName = $input.attr('class').split(' ')[2];
            ACC.calculator.handleSuffix($input, false);
            $input.data('suffix', $button.data('unit'));
            ACC.calculator.handleSuffix($input, true);
            if($button.hasClass('mob')) {
              $input.attr('placeholder', `${fieldName.charAt(0).toUpperCase() + fieldName.slice(1)} in ${unit}`);  
            }
            ACC.calculator.checkAndCalculatePlants();
        }
      });
      $('#thicknessDropdown a').on('click', function (e) {
    e.preventDefault()
     $('#thicknessDropdown li').removeClass('selected')
    $(this).parent('li').addClass('selected')
    var selectedValue = $(this).data('value')
    $('#thickness').val(selectedValue)
    ACC.calculator.CalculateFlagstone('');
  })
  $('#MenuoptionVolume a').on('click', function (e) {
    e.preventDefault()
    $('#MenuoptionVolume li').removeClass('selected')
    $(this).parent('li').addClass('selected')
    $('#MenuoptionVolume li span').addClass('hidden')
    $(this).parent('li').find('span').removeClass('hidden')
    $('#MenuoptionVolume li a').addClass('m-l-15')
    $(this).parent('li').find('a').removeClass('m-l-15')
    var selectedValue = $(this).data('value')
    $('.volumeDropdownBtn').val(selectedValue)
     $('.inputField .length').attr('data-dropdownmetric', selectedValue)
    ACC.calculator.CalculateFlagstone('');
  })
  $('#Menuoptionarea a').on('click', function (e) {
    e.preventDefault()
    $('#Menuoptionarea li').removeClass('selected')
    $(this).parent('li').addClass('selected')
    $('#Menuoptionarea li span').addClass('hidden')
    $(this).parent('li').find('span').removeClass('hidden')
    $('#Menuoptionarea li a').addClass('m-l-15')
    $(this).parent('li').find('a').removeClass('m-l-15')
    var selectedValue = $(this).data('value')
    $('.areaDropdownBtn').val(selectedValue)
    $('.inputField .length').attr('data-dropdownmetric', selectedValue)
    ACC.calculator.CalculateFlagstone('');
  })
  $('#Menuoptionweight a').on('click', function (e) {
    e.preventDefault()
    $('#Menuoptionweight li').removeClass('selected')
    $(this).parent('li').addClass('selected')
    $('#Menuoptionweight li span').addClass('hidden')
    $(this).parent('li').find('span').removeClass('hidden')
    $('#Menuoptionweight li a').addClass('m-l-15')
    $(this).parent('li').find('a').removeClass('m-l-15')
    var selectedValue = $(this).data('value')
    $('.weightDropdownBtn').val(selectedValue)
     $('.inputField .width').attr('data-dropdownmetric', selectedValue)
    ACC.calculator.CalculateFlagstone('');
  })

      //BarkMuch
     $("#tableMenuoptionVolume a").on('click', function (e) {
        e.preventDefault();
        $('#tableMenuoptionVolume li').removeClass('selected');
        $(this).parent('li').addClass('selected');
        $("#tableMenuoptionVolume li span").addClass("hidden");
        $(this).parent("li").find("span").removeClass("hidden");
        $("#tableMenuoptionVolume li a").addClass("m-l-15");
        $(this).parent("li").find("a").removeClass("m-l-15");
        var valuedepthflag = $(this).data('value');
        $('.volumeDropdownButton').val(valuedepthflag);
        $('.inputField .width').attr("data-dropdownmetric", valuedepthflag);
    });
    $("#tableMenuoptionarea a").on('click', function (e) {
        e.preventDefault();
        $('#tableMenuoptionarea li').removeClass('selected');
        $(this).parent('li').addClass('selected');
         $("#tableMenuoptionarea li span").addClass("hidden");
        $(this).parent("li").find("span").removeClass("hidden");
        $("#tableMenuoptionarea li a").addClass("m-l-15");
        $(this).parent("li").find("a").removeClass("m-l-15");
        var valuedepthflag = $(this).data('value');
        $('.areaDropdownButton').val(valuedepthflag);
        $('.inputField .height').attr("data-dropdownmetric", valuedepthflag);
    });
      $("#tableMenuoptionweight a").on('click', function (e) {
        e.preventDefault();
        $('#tableMenuoptionweight li').removeClass('selected');
        $(this).parent('li').addClass('selected');
         $("#tableMenuoptionweight li span").addClass("hidden");
        $(this).parent("li").find("span").removeClass("hidden");
        $("#tableMenuoptionweight li a").addClass("m-l-15");
        $(this).parent("li").find("a").removeClass("m-l-15");
        var valuedepthflag = $(this).data('value');
        $('.weightDropdownButton').val(valuedepthflag);
        $('.inputField .depth').attr("data-dropdownmetric", valuedepthflag);
    });
});