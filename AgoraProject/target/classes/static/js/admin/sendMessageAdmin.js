//消息发送
layui.use(['form','element','layer'], function() {
    var form = layui.form,
        element = layui.element,
        layer = layui.layer;

    //设置用户名表单是否显示以及是否必填
    form.on('select(type)', function (data) {
        if (data.value === 'one') {
            $("#receiverItem input").attr("required", true).attr("lay-verify", "required");
            $("#receiverItem").slideDown();
        } else {
            $("#receiverItem input").attr("required", false).attr("lay-verify", "");
            $("#receiverItem").slideUp();
        }
    });

    $("#messageFrom").submit(function () {
        //修改按钮样式
        $(".sendBtn").addClass("layui-btn-disabled").attr("disabled",true)
            .html("发送中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
        setTimeout(function () {//延时发送，改变按钮样式才能正确执行
            $.ajax({
                type: 'post',
                async: false,
                url: "/addMessage",
                data: {
                    "receiver": $("#receiver").val(),
                    "receiverId":$("#receiverId").val(),
                    "content": $("#messageContent").val(),
                },
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;

                    // 将按钮样式变回来
                    $(".sendBtn").removeClass("layui-btn-disabled").attr("disabled",false).html("发送");
                    layer.alert(content,{
                        skin: 'layui-layer-lan'
                        ,closeBtn: 0
                    });
                    if (flag === "0") {
                        //清空表单的值
                        form.val('messageFrom',{
                            'receiver':''
                            ,'receiverId':''
                            ,'messageContent':''
                        });
                        // parent.window.location.reload();
                    }
                }
            });
        },500);
        return false;
    });

});