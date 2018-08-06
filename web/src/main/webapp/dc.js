var dc = dc || {};

dc.regS = new RegExp("@{2}","gi");

dc.isLogin = false;
dc.actName = null;
dc.loginKey = null;

dc.login = function(un, pw, cid, callback) {
	   $.post("/dc/rest/user/login",
	   {accountName: un,pwd: pw,cid: cid},
	   function(data,status){
		 //var result = eval('(' + data + ')');  
	     if(data.success) {
	    	 dc.loginKey = data.data.loginKey;
	    	 dc.actName = un;
	    	 dc.isLogin = true;
	    	 //console.log(data);
	    	//$("input#userName").val(un);
	     }
	     callback(data);
	   });
}

dc.post=function(path, params, sucCb) {
	dc.ajax(path,params,sucCb,false,'POST',function(err){
		 throw err;
	});
}

dc.get=function(path, params, sucCb) {
	dc.ajax(path,params,subCb,false,'GET',function(err){
		 throw err;
	});
}

dc.ajax=function (path, params, sucCb, async, method, errCb) {
    if (!sucCb) {
        throw "Callback method cannot be null";
    }

    if (!method) {
        throw "request method cannot be null";
    }

    if (!path) {
    	 throw "path cannot be null";
    }
    
    var headers = {};
    if(dc.loginKey) {
    	headers.loginKey = dc.loginKey;
    }

    $.ajax({
        data: params,
        type: method,
        async: async,
        url: path,
        headers:headers,
        success: function (data, statuCode, xhr) {
            sucCb(data, statuCode, xhr);
        },
        beforeSend: function (xhr) {
            //xhr.setRequestHeader('Access-Control-Allow-Headers','*');
           /* if (beans.uc.isLogin()) {
                xhr.setRequestHeader("loginKey", beans.uc.curUser.data.loginKey);
            }*/
        },
        error: function (err, xhr) {
            if (errCb) {
                errCb(err, xhr);
            } else {
                sucCb(null, err, xhr);
            }
        }
    });
}

dc.fromJson=function (data) {
    var json = data.replace(/@@/g, '"');
    json = json.replace(/\(/g, '\{');
    json = json.replace(/\)/g, '\}');
    var jo = JSON.parse(json);
    return jo;
},

dc.parseResponse = function(data) {
	
	if(!data || !data.data) {
		return data;
	}
	
	 var json = data.data
	 json = json.replace(/[(]{1}/,"\{");
	 
 	 json = json.replace(/[)]{1}/,"\}");
 	 
 	 json = json.replace( dc.regS,"\"");
	 data.data = eval('(' + json + ')');
	 
	 return data;
	
}