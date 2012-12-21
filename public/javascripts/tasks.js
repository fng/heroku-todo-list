function deleteTask(id) {
    jsRoutes.controllers.Tasks.deleteTask().ajax({
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"id": id}),
        error: function (request, error) {
            alert("Error: " + error);
        },
        success: function (request) {
        }

    });

}


$(document).ready(function () {


    $("#addTaskForm").submit(function (event) {

        function onError(errorText) {
            $("#addTaskError").text(errorText)
            form.find("input").removeAttr("disabled")
        }

        function onSuccess() {
            form.find("input").removeAttr("disabled")
            form[0].reset()
            $("#addTaskError").empty()
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