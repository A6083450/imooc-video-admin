var deleteBgm = function(bgmId) {
	
	//var flag = window.confirm("是否确认删除？？？");
    sweetAlert({
        title: "删除背景音乐",
        text: "是否确认删除？？？",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnConfirm: false
    },  function(){
        $.ajax({
            url: '/video/delBgm?bgmId=' + bgmId,
            type: "POST",
            success: function(data) {
                if (data.status == 200 && data.msg == 'OK') {
                    swal({
                        title: "删除成功",
                        text: "删除成功",
                        type: "success",
                        confirmButtonText: "确定"
                    });
                    var jqGrid = $("#bgmList");
                    jqGrid.jqGrid().trigger("reloadGrid");
                }
            }
        })
    });
}

var BgmList = function() {

	// 构建bgm列表对象
    var handleBgmList = function() {
    	
    	// 上下文对象路径
		//var hdnContextPath = $("#hdnContextPath").val();
		//var bgmServer = $("#bgmServer").val();
		
		
		var jqGrid = $("#bgmList");  
        jqGrid.jqGrid({  
            caption: "所有bgm列表",  
            url: "/video/queryBgmList",
            mtype: "post",  
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式  
            datatype: "json",  
            colNames: ['ID', '歌曲名称','作者', '保存路径', '操作'],  
            colModel: [  
                { name: 'id', index: 'id', width: 30 },  
                { name: 'name', index: 'name', width: 30 },
                { name: 'author', index: 'author', width: 20 },
                { name: 'path', index: 'path', width: 50,
                	formatter: function(cellvalue, option, rowObject){

                		var html = "<a href='" + cellvalue + "' target='_blank'>点我播放</a>"
                		return html;
                	}
                },
                { name: '', index: '', width: 50, 
                	formatter: function(cellvalue, option, rowObject){
                		
                		var html = '<button class="btn btn-outline blue-chambray" id="" onclick=deleteBgm("' + rowObject.id + '") style="padding: 1px 3px 1px 3px;">删除</button>';
                		
                		return html;
                	}
                }
            ],  
            viewrecords: true,  		// 定义是否要显示总记录数
            rowNum: 10,					// 在grid上显示记录条数，这个参数是要被传递到后台
            rownumbers: true,  			// 如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
            autowidth: true,  			// 如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
            height: 500,				// 表格高度，可以是数字，像素值或者百分比
            rownumWidth: 36, 			// 如果rownumbers为true，则可以设置行号 的宽度
            pager: "#bgmListPager",		// 分页控件的id  
            subGrid: false				// 是否启用子表格
        }).navGrid('#bgmListPager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        
        
        // 随着窗口的变化，设置jqgrid的宽度  
        $(window).bind('resize', function () {  
            var width = $('.bgmList_wrapper').width()*0.99;  
            jqGrid.setGridWidth(width);  
        });  
        
        // 不显示水平滚动条
        jqGrid.closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    	
    }
    
    return {
        init: function() {
        	handleBgmList();
        }

    };

}();

jQuery(document).ready(function() {
	BgmList.init();
});