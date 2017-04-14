<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信开门</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="stylesheet" type="text/css" href="/static/common/js/amazeui/css/widget.css">
<link rel="stylesheet" type="text/css" href="/static/common/js/kalendae/kalendae.css">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/common${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/allmain${THEME}.css?v=${version}">
<link rel="stylesheet" type="text/css" href="/static/front/hotel/css/unlock.css?v=${version}">
<link rel="stylesheet" type="text/css"  href="/static/front/css/loading.css?v=${version}"/>
</head>
<body>
<div class="checkInInfo">
	<div class="idNo">
		<i style="background: url('/static/front/hotel/images/idCard.png') no-repeat center center;background-size:100% "></i>
		<input id="idNo" name="idNo" type="text" placeholder="请输身份证号码" value="${checkInRecord.idNo}"/>
	</div>
	<div class="roomNo">
		<i style="background: url('/static/front/hotel/images/roomNo.png') no-repeat center center;background-size:100%"></i>
		<input id="roomNo" name="roomNo" type="text" placeholder="请输入房间号" value="${checkInRecord.roomNo}"/>
	</div>
</div>

<div class="menu">
<div class="menu_left"><a href="javascript:onCheckIn()">入住</a></div>
<div class="menu_rigth"><a href="javascript:oneKeyOpen()">开锁</a></div>
</div>

<div class="am-modal am-modal-alert" tabindex="-1" id="alertTip">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="alertMsg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>
<div class="loading" id="loading"></div>
<script src="/static/common/js/jquery-1.11.2.js"></script>
<script src="/static/common/js/amazeui/amazeui.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">
	var $loading = $("#loading");
	var isInitLib = false;//蓝牙是否开启
    var isConnected = false;//设备连接状态
    var deviceId;//设备号，锁号
    var keyData;//开锁秘钥
    var checkInRecordId;

	/* 空 */
	function isNullOrEmpty(text) {
	 	if (text == null || typeof (text) != "string" || text == "" || text.length == 0) {
			return true;
		} else {
			return false;
		}
	};

    function onCheckIn() {
		if (isNullOrEmpty($('#idNo').val())) {
        	swal('请输入身份证号码!');
        } else if (isNullOrEmpty($('#roomNo').val())) {
        	swal('请输入房间号!');
        } else {
        	$loading.show();
			$.ajax({
				url : '/oauth/unlock/validateCheckInRecord.do',
				data : {
					idNo : $('#idNo').val(),
					roomNo : $('#roomNo').val()
				},
				success : function(res){
                    console.log(res);
					if(isNullOrEmpty(res.errorMsg)){
						deviceId = res.deviceId;
						keyData = res.content;
						checkInRecordId = res.id;
						bindDevice();
					} else {
						swal(res.errorMsg);
					}
				},
				error : function(res){
                    console.log('error:' + res);
					swal("入住异常");
				},
    			complete : function(){
    				$loading.hide();
    			}
			});
        }
		return;
    };

    function BleManage() {
    };

    //在它的原型上添加方法
    BleManage.prototype = {
        //初始化蓝牙库
        bleInit_openLib : function() {
               wx.invoke('openWXDeviceLib', {
               }, function(res) {
               		console.log('openWXDeviceLib:', res);
					if (res.bluetoothState == "on") {
						isInitLib = true;
                   } else {
						isInitLib = false;
                   }
               });
        },
	        
        //搜索蓝牙
        bleInit_scan : function() {
            wx.invoke('startScanWXDevice', {
                'btVersion' : 'ble'
            }, function(res) {
                console.log('开始扫描设备：', res);
            });
        },
        
        //关闭蓝牙      
        bleInit_stopScan : function() {
            wx.invoke('stopScanWXDevice', {
                'connType' : 'blue'
            }, function(res) {
                console.log('停止扫描设备：', res);
            });
        },
        
        //获取设备信息      
        bleInit_getDev_Info : function() {
            wx.invoke('getWXDeviceInfos', {
                'connType' : 'ble'
            }, function(res) {
                var infos = res.deviceInfos;
                for (var i = 0; i < infos.length; i++) {
                    if("connected" == infos[i].state) {
						//$("#lockImg").attr('src', "/styles-wap/images/connected.png");
						isConnected = true;
                    } else {
						//$("#lockImg").attr('src', "/styles-wap/images/disconnect.png");
						isConnected = false;
					}
                    return;
                }
                
                isConnected = false;
            });
        },
        
        //连接蓝牙   
        bleInit_connBle : function(callback) {
            wx.invoke('connectWXDevice', {
                'deviceId' : deviceId,
                'connType' : 'ble'
            }, function(res) {
            	callback && callback();
                console.log('connectWXDevice', res);
            });
        },
        
        bleInit_disconnBle : function() {
            wx.invoke('disconnectWXDevice', {
                'deviceId' : deviceId,
                'connType' : 'ble'
            }, function(res) {
                console.log('connectWXDevice', res);
            });
        },
        
        //获取ticket:type为1表示绑定,2表示解除绑定 
        bleInit_bindDevice : function() {
            wx.invoke('getWXDeviceTicket', {
                'deviceId' : deviceId,
                'type' : '1'
            }, function(res) {
            	if(res.err_msg != "getWXDeviceTicket:ok"){
					swal("获取操作凭证失败，请重试"+res.err_msg);
					//alert(res.err_msg);
	            } else {
                    $.ajax({
            			url : '/oauth/unlock/ajaxBindDevice.do',
            			data :{
            				ticket : res.ticket,
            				id:checkInRecordId
        				},
            			success : function(res){
                            console.log(res);
            				if(res == "ok"){
								swal("绑定成功！");
            				}
            			},
            			error : function(res){
							swal("绑定失败，请重试！");
            			},
            			complete : function(){
            				$loading.hide();
            			}
            		});
	            }
            });
            wx.error(function (res) {
            	alert(res);
            });
        },
        
        //发送数据      
        bleInit_sendData : function() {
            wx.invoke( 'sendDataToWXDevice',
                        {
                            'deviceId' : deviceId,
                            'connType' : 'ble',
                            'base64Data' : keyData
                        }, function(res) {
                        	//bleDisConn();//暂时先不断开
                        	$loading.hide();
                        	if(res.err_msg == "sendDataToWXDevice:ok"){
	                        	swal("开锁成功！");
                        	}else{
                        		swal("开锁失败！");
                        	}
                        	//alert(JSON.stringify(res))
                        });
        },
        
        //初始化微信配置      
        bleInit_callback : function(appId,signature,timestamp,nonceStr) {
            //刚刚开测试 建议这4个值 直接在页面上写死
            //动态获取签名的时候才建议 使用下面 ready里面的ajax
            wx.config({
                beta : true, //这个很重要，必须配置这个为true,才能调用微信的硬件API
                debug : false, //是否开启调试模式，会自动弹一些消息框显示微信返回的数据
               	appId: appId, //微信公众号的appId
               	timestamp: timestamp, // 必填，生成签名的时间戳
                nonceStr: nonceStr, // 必填，生成签名的随机串
	    		signature: signature,
                jsApiList : [ //需要调用的接口，都得在这里面写一遍
                "openWXDeviceLib",//初始化设备库（只支持蓝牙设备）
                "closeWXDeviceLib",//关闭设备库（只支持蓝牙设备）
                "getWXDeviceInfos",//获取设备信息（获取当前用户已绑定的蓝牙设备列表）
                "sendDataToWXDevice",//发送数据给设备
                "connectWXDevice",//连接设备
                "disconnectWXDevice",//断开设备连接
                "getWXDeviceTicket",//获取操作凭证
                //下面是监听事件：
                "onWXDeviceBindStateChange",//微信客户端设备绑定状态被改变时触发此事件
                "onWXDeviceStateChange",//监听连接状态，可以监听连接中、连接上、连接断开
                "onReceiveDataFromWXDevice",//接收到来自设备的数据时触发
                "onScanWXDeviceResult",//扫描到某个设备时触发
                "onWXDeviceBluetoothStateChange",//手机蓝牙打开或关闭时触发
                ]
            });
            
            wx.ready(function() {
                wx.invoke('openWXDeviceLib', {
                }, function(res) {
                    if (res.bluetoothState == "on") {
                        isInitLib = true;
                    } else {
                    	isInitLib = false;
                    }
                });  

              	//蓝牙改变事件（打开，关闭蓝牙的时候触发）
                wx.on('onWXDeviceBluetoothStateChange', function(res) {
                	console.log('onWXDeviceBluetoothStateChange', res);
					if("on" == res.state) {
						isInitLib = true;
					} else {
						isInitLib = false;
					}
					
			    	if(isInitLib) {
			    		//连接门锁
			    		bleConn();
			    	}
                });
                
                //设备绑定状态改变事件（解绑成功，绑定成功的瞬间，会触发）
                wx.on('onWXDeviceBindStateChange', function(res) {
                	console.log('onWXDeviceBindStateChange', res);
                });
				
                //设备连接状态改变
                wx.on('onWXDeviceStateChange', function(res) {
                    //有3个状态：connecting连接中,connected已连接,unconnected未连接
                    //每当手机和设备之间的状态改变的瞬间，会触发一次 
                    console.log('onWXDeviceStateChange', res);
					if("connected" == res.state) {
						//$("#lockImg").attr('src', "/styles-wap/images/connected.png");
						isConnected = true;
					} else {
						//$("#lockImg").attr('src', "/styles-wap/images/disconnect.png");
						isConnected = false;
					}
                });
				
                wx.error(function(res) {
                    alert("wx.error错误：" + JSON.stringify(res));
                    //如果初始化出错了会调用此方法，没什么特别要注意的        
                }); 
            });  
        }    
    } 
    
    var bleManage = null;    
    
    //开始扫描
    function openLib() {
        bleManage.bleInit_openLib();
    }    
    
    //开始扫描
    function scan() {
        bleManage.bleInit_scan();
    }
	
    //停止扫描
    function stopScan() {
        bleManage.bleInit_stopScan();
    }
	
    //查询周边设备的连接状态
    function getInfo() {
        bleManage.bleInit_getDev_Info();
    }
	
    //连接设备
    function bleConn(callback) {
        bleManage.bleInit_connBle(callback);
    }
    
  	//断开连接
    function bleDisConn() {
        bleManage.bleInit_disconnBle();
    }
    
    
    //JSSDK发送开门指令
    function sendData() {
        bleManage.bleInit_sendData();
    }
    
    //绑定设备
    function bindDevice() {
        bleManage.bleInit_bindDevice();
    }
    
    //进来动态加载签名      
    $(document).ready(function() {
        bleManage = new BleManage();
		var timestamp = parseInt((new Date).getTime()/1000);
        var nonceStr = Math.random().toString(36).substr(2);
        
        $.ajax({
    		url: "/ajaxGetSignature.do",
    		type: "POST",
    		data: {timestamp: timestamp, nonceStr: nonceStr, url: location.href.split('#')[0]},
    		dataType: "JSON",
    		success: function(data){
    			bleManage.bleInit_callback(data.appId,data.signature,timestamp,nonceStr);
    			
    			if(isNullOrEmpty("${checkInRecord.content}") && !isNullOrEmpty($('#idNo').val()) && isNullOrEmpty($('#roomNo').val())){
    	        	onCheckIn();
    			}
    		}
    	});
    });
    

    //一键开门
    function oneKeyOpen() {
    	if (isNullOrEmpty($('#idNo').val())) {
        	swal('请输入身份证号码!');
        } else if (isNullOrEmpty($('#roomNo').val())) {
        	swal('请输入房间号!');
        } else{
        	$loading.show();
	    	$.ajax({
				url : '/oauth/unlock/validateCheckInRecord.do',
				type: "POST",
	    		dataType: "JSON",
				data : {
					idNo : $('#idNo').val(),
					roomNo : $('#roomNo').val()
				},
				success : function(res){
					if(!isNullOrEmpty(res.errorMsg)){
						swal(res.errorMsg);
						return;
					}else{
						deviceId = res.deviceId;
						keyData = res.content;
					}
					//检查蓝牙是否开启
			    	if(isInitLib) {
			    		//连接门锁
			    		$loading.text("正在连接蓝牙请稍后...");
			    		$loading.show();
			    		bleConn(sendData());
			    	} else {
			    		swal("手机蓝牙未开启,请确认开启后重试");
			    		return;
			    	}
				},
				error : function(res){
	                console.log('error:' + res);
					swal("入住异常");
				},
    			complete : function(){
    				$loading.hide();
    			}
			});
        }
    };
    
	function swal(msg){
		$("#alertMsg").html(msg);
		$("#alertTip").modal();
	}
</script> 
</body>
</html>
