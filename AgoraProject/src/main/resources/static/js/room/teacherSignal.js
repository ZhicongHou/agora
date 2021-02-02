


class MessageUtil{


    static getSendChat(){return "sendChat"; }
    static getRaiseHand(){return "raiseHand"; }
    static getCancelRaiseHand(){return "cancelRaiseHand"; }
    static getApplyScreen(){return "applyScreen"; }
    static getStopStream(){return "stopStream";}
    static getStopStudentShareScreen(){return "stopStudentShareScreen";}



    static toObject(jsonString){
        return JSON.parse(jsonString.replace(/\n/g,"\\n").replace(/\r/g,"\\r"));
    }

    static toJson(instruction, content=""){
        return '{"instruction":"'+instruction+'", "content":"'+content+'"}';
    }
}


class TeacherDiscussionSignal{
    /**
     * 构造器保存同信所需用到的变量
     * @param  {[type]} aappId   [description]
     * @param  {[type]} userName [description]
     * @return {[type]}          [description]
     */
    constructor(aappId,userName,secId,role){
        this.secId=secId;
        this.role=role;
        this.roomId=null;
        this.appId=aappId;
        this.signal=null;
        this.userSession=null;
        this.messageType=null;
        this.userName=userName;
        this.joinSignal(userName);
    }


    /**
     * [joinSignal description]用户登陆到signal中
     * @param  {[type]} uid [description]
     * @return {[type]}     [description]
     */
    joinSignal(userName){
        //appid登陆到项目的signal
        this.signal = new Signal(this.appId);
        this.userSession = this.signal.login(userName,"_no_need_token");
        //登陆成功时候的回调
        this.userSession.onLoginSuccess = function () {
            console.log("login signal success");
        };
        //送到私聊信息时候的回调
        this.userSession.onMessageInstantReceive=function(account, uid, jsonString){
            var message = MessageUtil.toObject(jsonString);
            if(message.instruction===MessageUtil.getStopStream()){
                setTimeout(function () {
                    window.location.href = "alert.html";
                },3000);
            }
        }

    }

    /**
     * [joinchatChannal description] 用户加入某一频道，群聊
     * @param  {[type]} this.userSession [description] 用户用于通信的session
     * @param  {[type]} roomId      [description] 房间的id
     * @return {[type]} channel     [description]
     */
    joinChatChannel(roomId,chatId){
        //做一个外部引用
        let usignal=this;
        this.roomId=roomId;
        var channel = this.userSession.channelJoin(roomId);
        this.chatId=chatId;
        //回调函数
        //用户成功加入
        channel.onChannelJoined = function(){
            console.log("enter the channel success");
            //学生列表可用
            $('.studentItem').find(".layui-btn").removeClass('layui-btn-disabled').removeAttr('disabled');
            //老师的讨论区初始化
            $('#desc').removeAttr('disabled').attr('placeholder','请输入内容');
            usignal.displayPersonList();
        };
        //用户加入失败
        channel.onChannelJoinFailed = function(ecode){
            console.log("enter the channel fail :" + ecode);
        };
        //用户离开
        channel.onChannelLeaved = function(ecode){
            console.log("user leave the channel ：" + ecode);

        };
        //其他用户加入时候的回调函数
        channel.onChannelUserJoined = function(uid,id){
            console.log(uid+" enter channel");
            usignal.displayPersonList();
        };
        //其他用户离开时的回调函数
        channel.onChannelUserLeaved = function(uid,id){
            console.log(uid+" leave channel");
            if(uid=='admin'){
                $("#online"+uid).attr("src","/img/student.png");
            }
            usignal.displayPersonList();
        };
        //用户接收到的群信息
        channel.onMessageChannelReceive=function(account, uid, msg){
            // console.log(account+msg);
            usignal.displayChatMessage(usignal.chatId,account,msg);
        };
        return channel;
    }



    /**
     * [leaveChatChannel description] 用户离开群聊房间
     * @param  {[type]} channel [description] 用户加入的房间
     * @return {[type]}         [description]
     */
    leaveChatChannel(channel){
        channel.channelLeave(function(){
            console.log("leave channel");
        });
    }

    /**
     * [sendChatMessage description] 发送群聊消息
     * @param  {[type]} channel [description]
     * @param  {[type]} message [description]
     * @return {[type]}         [description]
     */
    sendChatMessage(channel,message){
        let usignal = this;
        channel.messageChannelSend(message, function(){});
    }

    /**
     * [displayMessage description] 显示群聊消息
     * @param  {[type]} label_id [description]显示消息的组件id
     * @param  {[type]} message  [description]显示的消息
     * @return {[type]}          [description]
     */
    displayChatMessage(labelId,account,jsonString){
        var message = MessageUtil.toObject(jsonString);
        if(message.instruction===MessageUtil.getRaiseHand()&&account!='admin'){
            $("#hand"+account).attr("src","/img/hand-green.png");
        }else if(message.instruction===MessageUtil.getCancelRaiseHand()&&account!='admin'){
            $("#hand"+account).attr("src","/img/hand.png");
        }else if(message.instruction===MessageUtil.getSendChat()){
            var content = message.content;
            $("#"+labelId).append("<li>" + account + " : " + content + "</li>");
        }
    }

    /**
     * [sendPrivateMessage description] 发送私聊消息
     * @param  {[type]} peer [description]
     * @param  {[type]} msg  [description]
     * @return {[type]}      [description]
     */
    sendPrivateMessage(peer,msg){
        this.userSession.messageInstantSend(peer, msg, function(){});
    }

    // loadPersonList(amountId,personListId){
    // 	this.amountId=amountId;
    // 	this.personListId=personListId;
    // 	alert(this.amountId+"  "+personListId);
    // 	this.displayPersonList(amountId,personListId);
    // }

    /**
     *  显示成员列表
     * @param  {[type]} roomId       [description] 房间id
     * @param  {[type]} amountId     [description]	用于显示人数的组件id
     * @param  {[type]} personListId [description]	用于显示成员列表的组件id
     * @return {[type]}              [description]
     */
    displayPersonList(){
        let userSignal = this;
        this.userSession.invoke("io.agora.signal.channel_query_userlist", {"name":userSignal.roomId},function(err,val){
            $("#amount").html(val.num-1);
            var list = val.list;
            for(var i = 0; i<list.length; i++){
                if(list[i][0]!=userSignal.userName&&list[i][0]!='admin'){
                    $("#online"+list[i][0]).attr("src","/img/student-blue.png");
                }
            }
        });
    }

}
