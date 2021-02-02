//查看历史记录
//目前没有消息通道
//后期可以考虑加
//所以现在只有管理员向用户发送信息，没有用户向管理员发送信息的功能
layui.use(['laypage','jquery','element'],function () {
    var laypage = layui.laypage,
        element = layui.element,
        $ = layui.jquery;

    // var allMsg = {};//得到所有信息，后台获取

    //初始化
    $('.msg').show();

    var count;
    var limit;

    $.ajax({
        type: "get",
        async: false,
        url: "/getRecMessageCount",
        datatype: "json",
        success: function (data) {
            count = data.count,
                limit = data.limit
        },
        error:function () {
            alert("获取消息数量错误，采用默认值");
            count = 10;
            limit = 50;
        }
    });

    //分页器
    laypage.render({
        elem:'historyPage'//id，分页器绑定的块
        ,limit:limit //一页最多的个数，我这里设置了10条
        ,count:count//总数，从后台获取
        ,jump:function (page,first) {//这个方法在点击分页按钮的时候触发
            //page.curr为当前的页码，可以用(page.curr-1)*10作为索引来得到当前页的第一条信息，以此类推
            //first（是否首次，一般用于初始加载的判断），在我们这里不需要用到
            $.ajax({
                type: "get",
                async: false,
                url: "/getRecMessages",
                data: {
                    "pageNum": page.curr,
                },
                datatype: "json",
                success: function (data) {
                    removeHistoryMsg();
                    for (var i=0;i<data.length;i++) {
                        addHistoryMsg(data[i].senderName,data[i].content);
                    }
                },
                error:function () {
                    alert("error");
                }
            });
        }
    });

    function addHistoryMsg(sender,content) {
        $(".allHistory").append("<blockquote class=\"layui-elem-quote layui-quote-nm\">\n" +
            "        <span class=\"sender\">"+sender+"</span>\n" +
            "        <strong> : </strong>\n" +
            "        <span class=\"content\">"+content+"</span>\n" +
            "    </blockquote>");
    }

    function removeHistoryMsg() {
        $(".allHistory").empty();
    }
});