

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

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏
    //表单提交
    form.on("submit(submit)",function (data) {
        // $(".confirmBtn").addClass("layui-btn-disabled").attr("disabled",true)
        //     .html("修改中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
        setTimeout(function () {
            $.ajax({
                type: "post",
                async: false,
                url: "/updateUser",
                data: {
                    "userName": data.field.userName,
                    "realName": data.field.realName,
                    "sex": data.field.sex,
                    // "resume": data.field.resume
                },
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;
                    // alert(content);
                    if(flag==="0"){
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        }, function(){
                            window.location.reload();
                        });
                    }else {
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        });
                        //将注册按钮的样式变回来
                        $(".confirmBtn").removeClass("layui-btn-disabled").attr("disabled",false).html("确认修改");
                    }
                }
            });
        },500);
        return false;
    });
});



