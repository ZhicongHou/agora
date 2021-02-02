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
            url: "/admin/getProhibitSction",
            datatype: "json",
            success: function (data) {
                table.render({
                    elem: '#table'
                    ,data: data
                    ,cols: [[
                        {field:'title', title: '课程名称', templet:'#sectionInfo'}
                        ,{field:'teaName', title: '任课老师'}
                        ,{field:'curAmount',sort:true, title: '上课人数'}
                        ,{title: '允许上课', toolbar: '#btn'}
                    ]]
                    ,page: true
                });
            }
        });
    }

    //监听行工具事件：允许上课
    table.on('tool(table)', function(obj){
        var data = obj.data;
        layer.msg('允许上课');
        if(obj.event === 'admit'){
            layer.confirm('是否允许上课?',{
                title:false,
                btn:['确定','取消'],//按钮
                yes:function () {
                    $.ajax({
                        type: "post",
                        url: "/admin/updateProhibitedBySecId",
                        data: {secId:data.secId, prohibited : false},
                        dataType: "json",
                        async:true,
                        success: function(data2){
                            if(data2.flag==="0"){
                                layer.msg('操作成功！');
                                tableRender();
                                $.ajax({
                                    type: "post",
                                    url: "/addMessage",
                                    data: {receiver:"one",receiverId:data.teaName,content : "直播被启用！您现在可正常直播了！"},
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