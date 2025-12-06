ACC.langcurrency = {

	_autoload: [
		"bindLangCurrencySelector"
	],

	bindLangCurrencySelector: function (){
//		$('#lang-selector').on('change',function (){
//			$('#lang-form').submit();
//		});
		$(document).on('click','.language-value', function(e) {
			$('.language-dropdown').toggleClass('active');
		});
		$(document).on('click', '.lang-item', function (e) {
			let target = $(e.target).closest('.lang-item')
			let selectedLang = target.attr('data-value');
			let currentLanguage = '';
			if ($(".language-es").length) {
				currentLanguage = 'es';
			}else {
				currentLanguage = 'en';
			}
			if(currentLanguage != selectedLang) {
				$('input[name=code]').val(selectedLang);
				$('#lang-form').submit();
			}else{
				e.stopPropagation();
				$('.language-dropdown').removeClass('active');
			}
		})

		$('#currency-selector').on('change',function (){
			$('#currency-form').submit();
		});
	}
};