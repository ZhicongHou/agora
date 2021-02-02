$(function () {
    //初始化
    var status = "ready";// recording, pause(就绪，正在录制，暂停)

   $("#record").on("click",function () {
       if (status === "recording" || status === "pause"){
           return;
       }
       if(status === "ready"){
           status = "recording";
           $('#record').attr("src","img/record.png");
           $('#pause').attr("src","img/pause-red.png");
           $('#stop').attr("src","img/stop-red.png");
       }
   });

   $('#pause').on("click",function () {
      if(status === "ready"){
          return;
      }
      if(status === "recording"){
          status = "pause";
          $('#record').attr("src","img/record.png");
          $('#pause').attr("src","img/start-red.png");
          $('#stop').attr("src","img/stop-red.png");
          return;
      }
      if(status === "pause"){
          status = "recording";
          $('#record').attr("src","img/record.png");
          $('#pause').attr("src","img/pause-red.png");
          $('#stop').attr("src","img/stop-red.png");
      }
   });

   $('#stop').on("click",function () {
       if(status === "ready"){
           return;
       }
       if(status === "pause" || status === "recording"){
           status = "ready";
           $('#record').attr("src","img/record-red.png");
           $('#pause').attr("src","img/pause.png");
           $('#stop').attr("src","img/stop.png");
       }
   });
});