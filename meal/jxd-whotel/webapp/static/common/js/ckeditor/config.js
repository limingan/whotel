/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.language = 'zh-cn';    // 配置语言
    config.uiColor = '#FFF';      // 背景颜色  
    config.width = '900px';       // 宽度  
    config.height = '250px';       // 宽度  
    config.skin = 'office2003';   // 界面v2, kama, office2003
    config.filebrowserUploadUrl="/admin/uploadFile.do";
	/*config.toolbar = [
        ['Source','Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['NumberedList','BulletedList','-','Outdent','Indent','TextColor'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','Maximize', 'ShowBlocks','-','Undo','Redo'],
        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak', 'Link', 'Unlink'],
        ['Styles','Format','Font','FontSize','Preview']
    ]; 
    */
    //配置操作界面上的工具按钮
    
    //增加中文字体
    config.font_names = '宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;' + config.font_names ;   
	
};
