$(document).ready(function() {


	// 從網址取得 pid，例如 http://localhost:8080/product-detail.html?pid=3
	const urlParams = new URLSearchParams(window.location.search);
	const pid = urlParams.get('pid');
	console.log(pid);
	var mypname;
	if (pid) {
		$.get(`/api/products/${pid}`, function(product) {
			$("#product-name").text(product.pname);
			$("#product-image").attr("src", "/image/" + product.image);
			$("#product-price").text("價格：NT$" + product.price);
			$("#size").text("尺寸：" + product.size);
			localStorage.setItem("pid", product.pid);
		mypname=product.pname;
		console.log("mypname:", mypname);
		$.ajax({
				url: `http://localhost:8080/api/products/productlist/${mypname}`,
				method: "GET",
				success: function(data) {
					console.log("data:", data);
					data.forEach(item => {					
						$("#size").append(`<option value="${item.size}"data-pid="${item.pid}">${item.size}</option>`)
						console.log("size2:"+item.size);
					})					
				}
			})
			// 加入數量選擇
			for (let i = 1; i <= 50; i++) {
				$("#quantity").append(new Option(i, i));  // 動態增加數量選項
			}
		}).fail(function() {
			$("#product-name").text("找不到商品");
			$("#product-price").text("");
			$("#product-size").text("");
			$("#product-image").hide();
		});
	} else {
		$("#product-name").text("未提供商品 pid");
		$("#product-image").hide();
	}
	
	/*(async function fetchProductData() {
	  // 從網址取得 pid，例如 http://localhost:8080/product-detail.html?pid=3
	  const urlParams = new URLSearchParams(window.location.search);
	  const pid = urlParams.get('pid');
	  let mypname; // 初始化 mypname 變數

	  if (pid) {
	    try {
	      // 使用 await 等待第一個 AJAX 請求完成
	      const product = await $.get(`/api/products/${pid}`);
	      // 更新網頁內容
	      $("#product-name").text(product.pname);
	      $("#product-image").attr("src", "/image/" + product.image);
	      $("#product-price").text("價格：NT$" + product.price);
	      $("#size").text("尺寸：" + product.size);
	      $("#product-brief").text(product.brief);

	      // 動態生成數量選項
	      for (let i = 1; i <= 50; i++) {
	        $("#quantity").append(new Option(i, i)); // 動態增加數量選項
	      }

	      // 設置 mypname 值
	      mypname = product.pname;
	      console.log("mypname:", mypname);

	      // 使用 mypname 進行第二個 AJAX 請求
	      if (mypname) {
	        const productList = await $.get(`http://localhost:8080/api/products/productlist/${mypname}`);
	        console.log("product list:", productList);
	        productList.forEach(item => {
	          $("#size").append(`<option value="${item.size}"data-pid="${item.pid}">${item.size}</option>`);
	          console.log("size:", item.size);
			  console.log("item",item);
			  console.log("pid",item.pid);
	        });
	      }
	    } catch (error) {
	      // 處理錯誤情況
	      console.error("錯誤發生:", error);
	      $("#product-name").text(pid ? "找不到商品" : "未提供商品 pid");
	      $("#product-image").hide();
	      $("#product-price").text("");
	      $("#size").text("");
	    }
	  } else {
	    // 如果未提供 pid
	    $("#product-name").text("未提供商品 pid");
	    $("#product-image").hide();
	  }
	})();
	*/
	
	$("#size").change(function() {
	    const selectedSize = $(this).val();  // 獲取選中的 size
	    const selectedOption = $(this).find("option:selected");  // 獲取選中的 option
	    const selectedPid = selectedOption.data("pid");  // 獲取對應的 pid
		localStorage.setItem("pid", selectedPid);
	    console.log("選擇的尺寸:", selectedSize);
	    console.log("對應的 PID:", selectedPid);

	    // 你可以將 pid 和 size 用於加入購物車的請求中
	});


	// 登入後顯示用戶資訊
	const token = localStorage.getItem("token");

	if (token) {
		$.ajax({
			url: "/currentUser",
			method: "GET",
			headers: {
				"Authorization": "Bearer " + token
			},
			success: function(response) {
				$("#loginLink").hide();
				$("#welcomeUser").text("歡迎您：" + localStorage.getItem("name"));
				$("#logoutLink").show();
			}
		});
	}

	// 登出操作
	$("#logoutLink").click(function(e) {
		e.preventDefault();
		localStorage.removeItem("token");
		window.location.href = "/index.html";
	});

	// 加入購物車表單提交
	$("#addToCartForm").submit(function(e) {
		e.preventDefault(); // 防止表單的默認提交

		// 檢查用戶是否登入
		const token = localStorage.getItem("token");
		const pid =localStorage.getItem("pid");
		if (token) {
			$.ajax({
				url: "/currentUser",  // 向後端發送請求，驗證 Token
				method: "GET",
				headers: {
					"Authorization": "Bearer " + token
				},
				success: function(user) {
					if (!user || !user.mid) {
						alert("請先登入！");
						return;
					}
					
					const mid = user.mid;
					const quantity = $("#quantity").val(); // 取得商品的數量
					console.log("pid: ", pid);
					console.log("mid: ", mid);
					console.log("quantity:", quantity);
					console.log("token:", token);

					$.ajax({
						url: `/api/add?mid=${mid}&pid=${pid}&quantity=${quantity}`,
						method: "POST",
						headers: {
							"Authorization": "Bearer " + token
						},
						success: function(response) {
							alert("商品已加入購物車");
						},
						error: function(err) {
							console.error("加入購物車錯誤", err);
							alert("加入購物車失敗");
						}
					});
				},
				error: function(xhr) {
					console.log("身份驗證錯誤：", xhr.responseText);
					alert("身份驗證錯誤，請重新登入");
				}
			});
		} else {
			alert("請先登入！");
			window.location.href = "/memberLogin";  // 跳轉到登入頁面
		}
	});
});
