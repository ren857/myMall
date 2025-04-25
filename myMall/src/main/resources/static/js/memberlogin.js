$(document).ready(function() {
	// 登入成功後儲存 token
	$("#loginForm").on("submit", function(e) {
		e.preventDefault(); // 不讓表單自己送出

		const data = {
			loginusername: $("#loginusername").val(),
			loginpassword: $("#loginpassword").val()
		};

		$.ajax({
			url: "/login",  // 發送登入請求到後端
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify(data),  // 登入資料
			success: function(response) {
				// 儲存 Token
				localStorage.setItem("token", response.token);
				localStorage.setItem("name", response.name);
				localStorage.setItem("mid",response.mid);
				console.log("123");
				console.log("123"+response.mid);
				console.log(response);
				console.log("token:"+response.token);
				
				window.location.href = "/index.html";  // 登入成功後跳轉到首頁
			},
			error: function(xhr) {
				alert(xhr.responseText);  // 顯示後端返回的錯誤訊息
			}
		});
	});
});


// 顯示或隱藏密碼
document.getElementById("showPassword").addEventListener("change", function() {
	const pwField = document.getElementById("loginpassword");
	pwField.type = this.checked ? "text" : "password";
});

// 跳轉到註冊頁面
$(document).ready(function() {
	$('#loginadd').on('submit', function(e) {
		e.preventDefault();  // 防止表單提交

		// 跳轉到註冊頁面
		window.location.href = "/memberadd";  // 使用 window.location.href 跳轉到 /memberadd 頁面
	});
});


