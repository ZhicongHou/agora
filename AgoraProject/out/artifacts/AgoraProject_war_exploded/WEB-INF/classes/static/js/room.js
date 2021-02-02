$(function () {
    var $msgShow =  $(".msgShow");
    $msgShow[0].scrollTop = $msgShow[0].scrollHeight;//让滚动条自动滚到底部

    //事件委派 监听是否有输入，设置按钮是否可以点击
    $("body").delegate(".msgText","propertychange input",function () {
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
        var $name = 'name';
        var $msg = $.trim($(".msgText").val());
        console.log($msg);
        if($msg.length > 0){
            $msgShow.append("<ul class=\'layui-text\'>"+ $name+"："+$msg +"</ul>");
            $msgShow[0].scrollTop = $msgShow[0].scrollHeight;
            $(".msgText").val('');
        }
    });

    var hand = false;//未举手
    $(".hand").on("click",function () {
        if(hand === false){
            hand = true;
            $(".hand").attr("src","img/hand-green.png");
        }
        else {

        }
    });
    //添加公告的方法
    function addNotice() {

    }

});
