$(function() {
    function distanceByLnglat(lng1, lat1, lng2, lat2) {
        var radLat1 = Rad(lat1);
        var radLat2 = Rad(lat2);
        var a = radLat1 - radLat2;
        var b = Rad(lng1) - Rad(lng2);
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;
        s = Math.round(s * 10000) / 10000000;
        s = s.toFixed(2);
        return s;
    }
    function Rad(d) {
        return d * Math.PI / 180.0
    };
    var point = new BMap.Point(116.331398, 39.897445);
    var geolocation = new BMap.Geolocation();
    if (document.location.href.indexOf("/shop/all/") == 0) {
        geolocation = null
    };
    geolocation.getCurrentPosition(function(r) {
        var _this = this;
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            locLng = r.point.lng;
            locLat = r.point.lat;
            $(".morelist").each(function() {
                var ShopLngLat = $(this).find("#showlan").val();
                var InputOF = ShopLngLat.indexOf(",");
                var InputOFLast = ShopLngLat.length;
                var ShopLng = ShopLngLat.slice(0, InputOF);
                var ShopLat = ShopLngLat.slice(InputOF + 1, InputOFLast);
                var dis111 = distanceByLnglat(locLng, locLat, ShopLng, ShopLat);
                $(this).find("#shopspostion").html(dis111 + "km");
            });
            $("#curlat").val(locLat);
            $("#curlng").val(locLng);

            isposition = $("#isposition").val();
            cururl = $("#cururl").val();
            if (isposition == 0) {
                var url = cururl + '&lat=' + locLat + '&lng=' + locLng;
                window.location = url;
            }
        } else {
            $(".morelist").each(function() {
                $(this).find("#shopspostion").html("鏃犳硶鑾峰彇璺濈" + _this.getStatus() + "");
            });
        }
    }, {
        enableHighAccuracy: true
    });
    //鍏充簬鐘舵€佺爜
    //BMAP_STATUS_SUCCESS	妫€绱㈡垚鍔熴€傚搴旀暟鍊尖€�0鈥濄€�
    //BMAP_STATUS_CITY_LIST	鍩庡競鍒楄〃銆傚搴旀暟鍊尖€�1鈥濄€�
    //BMAP_STATUS_UNKNOWN_LOCATION	浣嶇疆缁撴灉鏈煡銆傚搴旀暟鍊尖€�2鈥濄€�
    //BMAP_STATUS_UNKNOWN_ROUTE	瀵艰埅缁撴灉鏈煡銆傚搴旀暟鍊尖€�3鈥濄€�
    //BMAP_STATUS_INVALID_KEY	闈炴硶瀵嗛挜銆傚搴旀暟鍊尖€�4鈥濄€�
    //BMAP_STATUS_INVALID_REQUEST	闈炴硶璇锋眰銆傚搴旀暟鍊尖€�5鈥濄€�
    //BMAP_STATUS_PERMISSION_DENIED	娌℃湁鏉冮檺銆傚搴旀暟鍊尖€�6鈥濄€�(鑷� 1.1 鏂板)
    //BMAP_STATUS_SERVICE_UNAVAILABLE	鏈嶅姟涓嶅彲鐢ㄣ€傚搴旀暟鍊尖€�7鈥濄€�(鑷� 1.1 鏂板)
    //BMAP_STATUS_TIMEOUT	瓒呮椂銆傚搴旀暟鍊尖€�8鈥濄€�(鑷� 1.1 鏂板)
});
