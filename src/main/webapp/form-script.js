
	function check(input) {
		if (input.value != document.getElementById('password').value) {
			input.setCustomValidity('Password does not match.');
		} else {
			input.setCustomValidity('');
		}
	}
	function checkUsername(input) {
		$.ajax({
			type : "GET",
			url : "/newuser/username?username=" + input.value,

			success : function(data) {
				if (data === true) {
	
					document.getElementById("usernameSpan").innerHTML = "This username already exists!";
					input.setCustomValidity('This username already exists!');
				}
				else {
					document.getElementById("usernameSpan").innerHTML = "";
					input.setCustomValidity('');		
				}
			}
		});
	}
	
	function checkEmail(input) {
		$.ajax({
			type : "GET",
			url : "/newuser/email?email=" + input.value,

			success : function(data) {
				if (data === true) {
					document.getElementById("emailSpan").innerHTML = "User with this email already registered.";
					input.setCustomValidity('User with this email already registered.');
				}
				else {
					document.getElementById("emailSpan").innerHTML = "";
					input.setCustomValidity('');		
				}
			}
		});
	}

	function checkOldPassword() {

		var oldPassword = document.getElementById("oldPassword").value;
		$.ajax({
			type : "POST",
			url : "/checkpassword",
			data: { "oldPass" : oldPassword },
			success : function(data) {
				if (data === true) {
					document.getElementById('oldPassword').setCustomValidity('');
				}
				else {
					document.getElementById('oldPassword').setCustomValidity('Wrong password');	
				}
			}

		 });
	};	
	