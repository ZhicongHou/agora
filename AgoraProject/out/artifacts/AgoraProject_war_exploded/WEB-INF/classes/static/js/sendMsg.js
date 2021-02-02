layui.use('form', function () {
    var form = layui.form;


    //点击发送按钮
    form.on("submit(send)",function (data) {
        // $(".sendBtn").addClass("layui-btn-disabled")
        // .html("发送中 <i class=\"layui-icon layui-icon-loading-1 layui-anim layui-anim-rotate layui-anim-loop\" style=\"display: inline-block\"></i>");
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
                    parent.window.location.reload();
                }
            }
        });
        //改变发送按钮样式
        return false;
    });
    return false;
});