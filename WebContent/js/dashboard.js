//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});


// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        }
        else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}( jQuery ));

//Function toggleNumberOfElementPrinted
(function ( $ ) {
	$.fn.toggleNumberOfElementPrinted = function(id) {
		var idElement = '';
		if (id == 'tenElements') {
			idElement = '#' + id;
			$(idElement).prop("disabled", true);
			$('#fiftyElements').prop("disabled", false);
			$('#hundredElements').prop("disabled", false);
		} else {
			if (id == 'fiftyElements') {
				idElement = '#' + id;
				$(idElement).prop("disabled", true);
				$('#tenElements').prop("disabled", false);
				$('#hundredElements').prop("disabled", false);
			} else {
				if (id == 'hundredElements') {
					idElement = '#' + id;
					$(idElement).prop("disabled", true);
					$('#tenElements').prop("disabled", false);
					$('#fiftyElements').prop("disabled", false);
				}
			}
		}
		
		var data = "numberElementPerPages=" + $(idElement).val();
	        $.ajax({
	           type: "GET",
	           url: "dashboard",
	           data: data, 
	           success: function(result) {
	        	   if(typeof(result) !=='undefined'){
	        		   $('#results').html($($.parseHTML(result)).find("#results").html());
	        		   $(idElement).html($($.parseHTML(result)).find(idElement).html());
	        		   $(".editMode").hide();
	        		   console.log(result);
	        	   }
	           },
	           error: function(error) {
	        	   console.log('error');
	        	   console.log(error);
	           }
	       });
	}

}( jQuery ));

// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm("Are you sure you want to delete the selected computers?")) { 
            $('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));



//Event handling
//Onkeydown
$(document).keydown(function(e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if(e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});

