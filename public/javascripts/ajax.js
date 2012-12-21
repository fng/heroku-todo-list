function updateFragments(fragments) {
    $.each(fragments, function (index) {
        var fragment = fragments[index]
        var element = $("#" + fragment.id);
        element.replaceWith(fragment.html)
    })
}


function pollEvents() {
    jsRoutes.controllers.Events.events().ajax({
        success: function (data) {
            var element = $("#" + "counterId");
            element.text(data)
            updateFragments(data.fragments)
            pollEvents()
        },
        error: function (err) {
//            alert("Error: " + err);
        }

    })
}


$(document).ready(function () {
    setTimeout(pollEvents, 2000)
})