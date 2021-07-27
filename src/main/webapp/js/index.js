function createItem() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/createItem.do',
        data: JSON.stringify({
            desc: 'desc=' + $('#idCreated').val()
            ,opt: Array.from(document.getElementById('idSelect').selectedOptions).map(el => el.value)
        }),
        dataType: 'json'
    }).done(function (data) {
    }).fail(function (err) {
        alert("errCreateItem")
        console.log("errCreateItem");
        console.log(err);
        window.location.href = 'http://localhost:8080/todo/error.html';

    });
}

function update() {

    var table = document.getElementById("tbody");
    for (x = 0; x < table.rows.length; x++) {
        table.deleteRow(x);
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/update',
        dataType: 'json'
    }).done(function (data) {

        // console.log(data);
        for (x = 0; x < table.rows.length; x++) {
            table.deleteRow(x);
        }

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
            td1.setAttribute("id", "idDesc/" + index);
            td1.innerHTML = data[index].description;
            tr.appendChild(td1);

             var td2 = document.createElement("td");
            td2.setAttribute("id", "idDate/" + index);
            td2.innerHTML = data[index].timeCreat;
            tr.appendChild(td2);

            var td3 = document.createElement("td");
            if (data[index].acaunt != null) {
                td3.innerHTML = data[index].acaunt.login;
            }
            tr.appendChild(td3);

            var td4 = document.createElement("td");
            var innerHTML = "";
            // console.log(data[index]);
            // console.log(data[index].categories);
            // console.log(data[index].categories);
             for (x = 0; x < data[index].categories.length; x++) {
                 // console.log(data[index].categories[x]);
                 innerHTML = innerHTML + ";" + data[index].categories[x].name;
             }
            // console.log(innerHTML);
            td4.innerText = innerHTML;
            tr.appendChild(td4);


            tbody.appendChild(tr);
        }
    }).fail(function (err) {
        alert("errUpdate");
        console.log("errUpdate");
        console.log(err);
        window.location.href = 'http://localhost:8080/todo/error.html';
    });
}

function edit() {

    var array = new Array();
    var tbody = document.getElementById("tbody");

    for (x = 0; x < tbody.rows.length; x++) {
        var input = document.getElementById("idInput/" + x);
        var desc = document.getElementById("idDesc/" + x);
        var date = document.getElementById("idDate/" + x);
        array[x] = input.value + "/" + desc.innerText + "/" + input.checked + "/" + date.innerText;
    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/edit.do',
        data: JSON.stringify({
            arr: 'arr=' + array
        }),
        dataType: 'json'
    }).done(function (data) {
    }).fail(function (err) {
        alert("errEdit")
        console.log("errEdit");
        console.log(err);
        window.location.href = 'http://localhost:8080/todo/error.html';
    });

}

function categories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/categories',
        dataType: 'json'
    }).done(function (data) {
         select = document.getElementById("idSelect");
        for (var i = 0; i < data.length; i++) {
            var opt = document.createElement("option");
            opt.textContent = data[i].name;
            opt.value = data[i].id;
            select.appendChild(opt);
        }
    }).fail(function (err) {
        alert('errCategories');
        console.log("errCategories");
        console.log(err);
    });
}