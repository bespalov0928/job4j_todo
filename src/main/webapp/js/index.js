function createItem() {
    console.log($('#idCreated').val());
    $.ajax({

        type: 'POST',
        url: 'http://localhost:8080/todo/createItem.do',
        data: JSON.stringify({
            desc: 'desc=' + $('#idCreated').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        console.log("data: " + data);
    }).fail(function (err) {
        console.log("err: " + err);
    });
}

function update() {

    var table = document.getElementById("tbody");
    for (x = 0; x < table.rows.length; x++) {
        table.deleteRow(x);
    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/update.do',
        dataType: 'json'
    }).done(function (data) {
        console.log("data: " + data);
        var view = document.getElementById("idAllView");
        var tbody = document.getElementById("tbody");
        for (index = 0; index < data.length; index++) {
            if (!view.checked && !data[index].done) {
                continue;
            }
            var tr = document.createElement("tr");

            var th = document.createElement("th");
            th.innerHTML = index + 1;
            tr.appendChild(th);

            var td = document.createElement("td");
            tr.appendChild(td);

            var input = document.createElement("input");
            input.setAttribute("type", "checkbox");
            input.setAttribute("value", data[index].id);
            input.setAttribute("id", "idInput/" + index);

            if (data[index].done) {
                input.setAttribute("checked", "checked");
            }
            input.setAttribute("onclick", "edit()")
            td.appendChild(input);

            var td1 = document.createElement("td");
            td1.setAttribute("id", "idDesc/"+index);
            td1.innerHTML = data[index].description;
            tr.appendChild(td1);
            tbody.appendChild(tr);
        }


    }).fail(function (err) {
        console.log("err: " + err);
    });
}

function edit() {

    var array = new Array();
    var tbody = document.getElementById("tbody");

    for (x = 0; x < tbody.rows.length; x++) {
        var input = document.getElementById("idInput/" + x);
        var desc = document.getElementById("idDesc/" + x)
        console.log(input.value);
        array[x] = input.value+"/"+desc.innerText+"/"+input.checked;
        console.log(array.length);

    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/edit.do',
        data: JSON.stringify({
            arr: 'arr='+array
        }),
        dataType: 'json'
    }).done(function (data) {
        console.log("data: " + data);
    }).fail(function (err) {
        console.log("err: " + err);
    });

}