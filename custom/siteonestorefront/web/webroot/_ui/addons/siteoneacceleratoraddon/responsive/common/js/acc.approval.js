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