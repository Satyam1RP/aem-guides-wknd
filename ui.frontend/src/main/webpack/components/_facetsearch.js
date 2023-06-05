import $ from "jquery";
(function () {
    "use strict";
    console.log("start")
    const searchResult = document.getElementById("search_Result");
    const searchPath = $(".cmp-search__facets").attr("cmp-facetsearch-path");
    let tags = [];
    let industry
    let sector
    let country
    $("#industry,#sector,#country").on("change",function(){
        industry = $("#industry").val();
        sector = $("#sector").val();
        country = $("#country").val();
        tags = [industry,sector,country];
    })
    $(".cmp-search__facets__detail--searchbutton").click(function (e) {
        e.preventDefault();
            if(industry != "" || sector != "" || country != ""){
                search();
                document.querySelector(".cmp-search__facets__results").style.display = "block";
            }else{
                document.querySelector(".cmp-search__facets__results").style.display = "none";
            }
        })
    function search() {
        if (tags.length>0) {
           $.ajax({
                type: "GET",
                url: "/bin/facetsearch",
                data: "searchTags=" + tags + "&searchPath=" + searchPath,
                dataType: "json",
                success: function (response) {
                    let markup = "";
                    let template = "";
                    let noOfResults = parseInt(response.totalresult);
                    let results = response.results;
                    searchResult.innerHTML = "Total Results : "+noOfResults;
                    for (let index = 0; index < noOfResults; index++) {
                        template = `<div class="cmp-customsearch__results__items">
                                            <a class="cmp-customsearch__results__items-title" href="{{path}}">
                                                <h4 class="hover-underline-animation">{{title}}</h4>
                                            </a>
                                    </div>`;
                         template = template.replace("{{title}}", results[index].title);
                         template = template.replace("{{path}}", results[index].path+".html");
                         markup = markup + template; 
                    }                
                    $(".cmp-search__facets__results--container").html(markup);
                }
           })      
        } 
    }        
})();