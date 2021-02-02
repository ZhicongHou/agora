


var btnStatus;
var section;
var user;
function init(ssection, bbtnStatus, uuser) {
    btnStatus = bbtnStatus;
    section = ssection;
    user = uuser;
    setCourseName(section.title);
    setTeacherName(section.teaName);
    setBeginTime(section.beginTime);
    setPrice(section.price);
    setIntroduction(section.introduction);
    setStudent(section.upperLimit,section.curAmount);
    setHour(section.frequency,section.attended);

    setBuyBtn(btnStatus);
    var classTime = section.classTime.split(",");
    for(var i = 0; i<classTime.length; i++){
        var units = classTime[i].split(" ");
        addClassTime(units[0],units[1],units[2]);
    }
}


function setCourseName(courseName) {
    $(".courseName").html(courseName);
}

//设置老师名字
function setTeacherName(teacherName) {
    $(".teacherName").html(teacherName);
}

//设置开课时间
function setBeginTime(beginTime) {
    $(".beginTime").html(beginTime);
}

//设置价格
function setPrice(price) {
    $(".price").html(price);
}

//添加上课时段
function addClassTime(whichDay,begin,end) {
    $(".classTime").append("<tr>\n" +
        "                    <td>"+whichDay+"</td>\n" +
        "                    <td>"+begin+"</td>\n" +
        "                    <td>"+end+"</td>\n" +
        "                </tr>");
}

//设置学生容纳量和已选人数以及对应的进度条
function setStudent(capacity,having) {//学生容纳量，已选人数

    $(document).ready(function(){
        layui.use('element', function(){
            var element = layui.element;
            $(".capacity").html(capacity);
            $(".having").html(having);
            var percent = (having/capacity)*100+"%";
            element.progress('student', percent);
        });
    });
}

//设置课程简介
function setIntroduction(introduction) {
    $(".introduction").html(introduction);
}

//设置总课时和已完成课时以及对应的进度条
function setHour(total,completed) {
    layui.use('element', function(){
        var element = layui.element;
        $(".totalHours").html(total);
        $(".completed").html(completed);
        var percent = (completed/total)*100+"%";
        element.progress('hour', percent);
    });
}


function setBuyBtn(btnStatus){
    var btn = $(".buy");
    if(btnStatus === 'canBuy'){
        //默认，不需要操作
    }else if(btnStatus === 'student'){
        btn.removeClass("layui-btn-danger");
        btn.addClass("layui-btn-normal");
        btn.html("进入课程(学生身份)");
    }else if(btnStatus === 'teacher'){
        btn.removeClass("layui-btn-danger");
        btn.addClass("layui-btn-normal");
        btn.html("进入课程(老师身份)");
    }else if(btnStatus === 'bought'){
        btn.attr("disabled","true");
        btn.removeClass("layui-btn-danger");
        btn.addClass("layui-btn-disabled");
        btn.html("该课程已结束");
    }else if(btnStatus === 'finished'){   //finished
        btn.attr("disabled","true");
        btn.removeClass("layui-btn-danger");
        btn.addClass("layui-btn-disabled");
        btn.html("该课程已结束");
    }else if(btnStatus === 'notbuy'){   //finished
        btn.attr("disabled","true");
        btn.removeClass("layui-btn-danger");
        btn.addClass("layui-btn-disabled");
        btn.html("该课程已开课");
    }
}


$(document).ready(function(){
    //点击购买按钮
    $(".buy").on("click",function () {
        // var btnStatus = 'alreadyBought';//需要得到状态，最好把得到的状态写成全局变量
        if(user==null) {
            alert("请先登录！");
            return;
        }
        if(btnStatus === 'canBuy') {
            layer.open({
                type: 2,
                title: '请选择支付方式',
                anim: 1,
                resize: false,
                scrollbar: false,
                closeBtn: 1,
                shadeClose: true, //点击遮罩关闭层
                area: ['350px', '400px'],//大小暂时不知，你可以调
                // content: '/createCode?secId=' + section.secId,//弹框显示的url
                content: "/buyWin?secId="+section.secId
            });
        }else if(btnStatus === 'student' ){
            window.location.href = "/studentRoom?secId="+section.secId;
        }else if(btnStatus === 'teacher' ){
            window.location.href = "/teacherRoom?secId="+section.secId;
        }
    });

});





