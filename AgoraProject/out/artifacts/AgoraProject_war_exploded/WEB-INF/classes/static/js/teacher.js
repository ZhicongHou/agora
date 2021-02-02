$(function () {
    var $body = $("body");

    var $msgShow =  $(".msgShow");
    var $noticeShow = $(".noticeShow");
    $msgShow[0].scrollTop = $msgShow[0].scrollHeight;//让滚动条自动滚到底部
    $noticeShow[0].scrollTop = $noticeShow[0].scrollHeight;

    // var tipOfStudentName = null;

    //显示学生列表
    $(".showStudents").on("mouseenter",function () {
        $(".studentList2").slideDown();
    });

    //收起学生列表
    $(".studentList2").on("mouseleave",function () {
        $(".studentList2").slideUp();
    });

    //事件委派 监听是否有输入，设置按钮是否可以点击
    $body.delegate(".msgText","propertychange input",function () {
        console.log($(this).val());
        var $send = $(".msgSend");
        if($(this).val().length > 0){
            $send.removeClass("layui-btn-disabled");
            $send.attr("disabled",false);
        }else {
            $send.addClass("layui-btn-disabled");
            $send.attr("disabled",true);
        }
    });

    //讨论区，点击发送按钮
    $(".msgSend").on("click",function () {
        //name值要取
        var $name = 'name';

        //得到用户输入的文本，并且去掉文本前后的空格
        var $msg = $.trim($(".msgText").val());

        //判断输入内容是否为空，空则不发送
        if($msg.length > 0){
            // $msgShow.append("<ul class=\'layui-text\'>"+ $name+"："+$msg +"</ul>");
            $msgShow.append("<div class=\"layui-text msgItem \">"+$name+" : "+$msg+"</div>");
            $msgShow[0].scrollTop = $msgShow[0].scrollHeight;//让滚动条自动滚到底部
            $(".msgText").val('');
        }
        //与后台交互部分



    });

    //事件委派 监听是否有输入，设置按钮是否可以点击
    $body.delegate(".noticeTest","propertychange input",function () {
        // console.log($(this).val());
        var $send = $(".noticeSend");
        if($(this).val().length > 0){
            $send.removeClass("layui-btn-disabled");
            $send.attr("disabled",false);
        }else {
            $send.addClass("layui-btn-disabled");
            $send.attr("disabled",true);
        }
    });

    //公告栏 点击发布按钮
    $(".noticeSend").on("click",function () {
        var $notice = $.trim($(".noticeTest").val());
        if($notice.length > 0){
            $noticeShow.append("<div class=\"noticeItem\">\n" +
                "                        <p class=\"layui-text\">\n" +
                                            $notice +
                "                        </p>\n" +
                "                        <p class=\"noticeOperation\">\n" +
                "                            <span class=\"layui-text noticeTime\">"+ formartDate() +"</span>\n" +
                "                            <a href=\"#\" class=\"layui-icon layui-icon-delete noticeDelete\"></a>\n" +
                "                        </p>\n" +
                "                    </div>");
            $noticeShow[0].scrollTop = $noticeShow[0].scrollHeight;
            $(".noticeTest").val('');
        }
    });

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

    //返回一个格式化的时间
    function formartDate() {
        return new Date().format("yyyy-MM-dd hh:mm:ss");
    }

    //监听删除按钮，删除公告
    $body.delegate(".noticeDelete","click",function () {
        $(this).parents(".noticeItem").remove();
        //与后台交互部分


    });

    $(".students").on("click",function () {
        $(".myClassDiv").hide();
        $(".studentsDiv").show();
    });

    $(".myClass").on("click",function () {
        $(".myClassDiv").show();
        $(".studentsDiv").hide();
    });

    var begin = false;//是否正在上课，true时按钮显示下课，false时按钮显示上课
    // var open = false;
    $(".beginClass").on("click",function () {//点击上下课按钮
        if(begin === false){
            begin = true;
            $(this).removeClass("layui-btn-normal");
            $(this).addClass("layui-btn-danger");
            $(this).html("<img class=\"bell\" src=\"img/bell.png\" alt=\"\">下课");
            //与后台交互部分



        }
        else{
            begin = false;
            $(this).removeClass("layui-btn-danger");
            $(this).addClass("layui-btn-normal");
            $(this).html("<img class=\"bell\" src=\"img/bell.png\" alt=\"\">上课");
            //与后台交互部分



        }
    });

    // $(".openScreen").on("click",function () {
    //     if(open === false){
    //         open = true;
    //         $(this).removeClass("layui-btn-normal");
    //         $(this).addClass("layui-btn-danger");
    //         $(this).html("<img class=\"screenIcon\" src=\"img/screen.png\" alt=\"\">关屏");
    //     }
    //     else{
    //         open = false;
    //         $(this).removeClass("layui-btn-danger");
    //         $(this).addClass("layui-btn-normal");
    //         $(this).html("<img class=\"screenIcon\" src=\"img/screen.png\" alt=\"\">开屏");
    //     }
    // });


    //调试代码
    // $(".test2").on("click",function () {
    //     //取得毫秒模拟学生id
    //     var studentId = new Date().getMilliseconds();
    //     // alert(studentId);
    //     // addsmallScreen(studentId);
    //     addStudentTolist(studentId);
    // });


    //添加小屏
    function addsmallScreen(studentId) {
        // var newScreen = "<div class=\"smallScreen layui-inline\"  id=\""+studentId+"\"></div>";
        var newScreen = "<div class=\"studentScreen layui-inline\" id=\""+studentId+"\">\n" +
            "                <div class=\"layui-text idInScreen\">"+studentId+"</div>\n" +
            "                <div class=\"smallScreen\"></div>\n" +
            "            </div>";
        // alert(newScreen);
        $(".screen").append(newScreen);
    }

    var screenStatus = 'small';//记录当前屏幕的状态,small小屏，big大屏

    //点击’显示所有学生屏幕按钮‘
    var show = $(".showAllScreens");
    show.on("click",function () {
       if(screenStatus === 'big'){
           var nowScreen = $("#" + $(".title").html());
           nowScreen.css({
               "margin-left": "26px",
               "margin-top": "14px",
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

    //点击小屏
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
        $(this).show();
    });

    //点击全屏按钮，小屏时无效
    $(".fullScreen").on("click",function () {
       if(screenStatus === 'big'){

       }
    });

    //将学生添加到学生列表
    function addStudentTolist(studentName) {//用户名

        $(".studentList2").append("<div class=\"studentItem\" id=\""+"l"+studentName+"\">\n" +
            "            <span><img class=\"student\" src=\"img/student.png\" alt=\"\"></span>\n" +
            "            <div class=\"studentName layui-inline\">"+studentName+"</div>\n" +
            "            <span><img class=\"hand\" src=\"img/hand2.png\" alt=\"\"></span>\n" +
            "            <span><a href=\"javascript:;\"><button class=\"layui-btn layui-btn-xs layui-btn-normal\">\n" +
            "            <img class=\"applyScreen\" src=\"img/screen.png\" alt=\"\"></button></a></span>\n" +
            "        </div>");

        //改变当前房间人数
        var num = parseInt($(".studentBadge").html())+1;
        if(num>99){
            $(".studentBadge").html("99+");
        }else {
            $(".studentBadge").html(num);
        }
    }

    //点击学生列表中的申请屏幕按钮
    $body.delegate(".applyScreen","click",function () {
        if(screenStatus === "big"){
            return;
        }
        //此处应有是否申请成功的判断

        //得到学生用户名
        var studentName = $(this).parents(".studentItem").children(".studentName").html();

        //添加小屏
        addsmallScreen(studentName);
    });

    // setIsAttend("侯智聪",true);
    //根据用户名修改学生列表中的出席状态
    function setIsAttend(studentName,isAttend) {//用户名，是否出席boolean
        if (isAttend){//出席
            $("#l"+studentName+" .student").attr("src","img/student-blue.png");
        } else {
            $("#l"+studentName+" .student").attr("src","img/student.png");
        }
    }

    // setIsHand("侯智聪",true);
    //根据用户名修改学生列表中的举手状态
    function setIsHand(studentName,isHand){//用户名，是否举手boolean
        if (isHand){//出席
            $("#l"+studentName+" .hand").attr("src","img/hand-green.png");
        } else {
            $("#l"+studentName+" .hand").attr("src","img/hand2.png");
        }
    }

    // //显示tip（学生用户名完全显示）
    // $body.delegate(".studentName","mouseover",function () {
    //     tipOfStudentName = layer.tips($(this).html(), $(this),{
    //         // tips: [1, '#3595CC'],
    //         time: 1000
    //     });
    // });

    //关闭tip
    // $body.delegate(".studentName","mouseleave",function () {
    //     if (tipOfStudentName!==null){
    //         layer.close(tipOfStudentName);
    //         // tipOfStudentName = null;
    //         setTimeout(function () { tipOfStudentName = null;}, 1000);
    //     }
    // });
});
