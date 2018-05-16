<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.lero.model.*" %>
<%
DormRoomRating dormRoomRating = (DormRoomRating)request.getAttribute("dormRoomRating");
DormManager manager = (DormManager)(session.getAttribute("currentUser"));
String dormBuildId = "";
String dormRoomNumber = "";
if(dormRoomRating != null){
	dormBuildId = dormRoomRating.getDormBuildId();
	dormRoomNumber = dormRoomRating.getDormRoomNumber();
}
String buildId = "";
if(manager != null){
    buildId = manager.getDormBuildId()+"";
}
%>
<script type="text/javascript">
	function checkForm(){
		var dormBuildId=$("#dormBuildId") ? $("#dormBuildId").val() : "";
		$("#dormBuildId").attr("disabled", false);
		var dormRoomNumber=$("#dormRoomNumber") ? $("#dormRoomNumber").val() : "";
		$("#dormRoomNumber").attr("disabled", false);
		var scoreClean=$("#scoreClean") ? $("#scoreClean").val() : "";
		var scoreCulture=$("#scoreCulture") ? $("#scoreCulture").val() : "";
		var scoreObey=$("#scoreObey") ? $("#scoreObey").val() : "";
		var remark=$("#remark") ? $("#remark").val() : "";
		if(dormBuildId==""||dormRoomNumber==""||scoreClean==""||scoreCulture==""||scoreObey==null||remark==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		}
		return true;
	}
	function showDormRooms() {
		var promise = {};
		var $select = $("#dormRoomNumber");
		var dormBuildId = $("#dormBuildId").val();
		var param = {};
		param.buildToSelect = dormBuildId;
		if(dormBuildId){
			promise = new Promise(function(resolve, reject) {
				$.ajax({
					url: "/DormManage/dormRoom?action=nopagesearch",
					type: 'POST',
					dataType: "json",			  
					data: param,
					async:false,
					success: function(data){
						//var firstOption = $("#dormRoom").children()[0];
						if(data){
							resolve(data);
							var options = "";
							$.each(data, function(i,item){
								options += "<option value= " + item.dormRoomNumber + ">"+ item.dormRoomNumber +"</option>";
							});
							if($select[0]){
								$select.empty();
								$select.append("<option value=''>请选择...</option>");
								$select.append(options);
							}
						}
					}
				});
			});
		}
		return promise;
	}
	$(document).ready(function(){
		$("ul li:eq(3)").addClass("active");
		$("ul li:eq(3)").css("background-color","lightblue");
		laydate.render({
			elem: '#visInTime'
			,type: 'datetime'
		});
		laydate.render({
			elem: '#visOutTime'
			,type: 'datetime'
		});
		var dormBuildId = "<%=dormBuildId%>";
		var dormRoomNumber = "<%=dormRoomNumber%>";
		var buildId = "<%=buildId%>";
		if(buildId && buildId != ""){
			$("#dormBuildId").val(buildId);
			$("#dormBuildId").attr("disabled","disabled");
			showDormRooms();
		}
		if(dormBuildId && dormBuildId != ""){
			var promise = showDormRooms();
			promise.then(function(pdata){
				if(dormRoomNumber && dormRoomNumber != ""){
					$("#dormRoomNumber").val(dormRoomNumber);
					$("#dormRoomNumber").attr("disabled","disabled");  
				}
			});
			$("#dormBuildId").attr("disabled","disabled");  
		}
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${dormRoomRating.id!=null }">
				修改寝室评比
			</c:when>
			<c:otherwise>
				添加寝室评比
			</c:otherwise>
		</c:choose>
		</div>
		<form action="dormRoomRating?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="id" name="id" value="${dormRoomRating.id }"/>
				<table align="center" style="border-collapse: separate;border-spacing: 10px;">
					<tr>
						<td><font color="red">*</font>宿舍楼：</td>
						<td>
							<select onchange="showDormRooms()" id="dormBuildId" name="dormBuildId" style="width: 90px;">
								<option value="">请选择...</option>
								<c:forEach var="dormBuild" items="${dormBuildList }">
									<option value="${dormBuild.dormBuildId }" ${dormRoomRating.dormBuildId==dormBuild.dormBuildId?'selected':'' }>${dormBuild.dormBuildName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>寝室：</td>
						<td>
							<select id="dormRoomNumber" name="dormRoomNumber" style="width: 90px;">
								<option value="">请选择...</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>卫生得分：</td>
						<td>
							<select id="scoreClean" name="scoreClean">
								<option value="">请选择...</option>
								<option value="1" ${dormRoomRating.scoreClean=='1'?'selected':'' }>1</option>
								<option value="2" ${dormRoomRating.scoreClean=='2'?'selected':'' }>2</option>
								<option value="3" ${dormRoomRating.scoreClean=='3'?'selected':'' }>3</option>
								<option value="4" ${dormRoomRating.scoreClean=='4'?'selected':'' }>4</option>
								<option value="5" ${dormRoomRating.scoreClean=='5'?'selected':'' }>5</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>文化得分：</td>
						<td>
							<select id="scoreCulture" name="scoreCulture">
								<option value="">请选择...</option>
								<option value="1" ${dormRoomRating.scoreCulture=='1'?'selected':'' }>1</option>
								<option value="2" ${dormRoomRating.scoreCulture=='2'?'selected':'' }>2</option>
								<option value="3" ${dormRoomRating.scoreCulture=='3'?'selected':'' }>3</option>
								<option value="4" ${dormRoomRating.scoreCulture=='4'?'selected':'' }>4</option>
								<option value="5" ${dormRoomRating.scoreCulture=='5'?'selected':'' }>5</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>纪律得分：</td>
						<td>
							<select id="scoreObey" name="scoreObey">
								<option value="">请选择...</option>
								<option value="1" ${dormRoomRating.scoreObey=='1'?'selected':'' }>1</option>
								<option value="2" ${dormRoomRating.scoreObey=='2'?'selected':'' }>2</option>
								<option value="3" ${dormRoomRating.scoreObey=='3'?'selected':'' }>3</option>
								<option value="4" ${dormRoomRating.scoreObey=='4'?'selected':'' }>4</option>
								<option value="5" ${dormRoomRating.scoreObey=='5'?'selected':'' }>5</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>备注：</td>
						<td>
							<textarea id="remark" name="remark" placeholder="备注" type="text">${dormRoomRating.remark }</textarea>
	               		</td>
					</tr>
					<tr>
						<td>
							<input id="checkedId" name="checkedId" type="hidden" value="${currentUser.userName }">
	               		</td>
					</tr>
				</table>
				<div align="center">
					<input type="submit" class="btn btn-primary" value="保存"/>
					&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='dormRoomRating?aciton=list'">返回</button>
				</div>
				<div align="center">
					<font id="error" color="red">${error }</font>
				</div>
			</div>
		</form>
</div>