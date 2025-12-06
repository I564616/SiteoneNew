<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!-- Project Dashboard Heading -->
<section class="row margin0 bg-dark-grey border-radius-3 flex-center p-x-10 p-y-30 project-dashboard-page">
    <div class="col-md-7 f-s-24 font-GeogrotesqueSemiBold text-white">
        Project Dashboard
    </div>
    <div class="col-md-3 padding0 text-align-right">
        <div class="btn-group">
            <button type="button" class="btn bg-dark-grey border-grayish border-radius-3-imp text-white js-manage-drf" data-toggle="popover" data-content="Create and manage Design Request Form Templates to enable quicker Project submissions.">Manage DRF Templates</button>
            <button type="button" class="btn bg-dark-grey border-grayish border-radius-3-imp text-white dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="glyphicon glyphicon-chevron-down m-l-10 f-s-12" aria-hidden="true"></span>
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Separated link</a></li>
            </ul>
        </div>
    </div>
    <div class="col-md-2">
        <button class="btn btn-primary btn-block flex-center"><span class="glyphicon glyphicon-plus m-r-10 f-s-12" aria-hidden="true"></span>Submit New Project</button>
    </div>
</section><!-- ./Project Dashboard Heading -->
<div class="row margin0 m-y-15 project-dashboard-page">
    <!-- Left Filter section -->
    <section class="col-md-3 col-md-20pe border-grey border-radius-3">
        <div class="row bg-light-gray">
            <div class="col-md-3 padding0">
                <button class="btn bg-transparent text-dark-gray"><span class="project-filter-num">0</span> Filter<span class="project-filter-s" style="display: none;">s</span></button>
            </div>
            <div class="col-md-3 padding0">
                <button class="btn btn-link" onclick="ACC.project.clearFilter(this)">clear</button>
            </div>
            <div class="col-md-6 padding0">
                
            </div>
            <div class="col-md-9 b-t-grey-2 p-t-10 font-GeogrotesqueSemiBold text-default">
                Expand all Filters
            </div>
            <div class="col-md-3 b-t-grey-2 p-t-10 text-align-right">
                <div class="circle-radio">
                    <input type="checkbox" class="no-margin circle-radio-input" id="Cheap" value="Cheap" onchange="ACC.project.expandCheck(this)">
                    <div class="circle-radio-indicator"></div>
                </div>
            </div>
        </div>
        <div class="row b-t-grey">
            <div class="col-xs-12 padding0">
                <button class="btn btn-white-default btn-block text-default text-align-left bold project-accordion-button project-accordion-open" onclick="ACC.global.openCloseAccordion(this,'open', 1, 'project-accordion','chevron-down,chevron-up', true);" data-acconum="1">Project Status <span class="glyphicon glyphicon-chevron-up pull-right f-s-12 m-t-5"></span></button>
            </div>
            <div class="col-xs-12 p-y-20 project-accordion-data-1">
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 1" for="selected-brand1">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand1" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 1 , 'brand' , 'Option 1')" type="checkbox" value="Option 1" data-role="1" data-brand="Option 1">
                    </span>Option 1
                </label>
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 2" for="selected-brand2">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand2" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 2 , 'brand' , 'Option 2')" type="checkbox" value="Option 2" data-role="2" data-brand="Option 2">
                    </span>Option 2
                </label>
            </div>
        </div>
        <div class="row b-t-grey">
            <div class="col-xs-12 padding0">
                <button class="btn btn-white-default btn-block text-default text-align-left bold project-accordion-button project-accordion-open" onclick="ACC.global.openCloseAccordion(this,'open', 2, 'project-accordion','chevron-down,chevron-up', true);" data-acconum="2">Project Type <span class="glyphicon glyphicon-chevron-up pull-right f-s-12 m-t-5"></span></button>
            </div>
            <div class="col-xs-12 p-y-20 project-accordion-data-2">
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 1" for="selected-brand3">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand3" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 1 , 'brand' , 'Option 1')" type="checkbox" value="Option 1" data-role="1" data-brand="Option 1">
                    </span>Option 1
                </label>
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 2" for="selected-brand4">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand4" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 2 , 'brand' , 'Option 2')" type="checkbox" value="Option 2" data-role="2" data-brand="Option 2">
                    </span>Option 2
                </label>
            </div>
        </div>
        <div class="row b-t-grey">
            <div class="col-xs-12 padding0">
                <button class="btn btn-white-default btn-block text-default text-align-left bold project-accordion-button project-accordion-close" onclick="ACC.global.openCloseAccordion(this,'close', 3, 'project-accordion','chevron-down,chevron-up', true);" data-acconum="3">Status <span class="glyphicon glyphicon-chevron-down pull-right f-s-12 m-t-5"></span></button>
            </div>
            <div class="col-xs-12 p-y-20 project-accordion-data-3" style="display: none;">
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 1" for="selected-brand5">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand5" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 1 , 'brand' , 'Option 1')" type="checkbox" value="Option 1" data-role="1" data-brand="Option 1">
                    </span>Option 1
                </label>
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 2" for="selected-brand6">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand6" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 2 , 'brand' , 'Option 2')" type="checkbox" value="Option 2" data-role="2" data-brand="Option 2">
                    </span>Option 2
                </label>
            </div>
        </div>
        <div class="row b-t-grey">
            <div class="col-xs-12 padding0">
                <button class="btn btn-white-default btn-block text-default text-align-left bold project-accordion-button project-accordion-close" onclick="ACC.global.openCloseAccordion(this,'close', 4, 'project-accordion','chevron-down,chevron-up', true);" data-acconum="4">Project Under Revision <span class="glyphicon glyphicon-chevron-down pull-right f-s-12 m-t-5"></span></button>
            </div>
            <div class="col-xs-12 p-y-20 project-accordion-data-4" style="display: none;">
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 1" for="selected-brand7">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand7" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 1 , 'brand' , 'Option 1')" type="checkbox" value="Option 1" data-role="1" data-brand="Option 1">
                    </span>Option 1
                </label>
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 2" for="selected-brand8">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand8" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 2 , 'brand' , 'Option 2')" type="checkbox" value="Option 2" data-role="2" data-brand="Option 2">
                    </span>Option 2
                </label>
            </div>
        </div>
        <div class="row b-t-grey">
            <div class="col-xs-12 padding0">
                <button class="btn btn-white-default btn-block text-default text-align-left bold project-accordion-button project-accordion-close" onclick="ACC.global.openCloseAccordion(this,'close', 5, 'project-accordion','chevron-down,chevron-up', true);" data-acconum="5">Bidding Projects <span class="glyphicon glyphicon-chevron-down pull-right f-s-12 m-t-5"></span></button>
            </div>
            <div class="col-xs-12 p-y-20 project-accordion-data-5" style="display: none;">
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 1" for="selected-brand9">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand9" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 1 , 'brand' , 'Option 1')" type="checkbox" value="Option 1" data-role="1" data-brand="Option 1">
                    </span>Option 1
                </label>
                <label class="font-size-14 flex-center pointer transition-3s project-group-item" data-labelbrand="Option 2" for="selected-brand10">
                    <span class="colored-primary hidden-print p-r-10">
                        <input aria-label="Checkbox" id="selected-brand10" class="text-align-center project-group-item-check no-margin" onclick="ACC.project.filterOptionsCheck(this, 2 , 'brand' , 'Option 2')" type="checkbox" value="Option 2" data-role="2" data-brand="Option 2">
                    </span>Option 2
                </label>
            </div>
        </div>
    </section><!-- ./Left Filter section -->
    <div class="col-md-9 col-md-80pe">
        <!-- Right Filter section -->
        <section class="row flex-center m-l-0 bg-light-gray border-grey border-radius-3 p-y-20">
            <div class="col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon bg-white f-s-20 text-green" id="basic-addon1"><span class="glyphicon glyphicon-search"></span></span>
                    <input type="text" class="form-control" aria-label="...">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-white-border dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Project <span class="glyphicon glyphicon-chevron-down f-s-12 m-r-5" aria-hidden="true"></span></button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li><a href="#">Project</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Separated link</a></li>
                        </ul>
                    </div><!-- /btn-group -->
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
            <div class="col-md-7 text-right">
                <span class="font-GeogrotesqueSemiBold">Results per page</span>
                <div class="btn-group" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-white-border btn-small m-x-10 dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            50
                            <span class="glyphicon glyphicon-chevron-down f-s-12 m-r-5" aria-hidden="true"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#" class="disabled">50</a></li>
                            <li><a href="#">100</a></li>
                            <li><a href="#">150</a></li>
                            <li><a href="#">200</a></li>
                            <li><a href="#">250</a></li>
                        </ul>
                    </div>
                    <button type="button" class="btn btn-white-round btn-small m-r-10"><span class="glyphicon glyphicon-chevron-left f-s-12"></span></button>
                    <button type="button" class="btn btn-white-round btn-small"><span class="glyphicon glyphicon-chevron-right f-s-12"></span></button>
                </div>
            </div>
        </section><!-- ./Right Filter section -->
        <section class="row m-t-15 m-l-0 border-grey b-b-0 border-radius-3 js-project-data-holder"><!-- Right Project Card section --></section>
    </div>
</div>