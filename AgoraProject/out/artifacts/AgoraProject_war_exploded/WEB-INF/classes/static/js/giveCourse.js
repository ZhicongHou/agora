

var section;
function init(ssection){
    section = ssection;
}

layui.use(['form','jquery','laydate'],function () {


    var form = layui.form, laydate = layui.laydate;


    if(section!=null){
        section.startDate = section.startDate.substr(0,10);
        form.val("sectionData", {
            "courId": section.courId
            ,"title":section.title
            ,"introduction": section.introduction
            ,"startDate": section.startDate
            ,"price": section.price
            ,"upperLimit": section.upperLimit
            ,"frequency": section.frequency
        });

        var classTime = section.classTime.split(",");
        for(var i = 0; i<classTime.length; i++){
            var units = classTime[i].split(" ");
            if(i===0){
                $('#whichDay1').val(units[0]);
                $('#beginTime1').val(units[1]);
                $('#endTime1').val(units[2]);
                form.render();
            }else{
                addClassTimeTag(units[0],units[1],units[2]);
            }
        }
    }




    //表单验证
    form.verify({
        checkPrice:function (value) {
            if(value <0 ||value === null){
                return '请输入正确的价格！';
            }
        }
        ,checkCapacity:function (value) {
            if(value < 1 || value%1!==0){
                return '请输入正确的人数！'
            }
        }
        ,checkHour:function (value) {
            if(value < 1 || value%1!==0){
                return '请输入正确的小时数！'
            }
        }
    });

    //表单提交
    form.on("submit(submit)",function (data) {
        // var d = {};
        // var timeRows = $(".inputForm .classTime .whichDay");
        // alert(timeRows+"fuck");
        // $.each(timeRows, function() {
        //     alert($(this).val());
        // });
        // return false;

        var classTime = "";
        var timeRows = $(".whichDay, .beginTime, .endTime");
        var i = 0;
        while(i<timeRows.length){
            classTime += $(timeRows[i]).val()+" "; i++;
            if($(timeRows[i]).val()>=$(timeRows[i+1]).val()){
                alert("输入时间段非法：结束时间要大于开始时间！");
                return false;
            }
            classTime += $(timeRows[i]).val()+" "; i++;
            classTime += $(timeRows[i]).val(); i++;
            if(i<timeRows.length) {
                classTime += ",";
            }
        }


        $.ajax({
            type: "post",
            async: false,
            url: "/addSection",
            data: {
                "secId": section==null?null:section.secId,
                "courId": data.field.courId,
                "title": data.field.title,
                "startDate": data.field.startDate,
                "classTime": classTime,
                "price": data.field.price,
                "upperLimit": data.field.upperLimit,
                "frequency": data.field.frequency,
                "introduction": data.field.introduction
            },
            datatype: "json",
            success: function (data) {
                var flag = data.flag;
                var content = data.content;
                alert(content);
                if(flag==="0"){
                    window.location.href="/index";
                }
            }
        });
        return false;
    });


    laydate.render({
        elem: '#beginDate' //指定元素
    });

    laydate.render({
        type: 'time',
        elem:'#beginTime1'
    });

    laydate.render({
        type: 'time',
        elem:'#endTime1'
    });

    //监听删除上课时间按钮
    $('body').delegate(".delete","click",function () {
        $(this).parents(".classTime").remove();
    });


    function addClassTimeTag(whichDay="", beginTime="",endTime=""){
        //得到现在有多少个上课时间
        var numOfTime = parseInt(Math.random()*1000);
        $(".allTime").append("<div class=\" classTime layui-input-block\">\n" +
            "                <div class=\"layui-inline whichDayBlock\">\n" +
            "                    <select id=\"whichDay"+numOfTime+"\" class=\"whichDay\" name=\"whichDay\" lay-verify=\"required\">\n" +
            "                        <option value=\"\"   >星期几</option>\n" +
            "                        <option value=\"星期一\">星期一</option>\n" +
            "                        <option value=\"星期二\">星期二</option>\n" +
            "                        <option value=\"星期三\">星期三</option>\n" +
            "                        <option value=\"星期四\">星期四</option>\n" +
            "                        <option value=\"星期五\">星期五</option>\n" +
            "                        <option value=\"星期六\">星期六</option>\n" +
            "                        <option value=\"星期日\">星期日</option>\n" +
            "                    </select>\n" +
            "                </div>\n" +
            "                <div class=\"layui-inline whenBlock\">\n" +
            "                    <input type=\"text\" readonly  placeholder=\"开始时间\" class=\"layui-input beginTime\" id=\"beginTime"+numOfTime+"\" name=\"beginTime\" lay-verify=\"required\">\n" +
            "                </div>\n" +
            "                <div class=\"layui-inline whenBlock\">\n" +
            "                    <input type=\"text\" readonly  placeholder=\"结束时间\" class=\"layui-input endTime\" id=\"endTime"+numOfTime+"\" name=\"endTime\" lay-verify=\"required\">\n" +
            "                </div>\n" +
            "                <div class=\"layui-inline addTime\">\n" +
            "                    <a href=\"javascript:;\"><i class=\"layui-icon layui-icon-close delete\" style=\"font-size: 20px\"></i></a>\n" +
            "                </div>\n" +
            "            </div>");
        form.render();
        laydate.render({
            type: 'time',
            elem:"#beginTime"+numOfTime
            // elem:"#beginTime1"
        });
        laydate.render({
            type: 'time',
            elem:"#endTime"+numOfTime
        });


        $('#whichDay'+numOfTime).val(whichDay);
        $('#beginTime'+numOfTime).val(beginTime);
        $('#endTime'+numOfTime).val(endTime);
        form.render();

    }

    //添加上课时间表单
    $(".addTime").on("click",addClassTimeTag);
});


