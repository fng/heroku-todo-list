
function refreshTasks(){
jsRoutes.controllers.Application.tasks().ajax({
        success: function(data){
            updateNumberOfTasks(data.tasks.length)
            updateTasksTable(data.tasks)
//                        alert("Success: " + data.tasks.length);
        },
        error: function(err){
            alert("Error: " + err);
        }

    });

}

function addTask(label){
jsRoutes.controllers.Application.addTask().ajax({
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"label": label}),
        error: function(request, error){
            alert("Error: " + error);
        },
        success: function(request){
            refreshTasks()
//                           alert("Success: " + request);
        }

    });

}

function deleteTask(id){
jsRoutes.controllers.Application.deleteTask().ajax({
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"id": id}),
        error: function(request, error){
            alert("Error: " + error);
        },
        success: function(request){
            refreshTasks()
//                           alert("Success: " + request);
        }

    });

}


function updateNumberOfTasks(numberOfTasks){
    var span = $("#numberOfTasks")
    span.text(numberOfTasks)
}
function updateTasksTable(tasks){
    var tasksBlock = $("#tasksBlock")
    if(tasks.length > 0) {
        tasksBlock.show()
    } else {
        tasksBlock.hide()
    }

    var tableBody = $("#tasksTable tbody")
    tableBody.empty()
    $.each(tasks, function(index){
        tableBody.append('<tr><td>' + tasks[index].label + '</td><td>' + '<a class="btn btn-mini btn-warning" onclick="deleteTask('+tasks[index].id +')"><i class="icon-trash  icon-white"></i></a>' + '</td></tr>')
    })


}
$(document).ready(function(){

        refreshTasks()

       $("#addTaskForm").submit(function(event){
          event.preventDefault()
          var form = $(this)
          var input = form.find("input[id=label]").val()
          addTask(input)
          form[0].reset()
          $("#collapseTwo").collapse('hide')
      })

   })