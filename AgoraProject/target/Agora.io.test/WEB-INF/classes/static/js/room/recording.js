class Recording{
    constructor(downloadLinkId){
        this.downloadLink = document.querySelector(downloadLinkId);
        this.mediaRecorder;
        this.chunks = [];
        this.count = 0;
    }

    onBtnRecordClicked (audioVideoStream){
        var recording = this;
        console.log('Start recording...');
        if (typeof MediaRecorder.isTypeSupported == 'function'){
            if (MediaRecorder.isTypeSupported('video/webm;codecs=vp9')) {
                var options = {mimeType: 'video/webm;codecs=vp9'};
            } else if (MediaRecorder.isTypeSupported('video/webm;codecs=h264')) {
                var options = {mimeType: 'video/webm;codecs=h264'};
            } else  if (MediaRecorder.isTypeSupported('video/webm;codecs=vp8')) {
                var options = {mimeType: 'video/webm;codecs=vp8'};
            }
            console.log('Using '+options.mimeType);
            this.mediaRecorder = new MediaRecorder(audioVideoStream.stream, options);
        }else{
            console.log('isTypeSupported is not supported, using default codecs for browser');
            this.mediaRecorder = new MediaRecorder(audioVideoStream.stream);
        }
        this.mediaRecorder.start(10);
        audioVideoStream.stream.getTracks().forEach(function(track) {
            console.log(track.kind+":"+JSON.stringify(track.getSettings()));
            console.log(track.getSettings());
        });

        this.mediaRecorder.ondataavailable = function(e) {
            recording.chunks.push(e.data);
        };

        this.mediaRecorder.onerror = function(e){
            console.log('Error: ' + e);
            console.log('Error: ', e);
        };

        this.mediaRecorder.onstart = function(){
            console.log('Started & state = ' + recording.mediaRecorder.state);
        };

        this.mediaRecorder.onstop = function(){
            console.log('Stopped  & state = ' + recording.mediaRecorder.state);
            var blob = new Blob(recording.chunks, {type: "video/webm"});
            recording.chunks = [];
            var videoURL = window.URL.createObjectURL(blob);
            recording.downloadLink.href = videoURL;
            // recording.downloadLink.innerHTML = 'Download video file';
            var rand =  Math.floor((Math.random() * 10000000));
            var name  = "video_"+rand+".webm" ;
            recording.downloadLink.setAttribute( "download", name);
            // recording.downloadLink.setAttribute( "name", name);
        };

        this.mediaRecorder.onpause = function(){
            console.log('Paused & state = ' + recording.mediaRecorder.state);
        }

        this.mediaRecorder.onresume = function(){
            console.log('Resumed  & state = ' + recording.mediaRecorder.state);
        }

        this.mediaRecorder.onwarning = function(e){
            console.log('Warning: ' + e);
        };
    }
    onBtnStopClicked(){
        this.mediaRecorder.stop();
    }

    onResumeClicked(){
        console.log("resume");
        this.mediaRecorder.resume();
    }
    onPauseClicked(){
        console.log("pause");
        this.mediaRecorder.pause();
    }
}