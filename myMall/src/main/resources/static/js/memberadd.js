function result(data, status) {
    if (data !== "帳號重複請重新註冊") {
        alert("註冊成功");
        location.href = "memberLogin";
    } else {
        $("#msg").html(data);
    }
}

function memberadd(evt) {
    evt.preventDefault();
    
    const obj = {
        loginusername: $("#loginusername").val(),
        loginpassword: $("#loginpassword").val(),
        name: $("#name").val(),
        phone: $("#phone").val()
    };

    if (obj.loginusername === "" || obj.loginpassword === "" || obj.name === "" || obj.phone === "") {
        alert("請填所有資料");
        return; //阻止提交
    }

    $.ajax({
        url: "addMember",
        type: "post",
        dataType: "html",
        contentType: 'application/json',
        data: JSON.stringify(obj),
        success: result,
        error: function (err) {
            console.error("註冊失敗", err);
            alert("註冊失敗，請稍後再試");
        }
    });
}

$("#addm").submit(memberadd);
