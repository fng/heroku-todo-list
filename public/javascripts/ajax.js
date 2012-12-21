
function updateFragments(fragments) {
    $.each(fragments, function (index) {
        var fragment = fragments[index]
        var element = $("#" + fragment.id);
        element.replaceWith(fragment.html)
    })
}
