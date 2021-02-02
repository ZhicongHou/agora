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
            url: "/admin/getUnfinishedSectionOfPage",
            datatype: "json",
            success: function (data) {
                table.render({
                    elem: '#table'
                    ,data: data
                    ,cols: [[
                        {field:'id', hide:true}
                        ,{field:'title', title: '课程名称', templet:'#sectionInfo'}
                        ,{field:'teaName', title: '任课老师', templet:'#teaInfo'}
                        ,{field:'price',sort:true, title: '价格'}
                        ,{field:'proportion', title: '工资比例'}
                        ,{title: '设置工资比例',toolbar:'#btn'}
                    ]]
                    ,page: true
                });
            }
        });
    }

    //监听行工具事件：编辑
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
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
                        url: "/admin/updateProportionBySecId",
                        data: {secId:data.secId,proportion:percent},
                        dataType: "json",
                        async:true,
                        success: function(data){
                            if(data.flag==="0"){
                                layer.alert('修改成功');
                                tableRender();
                            }else{
                                layer.alert(data.content);
                            }
                        }
                    });
                    layer.close(index);//关闭弹窗
                }
            });
        }
    });
});