function livingScetionInit(appId){
    var adminsignal;
    adminsignal = new AdminSignal(appId,"admin");
    layui.use(['element','table'], function(){
        var element = layui.element
            ,layer = layui.layer
            ,table = layui.table;

        //初始化
        tableRender();
        //渲染表格
        function tableRender(){
            $.ajax({
                type: "get",
                async: false,
                url: "/admin/getInClassSction",
                datatype: "json",
                success: function (data) {
                    table.render({
                        elem: '#table'
                        ,data: data
                        ,cols: [[
                            {field:'secId', hide:true}
                            ,{field:'title', title: '课程名称', templet:'#sectionRoom'}
                            ,{field:'teaName',  title: '任课老师'}
                            ,{field:'curAmount',sort:true, title: '上课人数'}
                            ,{title: '禁止上课（切断视频流）', toolbar: '#btn'}
                        ]]
                        ,page: true
                    });
                }
            });
        }

        //监听行工具事件：禁播
        table.on('tool(table)', function(obj){
            var data = obj.data;
            if(obj.event === 'forbid'){
                layer.confirm('是否确定禁播?（正在直播的课程会被强制切断视频流）',{
                    title:false,
                    btn:['确定','取消'],//按钮
                    yes:function () {
                        $.ajax({
                            type: "post",
                            url: "/admin/updateProhibitedBySecId",
                            data: {secId:data.secId, prohibited : true},
                            dataType: "json",
                            async:true,
                            success: function(data2){
                                if(data2.flag==="0"){
                                    layer.msg('禁播成功');
                                    tableRender();
                                    adminsignal.sendPrivateMessage(data.teaName, MessageUtil.toJson(MessageUtil.getStopStream(),"stop"));
                                    $.ajax({
                                        type: "post",
                                        url: "/addMessage",
                                        data: {receiver:"one",receiverId:data.teaName, content : "您已被管理员禁播！！详情请咨询管理员。"},
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
                        layer.close(layer.index);
                    }
                })
            }
        });

    });
}

//启用禁播按钮
function enableButton() {
    $('.layui-btn').removeAttr('disabled').removeClass('layui-btn-disabled').addClass('layui-btn-danger');
}