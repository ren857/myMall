$(document).ready(function () {
	
  $.ajax({
        url: "/currentUser",  // 發送登入請求到後端
        method: "GET",
		headers: {
		       "Authorization": "Bearer " + localStorage.getItem("token")},
		success: function (response) {
			$("#loginLink").hide();  // 隱藏登入連結
			       $("#welcomeUser").text("歡迎您：" + localStorage.getItem("name"));  // 顯示使用者名稱
			       $("#logoutLink").show();  // 顯示登出連結
				   
		}
			  });

  // 登出操作
  $("#logoutLink").click(function (e) {
    e.preventDefault();
    localStorage.removeItem("token");  // 移除 Token
	localStorage.removeItem("name");
    window.location.href = "/memberLogin";  // 跳轉到首頁
  });
});
