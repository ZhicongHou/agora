layui.use(['element','table'], function(){
    var element = layui.element
        ,layer = layui.layer
        ,table = layui.table;

    //初始化
    tableRender();

    function tableRender(){
        $.ajax({
            type: "get",
            async: false,
            url: "/admin/getUnauthenTeacherOfPage",
            datatype: "json",
            success: function (data) {
                table.render({
                    elem: '#table'
                    ,data: data
                    ,cols: [[
                        {field:'userId', hide:true}
                        ,{field:'userName', title: '用户名', templet:'#userInfo'}
                        ,{field:'phoneNumber', title: '电话号码'}
                        ,{field:'alipayAccount', title: '支付宝账号'}
                        ,{field:'resume', width:'35%', title: '简介'}
                        ,{title:'认证', toolbar:'#authen', align:'center'}
                    ]]
                    ,page: true
                });
            }
        });
    }


    //监听行工具事件：认证和拒绝
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'authen'){
            layer.confirm('确定认证？',{
                title: false, //不显示标题
            },function (index) {
                $.ajax({
                    type: "post",
                    url: "/admin/authorizeTeaAuthen",
                    data: {userId:data.userId},
                    dataType: "json",
                    async:true,
                    success: function(data2){
                        if(data2.flag==="0"){
                            layer.msg('认证成功!');
                            tableRender();
                            $.ajax({
                                type: "post",
                                url: "/addMessage",
                                data: {receiver:"one", receiverId:data.userName, content : "教师身份已通过管理员审核！您现在可以开课啦！"},
                                dataType: "json",
                                async:true,
                                success: function(data){
                                    if(data.flag==="0"){
                                    }else{
                                        layer.alert(data.content);
                                    }
                                }
                            });
                        }else{
                            layer.alert(data2.content);
                        }
                    }
                });
                layer.close(index);//关闭弹窗
            });
        }
        else if(obj.event === 'refuse'){
            $.ajax({
                type: "post",
                url: "/deleteAuthen",
                data: {userId:data.userId},
                dataType: "json",
                async:true,
                success: function(data2){
                    if(data2.flag==="0"){
                        layer.msg('拒绝认证!');
                        tableRender();
                        $.ajax({
                            type: "post",
                            url: "/addMessage",
                            data: {receiver:"one", receiverId:data.userName, content : "教师身份认证失败！"},
                            dataType: "json",
                            async:true,
                            success: function(data){
                                if(data.flag==="0"){
                                }else{
                                    layer.alert(data.content);
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