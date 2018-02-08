function getUserInfo(userId) {
    $.get("user", {"id": userId}, function (data) {
        var result = JSON.parse(data);
        var item = "<tr class='userInfoRow'><td>" + result['userId'] + "</td><td>"
            + result['firstname'] + "</td><td>" + result['lastname'] + "</td><td>"
            + result['passport'] + "</td><td>" + result['email'] + "</td><td>"
            + "<button class='btn btn-primary form-control' onclick='hideUserInfoTable()'>"
            + "<span class='fa fa-times-circle' aria-hidden='true'></span>Close"
            + "</td></tr>";
        console.log(result.userId);
        console.log(result["userId"]);
        console.log(typeof result);
        $("#userInfoTable").append(item);
        
        $("#userInfoTable").css("display",  "");
    })
}

function hideUserInfoTable() {
    $("#userInfoTable .userInfoRow").remove();
    $("#userInfoTable").css("display",  "none");
}