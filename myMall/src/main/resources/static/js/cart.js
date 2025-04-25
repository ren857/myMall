$(document).ready(function() {
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
		window.location.href = "/index.html";  // 跳轉到首頁
	});





	// 確保後端返回的數據能夠正確渲染
	$.ajax({
		url: "http://localhost:8080/api/cart",
		method: "GET",
		headers: {
			"Authorization": "Bearer " + localStorage.getItem("token")
		},
		success: function(cartObject) {
			console.log(cartObject);
			if (cartObject.length === 0) {
				alert("購物車內沒有商品！");
			}

			// 將購物車數據保存至 localStorage
			localStorage.setItem("cartItems", JSON.stringify(cartObject));

			// 渲染購物車
			renderCart(cartObject);
		},
		error: function() {
			console.error("獲取購物車資料出錯:");
			alert("獲取購物車資料失敗");
		}
	});

	// 渲染購物車的函數
	function renderCart(cartItems) {
		let total = 0;
		const cartBody = document.getElementById("cart-body");
		cartBody.innerHTML = ''; // 清空之前的内容

		cartItems.forEach((item, index) => {
			const subtotal = item.price * item.quantity; // 小計
			total += subtotal;

			// 創建顯示商品的一行
			const row = document.createElement("tr");
			row.innerHTML = `
                <td>
                    <img src="/image/${item.image}" alt="商品圖片" width="150">
                    <p>${item.pname}</p>
                </td>
                <td>尺寸：${item.size}</td>               
                <td>NT$${item.price}</td>
                <td>
                    <div class="quantity-controls">
                        <button class="btn" onclick="changeQuantity(${index}, -1)">-</button>
                        <span id="quantity-${index}">${item.quantity}</span>
                        <button class="btn" onclick="changeQuantity(${index}, 1)">+</button>
                    </div>
                </td>
                <td>NT$${subtotal}</td>
                <td>
                    <button class="btn" onclick="removeItem(${index})">刪除</button>
                </td>
            `;
			cartBody.appendChild(row);
		});

		// 更新總金額
		//document.getElementById("total-amount").innerHTML = `總金額：<span style="color:red">NT$ ${total}</span>`;
		document.getElementById("total-money").innerText = `NT ${total}`;
	}

	// 數量變動的函數
	window.changeQuantity = function(index, delta) {
		const cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];


		// 更新數量，並防止小於 1
		cartItems[index].quantity += delta;
		if (cartItems[index].quantity < 1) cartItems[index].quantity = 1;

		// 更新顯示的數量
		document.getElementById(`quantity-${index}`).innerText = cartItems[index].quantity;

		// 更新本地存儲的 cartItems
		localStorage.setItem("cartItems", JSON.stringify(cartItems));

		// 渲染購物車
		renderCart(cartItems);

	}

	// 刪除商品
	window.removeItem = function(index) {
		const cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
		const token = localStorage.getItem("token");
		console.log("Updated cartItems in localStorage:", localStorage.getItem("cartItems"));
		// 刪除指定索引的商品

		removeObj=cartItems.splice(index, 1);
		console.log("removeObj",removeObj);
		localStorage.setItem("cartItems", JSON.stringify(cartItems));  // 保存更新後的 cartItems
		console.log("Updated cartItems in localStorage2:", localStorage.getItem("cartItems"));
		console.log("removeObj2",removeObj[0]);
		$.ajax({
					url: "http://localhost:8080/api/remove",
					method: "Delete",
					headers: {
						"Authorization": "Bearer " + token
					},
					contentType: "application/json",
					data: JSON.stringify(removeObj[0]),
					success: function(respData) {
						alert(respData);
					},
					error: function() {
						console.error("獲取購物車資料出錯:");
						alert(respData);
					}
				});
		renderCart(cartItems); // 更新渲染
	}

	// 結帳操作
	$("#addToCartForm").submit(function(e) {
		e.preventDefault();  // 防止表單默認提交
		const cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
		console.log("Updated cartItems in localStorage3:", localStorage.getItem("cartItems"));
		const token = localStorage.getItem("token");
		if (!token) {
			alert("請先登入！");
			window.location.href = "/memberLogin";  // 跳轉到登入頁面
			return;
		}

		$.ajax({
			url: "http://localhost:8080/api/updateQuantity",
			method: "POST",
			headers: {
				"Authorization": "Bearer " + token
			},
			contentType: "application/json",
			data: JSON.stringify(cartItems),
			success: function(respData) {
				alert(respData);
			},
			error: function() {
				console.error("獲取購物車資料出錯:");
				alert(respData);
			}
		});

	});

});