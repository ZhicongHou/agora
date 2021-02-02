

// var totalElements;
// function init(ttotalElements){
//     totalElements = ttotalElements;
// }

layui.use(['laypage','jquery'],function () {
    var laypage = layui.laypage;
    var $ = layui.jquery;

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏

    // var allMsg = {};//得到所有信息，后台获取

    var count;
    var limit;

    $.ajax({
        type: "get",
        async: false,
        url: "/getRecMessageCount",
        datatype: "json",
        success: function (data) {
            count = data.count;
            limit = data.limit;
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

            // var firstm = (page.curr-1)*10;//第一条信息
            // var lastm = (page.curr)*10-1;//最后一条信息
            // $.each(allMsg,function (i,message) {//i为索引，course为对象
            //     if(i < firstm || i > lastm){//不在要显示的范围
            //         // return;
            //     }
            //     else {
            //         //获取对象的元素，调用添加历史信息的方法，可以直接传一个对象，不过需要改写添加历史信息的方法
            //         //你需要在这里获取对象的元素
            //         var sender = '发件人';
            //         var content = '内容';
            //
            //         addHistoryMsg(sender,content);
            //     }
            // });
        }
    });

    // addHistoryMsg('邱泽扬','我是煞笔');
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