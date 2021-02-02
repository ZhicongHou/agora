
//待用，以后可以添加用户发送消息功能
layui.use('form', function () {
    var form = layui.form;

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

    //点击发送按钮
    form.on("submit(send)",function (data) {
        $(".sendBtn").addClass("layui-btn-disabled").attr("disabled",true)
            .html("发送中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
        setTimeout(function () {
            $.ajax({
                type: "post",
                async: false,
                url: "/addMessage",
                data: {
                    "receiver": data.field.receiver,
                    "content": data.field.content,
                },
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;
                    alert(content);

                    if (flag === "0") {
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        }, function(){
                            parent.window.location.reload();
                        });
                    }else{
                        layer.alert(content,{
                            skin: 'layui-layer-lan'
                            ,closeBtn: 0
                        });
                        $(".sendBtn").removeClass("layui-btn-disabled").attr("disabled",false).html("发送");
                    }
                }
            });
        },500);
        return false;
    });
});