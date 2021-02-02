layui.use(['element','table'], function(){

    var element = layui.element
        ,table = layui.table;

    $.ajax({
        type: "get",
        async: false,
        url: "/admin/getUserOfPage",
        datatype: "json",
        success: function (data) {
            console.log(data);
            $.each(data,function (i,item) {
                item.createTime = format(item.createTime);
            });
            table.render({
                elem: '#table'
                ,data: data
                ,cols: [[
                    {field:'userName', title: '用户名', templet:'#userInfo'}
                    ,{field:'email', title: 'email'}
                    ,{field:'realName', title: '真实姓名'}
                    ,{field:'createTime',sort:true, title: '创建时间'}
                ]]
                ,page: true
            });
        }
    });

});

function add0(m){return m<10?'0'+m:m }
function format(timestamp)
{
    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况

    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth()+1;
    var date = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
}