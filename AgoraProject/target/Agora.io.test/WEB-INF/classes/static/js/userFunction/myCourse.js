var tasksTotal;
var sectionsTotal;

function init(tAccount,sAccount){
    tasksTotal = tAccount;
    sectionsTotal = sAccount;
}

layui.use('laypage',function () {
    var laypage = layui.laypage;
    //分页器的绑定

    $(".layui-nav-bar").hide();//将导航栏下面的滑块隐藏
    laypage.render({
        elem: 'purchasedPage'//id
        , limit: 8 //一页最多的个数
        , count: tasksTotal//总数，从后台获取
        , jump: function (page, first) {//这个方法在点击分页按钮的时候触发
            $('#purchasedClasses').load('/getOtherTaskOfPage?pageNum='+page.curr);
        }
    });

    laypage.render({
        elem: 'myClassPage'
        , limit: 8
        , count: sectionsTotal//总数，从后台获取
        , jump: function (page, first) {//这个方法在点击分页按钮的时候触发
            $('#myClass').load('/getOtherSectionOfPage?pageNum='+page.curr);
        }
    });
});