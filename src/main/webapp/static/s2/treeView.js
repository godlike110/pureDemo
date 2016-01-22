/*
*简单mini的treeView控件
*作者：周文
*采用jQuery中getJSON模拟后台异步获取数据，请使用非Chrome环境测试
*初识前端，控件的结构原理如html页面中注释部分，代码封装和复用等还有待进一步提高和完善
*任何问题欢迎交流，联系邮箱：947778255@qq.com
*/


;(function($, window, undefined){
	//获取数据
 	function getData(url, selector) {
		$.getJSON(url, function(re){
			var htmlNode = constructTreeNode(re.data);
			
			if(selector === "root") {
				nodeToTree(".treeView", htmlNode);
			}
			else {
				selector.removeClass().addClass("node expand");
				selector.siblings(".collapsedVal").removeClass().addClass("expandVal");
				selector.siblings(".loading").hide();
				nodeToTree(selector.siblings(".children"), htmlNode);
			}
		})
	}	

	//构造节点
	function constructTreeNode(data) {
		var htmlNode = "";
		var Node;
		for(var i = 0, len = data.length; i < len; i++) {
			Node = "";
			Node = "<div class='treeNode'>";
			if(data[i].hasChildren) {
				Node += "<a class='collapsedVal' data-href="+data[i].id+">"+data[i].value+"</a>"+"<a class='node collapsed' id="+data[i].id+"></a><span class='loading'></span><div class='children'></div>";
			}
			else {
				Node += "<a class='indicator' data-href="+data[i].id+">"+data[i].value+"</a>";
			}
			Node += "</div>";
			htmlNode += Node;			
		}
		return htmlNode;
	}

	//将新节点添加到DOM
	function nodeToTree(selector, htmlNode) {
		$(selector).append(htmlNode);
	}

	//获取根节点数据
	getData("/zk/getData", "root");

	//点击最终指标取得其id值
	$(document).on("click", ".indicator", function(){
		alert($(this).attr("data-href"));
		$(".bgBlue").removeClass("bgBlue");
		$(this).addClass("bgBlue");
	})

	//点击展开图标，如果子节点数据已经存在则显示，否则从数据库获取
	$(".treeView").on("click", ".collapsed, .collapsedVal", function(){
		var id = $(this).attr("id") === undefined ? $(this).attr("data-href") : $(this).attr("id");
		var selector = $(this).attr("id") === undefined ? $(this).siblings(".collapsed") : $(this);
		if(selector.siblings(".children").html()) {
			selector.removeClass().addClass("node expand");
			selector.siblings(".collapsedVal").removeClass().addClass("expandVal");
			selector.siblings(".children").show();
		}
		else {
			$(this).siblings(".loading").show();
			getData("/zk/getData?qs="+id, selector);
		}	
	});

	//点击折叠图标，将其子节点隐藏
	$(".treeView").on("click", ".expand", function(){
		$(this).removeClass().addClass("node collapsed");
		$(this).siblings(".expandVal").removeClass().addClass("collapsedVal");
		$(this).siblings(".children").hide();
	})

})(jQuery, window);