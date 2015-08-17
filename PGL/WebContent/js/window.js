	window.onload = windowHeight; //页面载入完毕执行函数  
    function windowHeight() {  
            var h = document.documentElement.clientHeight; //获取当前窗口可视操作区域高度  
            var bodyHeight = document.getElementById("box"); //寻找ID为content的对象  
            bodyHeight.style.height = (h - 250) + "px"; //你想要自适应高度的对象

        }
	setInterval(windowHeight, 500)//每半秒执行一次windowHeight函