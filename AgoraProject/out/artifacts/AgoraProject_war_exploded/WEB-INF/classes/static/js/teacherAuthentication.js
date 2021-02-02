layui.use(['form','jquery','laydate'],function () {
    var form = layui.form;

    //表单验证
    form.verify({
        checkPhone:function (value) {
            if(value.length !== 11){
                return '请输入正确的手机号码！';
            }
        }
    });

    //表单提交
    form.on("submit(submit)",function (data) {
        $.ajax({
            type: "post",
            async: false,
            url: "/addTeaAuthen",
            data: {
                "phoneNumber": data.field.phoneNumber,
                "alipayAccount": data.field.alipay,
                "resume": data.field.resume
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
        return false;
    });



});