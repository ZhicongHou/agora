var secId;
var appId;
var userName;
var roomNum;
var title;

$(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

function teacherRoomInit(ssecId,aappId,uuserName,rroomNum,ttitle){
    secId =ssecId;
    appId = aappId;
    userName = uuserName;
    roomNum = rroomNum;
    title = ttitle;
    //视频、信令连接
    var userSignal = new TeacherDiscussionSignal(appId,userName,secId,"teacher");
    var teacherAgora = new TeacherAgora(appId,roomNum,secId);
    var recording = new Recording('a#downloadLink');

    teacherAgora.join();
    teacherAgora.createStream();
    var channel;
    setTimeout( function(){
        channel = userSignal.joinChatChannel(roomNum,"discussion");
    }, 5 * 1000 );

    function closeClass(){
        $.ajax({
            type: "post",
            url: "/updateInClassBySecId",
            data: {secId:teacherAgora.secId,inClass : false},
            dataType: "json",
            async:true,
            success: function(data){
                if(data.flag==="0"){
                    console.log("下课");
                }else{
                    console.log(data.content);
                }
            }
        });
    }

// //监听窗口关闭事件
    window.onbeforeunload = function(){
        userSignal.leaveChatChannel(channel);
        userAgora.close();
        closeClass();
    };

    $(window).unload(function(){
        closeClass();
    });

    history.pushState(null, null, document.URL);
    window.addEventListener("popstate",function(e) {
        history.pushState(null, null, document.URL);
    }, false);


    $(function () {
        var $body = $("body");
        var $msgShow =  $(".msgShow");
        var $noticeShow = $(".noticeShow");
        $msgShow[0].scrollTop = $msgShow[0].scrollHeight;//让滚动条自动滚到底部
        $noticeShow[0].scrollTop = $noticeShow[0].scrollHeight;

        //事件委派 监听是否有输入，设置按钮是否可以点击
        $body.delegate(".noticeTest","propertychange input",function () {
            var $send = $(".noticeSend");
            if($(this).val().length > 0){
                $send.removeClass("layui-btn-disabled").addClass('layui-btn-normal');
                $send.attr("disabled",false);
            }else {
                $send.addClass("layui-btn-disabled").removeClass('layui-btn-normal');
                $send.attr("disabled",true);
            }
        });

        //事件委派 监听是否有输入，设置按钮是否可以点击   监听是否按下回车
        $body.delegate(".msgText","propertychange input keydown.myPlugin",function () {
            var $send = $(".msgSend");
            if($(this).val().length > 0){
                $send.removeClass("layui-btn-disabled").addClass('layui-btn-normal');
                $send.attr("disabled",false);
            }else {
                $send.addClass("layui-btn-disabled").removeClass('layui-btn-normal');
                $send.attr("disabled",true);
            }
            if(event.which === 13){
                if(!event.shiftKey){//如果没有按下shift
                    // 避免回车键换行
                    event.preventDefault();
                    sendMsg();//发送消息
                }
            }
        });

        //点击发送消息按钮
        $("#sendMessage").on("click",function () {
            sendMsg();//发送消息
        });

        //发送消息的方法
        function sendMsg(){
            var text = $.trim($("#desc").val());//去掉前后空格
            if(text !== ""){
                userSignal.sendChatMessage(channel,MessageUtil.toJson(MessageUtil.getSendChat(),text));
                $("#desc").val("");
                $(".msgSend").addClass("layui-btn-disabled").removeClass('layui-btn-normal').attr("disabled",true);
            }
        }

        //显示学生列表
        $(".showStudents").on("mouseenter",function () {
            $(".studentList2").slideDown();
        });

        //收起学生列表
        $(".studentList2").on("mouseleave",function () {
            $(".studentList2").slideUp();
        });


        $(".students").on("click",function () {
            $(".myClassDiv").hide();
            $(".studentsDiv").show();
        });

        $(".myClass").on("click",function () {
            $(".myClassDiv").show();
            $(".studentsDiv").hide();
        });

        //点击上课按钮
        var begin = false;
        $(".beginClass").on("click",function () {
            if(begin === false){
                begin = true;
                $(this).removeClass("layui-btn-normal");
                $(this).addClass("layui-btn-danger");
                $(this).html("<img class=\"bell\" src=\"img/bell.png\" alt=\"\">下课");
                teacherAgora.publish();
            }
            else{
                begin = false;
                $(this).removeClass("layui-btn-danger");
                $(this).addClass("layui-btn-normal");
                $(this).html("<img class=\"bell\" src=\"img/bell.png\" alt=\"\">上课");
                teacherAgora.unpublish();
            }
        });
        var screenStatus = 'small';//记录当前屏幕的状态,small小屏，big大屏


        //点击’显示所有学生屏幕按钮‘
        var show = $(".showAllScreens");
        show.on("click",function () {
            if(screenStatus === 'big'){
                var nowScreen = $("#" + $(".title").html());
                nowScreen.css({
                    "margin-left": "26px",
                    "margin-top": "14px"
                });
                nowScreen.children(".idInScreen").show();
                nowScreen.children(".smallScreen").css({
                    "height":"118px",
                    "width":"210px"
                });

                $(".studentScreen").show();
                show.removeClass("layui-btn-normal");
                show.addClass("layui-btn-disabled");
                $(".title").html("学生屏幕");
                screenStatus = 'small';
            } else{
                // return;
            }
        });

        $body.delegate(".studentScreen","click",function () {
            screenStatus = 'big';
            show.removeClass("layui-btn-disabled");
            show.addClass("layui-btn-normal");
            $(".studentScreen").hide();
            $(".title").html($(this).attr('id'));
            $(this).children(".idInScreen").hide();
            $(this).css("margin",0);
            $(this).children(".smallScreen").css({
                "height":"412px",
                "width":"732px",
            });
            $('#fullScreenId').attr("onclick","showFull('"+$(this).children(".smallScreen").attr("id")+"')");
            $(this).show();
        });

        //点击全屏按钮，小屏时无效
        $(".fullScreen").on("click",function () {
            if(screenStatus === 'small'){
                return;
            } else {
                showFull('smallStudentScreen');
            }
        });

        //点击申请学生屏幕按钮
        $body.delegate(".applyScreenBtn","click",function () {
            var id=$(this).attr("id");
            userSignal.sendChatMessage(channel,MessageUtil.toJson(MessageUtil.getApplyScreen(),id));
        });


        //点击取消学生屏幕按钮
        $body.delegate(".stopShareScreen","click",function () {
            var id=$(this).attr("id");
            userSignal.sendChatMessage(channel,MessageUtil.toJson(MessageUtil.getStopStudentShareScreen(),id));
            $('#s'+id).remove();
        });

        //录制按钮们
        var status = "ready";
        $("#record").on("click",function () {
            if(teacherAgora.getLocalStream()!=null){
                if (status === "recording" || status === "pause"){
                    return;
                }
                if(status === "ready"){
                    status = "recording";
                    $('#record').attr("src","/img/record.png");
                    $('#pause').attr("src","/img/pause-red.png");
                    $('#stop').attr("src","/img/stop-red.png");
                    recording.onBtnRecordClicked(teacherAgora.getLocalStream());
                    $('#download').attr("src","/img/download-black.png");
                    $('downloadLink').attr('download','');
                }
            }
        });

        $('#pause').on("click",function () {
            if(teacherAgora.getLocalStream()!=null){
                if(status === "ready"){
                    $('#record').attr("src","/img/record.png");
                    $('#pause').attr("src","/img/pause-red.png");
                    $('#stop').attr("src","/img/stop-red.png");
                    return;
                }
                if(status === "recording"){
                    status = "pause";
                    $('#record').attr("src","/img/record.png");
                    $('#pause').attr("src","/img/start-red.png");
                    $('#stop').attr("src","/img/stop-red.png");
                    recording.onPauseClicked();
                    return;
                }
                if(status === "pause"){
                    status = "recording";
                    $('#record').attr("src","/img/record.png");
                    $('#pause').attr("src","/img/pause-red.png");
                    $('#stop').attr("src","/img/stop-red.png");
                    recording.onResumeClicked();
                }
            }
        });

        $('#stop').on("click",function () {
            if(teacherAgora.getLocalStream()!=null){
                if(status === "ready"){
                    return;
                }
                if(status === "pause" || status === "recording"){
                    status = "ready";
                    $('#record').attr("src","/img/record-red.png");
                    $('#pause').attr("src","/img/pause.png");
                    $('#stop').attr("src","/img/stop.png");
                    recording.onBtnStopClicked();
                    $('#download').attr('src','/img/download-red.png');
                }
            }
        });

    });
    $(document).ready(function(){
        layui.use(['upload','layer','element'],function () {
            var upload = layui.upload,
                element = layui.element,
                layer = layui.layer;

            $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

            var uploadInst = upload.render({
                elem:'#upload'
                ,accept:"file"
                ,url:'/uploadFile?secId='+secId//上传接口
                ,size: 100*1024 //最大允许上传的文件大小,100MB
                ,done:function (res,index,upload) {//服务端响应信息、当前文件的索引、重新上传的方法
                    //上传完毕回调。在上传接口请求完毕后触发，但文件不一定是上传成功的，只是接口的响应状态正常（200）。
                    //假设code=0代表上传成功
                    // do something （比如将res返回的图片链接保存到表单的隐藏域）
                    layer.alert("上传成功！");
                    refreshFileList()
                }
                ,error:function (index,upload) {//当前文件的索引、重新上传的方法
                    //请求异常回调
                    // layer.alert("上传失败！");
                }
            });
        });
    });
}


function deleteFile(secId, fileName) {
    $.ajax({
        type: "post",
        async: false,
        url: "/deleteFile",
        data:{
            secId:secId,
            fileName:fileName
        },
        datatype: "json",
        success: function (message) {
            if(message.flag==='0'){
                alert("删除成功");
                refreshFileList();
            }else{
                alert("删除失败！");
            }
        }
    });
}

//文件刷新
function refreshFileList() {
    $.ajax({
        type: "post",
        async: false,
        url: "/getFileList",
        data: {
            "secId": secId
        },
        datatype: "json",
        success: function (fileList) {
            $(".download").empty();
            for (var i = 0; i<fileList.length; i++){
                var downloadHref = "/downloadFile?secId="+secId+"&fileName="+fileList[i];
                var deleteHref = "/deleteFile?secId="+secId+"&fileName="+fileList[i];
                var fileHtml = "<div class=\"downloadItem\" id=\"fileList\" >\n" +
                    "                        <div class=\"layui-text layui-inline fileName\" >\n" +
                    fileList[i] +
                    "                        </div>\n" +
                    "                        <div class=\"layui-inline\">\n" +
                    "                            <a  href='"+downloadHref+"'>\n" +
                    "                                <button class=\"layui-btn layui-btn-radius layui-inline\">下载</button>\n" +
                    "                            </a>\n" +
                    "                        </div>\n" +
                    "                        <div class=\"layui-inline\" style=\"margin-left: 5px\">\n" +
                    "                           <button  onclick=\"deleteFile(\'"+secId+"\',\'"+fileList[i]+"\')\"  class=\"layui-btn layui-btn-radius layui-btn-danger layui-inline delete\">删除</button>\n" +
                    "                        </div>\n"+
                    "                    </div>";
                $(".download").append(fileHtml);
            }
        }
    });
}

//公告
function changeAnnouncement(content, time) {
    // $("#announcementBoard").text($notice);
    $(".noticeShow").empty();
    $(".noticeShow").append("<div class=\"noticeItem\">\n" +
        "                        <p class=\"layui-text\">\n" +
        content +
        "                        </p >\n" +
        "                        <p class=\"noticeOperation\">\n" +
        "                            <span class=\"layui-text noticeTime\">"+ time +"</span>\n" +
        "                        </p >\n" +
        "                    </div>");
}

function getAnnouncement(){
    $.ajax({
        type: "post",
        async: false,
        url: "/getAnnouncementBySecId",
        data: {
            "secId": secId
        },
        datatype: "json",
        success: function (data) {
            if (typeof(data.content) === "undefined") {
                changeAnnouncement("老师没有发布任何公告",formartDate());
            }else{
                changeAnnouncement(data.content, data.publishTime);
            }
        }
    });
}

//上传公告
$(".noticeSend").on("click",publishAnnouncement);
function publishAnnouncement(){
    var $notice = $.trim($(".noticeTest").val());
    if($notice.length > 0) {
        var  publishTime = formartDate();
        $.ajax({
            type: "post",
            async: false,
            url: "/writeAnnouncementBySecId",
            data: {
                "secId": secId,
                "title": title,
                "publishTime": publishTime,
                "content": $notice
            },
            datatype: "json",
            success: function (data) {
                var flag = data.flag;
                var content = data.content;
                // alert(content);
                layer.alert(content,{
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                });
                if (flag === "0") {
                    changeAnnouncement($notice,publishTime);
                }
            }
        });
        $(".noticeTest").val('');
        $('.noticeSend').removeClass('layui-btn-normal').addClass('layui-btn-disabled');
    }
}

//返回一个格式化的时间
function formartDate() {
    return new Date().format("yyyy-MM-dd hh:mm:ss");
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length===1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};

//启用讨论区内容输入
function enableInput() {
    $('#desc').removeAttr('disabled').attr('placeholder','请输入内容');
}

//启用学生列表中的按钮
function enableButtons() {
    $('.studentItem').find(".layui-btn").removeClass('layui-btn-disabled').removeAttr('disabled');
}