(function () {
  "use strict";

  var selectors = {
    self: '[data-cmp-is="product-component"]',
  };
  function product(config) {
    function init(config) {
      config.removeAttribute("data-cmp-is");
      console.log("hello");
      let firstView = config.querySelector(".cmp-product-active");
      let secondView = config.querySelector(".cmp-product");

      console.log(firstView);
      console.log(secondView);
      firstView.addEventListener("click", () => {
        console.log("inside view");
        firstView.style.display = "none";
        secondView.style.display = "flex";
      });
    }
    if (config && config.element) {
      init(config.element);
    }
  }
  function onDocumentReady() {
    var elements = document.querySelectorAll(selectors.self);
    for (var i = 0; i < elements.length; i++) {
      new product({ element: elements[i] });
    }
    var MutationObserver =
      window.MutationObserver ||
      window.WebKitMutationObserver ||
      window.MozMutationObserver;
    let body = document.querySelector("body");
    var observer = new MutationObserver(function (mutations) {
      mutations.forEach(function (mutation) {
        // needed for IE
        var nodesArray = [].slice.call(mutation.addedNodes);
        if (nodesArray.length > 0) {
          nodesArray.forEach(function (addedNode) {
            if (addedNode.querySelectorAll) {
              var elementsArray = [].slice.call(
                addedNode.querySelectorAll(selectors.self)
              );
              elementsArray.forEach(function (element) {
                new product({ element: element });
              });
            }
          });
        }
      });
    });

    observer.observe(body, {
      subtree: true,
      childList: true,
      characterData: true,
    });
  }
  if (document.readyState !== "loading") {
    onDocumentReady();
  } else {
    document.addEventListener("DOMContentLoaded", onDocumentReady);
  }
})();
