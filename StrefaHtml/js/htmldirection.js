jQuery(document).ready(function($){
	 var fileName = location.pathname.split("/").slice(-1)
                                                        var previous = "poprzedni artykuł";
                                                        var next = "następny artykuł";
                                                        var previousResult = previous.link("/previous?fileName=" + fileName);
                                                        var nextResult = next.link("/next?fileName=" + fileName);
                                                        document.getElementById("previous").innerHTML = previousResult;
                                                        document.getElementById("next").innerHTML = nextResult;

});
