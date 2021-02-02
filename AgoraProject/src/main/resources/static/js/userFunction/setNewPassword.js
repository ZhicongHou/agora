layui.use(['form','element','jquery','layer'],function () {
    var form = layui.form,
        element=layui.element,
        layer=layui.layer,
        $ = layui.jquery;

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏
    form.verify({
        password:function(value){
            if(value === null){
                return '密码不能为空！'
            }else if(value.length>20){
                return '密码长度不能超过20字符！';
            }else if(!/^[a-zA-Z0-9]+$/.test(value)){
                return '密码只能由数字和字母组成！';
            }
        }
        ,confirmPw:function (value) {
            if(value !== $('#newPassword').val()){
                return '密码与确认密码不一致！';
            }
        }
    });


    form.on("submit(setNewPassword)",function (data) {
        // var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        $.ajax({
            type: "post",
            async: false,
            url: "/executeChangePassword",
            data: {
                "newPassword": data.field.newPassword,
                "encodeString": data.field.encodeString
            },
            datatype: "json",
            success: function (data) {
                var flag = data.flag;
                var content = data.content;
                alert(content);
                if(flag==="0"){
                    window.location.href="/index";
                }
            }
        });
        return false;  //return true表明验证成功，然后访问post链接
    });

});