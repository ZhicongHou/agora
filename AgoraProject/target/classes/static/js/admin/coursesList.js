layui.use(['element','table','layer'], function(){
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
            url: "/admin/getAllCoursesOfPage",
            datatype: "json",
            success: function (data) {
                table.render({
                    elem: '#table'
                    ,data: data
                    ,toolbar: '#add'
                    ,cols: [[
                        {field:'id', hide:true, title: 'courseId'}
                        ,{field:'title', width:'80%', title: '科目名'}
                        ,{toolbar:'#delete'}
                    ]]
                    ,page: true
                });
            }
        });
    }

    //头工具栏事件：添加
    table.on('toolbar(table)', function(obj){
        if(obj.event === 'add'){
            layer.prompt({
                title: '请输入添加的科目名'
                , formType: 0
            }, function (courseName, index) {// 输入的内容（百分比），弹窗的索引
                //将courseName添加到表格中
                //判断输入是否合法
                if (courseName==="") {
                    layer.alert("输入不能为空！");
                } else {
                    $.ajax({
                        type: "post",
                        url: "/admin/addCourse",
                        data: {courseTitle:courseName},
                        dataType: "json",
                        async:true,
                        success: function(data){
                            if(data.flag==="0"){
                                layer.msg('添加成功！');
                                tableRender();
                            }else{
                                layer.alert(data.content);
                            }
                        }
                    });
                }
                layer.close(index);
            });
        }
    });

    //监听行工具事件：删除
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'delete'){
            layer.confirm('真的删除吗？',{
                title: false, //不显示标题
            }, function(index){
                $.ajax({
                    type: "get",
                    url: "/admin/deleteCourse",
                    data: {courseId:data.id},
                    dataType: "json",
                    async:true,
                    success: function(data){
                        if(data.flag==="0"){
                            layer.msg('删除成功');
                            tableRender();
                        }else{
                            layer.alert(data.content);
                        }
                    }
                });
                layer.close(index);
            });
        }
    });
});