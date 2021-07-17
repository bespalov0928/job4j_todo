function authorization() {
    alert("auth");
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/auth.do',
        data: JSON.stringify({
            email: 'email=' + $('#email').val(),
            pas: 'pas=' + $('#pas').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        console.log('data');
        console.log(data);
        alert("data")
        window.location.href = 'http://localhost:8080/todo/index.html';
    }).fail(function (err) {
        console.log('err');
        console.log(err);
        alert("err")
        window.location.href = 'http://localhost:8080/todo/error.html';

    });
    alert("auth end");

}

function reg() {


    console.log('email: '+$('#email').val());
    console.log('pas: ' + $('#pas').val());
    alert("begin reg");
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg.do',
        data: JSON.stringify({
            email: 'email=' + $('#email').val(),
            pas: 'pas=' + $('#pas').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        console.log(data);
        window.location = 'http://localhost:8080/todo/index.html';
    }).fail(function (err) {
        console.log(err);
        window.location='http://localhost:8080/todo/error.html';
    });
    alert("end reg");

}

function validate() {

}