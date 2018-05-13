<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function changeDormBuild(){
		if($("#dormBuildId").val()){
			var selectedText = $('#dormBuildName option:selected').text();
			if(selectedText){
				$("#dormBuildName").val(selectedText);
			}
		}		
	}
	function checkForm(){
		var dormBuildId=$("#dormBuildId").val()?$("#dormBuildId").val():"";
		var dormRoomNumber=$("#dormRoomNumber").val()?$("#dormRoomNumber").val():"";
		var dormRoomName=$("#dormRoomName").val()?$("#dormRoomName").val():"";
		var dormRoomTel=$("#dormRoomTel").val()?$("#dormRoomTel").val():"";
		var remark=$("#remark").val()?$("#remark").val():"";
		
		
		if(dormBuildId==""||dormRoomNumber==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		$("ul li:eq(4)").addClass("active");
		$("ul li:eq(4)").css("background-color","lightblue");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${dormRoom.id!=null }">
				修改寝室信息
			</c:when>
			<c:otherwise>
				添加寝室信息
			</c:otherwise>
		</c:choose>
		</div>
		<form action="dormRoom?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="id" name="id" value="${dormRoom.id }"/>
				<input type="hidden" id="dormBuildName" name="dormBuildName"/>
					<table align="center">
						<tr>
							<td><font color="red">*</font>宿舍楼：</td>
							<td>
								<select onchange="changeDormBuild()" id="dormBuildId" name="dormBuildId" style="width: 90px;">
									<c:forEach var="dormBuild" items="${dormBuildList }">
										<option value="${dormBuild.dormBuildId }" ${dormRoom.dormBuildId==dormBuild.dormBuildId?'selected':'' }>${dormBuild.dormBuildName }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>寝室号：</td>
							<td><input type="text" id="dormRoomNumber"  name="dormRoomNumber" value="${dormRoom.dormRoomNumber }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red"></font>寝室名：</td>
							<td><input type="text" id="dormRoomName"  name="dormRoomName" value="${dormRoom.dormRoomName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red"></font>联系电话：</td>
							<td><input type="text" id="dormRoomTel"  name="dormRoomTel" value="${dormRoom.dormRoomTel }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red"></font>简介：</td>
							<td><textarea type="text" id="remark"  name="remark" value="${dormRoom.remark }"  style="margin-top:5px;height:30px;"></textarea></td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='dormRoom'">返回</button>
					</div>
					<div align="center">
						<font id="error" color="red">${error }</font>
					</div>
			</div>
		</form>
</div>