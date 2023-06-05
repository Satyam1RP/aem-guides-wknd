import $ from "jquery";
(function() {
    "use strict";
    // Best practice:
    // For a good separation of concerns, don't rely on the DOM structure or CSS selectors,
    // but use dedicated data attributes to identify all elements that the script needs to
    // interact with.
    var selectors = {
        self:      '[data-cmp-is="cmp-universitycard"]'
    };
    function UniversityCard(config) {

        function init(config) {
            // Best practice:
            // To prevents multiple initialization, remove the main data attribute that
            // identified the component.
            config.removeAttribute("data-cmp-is");
            let cardItems = config.querySelectorAll(".cmp-universitycard__content__left--cards--card");
            let playButton = config.querySelector(".cmp-universitycard__content__right__container--videoplay");
            let pauseButton = config.querySelector(".cmp-universitycard__content__right__container--videopause");
            let interval = 2000;
            let activated = false;
            $(window).on('scroll', function(){
                let scrollTop = $(window).scrollTop() + $(window).height();
                if(scrollTop > $('.cmp-universitycard').offset().top + 300 && activated === false){
                    cardItems.forEach((carditem) => {
                        let value = carditem.firstElementChild;
                        let startValue = parseInt(carditem.firstElementChild.getAttribute("cmp-universitycard-minnumber"));
                        let endValue =   parseInt(carditem.firstElementChild.getAttribute("cmp-universitycard-maxnumber"));
                        let ispercent = carditem.firstElementChild.getAttribute("data-ispercent");
                        let duration = Math.floor(interval/endValue);
                        let counter = this.setInterval(function () {
                            startValue += 1;
                            if (ispercent == "true") {
                                value.textContent = startValue+"%";
                            } else {
                                value.textContent = startValue;
                            }
                            if (startValue == endValue ) {
                                clearInterval(counter);
                            }
                        }, duration);
                    }); 
                    activated = true;
                }else if(scrollTop < $('.cmp-universitycard').offset().top + 300 && activated === true){
                    cardItems.forEach((carditem) => {
                        let value = carditem.firstElementChild;
                        let startValue = parseInt(carditem.firstElementChild.getAttribute("cmp-universitycard-minnumber"));
                        let ispercent = carditem.firstElementChild.getAttribute("data-ispercent");
                        if (ispercent == "true") {
                            value.innerHTML = startValue+"%";
                        } else {
                            value.innerHTML = startValue;
                        }
                    });
                    activated = false;
                }
              });
        cardItems.forEach((item, index)=>{
            if(index%2!==0){
                item.classList.add("cmp-universitycard__content__shifted");
            }
        });
        playButton.addEventListener("click", () => {
           config.querySelector('#cmp-universitycard__samplevideo').play();
           playButton.style.display = "none"
           pauseButton.style.display = "block";
        });
        pauseButton.addEventListener("click", () => {
            config.querySelector('#cmp-universitycard__samplevideo').pause();
            pauseButton.style.display = "none";
            playButton.style.display = "block"
        });
          
        }

        if (config && config.element) {
            init(config.element);
        }
    }
    // Best practice:
    // Use a method like this mutation obeserver to also properly initialize the component
    // when an author drops it onto the page or modified it with the dialog.
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new UniversityCard({ element: elements[i] });
        }
        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        var body             = document.querySelector("body");
        var observer         = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                // needed for IE
                var nodesArray = [].slice.call(mutation.addedNodes);
                if (nodesArray.length > 0) {
                    nodesArray.forEach(function(addedNode) {
                        if (addedNode.querySelectorAll) {
                            var elementsArray = [].slice.call(addedNode.querySelectorAll(selectors.self));
                            elementsArray.forEach(function(element) {
                                new UniversityCard({ element: element });
                            });
                        }
                    });
                }
            });
        });

        observer.observe(body, {
            subtree: true,
            childList: true,
            characterData: true
        });
    }
    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
}());
