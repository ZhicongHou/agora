

var user;
function init(User){
    user = User;
}

layui.use('form',function () {
    var form = layui.form;
    form.val("userData", {
        "userName": user.userName
        ,"realName":user.realName
        ,"sex": user.sex
        ,"email": user.email
        ,"phoneNumber": user.phoneNumber
        // ,"resume": "什么叫国际巨星啊（战术后仰）"
    });

    //表单提交
    form.on("submit(submit)",function (data) {
        $.ajax({
            type: "post",
            async: false,
            url: "/agora/updateUser",
            data: {
                "realName": data.field.realName,
                "sex": data.field.sex,
                // "resume": data.field.resume
            },
            datatype: "json",
            success: function (data) {
                var flag = data.flag;
                var content = data.content;
                alert(content);
                if(flag==="0"){
                    window.location.reload();
                }
            }
        });
        return false;
    });
});



