$(document).ready(function() {
	const CONVERSIONS = {
		INCHES_PER_FOOT: 12,
		SQUARE_INCHES_PER_SQUARE_FOOT: 144,
		POUNDS_PER_TON: 2000,
		GENERAL_WEIGHT: 410,
		MATERIAL_WEIGHTS: {
			flagstone: 132,
			deckRock: 2800,
			stoneWalls: 2700,
			roadbaseFill: 2650
		}
	};


	const pageType = {
		isFlagstone: location.href.includes("projectcalculators/flagstone"),
		isDeckRock: location.href.includes("projectcalculators/decorativerock"),
		isStonewalls: location.href.includes("projectcalculators/stonewalls"),
		isBarkmulch: location.href.includes("projectcalculators/barkmulch"),
		isTopsoil: location.href.includes("projectcalculators/topsoil"),
		isRoadbaseFill: location.href.includes("projectcalculators/roadbasefill")
	};

	const formatNumber = (num) => {
		// Convert to number and limit decimal places
		let formatted = parseFloat(num);
		// Check if it's a valid number
		if (isNaN(formatted)) return 0;
		// If number is too large, cap it
		if (formatted > 999999999) formatted = 999999999;
		// If number is too small, floor at 0
		if (formatted < 0) formatted = 0;
		// Format to max 2 decimal places
		return parseFloat(formatted.toFixed(2));
	};


	function setupDropdown(type) {
		const buttonClass = `${type}Dropdown`;
		$(`.${buttonClass}Button`).on('click', () => {
			$(`.${buttonClass}ButtonContent`).toggle();
		});
		$(`.${buttonClass}Option`).on('click', () => {
			$(`.${buttonClass}ButtonContent`).toggle();
		});
	}

	['area', 'volume', 'weight'].forEach(setupDropdown);

	function updateDropdownButton(type, text, value) {
		$(`.${type}DropdownButton`)
			.first()
			.contents()
			.first()
			.replaceWith(text);
		$(`.${type}DropdownButton`).data('value', value);
	}

	function getAreaAsSqFt(dontUpdateInput) {
		const visableInputs = $("#columnOne input:visible").slice(0, 2);
		let area = 1;

		visableInputs.each(function() {
			const value = parseFloat($(this).val());
			if (!isNaN(value)) {
				area *= value;
			}
		});

		if (!dontUpdateInput) {
			$("#areaDropdownComponent input").val(formatNumber(area));
		}

		return area;
	}

	function getVolumeWithDepthAsCubicFeet() {
		const visableInputs = $("#columnOne input:visible");
		let area = 1;

		visableInputs.each(function() {
			const value = parseFloat($(this).val());
			if (!isNaN(value)) {
				area *= value;
			}
		});

		return area;
	}

	function calculateThickness(newThickness, newLabel) {
		const area = getAreaAsSqFt();

		// Validate area input
		if (isNaN(area) || area <= 0) {
			console.error("Invalid area value");
			return;
		}

		// calulates volume on input
		const calculateNewVolume = () => {
			let multiplier = newThickness;
			if (pageType.isFlagstone) {
				multiplier = parseFloat(newThickness) / 12;
			} else if (pageType.isDeckRock || pageType.isRoadbaseFill) {
				const _m = parseFloat((parseFloat($("#depth").val()).toFixed()));
				multiplier = _m;
			}
			const volume = parseFloat((area * multiplier).toFixed(2));
			return volume;
		};
		const newVolume = calculateNewVolume();

		// calulates weight on input
		const calculateNewWeight = (__newVolume) => {
			let multiplier = 132;
			if (pageType.isStonewalls) {
				multiplier = 2700;
			}
			if (pageType.isDeckRock) {
				multiplier = 2800;
			}
			if (pageType.isRoadbaseFill) {
				multiplier = 2650;
			}
			if (pageType.isFlagstone) {
				multiplier = 3564;
			}
			if (pageType.isTopsoil) {
				multiplier = 2100;
			}
			if (pageType.isBarkmulch) {
				multiplier = 410;
			}

			const weight = parseFloat((__newVolume * multiplier).toFixed(2));
			const results = {
				pnds: Math.ceil(weight),
				tons: parseFloat((weight / 2000).toFixed(2))
			};
			const currentWeightMetric = $("#weightDropdownComponent button").data("value");
			return results[currentWeightMetric]
		};

		// Update UI components with calculated values
		$("#volumeDropdownComponent input").val(formatNumber(newVolume));
		$("#weightDropdownComponent input").val(formatNumber(calculateNewWeight(newVolume)));
		if (newLabel) {
			$("#thicknessDropdownComponent button").text(newLabel).data("value", newThickness);
			$("#thicknessDropdownComponent input").val(newThickness);
		}
	}

	$(".thicknessDropdownOption").on('click', function(e) {
		function resetNewDropdowns(calculateThickness) {
			$('.areaDropdownButton').data('value', 'sqft').text("SQUARE FEET");
			$('.volumeDropdownButton').data('value', 'cbft').text("CUBIC FEET");
			$('.weightDropdownButton').data('value', 'pnds').text("POUNDS");

			$("#areaDropdownComponent input").val("");
			$("#volumeDropdownComponent input").val("");
			$("#weightDropdownComponent input").val("");

			$(".volumeDropdownOption").first().siblings().removeClass("selected").end().addClass("selected");
			$(".weightDropdownOption").first().siblings().removeClass("selected").end().addClass("selected");

			calculateThickness()
		}

		e.preventDefault();
		const $ThicknessOption = $(this);
		const thickness = $ThicknessOption.data("value")
		const label = $ThicknessOption.text()
		resetNewDropdowns(() => {
			calculateThickness(thickness, label);
		});
		$ThicknessOption.siblings().removeClass("selected").end().addClass("selected");
	});

	// Square Feet Dropdwon
	$(".areaDropdownOption1").on("click", function() {
		const $areaOption = $(this);
		const $input = $("#areaDropdownComponent input");
		const selectedMetric = $areaOption.attr("data-value");
		const previousMetric = $(".areaDropdownButton").attr("data-value");
		const currentArea = parseFloat($("#height").val()) * parseFloat($("#width").val())

		let convertedValue
		if (previousMetric === "sqft") {
			convertedValue = selectedMetric === "sqin" ? currentArea * 144 : selectedMetric === "sqft" ? currentArea : currentArea / 9;
		} else if (previousMetric === "sqin" && selectedMetric === "sqyd") {
			convertedValue = currentArea / 1296;
		} else {
			convertedValue = currentArea * 1296;
		}

		const isAnyInputEmpty = $("#columnOne input").toArray().some(input => $(input).val().trim() === "");
		if (isAnyInputEmpty) {
			return
		}
		$("#areaDropdownComponent #value").html(formatNumber(convertedValue));
		$input.val(selectedMetric === "sqft" ? currentArea : parseFloat(convertedValue.toFixed(2)));
		$areaOption.addClass("selected").siblings().removeClass("selected");
		updateDropdownButton("area", $areaOption.text(), selectedMetric);
	});

	// Cubic Yards Dropdwon
	$(".volumeDropdownOption1").on("click", function() {
		const $volumeOption = $(this);
		const $input = $("#volumeDropdownComponent #value");
		const $button = $("#volumeDropdownComponent button");
		const inputValue = parseFloat($input.text());
		const selectedMetric = $volumeOption.attr("data-value");
		const $area = getAreaAsSqFt(true);
		const currentArea = parseFloat($("#height").val()) * parseFloat($("#width").val()) * parseFloat($("#depth").val());
		let convertedValue;
		if (selectedMetric === "cbyd") {
			convertedValue = parseFloat((currentArea/ 27).toFixed(2));
		} else {
			if (selectedMetric === "cbft") {
				convertedValue = currentArea;
			}
		}

		const isAnyInputEmpty = $("#columnOne input").toArray().some(input => $(input).val().trim() === "");
		if (isAnyInputEmpty) {
			return
		}

		// Update input value and selected option

		$volumeOption.addClass("selected").siblings().removeClass("selected");
		$("#volumeDropdownComponent #value").html(formatNumber(convertedValue));
		// Update dropdown button
		updateDropdownButton("volume", $volumeOption.text(), selectedMetric);
	});

	// Pounds Dropdwon
	$(".weightDropdownOption1").on("click", function() {
		function calculateWeight(volume, metric) {
			if (pageType.isFlagstone) {
				return metric === "tns" ?
					volume * CONVERSIONS.MATERIAL_WEIGHTS.flagstone / CONVERSIONS.POUNDS_PER_TON :
					volume * CONVERSIONS.MATERIAL_WEIGHTS.flagstone;
			}
			if (pageType.isStonewalls) {
				return metric === "tns" ?
					volume * CONVERSIONS.MATERIAL_WEIGHTS.stoneWalls / CONVERSIONS.POUNDS_PER_TON :
					volume * CONVERSIONS.MATERIAL_WEIGHTS.stoneWalls;
			}
			if (pageType.isDeckRock) {
				return metric === "tns" ?
					volume * CONVERSIONS.MATERIAL_WEIGHTS.deckRock / CONVERSIONS.POUNDS_PER_TON :
					volume * CONVERSIONS.MATERIAL_WEIGHTS.deckRock;
			}
			if (pageType.isRoadbaseFill) {
				return metric === "tns" ?
					volume * CONVERSIONS.MATERIAL_WEIGHTS.roadbaseFill / CONVERSIONS.POUNDS_PER_TON :
					volume * CONVERSIONS.MATERIAL_WEIGHTS.roadbaseFill;
			}
			return 0
		}


		function coversionCheck() {
			const volumeInput = $("#volumeDropdownComponent #value");
			const volume = parseFloat(volumeInput.text());

			const isInCubicYrds = $("#volumeDropdownComponent button").data().value === "cbyd";
			if (isInCubicYrds) {
				return parseInt(volume * 27);
			}

			return volume;
		}
		let pageName = $(".pagename").val();
		const $WeightOption = $(this);
		const $weightInput = $("#weightDropdownComponent #value");

		//const $button = $("#weightDropdownComponent button");
		// const inputValue = parseFloat($weightInput.text());
		const selectedMetric = $WeightOption.attr("data-value");
		const currentyards = parseFloat($("#height").val() / 3) * parseFloat($("#width").val() / 3) * parseFloat($("#depth").val()) / 3;
		let Pounds;

		if (pageName == "Bark Mulch Calculator Page") {
			Pounds = currentyards * 410;
		}
		if (pageName == "Top Soil Calculator Page") {
			Pounds = currentyards * 2100;
		}
		if (selectedMetric == "tns") {
			tons = Pounds / 2000;
			convertedValue = tons;
		}
		else {
			convertedValue = Pounds;
		}
		$("#weightDropdownComponent #value").html(formatNumber(convertedValue));

		$WeightOption.siblings().removeClass("selected").end().addClass("selected");
		updateDropdownButton('weight', $WeightOption.text(), selectedMetric);
	});

	// inputs
	$("#height, #width, #depth").on("keydown", function(e) {
		// Allow numpad keys (96-105), control keys, and delete
		if (
			(e.which >= 96 && e.which <= 105) || // Numpad numbers
			$.inArray(e.which, [8, 9, 13, 37, 38, 39, 40, 46]) !== -1 || // Control keys and delete
			e.which === 110 || // Numpad decimal point
			e.which === 190 // Decimal point (.)
		) {
			return; // Allow these keys
		}

		// Disallow the 'e' character
		const char = String.fromCharCode(e.which);
		if (char === 'e' || char === 'E') {
			e.preventDefault();
		}

		// Ensure only numeric input and decimal point (for non-numpad keys)
		if (!/^[0-9.]$/.test(char) || (char === '.' && $(this).val().includes('.'))) {
			e.preventDefault();
		}
	});

	function validateInputs($input) {
		let value = $input.val();

		// Remove any non-numeric characters except decimal point
		value = value.replace(/[^0-9.]/g, '');

		// Remove leading zeros
		value = value.replace(/^0+(?=\d)/, '');

		// Limit to 9 digits total
		if (value.length > 9) {
			value = value.slice(0, 9);
		}

		// Prevent negative numbers by removing minus signs
		value = value.replace(/-/g, '');

		// Update input value with sanitized value
		$input.val(value);
		return value;
	}
	//Barkmuch and Toip
	

	$("#projectCalcInput button").on("click", function() {
		const $Button = $(this)//specific button in/ft
	    const $inputGroup = $Button.closest('#projectCalcInput');
		
		const metric = $Button.data('metric');
		const input = $(`#${metric}`);
		const currentSuffix = $Button.data('suffix');
		const unit = $Button.data('unit');
 		const $input = $inputGroup.find('input');
		ACC.calculator.handleSuffix($input, false);
        $input.data('suffix', $Button.data('unit'));
        ACC.calculator.handleSuffix($input, true);
        const fieldName = $input.attr('class').split(' ')[1];
        $input.attr('placeholder', `Enter ${fieldName} in ${unit}`);
		input.attr("data-metric", currentSuffix);
		$Button.attr("data-previous");
		$Button.siblings().removeClass('active').end().addClass('active');
		
	});
	
	$("#projectCalcBtnGroup button").on("click", function() {
		ACC.calculator.dropdownToggleConvertion('');
	});
	function onChangeofftandin(previousSuffix, currentSuffix, metric, formValue) {
		let pageName = $(".pagename").val();
		if (metric == 'height') {
			heightInInches = formValue;
		}
		if (metric == 'width') {
			widthInInches = formValue;
		}
		if (metric == 'depth') {
			depthInInches = formValue;
		}

		const area = heightInInches * widthInInches;
		const Volume = area * depthInInches;
		let Pounds;
		if (pageName == "Bark Mulch Calculator Page") {
			Pounds = ((heightInInches / 3) * (widthInInches / 3) * (depthInInches / 3)) * 410;
		}
		if (pageName == "Top Soil Calculator Page") {
			Pounds = ((heightInInches / 3) * (widthInInches / 3) * (depthInInches / 3)) * 2100;
		}
		$("#areaDropdownComponent #value").html(formatNumber(area));
		$("#volumeDropdownComponent #value").html(formatNumber(Volume));
		$("#weightDropdownComponent #value").html(formatNumber(Pounds));
	}

	// Initialize default values
	$('.areaDropdownButton').data('value', 'sqft');
	$('.volumeDropdownButton').data('value', 'cbft');
	$('.weightDropdownButton').data('value', 'pnds');
});