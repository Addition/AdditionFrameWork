document.write("<script type=\"text/javascript\" src=\"sha1.js\"></script>");

var weixinService = angular.module('WeixinService',[]);
var API_KEY = "gns11529c136998cb6";
var token = "";
var sessionId = "";
var loginSucess;
/*function includeJs(jsFilePath) {
    var js = document.createElement("script");

    js.type = "text/javascript";
    js.src = jsFilePath;

    document.head.appendChild(js);
}

includeJs("sha1.js");*/

function handleLoginData(data)
{
	if(null != data.status && 0 == data.status.code)
		loginSucess = true;
	if(null != data.data)
		{
			token = data.data.token;
			sessionId = data.data.sessionId;
		}
}

function getSha1(timestamp, nonce, encrypt)
{
	var arr
	var text
	if(null != encrypt)
		{
			arr = new Array(API_KEY,timestamp,nonce,encrypt);
			arr.sort();
			text = arr[0] + arr[1] + arr[2] + arr[3];
		}else
		{
			arr = new Array(API_KEY,timestamp,nonce);
			arr.sort();
			text = arr[0] + arr[1] + arr[2];
		}
	
	return CryptoJS.SHA1(text);
}

function weixinRouteConfig($routeProvider)
{
	$routeProvider.
	when('/login',{controller:userLoginController,templateUrl:'login.html'}).
	when('/contacts',{controller:contactsController,templateUrl:'contacts.html'}).
	otherwise({redirectTo:'/login'});
}
weixinService.config(weixinRouteConfig);

function userLoginController($scope,$http,$location)
{
	$scope.message = '';
	var signature = getSha1('1436510297000','gnesasadwerer',null);
	var config = {headers:{'Content-Type':'application/json;charset=UTF-8','X-Timestamp':'1436510297000','X-Nonce':'gnesasadwerer','X-Signature':signature}}
	$scope.login = function(user){
        $http.post("/nuas/api/v1/sessions/",user,config)
        .success(function(data, status, headers, config)
        		{
        	handleLoginData(data);
            $location.path("/contacts");
        		})
        .error(function(data, status, headers, config)
        		{
            alert("error");
        		});
    }
}

function contactsController($scope,$http,$location)
{
	$scope.message = '';
	var signature = getSha1('1436510297000','gnesasadwerer',null);
	var config = {headers:{'Content-Type':'application/json;charset=UTF-8','X-Timestamp':'1436510297000','X-Nonce':'gnesasadwerer','X-Signature':signature,'X-Token':token}}
	$http.get('/nuas/api/v1/contacts/',config)
	.success(function(data, status, headers, config)
    		{
				$scope.contacts = data.data;
    		})
    .error(function(data, status, headers, config)
    		{
        alert("error");
    		});
}