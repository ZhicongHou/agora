layui.use(['form','jquery','laydate','upload'],function () {


    var form = layui.form;
    var upload = layui.upload;

    //表单验证
    form.verify({
        checkPhone:function (value) {
            if(value.length !== 11){
                return '请输入正确的手机号码！';
            }
        }
        ,checkID:function (value) {
            var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if(!reg.test(value)){
                return '请输入正确的身份证号！';
            }
        }
        ,faceImg:function (value) {
            if(value===""){
                return '请上传身份证的正面照！';
            }
        }
        ,backImg:function (value) {
            if(value===""){
                return '请上传身份证的反面照！';
            }
        }
        ,photo:function (value) {
            if(value===""){
                return '请上传老师个人照片！'
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
                "resume": data.field.resume,
                "IDNumber": data.field.IDNumber
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


    //身份证正面照上传
    var uploadFace = upload.render({
        elem: '#idCardFace'
        ,url: '/uploadTeacherImage'
        ,field : 'file'
        ,data: {type: 'front'}
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#face').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功，将url填写到隐藏的表单项
            document.getElementById("face_url").value = res.url;
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var faceText = $('#faceText');
            faceText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs layui-btn-danger" id="reloadFace">重试</a>');
            faceText.find('#reloadFace').on('click', function(){
                uploadFace.upload();
            });
        }
    });


    //身份证正面照上传
    var uploadBack = upload.render({
        elem: '#idCardBack'
        ,url: '/uploadTeacherImage'
        ,field : 'file'
        ,data: {type:'back'}
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#back').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功，将url填写到隐藏的表单项
            document.getElementById("back_url").value = res.url;
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var backText = $('#backText');
            backText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs layui-btn-danger" id="reloadBack">重试</a>');
            backText.find('#reloadBack').on('click', function(){
                uploadBack.upload();
            });
        }
    });

    //个人照上传
    var uploadPhoto = upload.render({
        elem: '#photoUpload'
        ,url: '/uploadTeacherImage'
        ,field : 'file'
        ,data: {type:'person'}
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#photoPreview').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功，将url填写到隐藏的表单项
            document.getElementById("photo_url").value = res.url;
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var photoText = $('#photoText');
            photoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs layui-btn-danger" id="reloadPho">重试</a>');
            photoText.find('#reloadPho').on('click', function(){
                uploadBack.upload();
            });
        }
    });
});