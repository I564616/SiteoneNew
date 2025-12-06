<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="quote-update-expired hidden"> 
   <div class='row'>
      <div class='col-md-12 marginTopBVottom30 m-b-0-xs m-b-0-sm'>
         <p class='f-s-28 f-s-22-xs-px text-default font-Geogrotesque'>
            <spring:theme code='myquotes.expired.title' />
            <span class='p-l-3 quote-update-expired-number'></span>
         </p>
         <p class='f-s-16 f-s-16-xs-px text-dark-gray'>
            <spring:theme code='myquotes.expired.message' />
         </p>
      </div>
      <div class='col-md-12 marginBottom30 m-y-15-xs m-y-15-sm'>
         <label class='control-label float-left f-s-16-xs-px f-s-16-sm-px text-dark-gray'>
            <spring:theme code='myquotes.expired.note' /><span class="hidden-xs hidden-sm"> (<spring:theme code='myquotes.expired.optional' />)</span> :
         </label>
         <textarea rows="2" cols="50" type='text' class='form-control quote-update-expired-input' name='inputNote' placeholder='<spring:theme code="myquotes.expired.input" />' maxlength='150' ></textarea>
      </div>
   </div>
   <div class="row flex justify-center flex-dir-column-xs flex-dir-column-sm">
      <div class="col-sm-12 col-md-5 hidden-md hidden-lg">
         <button onclick="ACC.myquotes.sendExpiredQuote()" class="btn btn-block btn-primary bold-text"><spring:theme code='myquotes.expired.confirm' /></button>
      </div>
      <div class="col-sm-12 col-md-5 margin-bot-10-sm">
         <button onclick="ACC.colorbox.close();" class="btn btn-block btn-default bold-text"><spring:theme code='myquotes.expired.cancel' /></button>
      </div>
      <div class="col-sm-12 col-md-5 hidden-xs hidden-sm">
         <button onclick="ACC.myquotes.sendExpiredQuote()" class="btn btn-block btn-primary bold-text"><spring:theme code='myquotes.expired.confirm' /></button>
      </div>
   </div>
</div>