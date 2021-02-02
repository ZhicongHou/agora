layui.use(['element','table'], function(){
    var element = layui.element
        ,layer = layui.layer
        ,table = layui.table;

    //初始化
    tableRender();
    //设置左边导航栏
    function setLeftNav(dataUrl){
        if(dataUrl === "liveAuth"){
            $(".msg").hide();
            $(".auth").hide();
            $(".user").hide();
            $(".live").fadeIn();
        }else if(dataUrl === "userAndCourse"){
            $(".live").hide();
            $(".msg").hide();
            $(".auth").hide();
            $(".user").fadeIn();
        }else if(dataUrl === "authAndGrant"){
            $(".live").hide();
            $(".user").hide();
            $(".msg").hide();
            $(".auth").fadeIn();
        }else{
            $(".live").hide();
            $(".user").hide();
            $(".auth").hide();
            $(".msg").fadeIn();
        }
    }

    //渲染表格
    function tableRender(){
        $.ajax({
            type: "get",
            async: false,
            url: "/admin/getUnAuthorSectionOfPage",
            datatype: "json",
            success: function (data) {
                table.render({
                    elem: '#table'
                    ,data: data
                    ,cols: [[
                        {field:'secId', hide:true}
                        ,{field:'title', title: '课程名称', templet:'#sectionInfo'}
                        ,{field:'teaName', title: '任课老师', templet:'#teaInfo'}
                        ,{field:'price', title: '价格'}
                        ,{title:'是否授权', toolbar:'#author', align:'center'}
                    ]]
                    ,page: true
                });
            }
        });
    }

    //监听行工具事件：授权和拒绝
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'author'){

            layer.prompt({
                title: '请输入修改工资金额的百分比(0-100)'
                , formType: 0
            }, function (percent, index) {// 输入的内容（百分比），弹窗的索引
                //判断输入是否合法
                if (percent < 0 || percent > 100 || isNaN(percent)) {
                    layer.alert("请输入0-100的数字！");
                } else {
                    $.ajax({
                        type: "post",
                        url: "/admin/authorizeSection",
                        data: {secId:data.secId,proportion:percent},
                        dataType: "json",
                        async:true,
                        success: function(data2){
                            if(data2.flag==="0"){
                                layer.msg('授权成功');
                                tableRender();
                                $.ajax({
                                    type: "post",
                                    url: "/addMessage",
                                    data: {receiver : "one",receiverId : data.teaName,content : "您的课程已被授权！"},
                                    dataType: "json",
                                    async:true,
                                    success: function(data3){
                                        if(data3.flag==="0"){
                                        }else{
                                            layer.alert(data3.content);
                                        }
                                    }
                                });
                            }else{
                                layer.alert(data2.content);
                            }
                        }
                    });
                    layer.close(index);//关闭弹窗
                }
            });
        }
        else if(obj.event === 'refuse'){
            $.ajax({
                type: "post",
                url: "/deleteSection",
                data: {secId:data.secId},
                dataType: "json",
                async:true,
                success: function(data2){
                    if(data2.flag==="0"){
                        layer.msg('拒绝成功');
                        tableRender();
                        $.ajax({
                            type: "post",
                            url: "/addMessage",
                            data: {receiver : "one",receiverId : data.teaName,content : "您的课程不符合要求，已被拒绝认证！！详情请咨询管理员。"},
                            dataType: "json",
                            async:true,
                            success: function(data3){
                                if(data3.flag==="0"){
                                }else{
                                    layer.alert(data3.content);
                                }
                            }
                        });
                    }else{
                        layer.alert(data2.content);
                    }
                }
            });
        }
    });
});