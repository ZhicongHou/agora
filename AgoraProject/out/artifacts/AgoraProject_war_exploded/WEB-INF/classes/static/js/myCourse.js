layui.use('laypage',function () {

    var laypage = layui.laypage;
    var allCourse = {};//所有课程，从后台获取
    //分页器的绑定
    laypage.render({
        elem:'purchasedPage'//id
        ,limit:8 //一页最多的个数
        ,count:7//总数，从后台获取
        ,jump:function (page,first) {//这个方法在点击分页按钮的时候触发
            //page.curr为当前的页码，可以用(page.curr-1)*8作为索引来得到当前页的第一个课程，以此类推
            //first（是否首次，一般用于初始加载的判断），在我们这里不需要用到
            // console.log(page.curr);
            var firstc = (page.curr-1)*8;//第一个课程
            var lastc = (page.curr)*8-1;//最后一个课程
            $.each(allCourse,function (i,course) {//i为索引，course为对象
                if(i < firstc || i > lastc){//不在要显示的范围
                    return;
                }
                else {
                    //获取对象的元素，调用添加课程的方法，可以直接传一个对象，不过需要改写添加课程的方法
                    //这里缺了获取对象的元素
                    addPurchasedCourse();
                }
            });
        }
    });

    laypage.render({
        elem:'myClassPage'
        ,limit:8
        ,count:50//总数，从后台获取
        ,jump:function (page,first) {//这个方法在点击分页按钮的时候触发
            //page.curr为当前的页码，可以用(page.curr-1)*8作为索引来得到当前页的第一个课程，以此类推
            //first（是否首次，一般用于初始加载的判断），在我们这里不需要用到
            // console.log(page.curr);
            var firstc = (page.curr-1)*8;//第一个课程
            var lastc = (page.curr)*8-1;//最后一个课程
            $.each(allCourse,function (i,course) {//i为索引，course为对象
                if(i < firstc || i > lastc){//不在要显示的范围
                    return;
                }
                else {
                    //获取对象的元素，调用添加课程的方法，可以直接传一个对象，不过需要改写添加课程的方法
                    //这里缺了获取对象的元素
                    addMyClass();
                }
            });
        }
    });

    //这两个是调试用方法，可注释
    $(".test1").on("click",function () {
        // addPurchasedCourse("courseName","teacherName","price");
        addMyClass("courseName","teacherName","price");
    });
    $(".test2").on("click",function () {
        // deleteAllPurchased();
        deleteAllMyClass();
    });

    //删除页面上的所有已购课程
    function deleteAllPurchased() {
        $(".purchasedClasses").empty();
    }

    //删除页面上的所有我开的课程
    function deleteAllMyClass() {
        $(".myClasses").empty();
    }

    //添加已购课程的方法,需要添加多一个url的参数，替换掉默认的room.html
    function addPurchasedCourse(courseName,teacherName,price) {

        if($(".purchasedClasses > .display2").size()< 8){//不出bug的话是冗余的判断
            $(".purchasedClasses").append("<div class=\"display2 layui-inline\">\n" +
                "            <a href=\"room.html\">\n" +
                "                <div class=\"cover\" style=\"background-color: white\">封面</div>\n" +
                "            </a>\n" +
                "            <div class=\"title\">\n" +
                "                <a href=\"room.html\">\n" +
                "                    <div class=\"courseName\">"+courseName+"</div>\n" +
                "                </a><br>\n" +
                "                <p class=\"courseInfo\">\n" +
                "                    <span class=\"coursePrice\">"+teacherName+"</span>\n" +
                "                    <span class=\"teacherName\">"+price+"</span>\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>");
        }
    }

    //添加我开的课程的方法,需要添加多一个url的参数，替换掉默认的room.html
    function addMyClass(courseName,teacherName,price) {

        if($(".myClasses > .display3").size()< 8){//不出bug的话是冗余的判断
            $(".myClasses").append("<div class=\"display3 layui-inline\">\n" +
                "            <a href=\"room.html\">\n" +
                "                <div class=\"cover\" style=\"background-color: white\">封面</div>\n" +
                "            </a>\n" +
                "            <div class=\"title\">\n" +
                "                <a href=\"room.html\">\n" +
                "                    <div class=\"courseName\">"+courseName+"</div>\n" +
                "                </a><br>\n" +
                "                <p class=\"courseInfo\">\n" +
                "                    <span class=\"coursePrice\">"+teacherName+"</span>\n" +
                "                    <span class=\"teacherName\">"+price+"</span>\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>");
        }
    }

});