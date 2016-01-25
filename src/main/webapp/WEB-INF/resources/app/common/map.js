define(function(require,exports,module){
	var $ = require("jquery");
	exports.init = function init(){
		var map = new Object();  
		map.put = function (key,value){  
		    var s = "map." + key + ' = "' + value + '";';  
		    eval(s);  
		}
		map.get = function(key){
			var value = map[key]||0;
			return value;
		}
		map.keySet = function(){  
			var keySets = new Array();  
			for(key in map){  
				if(!(typeof(map[key])=="function")){  
					keySets.push(key);  
				}  
			}  
			return keySets;  
		}
		return map;
	}
});
