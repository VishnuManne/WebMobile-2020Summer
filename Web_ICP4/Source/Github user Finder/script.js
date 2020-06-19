function getGithubInfo(user) {
    // Sending the GET request and receiving the response
    var username='https://api.github.com/users/'+user;
    console.log(username);
    $.ajax({
        type: "GET",
        url: username,
        dataType: 'json',

    }).done(function(data){
        showUser(data);

    }).fail(function(){
        console.log("Some error Happened");
        noSuchUser(user);
    });

}

function showUser(user) {
    // filling the elements with the user content
    document.getElementById('image').src=user.avatar_url;
    document.getElementById('user_name').innerText=user.name;
    document.getElementById('user_id').innerText=user.id;
    document.getElementById('user_url').href=user.url;
    document.getElementById('user_url').innerText=user.html_url;
    document.getElementById('user_repository').innerText=user.public_repos;
    document.getElementById('user_followers').innerText=user.followers;
    document.getElementById('user_following').innerText=user.following;
    document.getElementById('user_location').innerText=user.location;
}
function noSuchUser(username) {
    if(data.message == "Not Found" || username == '') {
        alert("User not found");
    }
}
$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        if (e.which == 13) {
            username = $(this).val();
            $(this).val("");
            getGithubInfo(username);
        }
    })
});