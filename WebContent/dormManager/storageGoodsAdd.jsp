<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function checkForm(){
		var visSex=$("#stuNum") ? $("#stuNum").val() : "";
		var visPhone=$("#stuName") ? $("#stuName").val() : "";
		var visInTime=$("#inTime") ? $("#inTime").val() : "";
		var visOutTime=$("#outTime") ? $("#outTime").val() : "";
		var checkedId=$("#status") ? $("#status").val() : "";
		var visDormBuild=$("#remark") ? $("#remark").val() : "";
		if(visSex==""||visPhone==""||visInTime==null||visOutTime==""||visDormBuild==""||checkedId==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		$("ul li:eq(5)").addClass("active");
		$("ul li:eq(5)").css("background-color","lightblue");
		laydate.render({
			elem: '#inTime'
			,type: 'datetime'
		});
		laydate.render({
			elem: '#outTime'
			,type: 'datetime'
		});
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${visitorRecord.id!=null }">
				修改物品寄存状态
			</c:when>
			<c:otherwise>
				添加物品寄存
			</c:otherwise>
		</c:choose>
		</div>
		<form action="storageGoodsWeb?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="id" name="id" value="${visitorRecord.id }"/>
				<table align="center">
					<tr>
						<td><font color="red">*</font>名字：</td>
						<td><input type="text" id="stuName"  name="stuName" value="${visitorRecord.stuName }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>学号：</td>
						<td><input type="text" id="stuNum"  name="stuNum" value="${visitorRecord.stuNum }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>物品寄存状态：</td>
						<td>
							<select id="status" name="status" value="${visitorRecord.visSex=='0' ? '暂存' : '已取' }" style="margin-top:5px;height:30px;">
								<option value="">请选择...</option>
								<option value="0">暂存</option>
							<!-- 	<option value="1">已取</option> -->
							</select>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>寄存时间：</td>
						<td><input type="text" id="inTime"  name="inTime" placeholder="寄存时间" value="${visitorRecord.inTime }"  style="margin-top:5px;height:30px;" /></td>
					</tr>
					<tr>
						<td><font color="red">*</font>预计取走时间：</td>
						<td>
							<input id="outTime" name="outTime" placeholder="取走时间" type="text" value="${visitorRecord.outTime }">
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
					&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='storageGoodsWeb'">返回</button>
				</div>
				<div align="center">
					<font id="error" color="red">${error }</font>
				</div>
			</div>
		</form>
</div>