$(function() {
    getMood();
    //点击发送
    $("#send-but").bind("click", function () {
        sendInfo();
    });
    //点击测试
    $("#test").bind("click", function () {
        var yuan=$("#happy-span").text();

        var xian=50;
        $("#happy-span").animateNumber({ number: 165 });
    });

})



//监听键盘事件
$(document).keypress(function(e) {
    $("#input-area").focus();
debugger
    if (e.ctrlKey && e.which == 10){
        sendInfo();
    }
});


//发送信息
function sendInfo() {
    getMood();

    var info=$("#input-area").val();
    debugger
    var html="<li><div class=\"via-peer via\"></div><span class=\"advices-peer advices\">"+info+"</span></li>";

    if(info.length!=0){
        $("#content-ul").append(html);
        $("#input-area").val("");
        $.ajax({
            url: 'Think?',
            data:{data:info},
            syn:false,
            success: function (data) {
                html="<li><div class=\"via-our via\"></div><span class=\"advices-our advices\">"+data+"</span></li>";
                $("#content-ul").append(html);
                //滚动条向下滚
                $("#content").scrollTop($("#content-ul").height());
            }
        });
    }
}

var mood=["happy","doleful","anger","fear"];
/*情绪刷新*/
function getMood(){
    $.ajax({
        url: 'Mood/getMood',
        data_type: "json",
        success: function (data) {
            //data:情绪对象的json格式

            mood.forEach(function( val, index ) {
                $("#"+val).css('width', data[val]+"%");
                $("#"+val+"-span").prop('number', $("#"+val+"-span").text()).animateNumber({ number: data[val] });;
            });



        }
    });
}
/*心跳机制*/
setInterval(function () {
    getMood();
},1000);