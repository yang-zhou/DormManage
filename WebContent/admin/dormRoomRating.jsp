<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
$(document).ready(function(){
	$("ul li:eq(5)").addClass("active");
	$("ul li:eq(5)").css("background-color","lightblue");
	/* $('.datatable').dataTable( {        				
		 "oLanguage": {
				"sUrl": "/DormManage/media/zh_CN.json"
		 },
		"bLengthChange": false, //改变每页显示数据数量
		"bFilter": false, //过滤功能
		"aoColumns": [
			null,
			null,
			null,
			null,
			null,
			{ "asSorting": [ ] },
			{ "asSorting": [ ] }
		]
	}); */
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
	laydate.render({
		elem: '#startDate'
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endDate'
		,type: 'datetime'
	});
});

window.onload = function(){ 
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
};
	function recordDelete(recordId) {
		if(confirm("您确定要删除这条记录吗？")) {
			window.location="record?action=delete&recordId="+recordId;
		}
	}
</script>

<div class="data_list">
		<div class="data_list_title">
			寝室评比
		</div>
		<form name="myForm" class="form-search" method="post" action="visitorRecord?action=search" style="padding-bottom: 0px">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='dormRoomRating?action=preSave'">添加</button>
				<span class="data_search">
					<%-- <span class="controls input-append date form_date" style="margin-right: 10px" data-date="" data-date-format="yyyy-mm-dd" data-link-format="yyyy-mm-dd">
                    	<input id="startDate" name="startDate" style="width:120px;height: 30px;" placeholder="起始日期" type="text" value="${startDate }" readonly >
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		</span> --%>
               		<input id="startDate" name="startDate" style="width:120px;height: 30px;" placeholder="起始日期" type="text" value="${startDate }" readonly >
					<%-- <span class="controls input-append date form_date" style="margin-right: 10px" data-date="" data-date-format="yyyy-mm-dd" data-link-format="yyyy-mm-dd">
                    	<input id="endDate" name="endDate" style="width:120px;height: 30px;" placeholder="终止日期" type="text" value="${endDate }" readonly>
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		 </span> --%>
               		<input id="endDate" name="endDate" style="width:120px;height: 30px;" placeholder="终止日期" type="text" value="${endDate }" readonly>
					<select id="buildToSelect" name="buildToSelect" style="width: 110px;">
					<option value="">全部宿舍楼</option>
					<c:forEach var="dormBuild" items="${dormBuildList }">
						<option value="${dormBuild.dormBuildId }" ${buildToSelect==dormBuild.dormBuildId?'selected':'' }>${dormBuild.dormBuildName }</option>
					</c:forEach>
					</select>
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="dormRoom" ${searchType eq "dormRoom"?'selected':'' }>寝室</option>
					</select>
					&nbsp;<input id="s_visitorRecordText" name="s_visitorRecordText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_visitorRecordText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
					<th>编号</th>
					<th>dorm_build_id</th>
					<th>宿舍楼</th>
					<th>寝室号</th>
					<th>score_clean</th>
					<th>score_culture</th>
					<th>score_obey</th>
					<th>ratingDate</th>
					<th>备注</th>
					<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach  varStatus="i" var="dormRoomRating" items="${DormRoomRatingList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${dormRoomRating.dormBuildId }</td>
						<td>${dormRoomRating.dormBuildName }</td>
						<td>${dormRoomRating.dormRoomNumber }</td>
						<td>${dormRoomRating.scoreClean }</td>
						<td>${dormRoomRating.scoreCulture }</td>
						<td>${dormRoomRating.scoreObey }</td>
						<td>${dormRoomRating.ratingDate }</td>
						<td>${dormRoomRating.remark }</td>
						<td>
							<button class="btn btn-mini btn-danger" type="button" onclick="recordDelete(${visitorRecord.id })">删除</button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<nav aria-label="Page navigation">
		  <ul class="pagination">
		  	${pageCode }
		  </ul>
		</nav>
</div>