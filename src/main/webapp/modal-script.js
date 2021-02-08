
var modal = document.getElementById("myModal");
var yesButton = document.getElementById("confirmbutton");
var question = document.getElementById("question");
var span = document.getElementsByClassName("close")[0];
var modalFooter = document.getElementById("modal-footer-text"); 






span.onclick = function() {
	modal.style.display = "none";
}
function closeModal() {
	modal.style.display = "none";
}
window.onclick = function(event) {
	if (event.target == modal) {
	 modal.style.display = "none";
	}
	}

function cancelConfirmation(id) {
	emailElement = document.getElementById("email");
	if (emailElement != null) {

	if (emailElement.checkValidity() != false ) {
			modal.style.display = "block";
			document.getElementById("emailSpan").innerHTML = "";
			question.innerHTML = "Are you sure you want to cancel this meeting?";
			modalFooter.innerHTML = "If you change your mind you will have to wait all queue again.";
			yesButton.setAttribute('onclick','cencelTicket('+ id + ')');
		}
		else {
			document.getElementById("emailSpan").innerHTML = "Enter correnct e-mail."
 }
		
	}
	
	else { 
	modal.style.display = "block";
	question.innerHTML = "Are you sure you want to cancel this meeting?";
	modalFooter.innerHTML = "If you change your mind you will have to wait all queue again.";
	yesButton.setAttribute('onclick','cencelTicket('+ id + ')');
	}
}

function cencelTicket(id) {
	if (document.getElementById("email") != null) {
	var email = document.getElementById("email").value;
	var url = '/cancelticket?code=' + id  + '&email=' + email;
	window.location.href = url;
	}

	else {
		var url = '/cancelticket?code=';
		url = url + id;
		window.location.href = url;
	}
}

function deleteSpecialistConfirm(id) {
	modal.style.display = "block";
	question.innerHTML = "Are you sure you want to delete this specialist?";
	modalFooter.innerHTML = "All queue for this specialist will be removed as well.";
	yesButton.setAttribute('onclick','deleteSpecialist('+ id + ')');
	}
	
	
	
	function deleteSpecialist(id) {			
			modal.style.display = "none";
			 $.ajax({ 
		         type : "DELETE",
		         url : "/admin/removespecialist?id=" + id,
		      
		         success: function(data){
		    			if(data != 0) {
		         	$('#specialist' + id).closest('tr').remove();
		    			}  
		         }
		     });
		}
		