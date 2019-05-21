//On load
$(function() {
    // Default: hide errorCaontainer
    $("#errorContainer").hide();
});

$("#submit").click(function() {
	var name = $("#computerName").val();
	var introduced = $("#introduced").val();
	var discontinued = $("#discontinued").val();
	var companyId = $("#companyId").val();
	
	if (name == '') {
		$("#errorContainer").show();
		$('#errorMessage').html("Veuillez entrer un nom");
		return false;
	}
	
	var regex = new RegExp("^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
			+ "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
			+ "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
			+ "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	
	if (!(regex.test(introduced) || introduced == '')) {
		$("#errorContainer").show();
		$('#errorMessage').html("Veuillez entrer une date d'introduction valide");
		return false;
	}
	
	if (!(regex.test(discontinued) || discontinued == '')) {
		$("#errorContainer").show();
		$('#errorMessage').html("Veuillez entrer une date de discontinuité valide");
		return false;
	}
	
	introduced = new Date(introduced);
	discontinued = new Date(discontinued);
	var timestampedDate = new Date("1970-01-01");
	
	if ((introduced != '') && (introduced.getTime() <= timestampedDate.getTime())) {
		$("#errorContainer").show();
		$('#errorMessage').html("Il faut que la date d'introduction soit supérieure au 1er Janvier 1070");
		return false;
	}
	
	if ((discontinued != '') && (discontinued.getTime() <= timestampedDate.getTime())) {
		$("#errorContainer").show();
		$('#errorMessage').html("Il faut que la date de discontinuité soit supérieure au 1er Janvier 1070");
		return false;
	}
	
	if ((introduced != '') && (discontinued != '') && (discontinued.getTime() <= introduced.getTime())) {
		$("#errorContainer").show();
		$('#errorMessage').html("Il faut que la date de discontinuité soit supérieure à la date d'introduction");
		return false;
	}
	
	if (!((companyId == '') || (companyId > 0))) {
		$("#errorContainer").show();
		$('#errorMessage').html("Veuillez entrer une entreprise valide ou bien laisser le champs vide");
		return false;
	}
});