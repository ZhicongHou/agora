var secId;
var appId;
var userName;
var roomNum;
var userAgora=null;
function studentRoomInit(ssecId,aappId,uuserName,rroomNum){
    secId = ""+ssecId;
    appId = ""+aappId;
    userName = uuserName;
    roomNum = rroomNum;
    //视频,信令连接
    userAgora = new StudentAgora(appId, userName, roomNum,secId, "teacherScreen");
    userAgora.join();
    // enableSend();
    // enableHand();
    setTimeout( function(){
        if(userName=="admin" && userAgora.teacherStream==null){
            alert("老师已下线");
            $.ajax({
                type: "post",
                url: "/updateInClassBySecId",
                data: {secId : secId,inClass : false},
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
    }, 5 * 1000 );

    var userSignal = new UserDiscussionSignal(appId, userName, "student",roomNum);
    // var channel;
    // setTimeout(function () {
    //     channel = userSignal.joinChatChannel(roomNum, "discussion");
    // }, 5 * 1000);
    var recording = new Recording('a#downloadLink');

    // //监听窗口关闭事件
    window.onbeforeunload = function () {
        if (userSignal != null) {
            userSignal.leaveChatChannel(channel);
        }
        userAgora.close();
    };

    $(function () {
        var $msgShow =  $(".msgShow");
        $msgShow[0].scrollTop = $msgShow[0].scrollHeight;//让滚动条自动滚到底部

        //事件委派 监听是否有输入，设置按钮是否可以点击   监听是否按下回车
        $("body").delegate(".msgText","propertychange input keydown.myPlugin",function () {
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
                userSignal.sendChatMessage(MessageUtil.toJson(MessageUtil.getSendChat(),text));
                $("#desc").val("");
                $(".msgSend").removeClass('layui-btn-normal').addClass("layui-btn-disabled").attr("disabled",true);
            }
        }

        // recording, pause(就绪，正在录制，暂停)

        var status = "ready";
        $("#record").on("click",function () {
            if(userAgora.teacherStream!=null){
                if (status === "recording" || status === "pause"){
                    return;
                }
                if(status === "ready"){
                    status = "recording";
                    $('#record').attr("src","/img/record.png");
                    $('#pause').attr("src","/img/pause-red.png");
                    $('#stop').attr("src","/img/stop-red.png");
                    recording.onBtnRecordClicked(userAgora.teacherStream);
                    $('#download').attr("src","/img/download-black.png");
                    $('downloadLink').attr('download','');
                }
            }
        });

        $('#pause').on("click",function () {
            if(userAgora.teacherStream!=null){
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
            if(userAgora.teacherStream!=null){
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
}

//下载文件
function refreshFileList() {
    $.ajax({
        type: "post",
        async: false,
        url: "/getFileList",
        data: {
            "secId" : secId
        },
        datatype: "json",
        success: function (fileList) {
            $(".download").empty();
            for (var i = 0; i<fileList.length; i++){
                var downloadHref = "/downloadFile?secId="+secId+"&fileName="+fileList[i];
                var fileHtml = "<div class=\"downloadItem\" id=\"fileList\" >\n" +
                    "                        <div class=\"layui-text layui-inline fileName\" >\n" +
                    fileList[i] +
                    "                        </div>\n" +
                    "                        <div class=\"layui-inline\">\n" +
                    "                            <a  href='"+downloadHref+"'>\n" +
                    "                                <button class=\"layui-btn layui-inline\">下载</button>\n" +
                    "                            </a>\n" +
                    "                        </div>\n" +
                    "                    </div>";
                $(".download").append(fileHtml);
            }
        }
    });
}

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

//获取公告
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

function createStream() {
    if(userAgora!=null){
        if (userAgora.getStream() == null) {
            userAgora.createStream();
            setTimeout(function () {
                userAgora.publish();
            }, 5 * 1000);
        }
    }
}

function stopStream() {
    if(userAgora!=null){
        userAgora.close();
    }
}

//启用讨论区内容输入
function enableInput() {
    $('#desc').removeAttr('disabled').attr('placeholder','请输入内容');
}

//启用举手按钮
function enableHand() {

    //举手功能
    var hand = false;//未举手
    $(".hand").on("click",function () {
        if(hand === false){
            hand = true;
            $(".hand").attr("src","/img/hand-green.png");
            userSignal.sendChatMessage(channel,MessageUtil.toJson(MessageUtil.getRaiseHand()));
        }
        else {
            hand=false;
            $(".hand").attr("src","/img/hand.png");
            userSignal.sendChatMessage(channel,MessageUtil.toJson(MessageUtil.getCancelRaiseHand()));
        }
    });

}