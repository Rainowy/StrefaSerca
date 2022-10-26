$(document).ready(function () {
    //Show modal on first visit and save to localStorage
    let ls = localStorage.getItem('strefa.visited');
    if (ls == null) {
        $('#cookieModal').modal('show');
        localStorage.setItem('strefa.visited', 1)
    }
});