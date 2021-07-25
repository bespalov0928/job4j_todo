function authorization() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/auth.do',
        data: JSON.stringify({
            email: 'email=' + $('#email').val(),
            pas: 'pas=' + $('#pas').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        window.location.href = 'http://localhost:8080/todo/index.html';
    }).fail(function (err) {
        alert("err")
        console.log('err');
        console.log(err);
        window.location.href = 'http://localhost:8080/todo/error.html';

    });
}

function reg() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg.do',
        data: JSON.stringify({
            email: 'email=' + $('#email').val(),
            pas: 'pas=' + $('#pas').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        window.location = 'http://localhost:8080/todo/index.html';
    }).fail(function (err) {
        alert("err")
        console.log("err");
        console.log(err);
        window.location='http://localhost:8080/todo/error.html';
    });
}

function validate() {

}