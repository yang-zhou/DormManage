<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.lero.model.*" %>
<%

%>
<script type="text/javascript">
	function checkForm(){
		var visName=$("#visName") ? $("#visName").val() : "";
		var visIdCard=$("#visIdCard") ? $("#visIdCard").val() : "";
		var visSex=$("#visSex") ? $("#visSex").val() : "";
		var visPhone=$("#visPhone") ? $("#visPhone").val() : "";
		var visInTime=$("#visInTime") ? $("#visInTime").val() : "";
		var visOutTime=$("#visOutTime") ? $("#visOutTime").val() : "";
		var checkedId=$("#checkedId") ? $("#checkedId").val() : "";
		var visDormBuild=$("#visDormBuild") ? $("#visDormBuild").val() : "";
		var visDormBuildRoom=$("#visDormBuildRoom") ? $("#visDormBuildRoom").val() : "";
		var remark=$("#remark") ? $("#remark").val() : "";
		if(visName==""||visIdCard==""||visSex==""||visPhone==""||visInTime==null||visOutTime==""||visDormBuild==""||visDormBuildRoom==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		}
		return true;
	}
	function showDormRooms() {
		var promise = {};
		var $select = $("#visDormBuildRoom");
		var dormBuildId = $("#visDormBuild").val();
		var param = {};
		param.buildToSelect = dormBuildId;
		if(dormBuildId){
			promise = new Promise(function(resolve, reject) {
				$.ajax({
					url: "/DormManage/dormRoom?action=nopagesearch",
					type: 'POST',
					dataType: "json",			  
					data: param,
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
		$("ul li:eq(7)").addClass("active");
		$("ul li:eq(7)").css("background-color","lightblue");
		laydate.render({
			elem: '#visInTime'
			,type: 'datetime'
		});
		laydate.render({
			elem: '#visOutTime'
			,type: 'datetime'
		});
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${visitorRecord.id!=null }">
				修改访问记录
			</c:when>
			<c:otherwise>
				添加访问记录
			</c:otherwise>
		</c:choose>
		</div>
		<form action="visitorRecord?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="id" name="id" value="${visitorRecord.id }"/>
				<table align="center">
					<tr>
						<td><font color="red">*</font>名字：</td>
						<td><input type="text" id="visName"  name="visName" value="${visitorRecord.visName }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>性别：</td>
						<td>
							<select id="visSex" name="visSex" value="${visitorRecord.visSex=='1' ? '男' : '女' }" style="margin-top:5px;height:30px;">
								<option value="">请选择...</option>
								<option value="1">男</option>
								<option value="0">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>身份号：</td>
						<td><input type="text" id="visIdCard"  name="visIdCard" value="${visitorRecord.visIdCard }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>联系电话：</td>
						<td><input type="text" id="visPhone"  name="visPhone" value="${visitorRecord.visPhone }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>宿舍楼：</td>
						<td>
							<select  onchange="showDormRooms()" id="visDormBuild" name="visDormBuild" style="width: 90px;">
								<option>请选择...</option>
								<c:forEach var="visDormBuild" items="${dormBuildList }">
									<option value="${visDormBuild.dormBuildId }" ${visitorRecord.dormBuildId==visDormBuild.dormBuildId?'selected':'' }>${visDormBuild.dormBuildName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>寝室：</td>
						<td>
							<select id="visDormBuildRoom" name="visDormBuildRoom" style="width: 90px;">
								<option>请选择...</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>访问时间：</td>
						<td><input type="text" id="visInTime"  name="visInTime" placeholder="访问时间" value="${visitorRecord.visInTime }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>出寝时间：</td>
						<td>
							<input id="visOutTime" name="visOutTime" placeholder="出寝时间" type="text" value="${visitorRecord.visOutTime }">
	               		</td>
					</tr>
					<tr>
						<td><font color="red">*</font>备注：</td>
						<td>
							<textarea id="remark" name="remark" placeholder="备注" type="text" value="${visitorRecord.remark }"></textarea>
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
					&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='visitorRecord'">返回</button>
				</div>
				<div align="center">
					<font id="error" color="red">${error }</font>
				</div>
			</div>
		</form>
</div>