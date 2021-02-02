


var user;
function init(uuser){
    user = uuser;
}

layui.use(['layer','jquery'],function () {
    $(document).ready(function(){
        var $ = layui.jquery;
        var layer = parent.layer === undefined ? layui.layer:top.layer;

        $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

        $('.layui-nav-item.log').on('click',function () {
            layer.open({
                type: 2,
                title: '用户登录',
                anim: 1,
                resize: false,
                scrollbar: false,
                shadeClose: true, //点击遮罩关闭层
                area: ['350px', '390px'],
                content: '/login',//弹框显示的url
            });
        });

        $('.layui-nav-item.reg').on('click',function () {
            window.location.href="/register";
        });

        $('.layui-nav-item.userName').on('click',function () {
            window.location.href="/index";
        });

        $('.layui-nav-item.logout').on('click',function () {
            $.ajax({
                type: "post",
                async: false,
                url: "/logout",
                data: {},
                datatype: "json",
                success: function (data) {
                    var flag = data.flag;
                    var content = data.content;
                    if(flag==="0"){
                        layer.alert(content,{
                            skin: 'layui-layer-lan' //样式类名
                            ,closeBtn: 0
                        },function () {
                            window.location.href="/index";
                        });
                    }else {
                        layer.alert(content,{
                            skin: 'layui-layer-lan' //样式类名
                            ,closeBtn: 0
                        });
                    }
                }
            });
        });

        var message = $(".message");
        var messageShow = $(".messageShow");
        var isinmessage = false;
        var isinshow = false;

        message.on("mouseenter",function () {
            isinmessage = true;
        });

        message.on("mouseleave",function () {
            isinmessage = false;
        });

        messageShow.on("mouseenter",function () {
            isinshow = true;
        });

        messageShow.on("mouseleave",function () {
            isinshow = false;
        });

        setInterval(function () {
            if(!isinshow&&!isinmessage){
                // messageShow.fadeOut(300);
                messageShow.animate({ height: 'hide', opacity: 'hide' }, "fast");
            }
            else {
                messageShow.css({top:48,left: $(".message").offset().left-125});
                messageShow.animate({ height: 'show', opacity: 'show' }, "normal");
            }
        },100);


        //加载未读取消息
        $.ajax({
            type: "post",
            async: false,
            url: "/getUnreadedMessagesByRecieverId",
            data: {
                "recieveId": user.id
            },
            datatype: "json",
            success: function (messages) {
                setmsgNum(messages.length);
                for (var i = 0; i<messages.length; i++){
                    addMsg(messages[i].senderName,messages[i].content);
                }
            }
        });


        //设置未读消息个数(铃铛)
        function setmsgNum(msgNum) {//未读消息个数
            if(msgNum === 0){
                $(".allMsg").append("\n" +
                    "        <p style=\"text-align: center;margin-top: 100px\" class=\"layui-text\">暂无未读消息</p>");
            }
            else if(msgNum>99){
                $(".msgBadge").html("99+").show();
            }else {
                $(".msgBadge").html(msgNum).show();
            }
        }
        // addMsg("邱泽扬","暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读的。");
        // addMsg("邱泽扬","暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读的。");
        // addMsg("邱泽扬","暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读的。");
        // addMsg("邱泽扬","暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读的。");
        // addMsg("邱泽扬","暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读消息暂无未读的。");
        //将消息添加到消息框中
        function addMsg(sender,content) {//发件人，内容
            // alert(content.length);
            if(content.length > 59){//如果字符数大于59，截取前58，加上...
                content = content.substring(0,58)+'...';
            }
            $(".allMsg").append("<div style=\"margin-bottom: 10px \">\n" +
                "            <span class=\"sender\">"+sender+"</span>\n" +
                "            <strong> : </strong>\n" +
                "            <span class=\"content layui-text\">"+content+"</span>\n" +
                "        </div>");
        }

    });
});



