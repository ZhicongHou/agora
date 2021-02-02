
class MessageUtil{


    static getSendChat(){return "sendChat"; }
    static getRaiseHand(){return "raiseHand"; }
    static getCancelRaiseHand(){return "cancelRaiseHand"; }
    static getApplyScreen(){return "applyScreen"; }
    static getStopStream(){return "stopStream";}


    static toObject(jsonString){
        return JSON.parse(jsonString.replace(/\n/g,"\\n").replace(/\r/g,"\\r"));
    }

    static toJson(instruction, content=""){
        return '{"instruction":"'+instruction+'", "content":"'+content+'"}';
    }
}


class AdminSignal{
    /**
     * 构造器保存同信所需用到的变量
     * @param  {[type]} aappId   [description]
     * @param  {[type]} userName [description]
     * @return {[type]}          [description]
     */
    constructor(aappId,userName){
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
            $('.layui-btn').removeAttr('disabled').removeClass('layui-btn-disabled').addClass('layui-btn-danger');
        };
        //送到私聊信息时候的回调
        this.userSession.onMessageInstantReceive=function(account, uid, msg){
            alert(account+" : "+msg);
        }

    }

    /**
     * [sendPrivateMessage description] 发送私聊消息
     * @param  {[type]} peer [description]
     * @param  {[type]} msg  [description]
     * @return {[type]}      [description]
     */
    sendPrivateMessage(peer,msg){
        this.userSession.messageInstantSend(peer,msg, function(){});
    }


}
