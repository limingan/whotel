$(document).ready(function(){  
    if (window.clipboardData) {  
        $('.copy_btn').click(function() {
            window.clipboardData.setData("Text", $(this).attr("data-url"));  
            boxAlert('复制成功！');  
        }); 
    } else {  
        $(".copy_btn").zclip({  
            path:'/static/common/js/zclip/ZeroClipboard.swf',  
            copy:function(){return  $(this).attr("data-url");},  
            afterCopy:function(){boxAlert('复制成功！');}  
        });  
    }  
});