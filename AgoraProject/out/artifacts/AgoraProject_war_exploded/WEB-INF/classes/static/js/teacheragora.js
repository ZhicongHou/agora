
class TeacherAgora{
    constructor(appid,roomNum){
        this.appid=appid;
        this.client;
        this.localStream;
        this.camera;
        this.microphone;
        this.room=""+roomNum;
        if (!AgoraRTC.checkSystemRequirements()) {
            alert("Your browser does not support WebRTC!");
        }
        let teacherAgora = this;
        //打印测试信息
        AgoraRTC.Logger.error('this is error');
        AgoraRTC.Logger.warning('this is warning');
        AgoraRTC.Logger.info('this is info');
        AgoraRTC.Logger.debug('this is debug');

        AgoraRTC.getDevices(function (devices) {
            for (var i = 0; i !== devices.length; ++i) {
                var device = devices[i];
                if (device.kind === 'audioinput') {
                    teacherAgora.microphone = device.label;
                } else if (device.kind === 'videoinput') {
                    teacherAgora.camera = device.label;
                } else {
                    console.log('Some other kind of source/device: ', device);
                }
            }
        });

    }

    //客户端连接其他客户端的主机
    join() {
        //随机生成密钥
        var channel_key = null;
        //客户端配置
        var configClient = {
            mode: 'live',
            codec: 'vp8'
        }
        //创建客户端对象
        this.client = AgoraRTC.createClient(configClient);
        //客户端初始化
        let teacherAgora = this;
        this.client.init(this.appid, function () {
            console.log("AgoraRTC client initialized");
            teacherAgora.client.join(channel_key,teacherAgora.room,"t"+teacherAgora.room, function () {
                console.log("User "+ " join channel successfully");
            }, function (err) {
                console.log("Join channel failed", err);
            });
        }, function (err) {
            console.log("AgoraRTC client init failed", err);
        });
        this.channelKey = "";
        //回调方法打印错误信息
        this.client.on('error', function (err) {
            console.log("Got error msg:", err.reason);
            if (err.reason === 'DYNAMIC_KEY_TIMEOUT') {
                teacherAgora.client.renewChannelKey(channelKey, function () {
                    console.log("Renew channel key successfully");
                }, function (err) {
                    console.log("Renew channel key failed: ", err);
                });
            }
        });

        //回调通知，其他客户端的音视频流加入
        this.client.on('stream-added', function (evt) {
            var stream = evt.stream;
            console.log("New stream added: " + stream.getId());
            console.log("Subscribe ", stream);
            teacherAgora.client.subscribe(stream, function (err) {
                console.log("Subscribe stream failed", err);
            });
        });
        //回调通知，已接受远程音视频流
        this.client.on('stream-subscribed', function (evt) {
            var stream = evt.stream;
            console.log("Subscribe remote stream successfully: ");
            var newScreen = "<div class='studentScreen layui-inline' id=s"+stream.getId()+">" +
                "<div class='layui-text idInScreen'>"+stream.getId()+"</div>"+
                "<div class='smallScreen' id='screen"+stream.getId()+"'></div>"+
                "</div>";
            $("#smallStudentScreen").append(newScreen);
            stream.play('screen'+stream.getId());
        });
        //该回调通知 App 已删除远程音视频流
        this.client.on('stream-removed', function (evt) {
            var stream = evt.stream;
            stream.stop();
            console.log("Remote stream is removed " + stream.getId());
        });
        //该回调通知 App 对方用户已离开频道，即对方调用了
        this.client.on('peer-leave', function (evt) {
            var stream = evt.stream;
            $('#s' + stream.getId()).remove();
            if (stream) {
                stream.stop();
            }
        });
    }

    createStream(){
        var teacherAgora = this;
        //创建本地音视频流
        // this.localStream = AgoraRTC.createStream({streamID: this.room, audio: false, cameraId: this.camera, microphoneId: this.microphone, video: true, screen: false});
        this.localStream = AgoraRTC.createStream({streamID: this.room, audio: true, cameraId: this.camera, microphoneId: this.microphone, video: false, screen: true, extensionId: 'minllpmhdgpndnkomcoccfekfegnlikg'});
        this.localStream.on("accessAllowed", function() {
            console.log("accessAllowed");
        });

        // The user has denied access to the camera and mic.
        this.localStream.on("accessDenied", function() {
            console.log("accessDenied");
        });

        this.localStream.init(function() {
            console.log("getUserMedia successfully");
            teacherAgora.client.on('stream-published', function (evt) {
                console.log("Publish local stream successfully");
            });
        }, function (err) {
            console.log("getUserMedia failed", err);
        });
    }
    //学生取消视频共享
    cancel(){
        console.log("localStream cancel");
        this.client.unpublish(this.localStream, function (err) {
            console.log("Unpublish local stream failed" + err);
        });
        this.localStream.close();
    }

    publish(){
        this.client.publish(this.localStream,function (err) {
            console.log(err);
        })
    }

    unpublish(){
        this.client.unpublish(this.localStream,function (err) {
            console.log(err);
        })
    }

    close(){
        this.localStream.close();
        this.client.leave(function () {
            console.log("client leaves channel");
        })
    }

    displayStream(displayElemId){

    }
}
