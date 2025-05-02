$(document).ready(function() {
	const name = localStorage.getItem("name")
	$.ajax({
		url: "/currentUser",  // 發送登入請求到後端
		method: "GET",
		headers: {
			"Authorization": "Bearer " + localStorage.getItem("token")
		},
		success: function(response) {
			$("#loginLink").hide();  // 隱藏登入連結
			$("#welcomeUser").text("歡迎您：" + name);  // 顯示使用者名稱
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
	let token =localStorage.getItem("token")
	let cart = JSON.parse(localStorage.getItem("cart"))
	console.log("購物車cart: ", cart);
	let cartData = [];
	if (cart) {
		cartData = cart.filter(item => item.membername === name);
	}
	console.log("name名子:" + cartData)
	if (cartData) {
		renderCart(cartData);  // 把找到的 cartData 作為陣列傳遞給 renderCart 函數
	} else {
		console.log("沒有找到 name 的購物車資料");
	}

	// 渲染購物車的函數
	function renderCart(cartItems) {
		let total = 0;
		const cartBody = document.getElementById("cart-body");
		cartBody.innerHTML = ''; // 清空之前的内容

		if(token){
		cartItems.forEach((item, index) => {
			const subtotal = item.price * item.quantity; // 小計
			total += subtotal;

			// 創建顯示商品的一行
			const row = document.createElement("tr");
			row.innerHTML = `
                <td>
                    <img src="${item.image}" alt="商品圖片" width="150">
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
	}else{
		alert("請先登入");
		window.location.href = "/memberLogin";
	}

		// 更新總金額
		document.getElementById("total-money").innerText = `NT ${total}`;
	}

	// 數量變動的函數
	window.changeQuantity = function(index, delta) {
		const cartItems = JSON.parse(localStorage.getItem("cart")) || [];
		console.log("quantity" + cartItems[index].quantity);
		// 更新數量，並防止小於 1
		let currentQuantity = parseInt(cartItems[index].quantity);
		currentQuantity += delta;
		if (currentQuantity < 1) currentQuantity = 1;
		cartItems[index].quantity = currentQuantity;
		// 更新顯示的數量
		document.getElementById(`quantity-${index}`).innerText = currentQuantity;

		// 更新本地存儲的 cartItems
		localStorage.setItem("cart", JSON.stringify(cartItems));
		console.log("currentQuantity:" + currentQuantity)
		// 渲染購物車
		renderCart(cartItems);
	}

	// 刪除商品
	window.removeItem = function(index) {
		// 讀取購物車數據
		const cartItems = JSON.parse(localStorage.getItem("cart")) || [];

		// 刪除指定索引的商品
		cartItems.splice(index, 1);  // splice 用來刪除數組中的項目

		// 保存更新後的 cartItems 到 localStorage
		localStorage.setItem("cart", JSON.stringify(cartItems));

		// 重新渲染購物車
		renderCart(cartItems);
	}

	$("#addToCartForm").submit(function(e) {
		e.preventDefault();  // 防止表單默認提交

		const cartItems = JSON.parse(localStorage.getItem("cart"));
		const token = localStorage.getItem("token");
		const userName = localStorage.getItem("name");
		const userCart = cartItems.filter(item => item.membername === userName);
		console.log("cartItems01:  ", cartItems);
		if (!token) {
			alert("請先登入！");
			window.location.href = "/memberLogin";  // 跳轉到登入頁面
			return;
		}
		console.log("userCart00:", userCart);

		// 發送訂單請求
		$.ajax({
			url: "/api/orders/submitOrder",  // 提交訂單的後端接口
			method: "POST",
			headers: {
				"Authorization": "Bearer " + token
			},
			contentType: "application/json",
			data: JSON.stringify(userCart),  // 將訂單資料發送到後端
			success: function(response) {
				const updatedCart = cartItems.filter(item => item.membername !== userName);
				localStorage.setItem("cart", JSON.stringify(updatedCart));				
				alert("訂單已成功創建！");
				window.location.href = "/api/orders";  // 跳轉到訂單頁面
				console.log("userCart:", userCart)
			},
			error: function(error) {
				console.error("創建訂單失敗:", error);
				alert("創建訂單失敗");
			}
		});

	});
});