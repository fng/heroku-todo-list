function refreshTasks() {
    jsRoutes.controllers.Tasks.tasks().ajax({
        success: function (data) {
            updateNumberOfTasks(data.tasks.length)
            updateTasksTable(data.tasks)
        },
        error: function (err) {
            alert("Error: " + err);
        }

    });

}

function deleteTask(id) {
    jsRoutes.controllers.Tasks.deleteTask().ajax({
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"id": id}),
        error: function (request, error) {
            alert("Error: " + error);
        },
        success: function (request) {
            refreshTasks()
        }

    });

}


function updateNumberOfTasks(numberOfTasks) {
    var span = $("#numberOfTasks")
    span.text(numberOfTasks)
}

function updateTasksTable(tasks) {
    var tasksBlock = $("#tasksBlock")
    if (tasks.length > 0) {
        tasksBlock.show()
    } else {
        tasksBlock.hide()
    }

    var tableBody = $("#tasksTable tbody")
    tableBody.empty()
    $.each(tasks, function (index) {
        tableBody.append('<tr><td>' + tasks[index].label + '</td><td>' + '<a class="btn btn-mini btn-warning" onclick="deleteTask(' + tasks[index].id + ')"><i class="icon-trash  icon-white"></i></a>' + '</td></tr>')
    })


}

$(document).ready(function () {

    refreshTasks()

    $("#addTaskForm").submit(function (event) {

        function onError(errorText) {
            $("#addTaskError").text(errorText)
            form.find("input").removeAttr("disabled")
        }

        function onSuccess() {
            form.find("input").removeAttr("disabled")
            refreshTasks()
            form[0].reset()
            $("#addTaskError").empty()
            $("#collapseTwo").collapse('hide')
        }

        event.preventDefault()
        var form = $(this)
        var inputLabel = form.find("input[id=label]").val()

        //Disable all input fie3lds
        form.find("input").attr("disabled", "disabled")

        //Client side input validation
        if (!inputLabel) {
            onError("Label is required!")
            return
        }

        jsRoutes.controllers.Tasks.addTask().ajax({
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"label": inputLabel}),
            error: function (request, error) {
                onError("Error adding task: " + request.responseText)
            },
            success: function (request) {
                onSuccess()
            }
        });

    })

})