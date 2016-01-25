 define(function(require,exports,module){
 	var $ = require("jquery");
 	var map = require("common/map");
 	function init(){
			// $.extend($.fn.validatebox.defaults.rules, {    
			// 	custDate: {    
			//         validator: function(value, param){   
			//         	var e_d = value.replaceAll('-', '');
			//         	var s_d = $('#STARTDATE').xcpVal();
			//         	if( s_d != '' ){
			//         		s_d = s_d.replaceAll('-', '');
			        		
			//         		if( s_d.substring(0,4) != e_d.substring(0,4) ){
			//         			$.fn.validatebox.defaults.rules.custDate.message = '开始时间和结束时间不能跨年.';
			//         			return false;
			//         		}
			//         		s_d = parseInt(s_d, 10);
			//         		e_d = parseInt(e_d, 10);
			//         		if( e_d < s_d ){
			//         			$.fn.validatebox.defaults.rules.custDate.message = '结束时间必须大于开始时间.';
			//         			return false;
			//         		}
			//         	}
			//             return true;
			//         },    
			//         message: 'Field do not match.'   
			//     }    
			// });
		// 	 var sysDate=$xcp.def.getSysCurrDate()
		// 	$("#ENDDATE").xcpVal($xcp.def.getSysCurrDate());
		// 	$('#STARTDATE').xcpVal($xcp.date.addDays(sysDate,-30));
		// 	$('#ENDDATE').datebox({
		// 		required:true,
		// 		validType:['custDate']
		// 	});
		// 	var data = $("#BELONGORGNO").combobox("getData");
			
		// 	if(data.length != 0){
		// 		$("#BELONGORGNO").combobox("setValue",data[0].orgNo);
		// 	}
		}
	function generatePage(){
			return map.init();
	}
	module.exports = {
		init : init(),
		generatePage : generatePage()
	}
 });

