layui.use(['element','table'], function(){
    var element = layui.element
        ,table = layui.table;

    $.ajax({
        type: "get",
        async: false,
        url: "/admin/getTeacherOfPage",
        datatype: "json",
        success: function (data) {
            table.render({
                elem: '#table'
                ,data: data
                ,cols: [[
                    {field:'userName', title: '用户名', templet:'#teaInfo'}
                    ,{field:'phoneNumber', title: '电话号码'}
                    ,{field:'alipayAccount', title: '支付宝账号'}
                    ,{field:'resume', title: '简介'}
                ]]
                ,page: true
            });
        }
    });

});