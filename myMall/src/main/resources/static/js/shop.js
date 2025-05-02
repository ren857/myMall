$(document).ready(function() {

	$.ajax({
		url: "http://localhost:8080/api/products/listname",
		method: "GET",
		success: function(data) {
			console.log("data:", data);
			data.forEach(item => {
				$("#card-row").append(`<div class="col-md-4">
			 <div class="card" data-pid=${item.pid}>
			 <img class="card-img-top" src="/image/${item.image}" alt="Card image">
			 <div class="card-body">
			 <h4 class="card-name">${item.pname}</h4>
			 <p class="card-text">${item.brief}   ​</p >
			<a href="#" class="btn btn-primary">前往購物</a>
			 </div >
			</div >
			 </div >`)
			})
			// 點擊每個商品卡片，導向對應的頁面
			$(".card").click(function() {
				const pid = $(this).data("pid");
				console.log("pid"+pid);
				window.location.href = `/api/products/detail?pid=${pid}`;
			});
		}
	});

	$.ajax({
		url: "/currentUser",  // 發送登入請求到後端
		method: "GET",
		headers: {
			"Authorization": "Bearer " + localStorage.getItem("token")
		},
		success: function(response) {
			$("#loginLink").hide();  // 隱藏登入連結
			$("#welcomeUser").text("歡迎您：" + localStorage.getItem("name"));  // 顯示使用者名稱
			$("#logoutLink").show();  // 顯示登出連結
		}
	});

	// 登出操作
	$("#logoutLink").click(function(e) {
		e.preventDefault();
		localStorage.removeItem("token");  // 移除 Token
		localStorage.removeItem("name");
		window.location.href = "/memberLogin";  // 跳轉到首頁
	});

});