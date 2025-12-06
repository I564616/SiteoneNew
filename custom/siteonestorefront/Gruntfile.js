var timer = require("grunt-timer");

// Usage:
//
// grunt watch
// this command will watch for .less and .js changes inside of ybase and recompile css/js
//
// grunt dev
// this command will run all commands except minification and sync:syncfonts
//
// grunt build
// this is used to test a full build with minification
//

module.exports = function(grunt) {
  timer.init(grunt);
  // Project configuration.

  // Plugins
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-cssmin');

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    
    watch: {
        less: {
            files: ['web/webroot/WEB-INF/_ui-src/shared/less/variableMapping.less','web/webroot/WEB-INF/_ui-src/shared/less/generatedVariables.less',
                    'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-*/less/*', 'web/webroot/WEB-INF/_ui-src/**/themes/**/less/*.less'],
            tasks: ['less'],
        },
        javascript: {
            files: ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/**/*.js'],
            tasks: ['concat', 'uglify', 'copy:devjs'],
        },
    },
    less: {
        main: {
            files:
            {
                'web/webroot/_ui/responsive/theme-lambda/css/style.css': 'web/webroot/WEB-INF/_ui-src/responsive/themes/lambda/less/style.less'
            },
            options:{
                sourceMap:false // true for debugging CSS issues.
            }
        },
        addons: {
            files:
            {
                'web/webroot/_ui/responsive/theme-lambda/css/addons.css': 'web/webroot/WEB-INF/_ui-src/responsive/themes/lambda/less/addons.less'
            },
            options:{
                sourceMap:false // true for debugging CSS issues.
            }
        },
    },

    cssmin: { // deploy task only
        main: {
            src: 'web/webroot/_ui/responsive/theme-lambda/css/style.css',
            dest: 'web/webroot/_ui/responsive/theme-lambda/css/style.css'
            }
        },

    concat: {
        options: {
            separator: ';',
        },
        addons: {
            src: [ // Get all auto generated add-on files from UI, not UI-SRC
                'web/webroot/_ui/addons/assistedservicestorefront/responsive/common/js/assistedservicestorefront.js',
                'web/webroot/_ui/addons/assistedservicestorefront/responsive/common/js/jquery.tablesorter.pager.js',
                'web/webroot/_ui/addons/assistedservicestorefront/responsive/common/js/jquery.tablesorter.min.js',
                'web/webroot/_ui/addons/assistedservicestorefront/responsive/common/js/Chart.min.js',
                'web/webroot/_ui/addons/assistedservicestorefront/responsive/common/js/asm.storefinder.js',
                'web/webroot/_ui/addons/siteonepunchoutaddon/responsive/common/js/acc.punchout.js',
                'web/webroot/_ui/addons/siteoneorgaddon/responsive/common/js/commerceorgnavigation.js',
                'web/webroot/_ui/addons/siteoneorgaddon/responsive/common/js/siteoneorgaddon.js',
                'web/webroot/_ui/addons/siteoneorgaddon/responsive/common/js/jquery.validate.js',
                'web/webroot/_ui/addons/siteoneacceleratoraddon/responsive/common/js/acc.checkoutsummary.js',
                'web/webroot/_ui/addons/siteoneacceleratoraddon/responsive/common/js/acc.paymentType.js',
                'web/webroot/_ui/addons/siteoneacceleratoraddon/responsive/common/js/acc.replenishment.js',
                'web/webroot/_ui/addons/siteoneacceleratoraddon/responsive/common/js/acc.orderform.js',
                'web/webroot/_ui/addons/siteoneacceleratoraddon/responsive/common/js/acc.approval.js',
                'web/webroot/_ui/addons/smarteditaddon/responsive/common/js/smarteditaddon.js',
                'web/webroot/_ui/addons/smarteditaddon/shared/common/js/webApplicationInjector.js',
                'web/webroot/_ui/addons/smarteditaddon/shared/common/js/reprocessPage.js',
                'web/webroot/_ui/addons/smarteditaddon/shared/common/js/adjustComponentRenderingToSE.js',
            ],
            dest: 'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__addons.js',
        },
        main: {
            src: [
                'web/webroot/_ui/shared/js/generatedVariables.js', // This is a generated file, pull from _ui, not _ui-src
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/bootstrap/dist/js/bootstrap.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/enquire.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/Imager.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.blockUI-2.66.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.colorbox-min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.form.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.hoverIntent.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.pstrength.custom-1.2.0.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.syncheight.custom.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.tabs.custom.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery-ui-1.12.1.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.zoom.custom.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/owl.carousel.custom.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.tmpl-1.0.0pre.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.currencies.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.waitforimages.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.slideviewer.custom.1.2.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery.menu-aim.js',
				        'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/uuidv4.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/Chart.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/chartjs-plugin-datalabels.min.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.address.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.autocomplete.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.carousel.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.cart.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.cartitem.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.checkout.js',
				'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.mixedcartcheckout.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.checkoutaddress.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.checkoutsteps.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.cms.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.colorbox.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.common.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.forgottenpassword.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.global.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.hopdebug.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.imagegallery.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.langcurrencyselector.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.minicart.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.navigation.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.order.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.paginationsort.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.partner.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.payment.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.paymentDetails.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.pickupinstore.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.product.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.productVariant.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.productcardvariant.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.facets.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.productDetail.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.quickview.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.quote.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.ratingstars.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.refinements.js',
				'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.adobelinktracking.js',
				'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.ga4analytics.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.silentorderpost.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.tabs.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.termsandconditions.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.track.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.storefinder.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.futurelink.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.productorderform.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.savedcarts.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.pendo.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.multidgrid.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.quickorder.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.csv-import.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/autoload.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.accountdashboard.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.unlockuser.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.sdssearch.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.mystores.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.passwordvalidation.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.promotions.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.savedlist.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.recommendedlist.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.estimate.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.myquotes.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.briteverify.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.shareproductemail.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.sharecart.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.formvalidation.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.monthlyflyer.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.sharelistemail.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.invoicepage.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.calculator.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.pdp-bulk-calculator.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.project-calc-enhancements.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/acc.project.js',
                'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/cms/*.js',
            ],
            dest: 'web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__main.js',
          },
      },

      uglify: { // Deploy task only
        generated: {
            options: {
                mangle: true,
                SourceMap: false,
                compress: true,
            },
            files: {
                'web/webroot/_ui/responsive/common/js/_main.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__main.js'],
                'web/webroot/_ui/responsive/common/js/_addons.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__addons.js'],
                // Get jquery on it's own. Untl we have a dependency manager, this is the best approach to make sure jquery loads first.
                'web/webroot/_ui/responsive/common/js/jquery-3.5.1.min.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery-3.5.1.min.js'],
                'web/webroot/_ui/responsive/common/js/jquery-editable-select.min.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery-editable-select.min.js'], 
            }
        }
      },

      copy : {
        syncfonts: {
            files: [{
                expand: true,
                cwd: 'web/webroot/WEB-INF/_ui-src/',
                src: '**/themes/**/fonts/*',
                dest: 'web/webroot/_ui/',
                rename:function(dest,src){
                    var nsrc = src.replace(new RegExp("/themes/(.*)"),"/theme-$1");
                    return dest+nsrc;
                }
            }]
        },
        devjs: { // Dev task only
            files: {
                'web/webroot/_ui/responsive/common/js/_main.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__main.js'],
                'web/webroot/_ui/responsive/common/js/_addons.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/__addons.js'],
                // Get jquery on it's own. Untl we have a dependency manager, this is the best approach to make sure jquery loads first.
                'web/webroot/_ui/responsive/common/js/jquery-3.5.1.min.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery-3.5.1.min.js'],
                'web/webroot/_ui/responsive/common/js/jquery-editable-select.min.js': ['web/webroot/WEB-INF/_ui-src/responsive/lib/ybase-0.1.0/js/vendor/jquery-editable-select.min.js'],  
            },
        },
      },

    
});
 
  // This 'build' task is also the same task that is executed by 'ant siteonestorefront_compileuisrc'
  // and 'ant all'/'ant clean all'. Be careful making modifications. View SE-7701 for details.
    grunt.registerTask('build', ['less', 'cssmin', 'concat', 'uglify', 'copy:syncfonts']);
  
  // 'dev' is used exclusively for front end development, to build non-minified files
  // You can also use the watch task to avoid manually having to run 'grunt dev' each update
  // by using 'grunt watch' this will auto-compile css/js on change furing dev.
  grunt.registerTask('dev', ['less', 'concat', 'copy']);

  // Add more / custom task here.

};
